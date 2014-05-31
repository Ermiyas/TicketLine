package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.EmployeeServiceImpl;

public class EmployeeServiceTest {
	private EmployeeServiceImpl  service = null;
	
	private List<Employee> testData = null;
	
	private Comparator<Employee> usernameComparator = new Comparator<Employee>() {
		@Override
		public int compare(Employee o1, Employee o2) {
			return o1.getUsername().compareTo(o2.getUsername());
		}
	};
	
	private Sort sortParameter = new Sort(Direction.ASC, "username");
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {}
	
	@Before
	public void setUp() {
		service = new EmployeeServiceImpl();
		testData = new ArrayList<Employee>();
		
		Employee emp1 = new Employee("Manuela", "Oster", "m.oster", "hash", false, 0);
		emp1.setId(0);
		testData.add(emp1);
		
		Employee emp2 = new Employee("Jennifer", "Fuerst", "j.fuerst", "hash", true, 1);
		emp2.setId(1);
		testData.add(emp2);
		
		Employee emp3 = new Employee("Janina", "Scholz", "j.scholz", "hash", false, 4);
		emp3.setId(2);
		testData.add(emp3);
		
		Employee emp4 = new Employee("Jakob", "Scholz", "j.scholz", "hash", false, 0);
		emp4.setId(3);
		testData.add(emp4);
	}

	@After
	public void tearDown() {
		service = null;
		testData = null;
	}

	@Test
	public void testRetrieveAllEmployees_ShouldReturnFourEmps() {

		
		Collections.sort(testData, usernameComparator);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testData);
		service.setEmployeeDao(employeeDao);
		
		List<Employee> allEmployees = null;
		try {
			allEmployees = service.retrieveAllEmployees();
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
		assertEquals(4, allEmployees.size());
		assertEquals("j.fuerst", allEmployees.get(0).getUsername());
		assertEquals("j.scholz", allEmployees.get(1).getUsername());
		assertEquals("j.scholz", allEmployees.get(2).getUsername());
		assertEquals("m.oster", allEmployees.get(3).getUsername());
		
		Mockito.verify(employeeDao, Mockito.times(1)).findAll(sortParameter);
	}
	
	@Test
	public void testCreateEmployee_ShouldEqualReturnValue() {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		newEmp.setId(4);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.save(newEmp)).thenReturn(newEmp);
		service.setEmployeeDao(employeeDao);
		
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
		newEmp.setId(4);
		Collections.sort(testData, usernameComparator);
		List<Employee> testDataAfterCreate = new ArrayList<Employee>();
		testDataAfterCreate.addAll(testData);
		testDataAfterCreate.add(newEmp);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testData);
		Mockito.when(employeeDao.save(newEmp)).thenReturn(newEmp);
		service.setEmployeeDao(employeeDao);
		
		try {
			assertEquals(4, service.retrieveAllEmployees().size());
			Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testDataAfterCreate);
			service.createEmployee(newEmp);
			assertEquals(5, service.retrieveAllEmployees().size());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testCreateEmployee_ShouldBePersistantlySaved() {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		newEmp.setId(4);
		Collections.sort(testData, usernameComparator);
		List<Employee> testDataAfterCreate = new ArrayList<Employee>();
		testDataAfterCreate.addAll(testData);
		testDataAfterCreate.add(newEmp);
		Collections.sort(testDataAfterCreate, usernameComparator);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testData);
		Mockito.when(employeeDao.save(newEmp)).thenReturn(newEmp);
		service.setEmployeeDao(employeeDao);
		
		try {
			service.createEmployee(newEmp);
			Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testDataAfterCreate);
			assertEquals("c.frey", service.retrieveAllEmployees().get(0).getUsername());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}

	}
	
	@Test(expected = ServiceException.class)
	public void testCreateEmployee_ExistingID_ShouldFail() throws ServiceException {
		Employee newEmp = new Employee("Christine", "Frey", "c.frey", "hash", false, 0);
		newEmp.setId(1);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.save(newEmp)).thenReturn(newEmp);
		Mockito.when(employeeDao.exists(1)).thenReturn(true);
		service.setEmployeeDao(employeeDao);
		
		service.createEmployee(newEmp);
	}
	
	@Test
	public void testUpdateEmployee_ShouldEqualReturnValue() {
		Collections.sort(testData, usernameComparator);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testData);
		Mockito.when(employeeDao.exists(1)).thenReturn(true);
		service.setEmployeeDao(employeeDao);
		
		Employee emp = null;
		try {
			emp = service.retrieveAllEmployees().get(0);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
		Mockito.when(employeeDao.save(emp)).thenReturn(emp);
		emp.setFirstname("Changed");
		try {
			assertEquals("Changed", service.updateEmployee(emp).getFirstname());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testUpdateEmployee_ShouldBePersistantlySaved() {
		
		Collections.sort(testData, usernameComparator);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testData);
		Mockito.when(employeeDao.exists(Mockito.anyInt())).thenReturn(true);
		service.setEmployeeDao(employeeDao);
		
		Employee emp = null;
		try {
			emp = service.retrieveAllEmployees().get(0);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		Mockito.when(employeeDao.save(emp)).thenReturn(emp);
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
		newEmp.setId(4);
		Collections.sort(testData, usernameComparator);
		EmployeeDao employeeDao = Mockito.mock(EmployeeDao.class);
		Mockito.when(employeeDao.findAll(sortParameter)).thenReturn(testData);
		service.setEmployeeDao(employeeDao);
		
		service.updateEmployee(newEmp);
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
