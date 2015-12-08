package argendata.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.naming.directory.InvalidAttributesException;

import org.apache.log4j.Logger;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import argendata.dao.semantic.impl.RepositoryGateway;
import argendata.exceptions.NonSupportedQueryException;
import argendata.util.DataSession;
import argendata.util.Format;
import argendata.util.Language;
import argendata.util.Messages;
import argendata.util.Properties;
import argendata.web.command.MainPageForm;
import argendata.web.command.SparqlSearchForm;
import argendata.web.validator.SparqlSearchFormValidator;

@Controller
public class MainController {

	private SparqlSearchFormValidator sparqlValidator;
	private static final Logger logger = Logger.getLogger(MainController.class);
	private RepositoryGateway repositoryGateway;
	private DataSession dataSession;
	private Properties properties;
	private Messages messages;
	
	@Autowired
	public MainController(SparqlSearchFormValidator sparqlValidator,
		
			RepositoryGateway repositoryGateway,
			DataSession dataSession,
			Properties properties,
			Messages messages) {
		this.sparqlValidator = sparqlValidator;
		this.repositoryGateway = repositoryGateway;
		this.dataSession = dataSession;
		this.properties = properties;
		this.messages = messages;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView();

		dataSession.update();
		
		mav.addObject(new MainPageForm());
		mav.addObject("queryAnswer", "RDF Answer");

		mav.addObject(dataSession);
		
		return mav;
	}

	@RequestMapping(value = "/sparql", method = RequestMethod.GET)
	public ModelAndView sparqlQuery(
			@RequestParam(value = "query", required = false) String query,
			@RequestParam(value = "dataformat", required = false) Format format,
			@RequestParam(value = "language", required = false) Language language) {
		ModelAndView mav = new ModelAndView();

		if (query == null || query.isEmpty() || format==null || language == null) {
			mav.addObject(new SparqlSearchForm(properties.getDefaultQuery(),Format.RDFXML,Language.SPARQL,""));
			return mav;
		}

		try {
			query = URLDecoder.decode(query, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1);
		}
		
		String result = "";

		try {
			result = repositoryGateway.query(format, query, language);
		} catch (InvalidAttributesException e) {
			mav.addObject("error", messages.getInvalidAttributes());
		} catch (QueryEvaluationException e) {
			mav.addObject("error", messages.getBadQueryEvaluation());
		} catch (MalformedQueryException e) {
			mav.addObject("error", messages.getMalformedQuery());
		} catch (NonSupportedQueryException e) {
			mav.addObject("error", messages.getNonSupportedQuery());
		}

		SparqlSearchForm sparqlSearchForm = new SparqlSearchForm(query, format,
				language, result);

		mav.addObject(sparqlSearchForm);

		return mav;
	}

	@RequestMapping(value = "/sparql", method = RequestMethod.POST)
	public ModelAndView sparqlQueryPost(SparqlSearchForm sparqlSearch,
			Errors err) {

		ModelAndView mav = new ModelAndView();

		sparqlValidator.validate(sparqlSearch, err);

		if (err.hasErrors()) {
			mav.addObject(sparqlSearch);
			return mav;
		}

		try {
			mav.setViewName("redirect:sparql?query=" + URLEncoder.encode(sparqlSearch.getQuery(),"UTF-8")
					+ "&dataformat=" + sparqlSearch.getResponseFormat()
					+ "&language=" + sparqlSearch.getQueryLanguage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}

		return mav;
	}

}