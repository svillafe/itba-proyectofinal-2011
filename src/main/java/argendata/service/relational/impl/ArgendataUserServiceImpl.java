package argendata.service.relational.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import argendata.dao.relational.ArgendataUserDao;
import argendata.model.relational.ArgendataUser;
import argendata.service.relational.ArgendataUserService;

@Service
public class ArgendataUserServiceImpl implements ArgendataUserService {

	private ArgendataUserDao argendataUserDao;

	@Autowired
	public ArgendataUserServiceImpl(ArgendataUserDao myDao) {

		this.argendataUserDao = myDao;
	}

	public ArgendataUser authenticate(String username, String password)
			throws Exception {
		if(username==null || password == null || username.equals("") || password.equals("")){
			return null;
		}
		return this.argendataUserDao.authenticate(username, password);
	}

	public boolean registerUser(ArgendataUser user) throws Exception {
		return this.argendataUserDao.registerUser(user);
	}

	public ArgendataUser getUserById(int id) throws Exception {
		return argendataUserDao.getById(id);
	}

	public ArgendataUser authorizateUser(String username, String email)
			throws Exception {
		return argendataUserDao.authorizateUser(username, email);
	}


	public List<ArgendataUser> getAllUsers() throws Exception {
		List<ArgendataUser> response = new ArrayList<ArgendataUser>();
		Iterator<ArgendataUser> myIterator = argendataUserDao.getAll()
				.iterator();

		while (myIterator.hasNext()) {
			response.add(myIterator.next());
		}

		return response;
	}

	public void updateUser(ArgendataUser newUser) {
		argendataUserDao.updateUser(newUser);
	}

	public ArgendataUser getUserByUsername(String username) {
		
		return argendataUserDao.getUserByUsername(username);
	}

	public boolean existUsername(String username) {
		return argendataUserDao.getUserByUsername(username)!=null;
	}

	public boolean activateAccount(String username, String key) {
		 
		return argendataUserDao.activateAccount(username,key);
	}

	public String forgetPassword(String email) {
		return argendataUserDao.forgetPassword(email);
	}

	public ArgendataUser forgetValidation(String email, String key) {
		return argendataUserDao.forgetValidation(email,key);
	}

	public void changePassword(ArgendataUser user) {
		argendataUserDao.changePassword(user); 
		
	}

	public int getQty() {
		return argendataUserDao.getQty();
	}
	
	public List<ArgendataUser> topAppsPublishers(int qty){
		return argendataUserDao.topAppsPublishers(qty);
	}
	
	public List<ArgendataUser> topDatasetsPublishers(int qty){
		return argendataUserDao.topDatasetsPublishers(qty);
	}
	
	public List<ArgendataUser> getAdmins(){
		return argendataUserDao.getAdmins();
	}

	public List<String> getAdminsMail() {
		
		List<ArgendataUser> admins=argendataUserDao.getAdmins();
		List<String> resp = new ArrayList<String>();
		
		for(ArgendataUser a: admins){
			resp.add(a.getEmail());
		}
		return resp;
	}

	public boolean existMail(String email) {
		ArgendataUser au = argendataUserDao.getUserByEmail(email);
		return au!=null;
	}

	@Override
	public void deleteUser(ArgendataUser user) {
		argendataUserDao.delete(user);
		
	}
	
}
