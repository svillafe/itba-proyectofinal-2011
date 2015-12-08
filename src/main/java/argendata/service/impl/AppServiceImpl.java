package argendata.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.relational.AppDao;
import argendata.dao.relational.RelationalAppDao;
import argendata.dao.semantic.DatasetDao;
import argendata.dao.semantic.SemanticAppDao;
import argendata.dao.semantic.SolrDao;
import argendata.exceptions.IndexStoreException;
import argendata.exceptions.SemanticStoreException;
import argendata.model.FacetAppResponse;
import argendata.model.FacetCount;
import argendata.model.argendata.Semanticapp;
import argendata.model.dcat.Dataset;
import argendata.model.relational.App;
import argendata.model.relational.RelationalApp;
import argendata.service.AppService;
import argendata.util.Parsing;
import argendata.util.Properties;

@Service
public class AppServiceImpl implements AppService {

	private SemanticAppDao semanticAppDao;
	private RelationalAppDao relationalAppDao;
	private AppDao preAppDao;
	private DatasetDao datasetDao;
	private SolrDao solrDao;
	private Properties properties;
	private final Logger logger = Logger.getLogger(AppServiceImpl.class);

	@Autowired
	public AppServiceImpl(SemanticAppDao semanticAppDao, RelationalAppDao appDao,
			AppDao preAppDao, DatasetDao datasetDao, SolrDao solrDao,
			Properties properties) {

		this.relationalAppDao = appDao;
		this.preAppDao = preAppDao;
		this.semanticAppDao = semanticAppDao;
		this.datasetDao = datasetDao;
		this.solrDao = solrDao;
		this.properties = properties;
	}

	@Override
	public List<App> getAllApprovedApps() {
		List<App> resp = new ArrayList<App>();
		Iterable<Semanticapp> mySemanticApps = semanticAppDao.getAll();
		Iterator<Semanticapp> mySemanticAppIterator = mySemanticApps.iterator();
		while (mySemanticAppIterator.hasNext()) {
			Semanticapp aux1 = mySemanticAppIterator.next();
			RelationalApp aux2 = this.relationalAppDao.getRelationalApp("Semanticapp:"
					+ aux1.getName());
			if (aux1 != null && aux2 != null) {
				resp.add(new App(aux1, aux2));
			}
		}

		return resp;
	}

	@Override
	public App getApprovedAppByQName(String qName) {
		QName QN = new QName(properties.getNamespace(), qName);
		Semanticapp mySemanticApp = this.semanticAppDao.getById(QN);
		RelationalApp myRelationalApp = this.relationalAppDao.getRelationalApp(qName);

		return new App(mySemanticApp, myRelationalApp);
	}

	@Override
	public App approvedAppStore(App myApp) throws SemanticStoreException, IndexStoreException  {
		this.relationalAppstore(myApp.getRelationalApp());
		this.semanticAppStore(myApp.getSemanticApp());
		return myApp;
	}

	
	@Override
	public void approvedAppDelete(App obj) throws IndexStoreException  {
		this.solrDao.delete(obj);

		this.relationalAppDao.delete(this.relationalAppDao.getRelationalApp("Semanticapp:"
				+ obj.getNameId()));

		QName QN = new QName(properties.getNamespace(), "Semanticapp:"
				+ obj.getNameId());
		Semanticapp mySemanticApp = this.semanticAppDao.getById(QN);

		this.semanticAppDao.delete(mySemanticApp);

	}

	@Override
	public void preAppStore(App myApp) {
		this.preAppDao.store(myApp);

	}

	@Override
	public List<App> getWaitingForApproval() {
		List<App> response = new ArrayList<App>();
		response.addAll(this.preAppDao.getNotAllowed());
		return response;

	}

	@Override
	public App getPreAppById(int appId) {
		return this.preAppDao.getById(appId);
	}

	@Override
	public void preAppDelete(App preApp) {
		this.preAppDao.delete(preApp);

	}

	@Override
	public void preAppUpdate(App preApp) {
		this.preAppDao.store(preApp);

	}

	@Override
	public List<App> getAppsByUser(Integer id) {
		List<App> resp = new ArrayList<App>();

		List<RelationalApp> relationalAppList = this.relationalAppDao.getAppsByUser(id);

		for (RelationalApp p : relationalAppList) {
			resp.add(this.getApprovedAppByQName(p.getqName()));
		}

		resp.addAll(this.preAppDao.getAppsByUser(id));

		return resp;
	}

	@Override
	public App getPreAppByQName(String qName) {
		return this.preAppDao.getByQname(qName);
	}
	
	@Override
	public App getApprovedAppByName(String name) {
		return this.getApprovedAppByQName("Semanticapp:"+Parsing.withoutSpecialCharacters(name));
	}

	@Override
	public App getPreAppByNameId(String name) {
		return this.preAppDao.getByNameID(name);
	}

	@Override
	public boolean exist(String name) {
		String nameId = Parsing.withoutSpecialCharacters(name);
		if (!this.semanticAppDao.exist(nameId) && !this.preAppDao.exist(nameId)) {
			return false;
		}
		return true;
	}

