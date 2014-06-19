package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.CustomerDao;
import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.dao.ReceiptDao;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ReceiptService;

@Service
public class ReceiptServiceImpl implements ReceiptService {
	
	private static final Logger LOG = Logger.getLogger(ReceiptServiceImpl.class);
	
	private static final int centToPointsFactor = 100;
	
	@Autowired
	private ReceiptDao receiptDao;
	
	@Autowired
	private EntryDao entryDao;
	
	@Autowired
	private CustomerDao customerDao;

	@Override
	@Transactional
	public KeyValuePairDto<Receipt, Integer> createReceiptforEntries(List<Entry> entries, PaymentType pt)
			throws ServiceException {
		
		LOG.info("createReceiptforEntries called");
		
		if(entries == null){
			throw new ServiceException("List<EntryDto> must not be null.");
		}
		if(entries.isEmpty()){
			throw new ServiceException("List<EntryDto> must contain at least 1 object.");
		}
		if(pt == null){
			throw new ServiceException("PaymentType must not be null.");
		}
		
		List<Entry> dbEntries = new ArrayList<Entry>();
		
		for(Entry e: entries){
			Entry entry = entryDao.findOne(e.getId());
			dbEntries.add(entry);
		}
		
		entries = dbEntries;
		
		Basket b = entries.get(0).getBasket();
		
		if(b == null)
		{
			throw new ServiceException("Every Entry must have a basket.");
		}					
		
		int basketID = b.getId();
		for(Entry e: entries){			
			if(basketID != e.getBasket().getId())
			{
				throw new ServiceException("All entries must belong to the same basket.");
			}			
		}
		
		Customer c = b.getCustomer();
		
		//Neuen Punkte berechnen (Anmerkung: Ein Anoynmer Kunde kann nur die Punkte einlösen, die er durch diese Rechnung bekommen würde. Der Rest geht verloren.)
		int newPoints = c == null ? 0 : c.getPoints();		
		for(Entry e: entries){			
			
			if(e.getAmount() == null)
			{
				throw new ServiceException("The amount must not be null");
			}
			
			if(e.getTicket() != null && e.getArticle() != null)
			{
				throw new ServiceException("An Entry cannot have an Article and a Ticket associated with it.");
			}
			
			if(e.getTicket() != null)
			{
				if(e.getBuyWithPoints())
				{
					throw new ServiceException("Cannot by a Ticket with points.");
				}				
				
				Show s = e.getTicket().getShow();
				if(s == null)
				{
					Seat seat = e.getTicket().getSeat();							
					if(seat == null)
					{
						throw new ServiceException("The Ticket got neither a Show nor a Seat associated to it.");
					}
					Row r = seat.getRow();
					if(r == null)
					{
						throw new ServiceException("The Ticket got neither a Show nor a Row associated to it.");
					}					
					s = r.getShow();
				}
				if(s == null)
				{
					throw new ServiceException("The Ticket got no Show (neither directly nor indirectly) associated to it.");
				}
				if(s.getPriceInCent() == null)
				{
					throw new ServiceException("The price of a Show must not be null.");
				}				
				newPoints += (s.getPriceInCent() * e.getAmount()) / centToPointsFactor;
			}
			else
			{
				if(e.getArticle() != null)
				{
					if(e.getBuyWithPoints())
					{
						if(e.getArticle().getPriceInPoints() == null)
						{
							throw new ServiceException("The price (in points) of the Article must not be null.");
						}
						newPoints -= (e.getArticle().getPriceInPoints() * e.getAmount());
					}
					else
					{
						if(e.getArticle().getPriceInCent() == null)
						{
							throw new ServiceException("The price (in cent) of the Article must not be null.");
						}
						newPoints += (e.getArticle().getPriceInCent() * e.getAmount()) / centToPointsFactor;							
					}
				}
				else
				{
					throw new ServiceException("Every Entry must have either have either an ticket or an article associated with it.");
				}
			}						
		}
		
		if(newPoints < 0)
		{
			throw new ServiceException("The customer doesn't have enough points.");
		}
		
		Receipt receipt = new Receipt();
		receipt.setPaymentType(pt);
		receipt.setTransactionDate(new Date(System.currentTimeMillis()));		
		
		receipt.setEntries(dbEntries);
		receipt = receiptDao.saveAndFlush(receipt);
		
		for(Entry e: dbEntries){
			e.setReceipt(receipt);
			e.setSold(true);
			entryDao.save(e);
		}
		
		entryDao.flush();
		
		if(c != null)
		{
			c.setPoints(newPoints);
			customerDao.saveAndFlush(c);	
		}		
		
		return new KeyValuePairDto<Receipt, Integer>(receipt, c == null ? 0 : newPoints);
		
	}

}
