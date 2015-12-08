package argendata.model.dcat;

import org.openrdf.elmo.annotations.rdf;

import argendata.model.foaf.Organization;
import argendata.model.skos.ConceptScheme;


@rdf("http://www.w3.org/ns/dcat#Catalog")
public class Catalog {

	@rdf("http://www.w3.org/ns/dcat#spatial")
	private String spatial;
	@rdf("http://www.w3.org/ns/dcat#modified")
	private String modified;
	@rdf("http://www.w3.org/ns/dcat#created")
	private String created;
	
	@rdf("http://www.w3.org/2004/02/skos/core#ConceptScheme")
	private ConceptScheme themeTaxonomy;
	@rdf("http://www.w3.org/ns/dcat#Dataset")
	private Dataset dataset;
	@rdf("http://www.w3.org/ns/dcat#CatalogRecord")
	private CatalogRecord record;
	@rdf("http://www.w3.org/ns/foaf#Organization")
	private Organization publisher;
	
	public String getSpatial() {
		return spatial;
	}
	public void setSpatial(String spatial) {
		this.spatial = spatial;
	}
	public String getModified() {
		return modified;
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public ConceptScheme getThemeTaxonomy() {
		return themeTaxonomy;
	}
	public void setThemeTaxonomy(ConceptScheme themeTaxonomy) {
		this.themeTaxonomy = themeTaxonomy;
	}
	public Dataset getDataset() {
		return dataset;
	}
	public void setDataset(Dataset dataset) {
		this.dataset = dataset;
	}
	public CatalogRecord getRecord() {
		return record;
	}
	public void setRecord(CatalogRecord record) {
		this.record = record;
	}
	public Organization getPublisher() {
		return publisher;
	}
	public void setPublisher(Organization publisher) {
		this.publisher = publisher;
	}
	@Override
	public String toString() {
		return "Catalog [spatial=" + spatial + ", modified=" + modified
				+ ", created=" + created + ", themeTaxonomy=" + themeTaxonomy
				+ ", dataset=" + dataset + ", record=" + record
				+ ", publisher=" + publisher + "]";
	}
	
	
}
