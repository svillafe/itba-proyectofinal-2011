package argendata.web.command.base;

import argendata.model.base.PersistantEntity;

public abstract class AbstractForm<T extends PersistantEntity> {

	private int id = -1;

	public AbstractForm() {
		
	}
	
	public AbstractForm(T domainObject) {
		this.id = domainObject.getId();
	}

	public int getId() {
		return id;
	}

	public boolean isNew() {
		return this.id == -1;
	}
	
	public abstract T build();

	public abstract T update(T domainObject);

}