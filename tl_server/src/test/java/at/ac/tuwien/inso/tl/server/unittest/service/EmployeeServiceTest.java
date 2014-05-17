package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.service.impl.EmployeeServiceImpl;

public class EmployeeServiceTest {
	private EmployeeServiceImpl  service = null;
	
	private List<Employee> testData = null;
	
	@Before
	public void setUp() {
		service = new EmployeeServiceImpl();
		testData = new ArrayList<Employee>();
		testData.add(new Employee("Manuela", "Oster", "m.oster", "hash", false, 0));
		testData.add(new Employee("Jennifer", "Fuerst", "j.fuerst", "hash", true, 1));
		testData.add(new Employee("Janina", "Scholz", "j.scholz", "hash", false, 4));
		testData.add(new Employee("Jakob", "Scholz", "j.scholz", "hash", false, 0));
	}

	@After
	public void tearDown() {
		service = null;
		testData = null;
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testIncreaseWrongPasswordCounter_UserDoesntExist() {
		String mockUsername = "f.maier";
		
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findByUsername(mockUsername)).thenReturn(new ArrayList<Employee>());
		service.setEmployeeDao(employeeDao);
		
		service.increaseWrongPasswordCounter(mockUsername);
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testIncreaseWrongPasswordCounter_NotUnique() {
		String mockUsername = "j.scholz";
		
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findByUsername(mockUsername)).thenReturn(testData.subList(2, 4));
		service.setEmployeeDao(employeeDao);
		
		service.increaseWrongPasswordCounter(mockUsername);
	}
	
	@Test
	public void testIncreaseWrongPasswordCounter_Successful() {
		String mockUsername = "m.oster";
		
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findByUsername(mockUsername)).thenReturn(testData.subList(0, 1));
		Mockito.when(employeeDao.save(testData.get(0))).thenReturn(testData.get(0));
		service.setEmployeeDao(employeeDao);
		
		assertEquals(1, service.increaseWrongPasswordCounter(mockUsername));
		
		assertEquals(new Integer(1), testData.get(0).getWrongPasswordCounter());
	}
	
	@Test(expected=UsernameNotFoundException.class)
	public void testResetWrongPasswordCounter_UserDoesntExist() {
		String mockUsername = "f.maier";
		
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findByUsername(mockUsername)).thenReturn(new ArrayList<Employee>());
		service.setEmployeeDao(employeeDao);
		
		service.resetWrongPasswordCounter(mockUsername);
	}

	@Test(expected=UsernameNotFoundException.class)
	public void testResetWrongPasswordCounter_NotUnique() {
		String mockUsername = "j.scholz";
		
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findByUsername(mockUsername)).thenReturn(testData.subList(2, 4));
		service.setEmployeeDao(employeeDao);
		
		service.resetWrongPasswordCounter(mockUsername);
	}
	
	@Test
	public void testResetWrongPasswordCounter_Successful() {
		String mockUsername = "j.fuerst";
		
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findByUsername(mockUsername)).thenReturn(testData.subList(0, 1));
		service.setEmployeeDao(employeeDao);
		
		service.resetWrongPasswordCounter(mockUsername);
		
		Mockito.verify(employeeDao, Mockito.times(1)).save(Mockito.any(Employee.class));
	}

}
