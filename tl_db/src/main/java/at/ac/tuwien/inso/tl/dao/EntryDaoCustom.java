package at.ac.tuwien.inso.tl.dao;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Entry;

public interface EntryDaoCustom {
	/**
	 * 
	 * @param basket_id
	 * @return
	 */
	public Entry createEntry(Integer basket_id);
	/**
	 *  Liefert eine List<Map.Entry<Entry, Boolean> die zu diesem Basket gehoeren, wobei der Boolean-Parameter die Werte 
	 *  True für Ticket, False für Artikel und NULL für Storniert enthaelt.
	 * @param basket_id
	 * @return Liefert eine List<Map.Entry<Entry, Boolean> die zu diesem Basket gehoeren, wobei der Boolean-Parameter die Werte 
	 *  True für Ticket, False für Artikel und NULL für Storniert enthaelt.
	 */
	public List<Map.Entry<Entry, Boolean>> getEntry(Integer basket_id);
}
