package argendata.web.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import argendata.model.ListDatasetAppViewDTO;
import argendata.model.relational.App;
import argendata.model.relational.ArgendataUser;
import argendata.service.AppService;
import argendata.service.DatasetService;
import argendata.service.relational.ArgendataUserService;
import argendata.util.Parsing;
import argendata.util.Properties;
import argendata.web.command.AppForm;
import argendata.web.validator.AddAppValidator;

@Controller
public class AppsController implements HandlerExceptionResolver {

	private AddAppValidator appValidator;
	private AppService appService;
	private ArgendataUserService userService;
	private DatasetService datasetService;
	private Properties properties;
	private final static Logger logger = Logger.getLogger(AppsController.class);

	@Autowired
	public AppsController(AddAppValidator appValidator,
			ArgendataUserService userService, AppService appService,
			DatasetService datasetService, Properties properties) {

		this.appValidator = appValidator;
		this.appService = appService;
		this.userService = userService;
		this.datasetService = datasetService;
		this.properties = properties;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView mav = new ModelAndView();

		mav.addObject("apps", appService.getAllApprovedApps());

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView add(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		AppForm aForm = new AppForm();

		mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
		mav.addObject("publicKey", properties.getReCaptchaPublicKey());
		aForm.setUsername(user.getUsername());

		mav.addObject(aForm);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest request, HttpSession session,
			AppForm appForm, Errors err) {

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		App myApp;

		appValidator.validate(appForm, err);

		try {
			myApp = appForm.build(userService, datasetService, err);

			if (err.hasErrors()) {
				logger.error("Errores en la validacion del formulario");
				mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
				mav.addObject("publicKey", properties.getReCaptchaPublicKey());
				mav.setViewName("apps/add");
				return mav;
			}

			if (!user.isAdmin()) {
				appService.preAppStore(myApp);
				mav.setViewName("redirect:../user/listMyApps");
			} else {
				myApp.setIsAllowed(true);
				appService.approvedAppStore(myApp);
				user.incAppsQty();
				mav.setViewName("redirect:../search/searchAppMain");
			}

		} catch (Exception e) {
			logger.error("Errores al intentar dal de alta una aplicacion en la base de datos.");
			logger.error(e.getMessage(), e);
			return mav;
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam String qName, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainURL", properties.getMainURL());

		App retrievedApp = appService.getApprovedAppByQName(qName);
		mav.addObject(retrievedApp);

		Set<String> datasets = retrievedApp.getDataset();
		List<ListDatasetAppViewDTO> pushDataset = new ArrayList<ListDatasetAppViewDTO>();

		for (String d : datasets) {
			ListDatasetAppViewDTO ej = new ListDatasetAppViewDTO(d,
					Parsing.withoutSpecialCharacters(d));
			pushDataset.add(ej);
		}
		mav.addObject("datasets", pushDataset);

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");
		if (user != null && user.isAdmin()) {
			mav.addObject("admin", true);
		} else {
			mav.addObject("admin", false);
		}

		if (user != null) {
			mav.addObject("logged", true);
		} else {
			mav.addObject("logged", false);
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void iconOfApp(HttpServletResponse resp,
			@RequestParam(value = "idApp") App myApp) {
		resp.setContentType("image");
		ServletOutputStream out = null;

		// App myApp = appService.getApp(myAppNum);

		try {
			out = resp.getOutputStream();
			out.write(myApp.getIcon().getData());
		} catch (Exception e) {
			FileInputStream inStream = null;
			try {
				inStream = new FileInputStream(
						"./src/main/webapp/images/app.png");
				int c;
				while ((c = inStream.read()) != -1) {
					out.write(c);
				}

			} catch (Exception p) {
				logger.error(e.getMessage(), e);
				logger.error(p.getMessage(), p);
				try {
					inStream = new FileInputStream(
							this.properties.getProjectName()
									+ "/images/app.png");
					int c;
					while ((c = inStream.read()) != -1) {
						out.write(c);
					}
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}

			}
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public void screenshotOfApp(HttpServletResponse resp,
			@RequestParam(value = "idApp") App myApp) {
		resp.setContentType("image");
		ServletOutputStream out = null;

		try {
			out = resp.getOutputStream();
			out.write(myApp.getScreenshot().getData());
		} catch (Exception e) {
			FileInputStream inStream = null;
			try {
				inStream = new FileInputStream(
						"./src/main/webapp/images/noScreenshot.png");
				int c;
				while ((c = inStream.read()) != -1) {
					out.write(c);
				}

			} catch (Exception p) {
				logger.error(e.getMessage(), e);
				logger.error(p.getMessage(), p);
				try {
					inStream = new FileInputStream(
							this.properties.getProjectName()
									+ "/images/noScreenshot.png");
					int c;
					while ((c = inStream.read()) != -1) {
						out.write(c);
					}
				} catch (Exception e1) {
					logger.error(e1.getMessage(), e1);
				}

			}
		}
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) request.getSession().getAttribute(
				"user");

		if (ex instanceof MaxUploadSizeExceededException) {
			mav.addObject("errorMaxUp",
					"Las imagenes pueden ser como maximo de 200KB");

			AppForm ap = new AppForm();
			ap.setUsername(user.getUsername());
			mav.addObject(ap);
			mav.setViewName("apps/add");
		} else {
			mav.addObject("errors", "Unexpected error: " + ex.getMessage());
			mav.setViewName("static/error");
		}

		return mav;
	}

}
