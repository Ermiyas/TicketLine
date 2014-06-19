package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

public interface BasketService {
	
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
	 * Liefert eine List<KeyValuePairDto<BasketDto, CustomerDto>> wobei der Basket zu dem jeweiligen Customer gehört.
	 * Es werden jene KeyValuePairs hinzugefügt die den Suchkriterien entsprechen (also entweder stimmt die Basket_id
	 * überein oder der Basket wurde anhand eines Kunden der den in CustomerDto spezifizierten wurde hinzugefügt)
	 * @param basket_id die id des Baskets oder null
	 * @param customers Ein CustomerDto, dass die Suchkriterien fuer den Kunden bestimmt(kann unvollstaendig/null sein)
	 * @return  List<KeyValuePair<BasketDto basket, CustomerDto basketCustomer>> wobei basketCustomer auch null sein kann
	 *  (bei anonymen Kunden) bzw leere Liste, falls gar nichts gefunden wurde.
	 */
	public  List<KeyValuePairDto<BasketDto, CustomerDto>> findBasket(Integer basket_id, CustomerDto customers) throws ServiceException;
	
	/**
	 * Liefert eine Liste an ContainerDto die jeweils die Entries mit zugehoerigem Ticket,Artikel,Performance,Show,Location
	 * ,Row und Seat beinhalten.
	 * @param id Die id zu dem Warenkorb zu dem man die ContainerDto-Liste haben moechte
	 * @return Eine Liste an ContainerDto, die die Informationen zum Entry, Ticket,Artikel,Performance,Show,Location
	 * ,Row und Seat beinhalten.
	 * @throws ServiceException
	 */
	public List<ContainerDto> getEntryTicketArticlePerformanceRowSeatContainers(Integer id) throws ServiceException;
}
