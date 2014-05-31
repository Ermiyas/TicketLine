package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import at.ac.tuwien.inso.tl.dao.CustomerDao;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.CustomerServiceImpl;

public class CustomerServiceTest {
	
	private CustomerServiceImpl service = null;
	private List<Customer> testData = null;
	
	@Before
	public void setUp() throws ParseException {
		
		service = new CustomerServiceImpl();
		testData = new ArrayList<Customer>();
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		
		Customer cst1 = new Customer();
		cst1.setFirstname("Max");
		cst1.setLastname("Muster");
		cst1.setCity("Berlin");
		cst1.setCountry("Deutschland");
		cst1.setDateOfBirth(formatter.parse("16.01.1990"));
		cst1.setPoints(2);
		testData.add(cst1);
		
		Customer cst2 = new Customer();
		cst2.setFirstname("Theodor");
		cst2.setLastname("Tester");
		cst2.setCity("Bremen");
		cst2.setCountry("Deutschland");
		cst2.setDateOfBirth(formatter.parse("11.04.1994"));
		cst2.setPoints(4);
		testData.add(cst2);	
		
	}
	
	@Test
	public void testGetAllCustomers_ShouldReturnTwoElements() throws ServiceException {
		
		CustomerDao customerDao = Mockito.mock(CustomerDao.class);
		Mockito.when(customerDao.findAll()).thenReturn(testData);
		
		service.setCustomerDao(customerDao);
		
		assertEquals(2, service.getAll().size());
		
	}
	
	@Test
	public void testGetCustomerWithId5_ShouldReturnNull() throws ServiceException {
		
		CustomerDao customerDao = Mockito.mock(CustomerDao.class);
		Mockito.when(customerDao.findOne(5)).thenReturn(null);
		
		service.setCustomerDao(customerDao);
		
		Object result =	service.getById(5);
		assertNull(result);
		
	}
	
	@Test
	public void testGetCustomerWithId0_ShouldReturnMaxMuster() throws ServiceException {
		
		CustomerDao customerDao = Mockito.mock(CustomerDao.class);
		Mockito.when(customerDao.findOne(0)).thenReturn(testData.get(0));
		
		service.setCustomerDao(customerDao);
		
		Customer result = service.getById(0);
		assertEquals(testData.get(0), result);
		
	}
	
	@Test
	public void testUpdateCustomer_ShouldReturnModifiedObject () throws ServiceException {
		
		CustomerDao customerDao = Mockito.mock(CustomerDao.class);
		Mockito.when(customerDao.save(testData.get(0))).thenReturn(testData.get(0));
		
		service.setCustomerDao(customerDao);
		
		testData.get(0).setCountry("Ungarn");
		Customer result = service.update(testData.get(0));
		assertEquals("Ungarn",result.getCountry());
		
	}
	
	@Test
	public void testDeleteCustomer_ObjectShouldBeDeleted () throws ServiceException {
		
		CustomerDao customerDao = Mockito.mock(CustomerDao.class);
		
		Mockito.doAnswer(new Answer<Void>() {
			
			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				
				testData.remove(0);
				return null;
			}
			
		}).when(customerDao).delete(0);
		
		service.setCustomerDao(customerDao);
		service.deleteById(0);

		assertFalse(testData.get(0).getLastname().equals("Muster"));
		
	}
	
	@Test
	public void testCreateCustomer_ShouldReturnNewObject () throws ServiceException, ParseException {
		
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		Customer cst3 = new Customer();
		cst3.setFirstname("Maria");
		cst3.setLastname("Meisner");
		cst3.setCity("Frankfurt");
		cst3.setCountry("Deutschland");
		cst3.setDateOfBirth(formatter.parse("21.05.1970"));
		cst3.setPoints(6);
		
		CustomerDao customerDao = Mockito.mock(CustomerDao.class);
		Mockito.when(customerDao.save(cst3)).thenReturn(cst3);
		
		service.setCustomerDao(customerDao);

		Customer result = service.create(cst3);
		
		assertEquals("Meisner",result.getLastname());
		assertEquals("Maria",result.getFirstname());
		
	}
}