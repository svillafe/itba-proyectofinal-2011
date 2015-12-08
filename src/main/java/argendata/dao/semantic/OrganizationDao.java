package argendata.dao.semantic;

import argendata.dao.GenericDao;
import argendata.model.foaf.Organization;

public interface OrganizationDao extends GenericDao<Organization> {

	/**
	 * @param title The title for the new organization.
	 * @return A new organization created in the store.
	 */
	Organization newOrganization(String title);
	
}
