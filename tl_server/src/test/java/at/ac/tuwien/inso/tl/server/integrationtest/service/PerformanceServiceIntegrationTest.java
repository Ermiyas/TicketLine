package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.PerformanceService;

public class PerformanceServiceIntegrationTest extends AbstractServiceIntegrationTest{
	
	@Autowired
	PerformanceService service; 

	private static final Logger LOG = Logger.getLogger(PerformanceServiceIntegrationTest.class);
	
	@Before
	public void setUp(){
	}		
	
	@Test
	public void testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities()
	{
		LOG.info("testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities called.");
		try
		{
			assertTrue(service.getAllPerformances().size() == 
				service.findPerformancesSortedBySales(null, null, null, null, null, null).size());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}	
	
	@Test
	public void testfindPerformancesSortedBySales_findByContent()
	{
		LOG.info("testfindPerformancesSortedBySales_findByContent called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales("routine", null, null, null, null, null);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 1);	
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByDescription()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDescription called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales(null, "le", null, null, null, null);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 3);	
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByDurationInMinutesFrom()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDurationInMinutesFrom called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales(null, null, 255, null, null, null);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 3);		
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}	
	
	@Test
	public void testfindPerformancesSortedBySales_findByDurationInMinutesTo()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDurationInMinutesTo called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales(null, null, null, 100, null, null);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 4);	
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByPerformanceType()
	{
		LOG.info("testfindPerformancesSortedBySales_findByPerformanceType called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales(null, null, null, null, "konzert", null);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 4);		
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByArtist()
	{
		LOG.info("testfindPerformancesSortedBySales_findByArtist called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales(null, null, null, null, null, 9);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 4);			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByCombination()
	{
		LOG.info("testfindPerformancesSortedBySales_findByCombination called.");
		try
		{
			List<Entry<Performance, Integer>> performances = 
				service.findPerformancesSortedBySales("JacKsOn", "IT", 80, 200, "KoNzerT", 9);
			assertTrue(performances.size() == 1);
			assertTrue(performances.get(0).getKey().getId() == 4);			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}	
}
