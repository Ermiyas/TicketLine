package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EmployeeDto;

public interface EmployeeService {

	public List<EmployeeDto> getAllEmployees() throws ServiceException;
	
	public Integer createEmployee(EmployeeDto employee) throws ServiceException;
	
	public void updateEmployee(EmployeeDto employee) throws ServiceException;
}
