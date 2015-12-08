package argendata.model;

public class OrderType {

	private String name;
	private String destURL;
	private String showName;
	
	public OrderType(){
		
	}
	
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDestURL() {
		return destURL;
	}
	public void setDestURL(String destURL) {
		this.destURL = destURL;
	}
	
	
}
