package argendata.web.command;

public class ReportProblemForm {

	String type;
	String title;
	String problem;
	private String username;

	public ReportProblemForm() {

	}

	public ReportProblemForm(String type, String title,String username) {
		this.title = title;
		this.type = type;
		this.username=username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String build() {
		return "Tipo:"+this.type+"\nNombre:"+this.title+"\nDescripcion:"+this.problem+"\nUsuario:"+this.username+"\n";
		
	}

}
