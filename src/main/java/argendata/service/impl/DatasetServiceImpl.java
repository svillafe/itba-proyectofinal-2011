package argendata.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.relational.PreDatasetDao;
import argendata.dao.semantic.DatasetDao;
import argendata.dao.semantic.DistributionDao;
import argendata.dao.semantic.OrganizationDao;
import argendata.dao.semantic.SolrDao;
import argendata.exceptions.IndexStoreException;
import argendata.exceptions.SemanticStoreException;
import argendata.model.FacetCount;
import argendata.model.FacetDatasetResponse;
import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.foaf.Organization;
import argendata.model.relational.PreDataset;
import argendata.service.DatasetService;
import argendata.util.Parsing;
import argendata.util.Properties;

@Service
public class DatasetServiceImpl implements DatasetService {

	private DatasetDao datasetDao;
	private OrganizationDao organizationDao;
	private DistributionDao distributionDao;
	private SolrDao solrDao;
	private Properties properties;
	private PreDatasetDao preDatasetDao;
	private final Logger logger = Logger.getLogger(DatasetServiceImpl.class);

	@Autowired
	public DatasetServiceImpl(DatasetDao datasetDao,
			OrganizationDao organizationDao, DistributionDao distributionDao,
			SolrDao solrDao, Properties properties,
			PreDatasetDao preDatasetDao) {
		super();
		this.datasetDao = datasetDao;
		this.organizationDao = organizationDao;
		this.distributionDao = distributionDao;
		this.solrDao = solrDao;
		this.properties = properties;
		this.preDatasetDao = preDatasetDao;
	}

	@Override
	public Dataset store(Dataset obj) throws SemanticStoreException, IndexStoreException  {
		String publisherTitleId = obj.getPublisher().getNameId();
		Organization myPublisher = this.organizationDao
				.newOrganization(publisherTitleId);
		myPublisher.setName(obj.getPublisher().getName());

		Distribution myDistribution;

		if (obj.getDistribution() instanceof Feed) {
			myDistribution = this.distributionDao.newFeed(obj.getDistribution()
					.getAccessURL());
		} else if (obj.getDistribution() instanceof Download) {
			myDistribution = this.distributionDao.newDownload(obj
					.getDistribution().getAccessURL());
		} else {
			myDistribution = this.distributionDao.newWebService(obj
					.getDistribution().getAccessURL());
		}

		myDistribution.setAccessURL(obj.getDistribution().getAccessURL());
		myDistribution.setFormat(obj.getDistribution().getFormat());
		myDistribution.setSize(obj.getDistribution().getSize());

		Dataset ds = this.datasetDao.newDataset(obj.getTitleId());
		ds.setDataQuality(obj.getDataQuality());
		ds.setDistribution(myDistribution);

		ds.setLocation(obj.getLocation());

		Set<String> set = new TreeSet<String>();

		for (String s : obj.getKeyword()) {
			set.add(s.toLowerCase());
		}

		ds.setKeyword(set);

		ds.setLicense(obj.getLicense());
		ds.setModified(obj.getModified());
		ds.setPublisher(myPublisher);
		ds.setSpatial(obj.getSpatial());
		ds.setTemporal(obj.getTemporal());
		ds.setTitle(obj.getTitle());
		ds.setDescription(obj.getDescription());
		ds.setTitleId(obj.getTitleId());

		Dataset resp = null;
		try{
			resp = this.datasetDao.store(ds);
		}
		catch(Exception e){
			throw new SemanticStoreException(); 
		}
		
		try{
			this.solrDao.store(ds);
		}
		catch(Exception e1){
			this.datasetDao.delete(ds);
			throw new IndexStoreException();
		}
		
		return resp;
	}
	
	@Override
	public boolean store(PreDataset dataset) {
		return this.preDatasetDao.add(dataset);
	}

	@Override
	public void delete(Dataset obj) throws SemanticStoreException, IndexStoreException {
		try{
			this.solrDao.delete(obj);
		}catch(Exception e){
			try{
				this.datasetDao.delete(obj);
			}catch(Exception e1){
				throw new SemanticStoreException();
			}
			throw new IndexStoreException();
		}

		try{
			this.datasetDao.delete(obj);
		}catch(Exception e){
			throw new SemanticStoreException();
		}

	}

	@Override
	public Iterable<Dataset> getAllApprovedDatasets() {
		return datasetDao.getAll();
	}

	@Override
	public List<Dataset> getSomeApprovedDatasets() {
		return datasetDao.getSome();
	}

	@Override
	public boolean existApprovedDataset(String dataset) {
		Dataset retriveDataset = getApprovedDatasetByName(Parsing
				.withoutSpecialCharacters(dataset));

		return !(retriveDataset == null);
	}

	@Override
	public Dataset getApprovedDatasetByName(String name) {
		logger.debug("A getDatasetByName llega:" + name);
		return this.getApprovedDatasetByQName("Dataset:" + name);
	}

