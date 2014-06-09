package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.assertTrue;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import at.ac.tuwien.inso.tl.dao.PerformanceDao;
import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.server.service.impl.PerformanceServiceImpl;
public class PerformanceServiceTest {

	private PerformanceServiceImpl service = null;
	private List<Entry<Performance, Integer>> testData = null;
	private PerformanceDao dao;
	
	private static final Logger LOG = Logger.getLogger(PerformanceServiceTest.class);
	
	@Before
	public void setUp()  {
		
		LOG.info("setUp called.");
		service = new PerformanceServiceImpl();
		testData = new ArrayList<Entry<Performance, Integer>>();
		
		Performance p1 = new Performance();
		p1.setId(1);
		p1.setContent("Godzilla");
		p1.setDescription("Als bei einem angeblichen Routine-Einsatz am Arbeitsplatz");
		p1.setDurationInMinutes(125);
		p1.setPerformancetype("Film");		
		testData.add(new AbstractMap.SimpleEntry<Performance, Integer>(p1, 100));			
		
		Performance p3 = new Performance();
		p3.setId(3);
		p3.setContent("Koenig Lear");
		p3.setDescription("Shakespeares duesteres Trauerspiel, das nicht nur seine Zeitgenossen verstoerte");
		p3.setDurationInMinutes(255);
		p3.setPerformancetype("Theaterstueck");
		testData.add(new AbstractMap.SimpleEntry<Performance, Integer>(p3, 25));
		
		Performance p4 = new Performance();
		p4.setId(4);
		p4.setContent("This is it!");
		p4.setDescription("Michael Jacksons letzte Tour");
		p4.setDurationInMinutes(100);
		p4.setPerformancetype("Konzert");
		testData.add(new AbstractMap.SimpleEntry<Performance, Integer>(p4, 125));				
		
						
		dao = Mockito.mock(PerformanceDao.class);
		List<Performance> performances = new ArrayList<Performance>();
		performances.add(p1);
		performances.add(p3);
		performances.add(p4);
		Mockito.when(dao.findAll()).thenReturn(performances);
		Mockito.when(dao.findPerformancesSortedBySales(null, null, null, null, null, null)).thenReturn(testData);
		Mockito.when(dao.findPerformancesSortedBySales("routine", null, null, null, null, null)).thenReturn(testData.subList(0, 1));
		Mockito.when(dao.findPerformancesSortedBySales(null, "le", null, null, null, null)).thenReturn(testData.subList(1, 2));
		Mockito.when(dao.findPerformancesSortedBySales(null, null, 255, null, null, null)).thenReturn(testData.subList(1, 2));
		Mockito.when(dao.findPerformancesSortedBySales(null, null, null, 100, null, null)).thenReturn(testData.subList(2, 3));
		Mockito.when(dao.findPerformancesSortedBySales(null, null, null, null, "konzert", null)).thenReturn(testData.subList(2, 3));
		Mockito.when(dao.findPerformancesSortedBySales(null, null, null, null, null, 9)).thenReturn(testData.subList(2, 3));
		Mockito.when(dao.findPerformancesSortedBySales("JacKsOn", "IT", 80, 200, "KoNzerT", 9)).thenReturn(testData.subList(2, 3));
		
		service.setPerformanceDao(dao);
	}
	
	@Test
	public void testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities()
	{
		LOG.info("testfindPerformancesSortedBySales_WithAllNullShouldReturnAllEntities called.");
		assertTrue(dao.findAll().size() == 
				dao.findPerformancesSortedBySales(null, null, null, null, null, null).size());		
	}	
	
	@Test
	public void testfindPerformancesSortedBySales_findByContent()
	{
		LOG.info("testfindPerformancesSortedBySales_findByContent called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales("routine", null, null, null, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 1);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByDescription()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDescription called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales(null, "le", null, null, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 3);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByDurationInMinutesFrom()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDurationInMinutesFrom called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales(null, null, 255, null, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 3);			
	}	
	
	@Test
	public void testfindPerformancesSortedBySales_findByDurationInMinutesTo()
	{
		LOG.info("testfindPerformancesSortedBySales_findByDurationInMinutesTo called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales(null, null, null, 100, null, null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByPerformanceType()
	{
		LOG.info("testfindPerformancesSortedBySales_findByPerformanceType called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales(null, null, null, null, "konzert", null);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByArtist()
	{
		LOG.info("testfindPerformancesSortedBySales_findByArtist called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales(null, null, null, null, null, 9);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}
	
	@Test
	public void testfindPerformancesSortedBySales_findByCombination()
	{
		LOG.info("testfindPerformancesSortedBySales_findByCombination called.");
		List<Entry<Performance, Integer>> performances = 
				dao.findPerformancesSortedBySales("JacKsOn", "IT", 80, 200, "KoNzerT", 9);
		assertTrue(performances.size() == 1);
		assertTrue(performances.get(0).getKey().getId() == 4);			
	}	
}
