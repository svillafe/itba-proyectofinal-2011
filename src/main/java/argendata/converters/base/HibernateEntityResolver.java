package argendata.converters.base;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import argendata.model.base.PersistantEntity;

@Component
public class HibernateEntityResolver implements EntityResolver {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public <T extends PersistantEntity> T find(int id, Class<T> clazz) {
		Session session = getSession();
		T result = (T) session.get(clazz, id);
		return result;
	}

}
