package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface EmployeeService {

	/**
	 * Retrieves all employees
	 * 
	 * @return A list of all employees, ordered by username
	 * @throws ServiceException
	 */
	public List<Employee> retrieveAllEmployees() throws ServiceException;
	
	/**
	 * Creates the given employee object and returns the saved entity
	 * 
	 * @param employee object to persist
	 * @return the saved entity
	 * @throws ServiceException
	 */
	public Employee createEmployee(Employee employee) throws ServiceException;
	
	/**
	 * Updates the given employee object and returns the saved entity
	 * 
	 * @param employee object to update
	 * @return the updated entity
	 * @throws ServiceException
	 */
	public Employee updateEmployee(Employee employee) throws ServiceException;

	/**
	 * Increases the wrong-password-counter of the given employee by one
	 * @param 00username The username that should be updated
	 * @return The new counter value
	 */
	public int increaseWrongPasswordCounter(String username);
	
	/**
	 * Resets the wrong-password-counter of the given employee to zero
	 * @param username The username that should be reset
	 */
	public void resetWrongPasswordCounter(String username);
}
