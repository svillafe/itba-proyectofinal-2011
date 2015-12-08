package argendata.converters.base;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import argendata.model.base.PersistantEntity;

@Component
public abstract class BaseConverter<S, T extends PersistantEntity> implements
		Converter<S, T> {

	protected EntityResolver entityResolver;

	@SuppressWarnings("unchecked")
	public T convert(String arg0) {
		Class<T> domainClass;
		ParameterizedType thisType = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		domainClass = (Class<T>) thisType.getActualTypeArguments()[1];
		return entityResolver.find(Integer.valueOf(arg0), domainClass);
	}

	@Autowired
	public void setEntityResolver(EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}

}
