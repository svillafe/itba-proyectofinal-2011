package argendata.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.model.argendata.Semanticapp;
import argendata.model.dcat.Dataset;
import argendata.model.relational.ArgendataUser;
import argendata.model.relational.RelationalApp;
import argendata.service.AppService;
import argendata.service.DatasetService;
import argendata.service.relational.impl.ArgendataUserServiceImpl;

@Service
public class DataSession {

	private List<Dataset> datasets;
	private List<Semanticapp> apps;
	private Date cacheExpiration;
	private int usersQty;
	private int datasetsQty;
	private int appsQty;
	private DatasetService datasetService;
	private AppService appService;
	private ArgendataUserServiceImpl userService;
	private Stack<Integer> sevendays;
	private Map<String, Integer> provincias;
	private List<ArgendataUser> topDatasetsPublishers;
	private Properties properties;

	private final Logger logger = Logger.getLogger(DataSession.class);
	private Date diaryExpiration;
	private int oldQty;

	@Autowired
	public DataSession(DatasetService datasetService,
			AppService appService, ArgendataUserServiceImpl userService,
			Properties properties) {
		this.datasets = new ArrayList<Dataset>();
		this.apps = new ArrayList<Semanticapp>();
		this.cacheExpiration = GregorianCalendar.getInstance().getTime();
		this.diaryExpiration = GregorianCalendar.getInstance().getTime();
		this.usersQty = 0;
		this.datasetsQty = 0;
		this.appsQty = 0;
		this.oldQty = 0;
		this.datasetService = datasetService;
		this.appService = appService;
		this.userService = userService;
		this.sevendays = new Stack<Integer>();
		this.provincias = new HashMap<String, Integer>();
		this.properties = properties;

		for (int i = 0; i < 7; i++) {
			sevendays.push(0);
		}

	}

	private void getProvinciasQty() {
		provincias.put("Buenos Aires",
				datasetService.getApprovedDatasetsFromLocation("Buenos Aires"));
		provincias
				.put("Catamarca", datasetService.getApprovedDatasetsFromLocation("Catamarca"));
		provincias.put("Chaco", datasetService.getApprovedDatasetsFromLocation("Chaco"));
		provincias.put("Chubut", datasetService.getApprovedDatasetsFromLocation("Chubut"));
		provincias.put("Ciudad de Buenos Aires", datasetService.getApprovedDatasetsFromLocation("Ciudad de Buenos Aires"));
		provincias.put("Cordoba", datasetService.getApprovedDatasetsFromLocation("Cordoba"));
		provincias.put("Corrientes",
				datasetService.getApprovedDatasetsFromLocation("Corrientes"));
		provincias.put("Entre Rios",
				datasetService.getApprovedDatasetsFromLocation("Entre Rios"));
		provincias.put("Formosa", datasetService.getApprovedDatasetsFromLocation("Formosa"));
		provincias.put("Jujuy", datasetService.getApprovedDatasetsFromLocation("Jujuy"));
		provincias.put("La Pampa", datasetService.getApprovedDatasetsFromLocation("La Pampa"));
		provincias.put("La Rioja", datasetService.getApprovedDatasetsFromLocation("La Rioja"));
		provincias.put("Mendoza", datasetService.getApprovedDatasetsFromLocation("Mendoze"));
		provincias.put("Misiones", datasetService.getApprovedDatasetsFromLocation("Misiones"));
		provincias.put("Neuquen", datasetService.getApprovedDatasetsFromLocation("Neuquen"));
		provincias
				.put("Rio Negro", datasetService.getApprovedDatasetsFromLocation("Rio Negro"));
		provincias.put("Salta", datasetService.getApprovedDatasetsFromLocation("Salta"));
		provincias.put("San Juan", datasetService.getApprovedDatasetsFromLocation("San Juan"));
		provincias.put("San Luis", datasetService.getApprovedDatasetsFromLocation("San Luis"));
		provincias.put("Santa Cruz",
				datasetService.getApprovedDatasetsFromLocation("Santa Cruz"));
		provincias.put("Santa Fe", datasetService.getApprovedDatasetsFromLocation("Santa Fe"));
		provincias.put("Santiago del Estero",
				datasetService.getApprovedDatasetsFromLocation("Santiago del Estero"));
		provincias.put("Tierra del Fuego",
				datasetService.getApprovedDatasetsFromLocation("Tierra del Fuego"));
		provincias.put("Tucuman", datasetService.getApprovedDatasetsFromLocation("Tucuman"));

	}

	public void update() {
		
		
		if (cacheExpiration.before(GregorianCalendar.getInstance().getTime())) {
			// expiró => actualizar
			datasets = datasetService.getSomeApprovedDatasets();
			apps = appService.getSomeApprovedApps();
			cacheExpiration = new Date(System.currentTimeMillis()
					+ Long.valueOf(properties.getExpirationMillis())); // 86400000
																		// es un
																		// dia
			logger.info("Actualizado");

			datasetsQty = datasetService.getApprovedDatasetQuantity();
			appsQty = appService.getApprovedAppQuantity();
			usersQty = userService.getQty();
			
			getProvinciasQty();
			topDatasetsPublishers = userService.topDatasetsPublishers(5);
		}
		
		// calculo de la progresion diaria de datasets
		if(diaryExpiration.before(GregorianCalendar.getInstance().getTime())) {
			diaryExpiration = new Date(System.currentTimeMillis()
					+ Long.valueOf(properties.getDiaryMillis())); 
			
			datasetsQty = datasetService.getApprovedDatasetQuantity();
			
			sevendays.pop();
			sevendays.push(datasetsQty - oldQty);

			oldQty = datasetsQty;
		}
	}

	public List<ArgendataUser> getTopDatasetsPublishers() {
		return topDatasetsPublishers;
	}

	public void setTopDatasetsPublishers(
			List<ArgendataUser> topDatasetsPublishers) {
		this.topDatasetsPublishers = topDatasetsPublishers;
	}

	public Map<String, Integer> getProvincias() {
		return provincias;
	}

	public void setProvincias(Map<String, Integer> provincias) {
		this.provincias = provincias;
	}

	public Stack<Integer> getSevendays() {
		return sevendays;
	}

	public void setSevendays(Stack<Integer> sevendays) {
		this.sevendays = sevendays;
	}

	public List<Dataset> getDatasets() {
		return datasets;
	}

	public void setDatasets(List<Dataset> datasets) {
		this.datasets = datasets;
	}

	public List<Semanticapp> getApps() {
		return apps;
	}

	public void setApps(List<Semanticapp> apps) {
		this.apps = apps;
	}

	public Date getExpiration() {
		return cacheExpiration;
	}

	public void setExpiration(Date expiration) {
		this.cacheExpiration = expiration;
	}

	public int getUsersQty() {
		return usersQty;
	}

	public void setUsersQty(int usersQty) {
		this.usersQty = usersQty;
	}

	public int getDatasetsQty() {
		return datasetsQty;
	}

	public void setDatasetsQty(int datasetsQty) {
		this.datasetsQty = datasetsQty;
	}

	public int getAppsQty() {
		return appsQty;
	}

	public void setAppsQty(int appsQty) {
		this.appsQty = appsQty;
	}

}
