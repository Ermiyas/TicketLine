package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

public interface BasketService {
	
	// TODO ev. create(BasketDto basket), find(BasketDto basket), update(BasketDto basket), deleteById(Integer id), ...
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Gibt das Objekt vom Typ Warenkorb mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Warenkorb.
	 * @return Objekts vom Typ Warenkorb.
	 * @throws ServiceException Wenn zu der ID kein Warenkorb gefunden werden kann
	 */
	public BasketDto getById(Integer id) throws ServiceException;
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Gibt eine Liste aller Warenkörbe zurück.
	 * 
	 * @return java.util.List Eine Liste aller Warenkörbe.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt
	 */
	public List<BasketDto> getAll() throws ServiceException;
	
	/**
	 * Erstellt einen Basket 
	 * @return Ein Dto des erzeugten Basket
	 */
	public BasketDto createBasket()  throws ServiceException;
	
	/**
	 * 
	 * @param basket_id des gewuenschten Baskets
	 * @return
	 */
	public BasketDto getBasket(Integer basket_id)  throws ServiceException;
	
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
	public void setCustomerForBasket(BasketDto basket,Integer customer_id) throws ServiceException;
	
	/**
	 * 
	 * @param basket_id
	 * @param customers
	 * @return List<BasketDto>, wobei entweder die ID uebereinstimmt und/oder die ID des Kunden in der customer-Liste enthalten ist.
	 */
	public List<BasketDto> findBasket(Integer basket_id, List<Integer> customers) throws ServiceException;
}