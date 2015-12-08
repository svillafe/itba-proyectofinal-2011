package argendata.model.dcat;

import javax.xml.namespace.QName;

import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/ns/dcat#Download")
public class Download extends Distribution {

	@rdf("http://www.w3.org/ns/dcat#name")
	private String name;

	public QName getQName(){
		return new QName("http://www.argendata.com/data/","Download:"+super.getAccessURL());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Download [name=" + name + "]";
	}
	
	
}
