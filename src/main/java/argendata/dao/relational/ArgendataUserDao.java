package argendata.dao.relational;

import java.util.List;

import argendata.dao.GenericDao;
import argendata.model.relational.ArgendataUser;

public interface ArgendataUserDao extends GenericDao<ArgendataUser> {

	/**
	 * @param nick Username of the user that we want to authenticate. 
	 * @param password The secret password.
	 * @return An argendata user if the pair nick-password matches.
	 * @throws Exception 
	 */
	public ArgendataUser authenticate(String nick, String password) throws Exception;

	/**
	 * @param myUser stores an ArgendataUser.
	 * @return true if the store action was successful or false if it wasn't.
	 */
	public boolean registerUser(ArgendataUser myUser);

	/**
	 * @param nickName the username.
	 * @param eMail the email.
	 * @return An ArgendataUser. 
	 * @throws Exception
	 */
	public ArgendataUser authorizateUser(String nickName, String eMail) throws Exception;

	/**
	 * @param userSet The user to update.
	 */
	public void updateUser(ArgendataUser userSet);

	/**
	 * @param username Name of the user to fetch.
	 * @return An ArgendataUser with the specified username.
	 */
	public ArgendataUser getUserByUsername(String username);

	/**
	 * @param username Name of the user.
	 * @param key The secret key to activate the acount.
	 * @return true or false if the account was activated.
	 */
	public boolean activateAccount(String username, String key);

	/**
	 * @param email the email from the user.
	 * @return a new generated key that you may use to activate the account.
	 */
	public String forgetPassword(String email);

	/**
	 * @param email user's email.
	 * @param key the secret key of the user.
	 * @return An argendata user if the pair email-key matches.
	 */
	ArgendataUser forgetValidation(String email, String key);

	/**
	 * @param user the updated user.
	 */
	public void changePassword(ArgendataUser user);

	/**
	 * @return The quantity of users stored.
	 */
	public int getQty();

	/**
	 * @param qty Specifies the limit for the query.
	 * @return The top apps publishers list. 
	 */
	public List<ArgendataUser> topAppsPublishers(int qty);
	
	/**
	 * @param qty Specifies the limit for the query.
	 * @return The top datasets publishers list.
	 */
	public List<ArgendataUser> topDatasetsPublishers(int qty);

	/**
	 * @return Returns a list with the admins.
	 */
	public List<ArgendataUser> getAdmins();

	/**
	 * @param email The user's email.
	 * @return A user or null if the email doesn't exist in the store.
	 */
	public ArgendataUser getUserByEmail(String email);
	
}
