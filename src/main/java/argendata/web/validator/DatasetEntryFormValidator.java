package argendata.web.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.service.DatasetService;
import argendata.web.command.DatasetForm;


@Component
public class DatasetEntryFormValidator implements Validator{

	private DatasetService datasetService;
	
	

	@Autowired
	public DatasetEntryFormValidator(DatasetService datasetService){
		this.datasetService = datasetService;
	}
	
	public boolean supports(Class<?> clazz) {
		return DatasetForm.class.equals(clazz);
	}

	public void validate(Object arg0, Errors err) {
				
		DatasetForm dForm = (DatasetForm)arg0;
		
		if(dForm.getTitle()==null || dForm.getTitle().isEmpty()){
			err.rejectValue("title", "DatasetForm.invalid.title","El Titulo es obligatorio.");
		}else{
			if(!withoutSpecialCharacters(dForm.getTitle()).matches("[a-zA-Z0-9,.)(\\[\\]\\- ]+")){
				err.rejectValue("title", "DatasetForm.invalid.title","El Titulo del dataset  solo puede contener caracteres alfanumericos, '(', ')', '[', ']', ',', '.' y '-'.");
			}
		}
		
		if(dForm.getTitle() != null && (dForm.getTitle().length() < 6 || dForm.getTitle().length()> 256)){
			err.rejectValue("title", "DatasetForm.tooLong.title","El Titulo del dataset debe tener entre 6 y 256 caracteres.");
		}
	
		if(dForm.getPublisher()==null || dForm.getPublisher().isEmpty()){
			err.rejectValue("publisher", "DatasetForm.invalid.publisher","El Publicante es obligatorio.");
		}
		
		if(dForm.getPublisher()!=null && dForm.getPublisher().length()>140){
			err.rejectValue("publisher", "DatasetForm.tooLong.publisher","El Publicante es muy largo.");
		}
		
		if(dForm.getDescription()!=null && dForm.getDescription().length()>600){
			err.rejectValue("description", "DatasetForm.tooLong.description","La descripcion del dataset puede tener hasta 600 caracteres.");
		}
	
		if(dForm.getPublisher()!=null && dForm.getPublisher().contains(":")){
			err.rejectValue("publisher", "DatasetForm.tooLong.publisher","El Publicante no pueden contener ':'.");
		}
		
		if(dForm.getAccessURL()==null || dForm.getAccessURL().isEmpty()){
			err.rejectValue("accessURL", "DatasetForm.invalid.accesURL","La dirección de acceso es obligatorio.");
		}
		
		if(dForm.getAccessURL()!=null && !dForm.getAccessURL().startsWith("http")){
			dForm.setAccessURL("http://"+dForm.getAccessURL());
		}
		
		if(dForm.getAccessURL()!=null && dForm.getAccessURL().length()>500){
			err.rejectValue("accessURL", "DatasetForm.invalid.accesURL","La dirección de acceso supera los 500 caracteres.");
		}
		
		if(dForm.getKeyword()==null || dForm.getKeyword().isEmpty()){
			err.rejectValue("keyword", "DatasetForm.invalid.keyword","Al menos proponga una palabra clave.");
		}
		
		if(datasetService.existApprovedDataset(dForm.getTitle())|| datasetService.existPreDataset(dForm.getTitle())){
			err.rejectValue("title", "DatasetForm.duplicate.title","El titulo ya existe.");
		}
		
		if(dForm.getModified()==null || dForm.getModified().isEmpty()){
			err.rejectValue("modified", "DatasetForm.invalid.modified","El campo modificado es obligatorio.");
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		if(dForm.getModified()!=null){
			try{ 
				format.parse(dForm.getModified());
			}
			catch(ParseException e){ 
				err.rejectValue("modified", "DatasetForm.wrongFormat.modified","El campo modificado no respeta el formato.");
			}
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
