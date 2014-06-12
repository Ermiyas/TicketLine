package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Show;

public class ShowDaoTest extends AbstractDaoTest {
	@Autowired
	private ShowDao sdao;
	
	private static final Logger LOG = Logger.getLogger(ShowDaoTest.class);
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testfindShows_ShouldNotReturnNull()
	{
		LOG.info("testfindShows_ShouldNotReturnNull called");
		assertNotNull(sdao.findShows(null, null, null, null, null, null, null, null, null));
	}
	
	@Test
	public void testgetMinMaxPriceInCent_ShouldReturnNotNullValueAndSizeIsTwoAndMinNotGreaterThanMax()
	{
		LOG.info("testgetMinMaxPriceInCent_ShouldReturnNotNullValueAndSizeIsTwoAndMinNotGreaterThanMax called");
		int[] result = sdao.getMinMaxPriceInCent();
		assertNotNull(result);
		assertTrue(result.length == 2);		
		assertTrue(result[0] <= result[1]);
	}
	
	@Test
	public void testFindByPerformance_ShouldNotReturnNull(){
		LOG.info("testFindByPerformance_ShouldNotReturnNull called");
		List<Show> l = sdao.findByPerformance_id(1);
		assertNotNull(l);
	}
}
