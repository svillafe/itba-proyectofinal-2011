package argendata.model.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class PersistantEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public boolean isNew() {
		return id == null;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(int id){
		this.id=id;
	}
	
}