	@Override
	public Dataset getApprovedDatasetByQName(String qName) {
		logger.debug("A getDatasetByQName llega:" + qName);

		QName QN = new QName(properties.getNamespace(), qName);
		Dataset myDataset;
		try {
			myDataset = this.datasetDao.getById(QN);
		} catch (java.lang.ClassCastException e) {
			myDataset = null;
		}
		return myDataset;
	}

	@Override
	public int getApprovedDatasetQuantity() {
		return datasetDao.getQty();
	}

	@Override
	public Integer getApprovedDatasetsFromLocation(String string) {
		return datasetDao.getFromLocation(string);
	}
	
	

	@Override
	public List<PreDataset> getAllPreDatasets() {
		List<PreDataset> response = new ArrayList<PreDataset>();
		Iterator<PreDataset> myIterator = preDatasetDao.getAll().iterator();

		while (myIterator.hasNext()) {
			response.add(myIterator.next());
		}

		return response;
	}

	@Override
	public void update(PreDataset dataset) {
		preDatasetDao.update(dataset);

	}

	@Override
	public List<PreDataset> getPreDatasetWaitingForApproval() throws Exception {
		return getAllPreDatasets();
	}

	@Override
	public PreDataset getPreDatasetById(int id) {
		return preDatasetDao.getById(id);
	}

	@Override
	public void delete(PreDataset plain) {
		this.preDatasetDao.delete(plain);
	}

	@Override
	public PreDataset asyncGetPreDataset(String title) {
		return this.preDatasetDao.ayncGetPreDataset(title);
	}

	@Override
	public void asyncUpdatePreDataset(PreDataset aDataset) {
		this.preDatasetDao.asyncUpdateDataset(aDataset);
	}

	@Override
	public boolean existPreDataset(String title) {
		return this.preDatasetDao.exist(title);
	}

	@Override
	public QueryResponse resolveIndexQuery(String terms, String[] facetFields,
			Map<String, String> filterValuesMap, List<String> sortByFields,
			List<String> listKeywords) throws MalformedURLException,
			SolrServerException {
		String myQuery = "type:dataset AND ((*" + terms + "* OR *"
				+ terms.toLowerCase() + "* OR *" + terms.toUpperCase()
				+ "*)OR (description:*" + terms + "* OR description:*"
				+ terms.toLowerCase() + "* OR description:*"
				+ terms.toUpperCase() + "))";
		boolean facets = true;
		Integer minCount = 1;

		List<String> filter = new ArrayList<String>();

		if (filterValuesMap != null) {
			Set<String> filtersKey = filterValuesMap.keySet();
			for (String facet : filtersKey) {
				if (!facet.equals("modified") && !facet.equals("size")) {
					String aux = "\"";
					aux = aux.concat(filterValuesMap.get(facet));
					aux = aux.concat("\"");
					filter.add(facet + ":" + aux);
				} else {
					if (facet.equals("modified"))
						filter.add(facet + ""
								+ getSolrQueryDate(filterValuesMap.get(facet)));
					else
						filter.add(facet
								+ ""
								+ getSolrQueryExtention(filterValuesMap
										.get(facet)));
				}
			}
		}
		if (listKeywords != null) {
			for (String kw : listKeywords) {
				filter.add("keyword:" + kw);
			}
		}
		String sortByField = null;
		QueryResponse resp = null;
		if (sortByFields != null) {
			sortByField = sortByFields.get(1);
			if (sortByFields.get(2).equals("a")) {
				resp = solrDao.filterFacetQuery(myQuery, facets, minCount,
						facetFields, filter, sortByField, ORDER.asc);
				
			} else {
				resp = solrDao.filterFacetQuery(myQuery, facets, minCount,
						facetFields, filter, sortByField, ORDER.desc);
			}
		} else {
			resp = solrDao.filterFacetQuery(myQuery, facets, minCount,
					facetFields, filter, sortByField, ORDER.asc);
		}

		/*Aplicación de sortBy vía JAVA*/
		
		return resp;
	}

