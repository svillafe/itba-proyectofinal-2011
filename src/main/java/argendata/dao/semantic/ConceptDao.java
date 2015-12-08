package argendata.dao.semantic;

import argendata.dao.GenericDao;
import argendata.model.skos.Concept;
import argendata.model.skos.ConceptScheme;

public interface ConceptDao extends GenericDao<Concept> {

	/**
	 * @param title
	 * @return
	 */
	Concept newConcept(String title);

	/**
	 * @param title
	 * @return
	 */
	ConceptScheme newConceptScheme(String title);
}
