package argendata.dao.semantic;

import java.util.List;

import javax.xml.namespace.QName;

import argendata.dao.GenericDao;
import argendata.model.argendata.Semanticapp;
import argendata.model.relational.RelationalApp;

public interface SemanticAppDao extends GenericDao<Semanticapp> {

	/**
	 * @param qName The qualified name to be fetched in the store.
	 * @return A semanticApp for the given qualified name.
	 */
	public Semanticapp getById(QName qName);

	/**
	 * @param title The title for the new SemanticApp.
	 * @return A new semanticApp.
	 */
	public Semanticapp newSemanticApp(String title);

	/**
	 * @return A list of randomly selected apps.
	 */
	public List<Semanticapp> getSome();

	/**
	 * @return The quantity of semanticApps in the store.
	 */
	public int getQty();

	/**
	 * @param name The qualified name.
	 * @return True or false if a semanticapp for that name exists or not.
	 */
	public boolean exist(String name);

	/**
	 * @param name The semanticApp to fetch.
	 * @return The semanticApp.
	 */
	public Semanticapp getByName(String name);

	
	/**
	 * @param datasetId
	 * @return
	 */
	List<Semanticapp> getRelatedApp(String datasetId);

}
