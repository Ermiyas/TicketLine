package at.ac.tuwien.inso.tl.server.service;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;
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
	public void setCustomerForBasket(Basket basket,Integer customer_id) throws ServiceException;
	
	/**
	 * Liefert eine List<KeyValuePairDto<BasketDto, CustomerDto>> wobei der Basket zu dem jeweiligen Customer gehört.
	 * Es werden jene KeyValuePairs hinzugefügt die den Suchkriterien entsprechen (also entweder stimmt die Basket_id
	 * überein oder der Basket wurde anhand eines Kunden der den in CustomerDto spezifizierten wurde hinzugefügt)
	 * @param basket_id die id des Baskets oder null
	 * @param customers Ein CustomerDto, dass die Suchkriterien fuer den Kunden bestimmt(kann unvollstaendig/null sein)
	 * @return  List<KeyValuePair<BasketDto basket, CustomerDto basketCustomer>> wobei basketCustomer auch null sein kann
	 *  (bei anonymen Kunden) bzw leere Liste, falls gar nichts gefunden wurde.
	 */
	public  List<Map.Entry<Basket, Customer>> findBasket(Integer basket_id, Customer customers) throws ServiceException;
	
}
