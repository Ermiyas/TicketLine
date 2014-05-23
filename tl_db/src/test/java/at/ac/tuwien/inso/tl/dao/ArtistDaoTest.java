package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ArtistDaoTest extends AbstractDaoTest {
	@Autowired
	private ArtistDao adao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testfindArtistsByName_ShouldNotReturnNull()
	{
		assertNotNull(adao.findArtistsByName(null, null));
	}
	
	

}
