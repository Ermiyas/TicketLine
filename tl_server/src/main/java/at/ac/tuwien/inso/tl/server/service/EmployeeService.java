package at.ac.tuwien.inso.tl.server.service;

import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface EmployeeService {

	/**
	 * Creates the given employee object and returns the saved entity
	 * 
	 * @param employee object to persist
	 * @return the saved entity
	 * @throws ServiceException
	 */
	public Employee createEmployee(Employee employee) throws ServiceException;
	
	/**
	 * Deletes the given employee object
	 * 
	 * @param id of the employee object to delete
	 * @return returns nothing
	 * @throws ServiceException
	 */
	public void deleteEmployee(Integer id) throws ServiceException;
	
	/**
	 * Updates the given employee object and returns the saved entity
	 * 
	 * @param employee object to update
	 * @return the updated entity
	 * @throws ServiceException
	 */
	public Employee updateEmployee(Employee employee) throws ServiceException;
}
