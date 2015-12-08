package argendata.model;


public class ListDatasetAppViewDTO {

	private String title;
	private String titleId;

	public ListDatasetAppViewDTO(String title, String titleId) {
		super();
		this.title = title;
		this.titleId = titleId;
	}

	public final String getTitle() {
		return title;
	}

	public final void setTitle(String title) {
		this.title = title;
	}

	public final String getTitleId() {
		return titleId;
	}

	public final void setTitleId(String titleId) {
		this.titleId = titleId;
	}
}
