package argendata.model.relational;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

import argendata.model.argendata.Semanticapp;
import argendata.model.base.PersistantEntity;
import argendata.model.dcat.Dataset;

@Entity
@Table(name = "preApp")
public class App extends PersistantEntity {

	private String name;
	
	private String nameId;

	@Column(length = 600)
	private String description;

	private Integer points;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "data", column = @Column(name = "screenshot")) })
	private Image screenShot;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "data", column = @Column(name = "icon")) })
	private Image icon;

	@ManyToOne
	@JoinColumn(name = "publisher")
	private ArgendataUser publisher;

	private Boolean isAllowed;

	private String url;

	
	
	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<String> keyword;
	
	@CollectionOfElements(fetch = FetchType.EAGER)
	private Set<String> dataset;

	public App() {

	}

	public App(Semanticapp semanticApp, RelationalApp relationalApp) {
		this.name = semanticApp.getName();
		this.description = semanticApp.getDescription();
		this.points = Integer.valueOf(semanticApp.getPoints());
		this.screenShot = relationalApp.getScreenShot();
		this.icon = relationalApp.getIcon();
		this.isAllowed = relationalApp.getIsAllowed();
		this.publisher = relationalApp.getPublisher();
		this.url = semanticApp.getUrl();
		this.keyword = new TreeSet<String>();
		this.keyword.addAll(semanticApp.getAppkeyword());
		this.dataset = new TreeSet<String>();
		this.nameId = semanticApp.getNameId();
		for(Dataset d: semanticApp.getDataset()){
			dataset.add(d.getTitle());
		}
	}
	
	
	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}

	public void setDataset(Set<String> dataset) {
		this.dataset = dataset;
	}

	public Set<String> getDataset() {
		return dataset;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Image getScreenshot() {
		return screenShot;
	}

	public void setScreenshot(Image screenshot) {
		this.screenShot = screenshot;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public ArgendataUser getPublisher() {
		return publisher;
	}

	public void setPublisher(ArgendataUser publisher) {
		this.publisher = publisher;
	}

	public Boolean getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result
				+ ((isAllowed == null) ? 0 : isAllowed.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result
				+ ((screenShot == null) ? 0 : screenShot.hashCode());
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
		App other = (App) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (icon == null) {
			if (other.icon != null)
				return false;
		} else if (!icon.equals(other.icon))
			return false;
		if (isAllowed == null) {
			if (other.isAllowed != null)
				return false;
		} else if (!isAllowed.equals(other.isAllowed))
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
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (screenShot == null) {
			if (other.screenShot != null)
				return false;
		} else if (!screenShot.equals(other.screenShot))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public RelationalApp getRelationalApp() {
		RelationalApp resp = new RelationalApp("Semanticapp:" + nameId, isAllowed,
				publisher, icon, screenShot);
		return resp;
	}

	public Semanticapp getSemanticApp() {
		Semanticapp resp = new Semanticapp(name, description,
				points.toString(),keyword, url,dataset,nameId);
		return resp;
	}

	public Set<String> getKeyword() {

		return this.keyword;
	}

	public void setKeyword(Set<String> keyword) {
		this.keyword = keyword;
	}

}
