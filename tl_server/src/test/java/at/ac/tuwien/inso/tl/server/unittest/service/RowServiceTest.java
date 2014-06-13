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

import at.ac.tuwien.inso.tl.dao.RowDao;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.RowServiceImpl;

public class RowServiceTest {

	private RowServiceImpl service = null;
	private List<Row> testData = null;
	private RowDao dao;
	private List<Show> allShows = null;
	
	private static final Logger LOG = Logger.getLogger(RowServiceTest.class);
	
	@Before
	public void setUp()  {
		
		LOG.info("setUp called.");
		service = new RowServiceImpl();
		testData = new ArrayList<Row>();
		
		Show s1 = new Show();
		s1.setId(1);
		
		Show s2 = new Show();
		s2.setId(2);
		
		allShows = new ArrayList<Show>();
		allShows.add(s1);
		allShows.add(s2);
		
		Row r1 = new Row();
		r1.setId(1);	
		r1.setShow(s1);
		testData.add(r1);		

		Row r2 = new Row();
		r1.setId(2);	
		r2.setShow(s1);
		testData.add(r2);		

		Row r3 = new Row();
		r1.setId(3);	
		r3.setShow(s2);
		testData.add(r3);			

		dao = Mockito.mock(RowDao.class);
		Mockito.when(dao.findAll()).thenReturn(testData);
		Mockito.when(dao.findRows(null)).thenReturn(testData);
		Mockito.when(dao.findRows(1)).thenReturn(testData.subList(0, 2));
		Mockito.when(dao.findRows(2)).thenReturn(testData.subList(2, 3));
		
		service.setRowDao(dao);
	}
	
	@Test
	public void testfindRows_ShouldNotReturnNull()
	{
		LOG.info("testfindRows_ShouldNotReturnNull called.");
		try
		{
			assertNotNull(service.findRows(null));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindRows_ShouldReturnEmptyListIfIdIsInvalid()
	{
		LOG.info("testfindRows_ShouldReturnEmptyListIfIdIsInvalid called.");		
		
		int maxID = 0;
		for(Show s: allShows)
		{
			if(s.getId() > maxID)
				maxID = s.getId();
		}
		
		LOG.debug(String.format("max Show ID is %d, so trying to find Seats for ID %d", maxID, maxID + 1));		
		maxID++;
		
		try
		{
			assertTrue(service.findRows(maxID).size() == 0);
		}
		catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindRows_ShouldReturnAllWithNullValue()
	{
		LOG.info("testfindRows_ShouldReturnAllWithNullValue called.");
		try
		{						
			LOG.debug("loading all row (per find).");						
			List<Row> found = service.findRows(null);
			
			LOG.debug(String.format("findAll: %d rows, find: %d rows.", testData.size(), found.size()));
			
			assertTrue(testData.equals(found));
		}
		catch (ServiceException e) {
			fail("ServiceException thrown");
		}			
	}
	
	@Test
	public void testfindRows_findFirstSeatByID()
	{
		LOG.info("testfindRows_findFirstSeatByID called.");			

		try
		{
			Show firstShow = allShows.get(0);
			
			LOG.debug(String.format("find all rows for show with ID %d.", firstShow.getId()));
			List<Row> foundRows = service.findRows(firstShow.getId());
			
			LOG.debug(String.format("found %d rows.", foundRows.size()));
			
			for(Row r: foundRows)
			{
				if(r.getShow() != firstShow)
				   fail("row has wrong show");
			}		
			
			LOG.debug("testing if all rows have been loaded.");
			for(Row r: testData)
			{
				if(r.getShow() == firstShow)
					assertTrue(foundRows.contains(r));
			}
		}
		catch (ServiceException e) {
			fail("ServiceException thrown");
		}	
	}
}
