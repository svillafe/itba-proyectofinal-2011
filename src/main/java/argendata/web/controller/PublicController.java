package argendata.web.controller;

import java.math.BigInteger;
import java.security.SecureRandom;

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

import argendata.model.relational.ArgendataUser;
import argendata.service.MailService;
import argendata.service.relational.ArgendataUserService;
import argendata.util.Captcha;
import argendata.util.Messages;
import argendata.util.Properties;
import argendata.web.command.UserForm;
import argendata.web.validator.UserRegisterFormValidator;

@Controller
public class PublicController {

	private UserRegisterFormValidator registerFormValidator;
	private ArgendataUserService argendataUserService;
	private MailService mailService;
	private Properties properties;
	private Messages messages;
	private static final Logger logger = Logger
			.getLogger(PublicController.class);

	@Autowired
	public PublicController(ArgendataUserService argendataUserService,
			UserRegisterFormValidator registerFormValidator,
			MailService mailService, Properties properties, Messages messages) {
		this.registerFormValidator = registerFormValidator;
		this.argendataUserService = argendataUserService;
		this.mailService = mailService;
		this.messages = messages;
		this.properties = properties;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView register(
			@RequestParam(value = "e", required = false) String e) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("userForm", new UserForm());
		mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
		mav.addObject("publicKey", properties.getReCaptchaPublicKey());

		if (e != null) {
			mav.addObject("msg", this.messages.getMessageUserMustLogin());
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView register(UserForm userForm, Errors errors,
			HttpSession session,
			@RequestParam("recaptcha_challenge_field") String challangeField,
			@RequestParam("recaptcha_response_field") String responseField,
			ServletRequest servletRequest) {

		ModelAndView mav = new ModelAndView();
		String username;
		String key;
		ArgendataUser newPerson;

		registerFormValidator.validate(userForm, errors);
		if (errors.hasErrors()) {
			// mav.addObject("userForm",userForm);
			mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey", properties.getReCaptchaPublicKey());
			mav.setViewName("public/register");
			return mav;
		}

		if (!Captcha.check(servletRequest, properties.getReCaptchaPrivateKey(),
				challangeField, responseField)) {
			errors.reject("public.invalid.captcha",
					"El captcha fue mal ingresado");
			// mav.addObject("userForm",userForm);
			// mav.addObject(errors);
			mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey", properties.getReCaptchaPublicKey());
			mav.setViewName("public/register");
			return mav;
		}

		try {

			newPerson = userForm.build();
			username = newPerson.getUsername();
			key = generateRandomKey();
			newPerson.setKey(key);
			argendataUserService.registerUser(newPerson);

		} catch (Exception e) {

			logger.error("Errores al intentar registrar un nuevo usuario en la base de datos");
			logger.error(e.getMessage(), e);
			errors.reject("registration.error");
			return mav;
		}

		String link = properties.getMainURL() + "/public/activate?username="
				+ username + "&key=" + key;
		String subject = this.messages.getMailUserNewAccountSubject();
		String content = String.format(
				this.messages.getMailUserNewAccountBody(), link);

		mailService.sendMail(newPerson.getEmail(), subject, content,
				"text/plain");
		mav.setViewName("redirect:../static/registerSucces");
		return mav;
	}

	private String generateRandomKey() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	private String generateRandomPassword() {
		SecureRandom random = new SecureRandom();
		return (new BigInteger(130, random).toString(32)).substring(8, 18);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView forget(
			@RequestParam(value = "email") final String email,
			@RequestParam(value = "key") String key) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = argendataUserService.forgetValidation(email, key);

		if (user != null) {

			final String newPassword = generateRandomPassword();

			user.setPassword(newPassword);

			argendataUserService.changePassword(user);

			mav.addObject("user", user);
			mav.addObject("newPassword", newPassword);

			String subject = this.messages.getMailUserPasswordSubject();
			String content = this.messages.getMailUserPasswordBody();
			mailService.sendMail(email, subject, content, "text/plain");

		} else {
			mav.setViewName("redirect:../main/home");
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView activate(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "key") String key) {
		ModelAndView mav = new ModelAndView();

		if (argendataUserService.activateAccount(username, key)) {
			mav.addObject("msg", messages.getMessageUserAccountActivate());
		} else {
			mav.addObject("msg", messages.getMessageUserAccountNoActivate());
		}

		return mav;
	}
}
