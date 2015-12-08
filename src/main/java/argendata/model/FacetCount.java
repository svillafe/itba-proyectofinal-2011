package argendata.model;


public class FacetCount {

	private String name;
	private long count;
	
	public FacetCount(String name, long l) {
		this.name=name;
		this.count=l;
	}

	public String getName() {
		return name;
	}

	public long getCount() {
		return count;
	}

	
}
