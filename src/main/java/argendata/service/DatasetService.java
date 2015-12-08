package argendata.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;

import argendata.exceptions.IndexStoreException;
import argendata.exceptions.SemanticStoreException;
import argendata.model.FacetDatasetResponse;
import argendata.model.dcat.Dataset;
import argendata.model.relational.PreDataset;

public interface DatasetService {

	/**
	 * @param obj Dataset object to be stored in the store.
	 * @return The stored dataset.
	 * @throws IndexStoreException 
	 * @throws SemanticStoreException 
	 */
	public Dataset store(Dataset obj) throws SemanticStoreException, IndexStoreException;
	
	/**
	 * @param dataset A dataset to be stored.
	 * @return true or false if the operation ended successfully.
	 * @throws Exception
	 */
	public boolean store(PreDataset dataset) ;
	
	/**
	 * @param obj Dataset to be deleted.
	 * @throws SemanticStoreException 
	 * @throws IndexStoreException 
	 */
	public void delete(Dataset obj) throws SemanticStoreException, IndexStoreException;
	
	/**
	 * @param plain PreDataset to be deleted
	 */
	public void delete(PreDataset plain);
	
	/**
	 * @param dataset Dataset to be update.
	 * @param readyDataset 
	 * @throws IndexStoreException 
	 * @throws SemanticStoreException 
	 */
	public void update(Dataset dataset, Dataset readyDataset) throws SemanticStoreException, IndexStoreException;
	
	/**
	 * @param dataset Dataset to be update.
	 */
	public void update(PreDataset dataset);
	
	/**
	 * @return An iterable for all datasets stored.
	 */
	public Iterable<Dataset> getAllApprovedDatasets();
	
	/**
	 * @return A list of all datasets.
	 */
	public List<PreDataset> getAllPreDatasets();
	
	/**
	 * @param name The title of the dataset.
	 * @return A dataset for the given name.
	 */
	public Dataset getApprovedDatasetByName(String name);
	
	/**
	 * @param qName The qualified name of a dataset.
	 * @return The dataset for the given dataset.
	 */
	public Dataset getApprovedDatasetByQName(String qName);
		
	/**
	 * @param id The id of the plaindataset.
	 * @return A plain dataset.
	 */
	public PreDataset getPreDatasetById(int id);
	
	/**
	 * @param dataset The title of the dataset.
	 * @return True or false if the dataset exists or not.
	 */
	public boolean existApprovedDataset(String dataset);
	
	/**
	 * @param title
	 * @return True or false if a preDataset with that name already exists.
	 */
	public boolean existPreDataset(String title);
	
	/**
	 * @return A list with randomly selected approved datasets.
	 */
	public List<Dataset> getSomeApprovedDatasets();
	
	/**
	 * @param title Title to be fetched.
	 * @return An stored plaindataset for the given title.
	 */
	public PreDataset asyncGetPreDataset(String title);

	/**
	 * @param aDataset A dataset to be updated.
	 */
	public void asyncUpdatePreDataset(PreDataset aDataset);
	
	/**
	 * @param string
	 * @return
	 */
	public Integer getApprovedDatasetsFromLocation(String string);
	
	
	/**
	 * @return A list of plaindatasets that haven't been approved or disapproved yet.
	 * @throws Exception
	 */
	public List<PreDataset> getPreDatasetWaitingForApproval() throws Exception;
	
	/**
	 * @return The quantity of preDataset.
	 */
	public int getApprovedDatasetQuantity();
	
	/**
	 * @param terms
	 * @param facetFields
	 * @param filterValuesMap
	 * @param sortByFields
	 * @param listKeywords
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public QueryResponse resolveIndexQuery(String terms, String[] facetFields, Map<String, String> filterValuesMap,
			List<String> sortByFields, List<String> listKeywords) throws MalformedURLException, SolrServerException;

	/**
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	List<Count> getAllIndexKeywords() throws MalformedURLException,
			SolrServerException;
	

	/**
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	List<Count> getAllIndexFormats() throws MalformedURLException,
		SolrServerException;
	
	
	/**
	 * @return A list with Counts for the publishers of datasets in the store.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public List<Count> getAllIndexPublishers() throws MalformedURLException, SolrServerException;
	
	/**
	 * @param terms
	 * @param queryFacetsFields
	 * @param filterValuesMap
	 * @param mySortByFields
	 * @param myKeywords
	 * @return
	 */
	public FacetDatasetResponse searchOnIndexFacetDataset(String terms,
			String[] queryFacetsFields, Map<String, String> filterValuesMap,
			List<String> mySortByFields, List<String> myKeywords);
	
	/**
	 * @param term
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Set<String> getIndexRelatedPublishers(String term) throws MalformedURLException, SolrServerException;
	
	/**
	 * @param term
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Collection<? extends String> getIndexRelatedKeywords(String term) throws MalformedURLException, SolrServerException;
	
	/**
	 * @param term
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Collection<? extends String> getIndexRelatedTitles(String term) throws MalformedURLException, SolrServerException;
	
	/**
	 * @return A list with Counts for the locations of datasets in the store.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public List<Count> getIndexAllLocations() throws MalformedURLException, SolrServerException;
	
	/**
	 * WARNING!! It clears all the index!!! For debugging user only. 
	 */
	public void cleanIndex();
	
	/*
	 * @param title The title for the dataset to be created.
	 * @return A new dataset with the given title.
	 
	public Dataset newApprovedDataset(String title);*/
	
	/*
	 * @return A list with Counts for the resourcesTypes of datasets in the store.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 
	public List<Count> getAllIndexResourcesTypes() throws MalformedURLException, SolrServerException;*/
	
	/*
	 * @param string Publishers name.
	 * @return A list of datasets that have been published by the given publisher.
	
	public List<Dataset> getLastestApprovedDatasetsFromPublisherName(String string);*/
	
	/*
	 * @return A list with the recently modified datasets.
	 
	public List<Dataset> getRecentlyModifiedApprovedDatasets();*/
	
	/*
	 * @param string Keyword to be fetched.
	 * @param qty Query limit.
	 * @return A list with datasets that have the wanted keyword
	 
	public List<Dataset> getApprovedDatasetsFromKeyword(String keyword, int qty);*/
				
}
