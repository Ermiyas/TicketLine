package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import at.ac.tuwien.inso.tl.dao.LocationDao;
import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.server.service.impl.LocationServiceImpl;


public class LocationServiceTest {

	private LocationServiceImpl service = null;
	private List<Location> testData = null;
	private LocationDao dao;
	
	private static final Logger LOG = Logger.getLogger(LocationServiceTest.class);
	
	@Before
	public void setUp()  {
		
		LOG.info("setUp called.");
		service = new LocationServiceImpl();
		testData = new ArrayList<Location>();
		
		Location l1 = new Location();
		l1.setId(1);
		l1.setCity("Voesendorf");
		l1.setCountry("Oesterreich");
		l1.setDescription("SCS");
		l1.setPostalcode("2334");
		l1.setStreet("SCS-Straße");
		testData.add(l1);
		
		Location l2 = new Location();
		l2.setId(2);
		l2.setCity("Unterpremstaetten");
		l2.setCountry("Oesterreich");
		l2.setDescription("Schwarzlsee");
		l2.setPostalcode("8141");
		l2.setStreet(null);
		testData.add(l2);
		
		Location l3 = new Location();
		l3.setId(3);
		l3.setCity("Wien");
		l3.setCountry("Oesterreich");
		l3.setDescription("Flex");
		l3.setPostalcode("1010");
		l3.setStreet("Augartenbruecke 1");
		testData.add(l3);
		
		Location l4 = new Location();
		l4.setId(4);
		l4.setCity("Wien");
		l4.setCountry("Oesterreich");
		l4.setDescription("Pratersauna");
		l4.setPostalcode("1020");
		l4.setStreet("Waldsteingartenstraße 135");
		testData.add(l4);
		
		Location l5 = new Location();
		l5.setId(5);
		l5.setCity("Wien");
		l5.setCountry("Oesterreich");
		l5.setDescription("Lugner Kino City");
		l5.setPostalcode("1150");
		l5.setStreet("Gablenzgasse 3");
		testData.add(l5);
		
		Location l6 = new Location();
		l6.setId(6);
		l6.setCity("Wien");
		l6.setCountry("Oesterreich");
		l6.setDescription("Burgtheater");
		l6.setPostalcode("1010");
		l6.setStreet("Universiteatsring 2");
		testData.add(l6);
		
		Location l7 = new Location();
		l7.setId(7);
		l7.setCity("Berlin");
		l7.setCountry("Deutschland");
		l7.setDescription("Stadtion An der Alten Foersterei");
		l7.setPostalcode("12555");
		l7.setStreet("An der Wulheide 263");
		testData.add(l7);
		
		Location l8 = new Location();
		l8.setId(8);
		l8.setCity("Wien");
		l8.setCountry("Oesterreich");
		l8.setDescription("Wiener Staatsoper");
		l8.setPostalcode("1010");
		l8.setStreet("Opernring 2");
		testData.add(l8);
		
						
		dao = Mockito.mock(LocationDao.class);
		Mockito.when(dao.findAll()).thenReturn(testData);
		Mockito.when(dao.findLocations(null, null, null, null, null)).thenReturn(testData);
		Mockito.when(dao.findLocations("tte", null, null, null, null)).thenReturn(testData.subList(1, 2));
		Mockito.when(dao.findLocations(null, "deutsch", null, null, null)).thenReturn(testData.subList(6, 7));
		Mockito.when(dao.findLocations(null, null, "oper", null, null)).thenReturn(testData.subList(7, 8));	
		List<Location> l1100 = testData.subList(2, 6);
		l1100.set(3, l8);		
		Mockito.when(dao.findLocations(null, null, null, "10", null)).thenReturn(l1100);
		Mockito.when(dao.findLocations(null, null, null, null, "au")).thenReturn(testData.subList(2, 3));
		Mockito.when(dao.findLocations("VoE", "TER", "CS", "3", "RA")).thenReturn(testData.subList(0, 1));	
		
		service.setLocationDao(dao);
	}
	
	@Test
	public void testfindAll_ShouldNotReturnNull()
	{
		LOG.info("testfindAll_ShouldNotReturnNull called.");
		assertNotNull(dao.findAll());
	}
	
	@Test
	public void testfindLocation_ShouldNotReturnNull()
	{
		LOG.info("testfindLocation_ShouldNotReturnNull called.");
		assertNotNull(dao.findLocations(null, null, null, null, null));
	}	
	
	@Test
	public void testfindLocation_OnlyNullShouldReturnAll()
	{
		LOG.info("testfindLocation_ShouldNotReturnNull called.");
		assertTrue(dao.findAll().equals(dao.findLocations(null, null, null, null, null)));
	}
	
	@Test
	public void testfindLocation_FindByCity()
	{
		LOG.info("testfindLocation_FindByCity called.");
		assertTrue(dao.findLocations("tte", null, null, null, null).size() == 1);
	}
	
	@Test
	public void testfindLocation_FindByCountry()
	{
		LOG.info("testfindLocation_FindByCountry called.");
		List<Location> loc = dao.findLocations(null, "deutsch", null, null, null);
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 7);
	}
	
	@Test
	public void testfindLocation_FindByDescription()
	{
		LOG.info("testfindLocation_FindByDescription called.");
		List<Location> loc = dao.findLocations(null, null, "oper", null, null);
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 8);
	}
	
	@Test
	public void testfindLocation_FindByPostalCode()
	{
		LOG.info("testfindLocation_FindByPostalCode called.");
		assertTrue(dao.findLocations(null, null, null, "10", null).size() == 4);
	}
	
	@Test
	public void testfindLocation_FindByStreet()
	{
		LOG.info("testfindLocation_FindByStreet called.");
		List<Location> loc = dao.findLocations(null, null, null, null, "au");
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 3);
	}
	
	@Test
	public void testfindLocation_FindByCombiniation()
	{
		LOG.info("testfindLocation_FindByCombiniation called.");
		List<Location> loc = dao.findLocations("VoE", "TER", "CS", "3", "RA");
		assertTrue(loc.size() == 1);
		assertTrue(loc.get(0).getId() == 1);
	}
}