	@Override
	public List<Semanticapp> getSomeApprovedApps() {

		return this.semanticAppDao.getSome();
	}

	@Override
	public int getApprovedAppQuantity() {

		return this.semanticAppDao.getQty();
	}

	@Override
	public FacetAppResponse searchOnIndexFacetApp(String myTerms,
			List<String> mySortByFields, List<String> myKeywords)
			throws MalformedURLException, SolrServerException {

		List<App> myApps = searchApp(myTerms, mySortByFields, myKeywords);

		List<FacetCount> appsKeywords = searchAppKeywords(myTerms,
				mySortByFields, myKeywords);
		return new FacetAppResponse(myApps, appsKeywords);
	}
	
	@Override
	public List<FacetCount> getIndexAllAppKeywords() {
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

	@Override
	public Collection<? extends String> getIndexRelatedKeywords(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedKeywords(term, "app");
	}
	

	@Override
	public void approvedAppUpdate(App oldApp, App newApp) throws SemanticStoreException, IndexStoreException  {
		this.approvedAppDelete(oldApp); // app for deletion*/
		this.approvedAppStore(newApp);
	}
	
	@Override
	public List<String> getRelatedAppId(String datasetId){
		List<String> resp = new ArrayList<String>();
		List<Semanticapp> relatedSemanticApp = this.semanticAppDao.getRelatedApp(datasetId);
		for(Semanticapp a : relatedSemanticApp){
			resp.add(a.getNameId());			
		}
		return resp;
	}
	
	@Override
	public List<String> getRelatedAppName(String title) {
		List<String> resp = new ArrayList<String>();
		List<Semanticapp> relatedSemanticApp = this.semanticAppDao.getRelatedApp(Parsing.withoutSpecialCharacters(title));
		for(Semanticapp a : relatedSemanticApp){
			resp.add(a.getName());			
		}
		
		List<App> relatedPreApp = this.preAppDao.getRelatedApp(title);
		for(App a : relatedPreApp){
			resp.add(a.getName());			
		}
		
		return resp;
	}

	
	
	/* The private methods start here*/
	private RelationalApp relationalAppstore(RelationalApp myApp) {
		return relationalAppDao.store(myApp);
	}

	private Semanticapp semanticAppStore(Semanticapp obj) throws SemanticStoreException, IndexStoreException {

		Semanticapp semanticAppStore = this.semanticAppDao.newSemanticApp(obj
				.getNameId());

		semanticAppStore.setDescription(obj.getDescription());
		semanticAppStore.setPoints(obj.getPoints());
		semanticAppStore.setUrl(obj.getUrl());
		semanticAppStore.setName(obj.getName());
		semanticAppStore.setAppkeyword(obj.getAppkeyword());
		semanticAppStore.setNameId(obj.getNameId());
		Set<Dataset> pushMyDataset = new TreeSet<Dataset>();
		for (Dataset t : obj.getDataset()) {
			Dataset d = this.datasetDao.newDataset(t.getTitleId());
			QName QN = new QName(properties.getNamespace(), "Dataset:"
					+ t.getTitleId());
			Dataset old = (Dataset) (this.datasetDao.getById(QN));

			d.setAccessURL(old.getAccessURL());
			d.setDataQuality(old.getDataQuality());
			d.setDistribution(old.getDistribution());
			d.setFormat(old.getFormat());
			d.setKeyword(old.getKeyword());
			d.setLicense(old.getLicense());
			d.setModified(old.getModified());
			d.setPublisher(old.getPublisher());
			d.setSize(old.getSize());
			d.setSpatial(old.getSpatial());
			d.setTemporal(old.getTemporal());

			pushMyDataset.add(d);
		}
		semanticAppStore.setDataset(pushMyDataset);
		
		Semanticapp resp = null;
		
		try{
			this.semanticAppDao.store(semanticAppStore);
		} catch(Exception e){
			throw new SemanticStoreException();
		}
		
		try{
			this.solrDao.store(obj);
		}catch(Exception e){
			this.semanticAppDao.delete(semanticAppStore);
			throw new IndexStoreException();
		}
		
		return resp;
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

			App anApp = this.getApprovedAppByQName("Semanticapp:"
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

	private List<FacetCount> getFacetCount(List<Count> a) {
		List<FacetCount> resp = new ArrayList<FacetCount>();
		for (Count c : a) {
			resp.add(new FacetCount(c.getName(), c.getCount()));

		}
		return resp;
	}

	
	
	/*
	@Override
	public List<App> getAllowedApps() {
		List<App> resp = new ArrayList<App>();
		Iterator<RelationalApp> allowedAppsIterator = this.relationalAppDao
				.getAllowedApps().iterator();
		while (allowedAppsIterator.hasNext()) {
			RelationalApp aux1 = allowedAppsIterator.next();
			QName qName = new QName(properties.getNamespace(), aux1.getqName());

			Semanticapp aux2 = this.semanticAppDao.getById(qName);

			resp.add(new App(aux2, aux1));
		}

		return resp;
	}*/


}
