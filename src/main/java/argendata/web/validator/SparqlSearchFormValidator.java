package argendata.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.web.command.ChangePasswordForm;
import argendata.web.command.DatasetForm;
import argendata.web.command.MainPageForm;
import argendata.web.command.SparqlSearchForm;


@Component
public class SparqlSearchFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return SparqlSearchForm.class.equals(clazz);
	}

	public void validate(Object arg0, Errors err) {
				
		SparqlSearchForm form = (SparqlSearchForm)arg0;
		
		if( form.getQuery()==null || form.getQuery().isEmpty()){
			err.rejectValue("query","SparqlSearchForm.empty.query");
		}
		
		if(form.getQuery()!=null && form.getQuery().length() > 1200){
			err.rejectValue("query","SparqlSearchForm.tooLong.query");
		}
		
	}

}
