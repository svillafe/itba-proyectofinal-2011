package argendata.model;

import java.util.List;
import argendata.model.relational.App;

public class FacetAppResponse {
	private List<App> apps;
	private List<FacetCount> keywords;
	
	public FacetAppResponse(List<App> apps, List<FacetCount> keywords){
		this.apps=apps;
		this.keywords = keywords;
	}
	
	public int size(){
		return apps.size();
	}

	public List<App> getApps() {
		return apps;
	}

	public List<FacetCount> getKeywords() {
		return keywords;
	}
	
}
