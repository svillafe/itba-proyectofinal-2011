package argendata.dao.relational.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.relational.AppDao;
import argendata.model.relational.App;
import argendata.model.relational.RelationalApp;

@Repository
public class HibernateAppDao extends HibernateGenericDao<App> implements AppDao {
	@Autowired
	public HibernateAppDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<App> getNotAllowed() {
		Query query = getSession().createQuery(" from App where isAllowed = ?");
		query.setBoolean(0, false);
		List<App> result = (List<App>) query.list();
		return result;
	}

	public List<App> getAppsByUser(Integer id) {
		Query query = getSession().createQuery(" from App where publisher = ?");
		query.setInteger(0, id);
		List<App> result = (List<App>) query.list();
		return result;
	}

	public App getByQname(String qName) {

		Session session = getSession();
		String nombres [] = qName.split(":");
		
		
		
		Query query = session.createQuery(" from App where nameid = ?");
		query.setParameter(0, nombres[1]);
		App result = (App) query.uniqueResult();

		return result;
	}

	public App getByNameID(String name) {
		Session session = getSession();
			
		Query query = session.createQuery(" from App where nameid = ?");
		query.setParameter(0, name);
		App result = (App) query.uniqueResult();

		return result;
	}

	@Override
	public boolean exist(String nameId) {
	
		App a = this.getByNameId(nameId);
		
		return !(a==null);
	}

	private App getByNameId(String nameId) {
		Session session = getSession();
		
		Query query = session.createQuery(" from App where nameid = ?");
		query.setParameter(0, nameId);
		App result = (App) query.uniqueResult();

		return result;
	}

	@Override
	public List<App> getRelatedApp(String qName) {
		Query query = getSession().createQuery(" from App as app where ? in elements(app.dataset)");
		query.setString(0, qName);
		List<App> result = (List<App>) query.list();
		return result;
	}

}
