package argendata.service.semantic;

import javax.xml.namespace.QName;

import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;

public interface DistributionService {
	
	/**
	 * @param obj Distribution to be stored.
	 * @return The stored distribution.
	 */
	public Distribution store(Distribution obj);

	/**
	 * @param obj The distribution object to be deleted.
	 */
	public void delete(Distribution obj);

	/**
	 * @return An iterable object for all distributions in the store. 
	 */
	public Iterable<Distribution> findAll();

	/**
	 * @param qName A qualified name object with the distribution name.
	 * @return A distribution.
	 */
	public Distribution find(QName qName);

	/**
	 * @param title The title for the feed to be created.
	 * @return A new feed created in the store.
	 */
	public Feed newFeed(String title);

	/**
	 * @param title The title for the dowload to be created.
	 * @return A new download created in the store.
	 */
	public Download newDownload(String title);

	/**
	 * @param title The title for the webservice to be created.
	 * @return A new webservice created in the store.
	 */
	public WebService newWebService(String title);

}
