package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.ArticleDao;
import at.ac.tuwien.inso.tl.dao.BasketDao;
import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.dao.SeatDao;
import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {
	
	private static final Logger LOG = Logger.getLogger(EntryServiceImpl.class);
	
	@Autowired
	private EntryDao entryDao;
	
	@Autowired
	private BasketDao basketDao;
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private SeatDao seatDao;
	
	@Autowired
	private ArticleDao articleDao;

	@Override
	@Transactional
	@Modifying
	public Entry createEntry(Entry entry, Integer basket_id) throws ServiceException {
		
		LOG.info("createEntry called");
		
		if(entry == null){
			throw new ServiceException("entry must not be null");
		}
		else{
			if(entry.getBuyWithPoints() == null){
				throw new ServiceException("entry buywithpoints must not be null");
			}
			if(entry.getAmount() == null){
				throw new ServiceException("entry amount must not be null");
			}
			if(entry.getSold() == null){
				throw new ServiceException("entry sold-boolean must not be null");
			}
		}
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		
		
		Basket b = basketDao.findOne(basket_id);
		if(b == null){
			throw new ServiceException("No Basket found for basket_id "+basket_id);
		}
		entry.setBasket(b);
		return entryDao.save(entry);
		
	}		

	@Override
	@Transactional
	@Modifying
	public Entry createEntryForArticle(Entry entry, Integer articleID,
			Integer basket_id) throws ServiceException {
		LOG.info("createEntryForArticle called");
		
		if(entry == null){
			throw new ServiceException("entry must not be null");
		}
		if(articleID == null){
			throw new ServiceException("article must not be null");
		}			
		if(entry.getBuyWithPoints() == null){
			throw new ServiceException("entry buywithpoints must not be null");
		}
		if(entry.getAmount() == null){
			throw new ServiceException("entry amount must not be null");
		}
		if(entry.getSold() == null){
			throw new ServiceException("entry sold-boolean must not be null");
		}		
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}		
		
		Basket b = basketDao.findOne(basket_id);
		if(b == null){
			throw new ServiceException("No Basket found for basket_id "+basket_id);
		}
		
		Article a = articleDao.findOne(articleID);
		if(a == null){
			throw new ServiceException("No Article found for articleID "+articleID);
		}
		
		if(entry.getBuyWithPoints())
		{
			if(a.getPriceInPoints() == null)
			{
				throw new ServiceException("This article cannot be bought with points.");
			}
		}
		else
		{
			if(a.getPriceInCent() == null)
			{
				throw new ServiceException("This article cannot be bought with money.");
			}
		}
		
		entry.setBasket(b);
		entry.setArticle(a);
		return entryDao.save(entry);
	}



	@Override
	@Transactional
	public List<java.util.Map.Entry<Entry, Boolean>> getEntry(Integer basket_id) throws ServiceException {
		
		LOG.info("getEntry called");
		if(basket_id == null){
			throw new ServiceException("Basket_id must not be null");
		}
		
		ArrayList<Map.Entry<Entry, Boolean>> result = new ArrayList<Map.Entry<Entry, Boolean>>();
		
		for(Entry e: entryDao.findByBasket_id(basket_id)){
			Ticket ticket = e.getTicket();
			Article article = e.getArticle();
			if(ticket == null && article == null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, null));
			}
			else if(ticket != null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, true));
			}
			else if(article != null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, false));
			}
		}
		
		return result;
	}

	@Override
	@Transactional
	@Modifying
	public void undoEntry(Integer id) throws ServiceException {
		LOG.info("undoEntry called");
		if(id == null){
			throw new ServiceException("ID must not be null");
		}
		
		Entry e = entryDao.findOne(id);
		if(e == null){
			throw new ServiceException("No Entry found for ID "+id);
		}
		
		Ticket t = e.getTicket();
		Article a = e.getArticle();
		Receipt r = e.getReceipt();
		if(t != null){
			e.setTicket(null);
			entryDao.saveAndFlush(e);
			Seat s = t.getSeat();
			if(s != null){
				s = seatDao.findOne(s.getId());
				s.setTicket(null);
				seatDao.delete(s);
				seatDao.flush();
			}
			ticketDao.delete(ticketDao.findOne(t.getId()));
			ticketDao.flush();
			
		}
		if(a != null){
			e.setArticle(null);
			entryDao.saveAndFlush(e);
		}
		if(r == null){
			entryDao.delete(e);
			entryDao.flush();
		}				
	}

	@Override
	public Boolean isReversible(Integer id) throws ServiceException {
		LOG.info("isReversible called");
		if(id == null){
			throw new ServiceException("ID must not be null");
		}
		Entry e = entryDao.findOne(id);
		if(e == null){
			throw new ServiceException("No Entry found for ID "+id);
		}
		
		Ticket t = e.getTicket();
		Article a = e.getArticle();
		
		if(t != null){
			LOG.debug("ticket not null");
			Show show = t.getShow();
			if(show == null){
				LOG.debug("ticket = sitzplatz");
				Seat seat = t.getSeat();
				if(seat != null){
					LOG.debug("seat != null");
					Row row = seat.getRow();
					if(row != null){
						LOG.debug("row != null");
						show = row.getShow();
					}
				}
			}
			if(show != null){
				int c = show.getDateOfPerformance().compareTo(new Date(System.currentTimeMillis()));
				if(c > 0){
					return true;
				}
			}
			
		} else if(a != null){
			return true;
		}
		
		return false;
	}

	@Override
	public Entry findEntryBySeat(int seat_id) throws ServiceException {
		LOG.info("isSold called");
		try {
			Ticket ticket = seatDao.findOne(seat_id).getTicket(); 
			return ticketDao.findOne(ticket.getId()).getEntry();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}

