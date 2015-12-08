package argendata.service;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import argendata.model.dcat.Dataset;
import argendata.model.relational.App;
import argendata.model.relational.ArgendataUser;
import argendata.service.relational.ArgendataUserService;
import argendata.utils.AppTestFactory;
import argendata.utils.ArgendataUserTestFactory;
import argendata.utils.DatasetTestFactory;

@RunWith(SpringJUnit4ClassRunner.class)
public class AppServiceImplTest extends AbstractServiceTests {

	@Autowired
	private AppService appService;
	@Autowired
	private DatasetService datasetService;
	@Autowired
	private ArgendataUserService userService;

	static Set<Dataset> approvedDatasets;
	static ArgendataUser publisher;

	
	@Before
	public void init() {

		System.out.println("Executing the init method for a new test...");
		approvedDatasets = new HashSet<Dataset>();
		publisher = ArgendataUserTestFactory.getActivatedUser();
		try {
			userService.registerUser(publisher);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		for (int i = 0; i < 3; i++) {
			Dataset aux = DatasetTestFactory.getDataset();
			try {
				datasetService.store(aux);
				approvedDatasets.add(aux);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		hibernateSessionFactory.getCurrentSession().getTransaction().commit();
		
		elmoManager.getTransaction().commit();
		
		
	}

	@After
	public void destroyed() {
		
		System.out.println("Executing the destroy method....");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		try {
			for (Dataset a : approvedDatasets) {
				Dataset b = datasetService.getApprovedDatasetByName(a.getTitle());
				datasetService.delete(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		userService.deleteUser(publisher);
		
	}

	@Test
	public void approvedAppStoreTest() {
		
		System.out.println("Executing the approvedAppstoreTest...");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		hibernateSessionFactory.getCurrentSession().flush();
		
		App myApp = AppTestFactory.getApprovedApp(approvedDatasets, publisher);
		
		try {
			String name;
			appService.approvedAppStore(myApp);
			elmoManager.getTransaction().begin();
			name = myApp.getName();
			App retrieveApp = appService.getApprovedAppByName(name);
			Assert.assertEquals(myApp, retrieveApp);
			appService.approvedAppDelete(myApp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void preAppStoreTest() {
		System.out.println("Executing the preAppstoreTest...");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		hibernateSessionFactory.getCurrentSession().flush();
		elmoManager.getTransaction().begin();
		App myApp = AppTestFactory.getPreApp(approvedDatasets, publisher);
		appService.preAppStore(myApp);
		String name = myApp.getNameId();
		App retrieveApp = appService.getPreAppByNameId(name);
		appService.preAppDelete(myApp);
		hibernateSessionFactory.getCurrentSession().getTransaction().commit();
		Assert.assertEquals(myApp, retrieveApp);
	}

	@Test
	public void approvedAppDeleteTest() {
		System.out.println("Executing the approvedDeleteTest...");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		hibernateSessionFactory.getCurrentSession().flush();
		elmoManager.getTransaction().begin();
		App myApp = AppTestFactory.getApprovedApp(approvedDatasets, publisher);
		String name = myApp.getName();
		try {
			appService.approvedAppStore(myApp);
			appService.approvedAppDelete(myApp);
			Assert.assertFalse(appService.exist(name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void preAppDeleteTest() {
		System.out.println("Executing the preAppDeleteTest...");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		hibernateSessionFactory.getCurrentSession().flush();
		elmoManager.getTransaction().begin();
		App myApp = AppTestFactory.getPreApp(approvedDatasets, publisher);
		String name = myApp.getName();
		appService.preAppStore(myApp);
		appService.preAppDelete(myApp);
		Assert.assertFalse(appService.exist(name));
	}

	@Test
	public void approvedAppEditTest() {
		System.out.println("Executing the approvedAppEditTest...");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		hibernateSessionFactory.getCurrentSession().flush();
		elmoManager.getTransaction().begin();
		App myApp = AppTestFactory.getApprovedApp(approvedDatasets, publisher);
		String name = myApp.getName();
		try {
			appService.approvedAppStore(myApp);
			myApp.setName("Ejemplo");
			myApp.setNameId("Ejemplo");
			App retrieved = appService.getApprovedAppByName(name);
			appService.approvedAppUpdate(retrieved,myApp);
			
			App retrivedApp = appService.getApprovedAppByName(myApp.getName());
			
			Assert.assertEquals(myApp, retrivedApp);
			appService.approvedAppDelete(retrivedApp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Test
	public void preAppEditTest() {
		System.out.println("Executing the preAppEditTest...");
		hibernateSessionFactory.getCurrentSession().beginTransaction();
		hibernateSessionFactory.getCurrentSession().flush();
		elmoManager.getTransaction().begin();
		App myApp = AppTestFactory.getPreApp(approvedDatasets, publisher);

		appService.preAppStore(myApp);
		myApp.setName("Ejemplo");
		appService.preAppUpdate(myApp);
		App retrivedApp = appService.getPreAppByNameId(myApp.getNameId());
		
		appService.preAppDelete(myApp);
		Assert.assertEquals(myApp, retrivedApp);

	}

}
