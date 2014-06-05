package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface BasketService {

	/**
	 * Returns the basket object identified by the given id
	 * 
	 * @param id of the basket object
	 * @return the basket object
	 * @throws ServiceException
	 */
	public Basket getById(Integer id) throws ServiceException;	
	
	/**
	 * Creates the given basket object and returns the saved entity
	 * 
	 * @param basket object to persist
	 * @return the saved entity
	 * @throws ServiceException
	 */
//	public Basket create(Basket basket) throws ServiceException;
	
	/**
	 * Finds the given basket object and returns the found entities
	 * 
	 * @param basket object with its properties as search parameters
	 * @return the found entities
	 * @throws ServiceException
	 */
//	public List<Basket> find(Basket basket) throws ServiceException;
	
	/**
	 * Updates the given basket object and returns the saved entity
	 * 
	 * @param basket object to update
	 * @return the updated entity
	 * @throws ServiceException
	 */
//	public Basket update(Basket basket) throws ServiceException;
	
	/**
	 * Deletes the given basket object
	 * 
	 * @param id of the basket object to delete
	 * @return returns nothing
	 * @throws ServiceException
	 */
//	public void deleteById(Integer id) throws ServiceException;
	
	/**
	 * Returns a collection of all baskets.
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<Basket> getAll() throws ServiceException;
}