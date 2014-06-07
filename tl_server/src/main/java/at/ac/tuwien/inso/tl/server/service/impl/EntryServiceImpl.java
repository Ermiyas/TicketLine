package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.BasketDao;
import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {
	
	private static final Logger LOG = Logger.getLogger(PerformanceServiceImpl.class);
	
	@Autowired
	private EntryDao entryDao;
	
	@Autowired
	private BasketDao basketDao;

	@Override
	public Entry createEntry(Entry entry, Integer basket_id) throws ServiceException {
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
		}
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		
		try{
			Basket b = basketDao.getOne(basket_id);
			try {	
				entry.setBasket(b);
				return entryDao.saveAndFlush(entry);
			} catch (Exception e) {
				throw new ServiceException(e);
			}
		}catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
		
		
	}

	@Override
	public List<java.util.Map.Entry<Entry, Boolean>> getEntry(Integer basket_id) throws ServiceException {
		if(basket_id == null){
			throw new ServiceException("Basket_id must not be null");
		}
		try {	
			return entryDao.getEntry(basket_id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
