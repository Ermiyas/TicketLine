package at.ac.tuwien.inso.tl.server.service;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;



public interface EntryService {
	/**
	 * Erstellt ein Entry zu dem uebergebenen Entry und dessen Basket_id
	 * @param entryDto
	 * @param basket_id
	 * @return Das erzeugte Entry
	 * @throws ServiceException
	 */
	public Entry createEntry(Entry entry, Integer basket_id) throws ServiceException;
	/**
	 *  Findet alle Map.Entry<Entry, Boolean die zu diesem Basket gehoeren, wobei der Boolean-Parameter die Werte 
	 *  True für Ticket, False für Artikel und NULL für Storniert enthaelt.
	 * @param basket_id
	 * @return Die resultierende Liste aus Map Eintraegen die jeweil ein Entry und einen Boolean enthaelt wobei der Boolean-Parameter die Werte 
	 *  True für Ticket, False für Artikel und NULL für Storniert enthaelt.
	 */
	public  List<Map.Entry<Entry, Boolean>> getEntry(Integer basket_id) throws ServiceException;
}
