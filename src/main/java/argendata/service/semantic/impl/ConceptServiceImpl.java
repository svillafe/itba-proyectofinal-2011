package argendata.service.semantic.impl;

import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.semantic.ConceptDao;
import argendata.dao.semantic.impl.ElmoConceptDao;
import argendata.model.skos.Concept;
import argendata.model.skos.ConceptScheme;
import argendata.service.semantic.ConceptService;

@Service
public class ConceptServiceImpl implements ConceptService {

	
	private ConceptDao conceptDao;
	
	@Autowired
	public ConceptServiceImpl(ConceptDao ecd) {
		super();
		this.conceptDao = ecd;
	}

	public Iterable<Concept> findAll() {
		return conceptDao.getAll();
	}

	public Concept store(Concept obj) {
		return this.conceptDao.store(obj);
	}

	public void delete(Concept obj) {
		this.conceptDao.delete(obj);
	}

	public Concept find(QName qName) {

		return this.conceptDao.getById(qName);
	}



	public Concept newConcept(String title) {
		return conceptDao.newConcept(title);
	}

	public ConceptScheme newConceptScheme(String title) {
		return conceptDao.newConceptScheme(title);
	}

}
