package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Performance;

public class PerformanceDaoTest extends AbstractDaoTest {
	
	private static final Logger LOG = Logger.getLogger(PerformanceDaoTest.class);	
	
	@Autowired
	private PerformanceDao pdao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}		
	
	@Test
	public void testfindAll_ShouldNotReturnNull()
	{
		LOG.info("testfindAll_ShouldNotReturnNull called.");
		assertNotNull(pdao.findAll());
	}
	
	@Test
	public void testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities()
	{
		LOG.info("testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities called.");
		assertTrue(pdao.findAll().size() == 
				pdao.findPerformancesSortedBySales(null, null, null, null, null, null).size());		
	}		
	
	@Test
	public void testfindPerformancesSortedBySales_findByContent()
	{
		LOG.info("testfindPerformancesSortedBySales_findByContent called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales("routine", null, null, null, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 1);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByDescription()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDescription called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales(null, "le", null, null, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 3);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByDurationInMinutesFrom()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDurationInMinutesFrom called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales(null, null, 255, null, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 3);			
	}	
	
	@Test
	public void testfindPerformancesSortedBySales_findByDurationInMinutesTo()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDurationInMinutesTo called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales(null, null, null, 100, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByPerformanceType()
	{
		LOG.info("testfindPerformancesSortedBySales_findByPerformanceType called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales(null, null, null, null, "konzert", null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByArtist()
	{
		LOG.info("testfindPerformancesSortedBySales_findByArtist called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales(null, null, null, null, null, 9);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByCombination()
	{
		LOG.info("testfindPerformancesSortedBySales_findByCombination called.");
		List<Entry<Performance, Integer>> performances = 
				pdao.findPerformancesSortedBySales("JacKsOn", "IT", 80, 200, "KoNzerT", 9);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}	
}
