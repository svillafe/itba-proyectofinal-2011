package argendata.model.foaf;

import javax.xml.namespace.QName;

import org.apache.solr.client.solrj.beans.Field;
import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/ns/foaf#Organization")
public class Organization  {
	
	@rdf("http://www.w3.org/ns/foaf#nameid")
	private String nameid;
	
	
	@rdf("http://www.w3.org/ns/foaf#name")
	private String name;

	@Override
	public String toString() {
		return "Organization [name=" + name + "]";
	}

	public QName getQName(){
		return new QName("http://www.argendata.com/data/","Organization:"+name);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameId() {
		return nameid;
	}

	public void setNameId(String nameid) {
		this.nameid = nameid;
	}

	public Organization(){
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Organization other = (Organization) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
