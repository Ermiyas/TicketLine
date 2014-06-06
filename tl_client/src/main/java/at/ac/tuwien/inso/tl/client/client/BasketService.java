package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

public interface BasketService {
	
	/**
	 * Erstellt einen Basket fuer den Uebergebenen customer
	 * @param customer_id  CustomerID für die der Basket erstellt werden soll
	 * @return Ein Dto des erzeugten Basket
	 */
	public BasketDto createBasket(Integer customer_id)  throws ServiceException;
	
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
