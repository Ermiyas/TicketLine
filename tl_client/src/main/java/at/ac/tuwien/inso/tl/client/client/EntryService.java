package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

public interface EntryService {
	/**
	 * Erstellt einen neuen Eintrag
	 * @param basketID
	 * @return
	 */
	public EntryDto createEntry(Integer basketID);
	
	/**
	 * Liefert eine Liste der Einträge, die zu einem gewissen Warenkorb gehören.
	 * @param basketID Der Warenkorb, dessen Einträge geliefert werden sollen.
	 * @return Eine Liste von Paaren. Der Bool-Wert liefert für ein Ticket true, für einen Artikel false und für einen stornierten Eintrag null.
	 */
	public List<KeyValuePairDto<EntryDto, Boolean>> getEntry(Integer basketID);
	
}
