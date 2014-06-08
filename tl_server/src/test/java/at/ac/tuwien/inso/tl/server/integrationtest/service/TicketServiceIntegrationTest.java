package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.TicketService;

public class TicketServiceIntegrationTest extends
		AbstractServiceIntegrationTest {
	
	@Autowired
	TicketService service;

	private static final Logger LOG = Logger.getLogger(TicketServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testCreateTest_ShouldNotReturnNull()
	{
		LOG.info("testCreateTest_ShouldNotReturnNull called.");

		try {
			assertNotNull(service.createTicket(19, null, null));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
		
	}
	
	@Test
	public void testCreateTest_ShouldThrowServiceException(){
		LOG.info("testCreateTest_ShouldThrowServiceException called.");

		try {
			service.createTicket(null, null, 2);
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
			
		}
	}
	
	@Test
	public void testGetTicketForEntry_ShouldNotReturnNull(){
		LOG.info("testGetTicketForEntry_ShouldNotReturnNull called.");

		try {
			assertNotNull(service.getTicketForEntry(1));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testUndoTicket_ShouldThrowServiceException(){
		LOG.info("testUndoTicket_ShouldThrowServiceException called.");

		try {
			service.undoTicket(null);
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
			
		}
	}
	
	@Test
	public void testUndoTicket_ShouldNotThrowServiceException(){
		LOG.info("testUndoTicket_ShouldNotThrowServiceException called.");

		try {
			service.undoTicket(5);
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}

}
