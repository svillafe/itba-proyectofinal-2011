/*package argendata.service.semantic.impl;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.semantic.DatasetDao;
import argendata.dao.semantic.DistributionDao;
import argendata.dao.semantic.OrganizationDao;
import argendata.dao.semantic.SolrDao;
import argendata.dao.semantic.impl.RepositoryConnectionManager;
import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.foaf.Organization;
import argendata.service.semantic.DatasetService;
import argendata.util.Parsing;
import argendata.util.Properties;

@Service
public class DatasetServiceImpl implements DatasetService {

	private DatasetDao datasetDao;
	private OrganizationDao organizationDao;
	private DistributionDao distributionDao;
	private SolrDao solrDao;
	private Properties properties;
	private final Logger logger = Logger.getLogger(DatasetServiceImpl.class);

	@Autowired
	public DatasetServiceImpl(DatasetDao datasetDao,
			OrganizationDao organizationDao, DistributionDao distributionDao,
			SolrDao solrDao, Properties properties) {
		super();
		this.datasetDao = datasetDao;
		this.organizationDao = organizationDao;
		this.distributionDao = distributionDao;
		this.solrDao = solrDao;
		this.properties = properties;
	}

	public Iterable<Dataset> findAll() {
		return datasetDao.getAll();
	}

	public Dataset store(Dataset obj) throws IOException, SolrServerException {

		String publisherTitle = obj.getPublisher().getName();
		Organization myPublisher = this.organizationDao
				.newOrganization(publisherTitle);
		myPublisher.setName(obj.getPublisher().getName());

		Distribution myDistribution;

		if (obj.getDistribution() instanceof Feed) {
			myDistribution = this.distributionDao.newFeed(obj.getDistribution()
					.getAccessURL());
		} else if (obj.getDistribution() instanceof Download) {
			myDistribution = this.distributionDao.newDownload(obj
					.getDistribution().getAccessURL());
		} else {
			myDistribution = this.distributionDao.newWebService(obj
					.getDistribution().getAccessURL());
		}

		myDistribution.setAccessURL(obj.getDistribution().getAccessURL());
		myDistribution.setFormat(obj.getDistribution().getFormat());
		myDistribution.setSize(obj.getDistribution().getSize());

		Dataset ds = this.datasetDao.newDataset(obj.getTitleId());
		ds.setDataQuality(obj.getDataQuality());
		ds.setDistribution(myDistribution);

		ds.setLocation(obj.getLocation());

		Set<String> set = new TreeSet<String>();

		for (String s : obj.getKeyword()) {
			set.add(s.toLowerCase());
		}

		ds.setKeyword(set);

		ds.setLicense(obj.getLicense());
		ds.setModified(obj.getModified());
		ds.setPublisher(myPublisher);
		ds.setSpatial(obj.getSpatial());
		ds.setTemporal(obj.getTemporal());
		ds.setTitle(obj.getTitle());
		ds.setDescription(obj.getDescription());
		ds.setTitleId(obj.getTitleId());

		Dataset resp = this.datasetDao.store(ds);

		this.solrDao.store(ds);

		return resp;
	}

	public void delete(Dataset obj) throws IOException, SolrServerException {

		this.solrDao.delete(obj);

		this.datasetDao.delete(obj);

	}

	// public Dataset find(QName qName) {

	// return this.datasetDao.getById(qName);
	// }

	public Dataset newDataset(String title) {
		return datasetDao.newDataset(title);

	}

	public List<Dataset> getLastestFromPublisherName(String name) {

		return datasetDao.getLastestFromPublisherName(name);
	}

	public List<Dataset> getSome() {

		return datasetDao.getSome();
	}

	public List<Dataset> getRecentlyModified() {
		return datasetDao.getRecentlyModified();
	}

	public List<Dataset> getFromKeyword(String keyword, int qty) {

		return datasetDao.getFromKeyword(keyword, qty);
	}

	public Dataset getDatasetByName(String name) {
		logger.debug("A getDatasetByName llega:" + name);
		return this.getDatasetByQName("Dataset:" + name);
	}

	public Dataset getDatasetByQName(String qName) {
		logger.debug("A getDatasetByQName llega:" + qName);

		QName QN = new QName(properties.getNamespace(), qName);
		Dataset myDataset;
		try {
			myDataset = this.datasetDao.getById(QN);
		} catch (java.lang.ClassCastException e) {
			myDataset = null;
		}
		// PlainApp myPlainApp = this.plainAppDao.getPlainApp(qName);
		return myDataset;
	}

	public boolean existDataset(String dataset) {

		Dataset retriveDataset = getDatasetByName(Parsing
				.withoutSpecialCharacters(dataset));

		return !(retriveDataset == null);
	}

	public int getQty() {

		return datasetDao.getQty();
	}

	public Integer getFromLocation(String string) {

		return datasetDao.getFromLocation(string);
	}

}
*/