package argendata.web.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import argendata.model.DatasetViewDTO;
import argendata.model.dcat.Dataset;
import argendata.model.dcat.Distribution;
import argendata.model.dcat.Download;
import argendata.model.dcat.Feed;
import argendata.model.dcat.WebService;
import argendata.model.foaf.Organization;
import argendata.model.relational.ArgendataUser;
import argendata.model.relational.PreDataset;
import argendata.service.DatasetService;
import argendata.util.Parsing;
import argendata.util.Properties;
import argendata.web.command.BulkUploadForm;
import argendata.web.command.DatasetForm;
import argendata.web.validator.BulkUploadFormValidator;
import argendata.web.validator.DatasetEntryFormValidator;

@Controller
public class DatasetController {

	private DatasetEntryFormValidator validatorDataEntry;
	private DatasetService datasetService;
	private BulkUploadFormValidator validatorBulkUpload;
	private Properties properties;
	private static final Logger logger = Logger
			.getLogger(DatasetController.class);

	@Autowired
	public DatasetController(DatasetEntryFormValidator validatorA,
			BulkUploadFormValidator validatorC, DatasetService datasetService,
			Properties properties) {

		this.datasetService = datasetService;
		this.validatorDataEntry = validatorA;
		this.validatorBulkUpload = validatorC;
		this.properties = properties;

	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView error() {
		return new ModelAndView();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView view(@RequestParam String qName, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("mainURL", properties.getMainURL());

		Dataset retrievedDataset = this.datasetService
				.getApprovedDatasetByQName(qName);

		if (retrievedDataset == null) {
			logger.info("El dataset no existe");
			mav.setViewName("redirect:/bin/dataset/error");
			return mav;
		}

		String format = null;
		if (retrievedDataset.getDistribution() != null
				&& retrievedDataset.getDistribution().getFormat() != null) {
			format = retrievedDataset.getDistribution().getFormat()
					.toLowerCase();
		}

		DatasetViewDTO dview;
		/*
		 * Microsoft Office: doc, docx, xls, xlsx, ppt, pptx, pps; OpenDocument:
		 * odt, ods, odp; OpenOffice:sxw, sxc, sxi; Other Formats: wpd, pdf,
		 * rtf, txt, html, csv, tsv
		 */
		if (format != null
				&& (format.endsWith("doc") || format.endsWith("docx")
						|| format.endsWith("xls") || format.endsWith("xlsx")
						|| format.endsWith("ppt") || format.endsWith("pptx")
						|| format.endsWith("pps") || format.endsWith("odt")
						|| format.endsWith("ods") || format.endsWith("odp")
						|| format.endsWith("swx") || format.endsWith("sxi")
						|| format.endsWith("wpd") || format.endsWith("pdf")
						|| format.endsWith("rtf") || format.endsWith("txt")
						|| format.endsWith("csv") || format.endsWith("tsv"))) {
			dview = new DatasetViewDTO(retrievedDataset, true);
		} else {
			dview = new DatasetViewDTO(retrievedDataset, false);
		}

		mav.addObject("dto", dview);
		String theDate = "";
		/* Se pasa la fecha a un formato entendible por el usuario final */
		String correctDate = dview.getDataset().getModified();
		Scanner scanner = new Scanner(correctDate);
		scanner.useDelimiter("T");
		if (scanner.hasNext()) {
			theDate = scanner.next();
			Scanner scannerDate = new Scanner(theDate);
			scannerDate.useDelimiter("-");
			String year = scannerDate.next();
			String month = scannerDate.next();
			String day = scannerDate.next();
			theDate = year + "-" + month + "-" + day;
		} else {
			logger.error("No se pudo obtener la fecha de la última modificación.");
		}
		mav.addObject("datasetDate", theDate);

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");
		if (user != null && user.isAdmin()) {
			mav.addObject("admin", true);
		} else {
			mav.addObject("admin", false);
		}

		if (user != null) {
			mav.addObject("logged", true);
		} else {
			mav.addObject("logged", false);
		}

		return mav;
	}

	// @RequestMapping(value="/by/title/{title}.rdf", method =
	// RequestMethod.GET)
	// public ResponseEntity<String> rdfxml(@PathVariable String title){
	// QName qName = new QName("http://www.argendata.com/data/", title);
	//
	// String rdfxml = datasetService.getRDF(qName);
	//
	// HttpHeaders responseHeaders = new HttpHeaders();
	// responseHeaders.setContentType(MediaType.APPLICATION_XML);
	//
	// return new ResponseEntity<String>(rdfxml, responseHeaders,
	// HttpStatus.CREATED);
	// }

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView add(HttpSession session) {
		ModelAndView mav = new ModelAndView();

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		DatasetForm dForm = new DatasetForm();

		dForm.setUsername(user.getUsername());

		mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
		mav.addObject("publicKey", properties.getReCaptchaPublicKey());

		mav.addObject(dForm);

		return mav;
	}

	private int getResourceLength(PreDataset ds) {
		// String length = null;

		int len = 0;

		String format = null;
		if (ds.getDistribution() != null && ds.getFormat() != null) {
			format = ds.getFormat().toLowerCase();
		}

		if (format != null
				&& (format.endsWith("doc") || format.endsWith("docx")
						|| format.endsWith("xls") || format.endsWith("xlsx")
						|| format.endsWith("ppt") || format.endsWith("pptx")
						|| format.endsWith("pps") || format.endsWith("odt")
						|| format.endsWith("ods") || format.endsWith("odp")
						|| format.endsWith("swx") || format.endsWith("sxi")
						|| format.endsWith("wpd") || format.endsWith("pdf")
						|| format.endsWith("rtf") || format.endsWith("txt")
						|| format.endsWith("csv") || format.endsWith("tsv"))) {

			// me conecto y pido el content-length
			URL url;
			try {
				url = new URL(ds.getAccessURL());
				URLConnection conn = url.openConnection();

				len = conn.getContentLength();

				if (len != -1) {

					Integer l = (len / 1024);
					// length= l.toString()+"KB";
					len = l;
				} else {
					// length = "desconocido";
					len = 0;
				}

			} catch (MalformedURLException e) {
				logger.error("No se pudo obtener los headers del recurso "
						+ ds.getAccessURL());
				logger.error(e.getMessage(), e);
			} catch (IOException e) {
				logger.error("No se pudo obtener los headers del recurso "
						+ ds.getAccessURL());
				logger.error(e.getMessage(), e);
			}

		} else {
			// length = "-";
			len = 0;
		}

		return len;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView add(DatasetForm datasetEntryForm, Errors errors,
			HttpSession session) {
		ModelAndView mav = new ModelAndView();

		validatorDataEntry.validate(datasetEntryForm, errors);
		if (errors.hasErrors()) {
			logger.error("Errores en la validacion del formulario");
			mav.addObject("privateKey", properties.getReCaptchaPrivateKey());
			mav.addObject("publicKey", properties.getReCaptchaPublicKey());
			mav.setViewName("dataset/add");
			return mav;
		}

		PreDataset aDataset = datasetEntryForm.build();

		// the resource's length will be calculated later on a separate thread.
		aDataset.setSize(0);

		ArgendataUser user = (ArgendataUser) session.getAttribute("user");

		if (!aDataset.getDistribution().equals("Download")) {
			aDataset.setFormat("");
			aDataset.setSize(0);
		}

		if (!user.isAdmin()) {
			try {
				aDataset.setSize(getResourceLength(aDataset));
				datasetService.store(aDataset);
			} catch (Exception e) {
				logger.error("No se pudo agregar el dataset al store relacional");
				logger.error(e.getMessage(), e);
				mav.setViewName("redirect:../static/error");
				return mav;
			}

			mav.setViewName("redirect:../user/home?state=1");
		} else {
			Dataset readyDataset = aDataset.toNoRelationalDataset();

			try {
				datasetService.store(readyDataset);
			} catch (Exception e) {
				logger.error("No se pudo agregar el dataset al store semántico");
				logger.error(e.getMessage(), e);
				mav.setViewName("redirect:../static/error");
				
				return mav;
			}

			mav.setViewName("redirect:../search/searchDataHome");
		}

		asyncResourceLength(aDataset, user.isAdmin());

		return mav;
	}

	private void asyncResourceLength(final PreDataset aDataset,
			final boolean isAdmin) {

		Runnable r = new Runnable() {

			@Override
			public void run() {

				int length = getResourceLength(aDataset);

				if (isAdmin) {
					// se resuelve en el metodo del controller.
				} else {
					try {
						// se le da un tiempo a que cierre la transaccion aun
						// posiblemente vigente de http.
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						logger.info("interrumpido. nothing to do");
					}
					PreDataset pd = datasetService.asyncGetPreDataset(aDataset
							.getTitle());
					pd.setSize(length);
					datasetService.asyncUpdatePreDataset(aDataset);
				}
			}
		};

		new Thread(r).start();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView findAll() {
		ModelAndView mav = new ModelAndView();

		this.datasetService.cleanIndex();

		Iterable<Dataset> datasetsResults = datasetService
				.getAllApprovedDatasets();

		Iterator<Dataset> iterator = datasetsResults.iterator();

		List<Dataset> data = new ArrayList<Dataset>();

		while (iterator.hasNext()) {

			data.add((Dataset) iterator.next());
		}

		mav.addObject("datasets", data);

		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView bulkUpload() {
		ModelAndView mav = new ModelAndView();
		mav.addObject(new BulkUploadForm());
		return mav;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView bulkUpload(HttpSession session,
			BulkUploadForm bulkUploadForm, Errors err) {

		MultipartFile file = null;
		ModelAndView mav = new ModelAndView();

		file = bulkUploadForm.getFile();

		validatorBulkUpload.validate(bulkUploadForm, err);
		if (err.hasErrors()) {
			return mav;
		}

		String error = null;

		if (file.isEmpty()) {

			logger.error("Escriba una ruta valida!");
			error = "Error. El archivo esta vacio.";
			mav.addObject("error", error);
			return mav;
		}

		int count = 0;

		/*
		 * Titulo, Licencia, Tags (Separadas con ;),Calidad de la inforamci�n,
		 * Modificado, Granularidad Geografica, Granularidad Temporal,
		 * Publicante, URL de Acceso, Tamaño, Formato, Tipo de Distribucion,
		 * locacion, descripcion
		 */

		try {
			InputStream inputStream = file.getInputStream();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(inputStream));

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.isEmpty() || line.startsWith("#")) {
					continue; // es comentario o line en blanco
				}

				String[] attrs = line.split(";");

				if (attrs.length < 12) {
					throw new Exception("Linea " + (count + 1)
							+ " mal formada.");
				}
				/* Titulo */
				String title = attrs[0].trim();

				/* Licence */
				String license = attrs[1].trim();

				/* Tags */
				Set<String> tags = new HashSet<String>();
				String[] items = attrs[2].split(",");
				int cantTags = 0;
				logger.debug(items.length);
				while (cantTags < items.length) {
					tags.add(items[cantTags].trim());
					cantTags++;
				}

				/* Quality of the Information */
				String qualityOfInformation = attrs[3].trim();
				if (!(qualityOfInformation.equals("Baja")
						|| qualityOfInformation.equals("Media") || qualityOfInformation
						.equals("Alta"))) {
					throw new Exception("Linea " + (count + 1)
							+ " mal formada, calidad de informacion invalida");
				}

				/* Modify */
				String[] sels = attrs[4].split("-");

				if (sels.length < 3) {
					throw new Exception("Fecha invalidad en la linea "
							+ (count + 1));
				}

				String yearModify = sels[0];
				String monthModify = sels[1];
				String dayModify = sels[2];

				dayModify = dayModify.length() == 1 ? "0" + dayModify
						: dayModify;
				monthModify = monthModify.length() == 1 ? "0" + monthModify
						: monthModify;

				/*
				 * Date fechaModificado = Date.valueOf(anioModificado + "-" +
				 * mesModificado + "-" + diaModificado);
				 */
				// String fechaModificado = diaModificado + "-" + mesModificado
				// + "-" + anioModificado;
				String dateModify = yearModify + "-" + monthModify + "-"
						+ dayModify + "T23:59:59Z";
				// String fechaModificado = "1995-12-31T23:59:59Z";
				/* Granularidad Geografica */
				String granuGeogra = attrs[5].trim();

				/* Granularidad Temporal */
				String granuTemporal = attrs[6].trim();

				/* Publicante */
				String publicante = attrs[7].trim();
				Organization miPublicante = new Organization();
				miPublicante.setName(publicante);
				miPublicante.setNameId(Parsing.withoutSpecialCharacters(publicante));

				/* URL de Acceso */
				String url = attrs[8].trim();

				/* Tamaño */
				String tamanio = attrs[9].trim();

				/* Formato */
				String formato = attrs[10].trim();

				/* Tipo de Distribucion */
				Distribution distribution;

				String tipoDistribucion = attrs[11].trim();
				if (tipoDistribucion.equals("Feed")) {
					distribution = new Feed();
				} else if (tipoDistribucion.equals("Download")) {
					distribution = new Download();
				} else if (tipoDistribucion.equals("Web Service")) {
					distribution = new WebService();
				} else {
					throw new Exception("Linea " + (count + 1)
							+ " mal formada, tipo informacion invalida");
				}
				distribution.setAccessURL(url);
				distribution.setFormat(formato);
				distribution.setSize(new Integer(tamanio));

				/* Locacion */
				String location = attrs[12].trim();
				if (location.equals("")) {
					location = "Desconocida";
				}

				/* Descripcion */
				String description = "";
				for (int i = 13; i < attrs.length; i++) {
					description = description + attrs[i];
					if (i != attrs.length - 1) {
						description = description + "; ";
					}
				}

				Dataset aDataset = new Dataset(title, description, license,
						tags, qualityOfInformation, dateModify, granuGeogra,
						granuTemporal, location, miPublicante, distribution,
						Parsing.withoutSpecialCharacters(title));

				if (!datasetService.existApprovedDataset(title)) {
					datasetService.store(aDataset);
					count++;
				}
			}
		} catch (Exception a) {
			logger.error("Error en la carga masiva.");
			logger.error(a.getMessage(), a);
			error = a.getMessage();
			count = 0;
			mav.addObject("error", error);

		}

		if (count > 0) {
			mav.addObject("info", "Agrego " + count
					+ " dataset's exitosamente.");
		}

		if (count == 0) {
			mav.addObject("info", "No había datasets nuevos para agregar.");
		}
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView exportDatasetsMenu() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void exportDatasets(HttpServletRequest request,
			HttpServletResponse response) {

		response.setContentType("application/octet-stream");

		Iterable<Dataset> listDataset = this.datasetService
				.getAllApprovedDatasets();

		String text ="";
		for (Dataset d : listDataset) {
			String line = "";
			line = d.getTitle() + ";";
			line += d.getLicense() + ";";
			Set<String> myTags = d.getKeyword();
			for (String s : myTags) {
				line += s + ",";
			}
			line = line.substring(0, line.length() - 1);
			line += ";";
			line += d.getDataQuality() + ";";
			line += d.getModified().substring(0, 10) + ";";
			line += d.getSpatial() + ";";
			line += d.getTemporal() + ";";
			line += d.getPublisherName() + ";";
			line += d.getAccessURL() + ";";
			line += d.getSize() + ";";
			line += d.getFormat() + ";";
			if (d.getDistribution() instanceof Feed) {
				line += "Feed,";
			} else if (d.getDistribution() instanceof WebService) {
				line += "Web Service;";
			} else {
				line += "Download;";
			}
			line += d.getLocation() + ";";
			line += d.getDescription();
			line += "\n";
			text+= line;
		}
		response.setHeader("Content-Disposition","attachment; filename=\"Datasets.csv");
		response.setContentLength(text.length());
		
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(text.getBytes());
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error(e.getStackTrace());
		}

	}

}
