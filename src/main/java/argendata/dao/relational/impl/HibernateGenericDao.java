package argendata.dao.relational.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import argendata.dao.GenericDao;

public class HibernateGenericDao<T> implements GenericDao<T> {
	private Class<T> domainClass = getDomainClass();
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass()
					.getGenericSuperclass();
			domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	public T store(T obj) {
		Session session = getSession();
		session.save(obj);
		session.flush();
		return obj;
	}
	
	public void update(T obj){
		Session session = getSession();
		session.update(obj);
		session.flush();
		
	}

	public T getById(Object qName) {
		Session session = getSession();
		T result = (T) session.get(domainClass, (Integer)qName);
		return result;
	}

	public void delete(T obj) {
		Session session = getSession();
		session.delete(obj);
		session.flush();
	}

	public Iterable<T> getAll() {
		Session session = getSession();
		List<T> result = (List<T>) session.createQuery(
				"from " + domainClass.getSimpleName()).list();
		return result;
	}
}
