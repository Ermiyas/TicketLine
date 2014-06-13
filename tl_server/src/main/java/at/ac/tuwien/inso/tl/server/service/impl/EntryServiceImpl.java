package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.BasketDao;
import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.Receipt;
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
	
	// TODO ev. getById(Integer id), create(Entry entry), find(Entry entry), update(Entry entry), deleteById(Integer id), getAll(), ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public List<Entry> getListByBasketId(Integer id) throws ServiceException {
		LOG.info("getListByBasketId called.");

		try {
//			return entryDao.findEntriesByBasketId(id);		// TODO Temporaerloesung v. Robert,
			return entryDao.findByBasket_id(id);			// durch endgueltige Implementierung ersetzt
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

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
	public Boolean hasReceipt(Integer id) throws ServiceException {
		LOG.info("hasReceipt called");
		
		if(id == null){
			throw new ServiceException("ID must not be null");
		}
		
		Entry e = entryDao.findOne(id);
		if(e == null){
			throw new ServiceException("Not Entry found for id "+id);
		}
		
		if(e.getReceipt() == null){
			return false;
		}
		else{
			return true;
		}
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
			ticketDao.delete(ticketDao.getOne(t.getId()));
			
		}
		if(a != null){
			//TODO delete article
		}
		if(r == null){
			entryDao.delete(e);
		}
		
		
	}

	// TODO Zum Testen.
	public void setEntryDao(EntryDao dao) {
		this.entryDao = dao;
	}

}