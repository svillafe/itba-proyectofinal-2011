package argendata.dao.semantic.impl;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.ElmoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import argendata.converters.AppConverter;
import argendata.dao.semantic.SemanticAppDao;
import argendata.model.argendata.Semanticapp;
import argendata.model.relational.RelationalApp;
import argendata.util.Properties;

@Component
public class ElmoSemanticAppDao extends ElmoGenericDao<Semanticapp> implements
		SemanticAppDao {

	private RepositoryConnectionManager connectionManager;
	private final Logger logger = Logger.getLogger(ElmoSemanticAppDao.class);

	@Autowired
	public ElmoSemanticAppDao(RepositoryConnectionManager rcm,
			Properties properties) {
		this.connectionManager = rcm;
		super.setManager(connectionManager.getManager());
		super.setProperties(properties);
	}

	@Override
	public Iterable<Semanticapp> getAll() {
		return super.getAll();

	}

	public Semanticapp newSemanticApp(String title) {

		logger.debug("Semanticapp:" + title);

		Semanticapp resp = super.newElement("Semanticapp:" + title);

		return resp;

	}

	public Semanticapp getById(QName qName) {
		ElmoManager mimang = connectionManager.getManager();

		return (Semanticapp) mimang.find(qName);

	}

	@Override
	public Semanticapp getByName(String name) {

		ElmoManager mimang = connectionManager.getManager();
		QName QN = new QName(super.getProperties().getNamespace(),
				"Semanticapp:" + name);
		Semanticapp resp;
		try {
			resp = (Semanticapp) mimang.find(QN);
		} catch (ClassCastException e) {
			return null;
		}
		return resp;
	}

	@Override
	public List<Semanticapp> getRelatedApp(String datasetId) {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em

				.createQuery("PREFIX argendata: <http://www.w3.org/ns/argendata#> PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> SELECT ?s  WHERE { ?s rdf:type argendata:Semanticapp. ?s <http://www.w3.org/ns/argendata#dataset> <http://www.argendata.com/data/Dataset:"
						+ datasetId + ">}");
		return query.getResultList();

	}

	@Override
	public List<Semanticapp> getSome() {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("SELECT ?s WHERE { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/argendata#Semanticapp> } LIMIT 3");

		return query.getResultList();
	}

	public int getQty() {
		ElmoManager em = connectionManager.getManager();

		ElmoQuery query = em
				.createQuery("SELECT (COUNT (?s) AS ?count) WHERE {?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://www.w3.org/ns/argendata#Semanticapp> }");

		return Integer.valueOf(query.getSingleResult().toString());
	}

	@Override
	public boolean exist(String name) {

		return !(this.getByName(name) == null);

	}
}
