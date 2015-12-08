package argendata.web.command;


import org.springframework.web.multipart.MultipartFile;

import argendata.model.relational.ArgendataUser;
import argendata.web.command.base.AbstractForm;

public class UserForm extends AbstractForm<ArgendataUser>  {

	private String password;
	private String name;
	private String lastName;
	private String email;
	private String username;

	private String twitter;
	private String facebook;
	private String minibio;
	
	private MultipartFile avatar;
	private String repassword;

	
	
	public UserForm(ArgendataUser u) {
		super(u);
		this.setName(u.getName());
		this.setLastName(u.getLastName());
		this.setEmail(u.getEmail());
		this.setUsername(u.getUsername());
		this.setTwitter(u.getTwitter());
		this.setFacebook(u.getFacebook());
		this.setMinibio(u.getMinibio());
	}

	public UserForm() {

	}

	@Override
	public ArgendataUser build() {
		ArgendataUser u = new ArgendataUser(this.username, this.name, this.lastName,
				this.password, this.email, false);
		
		return u;
	}

	@Override
	public ArgendataUser update(ArgendataUser domainObject) {
		domainObject.setUsername(username);
		domainObject.setName(name);
		domainObject.setLastName(lastName);
		domainObject.setPassword(password);
		domainObject.setEmail(email);
		domainObject.setTwitter(twitter);
		domainObject.setFacebook(facebook);
		domainObject.setMinibio(minibio);
		
		return domainObject;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	public void setAvatar(MultipartFile avatar) {
		this.avatar = avatar;
	}

	public MultipartFile getAvatar() {
		return avatar;
	}

	@Override
	public String toString() {
		return "UserRegisterForm [ avatar=" + avatar
				+ ", email=" + email + ", lastName=" + lastName + ", name="
				+ name + ", password=" + password +  ", username=" + username + "]";
	}

	public String getRePassword() {
		return repassword;
		
	}
	
	public void setRePassword(String pass){
		this.repassword = pass;
	}
}
