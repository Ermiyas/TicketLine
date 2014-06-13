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
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Entry;
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

}

