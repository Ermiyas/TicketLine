package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.SeatService;

public class SeatServiceIntegrationTest extends AbstractServiceIntegrationTest{
	
	@Autowired
	SeatService service; 

	private static final Logger LOG = Logger.getLogger(SeatServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testfindShows_NullParameterShouldReturnAllSeats()
	{
		LOG.info("testfindShows_NullParameterShouldReturnAllSeats called.");
		
		try {			
		
			LOG.debug("loading all seats (per getAllSeats).");		
			List<Seat> allSeats = service.getAllSeats();
			
			LOG.debug("loading all seats (per find).");						
			List<Seat> foundSeats = service.findSeats(null);
			
			LOG.debug(String.format("findAll: %d seats, find: %d seats.", allSeats.size(), foundSeats.size()));
			
			assertTrue(allSeats.equals(foundSeats));
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
}
