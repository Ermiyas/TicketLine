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
	public Customer getCustomer(Integer id) throws ServiceException;	
	
	/**
	 * Creates the given customer object and returns the saved entity
	 * 
	 * @param customer object to persist
	 * @return the saved entity
	 * @throws ServiceException
	 */
	public Customer createCustomer(Customer customer) throws ServiceException;
	
	/**
	 * Finds the given customer object and returns the found entities
	 * 
	 * @param customer object with its properties as search parameters
	 * @return the found entities
	 * @throws ServiceException
	 */
	public List<Customer> findCustomer(Customer customer) throws ServiceException;
	
	/**
	 * Updates the given customer object and returns the saved entity
	 * 
	 * @param customer object to update
	 * @return the updated entity
	 * @throws ServiceException
	 */
	public Customer updateCustomer(Customer customer) throws ServiceException;
	
	/**
	 * Deletes the given customer object
	 * 
	 * @param id of the customer object to delete
	 * @return returns nothing
	 * @throws ServiceException
	 */
	public void deleteCustomer(Integer id) throws ServiceException;
	
	/**
	 * Returns a collection of all customers.
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<Customer> getAllCustomers() throws ServiceException;
}
