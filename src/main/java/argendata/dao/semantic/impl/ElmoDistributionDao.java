package argendata.dao.semantic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.semantic.DistributionDao;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.util.Properties;

@Repository
public class ElmoDistributionDao extends ElmoGenericDao<Distribution> implements DistributionDao {
	private RepositoryConnectionManager connectionManager;

	@Autowired
	public ElmoDistributionDao(RepositoryConnectionManager rcm, Properties properties) {
		this.connectionManager = rcm;
		super.setManager(connectionManager.getManager());
		super.setProperties(properties);
	}
	
	@Override
	public Iterable<Distribution> getAll(){
		return super.getAll();
		
	}

	public Distribution newDistribution(String title) {
		Distribution resp = super.newElement("Distribution:"+title);
		return resp;
	}

	public Feed newFeed(String title) {
		Feed resp = super.newElement("Feed:"+title,Feed.class);
		return resp;
	}

	public Download newDownload(String title) {
		Download resp = super.newElement("Download:"+title,Download.class);
		return resp;
	}

	public WebService newWebService(String title) {
		WebService resp = super.newElement("WebService:"+title,WebService.class);
		return resp;
	}
	
}
