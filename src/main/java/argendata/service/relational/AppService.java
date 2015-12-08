/*package argendata.service.relational;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;

import argendata.model.relational.App;

public interface AppService {


	/**
	 * @return The list of allowed apps.
	
	public List<App> getAllowedApps();

	/**
	 * @return The list with all apps.
	
	public List<App> getAllApps();

	/**
	 * @param qName The qualified name of an application.
	 * @return An application with the given qualified name.
	
	public App getPersistedApp(String qName);

	/**
	 * Stores the app in both stores, the index and the semantic one.
	 * @param myApp the app to be stored.
	 * @return An stored Application.
	 * @throws MalformedURLException 
	 * @throws IOException 
	 * @throws SolrServerException
	
	App store(App myApp) throws MalformedURLException, IOException,
			SolrServerException;

	/**
	 * @param obj
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws SolrServerException
	
	public void delete(App obj) throws MalformedURLException, IOException,
			SolrServerException;

	/**
	 * @param myApp A non published yet app to be stored.
	
	public void preStore(App myApp);

	/**
	 * @return A list of applications that haven't been approved or disapproved yet.
	
	public List<App> getWaitingForApproval();

	/**
	 * @param appId An application id.
	 * @return The application for the given id.
	
	public App getPreAppById(int appId);

	/**
	 * @param preApp Deletes from the store the preapp.
	
	public void deletePreApp(App preApp);

	/**
	 * @param preApp The app to be updated.
	
	public void updatePreApp(App preApp);

	/**
	 * @param id The user id.
	 * @return A list with apps that has been published by the given user.
	
	public List<App> getAppsByUser(Integer id);

	/**
	 * @param qName The qualified name for the app.
	 * @return An app.
	
	public App getPreAppByQName(String qName);

	/**
	 * @param title The app title.
	 * @return An app with the title title.
	
	public App getPreAppByName(String title);

	/**
	 * @param name Title of the app.
	 * @return True or false if the app with that name exists or not.
	
	public boolean exist(String name);

}
*/