package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.service.spi.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;

public class BasketDaoTest extends AbstractDaoTest{
	
	private static final Logger LOG = Logger.getLogger(BasketDaoTest.class);
	
	@Autowired
	private BasketDao basketDao;
	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}
	
	
	@Test
	public void testGetOneById1_ShouldNotReturnNull(){
		LOG.info("testGetOneById1_ShouldNotReturnNull called.");
		Basket b = basketDao.getOne(1);
		assertNotNull(b);
		Basket b2 = basketDao.getOne(1);
		assertNotNull(b.getCustomer());
	}
	
	@Test
	public void testFindByBasketIdAndCustomerIds_ShouldNotReturnNull(){
		LOG.info("testFindByBasketIdAndCustomerIds_ShouldNotReturnNull called");
		List<Integer> customers = new ArrayList<Integer>();
		customers.add(1);
		customers.add(2);
		try{
			List<Basket> baskets = basketDao.findByBasket_idAndCustomer_ids(1, customers);
			assertNotNull(baskets);
		} catch(ServiceException e){
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testFindByCustomerIds_ShouldNotReturnNull(){
		LOG.info("testFindByCustomerIds_ShouldNotReturnNull called");
		List<Integer> customers = new ArrayList<Integer>();
		customers.add(1);
		customers.add(2);
		try{
			List<Basket> baskets = basketDao.findByCustomer_ids(customers);
			assertNotNull(baskets);
			assertTrue(baskets.size() == 2);
		} catch(ServiceException e){
			fail("ServiceException thrown");
		}
		
	}
	
	
	

}
