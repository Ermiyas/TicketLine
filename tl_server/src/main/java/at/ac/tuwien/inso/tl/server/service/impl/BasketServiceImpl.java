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

import at.ac.tuwien.inso.tl.dao.BasketDao;
import at.ac.tuwien.inso.tl.dao.CustomerDao;
import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.dao.PropertySpecifiations;
import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.Ticket;
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
	
	@Autowired
	private CustomerDao customerDao;
	

	@Override
	@Transactional
	public Basket createBasket() throws ServiceException {
		LOG.info("createBasket called");
		Basket b = new Basket();
		b.setCreationdate(new Date(System.currentTimeMillis()));
		
		try {	
			return basketDao.save(b);
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
			return basketDao.findOne(basket_id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	@Modifying
	public void undoBasket(Integer basket_id) throws ServiceException {
		LOG.info("undoBasket called");
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		
		for(Entry e: entryDao.findByBasket_id(basket_id)){
			Ticket t = e.getTicket();
			if(e.getReceipt() == null){
				entryDao.delete(e);
			}
			else{
				throw new ServiceException("UndoBasket not allowed(Receipt existing for some Entry)");
			}
			if(t != null){
				ticketDao.delete(t);
			}
		}
		
		basketDao.delete(basket_id);

	}

	@Override
	public void setCustomerForBasket(Basket basket, Integer customer_id)
			throws ServiceException {
		LOG.info("setCustomerForBasket called");
		
		if(basket == null){
			throw new ServiceException("basket must not be null.");
		}
		if(basket.getId() == null){
			throw new ServiceException("basket_id must not be null.");
		}
		if(basket.getCreationdate() == null){
			throw new ServiceException("basket creatindate must not be null.");
		}
		
		Customer c = null;
		if(customer_id != null){
			c = customerDao.findOne(customer_id);
			if(c == null){
				throw new ServiceException("Customer with customer_id "+customer_id+" not found!");
			}
		}
		basket.setCustomer(c);
		basketDao.save(basket);
	}

	

	@Override
	public List<Map.Entry<Basket, Customer>> findBasket(
			Integer basket_id, Customer customers) throws ServiceException {
		
		LOG.info("findBasket called");
		
		List<Basket> baskets = null;
		List<Map.Entry<Basket, Customer>> result = new ArrayList<Map.Entry<Basket, Customer>>();
		
		if(customers != null){
			List<Integer> customerIds = new ArrayList<Integer>();
			for(Customer c: customerDao.findAll(PropertySpecifiations.searchMatch(customers))){
				customerIds.add(c.getId());
			}
			if(basket_id != null){
				if(customerIds.size() != 0){
					baskets = basketDao.findByBasket_idAndCustomer_ids(basket_id, customerIds);
				}
			}
			else{
				if(customerIds.size() != 0){
					baskets = basketDao.findByCustomer_ids(customerIds);
				}
			}
		}
		else{
			if(basket_id != null){
				baskets = new ArrayList<Basket>();
				Basket b = basketDao.findOne(basket_id);
				if(b != null){
					baskets.add(basketDao.findOne(basket_id));
				}
			}
			else{
				baskets = basketDao.findAll();
			}
		}
		
		if(baskets != null){
			for(Basket b:baskets){
				result.add(new AbstractMap.SimpleEntry<Basket,Customer>(b,b.getCustomer()));
			}
		}
		
		
		return result;
	}

}
