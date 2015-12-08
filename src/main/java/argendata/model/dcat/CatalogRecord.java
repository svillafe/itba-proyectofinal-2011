package argendata.model.dcat;

import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/ns/dcat#CatalogRecord")
public class CatalogRecord {

	@rdf("http://www.w3.org/ns/dcat#modified")
	private String modified;
	@rdf("http://www.w3.org/ns/dcat#issued")
	private String issued;
	@rdf("http://www.w3.org/ns/dcat#Dataset")
	private Dataset primaryTopic;
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getIssued() {
		return issued;
	}
	public void setIssued(String issued) {
		this.issued = issued;
	}
	public Dataset getPrimaryTopic() {
		return primaryTopic;
	}
	public void setPrimaryTopic(Dataset primaryTopic) {
		this.primaryTopic = primaryTopic;
	}
	@Override
	public String toString() {
		return "CatalogRecord [modified=" + modified + ", issued=" + issued
				+ ", primaryTopic=" + primaryTopic + "]";
	}
	
	
}
