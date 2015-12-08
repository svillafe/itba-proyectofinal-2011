package argendata.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.rmi.MarshalledObject;

import javax.naming.directory.InvalidAttributesException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import argendata.dao.semantic.impl.RepositoryGateway;
import argendata.exceptions.NonSupportedQueryException;
import argendata.util.Format;
import argendata.util.Language;

@Path("/repo")
@Component
public class RepositoryAPIService {

	private RepositoryGateway repositoryGateway;

	@Autowired
	public RepositoryAPIService(RepositoryGateway repositoryGateway) {
		this.repositoryGateway = repositoryGateway;
	}

	@GET
	@Path("/query.rdf")
	public Response excecuteSPARQLRDFQuery(@QueryParam(value = "q") String query) {
		
		return excecuteQuery(query,Format.RDFXML,Language.SPARQL);
	}

	@GET
	@Path("/query.json")
	public Response excecuteSPARQLJSONQuery(@QueryParam(value = "q") String query) {

		return excecuteQuery(query,Format.JSON,Language.SPARQL);
	}
	
	
	private Response excecuteQuery(String query, Format format, Language language){
		Response resp = null;
		String body= "";

		try {
			query=URLDecoder.decode(query,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			resp = Response.serverError().build();
		}
		
		if(query.length() > 600){
			return Response.status(413).entity("Muy largo").build();
		} else if(query.length()<=0){
			return Response.status(400).build();
		}

		try {
			body = repositoryGateway.query(format, query,
					language);
			if(format == Format.JSON){
				resp = Response.ok(body,MediaType.TEXT_PLAIN).build();
			}else{
				resp = Response.ok(body,MediaType.APPLICATION_XML_TYPE).build();
			}
			
		} catch (InvalidAttributesException e) {
			resp = Response.serverError().build();
		} catch (QueryEvaluationException e) {
			resp = Response.status(400).build();
		} catch (MalformedQueryException e) {
			resp = Response.status(421).entity("Consulta mal formada").build();
		} catch (NonSupportedQueryException e) {
			Response.status(420).entity("Consulta no soportada").build();
		}

		return resp;
	}
}
