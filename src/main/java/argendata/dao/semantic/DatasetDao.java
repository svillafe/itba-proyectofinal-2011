package argendata.dao.semantic;

import java.util.List;

import javax.xml.namespace.QName;

import argendata.dao.GenericDao;
import argendata.model.dcat.Dataset;

public interface DatasetDao extends GenericDao<Dataset> {


	/**
	 * @param qName The QualifiedName that identifies the dataset.
	 * @return A dataset.
	 */
	Dataset getById(QName qName);

	/**
	 * @param title The title that will be setted in the new dataset.
	 * @return A new dataset created in the store.
	 */
	Dataset newDataset(String title);

	/**
	 * @param name Name of the publisher.
	 * @return A list with the latest datasets from that publisher.
	 */
	List<Dataset> getLastestFromPublisherName(String name);

	/**
	 * @return List of recently modified datasets.
	 */
	List<Dataset> getRecentlyModified();

	/**
	 * @param keyword Keyword to fetch in the store.
	 * @param qty Quantity of results.
	 * @return A list with datasets that contains the wanted keyword.
	 */
	List<Dataset> getFromKeyword(String keyword, int qty);

	/**
	 * @return A list with randomly selected datasets.
	 */
	List<Dataset> getSome();

	/**
	 * @return The amount of datasets in the store.
	 */
	int getQty();

	/**
	 * @param string The location name.
	 * @return The amount of datasets for the given location.
	 */
	Integer getFromLocation(String string);
	
}
