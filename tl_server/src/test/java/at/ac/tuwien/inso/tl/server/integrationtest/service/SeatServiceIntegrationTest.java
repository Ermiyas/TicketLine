package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.SeatService;
import at.ac.tuwien.inso.tl.server.service.RowService;

public class SeatServiceIntegrationTest extends AbstractServiceIntegrationTest{
	
	@Autowired
	SeatService service; 
	
	@Autowired
	RowService rowService;

	private static final Logger LOG = Logger.getLogger(SeatServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		

	@Test
	public void testfindSeats_ShouldNotReturnNull()
	{
		LOG.info("testfindSeats_ShouldNotReturnNull called.");
		try
		{
			assertNotNull(service.findSeats(null));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}

	@Test
	public void testfindShows_ShouldReturnEmptyListIfIdIsInvalid()
	{
		LOG.info("testfindShows_ShouldReturnEmptyListIfIdIsInvalid called.");
		
		try
		{
			LOG.debug("loading all rows (per findRows).");		
			List<Row> allRows = rowService.findRows(null);
			
			int maxID = 0;
			for(Row r: allRows)
			{
				if(r.getId() > maxID)
					maxID = r.getId();
			}
			
			LOG.debug(String.format("max Row ID is %d, so trying to find Seats for ID %d", maxID, maxID + 1));		
			maxID++;		
			assertTrue(service.findSeats(maxID).size() == 0);
		
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}		
	
	@Test
	public void testfindShows_findFirstSeatByID()
	{
		LOG.info("testfindShows_findFirstSeatByID called.");
		
		try
		{
			LOG.debug("loading all Rows (per findRows).");		
			List<Row> allRows = rowService.findRows(null);
			
			if(allRows.size() > 0)
			{		
				Row firstRow = allRows.get(0);
				
				LOG.debug(String.format("find all seats for row with ID %d.", firstRow.getId()));
				List<Seat> foundSeats = service.findSeats(firstRow.getId());
				
				LOG.debug(String.format("found %d seats.", foundSeats.size()));
				
				for(Seat s: foundSeats)
				{
					if(s.getRow() != firstRow)
					   fail("Seat has wrong row");
				}		
				
				LOG.debug("testing if all seats have been loaded.");
				for(Seat s: service.findSeats(null))
				{
					if(s.getRow() == firstRow)
						assertTrue(foundSeats.contains(s));
				}
			}
			else
			{
				LOG.error("no seats found.");		
			}		
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
}
