/*package argendata.service.semantic.impl;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.semantic.SolrDao;
import argendata.model.FacetCount;
import argendata.model.FacetDatasetResponse;
import argendata.model.dcat.Dataset;
import argendata.service.semantic.DatasetSolrService;

@Service
public class DatasetSolrServiceImpl implements DatasetSolrService {

	private SolrDao solrDao;
	private final Logger logger = Logger
			.getLogger(DatasetSolrServiceImpl.class);

	@Autowired
	public DatasetSolrServiceImpl(SolrDao solrDao) {

		this.solrDao = solrDao;

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

	public List<Count> getAllKeywords() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "keyword" };
		QueryResponse resp = resolveQuery("*", kw, null, null, null);

		return resp.getFacetField("keyword").getValues();
	}

	public QueryResponse resolveQuery(String terms, String[] facetFields,
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

				// SolrQuery.ORDER.asc);
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

		return resp;
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

	public List<Count> getAllPublishers() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "publisher" };
		QueryResponse resp = resolveQuery("*", kw, null, null, null);

		return resp.getFacetField("publisher").getValues();
	}

	public List<Count> getAllResourcesTypes() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "resourceType" };
		QueryResponse resp = resolveQuery("*", kw, null, null, null);

		return resp.getFacetField("resourceType").getValues();
	}

	public List<Count> getAllLocations() throws MalformedURLException,
			SolrServerException {
		String[] kw = { "location" };
		QueryResponse resp = resolveQuery("*", kw, null, null, null);

		return resp.getFacetField("location").getValues();
	}

	public FacetDatasetResponse searchFacetDataset(String terms,
			String[] queryFacetsFields, Map<String, String> filterValuesMap,
			List<String> mySortByFields, List<String> myKeywords) {
		QueryResponse rsp = null;
		try {
			rsp = resolveQuery(terms, queryFacetsFields, filterValuesMap,
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
	public Set<String> getRelatedPublishers(String term)
			throws MalformedURLException, SolrServerException {

		return this.solrDao.getRelatedPublishers(term, "dataset");

	}

	@Override
	public void cleanIndex() {
		this.solrDao.cleanIndex();
	}

	@Override
	public Collection<? extends String> getRelatedKeywords(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedKeywords(term, "dataset");
	}

	@Override
	public Collection<? extends String> getRelatedTitles(String term)
			throws MalformedURLException, SolrServerException {
		return this.solrDao.getRelatedTitles(term, "dataset");

	}
}
*/