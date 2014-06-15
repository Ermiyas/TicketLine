package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Artist;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArtistService;

public class ArtistServiceIntegrationTest extends AbstractServiceIntegrationTest{
	
	@Autowired
	ArtistService service;

	private static final Logger LOG = Logger.getLogger(ArtistServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testgetAllArtists_ShouldNotReturnNull()
	{
		LOG.info("getAllArtists_ShouldNotReturnNull called.");
		try
		{
			assertNotNull(service.getAllArtists());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testgetAllArtists_NullParameterShouldReturnAllArtists()
	{
		LOG.info("testgetAllArtists_NullParameterShouldReturnAllArtists called.");
		try
		{
			assertTrue(service.getAllArtists().size() == 9);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindAll_ShouldNotReturnNull()
	{
		LOG.info("getAllArtists_ShouldNotReturnNull called.");
		try
		{
			assertNotNull(service.getAllArtists());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindArtist_ShouldNotReturnNull()
	{
		LOG.info("testfindArtist_ShouldNotReturnNull called.");
		try
		{
			assertNotNull(service.findArtists(null, null));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}		
	
	@Test
	public void testfindArtsit_TestSearchByFirstName()
	{
		LOG.info("testfindArtsit_TestSearchByFirstName called.");
		try
		{
			List<Artist> found = service.findArtists("Ug", null);
			assertTrue(found.size() == 1);
			assertTrue(found.get(0).getId() == 6);		
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}

	@Test
	public void testfindArtsit_TestSearchByLastName()
	{
		LOG.info("testfindArtsit_TestSearchByLastName called.");
		try
		{
			List<Artist> found = service.findArtists(null, "aNs");
			assertTrue(found.size() == 1);
			assertTrue(found.get(0).getId() == 3);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindArtsit_TestSearchByFirstAndLastName()
	{
		LOG.info("testfindArtsit_TestSearchByFirstAndLastName called.");
		try
		{
			List<Artist> found = service.findArtists("eT", "och");
			assertTrue(found.size() == 1);
			assertTrue(found.get(0).getId() == 2);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
}
