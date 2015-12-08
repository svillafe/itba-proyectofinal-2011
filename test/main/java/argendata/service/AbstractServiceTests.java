package argendata.service;

import java.net.MalformedURLException;

import org.apache.solr.client.solrj.SolrServer;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.openrdf.elmo.ElmoManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import argendata.dao.semantic.impl.RepositoryConnectionManager;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = { "classpath:testContext.xml" })
public abstract class AbstractServiceTests extends
		AbstractJUnit4SpringContextTests {

	@Autowired
	private RepositoryConnectionManager rcm;

	private SolrServer solrServer;
	
	@Autowired
	SessionFactory hibernateSessionFactory;
	ElmoManager elmoManager;

	@Before
	public void before() {
		System.out.println("Abriendo transacciones....");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		
		try {
			solrServer = rcm.getSolrServer();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		elmoManager = rcm.getManager();
		elmoManager.getTransaction().begin();
	}

	@After
	public void after() {
		
		hibernateSessionFactory.getCurrentSession().getTransaction().rollback();
		try {
			solrServer.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
		elmoManager.getTransaction().rollback();

	}

}
