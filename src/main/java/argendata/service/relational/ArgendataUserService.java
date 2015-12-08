package argendata.service.relational;

import java.util.List;

import argendata.model.relational.ArgendataUser;

public interface ArgendataUserService {

	/**
	 * Authenticates a user in the webapp.
	 * 
	 * @param nick
	 *            The username.
	 * @param password
	 *            The password.
	 * @return The ArgendataUser if the authentication was successful or null if
	 *         it doesn't.
	 * @throws Exception
	 */
	public ArgendataUser authenticate(String nick, String password)
			throws Exception;

	/**
	 * Registers a new user in the webapp.
	 * 
	 * @param user
	 *            The user to be registered.
	 * @return true or false if the registrarion was succesful.
	 * @throws Exception
	 */
	public boolean registerUser(ArgendataUser user) throws Exception;

	/**
	 * @param id
	 *            The id of the user
	 * @return An ArgendataUser for the given id.
	 * @throws Exception
	 */
	public ArgendataUser getUserById(int id) throws Exception;

	/**
	 * @param username
	 * @param eMail
	 * @return
	 * @throws Exception
	 */
	public ArgendataUser authorizateUser(String username, String eMail)
			throws Exception;

	/**
	 * @return A list with all registered users.
	 * @throws Exception
	 */
	public List<ArgendataUser> getAllUsers() throws Exception;

	/**
	 * @param user
	 *            The user whose information will be updated.
	 */
	public void updateUser(ArgendataUser user);

	/**
	 * @param username
	 *            The user's name.
	 * @return The user with the given username.
	 */
	public ArgendataUser getUserByUsername(String username);

	/**
	 * @param username
	 * @return True or false if the username exists or nor.
	 */
	public boolean existUsername(String username);

	/**
	 * @param username
	 * @param key
	 *            The secret key that only the user should know.
	 * @return true or false if the pair username-key matches with the one
	 *         stored.
	 */
	public boolean activateAccount(String username, String key);

	/**
	 * @param email
	 *            The user's email.
	 * @return A secret key that will be used to re-activate the account and
	 *         change password or NULL if the email doesn't match.
	 */
	public String forgetPassword(String email);

	/**
	 * @param email 
	 * @param key
	 * @return An ArgendataUser that matches the pair email-key or null if anyone does.
	 */
	public ArgendataUser forgetValidation(String email, String key);

	/**
	 * @param user The user with the new password to be updated.
	 */
	public void changePassword(ArgendataUser user);

	/**
	 * @return Quantity of users.
	 */
	public int getQty();

	/**
	 * @param qty Limit of the query.
	 * @return A list of users.
	 */
	public List<ArgendataUser> topAppsPublishers(int qty);

	/**
	 * @param qty Query limit.
	 * @return A list of users.
	 */
	public List<ArgendataUser> topDatasetsPublishers(int qty);

	/**
	 * @return Retrieves a list with the admins emails.
	 */
	public List<String> getAdminsMail();

	/**
	 * @return A list with the admin users.
	 */
	List<ArgendataUser> getAdmins();

	/**
	 * @param email An email :)
	 * @return True or false if an user with the given email already exists.
	 */
	public boolean existMail(String email);

	/**
	 * Delete a given user from the database.
	 * @param user The user to delete.
	 */
	public void deleteUser(ArgendataUser user);

}
