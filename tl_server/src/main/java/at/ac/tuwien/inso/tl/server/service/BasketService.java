package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface BasketService {
	
	// TODO create(Basket basket), find(Basket basket), update(Basket basket), deleteById(Integer id), ...

	/**
	 * Returns the basket object identified by the given id
	 * 
	 * @param id of the basket object
	 * @return the basket object
	 * @throws ServiceException
	 */
	public Basket getById(Integer id) throws ServiceException;	
	
	/**
	 * Returns a collection of all baskets.
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<Basket> getAll() throws ServiceException;
}