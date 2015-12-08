/*package argendata.service.semantic;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;

import argendata.model.FacetDatasetResponse;

public interface DatasetSolrService { 


	
	/**
	 * @param terms
	 * @param facetFields
	 * @param filterValuesMap
	 * @param sortByFields
	 * @param listKeywords
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public QueryResponse resolveQuery(String terms, String[] facetFields, Map<String, String> filterValuesMap,
			List<String> sortByFields, List<String> listKeywords) throws MalformedURLException, SolrServerException;

	/**
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	List<Count> getAllKeywords() throws MalformedURLException,
			SolrServerException;

	/**
	 * @return A list with Counts for the publishers of datasets in the store.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public List<Count> getAllPublishers() throws MalformedURLException, SolrServerException;

	/**
	 * @return A list with Counts for the resourcesTypes of datasets in the store.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public List<Count> getAllResourcesTypes() throws MalformedURLException, SolrServerException;

	/**
	 * @param terms
	 * @param queryFacetsFields
	 * @param filterValuesMap
	 * @param mySortByFields
	 * @param myKeywords
	 * @return
	
	public FacetDatasetResponse searchFacetDataset(String terms,
			String[] queryFacetsFields, Map<String, String> filterValuesMap,
			List<String> mySortByFields, List<String> myKeywords);

	/**
	 * @param term
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public Set<String> getRelatedPublishers(String term) throws MalformedURLException, SolrServerException;

	/**
	 * WARNING!! It clears all the index!!! For debugging user only. 
	
	public void cleanIndex();

	/**
	 * @param term
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public Collection<? extends String> getRelatedKeywords(String term) throws MalformedURLException, SolrServerException;

	/**
	 * @param term
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public Collection<? extends String> getRelatedTitles(String term) throws MalformedURLException, SolrServerException;

	/**
	 * @return A list with Counts for the locations of datasets in the store.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	
	public List<Count> getAllLocations() throws MalformedURLException, SolrServerException;
		
	
}
*/