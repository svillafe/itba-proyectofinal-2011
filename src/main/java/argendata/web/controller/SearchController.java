package argendata.web.controller;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import argendata.model.FacetAppResponse;
import argendata.model.FacetCount;
import argendata.model.FacetDatasetResponse;
import argendata.model.FacetPair;
import argendata.model.OrderType;
import argendata.model.dcat.Dataset;
import argendata.service.AppService;
import argendata.service.DatasetService;
import argendata.util.Messages;
import argendata.util.Parsing;
import argendata.util.Properties;

@Controller
public class SearchController {

	private AppService appService;
	private DatasetService datasetService;
	private Properties properties;
	private Messages messages;
	private final Logger logger = Logger.getLogger(SearchController.class);

	@Autowired
	public SearchController(AppService appService,
			DatasetService datasetService, Properties properties,
			Messages messages) {

		this.properties = properties;
		this.appService = appService;
		this.datasetService = datasetService;
		this.messages = messages;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView searchAppMain() {
		ModelAndView mav = new ModelAndView();

		List<FacetPair> resp = new ArrayList<FacetPair>();
		List<FacetCount> solrKeywords = appService.getIndexAllAppKeywords();

		for (FacetCount c : solrKeywords) {
			resp.add(new FacetPair(c.getName(), (int) c.getCount(), null, null));
		}

		mav.addObject("myapps", resp);
		return mav;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView searchDataHome() {
		ModelAndView mav = new ModelAndView();

		List<FacetPair> respKw = new ArrayList<FacetPair>();
		List<FacetPair> respFormat = new ArrayList<FacetPair>();
		List<FacetPair> respRt = new ArrayList<FacetPair>();

		List<Count> solrKeywords = null;
		List<Count> solrFormats = null;
		List<Count> solrLocations = null;
		try {
			solrKeywords = datasetService.getAllIndexKeywords();
			solrFormats = datasetService.getAllIndexFormats();
			solrLocations = datasetService.getIndexAllLocations();
		} catch (MalformedURLException e) {
			logger.error("No se han podido obtener datos del servicio. "
					+ e.getMessage());
			mav.setViewName("redirect:../main/home");
			return mav;
		} catch (SolrServerException e) {
			logger.error("No se han podido obtener datos del servicio. "
					+ e.getMessage());
			mav.setViewName("redirect:../main/home");
			return mav;
		}
		if (solrKeywords != null) {
			for (Count c : solrKeywords) {
				respKw.add(new FacetPair(c.getName(), (int) c.getCount(), null,
						null));
			}
		}

		if (solrFormats != null) {
			for (Count c : solrFormats) {
				respFormat.add(new FacetPair(c.getName(), (int) c.getCount(),
						null, null));
			}
		}
		if (solrLocations != null) {
			for (Count c : solrLocations) {
				respRt.add(new FacetPair(c.getName(), (int) c.getCount(), null,
						null));
			}
		}

		mav.addObject("listKeywords", respKw);
		mav.addObject("listFormats", respFormat);
		mav.addObject("listLocations", respRt);
		return mav;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView search(
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "terms", required = false) String terms,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "sortBy", required = false) String sortBy,
			@RequestParam(value = "resPerPage", required = false) String resPerPage,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "filters", required = false) String filters) {

		String[] queryFacetsFields = { "dataQuality", "license", "modified",
				"spatial", "temporal", "keyword", "publisher", "resourceType",
				"format", "size", "location" };

		ModelAndView mav = new ModelAndView();

		/* Parsing things */
		if (!(type == null || type.equals("dataset") || type.equals("app") || type
				.equals(""))) {
			mav.setViewName("redirect:../static/error");
			return mav;

		}
		String myTerms;
		if (keyword == null)
			keyword = "";
		if (type == null || type.equals("")) {
			type = "dataset";
		}

		if (type.equals("dataset")) {
			if (terms == null) {
				mav.setViewName("redirect:/bin/search/searchDataHome.jsp");
				return mav;
			}
			if (filters == null || filters.equals("null")) {
				filters = "";
			}
			if (filters.startsWith(","))
				filters = filters.substring(1);
		}
		myTerms = Parsing.parseTerms(terms, type);
		String myPage = Parsing.parsePage(page);
		List<String> mySortByFields = Parsing.parseSortBy(sortBy);
		String myResPerPage = Parsing.parseResultsPerPage(resPerPage);
		List<String> myKeywords = Parsing.parseKeyword(keyword);
		Map<String, String> filterValuesMap = type.equals("dataset") ? Parsing
				.parseDatesetFilter(filters) : null;
		/* The end of parsing */
		String actualURL = properties.getMainURL() + "/search/search?type="
				+ type + "&terms=" + myTerms;

		Integer resultsPerPage = new Integer(myResPerPage);
		String actualPage = "&page=" + myPage;
		String actualSort = "&sortBy=" + sortBy;
		String actualResPerPage = "&resPerPage=" + resultsPerPage;
		String actualKeyword = "&keyword=" + keyword;
		String actualFilters = type.equals("dataset") ? "&filters=" + filters
				: null;
		/* Estas variables solo se deben usar para el caso de Dataset */
		String urlWithoutTerm = "&filters=" + filters + "&page=" + page
				+ "&sortBy=" + sortBy + "&resPerPage=" + resPerPage
				+ "&keyword=" + keyword;
		try {
			/*----------------URL Things and some objects are push--------------------------*/
			List<OrderType> orderAttributesList = this.sortByProcess(type,
					mySortByFields, actualResPerPage, actualKeyword, actualURL,
					actualFilters);
			mav.addObject("orderAttributesList", orderAttributesList);

			String urlWithoutPage;

			if (type.equals("dataset")) {
				urlWithoutPage = actualURL + actualFilters + actualSort
						+ "&page=";
			} else {
				urlWithoutPage = actualURL + actualSort + "&page=";
			}

			/* Obtengo la respuesta de la query */
			FacetAppResponse appResponse = null;
			FacetDatasetResponse datasetResponse = null;
			int objAmount;
			if(type.equals("dataset")){
				String thePublisher = filterValuesMap.get("publisher");
				mav.addObject("thePublisher", thePublisher);
				mav.addObject("publisherSelected",
						this.messages.getPublisherSelected());
			}
			if (type.equals("app")) {
				/* Caso app */
				appResponse = appService.searchOnIndexFacetApp(myTerms,
						mySortByFields, myKeywords);
				objAmount = appResponse.size();
			} else {
				/* Caso dataset */
				datasetResponse = datasetService.searchOnIndexFacetDataset(
						myTerms, queryFacetsFields, filterValuesMap,
						mySortByFields, myKeywords);
				objAmount = datasetResponse.size();
			}

			/* Number page stuff */
			List<String> numberList = new ArrayList<String>();
			int pagesAmount = objAmount / resultsPerPage;
			int pageNumber = new Integer(myPage);

			/*
			 * numberList: esta la lista de los numeros de pagina, de acuerdo a
			 * cuantas paginas haya. Siempre va a haber al menos una. El for,
			 * empieza desde 1, porque siempre va a haber una página, entonces
			 * esa no la cuento.
			 */
			numberList.add((1) + "");
			for (int i = 1; i <= pagesAmount; i++) {
				numberList.add((i + 1) + "");
			}
			/*
			 * Si por la url pusieron un numero de página mayor, acá me redirije
			 * a la pagina 1
			 */
			if (pageNumber > numberList.size()) {
				actualPage = "&page=1";
				pageNumber = 1;
			}

			/* Esto era para un caso limite que no me acuerdo bien */
			if ((pagesAmount * resultsPerPage) > (objAmount - 1))
				numberList.remove(pagesAmount);

			/*
			 * minIndex y maxIndex te dice los índices de los datasets para
			 * mostrar. O sea, si estás en la página 2 y mostrás 5 datasets por
			 * página, el min índex sería el 6 y el maxIndex el 10.
			 */
			int minIndex = (pageNumber - 1) * resultsPerPage;
			int maxIndex = minIndex + resultsPerPage;
			if (maxIndex > objAmount)
				maxIndex = objAmount;

			/*
			 * Acá se usan los minIndex y maxIndex para obtener los datasets, y
			 * además se calcula cual es la página previa y siguiente
			 */

			mav.addObject("cantResults", objAmount);
			
			if (objAmount > 0) {
				if (type.equals("dataset")) {
					if (!terms.equals("*") || !(filters.equals(""))
							|| !(keyword.equals(""))) {
						mav.addObject("datasets", datasetResponse.getDatasets()
								.subList(minIndex, maxIndex));
						mav.addObject("numberList", numberList);

						if ((pageNumber - 1) > 0)
							mav.addObject("prevPage", urlWithoutPage
									+ (pageNumber - 1) + actualResPerPage);

						if ((pageNumber * resultsPerPage) <= (objAmount - 1))
							mav.addObject("nextPage", urlWithoutPage
									+ (pageNumber + 1) + actualResPerPage);
						mav.addObject("showingMin", minIndex+1);
						mav.addObject("showingMax", maxIndex);
					}
				} else {
					if ((pageNumber - 1) > 0)
						mav.addObject("prevPage", urlWithoutPage
								+ (pageNumber - 1) + actualResPerPage);

					if ((pageNumber * resultsPerPage) <= (objAmount - 1))
						mav.addObject("nextPage", urlWithoutPage
								+ (pageNumber + 1) + actualResPerPage);

					/*-------------------------------*/
					mav.addObject("apps",
							appResponse.getApps().subList(minIndex, maxIndex));
					mav.addObject("showingMin", minIndex);
					mav.addObject("showingMax", maxIndex);
				}
			}

			List<FacetPair> currentFilters = new ArrayList<FacetPair>();
			// APP
			// if (objAmount > 0) {
			if (type.equals("app")) {
				mav.addObject(
						"keywordsList",
						getKeywords(urlWithoutPage + page + "&resPerPage="
								+ myResPerPage + "&keyword=", myKeywords,
								appResponse.getKeywords(), currentFilters,
								keyword));
				mav.addObject("currentFiltersList", currentFilters);
			} else {

				// Dataset
				/* Aca se arma la lista de filtros */
				String[] fieldsToMap = { "dataQuality", "license", "spatial",
						"temporal", "publisher", "resourceType", "format",
						"location" };

				/*
				 * Parecido a getFacetPairs pero con los keywords. Se necesita
				 * de una función especial porque el manejo de los keywords es
				 * diferente del de los campos comunes
				 */
				mav.addObject(
						"keywordsList",
						getKeywords(urlWithoutPage + page + "&resPerPage="
								+ myResPerPage + "&keyword=", myKeywords,
								datasetResponse.getFacetCountValues("keyword"),
								currentFilters, keyword));

				StringBuffer beforeFilter = new StringBuffer("");
				StringBuffer afterFilter = new StringBuffer("");

				for (String field : fieldsToMap) {
					mav.addObject(
							field + "List",
							getFacetPairsList(field,
									datasetResponse.getFacetCountValues(field),
									filterValuesMap, actualFilters, actualSort,
									currentFilters, actualURL,
									actualResPerPage, actualKeyword,
									beforeFilter, afterFilter));
				}
				getPublishersURL("", null, filterValuesMap, actualFilters,
						actualSort, currentFilters, actualURL,
						actualResPerPage, actualKeyword, beforeFilter,
						afterFilter);

				mav.addObject("beforeFilter", beforeFilter);
				mav.addObject("afterFilter", afterFilter);
				/*
				 * Years which are filtered Parecido a getFacetPairs pero con
				 * los modified. Se necesita de una función especial porque el
				 * manejo de los modified (Fechas) es diferente del de los
				 * campos comunes
				 */
				mav.addObject(
						"modifiedList",
						getModifDates("modified", getYearsList(),
								filterValuesMap, actualFilters, actualSort,
								currentFilters, actualURL, actualResPerPage,
								actualKeyword));

				mav.addObject(
						"extentionList",
						getModifDates("size", getExtentionList(),
								filterValuesMap, actualFilters, actualSort,
								currentFilters, actualURL, actualResPerPage,
								actualKeyword));
				mav.addObject("currentFiltersList", currentFilters);

			}
			mav.addObject("notFound", this.messages.getNoResults());
			if (type.equals("dataset")) {
				mav.addObject("urlWithoutTerm", urlWithoutTerm);
			}
			mav.addObject("actualResPerPage", actualResPerPage);
			mav.addObject("type", type);
			mav.addObject("searchTerm", terms);
			mav.addObject("actualURL", actualURL);
			mav.addObject("actualPage", actualPage);
			mav.addObject("pageNumber", page);
			mav.addObject("resultsPerPage", myResPerPage);
			mav.addObject("urlWithoutPage", urlWithoutPage);
			mav.addObject("urlWithoutResPerPage", urlWithoutPage + 1
					+ "&resPerPage=");
			mav.addObject("actualKeyword", actualKeyword);
			mav.addObject("numberList", numberList);
		} catch (SolrServerException e) {
			logger.error(e.getMessage());
			mav.setViewName("redirect:/bin/search/search?terms=" + terms);

		} catch (MalformedURLException e) {
			logger.error(e.getMessage());
			mav.setViewName("redirect:/bin/search/search?terms=" + terms);
		}
		return mav;
	}

