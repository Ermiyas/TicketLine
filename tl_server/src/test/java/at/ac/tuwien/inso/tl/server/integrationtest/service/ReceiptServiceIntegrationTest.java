package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ReceiptService;
public class ReceiptServiceIntegrationTest extends
		AbstractServiceIntegrationTest {
	
	@Autowired
	ReceiptService service;
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void testCreateReceiptforEntries_ShouldNotReturnNull(){
		
		ArrayList<Entry> l = new ArrayList<Entry>();
		Entry e = new Entry();
		e.setId(5);
		l.add(e);
		try {
			Receipt r = service.createReceiptforEntries(l, PaymentType.CASH);
			assertNotNull(r);
		} catch (ServiceException e1) {
			fail("ServerException thrown");
		}
	}
	
	@Test
	public void testCreateReceiptforEntriesWithNullEntryList_ShouldThrowServiceException(){
		
		try {
			Receipt r = service.createReceiptforEntries(null, PaymentType.CASH);
			fail("ServerException thrown");
		} catch (ServiceException e1) {
			
		}
	}
	
	@Test
	public void testCreateReceiptforEntriesWithEmptyEntryList_ShouldThrowServiceException(){
		ArrayList<Entry> l = new ArrayList<Entry>();
		try {
			Receipt r = service.createReceiptforEntries(l, PaymentType.CASH);
			fail("ServerException thrown");
		} catch (ServiceException e1) {
			
		}
	}
	
	@Test
	public void testCreateReceiptforEntriesWithNullPaymentType_ShouldNotReturnNull(){
		
		ArrayList<Entry> l = new ArrayList<Entry>();
		Entry e = new Entry();
		e.setId(5);
		l.add(e);
		try {
			Receipt r = service.createReceiptforEntries(l, null);
			fail("ServerException thrown");
		} catch (ServiceException e1) {
			
		}
	}

}
