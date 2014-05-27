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
