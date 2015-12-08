package argendata.converters;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import argendata.model.relational.App;
import argendata.service.AppService;

@Component
public class AppConverter implements Converter<String, App> {

	private final Logger logger = Logger.getLogger(AppConverter.class);
	
	private AppService appService;

	@Autowired
	public AppConverter(AppService service) {
		this.appService = service;
	}

	public App convert(String arg) {
		logger.debug("Invocado (Converter):"+ arg);
		App resp = null;
		try {
			 /*Si no la tengo entre las apps peristidas entonces la busco en las que falta por persistir*/
			 resp = appService.getApprovedAppByQName(arg);
		}catch(java.lang.ClassCastException e){
		  resp = null;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if(resp ==null){
			resp = appService.getPreAppByQName(arg);
		}
		
		return resp;
	}
}

/*
 * public class AppConverter extends BaseConverter<String, App> {
 * 
 * }
 */