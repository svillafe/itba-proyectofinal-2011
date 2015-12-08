package argendata.dao.semantic.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.semantic.SolrDao;
import argendata.exceptions.IndexStoreException;
import argendata.model.dcat.Dataset;
import argendata.model.relational.App;

@Repository
public class SolrDaoImpl implements SolrDao {

	private RepositoryConnectionManager connectionManager;
	private static final Logger logger = Logger.getLogger(SolrDaoImpl.class);

	@Autowired
	public SolrDaoImpl(RepositoryConnectionManager rcm) {
		super();
		this.connectionManager = rcm;
	}

	public QueryResponse filterFacetQuery(String query, boolean facet,
			Integer minCount, String[] facetFields, List<String> filterQuery,
			String orderField, ORDER orderType) throws MalformedURLException,
			SolrServerException {

		SolrServer server = this.connectionManager.getSolrServer();
		SolrQuery solrQuery = new SolrQuery();

		solrQuery.setQuery(query).setFacet(facet);

		if (minCount != null) {
			solrQuery.setFacetMinCount(0);
		}

		if (facetFields != null) {
			for (String str : facetFields) {
				solrQuery.addFacetField(str);
			}
		}

		if (filterQuery != null) {
			for (String str : filterQuery) {
				solrQuery.addFilterQuery(str);
			}
		}

		if (orderField != null) {
			solrQuery.addSortField(orderField, orderType);
		}
		// solrQuery.addSortField("title", ORDER.asc);
		// solrQuery.addSortField("title", ORDER.asc);

		/* Setting the max amount of datasets returned */
		solrQuery.setRows(500);
		return server.query(solrQuery);
	}

	public QueryResponse simpleFacetQuery(String query, Integer minCount,
			Integer facetLimit, List<String> facetFields,
			List<String> filtersQuery, String orderField, ORDER orderType)
			throws MalformedURLException, SolrServerException {

		SolrServer server = this.connectionManager.getSolrServer();

		SolrQuery solrQuery = new SolrQuery().setQuery(query).setFacet(true);
		QueryResponse rsp;

		if (minCount != null) {
			solrQuery.setFacetMinCount(minCount);
		}

		if (facetLimit != null) {
			solrQuery.setFacetLimit(facetLimit);
		}

		if (facetFields != null) {
			for (String field : facetFields) {
				solrQuery.addFacetField(field);
			}
		}

		if (filtersQuery != null) {
			for (String field : filtersQuery) {
				solrQuery.addFilterQuery(field);
			}
		}

		if (orderField != null) {
			solrQuery.addSortField(orderField, orderType);
		}

		rsp = server.query(solrQuery);
		return rsp;
	}

	public QueryResponse simpleFacetQuery(String string, Integer minCount,
			Integer facetLimit, List<String> facetFields)
			throws MalformedURLException, SolrServerException {

		return this.simpleFacetQuery(string, minCount, facetLimit, facetFields,
				null, null, null);
	}

	public void store(Object obj) throws IOException, SolrServerException {

		SolrServer server;
		server = this.connectionManager.getSolrServer();
		server.addBean(obj);
		server.commit();
	}

	public void delete(Object obj) throws IndexStoreException {
		try {
			SolrServer server = this.connectionManager.getSolrServer();

			if (obj instanceof App) {
				server.deleteByQuery("title:\"" + ((App) obj).getName() + "\"");
			} else if (obj instanceof Dataset) {
				server.deleteByQuery("title:\"" + ((Dataset) obj).getTitle()
						+ "\"");
			}
			server.commit();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new IndexStoreException();
		}
	}

	private Set<String> getRelatedThings(String query, String element,
			String term) throws MalformedURLException, SolrServerException {

		SolrServer server;
		Set<String> elements = null;
		server = this.connectionManager.getSolrServer();
		SolrQuery solrQuery = new SolrQuery().setQuery(query);
		QueryResponse rsp;
		rsp = server.query(solrQuery);

		SolrDocumentList docs = rsp.getResults();
		List<SolrDocument> list = docs.subList(0, docs.size());

		elements = new TreeSet<String>();
		for (SolrDocument sd : list) {
			List<Object> p = (List<Object>) (sd.getFieldValues(element));

			Iterator<Object> it = p.iterator();

			String s;
			while (it.hasNext()) {

				s = withoutSpecialCharacters((String) it.next());
				term = withoutSpecialCharacters(term);

				if (!s.contains(term)) {
					it.remove();
				}
			}

			for (Object o : p) {
				elements.add((String) o);
			}

		}

		return elements;
	}

	@Override
	public Set<String> getRelatedPublishers(String term, String type)
			throws MalformedURLException, SolrServerException {
		term = withoutSpecialCharacters(term);
		String query = "type:dataset AND ((publisher:*" + term
				+ "*) OR (publisher:*" + term.toLowerCase()
				+ "*) OR (publisher:*" + term.toUpperCase() + "*))";
		return this.getRelatedThings(query, "publisher", term);
	}

	@Override
	public void cleanIndex() {

		SolrServer server;
		try {
			server = this.connectionManager.getSolrServer();
			server.deleteByQuery("*:*");
			server.commit();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Collection<? extends String> getRelatedKeywords(String term,
			String type) throws MalformedURLException, SolrServerException {
		term = withoutSpecialCharacters(term);
		String query = "type:dataset AND ((keyword:*" + term
				+ "*) OR (keyword:*" + term.toLowerCase() + "*) OR (keyword:*"
				+ term.toUpperCase() + "*))";
		return this.getRelatedThings(query, "keyword", term);
	}

	@Override
	public Collection<? extends String> getRelatedTitles(String term,
			String type) throws MalformedURLException, SolrServerException {
		term = withoutSpecialCharacters(term);
		String query = "type:dataset AND ((title:*" + term + "*) OR (title:*"
				+ term.toLowerCase() + "*) OR (title:*" + term.toUpperCase()
				+ "*))";
		return this.getRelatedThings(query, "title", term);
	}

	private String withoutSpecialCharacters(String title) {
		String resp = title;

		resp = resp.replace('\u00E1', 'a');
		resp = resp.replace('\u00C1', 'a');
		resp = resp.replace('\u00E9', 'e');
		resp = resp.replace('\u00EB', 'e');
		resp = resp.replace('\u00C9', 'e');
		resp = resp.replace('\u00CB', 'e');
		resp = resp.replace('\u00ED', 'i');
		resp = resp.replace('\u00CD', 'i');
		resp = resp.replace('\u00F3', 'o');
		resp = resp.replace('\u00D3', 'o');
		resp = resp.replace('\u00FA', 'u');
		resp = resp.replace('\u00FC', 'u');
		resp = resp.replace('\u00DA', 'u');
		resp = resp.replace('\u00DC', 'u');
		resp = resp.replace('\u00F1', 'n');

		resp = resp.toLowerCase();

		return resp;
	}

}
