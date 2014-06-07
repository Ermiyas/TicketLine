package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Show;

public class RowDaoTest extends AbstractDaoTest {
	
	private static final Logger LOG = Logger.getLogger(RowDaoTest.class);	
	
	@Autowired
	private RowDao rdao;
	
	@Autowired
	private ShowDao sdao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	@Test
	public void testfindRows_ShouldNotReturnNull()
	{
		LOG.info("testfindRows_ShouldNotReturnNull called.");
		assertNotNull(rdao.findRows(null));
	}
	
	@Test
	public void testfindRows_ShouldReturnEmptyListIfIdIsInvalid()
	{
		LOG.info("testfindRows_ShouldReturnEmptyListIfIdIsInvalid called.");
		
		LOG.debug("loading all shows (per findAll).");		
		List<Show> allShow = sdao.findAll();
		
		int maxID = 0;
		for(Show s: allShow)
		{
			if(s.getId() > maxID)
				maxID = s.getId();
		}
		
		LOG.debug(String.format("max Show ID is %d, so trying to find Seats for ID %d", maxID, maxID + 1));		
		maxID++;		
		assertTrue(rdao.findRows(maxID).size() == 0);
	}
	
	@Test
	public void testfindRows_ShouldReturnAllWithNullValue()
	{
		LOG.info("testfindRows_ShouldReturnAllWithNullValue called.");
		
		LOG.debug("loading all row (per findAll).");		
		List<Row> all = rdao.findAll();
		
		LOG.debug("loading all row (per find).");						
		List<Row> found = rdao.findRows(null);
		
		LOG.debug(String.format("findAll: %d rows, find: %d rows.", all.size(), found.size()));
		
		assertTrue(all.equals(found));
	}
	
	@Test
	public void testfindRows_findFirstSeatByID()
	{
		LOG.info("testfindRows_findFirstSeatByID called.");
		
		LOG.debug("loading all Shows (per findAll).");		
		List<Show> allShows = sdao.findAll();
		
		if(allShows.size() > 0)
		{		
			Show firstShow = allShows.get(0);
			
			LOG.debug(String.format("find all rows for show with ID %d.", firstShow.getId()));
			List<Row> foundRows = rdao.findRows(firstShow.getId());
			
			LOG.debug(String.format("found %d rows.", foundRows.size()));
			
			for(Row r: foundRows)
			{
				if(r.getShow() != firstShow)
				   fail("row has wrong show");
			}		
			
			LOG.debug("testing if all rows have been loaded.");
			for(Row r: rdao.findAll())
			{
				if(r.getShow() == firstShow)
					assertTrue(foundRows.contains(r));
			}
		}
		else
		{
			LOG.error("no rows found.");		
		}
	}
}
