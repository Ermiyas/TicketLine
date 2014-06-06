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
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.RowService;
import at.ac.tuwien.inso.tl.server.service.ShowService;

public class RowServiceIntegrationTest extends AbstractServiceIntegrationTest{
	
	@Autowired
	RowService service; 
	
	@Autowired
	ShowService showService;


	private static final Logger LOG = Logger.getLogger(RowServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testfindRows_ShouldNotReturnNull()
	{
		LOG.info("testfindRows_ShouldNotReturnNull called.");
		try
		{
			assertNotNull(service.findRows(null));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindRows_ShouldReturnEmptyListIfIdIsInvalid()
	{
		LOG.info("testfindRows_ShouldReturnEmptyListIfIdIsInvalid called.");
		
		try{
			LOG.debug("loading all shows (per findShows).");		
			List<Show> allShow = showService.findShows(null, null, null, null, null, null, null, null, null);
			
			int maxID = 0;
			for(Show s: allShow)
			{
				if(s.getId() > maxID)
					maxID = s.getId();
			}
			
			LOG.debug(String.format("max Show ID is %d, so trying to find Seats for ID %d", maxID, maxID + 1));		
			maxID++;		
			assertTrue(service.findRows(maxID).size() == 0);
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
			LOG.debug("loading all Shows (per findShows).");		
			List<Show> allShows = showService.findShows(null, null, null, null, null, null, null, null, null);
			
			if(allShows.size() > 0)
			{		
				Show firstShow = allShows.get(0);
				
				LOG.debug(String.format("find all rows for show with ID %d.", firstShow.getId()));
				List<Row> foundRows = service.findRows(firstShow.getId());
				
				LOG.debug(String.format("found %d rows.", foundRows.size()));
				
				for(Row r: foundRows)
				{
					if(r.getShow() != firstShow)
					   fail("row has wrong show");
				}		
				
				LOG.debug("testing if all rows have been loaded.");
				for(Row r: service.findRows(null))
				{
					if(r.getShow() == firstShow)
						assertTrue(foundRows.contains(r));
				}
			}
			else
			{
				LOG.error("no rows found.");		
			}			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
}
