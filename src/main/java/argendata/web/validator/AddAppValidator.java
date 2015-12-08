package argendata.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.service.AppService;
import argendata.web.command.AppForm;

@Component
public class AddAppValidator implements Validator {

	private AppService appService;
	
	@Autowired
	public AddAppValidator(AppService appService){
		this.appService = appService;
	}
	
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AppForm.class.equals(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {

		AppForm appForm = (AppForm)target;
		
		if(appForm.getName()==null || appForm.getName().isEmpty()){
			errors.rejectValue("name", "AppForm.invalid.name","El nombre de la aplicación es obligatorio.");
		}else if( !withoutSpecialCharacters(appForm.getName()).matches("[a-zA-Z0-9,.)(\\[\\]\\- ]+")){
			errors.rejectValue("name", "AppForm.invalid.name","El nombre de la aplicación solo puede contener caracteres alfanumericos, '(', ')', '[', ']' y '-'.");
		}
		
		if( appForm.getName()!=null && (appForm.getName().length() < 8 || appForm.getName().length() > 256)){
			errors.rejectValue("name", "AppForm.invalid.name","El nombre de la aplicación solo puede tener 8 y 256.");
		}
		
		if(appForm.getDataset()==null || appForm.getDataset().isEmpty()){
			errors.rejectValue("dataset", "AppForm.invalid.dataset","Al menos proponga un dataset.");
		}
	
		if(appForm.getDescription()==null || appForm.getDescription().isEmpty()){
			errors.rejectValue("description", "AppForm.invalid.description","La descripcion de la aplicacion es obligatorio.");
		}
		
		if(appForm.getDescription()!=null && appForm.getDescription().length()>600){
			errors.rejectValue("description", "AppForm.invalid.description","La descripcion de la aplicacion puede tener hasta 600 caracteres.");
		}
		
		if(appForm.getUrl()==null || appForm.getUrl().isEmpty()){
			errors.rejectValue("url", "AppForm.invalid.url","La dirección web es obligatoria.");
		}
		
		if(appForm.getUrl()!=null && !appForm.getUrl().startsWith("http")){
			appForm.setUrl("http://"+appForm.getUrl());
		}
		
		if(appForm.getUrl()!=null && appForm.getUrl().length() > 255){
			errors.rejectValue("url", "AppForm.invalid.url","La dirección debe ser menor a 255 caracteres.");
		}
		
		if(appForm.getKeyword()==null || appForm.getKeyword().isEmpty()){
			errors.rejectValue("keyword", "AppForm.invalid.keyword","Al menos proponga una palabra clave.");
		}
		
		if(appForm.getLogo().getSize()>204800){
			errors.rejectValue("logo", "AppForm.invalid.logo.size","El logo supera el tamaño maximo de 200 KB.");
		}
		
		if(appForm.getScreenshot().getSize()>204800){
			errors.rejectValue("logo", "AppForm.invalid.screenshot.size","El screenshot supera el tamaño maximo de 200 KB.");
		}
		
		
		if(appService.exist(appForm.getName())){
			errors.rejectValue("name", "AppForm.invalid.name","El nombre de la aplicación ya existe.");
		}
		
	}
	
	public String withoutSpecialCharacters(String title) {
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

		return resp;
	}
}
