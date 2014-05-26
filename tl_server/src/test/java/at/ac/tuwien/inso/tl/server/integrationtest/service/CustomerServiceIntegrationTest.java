package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.CustomerServiceImpl;

public class CustomerServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	@Autowired
	private CustomerServiceImpl service;
	
	private Integer maxmusterId = 1;
	private Integer theodorTesterId = 2;
	private Integer mariaMeisnerId = 3;

	@Test
	public void testSearch_ShouldFindMaxMusterLargeRequest() throws ParseException, ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setFirstname("Max");
		cst1.setLastname("Muster");
		cst1.setCity("Berlin");
		cst1.setCountry("Deutschland");
		cst1.setPoints(2);
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		cst1.setDateOfBirth(formatter.parse("16.01.1990"));
		
		List<Customer> result = service.find(cst1);
		
		assertSame(maxmusterId, result.get(0).getId());
		
	}
	
	@Test
	public void testSearch_ShouldFindTheodorTesterLargeRequest() throws ParseException, ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setFirstname("Theodor");
		cst1.setLastname("Tester");
		cst1.setCity("Bremen");
		cst1.setCountry("Deutschland");
		cst1.setPoints(4);
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		cst1.setDateOfBirth(formatter.parse("11.04.1994"));
		
		List<Customer> result = service.find(cst1);
		
		assertSame(theodorTesterId, result.get(0).getId());
		
	}
	
	@Test
	public void testSearch_ShouldFindAllAustrianCustomers () throws ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setCountry("Deutschland");
		
		List<Customer> result = service.find(cst1);
		
		assertEquals(3, result.size());
		
	}
	
	@Test
	public void testSearchWithPoints1_ShouldFindAllCustomers () throws ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setPoints(1);
		
		List<Customer> result = service.find(cst1);
		
		assertEquals(3, result.size());
		
	}
	
	@Test
	public void testSearchWithPoints5_ShouldFindMariaMueller () throws ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setPoints(5);
		
		List<Customer> result = service.find(cst1);
		
		assertSame(mariaMeisnerId, result.get(0).getId());
		
	}
	
	@Test
	public void testSearchWithPoints6_ShouldFindMariaMueller () throws ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setPoints(6);
		
		List<Customer> result = service.find(cst1);
		
		assertSame(mariaMeisnerId, result.get(0).getId());
		
	}
	
	@Test
	public void testSearchWithPoints10_ShouldFindNothing () throws ServiceException {
		
		Customer cst1 = new Customer();
		cst1.setPoints(10);
		
		List<Customer> result = service.find(cst1);
		
		assertEquals(0, result.size());
		
	}
	
}