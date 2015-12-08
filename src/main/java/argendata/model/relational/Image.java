package argendata.model.relational;

import javax.persistence.Embeddable;

import argendata.model.base.PersistantEntity;

@Embeddable
public class Image extends PersistantEntity {

	private byte[] data;

	public Image() {
		super();
	}

	public Image(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}