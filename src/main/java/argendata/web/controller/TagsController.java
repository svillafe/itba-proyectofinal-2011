package argendata.web.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import argendata.service.AppService;
import argendata.service.DatasetService;

@Controller
public class TagsController {
	private DatasetService datasetService;
	private AppService appSolrService;
	private final Logger logger = Logger.getLogger(TagsController.class);
	
	@Autowired
	public TagsController(DatasetService datasetSolrService,
			AppService appSolrService) {
		this.datasetService = datasetSolrService;
		this.appSolrService = appSolrService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView listAll() {
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	/*@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Map<String, List<String>> search(@RequestParam String term) {
		Map<String, List<String>> resp = new HashMap<String, List<String>>();

		List<String> finalKeywords = new ArrayList<String>();

		try {
			finalKeywords.addAll(this.datasetService.getIndexRelatedKeywords(term));
			finalKeywords.addAll(this.appSolrService.getIndexRelatedKeywords(term));
		} catch (MalformedURLException e) {
			logger.fatal("No se puede conectar a solr");
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}

		resp.put("availableTags", finalKeywords);
		return resp;
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	Map<String, List<String>> searchDatasets(@RequestParam String term) {
		Map<String, List<String>> resp = new HashMap<String, List<String>>();

		List<String> finalTitles = new ArrayList<String>();

		try {
			finalTitles.addAll(this.datasetService.getIndexRelatedTitles(term));
		} catch (MalformedURLException e) {
			logger.fatal("No se puede conectar a solr");
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}
		
		
		resp.put("availableTags", finalTitles);
		return resp;
	}*/

}
