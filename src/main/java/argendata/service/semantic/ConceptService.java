package argendata.service.semantic;

import javax.xml.namespace.QName;

import argendata.model.skos.Concept;
import argendata.model.skos.ConceptScheme;

public interface ConceptService {
	
	/**
	 * @param obj Concept to be stored.
	 * @return The stored concept.
	 */
	public Concept store(Concept obj);

	/**
	 * @param obj Concept to be deleted.
	 */
	public void delete(Concept obj);

	/**
	 * @return An iterable for all the Concepts stored
	 */
	public Iterable<Concept> findAll();

	/**
	 * @param qName The qualifiedName of a concept.
	 * @return A concept for the given qualified name.
	 */
	public Concept find(QName qName);

	/**
	 * @param title Title for the new concept to be created
	 * @return A new created concept.
	 */
	public Concept newConcept(String title);

	/**
	 * @param title The title for the ConceptScheme.
	 * @return A new created conceptscheme.
	 */
	public ConceptScheme newConceptScheme(String title);
}
