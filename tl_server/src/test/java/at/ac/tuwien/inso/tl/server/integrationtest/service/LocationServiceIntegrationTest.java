package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.LocationService;

public class LocationServiceIntegrationTest extends
		AbstractServiceIntegrationTest {

	@Autowired
	LocationService service;

	private static final Logger LOG = Logger.getLogger(LocationServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testfindAll_ShouldNotReturnNull()
	{
		LOG.info("testfindAll_ShouldNotReturnNull called.");
		try{
			assertNotNull(service.getAllLocations());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_ShouldNotReturnNull()
	{
		LOG.info("testfindLocation_ShouldNotReturnNull called.");
		try{	
			assertNotNull(service.findLocations(null, null, null, null, null));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}	
	
	@Test
	public void testfindLocation_OnlyNullShouldReturnAll()
	{
		LOG.info("testfindLocation_ShouldNotReturnNull called.");
		try{		
			assertTrue(service.getAllLocations().containsAll(service.findLocations(null, null, null, null, null)));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_FindByCity()
	{
		LOG.info("testfindLocation_FindByCity called.");
		try{	
			List<Location> loc = service.findLocations("tte", null, null, null, null);
			assertTrue(loc.size() == 1);
			assertTrue(loc.get(0).getId() == 2);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_FindByCountry()
	{
		LOG.info("testfindLocation_FindByCountry called.");
		try{		
			List<Location> loc = service.findLocations(null, "deutsch", null, null, null);
			assertTrue(loc.size() == 1);
			assertTrue(loc.get(0).getId() == 7);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_FindByDescription()
	{
		LOG.info("testfindLocation_FindByDescription called.");
		try{		
			List<Location> loc = service.findLocations(null, null, "oper", null, null);
			assertTrue(loc.size() == 1);
			assertTrue(loc.get(0).getId() == 8);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_FindByPostalCode()
	{
		LOG.info("testfindLocation_FindByPostalCode called.");
		try{		
			assertTrue(service.findLocations(null, null, null, "10", null).size() == 4);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_FindByStreet()
	{
		LOG.info("testfindLocation_FindByStreet called.");
		try{		
			List<Location> loc = service.findLocations(null, null, null, null, "au");
			assertTrue(loc.size() == 1);
			assertTrue(loc.get(0).getId() == 3);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindLocation_FindByCombiniation()
	{
		LOG.info("testfindLocation_FindByCombiniation called.");
		try{		
			List<Location> loc = service.findLocations("VoE", "TER", "CS", "3", "RA");
			assertTrue(loc.size() == 1);
			assertTrue(loc.get(0).getId() == 1);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
		
	@Test
	public void testfindLocationByShowID_FindValid()
	{
		LOG.info("testfindLocationByShowID_FindValid called.");
		try{	
			assertTrue(service.findLocationByShowID(7).getId() == 5);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test(expected = ServiceException.class)
	public void testfindLocationByShowID_FindInvalid() throws ServiceException
	{
		LOG.info("testfindLocationByShowID_FindInvalid called.");
		assertTrue(service.findLocationByShowID(87).getId() == 5);
	}
	
}
