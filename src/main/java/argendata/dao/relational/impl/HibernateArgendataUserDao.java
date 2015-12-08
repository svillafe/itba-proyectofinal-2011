package argendata.dao.relational.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import argendata.dao.relational.ArgendataUserDao;
import argendata.model.relational.ArgendataUser;
import argendata.model.relational.RelationalApp;

@Repository
public class HibernateArgendataUserDao extends
		HibernateGenericDao<ArgendataUser> implements ArgendataUserDao {

	private final static Logger logger = Logger.getLogger(HibernateArgendataUserDao.class);
	
	@Autowired
	public HibernateArgendataUserDao(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public ArgendataUser authenticate(String username, String password)
			throws Exception {

		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where username = ? AND password = ?");
		query.setParameter(0, username);
		query.setParameter(1, cypher(password));
		ArgendataUser result = (ArgendataUser) query.uniqueResult();

		return result;
	}

	public boolean registerUser(ArgendataUser myUser) {
		myUser.setPassword(cypher(myUser.getPassword()));
		return store(myUser) != null;
	}

	public ArgendataUser authorizateUser(String nickName, String eMail)
			throws Exception {
		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where username = ? AND email = ?");
		query.setParameter(0, nickName);
		query.setParameter(1, eMail);
		ArgendataUser result = (ArgendataUser) query.uniqueResult();
		return result;
	}

	public void updateUser(ArgendataUser newUser) {
		super.update(newUser);
		
	}

	public ArgendataUser getUserByUsername(String username) {
		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where username = ? ");
		query.setParameter(0, username);

		ArgendataUser result = (ArgendataUser) query.uniqueResult();

		return result;

	}

	public boolean activateAccount(String username, String key) {
		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where username = ? and key = ?");
		query.setParameter(0, username);
		query.setParameter(1, key);

		ArgendataUser result = (ArgendataUser) query.uniqueResult();

		if (result != null) {

			result.activate();

			updateUser(result);
		}

		return result != null;
	}
	
	public ArgendataUser forgetValidation(String email, String key) {
		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where email = ? and key = ?");
		query.setParameter(0, email);
		query.setParameter(1, key);

		ArgendataUser result = (ArgendataUser) query.uniqueResult();

		return result;
	}

	private String cypher(String message) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
		} catch (NoSuchAlgorithmException e) {
			logger.fatal(e);
		}

		md.update(message.getBytes());
		byte[] mb = md.digest();
		String out = "";
		for (int i = 0; i < mb.length; i++) {
			byte temp = mb[i];
			String s = Integer.toHexString(new Byte(temp));
			while (s.length() < 2) {
				s = "0" + s;
			}
			s = s.substring(s.length() - 2);
			out += s;
		}
		return out;
	}

	public String forgetPassword(String email) {
		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where email = ? ");
		query.setParameter(0, email);

		ArgendataUser result = (ArgendataUser) query.uniqueResult();

		if(result!=null){
			//se updatea la key
			result.setKey(generateRandomKey());
			updateUser(result);
			
			return result.getKey();
		}
		else
			return null;
		
	}

	private String generateRandomKey() {
		SecureRandom random = new SecureRandom();
	    return new BigInteger(130, random).toString(32);
	}

	public void changePassword(ArgendataUser user) {
		user.setPassword(cypher(user.getPassword()));
		updateUser(user);
	}

	public int getQty() {
		Session session = getSession();
		Query query = session
				.createQuery("SELECT COUNT (DISTINCT email )  FROM ArgendataUser  ");

		Integer result =  Integer.valueOf(query.uniqueResult().toString());

		if(result!=null){
			
			return result;
		}
		else
			return 0;
	}

	public List<ArgendataUser> topDatasetsPublishers(int qty) {
		Session session = getSession();
		Query query = session.createQuery(" from ArgendataUser ORDER BY datasetsQty DESC").setMaxResults(qty);
		List<ArgendataUser> result = (List<ArgendataUser>) query.list();

		return result;
	}

	public List<ArgendataUser> topAppsPublishers(int qty) {
		Session session = getSession();
		Query query = session.createQuery(" from ArgendataUser ORDER BY appsQty DESC ").setMaxResults(qty);
		List<ArgendataUser> result = (List<ArgendataUser>) query.list();

		return result;
	}

	public List<ArgendataUser> getAdmins() {
		Session session = getSession();
		Query query = session.createQuery(" from ArgendataUser where isadmin= ?").setParameter(0, true);
		List<ArgendataUser> result = (List<ArgendataUser>) query.list();

		return result;
	}

	public ArgendataUser getUserByEmail(String email) {
		Session session = getSession();
		Query query = session
				.createQuery(" from ArgendataUser where email = ? ");
		query.setParameter(0, email);

		ArgendataUser result = (ArgendataUser) query.uniqueResult();

		return result;
		
	}
	
}
