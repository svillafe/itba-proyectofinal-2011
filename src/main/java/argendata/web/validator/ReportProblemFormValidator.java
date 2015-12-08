package argendata.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import argendata.web.command.ReportProblemForm;


@Component
public class ReportProblemFormValidator implements Validator{

	public boolean supports(Class<?> clazz) {
		return ReportProblemForm.class.equals(clazz);
	}

	public void validate(Object arg0, Errors err) {
				
		ReportProblemForm reportForm = (ReportProblemForm)arg0;
		
		if(reportForm.getProblem()==null || reportForm.getProblem().isEmpty()){
			err.rejectValue("problem", "ReportProblemForm.invalid.problem","La descripción del problema es obligatoria.");
		}
			
		if( reportForm.getProblem() !=null && reportForm.getProblem().length() > 600){
			err.rejectValue("problem", "ReportProblemForm.tooLong.problem","La descripción del problema puede tener hasta 600 caracteres.");
		}
	}

}
