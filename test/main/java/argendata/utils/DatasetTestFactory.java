package argendata.utils;

import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.model.foaf.Organization;
import argendata.model.relational.PreDataset;
import argendata.util.Parsing;

public class DatasetTestFactory {

	public static String randomString() {
		Random r = new Random();
		return Long.toString(Math.abs(r.nextLong()), 36);
	}

	public static String randomLicense() {
		Random r = new Random();
		Double d = r.nextDouble();

		if (d < 0.25) {
			return "desconocida";
		} else if (d < 0.5) {
			return "libre";
		} else if (d < 0.75) {
			return "privado";
		} else {
			return "otra";
		}
	}

	public static String randomKeyword() {
		Random r = new Random();
		int qty = r.nextInt(6);
		String keywords = "";

		for (int i = 0; i < qty; i++) {
			keywords += Long.toString(Math.abs(r.nextLong()), 12) + ",";
		}

		return keywords;
	}

	public static String randomDataQuality() {
		Random r = new Random();
		Double d = r.nextDouble();

		if (d < 0.33) {
			return "Baja";
		} else if (d < 0.66) {
			return "Media";
		} else {
			return "Alta";
		}
	}

	public static String randomModified() {
		Random r = new Random();
		Double d = r.nextDouble();

		return "1999-01-01"+ "T23:59:59Z";
	}

	public static String randomSpatial() {
		Random r = new Random();
		Double d = r.nextDouble();
		
		if (d < 0.33) {
			return "municipal";
		} else if (d < 0.66) {
			return "provincial";
		} else  {
			return "comunal";
		} 
	}

	public static String randomTemporal() {
		Random r = new Random();
		Double d = r.nextDouble();
		
		if (d < 0.33) {
			return "mensual";
		} else if (d < 0.66) {
			return "anual";
		} else {
			return "diaria";
		} 
	}

	public static String randomDistribution() {
		Random r = new Random();
		Double d = r.nextDouble();
		
		if (d < 0.33) {
			return "Feed";
		} else if (d < 0.66) {
			return "Download";
		} else{
			return "Web Service";
		}
	}

	public static String randomAccessURL() {
		return "http://www."+randomString()+".com";
	}

	public static int randomSize() {
		Random r = new Random();
		Double d = r.nextDouble();
		return (int) (d * 1000);
	}

	public static String randomFormat() {
		Random r = new Random();
		Double d = r.nextDouble();
		
		if (d < 0.25) {
			return "XLS";
		} else if (d < 0.5) {
			return "PDF";
		} else if (d < 0.75) {
			return "DOC";
		} else  {
			return "TXT";
		} 
	}

	public static boolean randomApproved() {
		Random r = new Random();
		Double d = r.nextDouble();

		if (d < 0.5) {
			return true;
		} else {
			return false;
		}
	}

	public static String randomDecision() {
		return randomString();
	}

	public static String randomLocation() {
		Random r = new Random();
		Double d = r.nextDouble();

		if (d < 0.04) {
			return "desconocida";
		} else if (d < 0.08) {
			return "Buenos Aires";
		} else if (d < 0.12) {
			return "Catamarca";
		} else if (d < 0.16) {
			return "Chaco";
		} else if (d < 0.20) {
			return "Chubut";
		} else if (d < 0.24) {
			return "Ciudad Autonoma de Buenos Aires";
		} else if (d < 0.28) {
			return "Cordoba";
		} else if (d < 0.32) {
			return "Corrientes";
		} else if (d < 0.36) {
			return "Entre Rios";
		} else if (d < 0.40) {
			return "Formosa";
		} else if (d < 0.44) {
			return "Jujuy";
		} else if (d < 0.48) {
			return "La Pampa";
		} else if (d < 0.52) {
			return "La Rioja";
		} else if (d < 0.56) {
			return "Mendoza";
		} else if (d < 0.60) {
			return "Misiones";
		} else if (d < 0.64) {
			return "Neuquen";
		} else if (d < 0.68) {
			return "Rio Negro";
		} else if (d < 0.72) {
			return "Salta";
		} else if (d < 0.76) {
			return "San Juan";
		} else if (d < 0.80) {
			return "San Luis";
		} else if (d < 0.84) {
			return "Santa Cruz";
		} else if (d < 0.88) {
			return "Santa Fe";
		} else if (d < 0.92) {
			return "Santiago del Estero";
		} else if (d < 0.96) {
			return "Tierra del Fuego";
		} else {
			return "Tucuman";
		}

	}

	public static String randomTitleId() {
		return randomString();
	}

	public static Dataset getDataset() {

		String title = randomString();
		String description = randomString();
		String publisher = randomString();
		String titleId = title;
		String license = randomLicense();
		String keyword = randomKeyword();
		String dataQuality = randomDataQuality();
		String modified = randomModified();
		String spatial = randomSpatial();
		String temporal = randomTemporal();
		String distribution = randomDistribution();
		String accessURL = randomAccessURL();
		int size = randomSize();
		String format = randomFormat();
		String location = randomLocation();

		Organization miPublisher = new Organization();
		miPublisher.setName(publisher);
		miPublisher.setNameId(Parsing.withoutSpecialCharacters(publisher));

		Distribution miDistribution;

		if (distribution.equals("Feed")) {
			miDistribution = new Feed();
		} else if (distribution.equals("Download")) {
			miDistribution = new Download();
		} else {
			miDistribution = new WebService();
		}

		miDistribution.setAccessURL(accessURL);
		miDistribution.setFormat(format);
		miDistribution.setSize(size);

		Dataset ds = new Dataset();
		ds.setTitle(title);
		ds.setDescription(description);
		ds.setDataQuality(dataQuality);
		ds.setDistribution(miDistribution);
		ds.setTitleId(titleId);

		Set<String> set = new TreeSet<String>();
		keyword = keyword.trim();

		Scanner scanner = new Scanner(keyword);
		scanner.useDelimiter(",");
		String aKeyword;
		while (scanner.hasNext()) {
			aKeyword = scanner.next();
			set.add(aKeyword);
		}

		ds.setKeyword(set);

		ds.setLicense(license);
		ds.setModified(modified);
		ds.setPublisher(miPublisher);
		ds.setSpatial(spatial);
		ds.setTemporal(temporal);
		ds.setTitle(title);
		ds.setLocation(location);

		return ds;
	}

	public static PreDataset getPreDataset() {
		PreDataset preDataset = new PreDataset();

		preDataset.setDataQuality(randomDataQuality());
		preDataset.setDistribution(randomFormat());
		preDataset.setKeyword(randomKeyword());
		preDataset.setLicense(randomLicense());
		preDataset.setModified(randomModified());
		preDataset.setPublisher(randomString());
		preDataset.setSpatial(randomSpatial());
		preDataset.setTemporal(randomTemporal());
		preDataset.setTitle(randomString());
		preDataset.setUsername(randomString());
		preDataset.setAccessURL(randomAccessURL());
		preDataset.setFormat(randomString());
		preDataset.setLocation(randomLocation());
		preDataset.setDescription(randomString());
		preDataset.setTitleId(Parsing.withoutSpecialCharacters(preDataset.getTitle()));

		return preDataset;
	}
	
}
