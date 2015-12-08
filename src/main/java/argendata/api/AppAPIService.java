package argendata.api;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import argendata.dao.semantic.impl.ElmoSemanticAppDao;
import argendata.dao.semantic.impl.RepositoryGateway;
import argendata.exceptions.NonSupportedQueryException;
import argendata.model.argendata.Semanticapp;
import argendata.util.Parsing;
import argendata.util.Properties;

import com.sun.jersey.api.NotFoundException;

@Path("/app")
@Component
public class AppAPIService {

	private ElmoSemanticAppDao semanticAppDao;
	private Properties properties;
	private RepositoryGateway repositoryGateway;

	@Autowired
	public AppAPIService(ElmoSemanticAppDao semanticAppDao,
			Properties properties, RepositoryGateway repositoryGateway) {
		this.semanticAppDao = semanticAppDao;
		this.properties = properties;
		this.repositoryGateway = repositoryGateway;
	}

	@GET
	@Path("/by/title/{t}.json")
	@Produces("application/json" + ";charset=UTF-8")
	public Semanticapp getAppByTitleJSON(@PathParam(value = "t") String title) {
		try {
			if (title == null
					|| title.isEmpty()
					|| title.length() > Integer.valueOf(properties
							.getMaxQueryLength())) {
				throw new Exception();
			}

			validate(title);

			QName qName = new QName(properties.getNamespace(), "Semanticapp:"
					+ title);
			Semanticapp app = semanticAppDao.getById(qName);

			Semanticapp resp = new Semanticapp(app.getName(),
					app.getDescription(), app.getAppkeyword(), app.getUrl(),
					app.getDataset(), app.getPoints());

			return resp;
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}
	}

	@GET
	@Path("/by/title/{t}.rdf")
	@Produces("application/xml")
	public String getAppByTitleRDFXML(@PathParam(value = "t") String title) {

		try {

			title = URLDecoder.decode(title, "UTF-8");
			validate(title);
			title = Parsing.withoutSpecialCharacters(title);
		} catch (Exception e1) {
			throw new NotFoundException("Recurso no encontrado");
		}

		QName qName = new QName(properties.getNamespace(), "Semanticapp:"
				+ title);

		String rdfxml;
		try {
			rdfxml = repositoryGateway.getDatasetRDFByQName(qName);
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_XML);

		return rdfxml;// new ResponseEntity<String>(rdfxml, responseHeaders,
						// HttpStatus.CREATED);
	}

	private void validate(String query) throws NonSupportedQueryException {

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
