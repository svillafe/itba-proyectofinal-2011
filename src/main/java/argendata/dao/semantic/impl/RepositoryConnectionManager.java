package argendata.dao.semantic.impl;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.openrdf.elmo.ElmoManager;
import org.openrdf.elmo.ElmoModule;
import org.openrdf.elmo.sesame.SesameManagerFactory;
import org.openrdf.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import argendata.util.Properties;

@org.springframework.stereotype.Repository
public class RepositoryConnectionManager {

	private SesameManagerFactory sesameFactory;
	private Properties properties;
	private final Logger logger = Logger.getLogger(RepositoryConnectionManager.class);

	@Autowired
	public RepositoryConnectionManager(Properties properties) {
		this.properties = properties;
		init();
	}

	private void init() {
		ElmoModule module = new ElmoModule();

		try {
			sesameFactory = new SesameManagerFactory(module, new URL(
					properties.getSesameServer()),
					properties.getSesameRepositoryName());

		} catch (MalformedURLException e1) {
			logger.error("Error al inicializar el repositorio de Sesame");
			e1.printStackTrace();
		}

		logger.info("Repositorio inicializado--------------------------------------------");
	}

	public ElmoManager getManager() {
		ElmoManager manager;
		manager = sesameFactory.createElmoManager();
		return manager;
	}

	public Repository getRepository() {
		return sesameFactory.getRepository();
	}
	
	
	public SolrServer getSolrServer() throws MalformedURLException{
		return new CommonsHttpSolrServer(this.properties.getSolrServer());
	}
	

}
