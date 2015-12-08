package argendata.model.relational;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import argendata.model.base.PersistantEntity;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }) })
public class ArgendataUser extends PersistantEntity {

	private String username;
	private String name;
	private String lastName;
	private String password;
	private String email;
	private String key;
	private boolean isAdmin;
	private boolean activated;
	
	private String twitter;
	private String facebook;
	private String minibio; 
	
	private int appsQty;
	private int datasetsQty;
	
	/*
	 * @Type(type = "org.joda.time.contrib.hibernate.PersistentDateTime")
	 * private DateTime lastLogin;
	 */

	public ArgendataUser() {

	}

	public ArgendataUser(String username, String name, String lastName,
			String password, String email, boolean admin) {
		super();
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.isAdmin = admin;
		this.activated = false;
		// this.lastLogin = lastLogin;
		this.appsQty=0;
		this.datasetsQty=0;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public boolean getIsAdmin(){
		return isAdmin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArgendataUser other = (ArgendataUser) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ArgendataUser [email=" + email + ", isAdmin=" + isAdmin
				+ ", lastName=" + lastName + ", name=" + name + ", password="
				+ password + ", username=" + username + "]";
	}

	public void makeAdmin() {
		isAdmin = true;
	}

	public void activate() {
		activated = true;
	}

	public void desactivate() {
		activated = false;
	}
	
	public boolean isActivated(){
		return activated;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getMinibio() {
		return minibio;
	}

	public void setMinibio(String minibio) {
		this.minibio = minibio;
	}

	public int getAppsQty() {
		return appsQty;
	}

	public void setAppsQty(int appsQty) {
		this.appsQty = appsQty;
	}

	public void incAppsQty(){
		this.appsQty++;
	}
	
	public void decAppsQty(){
		this.appsQty--;
	}
	
	public int getDatasetsQty() {
		return datasetsQty;
	}

	public void setDatasetsQty(int datasetsQty) {
		this.datasetsQty = datasetsQty;
	}
	
	public void incDatasetsQty(){
		this.datasetsQty++;
	}
	
	public void decDatasetsQty(){
		this.datasetsQty--;
	}
	
	/*
	 * public DateTime getLastLogin() { return lastLogin; }
	 * 
	 * public void setLastLogin(DateTime lastLogin) { this.lastLogin =
	 * lastLogin; }
	 * 
	 * public String getLastLoginString() { return
	 * this.lastLogin.toString("dd:MM:yy"); }
	 */

}
