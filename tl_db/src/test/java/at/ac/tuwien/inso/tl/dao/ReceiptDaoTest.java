package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.Date;
import org.hibernate.service.spi.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
	public void testCreateReceipt_ShouldSetId(){
		
		try{
		
			Receipt r = new Receipt();
			r.setPaymentType(PaymentType.CASH);
			r.setTransactionDate(new Date(System.currentTimeMillis()));
			r = rdao.save(r);
			
			if(r.getId() == null){
				throw new ServiceException("ID not Set");
			}
		}
		catch(ServiceException e){
			fail("ServiceException thrown");
		}
		
		
	}
	
	
}

