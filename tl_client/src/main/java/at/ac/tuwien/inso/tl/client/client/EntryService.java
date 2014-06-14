package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

public interface EntryService {
	
	// TODO create(EntryDto entry), find(EntryDto entry), getById(Integer id), update(EntryDto entry), deleteById(Integer id), getAll(), ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Liefert eine Liste von Einträgen, die im angegebenen Warenkorb sind.
	 * 
	 * @param basket Der Warenkorb, zu dem alle Einträge gesucht werden
	 * @return Eine Liste von Einträgen.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt.
	 */
	public List<EntryDto> getList(BasketDto basket) throws ServiceException;		// get all Entries of a basket
	
	/**
	 * 
	 * @param entryDto
	 * @param basket_id
	 * @return
	 * @throws ServiceException
	 */
	public EntryDto createEntry(EntryDto entryDto, Integer basket_id) throws ServiceException;
	
	/**
	 *  List<KeyValuePairDto<EntryDto, Boolean>> die zu diesem Basket gehoeren, wobei der Boolean-Parameter die Werte 
	 *  True für Ticket, False für Artikel und NULL für Storniert enthaelt.
	 * @param basket_id
	 * @return Die resultierende Liste aus KeyValuPairDto
	 */
	public List<KeyValuePairDto<EntryDto, Boolean>> getEntry(Integer basket_id) throws ServiceException;
	
	/**
	 * Liefet true wenn es zu dem Entry (das zu der uebergebenen ID passt) eine Rechnung gibt sonst false
	 * @param id Die ID des Entries zu dem man wissen will ob es eine Rechnung gibt
	 * @return true wenn es zu dem Entry (das zu der uebergebenen ID passt) eine Rechnung gibt sonst false
	 * @throws ServiceException
	 */
	public Boolean hasReceipt(Integer id) throws ServiceException;
	
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
