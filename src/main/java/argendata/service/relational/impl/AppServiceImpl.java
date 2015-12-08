/*package argendata.service.relational.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.relational.AppDao;
import argendata.dao.relational.PlainAppDao;
import argendata.dao.semantic.DatasetDao;
import argendata.dao.semantic.SemanticAppDao;
import argendata.dao.semantic.SolrDao;
import argendata.model.argendata.Semanticapp;
import argendata.model.dcat.Dataset;
import argendata.model.relational.App;
import argendata.model.relational.PlainApp;
import argendata.service.relational.AppService;
import argendata.util.Parsing;
import argendata.util.Properties;

/*@Service
public class AppServiceImpl implements AppService {

	private SemanticAppDao semanticAppDao;
	private PlainAppDao plainAppDao;
	private AppDao preAppDao;
	private DatasetDao datasetDao;
	private SolrDao solrDao;
	private Properties properties;

	@Autowired
	public AppServiceImpl(SemanticAppDao semanticAppDao, PlainAppDao appDao,
			AppDao preAppDao, DatasetDao datasetDao, SolrDao solrDao,
			Properties properties) {

		this.plainAppDao = appDao;
		this.preAppDao = preAppDao;
		this.semanticAppDao = semanticAppDao;
		this.datasetDao = datasetDao;
		this.solrDao = solrDao;
		this.properties = properties;
	}

	public List<App> getAllApps() {
		List<App> resp = new ArrayList<App>();
		Iterable<Semanticapp> mySemanticApps = semanticAppDao.getAll();
		Iterator<Semanticapp> mySemanticAppIterator = mySemanticApps.iterator();
		while (mySemanticAppIterator.hasNext()) {
			Semanticapp aux1 = mySemanticAppIterator.next();
			PlainApp aux2 = this.plainAppDao.getPlainApp("Semanticapp:"
					+ aux1.getName());
			if (aux1 != null && aux2 != null) {
				resp.add(new App(aux1, aux2));
			}
		}

		return resp;
	}

	public List<App> getAllowedApps() {
		List<App> resp = new ArrayList<App>();
		Iterator<PlainApp> allowedAppsIterator = this.plainAppDao
				.getAllowedApps().iterator();
		while (allowedAppsIterator.hasNext()) {
			PlainApp aux1 = allowedAppsIterator.next();
			QName qName = new QName(properties.getNamespace(),
					aux1.getqName());

			Semanticapp aux2 = this.semanticAppDao.getById(qName);

			resp.add(new App(aux2, aux1));
		}

		return resp;
	}

	public App getPersistedApp(String qName) {
		QName QN = new QName(properties.getNamespace(), qName);
		Semanticapp mySemanticApp = this.semanticAppDao.getById(QN);
		PlainApp myPlainApp = this.plainAppDao.getPlainApp(qName);

		return new App(mySemanticApp, myPlainApp);
	}

	public App store(App myApp) throws MalformedURLException, IOException,
			SolrServerException {
		this.plainAppstore(myApp.getPlainApp());
		this.semanticAppStore(myApp.getSemanticApp());
		return myApp;
	}

	private PlainApp plainAppstore(PlainApp myApp) {
		return plainAppDao.store(myApp);
	}

	private Semanticapp semanticAppStore(Semanticapp obj) throws IOException,
			SolrServerException {

		Semanticapp semanticAppStore = this.semanticAppDao.newSemanticApp(obj
				.getNameId());

		semanticAppStore.setDescription(obj.getDescription());
		semanticAppStore.setPoints(obj.getPoints());
		semanticAppStore.setUrl(obj.getUrl());
		semanticAppStore.setName(obj.getName());
		semanticAppStore.setAppkeyword(obj.getAppkeyword());
		semanticAppStore.setNameId(obj.getNameId());
		Set<Dataset> pushMyDataset = new TreeSet<Dataset>();
		for (Dataset t : obj.getDataset()) {
			Dataset d = this.datasetDao.newDataset(t.getTitleId());
			QName QN = new QName(properties.getNamespace(), "Dataset:"
					+ t.getTitleId());
			Dataset old = (Dataset) (this.datasetDao.getById(QN));

			d.setAccessURL(old.getAccessURL());
			d.setDataQuality(old.getDataQuality());
			d.setDistribution(old.getDistribution());
			d.setFormat(old.getFormat());
			d.setKeyword(old.getKeyword());
			d.setLicense(old.getLicense());
			d.setModified(old.getModified());
			d.setPublisher(old.getPublisher());
			d.setSize(old.getSize());
			d.setSpatial(old.getSpatial());
			d.setTemporal(old.getTemporal());

			pushMyDataset.add(d);
		}
		semanticAppStore.setDataset(pushMyDataset);
		Semanticapp resp = this.semanticAppDao.store(semanticAppStore);

		this.solrDao.store(obj);

		return resp;
	}

	public void delete(App obj) throws MalformedURLException, IOException,
			SolrServerException {

		this.solrDao.delete(obj);

		this.plainAppDao.delete(this.plainAppDao.getPlainApp("Semanticapp:"
				+ obj.getNameId()));

		QName QN = new QName(properties.getNamespace(), "Semanticapp:"
				+ obj.getNameId());
		Semanticapp mySemanticApp = this.semanticAppDao.getById(QN);

		this.semanticAppDao.delete(mySemanticApp);

	}

	public void preStore(App myApp) {
		this.preAppDao.store(myApp);

	}

	public List<App> getWaitingForApproval() {
		List<App> response = new ArrayList<App>();
		response.addAll(this.preAppDao.getNotAllowed());
		return response;

	}

	public App getPreAppById(int appId) {
		return this.preAppDao.getById(appId);
	}

	public void deletePreApp(App preApp) {
		this.preAppDao.delete(preApp);

	}

	public void updatePreApp(App preApp) {
		this.preAppDao.store(preApp);

	}

	public List<App> getAppsByUser(Integer id) {

		List<App> resp = new ArrayList<App>();

		List<PlainApp> plainAppList = this.plainAppDao.getAppsByUser(id);

		for (PlainApp p : plainAppList) {
			resp.add(this.getPersistedApp(p.getqName()));
		}

		resp.addAll(this.preAppDao.getAppsByUser(id));

		return resp;
	}

	public App getPreAppByQName(String qName) {

		return this.preAppDao.getByQname(qName);
	}

	public App getPreAppByName(String name) {
		return this.preAppDao.getByNameID(name);
	}

	public List<PlainApp> getSome() {

		return this.semanticAppDao.getSome();
	}

	public int getQty() {

		return this.semanticAppDao.getQty();
	}

	@Override
	public boolean exist(String name) {
		String nameId = Parsing.withoutSpecialCharacters(name);
		if (!this.semanticAppDao.exist(nameId) && !this.preAppDao.exist(nameId)) {
			return false;
		}
		return true;
	}

}
*/