package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

public interface EntryService {
	
	// TODO create(EntryDto entry), find(EntryDto entry), getById(Integer id), update(EntryDto entry), deleteById(Integer id), getAll(), ...

	/**
	 * Liefert eine Liste von Einträgen, die im angegebenen Warenkorb sind.
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
}