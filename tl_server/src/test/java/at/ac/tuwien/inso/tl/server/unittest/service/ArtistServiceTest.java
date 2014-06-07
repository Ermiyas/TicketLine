package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import at.ac.tuwien.inso.tl.dao.ArtistDao;
import at.ac.tuwien.inso.tl.model.Artist;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.ArtistServiceImpl;

public class ArtistServiceTest {

	private ArtistServiceImpl service = null;
	private List<Artist> testData = null;
	private ArtistDao dao;
	
	private static final Logger LOG = Logger.getLogger(ArtistServiceTest.class);
	
	@Before
	public void setUp()  {
		
		LOG.info("setUp called.");
		service = new ArtistServiceImpl();
		testData = new ArrayList<Artist>();
		
		Artist a1 = new Artist();
		a1.setId(2);
		a1.setFirstname("Juliette");
		a1.setLastname("Binoche");
		testData.add(a1);
		
		Artist a2 = new Artist();
		a2.setId(3);
		a2.setFirstname("Bryan");
		a2.setLastname("Cranston");
		testData.add(a2);
		
		Artist a3 = new Artist();
		a3.setId(6);
		a3.setFirstname("Hugh");
		a3.setLastname("Jackman");		
		testData.add(a3);
						
		dao = Mockito.mock(ArtistDao.class);
		Mockito.when(dao.findAll()).thenReturn(testData);
		Mockito.when(dao.findArtists(null, null)).thenReturn(testData);
		Mockito.when(dao.findArtists("Ug", null)).thenReturn(testData.subList(2, 3));
		Mockito.when(dao.findArtists(null, "aNs")).thenReturn(testData.subList(1, 2));
		Mockito.when(dao.findArtists("eT", "och")).thenReturn(testData.subList(0, 1));		
		
		service.setArtistDao(dao);
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
			assertTrue(testData.equals(service.getAllArtists()));
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
	public void testfindArtist_NullParameterShouldReturnAllArtists()
	{
		LOG.info("testfindArtist_NullParameterShouldReturnAllArtists called.");
		try
		{
			assertTrue(testData.equals(service.findArtists(null, null)));
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
