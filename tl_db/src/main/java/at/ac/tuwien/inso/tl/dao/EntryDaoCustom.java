package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Entry;

public interface EntryDaoCustom {
	
	/**
	 * Liefert eine Liste von Entries, die dem Basket zugeordnet sind
	 * @param id Die ID des Baskets
	 * @return Eine Liste von Entries.
	 */
	public List<Entry> findEntriesByBasketId(Integer id);
}
