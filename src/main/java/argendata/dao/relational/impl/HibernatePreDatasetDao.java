package argendata.dao.relational.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.relational.PreDatasetDao;
import argendata.model.relational.RelationalApp;
import argendata.model.relational.PreDataset;
import argendata.util.Parsing;

@Repository
public class HibernatePreDatasetDao extends HibernateGenericDao<PreDataset>
		implements PreDatasetDao {

	SessionFactory sessionFactory;

	@Autowired
	public HibernatePreDatasetDao(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		super.setSessionFactory(sessionFactory);
	}

	public boolean add(PreDataset dataset) {
		return store(dataset) != null;
	}

	@Override
	public void update(PreDataset dataset) {
		store(dataset);
	}

	@Override
	public PreDataset ayncGetPreDataset(String title) {

		sessionFactory.getCurrentSession().beginTransaction();

		Session session = getSession();

		Query query = session
				.createQuery(" from PreDataset where title = ? ");
		query.setParameter(0, title);

		PreDataset result = (PreDataset) query.uniqueResult();

		sessionFactory.getCurrentSession().getTransaction().commit();

		return result;
	}

	@Override
	public void asyncUpdateDataset(PreDataset aDataset) {
		Session session = getSession();
		session.beginTransaction();

		session.update(aDataset);
		session.flush();
		session.getTransaction().commit();
	}

	@Override
	public boolean exist(String title) {
		sessionFactory.getCurrentSession().beginTransaction();

		Session session = getSession();

		Query query = session
				.createQuery(" from PreDataset where titleId = ? ");
		query.setParameter(0, Parsing.withoutSpecialCharacters(title));

		PreDataset result = (PreDataset) query.uniqueResult();

		return !(result==null);
	}

}
