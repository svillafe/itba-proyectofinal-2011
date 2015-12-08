package argendata.dao.semantic.impl;

import java.lang.reflect.ParameterizedType;

import javax.xml.namespace.QName;

import org.openrdf.elmo.ElmoManager;

import argendata.dao.GenericDao;
import argendata.util.Properties;

public class ElmoGenericDao<T> implements GenericDao<T> {

	private ElmoManager manager;
	private Class<T> domainClass = getDomainClass();
	private Properties properties;

	@SuppressWarnings("unchecked")
	protected Class<T> getDomainClass() {
		if (domainClass == null) {
			ParameterizedType thisType = (ParameterizedType) getClass()
					.getGenericSuperclass();
			domainClass = (Class<T>) thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	public synchronized T store(T obj) {

		while (manager.getTransaction().isActive()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		manager.getTransaction().begin();
		manager.persist(obj);
		manager.getTransaction().commit();

		return null;
	}

	protected T newElement(String title) {
		
		while (manager.getTransaction().isActive()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		manager.getTransaction().begin();
		
		QName id = new QName(properties.getNamespace(), title);
		T objeto = manager.designate(id, getDomainClass());
		
		manager.getTransaction().commit();
		
		return objeto;
	}

	protected <E> E newElement(String title, Class<E> miclase) {
		
		while (manager.getTransaction().isActive()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		manager.getTransaction().begin();
		
		QName id = new QName(properties.getNamespace(), title);
		E objeto = manager.designate(id, miclase);
		
		manager.getTransaction().commit();
		return objeto;
	}

	public void delete(T obj) {
		
		while (manager.getTransaction().isActive()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		manager.getTransaction().begin();
		manager.remove(obj);
		manager.getTransaction().commit();

	}

	public void setManager(ElmoManager manager) {
		this.manager = manager;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getProperties() {
		return this.properties;
	}

	public Iterable<T> getAll() {

		Iterable<T> data = (Iterable<T>) manager.findAll(domainClass);

		return data;
	}

	public T getById(Object qName) {
		return (T) manager.find((QName) qName);
	}

}
