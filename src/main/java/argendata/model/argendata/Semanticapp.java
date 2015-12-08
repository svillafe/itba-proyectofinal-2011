package argendata.model.argendata;

import java.util.Set;
import java.util.TreeSet;

import org.apache.solr.client.solrj.beans.Field;
import org.openrdf.elmo.annotations.rdf;

import argendata.model.dcat.Dataset;
import argendata.util.Parsing;

@rdf("http://www.w3.org/ns/argendata#Semanticapp")
public class Semanticapp {

	@Field("type")
	private String type;

	@Field("title")
	@rdf("http://www.w3.org/ns/argendata#name")
	private String name;
	
	@Field("titleId")
	@rdf("http://www.w3.org/ns/argendata#nameId")
	private String nameId;

	@Field("description")
	@rdf("http://www.w3.org/ns/argendata#description")
	private String description;

	@Field("points")
	@rdf("http://www.w3.org/ns/argendata#points")
	private String points;

	@Field("url")
	@rdf("http://www.w3.org/ns/argendata#url")
	private String url;
	
	@Field("appkeyword")
	@rdf("http://www.w3.org/ns/argendata#appkeyword")
	private Set<String> appkeyword;
	
	@rdf("http://www.w3.org/ns/argendata#dataset")
	private Set<Dataset> dataset;

	public Semanticapp() {
		super();
		this.type="app";
	}

	public Semanticapp(String name, String description, String points,
			Set<String> keyword, String url, Set<String> dataset, String nameId) {
		super();
		this.type = "app";
		this.setName(name);
		this.setDescription(description);
		this.points = points;
		this.url = url;
		this.appkeyword = new TreeSet<String>();
		this.appkeyword.addAll(keyword);
		this.dataset = new TreeSet<Dataset>();
		this.nameId = nameId;
		for(String d: dataset){
			Dataset aux =new Dataset();
			aux.setTitle(d);
			aux.setTitleId(Parsing.withoutSpecialCharacters(d));
			this.dataset.add(aux);
		}
	}

	public Semanticapp(String name2, String description2, 
			Set<String> appkeyword2, String url2, Set<Dataset> dataset2,String points2) {
		super();
		this.type = "app";
		this.setName(name2);
		this.setDescription(description2);
		this.points = points2;
		this.url = url2;
		this.appkeyword = new TreeSet<String>();
		this.appkeyword.addAll(appkeyword2);
		this.dataset = dataset2;
	}

	public Set<Dataset> getDataset() {
		return dataset;
	}

	public void setDataset(Set<Dataset> dataset) {
		this.dataset = dataset;
	}

	public String getName() {
		return name;
	}

	@Field("title")
	public void setName(String name) {
		this.name = name;
	}

	
	public String getDescription() {
		return description;
	}

	@Field("description")
	public void setDescription(String description) {
		this.description = description;
	}

	public String getPoints() {
		return points;
	}

	@Field("points")
	public void setPoints(String points) {
		this.points = points;
	}
	


	public Set<String> getAppkeyword() {
		return appkeyword;
	}

	@Field("appkeyword")
	public void setAppkeyword(Set<String> appkeyword) {
		this.appkeyword = appkeyword;
	}

	public String getUrl() {
		return url;
	}

	@Field("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Semanticapp other = (Semanticapp) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public String getType() {
		return type;
	}


	@Field("type")
	public void setType(String type) {
		this.type = type;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	
	

}