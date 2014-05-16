package at.ac.tuwien.inso.tl.server.service.impl;

import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public Employee createEmployee(Employee employee) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public void deleteEmployee(Integer id) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public Employee updateEmployee(Employee employee) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public int increaseWrongPasswordCounter(String username) {
		throw new NotYetImplementedException();
	}

	@Override
	public void resetWrongPasswordCounter(String username) {
		throw new NotYetImplementedException();
	}

	// -------------------- For Testing purposes --------------------

	public void setEmployeeDao(EmployeeDao dao) {
		this.employeeDao = dao;
	}

	// -------------------- For Testing purposes --------------------

}
