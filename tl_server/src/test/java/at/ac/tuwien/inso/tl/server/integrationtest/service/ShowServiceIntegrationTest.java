package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

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

}
