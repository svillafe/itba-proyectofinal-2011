/*package argendata.service.relational;

import java.util.List;

import argendata.model.relational.PlainDataset;

public interface PlainDatasetService {

	/**
	 * @param dataset A dataset to be stored.
	 * @return true or false if the operation ended successfully.
	 * @throws Exception
	
	public boolean add(PlainDataset dataset) ;

	
	/**
	 * @return A list of all datasets.
	
	public List<PlainDataset> getAllDatasets();

	
	/**
	 * @param dataset Dataset to be update.
	
	public void updateDataset(PlainDataset dataset);

	
	/**
	 * @return A list of plaindatasets that haven't been approved or disapproved yet.
	 * @throws Exception
	
	public List<PlainDataset> getWaitingForApproval() throws Exception;

	/**
	 * @param id The id of the plaindataset.
	 * @return A plain dataset.
	
	public PlainDataset getById(int id);

	/**
	 * @param plain PlainDataset to be deleted
	
	public void delete(PlainDataset plain);

	/**
	 * @param title Title to be fetched.
	 * @return An stored plaindataset for the given title.
	
	public PlainDataset ayncGetPlainDataset(String title);

	/**
	 * @param aDataset A dataset to be updated.
	
	public void asyncUpdateDataset(PlainDataset aDataset);

	/**
	 * @param title
	 * @return True or false if a plainApp with that name already exists.
	
	public boolean exist(String title);

}
*/