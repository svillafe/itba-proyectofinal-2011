/*package argendata.service.semantic.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.semantic.SolrDao;
import argendata.model.FacetAppResponse;
import argendata.model.FacetCount;
import argendata.model.relational.App;
import argendata.service.relational.AppService;
import argendata.service.semantic.AppSolrService;

@Service
public class AppSolrServiceImpl implements AppSolrService {

	private SolrDao solrDao;
	private AppService appService;
	private final Logger logger = Logger.getLogger(AppSolrServiceImpl.class);

	@Autowired
	public AppSolrServiceImpl(SolrDao solrDao, AppService appService) {

		this.solrDao = solrDao;
		this.appService = appService;
	}

	public FacetAppResponse searchFacetApp(String myTerms,
			List<String> mySortByFields, List<String> myKeywords)
			throws MalformedURLException, SolrServerException {

		List<App> myApps = searchApp(myTerms, mySortByFields, myKeywords);

		List<FacetCount> appsKeywords = searchAppKeywords(myTerms,
				mySortByFields, myKeywords);
		return new FacetAppResponse(myApps, appsKeywords);
	}

	private List<App> searchApp(String myTerms, List<String> mySortByFields,
			List<String> myKeywords) throws MalformedURLException,
			SolrServerException {

		QueryResponse queryRsp = searchQueryApp(myTerms, mySortByFields,
				myKeywords);

		SolrDocumentList docs = queryRsp.getResults();
		List<SolrDocument> list = docs.subList(0, docs.size());
		List<App> resp = new ArrayList<App>();

		for (SolrDocument sd : list) {

			App anApp = appService.getPersistedApp("Semanticapp:"
					+ (String) sd.getFieldValue("titleId"));

			resp.add(anApp);
		}

		return resp;

	}

	private QueryResponse searchQueryApp(String myTerms,
			List<String> mySortByFields, List<String> myKeywords)
			throws MalformedURLException, SolrServerException {

		QueryResponse rsp;

		String appQuery = "type:app AND ((*" + myTerms + "* OR *"
				+ myTerms.toLowerCase() + "* OR *" + myTerms.toUpperCase()
				+ "*)OR (description:*" + myTerms + "* OR description:*"
				+ myTerms.toLowerCase() + "* OR description:*"
				+ myTerms.toUpperCase() + "))";

		List<String> myFacetFields = new ArrayList<String>();
		List<String> myFiltersQuery = new ArrayList<String>();

		myFacetFields.add("appkeyword");

		for (String kw : myKeywords) {
			myFiltersQuery.add("appkeyword:" + kw);
		}

		if (mySortByFields.get(2).equals("a")) {
			rsp = solrDao.simpleFacetQuery(appQuery, 1, null, myFacetFields,
					myFiltersQuery, mySortByFields.get(1), SolrQuery.ORDER.asc);
		} else {
			rsp = solrDao
					.simpleFacetQuery(appQuery, 1, null, myFacetFields,
							myFiltersQuery, mySortByFields.get(1),
							SolrQuery.ORDER.desc);
		}
		return rsp;

	}

	public List<FacetCount> getAllAppKeywords() {

		List<Count> resp = new ArrayList<Count>();

		QueryResponse rsp = null;

		String appQuery = "type:app";

		List<String> myFacetFields = new ArrayList<String>();
		List<String> myFiltersQuery = new ArrayList<String>();

		myFacetFields.add("appkeyword");

		try {
			rsp = solrDao.simpleFacetQuery(appQuery, 1, null, myFacetFields,
					myFiltersQuery, "title", SolrQuery.ORDER.asc);
		} catch (MalformedURLException e) {
			logger.fatal("No se puede conectar a solr");
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}

		if (rsp.getFacetField("appkeyword") != null) {
			List<Count> lc = rsp.getFacetField("appkeyword").getValues();
			if (lc != null)
				resp.addAll(lc);
		}
		return getFacetCount(resp);

	}

	private List<FacetCount> getFacetCount(List<Count> a) {
		List<FacetCount> resp = new ArrayList<FacetCount>();
		for (Count c : a) {
			resp.add(new FacetCount(c.getName(), c.getCount()));

		}
		return resp;
	}

	private List<FacetCount> searchAppKeywords(String myTerms,
			List<String> mySortByFields, List<String> myKeywords)
			throws MalformedURLException, SolrServerException {

		List<Count> response = new ArrayList<Count>();

		QueryResponse queryRsp = searchQueryApp(myTerms, mySortByFields,
				myKeywords);

		if (queryRsp.getFacetField("appkeyword") != null) {
			List<Count> lc = queryRsp.getFacetField("appkeyword").getValues();
			if (lc != null)
				response.addAll(lc);
		}

		return getFacetCount(response);
	}

	@Override
	public Collection<? extends String> getKetwords(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedKeywords(term, "app");
	}

}
*/