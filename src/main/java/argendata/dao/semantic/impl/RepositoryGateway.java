package argendata.dao.semantic.impl;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.naming.directory.InvalidAttributesException;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.openrdf.model.Resource;
import org.openrdf.model.ValueFactory;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQueryResultHandler;
import org.openrdf.query.resultio.sparqljson.SPARQLResultsJSONWriter;
import org.openrdf.query.resultio.sparqlxml.SPARQLResultsXMLWriter;
import org.openrdf.repository.Repository;
import org.openrdf.rio.rdfxml.RDFXMLWriter;
import org.openrdf.rio.rdfxml.util.OrganizedRDFXMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.exceptions.NonSupportedQueryException;
import argendata.util.Format;
import argendata.util.Language;
import argendata.util.Properties;

@Service
public class RepositoryGateway {

	private final Logger logger = Logger.getLogger(Repository.class);
	private RepositoryConnectionManager rcm;
	private Properties properties;

	@Autowired
	public RepositoryGateway(RepositoryConnectionManager rcm,
			Properties properties) {
		this.rcm = rcm;
		this.properties = properties;

	}

	public String getDatasetRDFByQName(QName qName) throws Exception {
		String rdf = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OrganizedRDFXMLWriter wr = new OrganizedRDFXMLWriter(baos);

			Repository repo = rcm.getRepository();
			ValueFactory vf = repo.getConnection().getValueFactory();

			Resource r = vf.createURI(qName.getNamespaceURI()
					+ qName.getLocalPart());

			repo.getConnection().exportStatements(r, null, null, true, wr);

			rdf = baos.toString("UTF-8");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return rdf;
	}

	public String getAppRDFByQName(QName qName) throws Exception {
		String rdf = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OrganizedRDFXMLWriter wr = new OrganizedRDFXMLWriter(baos);

			Repository repo = rcm.getRepository();
			ValueFactory vf = repo.getConnection().getValueFactory();

			Resource r = vf.createURI(qName.getNamespaceURI()
					+ qName.getLocalPart());

			repo.getConnection().exportStatements(r, null, null, true, wr);

			rdf = baos.toString("UTF-8");

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}

		return rdf;
	}
	
	public String getDatasetRDFByPublisher(QName qName) throws Exception {
		String rdf = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OrganizedRDFXMLWriter wr = new OrganizedRDFXMLWriter(baos);

			Repository repo = this.rcm.getRepository();

			String query = "CONSTRUCT * FROM  {Dataset} p {x}, {Dataset} <http://www.w3.org/ns/foaf#publisher> {<"
					+ qName.getNamespaceURI() + qName.getLocalPart() + ">}";
			repo.getConnection().prepareGraphQuery(QueryLanguage.SERQL, query)
					.evaluate(wr);

			rdf = baos.toString("UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return rdf;
	}

	public String getDatasetRDFByKeyword(String keyword) throws Exception {
		String rdf = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OrganizedRDFXMLWriter wr = new OrganizedRDFXMLWriter(baos);

			Repository repo = this.rcm.getRepository();

			String query = "CONSTRUCT * FROM  {Dataset} p {x}, {Dataset} <http://www.w3.org/ns/dcat#keyword> {\""
					+ keyword + "\"}";
			repo.getConnection().prepareGraphQuery(QueryLanguage.SERQL, query)
					.evaluate(wr);

			rdf = baos.toString("UTF-8");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
		return rdf;
	}

	public String query(Format responseFormat, String query,
			Language queryLanguage) throws NonSupportedQueryException,
			InvalidAttributesException, QueryEvaluationException,
			MalformedQueryException {

		validateQuery(query);
		sanitizeQuery(query);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Repository repo = this.rcm.getRepository();

		if ((responseFormat.equals(Format.JSON) && queryLanguage
				.equals(Language.SeRQL))
				|| (!responseFormat.equals(Format.JSON) && !responseFormat
						.equals(Format.RDFXML))
				|| (!queryLanguage.equals(Language.SeRQL) && !queryLanguage
						.equals(Language.SPARQL))) {
			throw new InvalidAttributesException();
		}

		if (queryLanguage.equals(Language.SeRQL)) {
			OrganizedRDFXMLWriter wr = new OrganizedRDFXMLWriter(baos);
			try {
				repo.getConnection()
						.prepareGraphQuery(QueryLanguage.SERQL, query)
						.evaluate(wr);
			} catch (QueryEvaluationException e1) {
				throw e1;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			}

		} else if (queryLanguage.equals(Language.SPARQL)) {
			TupleQueryResultHandler wr = null;

			if (responseFormat.equals(Format.JSON)) {
				wr = new SPARQLResultsJSONWriter(baos);
			} else {
				wr = new SPARQLResultsXMLWriter(baos);
			}

			try {
				repo.getConnection()
						.prepareTupleQuery(QueryLanguage.SPARQL, query)
						.evaluate(wr);
			} catch (QueryEvaluationException e1) {
				throw e1;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				return null;
			}
		} else {
			throw new InvalidAttributesException();
		}

		return baos.toString();

	}

	private void sanitizeQuery(String query) {
		if (!query.contains("LIMIT")) {
			query.concat(" LIMIT " + properties.getSesameQueryLimit());
		}

	}

	private void validateQuery(String query) throws NonSupportedQueryException {

		if (query == null) {
			throw new NonSupportedQueryException("La consulta es vacía");
		}

		if (query.toUpperCase().contains("MODIFY")
				|| query.toUpperCase().contains("UPDATE")
				|| query.toUpperCase().contains("DELETE")
				|| query.toUpperCase().contains("INSERT")
				|| query.toUpperCase().contains(" LOAD")
				|| query.toUpperCase().contains("CLEAR")
				|| query.toUpperCase().contains("DROP")) {
			throw new NonSupportedQueryException();
		}

	}
}
