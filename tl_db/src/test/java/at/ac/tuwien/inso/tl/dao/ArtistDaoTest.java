package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Artist;

public class ArtistDaoTest extends AbstractDaoTest {
	
	@Autowired
	private ArtistDao adao;

	private static final Logger LOG = Logger.getLogger(ArtistDaoTest.class);
	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}	
	
	@Test
	public void testfindArtist_ShouldNotReturnNull()
	{
		LOG.info("testfindArtist_ShouldNotReturnNull called.");
		assertNotNull(adao.findArtists(null, null));
	}
	
	@Test
	public void testfindArtist_NullParameterShouldReturnAllArtists()
	{
		LOG.info("testfindArtist_NullParameterShouldReturnAllArtists called.");
		assertTrue(adao.findAll().containsAll(adao.findArtists(null, null)));
	}
	
	@Test
	public void testfindArtsit_TestSearchByFirstName()
	{
		LOG.info("testfindArtsit_TestSearchByFirstName called.");
		List<Artist> found = adao.findArtists("Ug", null);
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getId() == 6);		
	}

	@Test
	public void testfindArtsit_TestSearchByLastName()
	{
		LOG.info("testfindArtsit_TestSearchByLastName called.");
		List<Artist> found = adao.findArtists(null, "aNs");
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getId() == 3);
	}
	
	@Test
	public void testfindArtsit_TestSearchByFirstAndLastName()
	{
		LOG.info("testfindArtsit_TestSearchByFirstAndLastName called.");
		List<Artist> found = adao.findArtists("eT", "och");
		assertTrue(found.size() == 1);
		assertTrue(found.get(0).getId() == 2);
	}
	
}
