package argendata.dao.semantic.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.semantic.ConceptDao;
import argendata.model.skos.Concept;
import argendata.model.skos.ConceptScheme;
import argendata.util.Properties;

@Repository
public class ElmoConceptDao extends ElmoGenericDao<Concept> implements ConceptDao {
	private RepositoryConnectionManager connectionManager;

	@Autowired
	public ElmoConceptDao(RepositoryConnectionManager cm, Properties properties) {
		this.connectionManager = cm;
		super.setManager(connectionManager.getManager());
		super.setProperties(properties);
	}
	
	@Override
	public Iterable<Concept> getAll(){
		return super.getAll();
		
	}

	public Concept newConcept(String title) {
		Concept resp = super.newElement("Concept:"+title);
		return resp;
	}

	public ConceptScheme newConceptScheme(String title) {
		ConceptScheme resp = super.newElement("ConceptScheme:"+title,ConceptScheme.class);
		return resp;
	}
}
