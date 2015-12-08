package argendata.model.dcat;

import javax.xml.namespace.QName;

import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/ns/dcat#Distribution")
public abstract class Distribution {

	@rdf("http://www.w3.org/ns/dcat#accessURL")
	private String accessURL;

	@rdf("http://www.w3.org/ns/dcat#size")
	private int size;

	@rdf("http://www.w3.org/ns/dcat#format")
	private String format;

	public QName getQName(){
		return new QName("http://www.argendata.com/data/","Distribution:"+accessURL);
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
		if(format == null || format.equals(""))
			this.format = "Otros formatos";
		else
			this.format = format;
	}

	@Override
	public String toString() {
		return "Distribution [accessURL=" + accessURL + ", size=" + size
				+ ", format=" + format + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accessURL == null) ? 0 : accessURL.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((size == 0) ? 0 : size);
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
		Distribution other = (Distribution) obj;
		if (accessURL == null) {
			if (other.accessURL != null)
				return false;
		} else if (!accessURL.equals(other.accessURL))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (size == 0) {
			if (other.size != 0)
				return false;
		} else if (size!=other.size)
			return false;
		return true;
	}

}
