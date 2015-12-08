package argendata.dao.relational;

import java.util.List;

import argendata.dao.GenericDao;
import argendata.model.relational.RelationalApp;

public interface RelationalAppDao extends GenericDao<RelationalApp> {
	
	/**
	 * @param qName A qualified name.
	 * @return A plainApp.
	 */
	public RelationalApp getRelationalApp(String qName);

	/**
	 * @return A list of allowed apps.
	 */
	public List<RelationalApp> getAllowedApps();

	/**
	 * @param id The user id.
	 * @return A list of plainApps.
	 */
	public List<RelationalApp> getAppsByUser(Integer id);
}
