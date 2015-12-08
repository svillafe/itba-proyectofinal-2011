package argendata.model.skos;

import org.openrdf.elmo.annotations.rdf;

@rdf("http://www.w3.org/2004/02/skos/core#ConceptScheme")
public class ConceptScheme {
	
	
	@rdf("http://www.w3.org/2004/02/skos/core#clases")
	private String clases;


	public String getClases() {
		return clases;
	}

	public void setClases(String clases) {
		this.clases = clases;
	}

	@Override
	public String toString() {
		return "ConceptScheme [clases=" + clases + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clases == null) ? 0 : clases.hashCode());
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
		ConceptScheme other = (ConceptScheme) obj;
		if (clases == null) {
			if (other.clases != null)
				return false;
		} else if (!clases.equals(other.clases))
			return false;
		return true;
	}
	
	
		
}
