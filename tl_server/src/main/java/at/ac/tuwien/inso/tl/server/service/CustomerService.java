package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface CustomerService {

	/**
	 * Returns the customer object identified by the given id
	 * 
	 * @param id of the customer object
	 * @return the customer object
	 * @throws ServiceException
	 */
	public Customer getById(Integer id) throws ServiceException;	
	
	/**
	 * Creates the given customer object and returns the saved entity
	 * 
	 * @param customer object to persist
	 * @return the saved entity
	 * @throws ServiceException
	 */
	public Customer create(Customer customer) throws ServiceException;
	
	/**
	 * Finds the given customer object and returns the found entities
	 * 
	 * @param customer object with its properties as search parameters
	 * @return the found entities
	 * @throws ServiceException
	 */
	public List<Customer> find(Customer customer) throws ServiceException;
	
	/**
	 * Updates the given customer object and returns the saved entity
	 * 
	 * @param customer object to update
	 * @return the updated entity
	 * @throws ServiceException
	 */
	public Customer update(Customer customer) throws ServiceException;
	
	/**
	 * Deletes the given customer object
	 * 
	 * @param id of the customer object to delete
	 * @return returns nothing
	 * @throws ServiceException
	 */
	public void deleteById(Integer id) throws ServiceException;
	
	/**
	 * Returns a collection of all customers.
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<Customer> getAll() throws ServiceException;
}
