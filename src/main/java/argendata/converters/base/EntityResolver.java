package argendata.converters.base;

import argendata.model.base.PersistantEntity;

public interface EntityResolver {

	public <T extends PersistantEntity> T find(int id, Class<T> clazz);

}
