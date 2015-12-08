package argendata.dao.semantic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;

import argendata.exceptions.IndexStoreException;
import argendata.model.argendata.Semanticapp;
import argendata.model.relational.App;

public interface SolrDao {
	
	/**
	 * @param string
	 * @param minCount
	 * @param facetLimit
	 * @param facetFields
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public QueryResponse simpleFacetQuery(String string, Integer minCount,
			Integer facetLimit, List<String> facetFields)
			throws MalformedURLException, SolrServerException;

	/**
	 * @param query
	 * @param facet
	 * @param minCount
	 * @param facetFields
	 * @param filter
	 * @param orderField
	 * @param orderType
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public QueryResponse filterFacetQuery(String query, boolean facet,
			Integer minCount, String[] facetFields, List<String> filter, String orderField,
			ORDER orderType) throws MalformedURLException, SolrServerException;

	/**
	 * @param query
	 * @param minCount
	 * @param facetLimit
	 * @param facetFields
	 * @param filtersQuery
	 * @param orderField
	 * @param orderType
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	QueryResponse simpleFacetQuery(String query, Integer minCount,
			Integer facetLimit, List<String> facetFields,
			List<String> filtersQuery, String orderField, ORDER orderType)
			throws MalformedURLException, SolrServerException;

	/**
	 * @param obj
	 * @throws IndexStoreException 
	 */
	public void delete(Object obj) throws IndexStoreException;

	/**
	 * @param obj
	 * @throws IOException
	 * @throws SolrServerException
	 */
	public void store(Object obj) throws IOException, SolrServerException;

	/**
	 * @param term
	 * @param type
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Set<String> getRelatedPublishers(String term, String type) throws MalformedURLException, SolrServerException;

	/**
	 * Clears the index. Method for debugging purposes.
	 */
	public void cleanIndex();

	/**
	 * @param term
	 * @param string
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Collection<? extends String> getRelatedKeywords(String term,
			String string) throws MalformedURLException, SolrServerException;

	/**
	 * @param term
	 * @param type
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Collection<? extends String> getRelatedTitles(String term, String type) throws MalformedURLException, SolrServerException;

}
