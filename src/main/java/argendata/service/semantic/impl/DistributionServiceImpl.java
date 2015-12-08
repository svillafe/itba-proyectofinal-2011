package argendata.service.semantic.impl;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.semantic.DistributionDao;
import argendata.dao.semantic.impl.ElmoDistributionDao;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.service.semantic.DistributionService;

@Service
public class DistributionServiceImpl implements DistributionService {

	private DistributionDao distributionDao;
	
	@Autowired
	public DistributionServiceImpl(DistributionDao distributionDao) {
		super();
		this.distributionDao = distributionDao;
	}

	public Iterable<Distribution> findAll() {
		return distributionDao.getAll();
	}

	public Distribution store(Distribution obj) {

		return this.distributionDao.store(obj);
	}

	public void delete(Distribution obj) {
		this.distributionDao.delete(obj);
	}

	public Distribution find(QName qName) {

		return this.distributionDao.getById(qName);
	}

	public Feed newFeed(String title) {
		Feed resp = distributionDao.newFeed(title);
		return resp;
	}

	public Download newDownload(String title) {
		Download resp = distributionDao.newDownload(title);
		return resp;
	}

	public WebService newWebService(String title) {
		WebService resp = distributionDao.newWebService(title);
		return resp;
	}

}
