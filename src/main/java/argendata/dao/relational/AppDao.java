package argendata.dao.relational;

import java.util.List;

import argendata.dao.GenericDao;
import argendata.model.relational.App;

public interface AppDao extends GenericDao<App> {

	/**
	 * @return A list of non allowed apps.
	 */
	public List<App> getNotAllowed();

	/**
	 * @param id The id from a ArgendataUser.
	 * @return A list of apps proposed by the user.
	 */
	public List<App> getAppsByUser(Integer id);

	/**
	 * @param qName The qualified name of an application.
	 * @return An application.
	 */
	public App getByQname(String qName);

	/**
	 * 
	 * @param name The name of the app that we are looking for. 
	 * @return An application.
	 */
	public App getByNameID(String name);

	/**
	 * @param name The name of the application that we want to know if exists.
	 * @return True if it exists or false if it doesn't.
	 */
	public boolean exist(String name);

	public List<App> getRelatedApp(String qName);
	
}
