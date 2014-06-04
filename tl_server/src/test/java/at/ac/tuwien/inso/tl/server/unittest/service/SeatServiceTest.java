package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import at.ac.tuwien.inso.tl.dao.SeatDao;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.SeatServiceImpl;

public class SeatServiceTest {
	
	private SeatServiceImpl service = null;
	private List<Seat> testData = null;
	private SeatDao dao;
	private List<Row> allRows = null;
	
	private static final Logger LOG = Logger.getLogger(SeatServiceTest.class);
	
	@Before
	public void setUp()  {
		
		LOG.info("setUp called.");
		service = new SeatServiceImpl();
		testData = new ArrayList<Seat>();
		
		Row r1 = new Row();
		r1.setId(1);
		
		Row r2 = new Row();
		r2.setId(2);
		
		allRows = new ArrayList<Row>();
		allRows.add(r1);
		allRows.add(r2);
		
		Seat s1 = new Seat();
		s1.setId(1);
		s1.setRow(r1);
		s1.setSequence(1);
		s1.setTicket(null);
		testData.add(s1);
		
		Seat s2 = new Seat();
		s2.setId(2);
		s2.setRow(r1);
		s2.setSequence(2);
		s2.setTicket(null);
		testData.add(s2);
		
		Seat s3 = new Seat();
		s3.setId(1);
		s3.setRow(r2);
		s3.setSequence(1);
		s3.setTicket(null);
		testData.add(s3);		

		dao = Mockito.mock(SeatDao.class);
		Mockito.when(dao.findAll()).thenReturn(testData);
		Mockito.when(dao.findSeats(null)).thenReturn(testData);
		Mockito.when(dao.findSeats(1)).thenReturn(testData.subList(0, 2));
		Mockito.when(dao.findSeats(2)).thenReturn(testData.subList(2, 3));
		
		service.setSeatDao(dao);
	}
	
	@Test
	public void testfindShows_ShouldReturnNullIfIdIsInvalid()
	{
		LOG.info("testfindShows_ShouldReturnNullIfIdIsInvalid called.");				
		
		int maxID = 2;		
		
		LOG.debug(String.format("max Row ID is %d, so trying to find Seats for ID %d", maxID, maxID + 1));		
		maxID++;	
		
		try {
			assertTrue(service.findSeats(maxID).size() == 0);
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testfindShows_NullParameterShouldReturnAllSeats()
	{
		LOG.info("testfindShows_NullParameterShouldReturnAllSeats called.");
		
		try {			
		
			LOG.debug("loading all seats (per getAllSeats).");		
			List<Seat> allSeats = service.getAllSeats();
			
			LOG.debug("loading all seats (per find).");						
			List<Seat> foundSeats = service.findSeats(null);
			
			LOG.debug(String.format("findAll: %d seats, find: %d seats.", allSeats.size(), foundSeats.size()));
			
			assertTrue(allSeats.equals(foundSeats));
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}	
	
	@Test
	public void testfindShows_findFirstSeatByID()
	{
		LOG.info("testfindShows_findFirstSeatByID called.");
		
		try {		
			Row firstRow = allRows.get(0);
			
			LOG.debug(String.format("find all seats for row with ID %d.", firstRow.getId()));
			List<Seat> foundSeats = service.findSeats(firstRow.getId());
			
			LOG.debug(String.format("found %d seats.", foundSeats.size()));
			
			for(Seat s: foundSeats)
			{
				if(s.getRow() != firstRow)
				   fail("Seat has wrong row");
			}		
			
			LOG.debug("testing if all seats have been loaded.");
			for(Seat s: service.getAllSeats())
			{
				if(s.getRow() == firstRow)
					assertTrue(foundSeats.contains(s));
			}
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}	
}
