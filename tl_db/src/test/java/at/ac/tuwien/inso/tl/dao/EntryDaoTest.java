package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Entry;

public class EntryDaoTest {
	@Autowired
	private EntryDao edao;
	
	private static final Logger LOG = Logger.getLogger(TicketDaoTest.class);
	

	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}	
	
	
	@Test
	public void testFindByBasketId_ShouldNotReturnEmpty(){
		LOG.info("getEntryWithBasketId1TestShouldNotReturnEmpty called");
		
		if(edao == null){
			LOG.error("Autowired EntryDao null");
		}
		else{
			List<Entry> entries = edao.findByBasket_id(1);
			assertFalse(entries.isEmpty());
		}
		
	}

}
