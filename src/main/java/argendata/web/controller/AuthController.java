package argendata.web.controller;

import javax.mail.PasswordAuthentication;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import argendata.model.relational.ArgendataUser;
import argendata.service.MailService;
import argendata.service.relational.ArgendataUserService;
import argendata.util.Messages;
import argendata.util.Properties;
import argendata.web.command.ForgetPasswordForm;
import argendata.web.validator.UserRegisterFormValidator;

@Controller
public class AuthController {

	private UserRegisterFormValidator registerFormValidator;
	private ArgendataUserService argendataUserService;
	private MailService mailService;
	private final static Logger logger = Logger.getLogger(AuthController.class);
	private Properties properties;
	private Messages messages;

	@Autowired
	public AuthController(ArgendataUserService argendataUserService,
			UserRegisterFormValidator registerFormValidator,
			MailService mailService, Messages messages, Properties properties) {
		this.registerFormValidator = registerFormValidator;
		this.argendataUserService = argendataUserService;
		this.mailService = mailService;
		this.messages = messages;
		this.properties = properties;
	}

	// @RequestMapping(method = RequestMethod.GET)
	// public ModelAndView login() {
	// ModelAndView mav = new ModelAndView();
	// mav.addObject("userLoginForm", new UserLoginForm());
	// return mav;
	// }

	@RequestMapping(method = RequestMethod.POST)
	public String forget(
			@ModelAttribute("forgetPasswordForm") ForgetPasswordForm forgetPasswordForm,
			Errors errors, HttpSession session) {

		final String email = forgetPasswordForm.getEmail();

		final String key;

		try {
			key = argendataUserService.forgetPassword(email);
		} catch (Exception e) {
			logger.error("No se pudo conectar al servicio de usuarios para conseguir la clave");
			return "redirect:/bin/static/error";
		}

		if (key != null) {
			String subject = this.messages.getMailUserChangePasswordSubject();
			String link = this.properties.getMainURL()
					+ "/public/forget?email=" + email + "&key=" + key;
			String content = String.format(
					this.messages.getMailUserChangePasswordBody(), link);
			String typeContent = "text/plain";
			mailService.sendMail(email, subject, content, typeContent);

		} else {
			// no se hace el reject erros sino estariamos dando info de la db de
			// usuarios
			return "redirect:../main/home";
		}
		return "redirect:../main/home";

	}

	private class SMTPAuthenticator extends javax.mail.Authenticator {
		public PasswordAuthentication getPasswordAuthentication() {
			String username = "argendata@velveteyes.com.ar";
			String password = "pf2011";
			return new PasswordAuthentication(username, password);
		}
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public @ResponseBody
	String login(@RequestParam("username") String username,
			@RequestParam("password") String password, HttpSession session) {

		ArgendataUser user = null;

		try {
			user = argendataUserService.authenticate(username, password);
		} catch (Exception e) {
			logger.error("No se pudo conectar al servicio de usuarios");
			return "-1"; // ko
		}

		if (user != null) {
			if (user.isActivated()) {
				session.setAttribute("user", user);
			} else {
				return "-2"; // no activado
			}
		} else {
			return "-3"; // incorrectos
		}
		return user.getUsername(); // username

	}

	/*
	 * @RequestMapping(method = RequestMethod.POST) public String login(
	 * 
	 * @ModelAttribute("signInForm") UserLoginForm loginForm, Errors errors,
	 * HttpSession session) {
	 * 
	 * String username = loginForm.getUsername(); String password =
	 * loginForm.getPassword();
	 * 
	 * ArgendataUser user = null;
	 * 
	 * try { user = argendataUserService.authenticate(username, password); }
	 * catch (Exception e) { e.printStackTrace(); return
	 * "redirect:/bin/static/error"; }
	 * 
	 * if (user != null) { //user.setLastLogin(new DateTime());
	 * if(user.isActivated()){ session.setAttribute("user", user); } else{
	 * errors.reject("Cuenta no activada"); return "redirect:../main/home"; } }
	 * else { errors.reject("fail.username"); return "redirect:../main/home"; }
	 * return "redirect:../user/home";
	 * 
	 * }
	 */

	@RequestMapping(method = RequestMethod.POST)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:../main/home";
	}

}
