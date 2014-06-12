package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EntryService;

public class EntryServiceIntegrationTest extends AbstractServiceIntegrationTest {
	@Autowired
	EntryService service;

	private static final Logger LOG = Logger.getLogger(EntryServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testCreateEntry_ShouldNotReturnNull()
	{
		LOG.info("testCreateTest_ShouldNotReturnNull called.");

		try {
			Entry e = new Entry();
			e.setAmount(0);
			e.setBuyWithPoints(false);
			e.setSold(false);
			assertNotNull(service.createEntry(e, 1));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testCreateEntryWithoutSoldSet_ShouldThrowServiceException()
	{
		LOG.info("testCreateEntryWithoutSoldSet_ShouldThrowServiceException called.");

		try {
			Entry e = new Entry();
			e.setAmount(0);
			e.setBuyWithPoints(false);
			assertNotNull(service.createEntry(e, 1));
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
		}
	}
	
	@Test
	public void testCreateEntryWithoutAmountSet_ShouldThrowServiceException()
	{
		LOG.info("testCreateEntryWithoutAmountSet_ShouldThrowServiceException called.");

		try {
			Entry e = new Entry();
			e.setSold(false);
			e.setBuyWithPoints(false);
			assertNotNull(service.createEntry(e, 1));
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
		}
	}
	
	@Test
	public void testCreateEntryWithoutBuyWithPointsSet_ShouldThrowServiceException()
	{
		LOG.info("testCreateEntryWithoutBuyWithPointsSet_ShouldThrowServiceException called.");

		try {
			Entry e = new Entry();
			e.setBuyWithPoints(false);
			e.setSold(false);
			assertNotNull(service.createEntry(e, 1));
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
		}
		
	}
	
	@Test
	public void testCreateEntryWithNullEntry_ShouldThrowServiceException()
	{
		LOG.info("testCreateEntryWithNullEntry_ShouldThrowServiceException called.");

		try {
			assertNotNull(service.createEntry(null, 1));
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
		}
		
	}
	
	@Test
	public void testCreateEntryWithBasketIdNull_ShouldThrowServiceException()
	{
		LOG.info("testCreateEntryWithBasketIdNull_ShouldThrowServiceException called.");

		try {
			
			Entry e = new Entry();
			e.setBuyWithPoints(false);
			e.setSold(false);
			assertNotNull(service.createEntry(e, null));
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
		}
		
	}
	
	@Test
	public void testgetEntry_ShouldNotReturnNull()
	{
		LOG.info("testgetEntry_ShouldNotReturnNull called.");

		try {
			
			assertNotNull(service.getEntry(1));
			
		} catch (ServiceException e) {
			LOG.error(e);
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testgetEntryByBasketId1_BooleanShouldBeTrue()
	{
		LOG.info("testgetEntryByBasketId1_BooleanShouldBeTrue called.");

		try {
			List<Map.Entry<Entry, Boolean>> entries = service.getEntry(1);
			
			assertTrue(entries.get(0).getValue());
			
		} catch (ServiceException e) {
			LOG.error(e);
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testgetEntryByBasketIdNull_ShouldThrowServiceException()
	{
		LOG.info("testgetEntryByBasketIdNull_ShouldThrowServiceException called.");

		try {
			service.getEntry(null);
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
		}
		
	}


}
