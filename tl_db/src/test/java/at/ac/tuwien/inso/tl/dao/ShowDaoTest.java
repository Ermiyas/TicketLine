package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ShowDaoTest extends AbstractDaoTest {
	@Autowired
	private ShowDao sdao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testfindShows_ShouldNotReturnNull()
	{
		assertNotNull(sdao.findShows(null, null, null, null, null, null, null, null, null));
	}
	
	@Test
	public void testgetMinMaxPriceInCent_ShouldReturnNotNullValueAndSizeIsTwoAndMinNotGreaterThanMax()
	{
		int[] result = sdao.getMinMaxPriceInCent();
		assertNotNull(result);
		assertTrue(result.length == 2);		
		assertTrue(result[0] <= result[1]);
	}
}