	private List<String> getYearsList() {
		List<String> yearsList = new ArrayList<String>();

		yearsList.add(Integer.toString(Calendar.getInstance()
				.get(Calendar.YEAR)));
		yearsList.add(Integer.toString(Calendar.getInstance()
				.get(Calendar.YEAR) - 1));
		yearsList.add(Integer.toString(Calendar.getInstance()
				.get(Calendar.YEAR) - 7) + " hasta la actualidad");
		yearsList.add("Todos los datasets");

		return yearsList;
	}

	private List<String> getExtentionList() {
		List<String> extentionList = new ArrayList<String>();

		extentionList.add("Hasta 128kb");
		extentionList.add("Entre 128kb y 512kb");
		extentionList.add("Mas de 512kb");
		extentionList.add("Indefinido");

		return extentionList;
	}

	private List<FacetPair> getModifDates(String facet, List<String> yearsList,
			Map<String, String> filterValuesMap, String actualFilters,
			String actualSort, List<FacetPair> currentFilters,
			String actualURL, String actualResPerPage, String actualKeyword) {

		List<FacetPair> modifiedList = new ArrayList<FacetPair>();
		if (filterValuesMap.containsKey(facet)) {
			String modifiedValue = filterValuesMap.get(facet);
			String newFiltersURL = "&filters=";
			for (String c : yearsList) {
				if (c.equals(modifiedValue)) {
					/*
					 * Armo la URL nueva sin el filtro correspondiente a este
					 * Facet
					 */
					for (String key : filterValuesMap.keySet()) {
						if (!key.equals(facet)) {
							newFiltersURL = newFiltersURL.concat(key + ":"
									+ filterValuesMap.get(key) + ",");
						}
					}
					if (newFiltersURL.endsWith(","))
						newFiltersURL = newFiltersURL.substring(0,
								newFiltersURL.length() - 1);
					modifiedList.add(new FacetPair("(-)" + c, 0, actualURL
							+ newFiltersURL + actualSort + "&page=1"
							+ actualResPerPage + actualKeyword, c));
					currentFilters.add(new FacetPair("(-)" + c, 0, actualURL
							+ newFiltersURL + actualSort + "&page=1"
							+ actualResPerPage + actualKeyword, c));

				}

			}
		} else {
			for (String c : yearsList) {
				if (actualFilters.endsWith("="))
					modifiedList.add(new FacetPair(">>" + c, 0, actualURL
							+ actualFilters + facet + ":" + c + actualSort
							+ "&page=1" + actualResPerPage + actualKeyword, c));
				else
					modifiedList.add(new FacetPair(">>" + c, 0, actualURL
							+ actualFilters + "," + facet + ":" + c
							+ actualSort + "&page=1" + actualResPerPage
							+ actualKeyword, c));
			}
		}

		return modifiedList;
	}

