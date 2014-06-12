package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;

public class SeatDaoTest extends AbstractDaoTest {
	
	private static final Logger LOG = Logger.getLogger(SeatDaoTest.class);
	
	@Autowired
	private SeatDao sdao;
	
	@Autowired
	private RowDao rdao;
	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}	
	
	@Test
	public void testfindSeats_ShouldReturnEmptyListIfIdIsInvalid()
	{
		LOG.info("testfindSeats_ShouldReturnEmptyListIfIdIsInvalid called.");
		
		LOG.debug("loading all rows (per findAll).");		
		List<Row> allRows = rdao.findAll();
		
		int maxID = 0;
		for(Row r: allRows)
		{
			if(r.getId() > maxID)
				maxID = r.getId();
		}
		
		LOG.debug(String.format("max Row ID is %d, so trying to find Seats for ID %d", maxID, maxID + 1));		
		maxID++;		
		assertTrue(sdao.findSeats(maxID).size() == 0);
	}
	
	
	@Test
	public void testfindSeats_NullParameterShouldReturnAllSeats()
	{
		LOG.info("testfindSeats_NullParameterShouldReturnAllSeats called.");
		
		LOG.debug("loading all seats (per findAll).");		
		List<Seat> allSeats = sdao.findAll();
		
		LOG.debug("loading all seats (per find).");						
		List<Seat> foundSeats = sdao.findSeats(null);
		
		LOG.debug(String.format("findAll: %d seats, find: %d seats.", allSeats.size(), foundSeats.size()));
		
		assertTrue(allSeats.equals(foundSeats));
	}	
	
	@Test
	public void testfindSeats_findFirstSeatByID()
	{
		LOG.info("testfindSeats_findFirstSeatByID called.");
		
		LOG.debug("loading all Rows (per findAll).");		
		List<Row> allRows = rdao.findAll();
		
		if(allRows.size() > 0)
		{		
			Row firstRow = allRows.get(0);
			
			LOG.debug(String.format("find all seats for row with ID %d.", firstRow.getId()));
			List<Seat> foundSeats = sdao.findSeats(firstRow.getId());
			
			LOG.debug(String.format("found %d seats.", foundSeats.size()));
			
			for(Seat s: foundSeats)
			{
				if(s.getRow() != firstRow)
				   fail("Seat has wrong row");
			}		
			
			LOG.debug("testing if all seats have been loaded.");
			for(Seat s: sdao.findAll())
			{
				if(s.getRow() == firstRow)
					assertTrue(foundSeats.contains(s));
			}
		}
		else
		{
			LOG.error("no seats found.");		
		}
	}
}

