package argendata.service.semantic;

import javax.xml.namespace.QName;

import argendata.model.foaf.Organization;

public interface OrganizationService {
	
	/**
	 * @param obj An organization to be stored.
	 * @return The stored organization.
	 */
	public Organization store(Organization obj);

	/**
	 * @param obj An organization to be deleted.
	 */
	public void delete(Organization obj);

	/**
	 * @return An iterable object for all Organizations stored.
	 */
	public Iterable<Organization> findAll();

	/**
	 * @param qName The qualified name of an organization
	 * @return An organization for the given qualifiedName
	 */
	public Organization find(QName qName);

	/**
	 * @param titleId The title of the organization to be created.
	 * @return The new organization created in the store.
	 */
	public Organization newOrganization(String titleId);
}