	private List<FacetPair> getKeywords(String urlWithoutKeyword,
			List<String> listKeywords, List<FacetCount> list,
			List<FacetPair> currentFilters, String currentKeywords) {

		List<FacetPair> modifiedList = new ArrayList<FacetPair>();
		String actualKeywordURL = "&keyword=" + currentKeywords;
		String newKeywordURL = "&keyword=";

		if (list == null)
			return modifiedList;

		for (FacetCount c : list) {
			if (listKeywords.contains(c.getName())) {

				newKeywordURL = "";
				/* Armo la nueva URL, sin este par�metro */
				for (String str : listKeywords) {
					if (!(c.getName().equals(str)))
						newKeywordURL = newKeywordURL.concat(str + ",");
				}
				if (newKeywordURL.endsWith(","))
					newKeywordURL = newKeywordURL.substring(0,
							newKeywordURL.length() - 1);
				if(c.getCount() !=0)
				modifiedList.add(new FacetPair("(-)" + c.getName(), (int) c
						.getCount(), urlWithoutKeyword + newKeywordURL, c
						.getName()));
				currentFilters.add(new FacetPair("(-)" + c.getName(), (int) c
						.getCount(), urlWithoutKeyword + newKeywordURL, c
						.getName()));
			} else {

				if (actualKeywordURL.endsWith("=")){
					if(c.getCount() !=0)
					modifiedList.add(new FacetPair(">>" + c.getName(), (int) c
							.getCount(), urlWithoutKeyword + c.getName(), c
							.getName()));
				}else{
					if(c.getCount() !=0)
					modifiedList.add(new FacetPair(">>" + c.getName(), (int) c
							.getCount(), urlWithoutKeyword + currentKeywords
							+ "," + c.getName(), c.getName()));
				}
			}

		}

		return modifiedList;
	}

