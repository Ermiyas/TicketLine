package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.security.AuthenticationSuccessEventListener;
import at.ac.tuwien.inso.tl.server.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	private static final Logger LOG = Logger.getLogger(EmployeeServiceImpl.class);
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public List<Employee> retrieveAllEmployees() throws ServiceException {
		throw new NotYetImplementedException();
	}
	
	@Override
	public Employee createEmployee(Employee employee) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public Employee updateEmployee(Employee employee) throws ServiceException {
		throw new NotYetImplementedException();
	}

	@Override
	public int increaseWrongPasswordCounter(String username) throws UsernameNotFoundException {
		LOG.info("Increasing wrongPasswordCounter by one");
		
		List<Employee> employees = employeeDao.findByUsername(username);
		
		Employee emp = null;
		if(employees.isEmpty()){
			throw new UsernameNotFoundException(String.format("user %s does not exists", username));
		}
		
		if(1 == employees.size()){
			emp = employees.get(0);
		} else {
			throw new UsernameNotFoundException(String.format("Username %s is not unique", username));
		}
		
		emp.setWrongPasswordCounter(emp.getWrongPasswordCounter() + 1);
		employeeDao.save(emp);
		LOG.info(String.format("Increased wrongPasswordCounter to %d", emp.getWrongPasswordCounter()));
		return emp.getWrongPasswordCounter();
	}

	@Override
	public void resetWrongPasswordCounter(String username) throws UsernameNotFoundException{
		LOG.info("Reset wrongPasswordCounter to zero");
		
		List<Employee> employees = employeeDao.findByUsername(username);
		
		Employee emp = null;
		if(employees.isEmpty()){
			throw new UsernameNotFoundException(String.format("user %s does not exists", username));
		}
		
		if(1 == employees.size()){
			emp = employees.get(0);
		} else {
			throw new UsernameNotFoundException(String.format("Username %s is not unique", username));
		}
		
		emp.setWrongPasswordCounter(0);
		LOG.info("wrongPasswordCounter set to zero");
		employeeDao.save(emp);
	}

	// -------------------- For Testing purposes --------------------

	public void setEmployeeDao(EmployeeDao dao) {
		this.employeeDao = dao;
	}

	// -------------------- For Testing purposes --------------------

}
