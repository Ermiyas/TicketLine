package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class PerformanceDaoTest extends AbstractDaoTest {
	@Autowired
	private PerformanceDao pdao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testgetMinAndMaxDuration_ShouldReturnNotNullValueAndSizeIsTwoAndMinNotGreaterThanMax()
	{
		int[] result = pdao.getMinAndMaxDuration();
		assertNotNull(result);
		assertTrue(result.length == 2);		
		assertTrue(result[0] <= result[1]);
	}
	
	@Test
	public void testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities()
	{
		assertTrue(pdao.findAll().size() == 
				pdao.findPerformancesSortedBySales(null, null, null, null, null, null).size());
	}
}
