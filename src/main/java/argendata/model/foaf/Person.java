package argendata.model.foaf;

import javax.xml.namespace.QName;

import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/ns/foaf#Person")
public class Person {

	@rdf("http://www.w3.org/ns/foaf#name")
	private String name;

	@Override
	public String toString() {
		return "Organization [name=" + name + "]";
	}

	public QName getQName(){
		return new QName("http://www.argendata.com/data/","Person:"+name);
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
