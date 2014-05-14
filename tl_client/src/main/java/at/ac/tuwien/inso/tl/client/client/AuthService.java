package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.UserEvent;
import at.ac.tuwien.inso.tl.dto.UserStatusDto;

public interface AuthService {
	
	public UserStatusDto getUserStatus();

	/**
	 *  Authenticate the user with a given username and password
	 * 
	 * @param username the username for the login
	 * @param password the password for the login
	 * @return An indicator of what happened
	 * @throws ServiceException if a problem occurs
	 */
	public UserEvent login(String username, String password) throws ServiceException;
	
	/**
	 * Logout the current authenticated user
	 * 
	 * @throws ServiceException if a problem occurs
	 */
	public void logout() throws ServiceException;
}