	@Override
	public List<Count> getAllIndexKeywords() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "keyword" };
		QueryResponse resp = resolveIndexQuery("*", kw, null, null, null);

		return resp.getFacetField("keyword").getValues();
	}
	
	@Override
	public List<Count> getAllIndexFormats() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "format" };
		QueryResponse resp = resolveIndexQuery("*", kw, null, null, null);

		return resp.getFacetField("format").getValues();
	}

	@Override
	public List<Count> getAllIndexPublishers() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "publisher" };
		QueryResponse resp = resolveIndexQuery("*", kw, null, null, null);

		return resp.getFacetField("publisher").getValues();
	}

	@Override
	public FacetDatasetResponse searchOnIndexFacetDataset(String terms,
			String[] queryFacetsFields, Map<String, String> filterValuesMap,
			List<String> mySortByFields, List<String> myKeywords) {
		QueryResponse rsp = null;
		try {
			rsp = resolveIndexQuery(terms, queryFacetsFields, filterValuesMap,
					mySortByFields, myKeywords);
		} catch (MalformedURLException e) {
			logger.fatal("No se puede conectar a solr");
		} catch (SolrServerException e) {
			logger.error(e.getMessage(), e);
		}

		List<Dataset> datasets = getDatasets(rsp);
		
		Map<String, List<FacetCount>> values = new HashMap<String, List<FacetCount>>();

		for (String a : queryFacetsFields) {
			List<FacetCount> fc = new ArrayList<FacetCount>();
			if (rsp.getFacetField(a).getValues() != null) {
				for (Count c : rsp.getFacetField(a).getValues()) {
					fc.add(new FacetCount(c.getName(), c.getCount()));
				}

				values.put(a, fc);
			}
		}

		return new FacetDatasetResponse(datasets, values);
	}

	@Override
	public Set<String> getIndexRelatedPublishers(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedPublishers(term, "dataset");
	}

	@Override
	public void cleanIndex() {
		this.solrDao.cleanIndex();

	}

	@Override
	public Collection<? extends String> getIndexRelatedKeywords(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedKeywords(term, "dataset");
	}

	@Override
	public Collection<? extends String> getIndexRelatedTitles(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedTitles(term, "dataset");
	}

	@Override
	public List<Count> getIndexAllLocations() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "location" };
		QueryResponse resp = resolveIndexQuery("*", kw, null, null, null);

		return resp.getFacetField("location").getValues();
	}

	@Override
	public void update(Dataset dataset,Dataset readyDataset) throws SemanticStoreException, IndexStoreException  {
		this.delete(dataset); // dataset for deletion
		this.store(readyDataset);
	}
	
	private String getSolrQueryDate(String modifValue) {
		if (modifValue.equals(Integer.toString(Calendar.getInstance().get(
				Calendar.YEAR))))
			return ":[" + modifValue + "-01-01T00:00:00.000Z TO NOW]";
		else {
			if (modifValue.equals(Integer.toString(Calendar.getInstance().get(
					Calendar.YEAR) - 1)))
				return ":[" + modifValue + "-01-01T00:00:00.000Z TO "
						+ modifValue + "-12-31T23:59:59Z]";
			else {
				if (modifValue.equals(Integer.toString(Calendar.getInstance()
						.get(Calendar.YEAR) - 5) + " hasta la actualidad"))
					return ":["
							+ (Calendar.getInstance().get(Calendar.YEAR) - 5)
							+ "-01-01T00:00:00.000Z TO NOW]";
				else
					return ":[* TO NOW]";
			}
		}
	}

	private String getSolrQueryExtention(String extention) {
		if (extention.equals("Hasta 128kb"))
			return ":[1 TO 128]";
		else {
			if (extention.equals("Entre 128kb y 512kb"))
				return ":[128 TO 512]";
			else {
				if (extention.equals("Mas de 512kb"))
					return ":[512 TO *]";
				else
					return ":0";
			}
		}
	}

	private List<Dataset> getDatasets(QueryResponse rsp) {

		List<Dataset> datasets = new ArrayList<Dataset>();
		SolrDocumentList docs = rsp.getResults();

		List<SolrDocument> list = docs.subList(0, docs.size());

		Set<String> keywords;
		for (SolrDocument sd : list) {
			keywords = new HashSet<String>();
			Object obj = sd.getFieldValue("keyword");
			if (obj instanceof String) {
				keywords.add((String) obj);
			} else
				keywords.addAll((List<String>) obj);

			datasets.add(new Dataset(
					(String) (sd.getFieldValue("title")),
					(String) (sd.getFieldValue("license")),
					keywords,
					(String) (sd.getFieldValue("dataQuality")),
					(String) ((Date) (sd.getFieldValue("modified"))).toString(),
					(String) (sd.getFieldValue("spatial")), (String) (sd
							.getFieldValue("temporal")), (String) (sd
							.getFieldValue("location")), (String) (sd
							.getFieldValue("publisher")), (String) (sd
							.getFieldValue("resourceType")), (String) (sd
							.getFieldValue("titleId"))));
		}

		return datasets;
	}

	
	
	/*@Override
	public List<Count> getAllIndexResourcesTypes() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "resourceType" };
		QueryResponse resp = resolveIndexQuery("*", kw, null, null, null);

		return resp.getFacetField("resourceType").getValues();
	}*/
	
	/*
	@Override
	public Dataset newApprovedDataset(String title) {
		return datasetDao.newDataset(title);
	}*/

	/*
	@Override
	public List<Dataset> getLastestApprovedDatasetsFromPublisherName(String name) {
		return datasetDao.getLastestFromPublisherName(name);
	}*/

	/*@Override
	public List<Dataset> getRecentlyModifiedApprovedDatasets() {
		return datasetDao.getRecentlyModified();
	}*/
/*
	@Override
	public List<Dataset> getApprovedDatasetsFromKeyword(String keyword, int qty) {
		return datasetDao.getFromKeyword(keyword, qty);
	}*/

}
