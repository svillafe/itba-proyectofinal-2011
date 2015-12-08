package argendata.dao.semantic;

import argendata.dao.GenericDao;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;

public interface DistributionDao extends GenericDao<Distribution> {

	/**
	 * @param title The title for the new feed.
	 * @return A new Feed.
	 */
	Feed newFeed(String title);

	/**
	 * @param title The title for the new download.
	 * @return A new Download.
	 */
	Download newDownload(String title);

	/**
	 * @param title The title for the new webservice.
	 * @return A new WebService.
	 */
	WebService newWebService(String title);
}
