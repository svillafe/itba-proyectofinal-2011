package argendata.model;

public class FacetPair {

	private String name;
	private int quantity;
	private String destURL;
	private String label;
	
	public FacetPair(String name, int count, String destURL, String label) {
		this.name = name;
		this.quantity = count;
		this.destURL = destURL;
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getDestURL() {
		return destURL;
	}
	public void setDestURL(String destURL) {
		this.destURL = destURL;
	}
	
	
}
