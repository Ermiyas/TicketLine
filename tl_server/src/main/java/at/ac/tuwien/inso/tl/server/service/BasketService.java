package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface BasketService {
	
	// TODO ev. create(Basket basket), find(Basket basket), update(Basket basket), deleteById(Integer id), ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Returns the basket object identified by the given id
	 * 
	 * @param id of the basket object
	 * @return the basket object
	 * @throws ServiceException
	 */
	public Basket getById(Integer id) throws ServiceException;	
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Returns a collection of all baskets.
	 * 
	 * @return java.util.List
	 * @throws ServiceException
	 */
	public List<Basket> getAll() throws ServiceException;

	/**
	 * Erstellt einen Basket 
	 * @return Ein Dto des erzeugten Basket
	 */
	public Basket createBasket()  throws ServiceException;
	
	/**
	 * 
	 * @param basket_id des gewuenschten Baskets
	 * @return
	 */
	public Basket getBasket(Integer basket_id)  throws ServiceException;
	
	/**
	 * Loescht alle Entries dieses Baskets und ggf. die Tickets dazu sowie den Basket selbst
	 * @param basket_id Id des zu löschenden Baskets
	 */
	public void undoBasket(Integer basket_id)  throws ServiceException;
	
	/**
	 * Setzt einen customer fuer den Basket
	 * @param basket 
	 * @param customer_id 
	 */
	public void setCustomerForBasket(Basket basket, Integer customer_id) throws ServiceException;
	
	/**
	 * 
	 * @param basket_id
	 * @param customers
	 * @return List<BasketDto>, wobei entweder die ID uebereinstimmt und/oder die ID des Kunden in der customer-Liste enthalten ist.
	 */
	public List<Basket> findBasket(Integer basket_id, List<Integer> customers) throws ServiceException;
}