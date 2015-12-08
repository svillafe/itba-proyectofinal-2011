package argendata.web.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import argendata.model.relational.App;
import argendata.model.relational.ArgendataUser;
import argendata.service.AppService;
import argendata.service.MailService;
import argendata.service.relational.ArgendataUserService;
import argendata.util.Messages;
import argendata.util.Properties;
import argendata.web.command.ChangePasswordForm;
import argendata.web.command.UserForm;
import argendata.web.validator.ChangePasswordFormValidator;
import argendata.web.validator.UpdateUserValidator;

@Controller
public class UserController {

	private ArgendataUserService argendataUserService;
	private ChangePasswordFormValidator changePasswordFormValidator;
	private UpdateUserValidator updateUserValidator;
	private AppService appService;
	private MailService mailService;
	private Properties properties;
	private Messages messages;
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	public UserController(ArgendataUserService argendataUserService,
			ChangePasswordFormValidator changePasswordFormValidator,
			AppService appService, UpdateUserValidator updateValidator,
			MailService mailService, Properties properties, Messages messages) {
		this.argendataUserService = argendataUserService;
		this.changePasswordFormValidator = changePasswordFormValidator;
		this.appService = appService;
		this.updateUserValidator = updateValidator;
		this.mailService = mailService;
		this.properties = properties;
		this.messages = messages;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView changePassword(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		mav.addObject("changePasswordForm", new ChangePasswordForm());
		mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
		mav.addObject("publicKey", properties.getReCaptchaPublicKey());

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView changePassword(ChangePasswordForm changePasswordForm,
			Errors errors, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		changePasswordFormValidator.validate(changePasswordForm, errors);
		if (errors.hasErrors()) {
			mav.setViewName("user/changePassword");
			mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey", properties.getReCaptchaPublicKey());
			return mav;
		}

		ArgendataUser sessionUser = (ArgendataUser) session
				.getAttribute("user");

		ArgendataUser user = argendataUserService.getUserByUsername(sessionUser
				.getUsername());

		if (user != null) {
			user.setPassword(changePasswordForm.getNewPassword());
			argendataUserService.changePassword(user);
			mav.setViewName("redirect:home?state=4");
		} else {
			mav.setViewName("redirect:home");
		}

		return mav;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView profile(@RequestParam("username") String username,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser actualUser = (ArgendataUser) session.getAttribute("user");
		ArgendataUser user = argendataUserService.getUserByUsername(username);

		if(user==null){
			mav.setViewName("redirect:../main/home");
			return mav;
		}
		
		mav.addObject("user", user);
		mav.addObject("actualUser", actualUser.getUsername());

		MessageDigest m;
		String hash =null;
		String email = user.getEmail().trim(); 
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(email.getBytes(),0,email.length());
			hash = new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			logger.error("No se puede obtener el hash del mail");
		}
	    
		mav.addObject("hash", hash);
		
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editProfile(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		UserForm myUserForm = new UserForm(user);

		mav.addObject("userForm", myUserForm);

		mav.addObject("user", user);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView editProfile(UserForm userForm, Errors errors,
			HttpSession session) {
		boolean resp = false;

		ArgendataUser oldPerson = (ArgendataUser) session.getAttribute("user");
		ModelAndView mav = new ModelAndView("redirect:home");

		updateUserValidator.validate(userForm, errors, oldPerson,
				this.argendataUserService);

		if (errors.hasErrors()) {
			logger.error("Errores en la validacion del formulario");
			mav.setViewName("user/editProfile");
			return mav;
		}

		try {

			oldPerson.setName(userForm.getName());
			oldPerson.setLastName(userForm.getLastName());
			if (!userForm.getEmail().equals(oldPerson.getEmail())) {
				oldPerson.desactivate();
			}
			oldPerson.setEmail(userForm.getEmail());
			oldPerson.setFacebook(userForm.getFacebook());
			oldPerson.setTwitter(userForm.getTwitter());
			oldPerson.setMinibio(userForm.getMinibio());

			argendataUserService.updateUser(oldPerson);
		} catch (Exception e) {
			logger.error("Errores al intentar actualizar usuario en la base de datos");
			logger.error(e.getMessage(), e);
			errors.reject("registration.error");
			return mav;
		}

		if (!oldPerson.isActivated()) {
			String key = generateRandomKey();
			oldPerson.setKey(key);

			String link = properties.getMainURL()
					+ "/public/activate?username=" + oldPerson.getUsername()
					+ "&key=" + key;
			String content = String.format(
					this.messages.getMailUserMailUpdateBody(), link);
			mailService.sendMail(oldPerson.getEmail(),
					this.messages.getMailUserMailUpdateSubject(), content,
					"text/plain");

			session.invalidate();
			mav.setViewName("redirect:../main/home");

		}

		return mav;
	}

	private String generateRandomKey() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listMyApps(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		List<App> userApps = appService.getAppsByUser(user.getId());
		mav.addObject("myApps", userApps);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(
			@RequestParam(value = "state", required = false) String state,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		if (user.isAdmin()) {
			mav.setViewName("redirect:../admin/home?state=" + state);
			return mav;
		}

		if (state != null) {
			int state_i = Integer.valueOf(state);
			String msg = "";
			switch (state_i) {
			case 1: {
				msg += this.messages.getMessageDatasetReceive();
				break;
			}
			case 2: {
				msg += this.messages.getMessageUserGreetings();
				break;
			}
			case 3: {
				msg += this.messages.getMessageAppReceive();
				break;
			}
			case 4: {
				msg += this.messages.getMessagePasswordChange();
				break;
			}
			default: {
				msg += ""; // ????????????????
				break;
			}
			}

			mav.addObject("msg", msg);
		}

		return mav;
	}

}
