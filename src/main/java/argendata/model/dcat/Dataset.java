package argendata.model.dcat;

import java.util.Set;
import java.util.TreeSet;

import javax.xml.namespace.QName;

import org.apache.solr.client.solrj.beans.Field;
import org.openrdf.elmo.annotations.rdf;

import argendata.model.foaf.Organization;
import argendata.model.skos.Concept;
import argendata.util.Parsing;

@rdf("http://www.w3.org/ns/dcat#Dataset")
public class Dataset implements Comparable<Dataset> {

	@Field("type")
	private String type;

	@Field("title")
	@rdf("http://www.w3.org/ns/dc#title")
	private String title;

	@Field("titleId")
	@rdf("http://www.w3.org/ns/dc#titleId")
	private String titleId;

	@Field("description")
	@rdf("http://www.w3.org/ns/dc#description")
	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Field("license")
	@rdf("http://www.w3.org/ns/dc#license")
	private String license;

	@Field("keyword")
	@rdf("http://www.w3.org/ns/dcat#keyword")
	private Set<String> keyword;

	@Field("dataQuality")
	@rdf("http://www.w3.org/ns/dcat#dataQuality")
	private String dataQuality;

	@Field("modified")
	@rdf("http://www.w3.org/ns/dc#modified")
	private String modified;

	@Field("spatial")
	@rdf("http://www.w3.org/ns/dc#spatial")
	private String spatial;

	@Field("temporal")
	@rdf("http://www.w3.org/ns/dc#temporal")
	private String temporal;

	@Field("location")
	@rdf("http://www.w3.org/ns/argendata#location")
	private String location;

	@rdf("http://www.w3.org/ns/foaf#publisher")
	private Organization publisher;

	@rdf("http://www.w3.org/ns/skos#theme")
	private Concept theme;

	@rdf("http://www.w3.org/ns/dcat#distribution")
	private Distribution distribution;

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Field("publisher")
	public void setPublisherName(String name) {
		if (publisher == null) {
			publisher = new Organization();
		}
		publisher.setName(name);
		publisher.setNameId(Parsing.withoutSpecialCharacters(name));
	}

	public String getPublisherName() {
		return this.publisher.getName();
	}

	@Field("resourceType")
	public void setResourceType(String type) {
		if (distribution == null) {
			if (type.equals("WebService")) {
				distribution = new WebService();
			} else if (type.equals("Feed")) {
				distribution = new Feed();
			} else if (type.equals("Download")) {
				distribution = new Download();
			}
		} else {
			
		}
	}

	public String getResourceType() {
		if (distribution instanceof WebService) {
			return "WebService";
		} else if (distribution instanceof Feed) {
			return "Feed";
		} else {
			return "Download";
		}
	}

	@Field("accessURL")
	public void setAccessURL(String url) {
		// PUEDE EXPLOTAR ESTO SI distribution no está seteado.... OJO!
		// no puedo instanciar distribution porque no se el tipo... UU
		distribution.setAccessURL(url);
	}

	public String getAccessURL() {
		return distribution.getAccessURL();
	}

	@Field("format")
	public void setFormat(String format) {
		distribution.setFormat(format);
	}

	public String getFormat() {
		return distribution.getFormat();
	}

	@Field("size")
	public void setSize(int size) {
		distribution.setSize(size);
	}

