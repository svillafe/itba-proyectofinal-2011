package argendata.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import argendata.model.dcat.Dataset;
import argendata.model.relational.App;
import argendata.model.relational.ArgendataUser;

public class AppTestFactory {

	public static String randomString() {
		Random r = new Random();
		return Long.toString(Math.abs(r.nextLong()), 36);
	}

	public static Set<String> randomKeyword() {
		Random r = new Random();
		int qty = r.nextInt(6);
		Set<String> keywords = new HashSet<String>();

		for (int i = 0; i < qty; i++) {
			keywords.add(Long.toString(Math.abs(r.nextLong()), 12));
		}

		return keywords;
	}

	public static String randomAccessURL() {
		return "http://www." + randomString() + ".com";
	}

	public static App getApprovedApp(Set<Dataset> approvedDatasets,
			ArgendataUser publisher) {

		String name = randomString();
		String nameId = name;
		String description = randomString();
		Boolean isAllowed = true;
		Set<String> keyword = randomKeyword();
		Set<String> datasets = new HashSet<String>();
		for (Dataset a : approvedDatasets) {
			datasets.add(a.getTitle());
		}
		String url = randomAccessURL();
		

		App resp = new App();
		resp.setName(name);
		resp.setDescription(description);
		resp.setIsAllowed(isAllowed);
		resp.setUrl(url);
		resp.setKeyword(keyword);
		resp.setDataset(datasets);
		resp.setNameId(nameId);
		resp.setPublisher(publisher);
		resp.setPoints(0);

		return resp;

	}

	public static App getPreApp(Set<Dataset> approvedDatasets,
			ArgendataUser publisher) {

		String name = randomString();
		String nameId = randomString();
		String description = randomString();
		Boolean isAllowed = false;
		Set<String> keyword = randomKeyword();
		Set<String> datasets = new HashSet<String>();
		for (Dataset a : approvedDatasets) {
			datasets.add(a.getTitle());
		}

		String url = randomAccessURL();

		App resp = new App();
		resp.setName(name);
		resp.setDescription(description);
		resp.setIsAllowed(isAllowed);
		resp.setUrl(url);
		resp.setKeyword(keyword);
		resp.setDataset(datasets);
		resp.setNameId(nameId);
		resp.setPublisher(publisher);

		return resp;

	}

}
