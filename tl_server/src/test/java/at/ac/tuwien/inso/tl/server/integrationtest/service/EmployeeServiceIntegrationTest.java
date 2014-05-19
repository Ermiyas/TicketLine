package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.EmployeeServiceImpl;

public class EmployeeServiceIntegrationTest extends
		AbstractServiceIntegrationTest {
	
	@Autowired
	private EmployeeServiceImpl  service;
	
	@Autowired
	private EmployeeDao dao;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRetrieveAllEmployees_ShouldReturnFourEmps() {

		List<Employee> allEmployees = null;
		try {
			allEmployees = service.retrieveAllEmployees();
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
		assertEquals(5, allEmployees.size());
		assertEquals("j.fuerst", allEmployees.get(0).getUsername());
		assertEquals("j.scholz", allEmployees.get(1).getUsername());
		assertEquals("ja.scholz", allEmployees.get(2).getUsername());
		assertEquals("m.oster", allEmployees.get(3).getUsername());
		assertEquals("marvin", allEmployees.get(4).getUsername());
	}
	
	@Test
	public void testCreateEmployee_ShouldEqualReturnValue() {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		
		Employee returnEmp = null;
		try {
			returnEmp = service.createEmployee(newEmp);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
		assertEquals("c.frey", returnEmp.getUsername());
		
	}
	
	@Test
	public void testCreateEmployee_ShouldIncreaseEmpCount() {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		
		try {
			assertEquals(5, service.retrieveAllEmployees().size());
			service.createEmployee(newEmp);
			assertEquals(6, service.retrieveAllEmployees().size());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testCreateEmployee_ShouldBePersistantlySaved() {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
	
		try {
			service.createEmployee(newEmp);
			assertEquals("c.frey", service.retrieveAllEmployees().get(0).getUsername());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}

	}
	
	@Test(expected = ServiceException.class)
	public void testCreateEmployee_ExistingID_ShouldFail() throws ServiceException {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		newEmp.setId(1);
		
		service.createEmployee(newEmp);
	}
	
	@Test
	public void testUpdateEmployee_ShouldEqualReturnValue() {		
		Employee emp = null;
		try {
			emp = service.retrieveAllEmployees().get(0);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
		emp.setFirstname("Changed");
		try {
			assertEquals("Changed", service.updateEmployee(emp).getFirstname());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testUpdateEmployee_ShouldBePersistantlySaved() {
		Employee emp = null;
		try {
			emp = service.retrieveAllEmployees().get(0);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
		emp.setFirstname("aaa");
		try {
			service.updateEmployee(emp);
			assertEquals("aaa", service.retrieveAllEmployees().get(0).getFirstname());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test(expected = ServiceException.class)
	public void testUpdateEmployee_NonExistant_ShouldFail() throws ServiceException {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		newEmp.setId(10);
		
		service.updateEmployee(newEmp);
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testIncreaseWrongPasswordCounter_UserDoesntExist() {
		String username = "f.maier";
		
		service.increaseWrongPasswordCounter(username);
	}
	
	@Test
	public void testIncreaseWrongPasswordCounter_Successful() {
		String username = "m.oster";
		
		assertEquals(1, service.increaseWrongPasswordCounter(username));
		
		List<Employee> employeeList = dao.findByUsername(username);
		
		assertEquals(1, employeeList.size());
		assertEquals(new Integer(1), employeeList.get(0).getWrongPasswordCounter());
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testResetWrongPasswordCounter_UserDoesntExist() {
		String username = "f.maier";

		service.resetWrongPasswordCounter(username);
	}

	@Test
	public void testResetWrongPasswordCounter_Successful() {
		String username = "j.fuerst";
		
		service.resetWrongPasswordCounter(username);
		
		List<Employee> employeeList = dao.findByUsername(username);
		
		assertEquals(1, employeeList.size());
		assertEquals(new Integer(0), employeeList.get(0).getWrongPasswordCounter());
	}

}
