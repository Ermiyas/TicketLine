package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;

public class ReceiptDaoTest extends AbstractDaoTest {
	
	@Autowired
	private ReceiptDao rdao;
	
	@Autowired
	private EntryDao edao;

	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void createReceiptForEntriesTest(){
		
		List<Entry> entries = new ArrayList<Entry>();
		Entry e = edao.getOne(2);
		Entry e2 = edao.getOne(3);
		
		entries.add(e);
		entries.add(e2);
		
		assertTrue(e.getReceipt() == null);
		
		Receipt r = rdao.createReceiptforEntries(entries, PaymentType.CASH);
		
		
	}
	
	
}



