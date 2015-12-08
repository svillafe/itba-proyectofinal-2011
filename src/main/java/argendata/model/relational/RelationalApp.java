package argendata.model.relational;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import argendata.model.base.PersistantEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "qName" }) })
public class RelationalApp extends PersistantEntity {

	private String qName;

	private Boolean isAllowed;

	@ManyToOne
	@JoinColumn(name = "publisher")
	private ArgendataUser publisher;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "data", column = @Column(name = "icon")) })
	private Image icon;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "data", column = @Column(name = "screenshot")) })
	private Image screenShot;

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public Image getScreenShot() {
		return screenShot;
	}

	public void setScreenShot(Image screenShot) {
		this.screenShot = screenShot;
	}

	public Boolean getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public ArgendataUser getPublisher() {
		return publisher;
	}

	public void setPublisher(ArgendataUser publisher) {
		this.publisher = publisher;
	}

	public RelationalApp() {

	}

	public RelationalApp(String qName, Boolean isAllowed, ArgendataUser publisher,
			Image icon, Image screenShot) {
		super();
		this.qName = qName;
		this.isAllowed = isAllowed;
		this.publisher = publisher;
		this.icon = (icon == null ? null : new Image(icon.getData()));
		this.screenShot = (screenShot == null ? null : new Image(
				screenShot.getData()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((icon == null) ? 0 : icon.hashCode());
		result = prime * result
				+ ((isAllowed == null) ? 0 : isAllowed.hashCode());
		result = prime * result
				+ ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((qName == null) ? 0 : qName.hashCode());
		result = prime * result
				+ ((screenShot == null) ? 0 : screenShot.hashCode());
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
		RelationalApp other = (RelationalApp) obj;
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
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (qName == null) {
			if (other.qName != null)
				return false;
		} else if (!qName.equals(other.qName))
			return false;
		if (screenShot == null) {
			if (other.screenShot != null)
				return false;
		} else if (!screenShot.equals(other.screenShot))
			return false;
		return true;
	}

}
