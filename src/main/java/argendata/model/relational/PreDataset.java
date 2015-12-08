package argendata.model.relational;

import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import argendata.model.base.PersistantEntity;
import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.model.foaf.Organization;
import argendata.util.Parsing;

@Entity
@Table(name = "PreDataset")
public class PreDataset extends PersistantEntity {

	private String title;

	@Column(length=600)
	private String description;

	private String license;

	private String keyword;

	private String dataQuality;

	private String modified;

	private String spatial;

	private String temporal;

	private String publisher;

	private String theme;

	private String distribution;

	private String accessURL;

	private int size;

	private String format;

	private String username;

	private boolean approved;

	private String decision;

	private String location;

	private String titleId;

	public String getTitleId() {
		return titleId;
	}

	public String getAccessURL() {
		return accessURL;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setAccessURL(String accessURL) {
		this.accessURL = accessURL;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

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

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		PreDataset other = (PreDataset) obj;
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

	public Dataset toNoRelationalDataset() {

		Organization miPublisher = new Organization();
		miPublisher.setName(publisher);
		miPublisher.setNameId(Parsing.withoutSpecialCharacters(publisher));

		Distribution miDistribution;

		if (distribution.equals("Feed")) {
			miDistribution = new Feed();
		} else if (distribution.equals("Download")) {
			miDistribution = new Download();
		} else {
			miDistribution = new WebService();
		}

		miDistribution.setAccessURL(accessURL);
		miDistribution.setFormat(format);
		miDistribution.setSize(size);

		Dataset ds = new Dataset();
		ds.setTitle(title);
		ds.setDescription(description);
		ds.setDataQuality(dataQuality);
		ds.setDistribution(miDistribution);
		ds.setTitleId(this.titleId);

		Set<String> set = new TreeSet<String>();
		keyword = keyword.trim();

		Scanner scanner = new Scanner(keyword);
		scanner.useDelimiter(",");
		String aKeyword;
		while (scanner.hasNext()) {
			aKeyword = scanner.next();
			set.add(aKeyword);
		}

		ds.setKeyword(set);

		ds.setLicense(license);
		ds.setModified(modified);
		ds.setPublisher(miPublisher);
		ds.setSpatial(spatial);
		ds.setTemporal(temporal);
		ds.setTitle(title);
		ds.setLocation(location);
		
		return ds;
	}

	public void setTitleId(String titleId) {
		this.titleId = titleId;

	}

}
