package argendata.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import argendata.model.dcat.Dataset;
import argendata.model.relational.App;
import argendata.service.AppService;
import argendata.service.DatasetService;
import argendata.util.Properties;

@Controller
public class ExternalController {

	private DatasetService datasetService;
	private AppService appServicie;
	private Properties properties;
	private static final Logger logger = Logger
	.getLogger(DatasetController.class);

	@Autowired
	public ExternalController(AppService appService, Properties properties,
			DatasetService datasetService) {
		this.datasetService = datasetService;
		this.appServicie = appService;
		this.properties = properties;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView link(@RequestParam String qName) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainURL", properties.getMainURL());

		String[] input = qName.split(":");

		if (input[0].equals("Dataset")) {
			Dataset retrievedDataset = datasetService.getApprovedDatasetByQName(qName);

			if (retrievedDataset == null) {
				logger.info("El dataset no existe");
				mav.setViewName("redirect:/bin/dataset/error");
				return mav;
			}
			
			
			mav.addObject("url", retrievedDataset.getDistribution()
					.getAccessURL());
			mav.addObject("socialUrl",
					properties.getMainURL() + "/dataset/view?qName=Dataset:"
							+ retrievedDataset.getTitleId());
		} else {
			App retrievedApp = appServicie.getApprovedAppByQName(qName);
			
			mav.addObject("url", retrievedApp.getUrl());
			mav.addObject("socialUrl",
					properties.getMainURL() + "/apps/detail?qName=Semanticapp:"
							+ retrievedApp.getNameId());
		}

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView linkWP(@RequestParam String webPage) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url", webPage);
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doc(@RequestParam String qName) {
		ModelAndView mav = new ModelAndView();
		Dataset retrievedDataset = this.datasetService.getApprovedDatasetByQName(qName);

		if (retrievedDataset == null) {
			logger.info("El dataset no existe");
			mav.setViewName("redirect:/bin/dataset/error");
			return mav;
		}
		mav.addObject("publisher", retrievedDataset.getPublisherName());
		mav.addObject("url", retrievedDataset.getDistribution().getAccessURL());
		mav.addObject("socialUrl", properties.getMainURL()
				+ "/dataset/view?qName=Dataset:" + retrievedDataset.getTitleId());

		return mav;
	}
}
