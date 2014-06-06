package at.ac.tuwien.inso.tl.dao;


import java.util.Map;



import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Ticket;

public class TicketDaoTest extends AbstractDaoTest {
	@Autowired
	private TicketDao tdao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void getTicketForEntryWithId1ShouldHaveTicketId1AndBooleanTrueTest(){
		Map.Entry<Ticket, Boolean> entry = tdao.getTicketForEntry(1);
		assertTrue(entry.getKey().getId() == 1);
		assertTrue(entry.getValue());
	}
	
	@Test
	public void deleteTicketAndEntryWithTicketIdShouldDeleteTicketAndEntry(){
		
		/*
		assertTrue(tdao.exists(7));
		tdao.undoTicket(7);
		assertFalse(tdao.exists(7));
		
		*/
	}
	
}

