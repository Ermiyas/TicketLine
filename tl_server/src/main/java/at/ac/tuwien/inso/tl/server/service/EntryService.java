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

	/**
	 * Loescht das Entry mit der uebergebenen ID sowie dessen Ticket oder Artikel, wenn es keine Rechnung 
	 * zu dem Entry gibt. Wenn es eine Rechnung gibt so wird das Ticket oder der Artikel geloescht und die
	 * jeweilige FK Beziehung im Entry auf null gesetzt. 
	 * @param id Die id des zu loeschenden Entries
	 */
	public void undoEntry(Integer id) throws ServiceException;
	
	/**
	 * Ueberprueft ob das Entry mit uebergeben id Stornierbar ist. Wenn das Entry einen Artikel enthaelt so 
	 * ist es immer stornierbar wobei wenn es ein Ticket enhaelt nur dann stornierbar ist wenn das Datum der
	 * Auffuehrung in der Zukunft liegt.
	 * @param id Die Id des Entries zu dem man wissen will ob es stornierbar ist
	 * @return true wenn das Entry stornierbar ist/ false wenn es nicht stornierbar ist
	 */
	public Boolean isReversible(Integer id) throws ServiceException;
}
