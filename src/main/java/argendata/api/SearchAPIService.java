package argendata.api;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import argendata.service.AppService;
import argendata.service.DatasetService;

@Path("/search")
@Component
public class SearchAPIService {

	private DatasetService datasetService;
	private AppService appSolrService;
	private final Logger logger = Logger.getLogger(SearchAPIService.class);

	@Autowired
	public SearchAPIService(DatasetService datasetService,
			AppService appSolrService) {
		this.datasetService = datasetService;
		this.appSolrService = appSolrService;
	}

	@GET
	@Path("/tag/{term}")
	@Produces("application/json")
	public Map<String, List<String>> search(
			@PathParam(value = "term") String term) {
		
		Map<String, List<String>> resp = new HashMap<String, List<String>>();

		Set<String> keywordsSet = new TreeSet<String>();

		try {
			keywordsSet.addAll(this.datasetService
					.getIndexRelatedKeywords(term));
			keywordsSet.addAll(this.appSolrService
					.getIndexRelatedKeywords(term));
		} catch (MalformedURLException e) {
			logger.fatal("No se puede conectar a solr");
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}

		List<String> keywords = new ArrayList<String>(); 
		keywords.addAll(keywordsSet);
		resp.put("availableTags", keywords);
		return resp;
	}

	@GET
	@Path("/publisher/{term}")
	@Produces("application/json")
	public Map<String, List<String>> publishers(@PathParam(value = "term") String term) {
	
		Map<String, List<String>> resp = new HashMap<String, List<String>>();

		List<String> finalPublishers = new ArrayList<String>();

		try {
		
			finalPublishers.addAll(this.datasetService.getIndexRelatedPublishers(term));

		} catch (MalformedURLException e) {
			logger.error("Error al internet conseguir los publisher."
					+ e.getMessage());
			logger.error(e.getStackTrace());
		} catch (SolrServerException e) {
			logger.error("Error al internet conseguir los publisher."
					+ e.getMessage());
			logger.error(e.getStackTrace());
		}


		resp.put("source", finalPublishers);
		return resp;
	}

	@GET
	@Path("/dataset/{term}")
	@Produces("application/json")
	public Map<String, List<String>> searchDatasets(@PathParam(value = "term") String term) {
		
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
	}
}
