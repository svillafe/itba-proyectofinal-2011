package argendata.dao.relational.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.relational.RelationalAppDao;
import argendata.model.relational.RelationalApp;

@Repository
public class HibernateRelationalAppDao extends HibernateGenericDao<RelationalApp>
		implements RelationalAppDao {

	@Autowired
	public HibernateRelationalAppDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public RelationalApp getRelationalApp(String qName) {
		Session session = getSession();
		Query query = session.createQuery(" from RelationalApp where qname = ?");
		query.setParameter(0, qName);
		RelationalApp result = (RelationalApp) query.uniqueResult();

		return result;
	}

	public List<RelationalApp> getAllowedApps() {
		Session session = getSession();
		Query query = session.createQuery(" from RelationalApp where isallowed = ?");
		query.setParameter(0, true);
		List<RelationalApp> result = (List<RelationalApp>) query.list();

		return result;
	}

	public List<RelationalApp> getAppsByUser(Integer id) {
		Session session = getSession();
		Query query = session.createQuery(" from RelationalApp where publisher = ?");
		query.setInteger(0, id);
		List<RelationalApp> result = (List<RelationalApp>) query.list();

		return result;
	}

}