	private void getPublishersURL(String facet, List<FacetCount> list,
			Map<String, String> filterValuesMap, String actualFilters,
			String actualSort, List<FacetPair> currentFilters,
			String actualURL, String actualResPerPage, String actualKeyword,
			StringBuffer beforeFilter, StringBuffer afterFilter) {

		String newFiltersURL = "&filters=";
		for (String key : filterValuesMap.keySet()) {
			newFiltersURL = newFiltersURL.concat(key + ":"
					+ filterValuesMap.get(key) + ",");
		}
		if (newFiltersURL.endsWith(","))
			newFiltersURL = newFiltersURL.substring(0,
					newFiltersURL.length() - 1);
		if (newFiltersURL.charAt(newFiltersURL.length() - 1) == '=') {
			beforeFilter.append(actualURL + newFiltersURL.concat("publisher:"));
		} else
			beforeFilter
					.append(actualURL + newFiltersURL.concat(",publisher:"));
		afterFilter.append(actualSort + "&page=1" + actualResPerPage
				+ actualKeyword);
		return;
	}

	private List<FacetPair> getFacetPairsList(String facet,
			List<FacetCount> list, Map<String, String> filterValuesMap,
			String actualFilters, String actualSort,
			List<FacetPair> currentFilters, String actualURL,
			String actualResPerPage, String actualKeyword,
			StringBuffer beforeFilter, StringBuffer afterFilter) {

		List<FacetPair> modifiedList = new ArrayList<FacetPair>();
		if (list == null)
			return modifiedList;
		boolean changeFlag = false;
		if (filterValuesMap.containsKey(facet)) {
			String modifiedValue = filterValuesMap.get(facet);
			String newFiltersURL = "&filters=";

			if (facet.equals("publisher")) {
				/*
				 * Armo la URL nueva sin el filtro correspondiente a este Facet
				 */
				for (String key : filterValuesMap.keySet()) {
					if (!key.equals(facet)) {
						newFiltersURL = newFiltersURL.concat(key + ":"
								+ filterValuesMap.get(key) + ",");
					}
				}

				if (newFiltersURL.endsWith(","))
					newFiltersURL = newFiltersURL.substring(0,
							newFiltersURL.length() - 1);
				modifiedList.add(new FacetPair("(-)"
						+ filterValuesMap.get(facet), (int) list.get(0)
						.getCount(), actualURL + newFiltersURL + actualSort
						+ "&page=1" + actualResPerPage + actualKeyword,
						filterValuesMap.get(facet)));
				currentFilters.add(new FacetPair("(-)"
						+ filterValuesMap.get(facet), (int) list.get(0)
						.getCount(), actualURL + newFiltersURL + actualSort
						+ "&page=1" + actualResPerPage + actualKeyword,
						filterValuesMap.get(facet)));

			} else {

				for (FacetCount c : list) {
					if (c.getName().equals(modifiedValue)) {
						/*
						 * Armo la URL nueva sin el filtro correspondiente a
						 * este Facet
						 */
						for (String key : filterValuesMap.keySet()) {
							if (!key.equals(facet)) {
								newFiltersURL = newFiltersURL.concat(key + ":"
										+ filterValuesMap.get(key) + ",");
							}
						}
					}

					if (newFiltersURL.endsWith(","))
						newFiltersURL = newFiltersURL.substring(0, newFiltersURL.length() - 1);
					if(c.getCount() != 0)
					modifiedList.add(new FacetPair("(-)" + c.getName(), (int) c
							.getCount(), actualURL + newFiltersURL + actualSort
							+ "&page=1" + actualResPerPage + actualKeyword, c
							.getName()));
					if(c.getName().equals(filterValuesMap.get(facet)))
					currentFilters.add(new FacetPair("(-)" + c.getName(),
							(int) c.getCount(), actualURL + newFiltersURL
									+ actualSort + "&page=1" + actualResPerPage
									+ actualKeyword, c.getName()));
				}
			}
		} else {
			for (FacetCount c : list) {
				if (actualFilters.endsWith("=")) {
					if(c.getCount() != 0)
					modifiedList.add(new FacetPair(">>" + c.getName(), (int) c
							.getCount(), actualURL + actualFilters + facet
							+ ":" + c.getName() + actualSort + "&page=1"
							+ actualResPerPage + actualKeyword, c.getName()));
				} else {
					if(c.getCount() != 0)
					modifiedList.add(new FacetPair(">>" + c.getName(), (int) c
							.getCount(), actualURL + actualFilters + ","
							+ facet + ":" + c.getName() + actualSort
							+ "&page=1" + actualResPerPage + actualKeyword, c
							.getName()));
				}
			}
		}
		return modifiedList;
	}

