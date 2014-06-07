package at.ac.tuwien.inso.tl.dao;


import java.util.Map;





import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Ticket;

public class TicketDaoTest extends AbstractDaoTest {
	
	private static final Logger LOG = Logger.getLogger(TicketDaoTest.class);
	
	@Autowired
	private TicketDao tdao;
	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}	
	
	@Test
	public void createStandingTicketTest(){
		LOG.info("createStandingTicket called.");
		tdao.createTicket(19, null, null);
	}
	
	@Test
	public void createSeatTicketTest(){
		LOG.info("createSeatTicket called.");
		tdao.createTicket(null, 50, null);
	}
	
	@Test
	public void getTicketForEntryWithId1ShouldHaveTicketId1AndBooleanTrueTest(){
		LOG.info("getTicketForEntryWithId1ShouldHaveTicketId1AndBooleanTrueTest called.");
		Map.Entry<Ticket, Boolean> entry = tdao.getTicketForEntry(1);
		assertTrue(entry.getKey().getId() == 1);
		assertTrue(entry.getValue());
	}
	
	@Test
	public void deleteTicketAndEntryWithTicketIdShouldDeleteTicketAndEntry(){
		LOG.info("deleteTicketAndEntryWithTicketIdShouldDeleteTicketAndEntry called.");
		
		assertTrue(tdao.exists(7));
		tdao.undoTicket(7);
		assertFalse(tdao.exists(7));
	}
	
}

