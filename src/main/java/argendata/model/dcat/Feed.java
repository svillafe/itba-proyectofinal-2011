package argendata.model.dcat;

import javax.xml.namespace.QName;

import org.openrdf.elmo.annotations.inverseOf;
import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/ns/dcat#Feed")
public class Feed extends Distribution {
	@rdf("http://www.w3.org/ns/dcat#name")
	private String name;

	
	public QName getQName(){
		return new QName("http://www.argendata.com/data/","Feed:"+super.getAccessURL());
	}
	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Feed [name=" + name + "]";
	}
	
	/*
	public static Feed valueOf(String miString){
		Feed aux = new Feed();
		aux.setName(miString);
		return aux;
	}*/
}