	private List<OrderType> sortByProcess(String type,
			List<String> mySortByFields, String actualResPerPage,
			String actualKeyword, String actualURL, String actualFilters) {
		List<OrderType> orderAttributesList = new ArrayList<OrderType>();

		OrderType orderType;
		/*
		 * Inicio de procesamiento de sortby, se sospecha que puede llegar a
		 * juntarse los dos casos
		 */
		if (type.equals("app")) {

			orderType = new OrderType();
			orderType.setShowName(mySortByFields.get(0));
			orderType.setName(mySortByFields.get(1));
			String orderArg = mySortByFields.get(2).equals("a") ? ":desc"
					: ":asc";
			orderType.setDestURL(actualURL + "&sortBy=" + mySortByFields.get(1)
					+ orderArg + "&page=1" + actualResPerPage + actualKeyword);

			orderAttributesList.add(orderType);

		} else {

			int cantOrders = mySortByFields.size();
			for (int i = 0; i < cantOrders; i += 3) {
				orderType = new OrderType();
				orderType.setShowName(mySortByFields.get(i));
				orderType.setName(mySortByFields.get(i + 1));

				String ord = mySortByFields.get(i + 2).equals("a") ? ":desc"
						: ":asc";
				String sortFields = "";
				String destUrl = actualURL + actualFilters + "&sortBy="
						+ mySortByFields.get(i + 1) + ord;
				for (int j = 0; j < mySortByFields.size(); j += 3) {
					if (j != i) {
						sortFields = sortFields.concat(","
								+ mySortByFields.get(j + 1));
						if (mySortByFields.get(j + 2).equals("a"))
							sortFields = sortFields.concat(":asc");
						else
							sortFields = sortFields.concat(":desc");
					}
				}
				destUrl = destUrl.concat(sortFields + "&page=1"
						+ actualResPerPage + actualKeyword);
				orderType.setDestURL(destUrl);
				orderAttributesList.add(orderType);
			}
		}
		return orderAttributesList;
	}

}
