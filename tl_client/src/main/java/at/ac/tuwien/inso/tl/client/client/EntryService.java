package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

public interface EntryService {
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
