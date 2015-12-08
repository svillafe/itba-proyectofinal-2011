package argendata.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import argendata.web.command.BulkUploadForm;


@Component
public class BulkUploadFormValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return BulkUploadForm.class.equals(clazz);
	}
	
	
	public void validate(Object obj, Errors err) {
	
		BulkUploadForm form = (BulkUploadForm) obj;

		MultipartFile aux = form.getFile();
		
		if (form.hayArchivo()) {
			if (!((aux.getContentType().split("/")[0].startsWith("text"))) && !(aux.getContentType().equals("application/vnd.ms-excel")) ) {
				err.rejectValue("file", "BulkUpload.error","Se produzco un error en la carga del archivo. No es un archivo valido.");
			}
		}else{
			err.rejectValue("file", "BulkUpload.notFile", "Por favor seleccione un archivo antes de apretar Cargar.");
		}
	

	}

	

}