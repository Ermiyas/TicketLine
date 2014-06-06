package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationDaoTest extends AbstractDaoTest {
	@Autowired
	private LocationDao ldao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testfindShows_ShouldNotReturnNull()
	{
		assertNotNull(ldao.findLocations(null, null, null, null, null));
	}		
}
