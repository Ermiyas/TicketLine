package at.ac.tuwien.inso.tl.server.service;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;


public interface EntryService {

	// TODO ev. getById(Integer id), create(Entry entry), find(Entry entry), update(Entry entry), deleteById(Integer id), getAll(), ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Finds the given basket object and returns the found entities
	 * 
	 * @param entry object with its properties as search parameters
	 * @return the found entities
	 * @throws ServiceException
	 */
	public List<Entry> getListByBasketId(Integer id) throws ServiceException;
	
	/**
	 * Erstellt ein Entry zu dem uebergebenen Entry und dessen Basket_id
	 * @param entryDto
	 * @param basket_id
	 * @return Das erzeugte Entry
	 * @throws ServiceException
	 */
	public Entry createEntry(Entry entry, Integer basket_id) throws ServiceException;
	
	/**
	 * Erstellt ein Entry zu dem uebergebenen Artikel, dem Entry und dessen Basket_id.
	 * @param entryDto Der Container für die Entry-Daten.
	 * @param articleID Die ID des zu setzende Artikels.
	 * @param basket_id Die ID des Baskets zu dem der Entry gehört.
	 * @return Der erzeugte Entry.
	 * @throws ServiceException
	 */
	public Entry createEntryForArticle(Entry entry, Integer articleID, Integer basket_id) throws ServiceException;	
	
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
	
	/**
	 * Findet den Entry mit uebereinstimmender seat_id und ueberprueft, ob das Entry bereits verkauft worden ist.
	 * @param seat_id Die Id eines Sitzes
	 * @return true wenn das Entry bereits verkauft wurde / false wenn nicht noch nicht verkauft wurde
	 */
	public Entry findEntryBySeat(int seat_id) throws ServiceException;
}
