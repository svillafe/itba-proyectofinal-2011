package argendata.web.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.service.relational.ArgendataUserService;
import argendata.web.command.UserForm;


@Component
public class UserRegisterFormValidator implements Validator {

	private ArgendataUserService argendataUserService;
	private static Pattern pattern = Pattern
			.compile("^(([A-Za-z0-9]+_+)|([A-Za-z0-9]+\\-+)|([A-Za-z0-9]+\\.+)"
					+ "|([A-Za-z0-9]+\\++))*[A-Za-z0-9]+@((\\w+\\-+)|(\\w+\\.))*"
					+ "\\w{1,63}\\.[a-zA-Z]{2,6}$");

	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@Autowired
	public UserRegisterFormValidator(ArgendataUserService argendataUserService){
		this.argendataUserService = argendataUserService;
	}
	
	public void validate(Object obj, Errors err) {
		
		UserForm form = (UserForm)obj;
		
		if(form.getUsername()==null || form.getUsername().isEmpty()){
			err.rejectValue("username", "UserForm.empty.username", "Obligatorio.");
		}
		
		if(form.getUsername()!=null && (form.getUsername().length()<5 || form.getUsername().length()>16)){
			err.rejectValue("username", "UserForm.length.username", "Debe tener entre 5 y 16 caracteres.");
		}
		
		if(form.getUsername()!=null && !form.getUsername().matches("[a-zA-Z0-9]+")){
			err.rejectValue("username", "UserForm.wrongFormat.username", "Solo caracteres alfanumericos.");
		}
		
		if(form.getUsername()!=null && argendataUserService.existUsername(form.getUsername())){
			err.rejectValue("username", "UserForm.invalid.username", "Un usuario con ese nombre ya existe.");
		}
		
		if(form.getLastName()==null || form.getLastName().isEmpty()){
			err.rejectValue("lastName", "UserForm.empty.lastName", "Obligatorio.");
		}
		
		if(form.getName()==null || form.getName().isEmpty()){
			err.rejectValue("name", "UserForm.empty.name", "Obligatorio.");
		}

		if(form.getLastName()!=null && form.getLastName().length() >50){
			err.rejectValue("name", "UserForm.length.lastName", "Hasta 50 caracteres.");
		} 
		
		if(form.getName()!=null && form.getName().length() >50){
			err.rejectValue("name", "UserForm.length.name", "Hasta 50 caracteres.");
		} 
		
		if(form.getPassword()==null || form.getPassword().isEmpty()){
			err.rejectValue("password", "UserForm.empty.password", "Obligatorio.");
		}
		
		if(form.getPassword()!=null && (form.getPassword().length()<6 || form.getPassword().length()>16)){
			err.rejectValue("password", "UserForm.tooLong.password", "El password debe ser en 6 y 16 caracteres de longitud.");
		}
		
		if(form.getRePassword()==null || form.getRePassword().isEmpty()){
			err.rejectValue("rePassword", "UserForm.empty.repassword", "Obligatorio.");
		}
		
		if((form.getPassword()!=null && form.getRePassword()!=null ) && form.getRePassword().equals(form.getPassword())==false){
			err.rejectValue("rePassword", "UserForm.invalid.rePassword", "No coinciden.");			
		}
		
		boolean flag = true;
		
		if(form.getEmail()==null || form.getEmail().isEmpty()){
			err.rejectValue("email", "UserForm.empty.email", "Obligatorio.");
			flag = false;
		}
		
		if (flag && !(pattern.matcher(form.getEmail()).find())) {
			err.rejectValue("email", "UserForm.invalid.email", "No válido.");
		}
		
		if(argendataUserService.existMail(form.getEmail())){
			err.rejectValue("email", "UserForm.duplicate.email", "Ya existe un usuario con dicho email.");
		}
		
	}
}
