package argendata.dao.semantic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import argendata.dao.semantic.OrganizationDao;
import argendata.model.foaf.Organization;
import argendata.util.Properties;

@Repository
public class ElmoOrganizationDao extends ElmoGenericDao<Organization> implements
		OrganizationDao {
	private RepositoryConnectionManager connectionManager;

	@Autowired
	public ElmoOrganizationDao(RepositoryConnectionManager rcm, Properties properties) {
		this.connectionManager = rcm;
		super.setManager(connectionManager.getManager());
		super.setProperties(properties);
	}

	@Override
	public Iterable<Organization> getAll() {
		return super.getAll();

	}

	public Organization newOrganization(String title) {
		Organization resp = (Organization)(super.newElement("Organization:" + title));
		return resp;
	}

}
