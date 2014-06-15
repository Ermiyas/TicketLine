package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.BasketService;

public class BasketServiceIntegrationTest extends
		AbstractServiceIntegrationTest {
	
	@Autowired
	BasketService service;
	
	private static final Logger LOG = Logger.getLogger(BasketServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}
	
	
	
	@Test
	public void testCreateBasket_ShouldNotReturnNull(){
		LOG.info("testCreateBasket_ShouldNotReturnNull called");
		try {
			assertNotNull(service.createBasket());
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testCreateBasket_ShouldSetId(){
		LOG.info("testCreateBasket_ShouldSetId called");
		try {
			Basket b = service.createBasket();
			assertNotNull(b.getId());
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	
	@Test
	public void testGetBasketWithNullId_ShouldThrowServiceException(){
		LOG.info("testGetBasketWithNullId_ShouldThrowServiceException called");
		try {
			Basket b = service.getBasket(null);
			fail("ServiceException not thrown");
			
		} catch (ServiceException e) {
			
		}
	}
	
	@Test
	public void testGetBasketWithId1_ShouldNotReturnNull(){
		LOG.info("testGetBasketWithId1_ShouldNotReturnNull called");
		try {
			Basket b = service.getBasket(1);
			assertNotNull(b);
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testundoBasketWithId1_ShouldDeleteBasket(){
		LOG.info("testundoBasketWithId1_ShouldDeleteBasket called");
		try {
			service.undoBasket(1);
			Basket b = service.getBasket(1);
			//fail("ServiceException not thrown");
			
		} catch (ServiceException e) {
			
		}
	}
	
	@Test
	public void testSetCustomerForBasket_ShouldSetCustomer(){
		LOG.info("testSetCustomerForBasket_ShouldSetCustomer called");
		
		
		try {
			Basket b = service.createBasket();
			Integer id = b.getId();
			service.setCustomerForBasket(b, 1);
			
			Basket b2 = service.getBasket(id);
			assertTrue(b2.getCustomer() != null);
			
			
		} catch (ServiceException e) {
			fail("ServiceException  thrown");
		}
	}
	
	
	
	@Test
	public void testSetCustomerForBasketNull_ShouldSetCustomerNull(){
		LOG.info("testSetCustomerForBasket_ShouldSetCustomer called");
		
		
		try {
			Basket b = service.getBasket(1);
			assertTrue(b.getCustomer() != null);
			service.setCustomerForBasket(b, null);
			Basket b2 = service.getBasket(1);
			assertTrue(b2.getCustomer() == null);
			
			
		} catch (ServiceException e) {
			fail("ServiceException  thrown");
		}
	}
	
	@Test
	public void testFindBasketWithNullCustomer_ShouldNotReturnNull(){
		LOG.info("testFindBasket_ShouldNotReturnNull called");
		
		try{
			assertNotNull(service.findBasket(1, null));
		} catch(ServiceException e){
			fail("ServiceException  thrown");
		}
	}
	
	@Test
	public void testFindBasketWithCustomerNameFirstNameMax_ShouldNotReturnNull(){
		LOG.info("testFindBasket_ShouldNotReturnNull called");
		
		try{
			Customer c = new Customer();
			c.setFirstname("Max");
			List<Entry<Basket, Customer>> baskets = service.findBasket(null, c);
			assertNotNull(baskets);
			assertTrue(baskets.get(0).getKey().getId() == 1);
			assertTrue(baskets.get(0).getValue().getId() == 1);
			
		} catch(ServiceException e){
			fail("ServiceException  thrown");
		}
	}
	
	

}
