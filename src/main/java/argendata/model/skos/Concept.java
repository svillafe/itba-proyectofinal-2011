package argendata.model.skos;

import java.io.Serializable;

import org.openrdf.elmo.annotations.rdf;
import org.openrdf.elmo.annotations.inverseOf;

@rdf("http://www.w3.org/2004/02/skos/core#Concept")
public class Concept  {


	@rdf("http://www.w3.org/2004/02/skos/core#ConceptScheme")
	private ConceptScheme inScheme;


	public ConceptScheme getInScheme() {
		return inScheme;
	}

	
	public void setInScheme(ConceptScheme inScheme) {
		this.inScheme = inScheme;
	}

	@Override
	public String toString() {
		return "Concept [inScheme=" + inScheme + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((inScheme == null) ? 0 : inScheme.hashCode());
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
		Concept other = (Concept) obj;
		if (inScheme == null) {
			if (other.inScheme != null)
				return false;
		} else if (!inScheme.equals(other.inScheme))
			return false;
		return true;
	}
	

	
	
	/*public static Concept valueOf(String miString){
		ConceptScheme aux= new ConceptScheme();
		aux.setClases(miString);
		Concept aux2 = new Concept();
		aux2.setInScheme(aux);
		return aux2;
	}*/
	
	/*public Concept(){
		super();
	}*/
	
	

}
