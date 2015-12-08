package argendata.web.command;

import argendata.util.Format;
import argendata.util.Language;


public class SparqlSearchForm {

	private String query;
	private Format responseFormat;
	private Language queryLanguage;
	private String result;
	
	public SparqlSearchForm(String query, Format responseFormat, Language queryLanguage, String result) {
		this.query = query;
		this.responseFormat = responseFormat;
		this.queryLanguage = queryLanguage;
		this.result = result;
	}

	public SparqlSearchForm() {
		query = "";
		responseFormat = Format.RDFXML;
		queryLanguage = Language.SPARQL;
		result = "";
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Format getResponseFormat() {
		return responseFormat;
	}

	public void setResponseFormat(Format responseFormat) {
		this.responseFormat = responseFormat;
	}

	public Language getQueryLanguage() {
		return queryLanguage;
	}

	public void setQueryLanguage(Language queryLanguage) {
		this.queryLanguage = queryLanguage;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
