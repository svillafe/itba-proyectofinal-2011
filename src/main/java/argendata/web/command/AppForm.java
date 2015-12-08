package argendata.web.command;

import java.io.IOException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import argendata.model.relational.App;
import argendata.model.relational.Image;
import argendata.service.relational.ArgendataUserService;
import argendata.service.DatasetService;
import argendata.util.Parsing;

public class AppForm {

	private String name;

	private String description;

	private Image icon;

	private String username;

	private MultipartFile logo;

	private MultipartFile screenshot;

	private String url;

	private Boolean isAllowed;

	private String oldTitleIdForEdition;

	private String keyword;
	
	private String dataset;

	public Boolean getIsAllowed() {
		return isAllowed;
	}

	public void setIsAllowed(Boolean isAllowed) {
		this.isAllowed = isAllowed;
	}

	public AppForm(App myApp) {
		super();

		this.keyword = "";
		this.dataset="";
		for (String s : myApp.getKeyword()) {
			keyword += s + ",";
		}
		for (String s : myApp.getDataset()){
			this.dataset +=s + ",";
		}
		
		if (keyword.length() > 0) {
			keyword = keyword.substring(0, keyword.length() - 1);
		}
		if (dataset.length() > 0) {
			dataset = dataset.substring(0, dataset.length() - 1);
		}

		this.name = myApp.getName();
		this.description = myApp.getDescription();
		this.username = myApp.getPublisher().getUsername();
		this.url = myApp.getUrl();
		this.isAllowed = myApp.getIsAllowed();
		this.oldTitleIdForEdition = myApp.getNameId();
	}

	public AppForm() {
		super();
		this.isAllowed = false;
	}

	public MultipartFile getLogo() {
		return logo;
	}

	public void setLogo(MultipartFile logo) {
		this.logo = logo;
	}

	public MultipartFile getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(MultipartFile screenshot) {
		this.screenshot = screenshot;
	}
	

	public String getDataset() {
		return dataset;
	}

	public void setDataset(String dataset) {
		this.dataset = dataset;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Image getIcon() {
		return icon;
	}

	public void setIcon(Image icon) {
		this.icon = icon;
	}

	public void setUsername(String username) {
		this.username = username;

	}

	public String getUsername() {
		return this.username;
	}

	public String getOldTitleIdForEdition() {
		return oldTitleIdForEdition;
	}

	public void setOldTitleIdForEdition(String oldTitleForEdition) {
		this.oldTitleIdForEdition = oldTitleForEdition;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public boolean hayLogo() {
		if (logo.getContentType() == null)
			return false;
		if (logo.getContentType().startsWith("application/octet-stream"))
			return false;
		return true;
	}

	public boolean hayScreen() {
		if (screenshot.getContentType() == null)
			return false;
		if (screenshot.getContentType().startsWith("application/octet-stream"))
			return false;
		return true;
	}

	public App build(ArgendataUserService userService, DatasetService datasetService, Errors err) throws IOException {
		App resp = new App();

		Set<String> setKeyword = new TreeSet<String>();
		Set<String> setDataset = new TreeSet<String>();
		
		
		keyword = keyword.trim();
		Scanner scannerKey = new Scanner(keyword);
		scannerKey.useDelimiter(",");
		String aKeyword;
		while (scannerKey.hasNext()) {
			aKeyword = scannerKey.next();
			setKeyword.add(aKeyword);
		}
		
		dataset = dataset.trim();
		Scanner scannerData = new Scanner(dataset);
		scannerData.useDelimiter(",");
		String aData;
		while (scannerData.hasNext()) {
			aData = scannerData.next();
			setDataset.add(aData);
		}
		resp.setDataset(setDataset);

		boolean error=false;
		for(String dataset: setDataset){
		
			if(!datasetService.existApprovedDataset(dataset)){
				error=true;
			}
		}
		if(error){
			err.rejectValue("dataset", "AppForm.invalid.dataset", "Ha ingresado dataset que no existen en el repositorio.");
		}
		
		resp.setNameId(Parsing.withoutSpecialCharacters(name));
		resp.setDescription(description);
		resp.setName(name);
		resp.setUrl(url);

		resp.setKeyword(setKeyword);
		resp.setPoints(0);
		resp.setPublisher(userService.getUserByUsername(username));
		resp.setIsAllowed(isAllowed);

		if (this.hayLogo()) {
			resp.setIcon(new Image(this.logo.getBytes()));
		}
		if (this.hayScreen()) {
			resp.setScreenshot(new Image(screenshot.getBytes()));
		}

		return resp;
	}
}
