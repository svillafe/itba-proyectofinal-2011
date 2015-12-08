package argendata.web.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import argendata.model.relational.ArgendataUser;
import argendata.service.relational.ArgendataUserService;
import argendata.web.command.UserForm;

@Component
public class UpdateUserValidator {

	private static Pattern pattern = Pattern
			.compile("^(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\\-+)|([A-Za-z0-9]+\\.+)"
					+ "|([A-Za-z0-9]+\\++))*[A-Za-z0-9]+@((\\w+\\-+)|(\\w+\\.))*"
					+ "\\w{1,63}\\.[a-zA-Z]{2,6}$");

	
	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@SuppressWarnings("unused")
	public void validate(Object obj, Errors err, ArgendataUser oldUser,
			ArgendataUserService argendataUserService) {

		UserForm form = (UserForm) obj;


		/*if (form.getUsername() == null || form.getUsername().isEmpty()) {
			err.rejectValue("username", "UserForm.invalid.username",
					"Obligatorio.");
		}

		if (!form.getUsername().equals(oldUser.getUsername())) {
			if (form.getUsername() != null
					&& argendataUserService.existUsername(form.getUsername())) {
				err.rejectValue("username",
						"UserForm.invalid.username",
						"Un usuario con ese nombre ya existe.");
			}
		}*/
		
		if (form.getLastName() == null || form.getLastName().isEmpty()) {
			err.rejectValue("lastName", "UserForm.invalid.lastName",
					"Obligatorio.");
		}

		if (form.getName() == null || form.getName().isEmpty()) {
			err.rejectValue("name", "UserForm.invalid.name",
					"Obligatorio.");
		}

		boolean flag = true;
		
		if(form.getEmail()==null || form.getEmail().isEmpty()){
			err.rejectValue("email", "UserForm.empty.email", "Obligatorio.");
			flag = false;
		}
		
		if (flag && !(pattern.matcher(form.getEmail()).find())) {
			err.rejectValue("email", "UserForm.invalid.email", "No válido.");
		}
		
		if (form.getFacebook() == null || form.getFacebook().length() > 200) {
			err.rejectValue("minibio", "UserForm.invalid.facebook",
					"Muy largo, hasta 200 caracteres.");
		}
		
		if (form.getTwitter() == null || form.getTwitter().length() > 200) {
			err.rejectValue("minibio", "UserForm.invalid.twitter",
					"Muy largo, hasta 200 caracteres.");
		}
		
		if (form.getMinibio() == null || form.getMinibio().length() > 250) {
			err.rejectValue("minibio", "UserRegisterForm.invalid.minibio",
					"Muy largo, hasta 250 caracteres.");
		}

		/*if(form.getUsername()!=null && !form.getUsername().matches("[a-zA-Z0-9]+")){
			err.rejectValue("username", "UserForm.wrongFormat.username", "Solo puede contener caracteres alfanumericos.");
		}*/

		if(form.getLastName()!=null && form.getLastName().length() >50){
			err.rejectValue("name", "UserForm.length.lastName", "Hasta 50 caracteres.");
		} 
		
		if(form.getName()!=null && form.getName().length() >50){
			err.rejectValue("name", "UserForm.length.name", "Hasta 50 caracteres.");
		} 
		
	}
}
