package argendata.dao.semantic.impl;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.ElmoQuery;
import org.springframework.beans.factory.annotation.Autowired;

import argendata.dao.semantic.DatasetDao;
import argendata.model.dcat.Dataset;
import argendata.util.Properties;

@org.springframework.stereotype.Repository
public class ElmoDatasetDao extends ElmoGenericDao<Dataset> implements
		DatasetDao {

	private RepositoryConnectionManager connectionManager;
	private final Logger logger = Logger.getLogger(ElmoDatasetDao.class);
	
	@Autowired
	public ElmoDatasetDao(RepositoryConnectionManager rcm,Properties properties) {
		this.connectionManager = rcm;
		super.setManager(connectionManager.getManager());
		super.setProperties(properties);
	}

	@Override
	public Iterable<Dataset> getAll() {
		return super.getAll();

	}

	public Dataset newDataset(String title) {

		Dataset resp = super.newElement("Dataset:" + title);

		return resp;

	}

	public Dataset getById(QName qName) {
		ElmoManager mimang = connectionManager.getManager();
		return (Dataset) mimang.find(qName);
	}

	public List<Dataset> getSome() {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("PREFIX dc: <http://www.w3.org/ns/dc#> SELECT ?a WHERE { ?a dc:modified ?b } ORDER BY DESC(?a) LIMIT 3");

		return query.getResultList();
	}

	public List<Dataset> getLastestFromPublisherName(String name) {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("PREFIX foaf: <http://www.w3.org/ns/foaf#> SELECT ?a WHERE { ?b foaf:name \""
						+ name + "\". ?a foaf:publisher ?b }");

		return query.getResultList();
	}

	public List<Dataset> getRecentlyModified() {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("PREFIX dc: <http://www.w3.org/ns/dc#> SELECT ?a WHERE { ?a dc:modified ?b } ORDER BY DESC(?a) LIMIT 5");

		return query.getResultList();
	}

	public List<Dataset> getFromKeyword(String keyword, int qty) {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("PREFIX dcat: <http://www.w3.org/ns/dcat#> SELECT ?a WHERE { ?a dcat:keyword \""
						+ keyword + "\" }  LIMIT " + qty);
		return query.getResultList();
	}

	public List<Dataset> getAllByPublisher(String publisher) {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("PREFIX foaf: <http://www.w3.org/ns/foaf#> SELECT ?a WHERE { ?b foaf:name \""
						+ publisher + "\". ?a foaf:publisher ?b }");

		return query.getResultList();
	}

	public int getQty() {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("SELECT (COUNT (?s) AS ?count) WHERE {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/dcat#Dataset>}");

		return Integer.valueOf(query.getSingleResult().toString()) ;
	}

	public Integer getFromLocation(String string) {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("SELECT (COUNT (?a) as ?count) WHERE { ?a <http://www.w3.org/ns/argendata#location> \""
						+ string + "\" } ");
		return Integer.valueOf(query.getSingleResult().toString()) ;
	}

}

//CONSTRUCT * FROM  {Dataset} p {x}, {Dataset} <http://www.w3.org/ns/foaf#publisher>{<http://www.argendata.com/data/Organization:MECON>}