	public int getSize() {
		return distribution.getSize();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Set<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(Set<String> keywords) {
		this.keyword.addAll(keywords);
	}

	/*
	 * public String getGranularity() { return granularity; }
	 */

	/*
	 * public void setGranularity(String granularity) { this.granularity =
	 * granularity; }
	 */

	public String getDataQuality() {
		return dataQuality;
	}

	public void setDataQuality(String dataQuality) {
		this.dataQuality = dataQuality;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getSpatial() {
		return spatial;
	}

	public void setSpatial(String spatial) {
		this.spatial = spatial;
	}

	public String getTemporal() {
		return temporal;
	}

	public void setTemporal(String temporal) {
		this.temporal = temporal;
	}

	public Organization getPublisher() {
		return publisher;
	}

	public void setPublisher(Organization publisher) {
		this.publisher = publisher;
	}

	public Distribution getDistribution() {
		return distribution;
	}

	public String getTitleId() {
		return titleId;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;
	}

	public void setDistribution(Distribution distribution) {
		this.distribution = distribution;
	}

	public Dataset() {
		super();
		this.type = "dataset";
		this.location = "desconocida";
		this.keyword = new TreeSet<String>();
	}

	public Dataset(String title, String license, Set<String> keyword,
			String dataQuality, String modified, String spatial,
			String temporal, String location, String titleId) {
		super();
		this.type = "dataset";
		this.title = title;
		this.license = license;
		this.keyword = new TreeSet<String>();
		this.keyword.addAll(keyword);
		this.dataQuality = dataQuality;
		this.modified = modified;
		this.spatial = spatial;
		this.temporal = temporal;
		this.location = location;
		this.titleId = titleId;
	}

	public Dataset(String title, String description, String license,
			Set<String> keyword, String dataQuality, String modified,
			String spatial, String temporal, String location,
			Organization publisher, Distribution distribution, String titleId) {
		super();
		this.type = "dataset";
		this.title = title;
		this.license = license;
		this.keyword = new TreeSet<String>();
		this.keyword.addAll(keyword);
		this.dataQuality = dataQuality;
		this.modified = modified;
		this.spatial = spatial;
		this.temporal = temporal;
		this.publisher = publisher;
		this.distribution = distribution;
		this.location = location;
		this.description = description;
		this.titleId = titleId;
	}

	public Dataset(String title, String license, Set<String> keyword,
			String dataQuality, String modified, String spatial,
			String temporal, String location, String publisher,
			String distribution, String titleId) {
		super();
		this.type = "dataset";
		this.title = title;
		this.license = license;
		this.keyword = new TreeSet<String>();
		this.keyword.addAll(keyword);
		this.dataQuality = dataQuality;
		this.modified = modified;
		this.spatial = spatial;
		this.temporal = temporal;
		this.setPublisherName(publisher);
		this.setResourceType(distribution);
		this.location = location;
		this.titleId = titleId;
	}

	public QName getQName() {
		return new QName("http://www.argendata.com/data/", "Dataset:" + title);
	}

	@Override
	public String toString() {
		return "Dataset [title=" + title + ", license=" + license
				+ ", keyword=" + keyword + "," + ", dataQuality=" + dataQuality
				+ ", modified=" + modified + ", spatial=" + spatial
				+ ", temporal=" + temporal + ", publisher=" + publisher
				+ ", location=" + location + ", distribution=" + distribution
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataQuality == null) ? 0 : dataQuality.hashCode());
		result = prime * result
				+ ((distribution == null) ? 0 : distribution.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((license == null) ? 0 : license.hashCode());
		result = prime * result
				+ ((modified == null) ? 0 : modified.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((spatial == null) ? 0 : spatial.hashCode());
		result = prime * result
				+ ((temporal == null) ? 0 : temporal.hashCode());
		result = prime * result + ((theme == null) ? 0 : theme.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dataset other = (Dataset) obj;
		if (dataQuality == null) {
			if (other.dataQuality != null)
				return false;
		} else if (!dataQuality.equals(other.dataQuality))
			return false;
		if (distribution == null) {
			if (other.distribution != null)
				return false;
		} else if (!distribution.equals(other.distribution))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (license == null) {
			if (other.license != null)
				return false;
		} else if (!license.equals(other.license))
			return false;
		if (modified == null) {
			if (other.modified != null)
				return false;
		} else if (!modified.equals(other.modified))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (spatial == null) {
			if (other.spatial != null)
				return false;
		} else if (!spatial.equals(other.spatial))
			return false;
		if (temporal == null) {
			if (other.temporal != null)
				return false;
		} else if (!temporal.equals(other.temporal))
			return false;
		if (theme == null) {
			if (other.theme != null)
				return false;
		} else if (!theme.equals(other.theme))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(Dataset o) {
		return this.title.compareTo(o.getTitle());
	}

}
