package argendata.dao.relational;

import argendata.dao.GenericDao;
import argendata.model.relational.PreDataset;

public interface PreDatasetDao extends GenericDao<PreDataset> {

	/**
	 * @param dataset A preDataset to store.
	 * @return true or false if the operation ends successfully.
	 */
	public boolean add(PreDataset dataset);

	/**
	 * @param dataset The dataset to be updated in the store.
	 */
	public void update(PreDataset dataset);

	/**
	 * @param title The title of the plain dataset.
	 * @return A plain dataset.
	 */
	public PreDataset ayncGetPreDataset(String title);

	/**
	 * @param aDataset The dataset to update.
	 */
	public void asyncUpdateDataset(PreDataset aDataset);

	/**
	 * @param title A dataset title.
	 * @return true or false if a predataset with that title exists or not.
	 */
	public boolean exist(String title);
	
	
}
