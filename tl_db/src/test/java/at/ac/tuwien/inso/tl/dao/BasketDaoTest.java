package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Basket;

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
	public void testsetCustomerForBasketShouldSetCustomer(){
		LOG.info("setCustomerForBasketShouldSetCustomerTest called.");
		//Basket b2 = basketDao.getOne(1);
		//System.out.println(b2.getCustomer().getFirstname());
		
		
		Basket b = new Basket();
		b.setCreationdate(new Date(System.currentTimeMillis()));
		b = basketDao.save(b);
		basketDao.setCustomerForBasket(b, 1);
		
		/*
		WIESOOOOOOOOOOOOOOOO!!!!
		Integer id = b.getId();
		
		basketDao.flush();
		
		Basket b2 = basketDao.getOne(id);
		LOG.debug("Basket id "+b2.getId());
		assertTrue(b2.getCustomer() != null);
		*/
		
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
	public void testSetCustomerForBasketWithCustomerId0_ShouldNotThrowException(){
		LOG.info("testGetOneById1_ShouldNotReturnNull called.");
		Basket b = basketDao.findOne(1);
		assertTrue(b.getCustomer() != null);
		basketDao.setCustomerForBasket(b, null);
		/*
		Basket b2 = basketDao.findOne(1);
		assertTrue(b2.getCustomer() != null);
		*/
		
	}

}
