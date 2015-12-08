package argendata.service.semantic.impl;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.semantic.OrganizationDao;
import argendata.model.foaf.Organization;
import argendata.service.semantic.OrganizationService;

@Service
public class OrganizationServiceImpl implements OrganizationService {

	private OrganizationDao organizationDao;
	
	@Autowired
	public OrganizationServiceImpl(OrganizationDao organizationDao) {
		super();
		this.organizationDao = organizationDao;
	}

	public Iterable<Organization> findAll() {
		return organizationDao.getAll();
	}

	public Organization store(Organization obj) {

		
		return this.organizationDao.store(obj);
	}

	public void delete(Organization obj) {
		this.organizationDao.delete(obj);
	}

	public Organization find(QName qName) {

		return this.organizationDao.getById(qName);
	}

	public Organization newOrganization(String title) {
		return organizationDao.newOrganization(title);
	}

}
