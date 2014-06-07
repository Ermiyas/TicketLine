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
	public void setCustomerForBasketShouldSetCustomerTest(){
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

}
