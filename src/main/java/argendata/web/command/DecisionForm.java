package argendata.web.command;


public class DecisionForm {

	private boolean approved;
	private String decision;
	private int id;
	
	public DecisionForm(){
		
	}
	
	public DecisionForm(int datasetId){
		this.approved = true;
		this.id = datasetId;
	}
	
	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	
	public int getId() {
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
}
