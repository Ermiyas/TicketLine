package at.ac.tuwien.inso.tl.dao;

import java.util.Map;

import at.ac.tuwien.inso.tl.model.Ticket;


public interface TicketDaoCustom {
	
	/**
	 * Liefert ein KeyValuePairDto<TicketDto, Boolean> das zu diesem Entry gehoert 
	 * wobei der Boolean = True ist wenn es ein Sitzplatz ist und false wenn es ein Stehplatz ist)
	 * @param entry_id Die id eines Entrys 
	 * @return Ein Key-Value Pair  
	 */
	public Map.Entry<Ticket, Boolean> getTicketForEntry(Integer entry_id);
	
}
