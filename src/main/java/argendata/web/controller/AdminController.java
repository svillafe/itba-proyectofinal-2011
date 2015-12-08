package argendata.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import argendata.model.dcat.Dataset;
import argendata.model.relational.App;
import argendata.model.relational.ArgendataUser;
import argendata.model.relational.PreDataset;
import argendata.service.AppService;
import argendata.service.DatasetService;
import argendata.service.MailService;
import argendata.service.relational.ArgendataUserService;
import argendata.util.Messages;
import argendata.util.Parsing;
import argendata.web.command.AppForm;
import argendata.web.command.DatasetForm;
import argendata.web.command.DecisionForm;
import argendata.web.validator.EditAppValidator;
import argendata.web.validator.EditDatasetEntryFormValidator;

@Controller
public class AdminController {

	private ArgendataUserService argendataUserService;
	private DatasetService datasetService;
	private AppService appService;
	private MailService mailService;
	private Messages messages;
	private EditDatasetEntryFormValidator validatorDataEntry;
	private EditAppValidator editAppValidator;
	private static final Logger logger = Logger
			.getLogger(AdminController.class);

	@Autowired
	public AdminController(ArgendataUserService argendataUserService,
			DatasetService datasetService, AppService appService,
			MailService mailService, Messages messages,
			EditDatasetEntryFormValidator val, EditAppValidator valApp) {
		this.argendataUserService = argendataUserService;
		this.datasetService = datasetService;
		this.appService = appService;
		this.mailService = mailService;
		this.messages = messages;
		this.validatorDataEntry = val;
		this.editAppValidator = valApp;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listUsers() {
		ModelAndView mav = new ModelAndView();

		List<ArgendataUser> users;
		try {
			users = argendataUserService.getAllUsers();
		} catch (Exception e) {
			logger.error("No se pudieron obtener los usuarios usando el servicio de usuarios");
			mav.setViewName("redirect:/bin/static/error");
			return mav;
		}

		mav.addObject("users", users);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String makeAdmin(@RequestParam("id") String id) {

		int id_int = Integer.valueOf(id);

		ArgendataUser user = null;
		try {
			user = argendataUserService.getUserById(id_int);
		} catch (Exception e) {
			logger.error("No se puede obtener el servicio");
			logger.error(e.getMessage(), e);
			return "redirect:home";
		}

		user.makeAdmin();

		argendataUserService.updateUser(user);

		return "redirect:listUsers";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String revertAdmin(@RequestParam("id") String id) {

		int id_int = Integer.valueOf(id);

		ArgendataUser user = null;
		try {
			user = argendataUserService.getUserById(id_int);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "redirect:home";
		}

		user.setAdmin(false);

		argendataUserService.updateUser(user);

		return "redirect:listUsers";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView deleteUser(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();

		int id_int = Integer.valueOf(id);

		ArgendataUser user = null;

		try {
			user = argendataUserService.getUserById(id_int);
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			mav.setViewName("redirect:home");
			return mav;
		}

		List<App> apps;

		if (user != null) {
			apps = appService.getAppsByUser(Integer.valueOf(id));
			if (apps.size() == 0) {
				mav.addObject("apps", null);
			} else {
				mav.addObject("apps", apps);
			}
		}

		mav.addObject("id", id);
		mav.addObject("userToDelete", user.getUsername());

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String deleteUserPost(@RequestParam("id") String id) {

		int id_int = Integer.valueOf(id);

		ArgendataUser user = null;

		try {
			user = argendataUserService.getUserById(id_int);
		} catch (Exception e1) {
			logger.error("No se puede obtener el servicio");
			logger.error(e1.getMessage(), e1);
			return "redirect:home";
		}

		try {
			argendataUserService.deleteUser(user);
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "redirect:home";
		}

		return "redirect:listUsers";
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView acceptedDatasets() {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView datasetForApproval() {
		ModelAndView mav = new ModelAndView();

		List<PreDataset> list = null;
		try {
			list = datasetService.getPreDatasetWaitingForApproval();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		mav.addObject("datasets", list);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView appsForApproval() {

		ModelAndView mav = new ModelAndView();

		List<App> list = null;
		try {
			list = appService.getWaitingForApproval();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		mav.addObject("apps", list);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView decisionDataset(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();

		int datasetId = Integer.parseInt(id);

		PreDataset plain = datasetService.getPreDatasetById(datasetId);

		DecisionForm dForm = new DecisionForm(datasetId);

		mav.addObject("dataset", plain);
		mav.addObject("decisionForm", dForm);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	String deleteDataset(@RequestParam("qName") String qName) {

		String resp = "";

		Dataset dataset = this.datasetService.getApprovedDatasetByQName(qName);

		String datasetId = qName.split(":")[1];

		if (dataset == null) {
			logger.error("No existe");
			resp = "-1";
			return resp;
		}

		List<String> appsReferenceName = null;
		appsReferenceName = appService.getRelatedAppName(dataset.getTitle());

		if (appsReferenceName.size() > 0) {
			for (String a : appsReferenceName) {
				resp = resp + a + ", ";
			}
			resp = resp.substring(0, resp.length() - 2);
			resp += ".";

			return resp;
		}

		try {
			datasetService.delete(dataset); // dataset for deletion
		} catch (Exception e) {

			logger.error("No se pudo borrar del store semántico");
			logger.error(e.getMessage(), e);
			resp = "-2";
			return resp;
		}

		resp = "0";

		return resp;
	}



	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView deleteApp(@RequestParam("qName") String qName) {
		ModelAndView mav = new ModelAndView();

		App myApp;
		try {
			myApp = appService.getApprovedAppByQName(qName);
		} catch (java.lang.ClassCastException e) {
			myApp = null;
		}

		if (myApp == null) {
			/* Si no esta en forma semantica, pruebo si esta en la PreApp */
			myApp = appService.getPreAppByQName(qName);

			if (myApp == null) {
				mav.setViewName("redirect:home");
				return mav;
			}
		}

		try {
			appService.approvedAppDelete(myApp);
		} catch (Exception e) {

			logger.error("No se pudo borrar del store semantico");
			logger.error(e.getMessage(), e);

			mav.setViewName("redirect:/bin/static/error");
			return mav;
		}

		mav.setViewName("redirect:../search/searchAppMain");
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView editDataset(DatasetForm editDatasetForm, Errors errors) {
		ModelAndView mav = new ModelAndView();

		Dataset dataset = this.datasetService
				.getApprovedDatasetByName(editDatasetForm
						.getOldTitleIdForEdition());
		validatorDataEntry.validate(editDatasetForm, errors);
		if (errors.hasErrors()) {
			logger.error("Errores en la validacion del formulario");
			mav.addObject(errors);
			return mav;
		}

		if (dataset == null) {
			logger.error("No existe");
			mav.setViewName("redirect:/bin/dataset/error");
			return mav;
		}

		PreDataset aDataset = editDatasetForm.build(); // storable dataset
		Dataset readyDataset = aDataset.toNoRelationalDataset();
		List<String> appsId = appService
				.getRelatedAppName(dataset.getTitleId());
		try {
			datasetService.update(dataset, readyDataset);
		} catch (Exception e) {
			logger.error("No se actualizar el dataset", e);
			mav.setViewName("redirect:/bin/static/error");
			return mav;
		}

		// We have to update the reference of the apps
		updateAppReferenced(appsId, dataset.getTitle(), readyDataset);

		mav.setViewName("redirect:../dataset/view?qName=Dataset:"
				+ readyDataset.getTitleId());
		return mav;
	}

	private void updateAppReferenced(List<String> appsId, String oldTitle,
			Dataset newDataset) {

		for (String aid : appsId) {
			App app = this.appService.getApprovedAppByName(aid);
			App oldApp = this.appService.getApprovedAppByName(aid);
			Set<String> datasets = app.getDataset();
			datasets.remove(oldTitle);
			datasets.add(newDataset.getTitle());
			app.setDataset(datasets);
			try {
				appService.approvedAppUpdate(oldApp, app);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView editPlainDataset(DatasetForm editDatasetForm) {
		ModelAndView mav = new ModelAndView();

		PreDataset pd = datasetService.getPreDatasetById(editDatasetForm
				.getId());

		PreDataset fpd = editDatasetForm.build();

		pd.setAccessURL(fpd.getAccessURL());
		pd.setApproved(fpd.isApproved());
		pd.setDataQuality(fpd.getDataQuality());
		pd.setDecision(fpd.getDecision());
		pd.setDistribution(fpd.getDistribution());
		pd.setFormat(fpd.getFormat());
		pd.setKeyword(fpd.getKeyword());
		pd.setLicense(fpd.getLicense());
		pd.setModified(fpd.getModified());
		pd.setPublisher(fpd.getPublisher());
		pd.setSize(fpd.getSize());
		pd.setSpatial(fpd.getSpatial());
		pd.setTemporal(fpd.getTemporal());
		pd.setTheme(fpd.getTheme());
		pd.setTitle(fpd.getTitle());
		pd.setUsername(fpd.getUsername());
		pd.setTitleId(fpd.getTitleId());

		datasetService.update(pd);

		mav.setViewName("redirect:datasetForApproval");

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editDataset(@RequestParam("qName") String qName) {
		ModelAndView mav = new ModelAndView();

		Dataset dataset = this.datasetService.getApprovedDatasetByQName(qName);
		if (dataset == null) {
			mav.setViewName("redirect:home");
			return mav;
		}

		DatasetForm datasetForm = new DatasetForm(dataset);

		mav.addObject(datasetForm);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editPlainDataset(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();

		int iid = Integer.valueOf(id);

		PreDataset dataset = datasetService.getPreDatasetById(iid);

		if (dataset == null) {
			mav.setViewName("redirect:home");
			return mav;
		}

		DatasetForm datasetForm = new DatasetForm(dataset);

		mav.addObject("editDatasetForm", datasetForm);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String decisionDataset(final DecisionForm dForm) {
		int id = dForm.getId();

		final PreDataset plain = datasetService.getPreDatasetById(id);
		Dataset dataset = plain.toNoRelationalDataset();
		ArgendataUser user = argendataUserService.getUserByUsername(plain
				.getUsername());

		try {
			if (dForm.isApproved()) {
				datasetService.store(dataset);

				user.incDatasetsQty();

				mailService.sendMail(user.getEmail(), messages
						.getMailDatasetAcceptSubject(), String.format(
						messages.getMailDatasetAcceptBody(), plain.getTitle()),
						"text/plain");

			} else {
				mailService.sendMail(user.getEmail(), messages
						.getMailDatasetRejectSubject(), String.format(
						messages.getMailDatasetRejectBody(), plain.getTitle(),
						dForm.getDecision()), "text/plain");

			}

		} catch (Exception e) {
			logger.error("No se pudo agregar el dataset al store", e);
			return "redirect:/bin/static/error";
		}

		// actualizo
		// plain.setApproved(dForm.isApproved());
		// plain.setDecision(dForm.getDecision());
		// plainDatasetService.updateDataset(plain);

		datasetService.delete(plain);
		logger.info("Borro dataset de la base relacional");
		return "redirect:datasetForApproval";

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView decisionApp(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();

		int appId = Integer.parseInt(id);

		App preApp = appService.getPreAppById(appId);

		DecisionForm dForm = new DecisionForm(appId);

		mav.addObject("app", preApp);
		mav.addObject("decisionForm", dForm);

		List<Dataset> datasets = new ArrayList<Dataset>();

		for (String dataset : preApp.getDataset()) {
			datasets.add(datasetService.getApprovedDatasetByName(Parsing
					.withoutSpecialCharacters(dataset)));
		}

		mav.addObject("datasets", datasets);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String decisionApp(final DecisionForm dForm) {
		int id = dForm.getId();

		App preApp = this.appService.getPreAppById(id);
		final String addressTo = preApp.getPublisher().getEmail();

		final String nameApp = preApp.getName();
		if (dForm.isApproved()) {
			preApp.setIsAllowed(true);
			try {
				appService.approvedAppStore(preApp);
			} catch (Exception e) {
				logger.error("No se pudo guardar la app", e);
				return "redirect:appsForApproval";
			}

			ArgendataUser user = argendataUserService.getUserByUsername(preApp
					.getPublisher().getUsername());

			user.incAppsQty();

			mailService.sendMail(addressTo, this.messages
					.getMailAppAcceptSubject(), String.format(
					this.messages.getMailAppAcceptBody(), nameApp),
					"text/plain");

			appService.preAppDelete(preApp);
		} else {
			mailService.sendMail(addressTo, this.messages
					.getMailAppRejectSubject(), String.format(
					this.messages.getMailAppRejectBody(), nameApp,
					dForm.getDecision()), "text/plain");

			appService.preAppDelete(preApp);
		}

		return "redirect:appsForApproval";

	}

	// @RequestMapping(method = RequestMethod.GET)
	// public ModelAndView home() {
	// ModelAndView mav = new ModelAndView();
	//
	// return mav;
	// }

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home(
			@RequestParam(value = "state", required = false) String state) {
		ModelAndView mav = new ModelAndView();

		if (state != null && state.equals("null") == false) {
			int state_i = Integer.valueOf(state);
			String msg = "";
			switch (state_i) {
			case 1: {
				msg += this.messages.getMessageDatasetReceive();
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

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView editApp(@RequestParam("qName") String qName,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		App myApp;
		try {
			myApp = appService.getApprovedAppByQName(qName);
		} catch (java.lang.ClassCastException e) {
			myApp = null;
		}

		if (myApp == null) {
			/* Si no esta en forma semantica, pruebo si esta en la PreApp */
			myApp = appService.getPreAppByQName(qName);

			if (myApp == null) {
				mav.setViewName("redirect:home");
				return mav;
			}
		}

		AppForm myAppForm = new AppForm(myApp);

		myAppForm.setUsername(((ArgendataUser) session.getAttribute("user"))
				.getUsername());
		mav.addObject(myAppForm);
		mav.addObject("app", myApp);

		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView editApp(AppForm editAppForm, Errors err) {
		ModelAndView mav = new ModelAndView();

		if (!editAppForm.getIsAllowed()) {

			App oldApp = appService.getPreAppByNameId(editAppForm
					.getOldTitleIdForEdition());

			App newApp = null;
			try {
				newApp = editAppForm.build(this.argendataUserService,
						this.datasetService, err);
			} catch (IOException e) {
				logger.error("Error utilizando el servicio de usuarios", e);
			}
			oldApp.setName(newApp.getName());
			oldApp.setDescription(newApp.getDescription());
			oldApp.setUrl(newApp.getUrl());
			oldApp.setNameId(newApp.getNameId());
			if (editAppForm.hayLogo()) {
				oldApp.setIcon(newApp.getIcon());
			}
			if (editAppForm.hayScreen()) {
				oldApp.setScreenshot(newApp.getScreenshot());
			}

			appService.preAppUpdate(oldApp);

			mav.setViewName("redirect:appsForApproval");

		} else {

			App retrivedApp = null;
			try {
				retrivedApp = appService.getApprovedAppByQName("Semanticapp:"
						+ editAppForm.getOldTitleIdForEdition());
			} catch (Exception e1) {
				logger.error("No existe");
				mav.setViewName("redirect:/bin/static/error");
				return mav;
			}

			App myApp = null;
			editAppValidator.validate(editAppForm, err);
			if (err.hasErrors()) {
				logger.error("Errores en la validacion del formulario");
				mav.addObject("app", retrivedApp);
				return mav;
			}

			try {

				myApp = editAppForm.build(this.argendataUserService,
						this.datasetService, err);

			} catch (IOException e1) {
				logger.error(e1.getMessage(), e1);
			} // storable app
			if (!editAppForm.hayLogo()) {
				myApp.setIcon(retrivedApp.getIcon());
			}

			if (!editAppForm.hayScreen()) {
				myApp.setScreenshot(retrivedApp.getScreenshot());
			}

			try {

				appService.approvedAppUpdate(retrivedApp, myApp);

				/*
				 * appService.approvedAppDelete(retrivedApp); // app for
				 * deletion
				 */

			} catch (Exception e) {
				logger.error("No se pudo borrar la app", e);
				mav.setViewName("redirect:/bin/static/error");
				return mav;
			}

			/*
			 * try { appService.approvedAppStore(myApp); } catch (Exception e) {
			 * logger.error("No se pudo borrar la app", e);
			 * mav.setViewName("redirect:/bin/static/error"); return mav; }
			 */

			mav.setViewName("redirect:../apps/detail?qName=Semanticapp:"
					+ myApp.getNameId());

		}
		return mav;
	}
}
