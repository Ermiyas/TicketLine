package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.BasketDao;
import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {
	
	private static final Logger LOG = Logger.getLogger(BasketServiceImpl.class);
	
	@Autowired
	private BasketDao basketDao;
	
	@Autowired
	private EntryDao entryDao;
	
	@Autowired
	private TicketDao ticketDao;

	@Override
	public Basket createBasket() throws ServiceException {
		LOG.info("createBasket called");
		Basket b = new Basket();
		b.setCreationdate(new Date(System.currentTimeMillis()));
		
		try {	
			return basketDao.saveAndFlush(b);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public Basket getBasket(Integer basket_id) throws ServiceException {
		LOG.info("getBasket called");
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		try {	
			return basketDao.getOne(basket_id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void undoBasket(Integer basket_id) throws ServiceException {
		LOG.info("undoBasket called");
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		
		for(java.util.Map.Entry<Entry, Boolean> map: entryDao.getEntry(basket_id)){
			Entry e = map.getKey();
			if(e.getTicket() != null){
				ticketDao.undoTicket(e.getTicket().getId());
			}
			else{
				entryDao.delete(e.getId());
			}
		}
		
		basketDao.delete(basket_id);

	}

	@Override
	public void setCustomerForBasket(Basket basket, Integer customer_id)
			throws ServiceException {
		LOG.info("setCustomerForBasket called");
		
		basketDao.setCustomerForBasket(basket, customer_id);

	}

	@Override
	public List<Basket> findBasket(Integer basket_id, List<Integer> customers)
			throws ServiceException {
		LOG.info("findBasket called");
		// TODO Auto-generated method stub
		return null;
	}

}
