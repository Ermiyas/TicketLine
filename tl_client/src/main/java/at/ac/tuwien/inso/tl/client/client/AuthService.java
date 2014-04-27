package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.UserStatusDto;

public interface AuthService {
	
	public UserStatusDto getUserStatus();

	/**
	 *  Authenticate the user with a given username and password
	 * 
	 * @param username the username for the login
	 * @param password the password for the login
	 * @return true if login was successful, false otherwise
	 * @throws ServiceException if a problem occurs
	 */
	public boolean login(String username, String password) throws ServiceException;
	
	/**
	 * Logout the current authenticated user
	 * 
	 * @throws ServiceException if a problem occurs
	 */
	public void logout() throws ServiceException;
}
