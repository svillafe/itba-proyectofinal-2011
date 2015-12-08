package argendata.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.web.command.MainPageForm;


@Component
public class FacetedSearchFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return MainPageForm.class.equals(clazz);
	}

	public void validate(Object arg0, Errors arg1) {
		
	}

}
