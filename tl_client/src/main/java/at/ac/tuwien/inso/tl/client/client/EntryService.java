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
	 * Liefet true wenn es zu dem Entry (das zu der uebergebenen ID passt) eine Rechnung gibt sonst false
	 * @param id Die ID des Entries zu dem man wissen will ob es eine Rechnung gibt
	 * @return true wenn es zu dem Entry (das zu der uebergebenen ID passt) eine Rechnung gibt sonst false
	 * @throws ServiceException
	 */
	public Boolean hasReceipt(Integer id) throws ServiceException;
}
