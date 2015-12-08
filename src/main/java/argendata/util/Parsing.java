package argendata.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parsing {

	/* You can not instantiate this class */
	private Parsing() {

	}

	private static final String DefaultResultsPerPage = "10";

	/** List returned: showName, Name, "d" for descending or "a" for ascending */
	public static List<String> parseSortBy(String chain) {

		List<String> answers = new ArrayList<String>();

		try {
			if (chain == null || chain.equals("null") || chain.equals("")) {
				answers.add(0, "Titulo - (Z-A)");
				answers.add(1, "titleId");
				answers.add(2, "a");
				answers.add(3, "Fecha - (Z-A)");
				answers.add(4, "modified");
				answers.add(5, "a");
			} else {
				Scanner scanner = new Scanner(chain);
				scanner.useDelimiter(",");
				while (scanner.hasNext()) {
					String facetAndName = scanner.next();
					Scanner scannerAux = new Scanner(facetAndName);
					scannerAux.useDelimiter(":");
					String field = scannerAux.next();
					String orderTypeValue = scannerAux.next();
					if (orderTypeValue.equals("asc")) {
						if (field.equals("titleId"))
							answers.add("Título" + " - (Z-A)");
						else {
							if (field.equals("modified"))
								answers.add("Fecha" + " - (Z-A)");
							else
								answers.add(field + " - (Z-A)");
						}
						answers.add(field);
						answers.add("a");
					} else {
						if (field.equals("titleId"))
							answers.add("Título" + " - (A-Z)");
						else {
							if (field.equals("modified"))
								answers.add("Fecha" + " - (A-Z)");
							else
								answers.add(field + " - (A-Z)");
						}
						answers.add(field);
						answers.add("d");
					}
				}

			}
			/* If anything goes wrong with the scanner, sort by title */
		} catch (Exception e) {
			e.printStackTrace();
			answers.add(0, "Título - (Z-A)");
			answers.add(1, "titleId");
			answers.add(2, "a");
			answers.add(3, "Fecha - (Z-A)");
			answers.add(4, "modified");
			answers.add(5, "a");
			return answers;
		}

		return answers;
	}

	public static Map<String, String> parseDatesetFilter(String chain) {

		HashMap<String, String> answers = new HashMap<String, String>();

		if ((chain != null) && (!chain.equals(""))) {
			Scanner scanner = new Scanner(chain);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				String facetAndName = scanner.next();
				Scanner scannerAux = new Scanner(facetAndName);
				scannerAux.useDelimiter(":");
				String facet = scannerAux.next();
				String facetValue = scannerAux.next();
				answers.put(facet, facetValue);
			}
		}

		return answers;

	}

	public static String parsePage(String chain) {

		String response = chain;
		if ((chain == null) || chain.equals(""))
			response = "1";

		Integer pageNumber;
		try {
			pageNumber = new Integer(chain);
			if (pageNumber < 1)
				response = "1";
		} catch (NumberFormatException e) {
			response = "1";
		}

		return response;
	}

	public static String parseResultsPerPage(String chain) {

		String resp = chain;
		if ((chain == null) || chain.equals(""))
			resp = Parsing.DefaultResultsPerPage;

		Integer resultsPerPage;

		try {
			resultsPerPage = new Integer(chain);
			if (resultsPerPage < 1)
				resp = Parsing.DefaultResultsPerPage;
		} catch (Exception e) {
			resp = Parsing.DefaultResultsPerPage;
		}
		return resp;
	}

	public static List<String> parseKeyword(String chain) {
		List<String> listKeywords = new ArrayList<String>();

		if ((chain != null) && (!chain.equals(""))) {
			Scanner scanner = new Scanner(chain);
			scanner.useDelimiter(",");
			while (scanner.hasNext()) {
				String value = scanner.next();
				listKeywords.add(value);
			}
		}
		return listKeywords;
	}

	public static String parseTerms(String terms, String type) {
		String resp = terms;
		if (terms == null && type.equals("app")) {
			resp = "";
		} else if (terms == null && type.equals("dataset")) {
			resp = "*";
		}
		
		if (terms != null) {

			resp = resp.replace('\u00E1', 'a');
			resp = resp.replace('\u00C1', 'a');
			resp = resp.replace('\u00E9', 'e');
			resp = resp.replace('\u00EB', 'e');
			resp = resp.replace('\u00C9', 'e');
			resp = resp.replace('\u00CB', 'e');
			resp = resp.replace('\u00ED', 'i');
			resp = resp.replace('\u00CD', 'i');
			resp = resp.replace('\u00F3', 'o');
			resp = resp.replace('\u00D3', 'o');
			resp = resp.replace('\u00FA', 'u');
			resp = resp.replace('\u00FC', 'u');
			resp = resp.replace('\u00DA', 'u');
			resp = resp.replace('\u00DC', 'u');
		}
		
		return resp;
	}

	public static String withoutSpecialCharacters(String title) {
		String resp = title;
		
		resp = resp.replace('\u00E1', 'a');
		resp = resp.replace('\u00C1', 'a');
		resp = resp.replace('\u00E9', 'e');
		resp = resp.replace('\u00EB', 'e');
		resp = resp.replace('\u00C9', 'e');
		resp = resp.replace('\u00CB', 'e');
		resp = resp.replace('\u00ED', 'i');
		resp = resp.replace('\u00CD', 'i');
		resp = resp.replace('\u00F3', 'o');
		resp = resp.replace('\u00D3', 'o');
		resp = resp.replace('\u00FA', 'u');
		resp = resp.replace('\u00FC', 'u');
		resp = resp.replace('\u00DA', 'u');
		resp = resp.replace('\u00DC', 'u');
		resp = resp.replace('\u00F1', 'n');
		resp = resp.replace(' ', '-');
		return resp;
	}

}