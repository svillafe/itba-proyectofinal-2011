/*package argendata.service.relational.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.relational.PlainDatasetDao;
import argendata.model.relational.PlainDataset;
import argendata.service.relational.PlainDatasetService;

@Service
public class PlainDatasetServiceImpl implements PlainDatasetService {

	private PlainDatasetDao plainDatasetDao;

	@Autowired
	public PlainDatasetServiceImpl(PlainDatasetDao myDao) {

		this.plainDatasetDao = myDao;
	}

	public boolean add(PlainDataset dataset) {
		return this.plainDatasetDao.add(dataset);
	}

	public List<PlainDataset> getAllDatasets()  {
		List<PlainDataset> response = new ArrayList<PlainDataset>();
		Iterator<PlainDataset> myIterator = plainDatasetDao.getAll()
				.iterator();

		while (myIterator.hasNext()) {
			response.add(myIterator.next());
		}

		return response;
	}

	public void updateDataset(PlainDataset dataset) {
		plainDatasetDao.update(dataset);
	}

	public List<PlainDataset> getWaitingForApproval() throws Exception {
		
		return getAllDatasets();
	}

	public PlainDataset getById(int id) {
		
		return plainDatasetDao.getById(id);
	}

	public void delete(PlainDataset plain) {
		this.plainDatasetDao.delete(plain);
	}


	@Override
	public PlainDataset ayncGetPlainDataset(String title) {
		return this.plainDatasetDao.ayncGetPlainDataset(title);
	}

	@Override
	public void asyncUpdateDataset(PlainDataset aDataset) {
		this.plainDatasetDao.asyncUpdateDataset(aDataset);
	}

	@Override
	public boolean exist(String title) {
		return this.plainDatasetDao.exist(title);
	}

}
*/