package argendata.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import argendata.exceptions.IndexStoreException;
import argendata.exceptions.SemanticStoreException;
import argendata.model.FacetAppResponse;
import argendata.model.FacetCount;
import argendata.model.argendata.Semanticapp;
import argendata.model.relational.App;

public interface AppService {

	/**
	 * Stores the app in both stores, the index and the semantic one.
	 * 
	 * @param myApp
	 *            the app to be stored.
	 * @return An stored Application.
	 * @throws IndexStoreException 
	 * @throws SemanticStoreException 
	 */
	App approvedAppStore(App myApp) throws  SemanticStoreException, IndexStoreException;
	
	/**
	 * @param myApp
	 *            A non published yet app to be stored.
	 */
	public void preAppStore(App myApp);

	/**
	 * @param obj
	 * @throws IndexStoreException 
	 */
	public void approvedAppDelete(App obj) throws IndexStoreException;
	
	/**
	 * @param preApp
	 *            Deletes from the store the preapp.
	 */
	public void preAppDelete(App preApp);
	
	/**
	 * 
	 * @param oldApp
	 * @param newApp 
	 * @throws IndexStoreException 
	 * @throws SemanticStoreException 
	 */
	public void approvedAppUpdate(App oldApp, App newApp) throws  SemanticStoreException, IndexStoreException;
	
	/**
	 * @param preApp
	 *            The app to be updated.
	 */
	public void preAppUpdate(App preApp);

	/**
	 * @return The list with all apps.
	 */
	public List<App> getAllApprovedApps();
	
	/**
	 * @return A list of applications that haven't been approved or disapproved
	 *         yet.
	 */
	public List<App> getWaitingForApproval();

	/**
	 * @param qName
	 *            The qualified name of an application.
	 * @return An application with the given qualified name.
	 */
	public App getApprovedAppByQName(String qName);
	
	/**
	 * @param name
	 * 
	 * @return
	 */
	App getApprovedAppByName(String name);
	
	/**
	 * @param qName
	 *            The qualified name for the app.
	 * @return An app.
	 */
	public App getPreAppByQName(String qName);
	
	/**
	 * @param appId
	 *            An application id.
	 * @return The application for the given id.
	 */
	public App getPreAppById(int appId);

	/**
	 * @param name
	 *            The app name.
	 * @return An app with the title name.
	 */
	public App getPreAppByNameId(String name);

	/**
	 * @param id
	 *            The user id.
	 * @return A list with apps (Approved/disapproved/waiting for a decision)
	 *         that has been published by the given user.
	 */
	public List<App> getAppsByUser(Integer id);

	/**
	 * @param name
	 *            Title of the app.
	 * @return True or false if the app with that name exists or not.
	 */
	public boolean exist(String name);

	/**
	 * @param myTerms
	 * @param mySortByFields
	 * @param myKeywords
	 * @return
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public FacetAppResponse searchOnIndexFacetApp(String myTerms,
			List<String> mySortByFields, List<String> myKeywords)
			throws MalformedURLException, SolrServerException;

	/**
	 * @return
	 */
	public List<FacetCount> getIndexAllAppKeywords();

	/**
	 * Returns the keywords that relates to the terms.
	 * 
	 * @param term
	 *            the terms of the search.
	 * @return A collection that has the related keywords.
	 * @throws MalformedURLException
	 * @throws SolrServerException
	 */
	public Collection<? extends String> getIndexRelatedKeywords(String term)
			throws MalformedURLException, SolrServerException;

	/**
	 * @return A list with randomly selected approved apps (only return the
	 *         semantic part of each app).
	 */
	List<Semanticapp> getSomeApprovedApps();

	/**
	 * 
	 * @return
	 */
	public int getApprovedAppQuantity();

	/**
	 * @param datasetId
	 * @return
	 */
	List<String> getRelatedAppId(String datasetId);

	List<String> getRelatedAppName(String qName);

}
