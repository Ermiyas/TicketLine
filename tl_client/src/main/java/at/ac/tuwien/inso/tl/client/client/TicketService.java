package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public interface TicketService {

	/**
	 * Erstellt ein neues Ticket wobei entweder show_id oder seat_id null sein muss
	 * @param show_id Die id der Show oder null wenn es Sitzplaetze gibt
	 * @param seat_id Die id des Sitzes oder null wenn es nur Stehplaetze gibt
	 * @param entry_id Die id eines Entry Eintrags kann null sein
	 * @return Das erzeugte Ticket
	 */
	public TicketDto createTicket(Integer show_id,Integer seat_id,Integer entry_id) throws ServiceException;
	
	/**
	 * Liefert ein KeyValuePairDto<TicketDto, Boolean> das zu diesem Entry gehoert 
	 * wobei der Boolean = True ist wenn es ein Sitzplatz ist und false wenn es ein Stehplatz ist)
	 * @param entry_id Die id eines Entrys 
	 * @return Ein KeyValuePairDto das zu diesem Entry gehoert 
	 */
	public KeyValuePairDto<TicketDto, Boolean> getTicketForEntry(Integer entry_id) throws ServiceException;
	
	/**
	 * Loescht das Ticket, dass zu der uebergebenen ticket_id passt und dessen Entry
	 * @param id Die ID des Tickets, dass man loeschen moechte.
	 */
	public void undoTicket(Integer id) throws ServiceException;
}
