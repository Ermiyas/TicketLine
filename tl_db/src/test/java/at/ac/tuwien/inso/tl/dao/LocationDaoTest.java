package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Location;

public class LocationDaoTest extends AbstractDaoTest {
	
	@Autowired
	private LocationDao ldao;
	
	private static final Logger LOG = Logger.getLogger(LocationDaoTest.class);
	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");	
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}	
	
	@Test
	public void testfindAll_ShouldNotReturnNull()
	{
		LOG.info("testfindAll_ShouldNotReturnNull called.");
		assertNotNull(ldao.findAll());
	}
	
	@Test
	public void testfindLocation_ShouldNotReturnNull()
	{
		LOG.info("testfindLocation_ShouldNotReturnNull called.");
		assertNotNull(ldao.findLocations(null, null, null, null, null));
	}	
	
	@Test
	public void testfindLocation_OnlyNullShouldReturnAll()
	{
		LOG.info("testfindLocation_ShouldNotReturnNull called.");
		assertTrue(ldao.findAll().containsAll(ldao.findLocations(null, null, null, null, null)));
	}
	
	@Test
	public void testfindLocation_FindByCity()
	{
		LOG.info("testfindLocation_FindByCity called.");
		assertTrue(ldao.findLocations("tte", null, null, null, null).size() == 1);
	}
	
	@Test
	public void testfindLocation_FindByCountry()
	{
		LOG.info("testfindLocation_FindByCountry called.");
		List<Location> loc = ldao.findLocations(null, "deutsch", null, null, null);
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 7);
	}
	
	@Test
	public void testfindLocation_FindByDescription()
	{
		LOG.info("testfindLocation_FindByDescription called.");
		List<Location> loc = ldao.findLocations(null, null, "oper", null, null);
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 8);
	}
	
	@Test
	public void testfindLocation_FindByPostalCode()
	{
		LOG.info("testfindLocation_FindByPostalCode called.");
		assertTrue(ldao.findLocations(null, null, null, "10", null).size() == 4);
	}
	
	@Test
	public void testfindLocation_FindByStreet()
	{
		LOG.info("testfindLocation_FindByStreet called.");
		List<Location> loc = ldao.findLocations(null, null, null, null, "au");
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 3);
	}
	
	@Test
	public void testfindLocation_FindByCombiniation()
	{
		LOG.info("testfindLocation_FindByCombiniation called.");
		List<Location> loc = ldao.findLocations("VoE", "TER", "CS", "3", "RA");
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 1);
	}	
}
