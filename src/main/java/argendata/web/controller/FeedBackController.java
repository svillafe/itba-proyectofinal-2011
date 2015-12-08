package argendata.web.controller;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import argendata.dao.semantic.impl.RepositoryConnectionManager;
import argendata.model.relational.ArgendataUser;
import argendata.service.MailService;
import argendata.service.relational.ArgendataUserService;
import argendata.util.Captcha;
import argendata.util.Messages;
import argendata.util.Properties;
import argendata.web.command.ReportProblemForm;
import argendata.web.validator.ReportProblemFormValidator;

@Controller
public class FeedBackController {

	private MailService mailService;
	private ReportProblemFormValidator reportProblemValidator;
	private Properties properties;
	private ArgendataUserService userService;
	private Messages messages;
	private final Logger logger = Logger.getLogger(FeedBackController.class);

	@Autowired
	public FeedBackController(MailService mailService,
			ReportProblemFormValidator reportProblemValidator,
			Properties properties, ArgendataUserService userService, Messages messages) {
		this.mailService = mailService;
		this.reportProblemValidator = reportProblemValidator;
		this.properties = properties;
		this.userService = userService;
		this.messages = messages;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView reportProblem(HttpSession session,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "title", required = false) String title) {

		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		if (user != null) {
			mav.addObject(new ReportProblemForm(type, title, user.getUsername()));
			mav.addObject("privateKey",properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey",properties.getReCaptchaPublicKey());
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView reportProblem(HttpSession session,
			ReportProblemForm reportProblemForm, Errors err,
			@RequestParam("recaptcha_challenge_field") String challangeField,
			@RequestParam("recaptcha_response_field") String responseField,
			ServletRequest servletRequest) {

		ModelAndView mav = new ModelAndView();

		reportProblemValidator.validate(reportProblemForm, err);
		if (err.hasErrors()) {
			logger.error("Errores en la validacion del formulario");
			mav.addObject("privateKey",properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey",properties.getReCaptchaPublicKey());
			mav.setViewName("feedback/reportProblem");
			return mav;
		}

		if (!Captcha.check(servletRequest, properties.getReCaptchaPrivateKey(),
				challangeField, responseField)) {
			err.reject("ReportProblem.invalid.captcha",
					"El captcha fue mal ingresado");
			mav.addObject("privateKey",properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey",properties.getReCaptchaPublicKey());
			mav.setViewName("feedback/reportProblem");
			return mav;
		}

		List<String> adminsMails = userService.getAdminsMail();

		String body = reportProblemForm.build();
		String subject = this.messages.getMailErrorReportSubject();
		String cType = "text/plain";

		for (String mail : adminsMails) {
			mailService.sendMail(mail, subject, body, cType);
		}

		mav.setViewName("redirect:../main/home");
		return mav;

	}
}
