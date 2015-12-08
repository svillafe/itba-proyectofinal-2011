package argendata.dao;


public interface GenericDao<T> {

	/**
	 * @param obj Stores an object of type T
	 * @return The stored object.
	 */
	public T store(T obj);

	/**
	 * @param qName QualifiedName of the object to fetch.
	 * @return the fetched object
	 */
	public T getById(Object qName);

	/**
	 * @param obj Object to delete
	 */
	public void delete(T obj);

	/**
	 * @return all elements of type T.
	 */
	public Iterable<T> getAll();
}
