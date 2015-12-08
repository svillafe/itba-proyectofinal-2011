package argendata.model;

import java.util.List;
import java.util.Map;

import argendata.model.dcat.Dataset;

public class FacetDatasetResponse {

	private List<Dataset> datasets;
	private Map<String,List<FacetCount>> fields;
	
	
	
	public FacetDatasetResponse(List<Dataset> datasets,
			Map<String, List<FacetCount>> fields) {
		super();
		this.datasets = datasets;
		this.fields = fields;
	}

	public List<FacetCount> getFacetCountValues(String string) {
		return fields.get(string);
	}
	
	public List<Dataset> getDatasets(){
		return datasets;
	}

	public int size() {
		return datasets.size();
	}

}
