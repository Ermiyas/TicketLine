package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;


import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ShowService;

public class ShowServiceIntegrationTest extends AbstractServiceIntegrationTest {
	
	@Autowired
	private ShowService showService;
	
	private static final Logger LOG = Logger.getLogger(ShowServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testGetShowsForPerformance_ShouldNotReturnNull(){
		LOG.info("testGetShowsForPerformance_ShouldNotReturnNull called");
		try {
			assertNotNull(showService.getShowsForPerformance(1));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testGetShowsForLocation_ShouldNotReturnNull(){
		LOG.info("testGetShowsForLocation_ShouldNotReturnNull called");
		try {
			assertNotNull(showService.getShowsForLocation(1));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testGetAllShows_ShouldNotReturnNull(){
		LOG.info("testGetAllShows_ShouldNotReturnNull called");
		try {
			assertNotNull(showService.getAllShows());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindAllShowsWithAllNull_ShouldContainAllShows(){
		LOG.info("testGetAllShows_ShouldNotReturnNull called");
		try {
			int x = showService.findShows(null, null, null, null, null, null, null, null, null).size();
			int y = showService.getAllShows().size();
			assertTrue(x == y);
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindAllShowsWithPerformanceId1_ShouldFilterSomeShows(){
		LOG.info("testGetAllShows_ShouldNotReturnNull called");
		try {
			int x = showService.findShows(null, null, null, null, null, null, null, null, 1).size();
			int y = showService.getAllShows().size();
			assertTrue(x != y);
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}

}
