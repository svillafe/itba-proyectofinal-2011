package argendata.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.web.command.ChangePasswordForm;
import argendata.web.command.DatasetForm;
import argendata.web.command.MainPageForm;


@Component
public class ChangePasswordFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.equals(clazz);
	}

	public void validate(Object arg0, Errors err) {
				
		ChangePasswordForm form = (ChangePasswordForm)arg0;
		
		if(form.getNewPassword()==null || form.getNewPassword().isEmpty()){
			err.rejectValue("newPassword", "ChangePasswordForm.invalid.newPassword", "Ingrese la nueva contraseña.");
		}
		
		if(form.getOldPassword()==null || form.getOldPassword().isEmpty()){
			err.rejectValue("oldPassword", "ChangePasswordForm.invalid.newPassword","Complete la vieja contraseña.");
		}
		
		if(form.getReNewPassword()==null || form.getReNewPassword().isEmpty()){
			err.rejectValue("reNewPassword", "ChangePasswordForm.invalid.newPassword","Debe reingresar la contraseña.");
		}
		
		if(form.getNewPassword()!=null && (form.getNewPassword().length()<6 || form.getNewPassword().length()>16)){
			err.rejectValue("newPassword", "ChangePasswordForm.invalid.newPassword","La contraseña debe tener entre 6 y 16.");
		}
		
		if(form.getNewPassword()!=null && form.getReNewPassword()!=null && form.getReNewPassword().equals(form.getNewPassword())==false){
			err.rejectValue("newPassword", "ChangePasswordForm.invalid.newPassword", "Las contraseñas no coinciden.");
		}
	}

}
