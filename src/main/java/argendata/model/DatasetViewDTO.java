package argendata.model;

import argendata.model.dcat.Dataset;

public class DatasetViewDTO {

	private Dataset dataset;
	private boolean isDoc;
	
	public DatasetViewDTO(Dataset dataset, boolean isDoc) {
		super();
		this.dataset = dataset;
		this.isDoc = isDoc;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}

	public boolean isDoc() {
		return isDoc;
	}
	
	public boolean getIsDoc() {
		return isDoc;
	}

	public void setDoc(boolean isDoc) {
		this.isDoc = isDoc;
	}
	
	
}
