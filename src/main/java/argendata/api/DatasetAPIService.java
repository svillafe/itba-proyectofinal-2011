package argendata.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.xml.namespace.QName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import argendata.dao.semantic.impl.ElmoDatasetDao;
import argendata.dao.semantic.impl.ElmoDistributionDao;
import argendata.dao.semantic.impl.ElmoOrganizationDao;
import argendata.dao.semantic.impl.RepositoryGateway;
import argendata.exceptions.NonSupportedQueryException;
import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.model.foaf.Organization;
import argendata.util.Parsing;
import argendata.util.Properties;

import com.sun.jersey.api.NotFoundException;

@Path("/dataset")
@Component
public class DatasetAPIService {

	private static final int MAX_QUERY_PARAM = 100;
	private static final Integer MAX_QTY = 50;
	private ElmoDatasetDao datasetDao;
	private ElmoOrganizationDao organizationDao;
	private ElmoDistributionDao distributionDao;
	private RepositoryGateway repositoryGateway;
	private Properties properties;

	@Autowired
	public DatasetAPIService(ElmoDatasetDao datasetDao,
			ElmoOrganizationDao organizationDao,
			ElmoDistributionDao distributionDao,
			RepositoryGateway repositoryGateway,
			Properties properties) {
		this.datasetDao = datasetDao;
		this.organizationDao = organizationDao;
		this.distributionDao = distributionDao;
		this.repositoryGateway = repositoryGateway;
		this.properties = properties;
	}

	@GET
	@Path("/by/publisher/{p}.json")
	@Produces("application/json"+ ";charset=UTF-8")
	public List<Dataset> getDatasetsByPublisherJSON(
			@PathParam(value = "p") String publisher) {
		
		/*try {
			publisher=URLDecoder.decode(publisher,"UTF-8");
			publisher=Parsing.withoutSpecialCharacters(publisher);
		} catch (UnsupportedEncodingException e1) {
			throw new NotFoundException("Recurso no encontrado");
		}*/
		
		try {
			if (publisher == null || publisher.isEmpty()
					|| publisher.length() > MAX_QUERY_PARAM) {
				throw new Exception();
			}
			validate(publisher);
			List<Dataset> storeDatasets = datasetDao
					.getAllByPublisher(publisher);
			List<Dataset> datasets = pureList(storeDatasets);

			return datasets;
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}
	}

	@GET
	@Path("/by/publisher/{p}.rdf")
	@Produces("application/xml")
	public String getDatasetsByPublisherRDFXML(
			@PathParam(value = "p") String publisher) {

		try {
			publisher=URLDecoder.decode(publisher,"UTF-8");
			validate(publisher);
			publisher=Parsing.withoutSpecialCharacters(publisher);
		} catch (Exception e1) {
			throw new NotFoundException("Recurso no encontrado");
		}
		
		QName qName = new QName(properties.getNamespace(),
				"Organization:" + publisher);

		String rdfxml;
		try {
			rdfxml = repositoryGateway.getDatasetRDFByPublisher(qName);
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}

		return rdfxml;
	}

	@GET
	@Path("/by/title/{t}.json")
	@Produces("application/json"+ ";charset=UTF-8")
	public Dataset getDatasetByTitleJSON(@PathParam(value = "t") String title) {
		try {
			
			try {
				title=URLDecoder.decode(title,"UTF-8");
				validate(title);
				title=Parsing.withoutSpecialCharacters(title);
			} catch (Exception e1) {
				throw new NotFoundException("Recurso no encontrado");
			}
			
			if (title == null || title.isEmpty()
					|| title.length() > MAX_QUERY_PARAM) {
				throw new Exception();
			}
			QName qName = new QName(properties.getNamespace(),
					"Dataset:" + title);

			Dataset d = datasetDao.getById(qName);

			Organization o = new Organization();
			o.setName(d.getPublisher().getName());
			Distribution dis = null;
			if (d.getDistribution() instanceof WebService) {
				dis = new WebService();
			} else if (d.getDistribution() instanceof Feed) {
				dis = new Feed();
			} else if (d.getDistribution() instanceof Download) {
				dis = new Download();
			}
			dis.setAccessURL(d.getAccessURL());
			dis.setFormat(d.getFormat());
			dis.setSize(d.getSize());
			Dataset dataset = new Dataset(d.getTitle(), d.getDescription(),
					d.getLicense(), d.getKeyword(), d.getDataQuality(),
					d.getModified(), d.getSpatial(), d.getTemporal(),
					d.getLocation(), o, dis, Parsing.withoutSpecialCharacters(d
							.getTitleId()));

			return dataset;
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}
	}

	@GET
	@Path("/by/title/{t}.rdf")
	@Produces("application/xml")
	public String getDatasetByTitleRDFXML(@PathParam(value = "t") String title) {
		
		try {
			
			title=URLDecoder.decode(title,"UTF-8");
			validate(title);
			title=Parsing.withoutSpecialCharacters(title);
		} catch (Exception e1) {
			throw new NotFoundException("Recurso no encontrado");
		}
		
		QName qName = new QName(properties.getNamespace(), "Dataset:"
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

	@GET
	@Path("/by/keyword/{k}.json")
	@Produces("application/json"+ ";charset=UTF-8")
	public List<Dataset> getDatasetsByKeywordJSON(
			@PathParam(value = "k") String keyword) {
		try {
			validate(keyword);
			if (keyword == null || keyword.isEmpty()
					|| keyword.length() > MAX_QUERY_PARAM) {
				throw new Exception();
			}

			List<Dataset> storeDatasets = datasetDao
					.getFromKeyword(keyword, 25);

			List<Dataset> datasets = pureList(storeDatasets);

			return datasets;
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}
	}

	@GET
	@Path("/by/keyword/{k}.rdf")
	@Produces("application/xml")
	public String getDatasetsByKeywordRDFXML(
			@PathParam(value = "k") String keyword) {

		String rdfxml;
		try {
			validate(keyword);
			rdfxml = repositoryGateway.getDatasetRDFByKeyword(keyword);
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}

		return rdfxml;
	}

	@GET
	@Path("/latest.json")
	@Produces("application/json"+ ";charset=UTF-8")
	public List<Dataset> getDatasetsByModifiedJSON() {
		try {
			List<Dataset> storeDatasets = datasetDao.getRecentlyModified();

			List<Dataset> datasets = pureList(storeDatasets);

			return datasets;
		} catch (Exception e) {
			throw new NotFoundException("Recurso no encontrado");
		}
	}

	private List<Dataset> pureList(List<Dataset> storeDatasets) {
		List<Dataset> datasets = new ArrayList<Dataset>();

		for (Dataset d : storeDatasets) {
			Organization o = new Organization();
			o.setName(d.getPublisher().getName());
			Distribution dis = null;
			if (d.getDistribution() instanceof WebService) {
				dis = new WebService();
			} else if (d.getDistribution() instanceof Feed) {
				dis = new Feed();
			} else if (d.getDistribution() instanceof Download) {
				dis = new Download();
			}
			dis.setAccessURL(d.getAccessURL());
			dis.setFormat(d.getFormat());
			dis.setSize(d.getSize());
			datasets.add(new Dataset(d.getTitle(), d.getDescription(), d
					.getLicense(), d.getKeyword(), d.getDataQuality(), d
					.getModified(), d.getSpatial(), d.getTemporal(), d
					.getLocation(), o, dis, Parsing.withoutSpecialCharacters(d.getTitle())));
		}

		return datasets;
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
