package argendata.web.command;

import argendata.model.dcat.Dataset;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.model.relational.PreDataset;
import argendata.util.Parsing;

public class DatasetForm {

	private String title;

	private String description;

	private String license;

	private String keyword;

	private String quality;

	private String location;

	private String modified;

	private String spatial;

	private String temporal;

	private String publisher;

	private String accessURL;

	private int size;

	private String format;

	private String typeDistribution;

	private String username;

	private String oldTitleIdForEdition;

	private int id;

	public DatasetForm(Dataset dataset) {
		oldTitleIdForEdition = dataset.getTitleId();
		title = dataset.getTitle();
		license = dataset.getLicense();

		keyword = "";
		for (String s : dataset.getKeyword()) {
			keyword += s + ",";
		}
		keyword = keyword.substring(0, keyword.length() - 1);
		quality = dataset.getDataQuality();
		modified = dataset.getModified();
		spatial = dataset.getSpatial();
		temporal = dataset.getTemporal();
		publisher = dataset.getPublisher().getName();
		accessURL = dataset.getDistribution().getAccessURL();
		size = dataset.getDistribution().getSize();
		format = dataset.getDistribution().getFormat();
		
		if( dataset.getDistribution() instanceof Feed ){
			typeDistribution = "Feed";
		}else if (dataset.getDistribution() instanceof WebService){
			typeDistribution = "WebService";
		} else if(dataset.getDistribution() instanceof Download){
			typeDistribution = "Download";
		} else {
			typeDistribution = "";
		}
		
		location = dataset.getLocation();
		description = dataset.getDescription();
	}

	public DatasetForm(PreDataset dataset) {
		oldTitleIdForEdition = dataset.getTitleId();
		title = dataset.getTitle();
		license = dataset.getLicense();
		keyword = dataset.getKeyword();
		quality = dataset.getDataQuality();
		modified = dataset.getModified();
		spatial = dataset.getSpatial();
		temporal = dataset.getTemporal();
		publisher = dataset.getPublisher();
		accessURL = dataset.getAccessURL();
		size = dataset.getSize();
		format = dataset.getFormat();
		typeDistribution = dataset.getDistribution();
		id = dataset.getId();
		username = dataset.getUsername();
		location = dataset.getLocation();
		description = dataset.getDescription();

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getOldTitleIdForEdition() {
		return oldTitleIdForEdition;
	}

	public void setOldTitleIdForEdition(String oldTitleForEdition) {
		this.oldTitleIdForEdition = oldTitleForEdition;
	}

	public String getTypeDistribution() {
		return typeDistribution;
	}

	public void setTypeDistribution(String typeDistribution) {
		this.typeDistribution = typeDistribution;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
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

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public DatasetForm() {

	};

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAccessURL() {
		return accessURL;
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

	public PreDataset build() {
		PreDataset preDataset = new PreDataset();

		preDataset.setId(id);
		preDataset.setDataQuality(quality);
		preDataset.setDistribution(typeDistribution);
		preDataset.setKeyword(keyword);
		preDataset.setLicense(license);
		preDataset.setModified(modified + "T23:59:59Z");
		preDataset.setPublisher(publisher);
		preDataset.setSpatial(spatial);
		preDataset.setTemporal(temporal);
		preDataset.setTitle(title);
		preDataset.setUsername(username);
		preDataset.setAccessURL(accessURL);
		preDataset.setFormat(format);
		preDataset.setLocation(location);
		preDataset.setDescription(description);
		preDataset.setTitleId(Parsing.withoutSpecialCharacters(title));

		return preDataset;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
