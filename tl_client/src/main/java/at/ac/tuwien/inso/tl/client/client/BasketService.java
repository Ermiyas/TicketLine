package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.dto.BasketDto;

public interface BasketService {
	/**
	 * Erstellt einen neuen, leeren Warenkorb
	 * @return Einen neuen, leeren Warenkorb
	 */
	public BasketDto createBasket();
	
	/**
	 * Liefert den Warenkorb mit der übergebenen ID
	 * @param basketID Die ID des Warenkorbs, der retourniert werden soll.
	 * @return Den Warenkorb
	 */
	public BasketDto getBasket(Integer basketID);
	
	/**
	 * Löscht alle Einträge des Warenkorbs, die Tickets und den Basket selbst.
	 * 
	 * Für keinen Eintrag des Warenkorbs darf eine Rechnung ausgestellt worden sein.
	 * @param basketID Die ID des Warenkorbs, der gelöscht werden soll.
	 */
	public void undoBasket(Integer basketID);
	
	/**
	 * Weist den Warenkorb einem Kunden zu.
	 * @param basket Der Warenkorb, der einem Kunden zugewiesen werden soll.
	 * @param customerID Die ID des Kunden, dem der Warenkorb zugewiesen werden soll.
	 */
	public void setCustomerForBasket(BasketDto basket, Integer customerID);
}
