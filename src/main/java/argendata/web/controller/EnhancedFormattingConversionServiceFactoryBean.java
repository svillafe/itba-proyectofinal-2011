package argendata.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import argendata.dao.semantic.impl.RepositoryConnectionManager;

public class EnhancedFormattingConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
	
	private final Converter<?, ?>[] converters;
	private final Logger logger = Logger.getLogger(EnhancedFormattingConversionServiceFactoryBean.class);
	
	@Autowired
	public EnhancedFormattingConversionServiceFactoryBean(Converter<?, ?>[] converters) {
		this.converters = converters;
	}
	
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		for (Converter<?,?> c: converters) {
			registry.addConverter(c);
			logger.info("REGISTRANDO CONVERTER");
		}
	}
}
