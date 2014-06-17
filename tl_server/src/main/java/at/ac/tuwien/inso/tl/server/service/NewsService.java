package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface NewsService {
	
	/**
	 * Returns the news object identified by the given id
	 * 
	 * @param id of the news object
	 * @return the news object
	 * @throws ServiceException
	 */
	public News getById(Integer id) throws ServiceException;
	
	/**
	 * Saves the given news object and returns the saved entity
	 * 
	 * @param news object to persist
	 * @return the saved entity
	 * @throws ServiceException
	 */
	public News save(News news) throws ServiceException;
	
	/**
	 * Returns a collection of all news.
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<News> getNews() throws ServiceException;
	
	/**
	 * Adds an employee to the the readby list of a news item, if he is already there he will be removed
	 * @param the news id
	 * @param the username of the employee
	 * @return void
	 * @throws ServiceException
	 */
	public void addNewsReadByEmployee(Integer news_id, String username) throws ServiceException;
	
	/**
	 * Adds an employee to the list of users who have read a news item, if he is already there he will stay there
	 * @param the news id
	 * @param the username of the employee
	 * @return void
	 * @throws ServiceException
	 */
	public void addNewsReadByEmployeeForce(Integer news_id, String username) throws ServiceException;
	
	/**
	 * Gets all news which have not been read by the current user
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<News> findAllUnreadNewsOfEmployee() throws ServiceException;
	
	/**
	 * Searches all news titles, uses wildcards (%..%) and is case-insensitive
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<News> search(News news) throws ServiceException;
	
	/**
	 * Returns true if a certain news item is read by a certain user
	 * @param the news id
	 * @param the username
	 * @return java.lang.Boolean
	 * @throws ServiceException
	 */
	public Boolean getNewsIsReadByEmployee(Integer news_id, String username) throws ServiceException;
}
