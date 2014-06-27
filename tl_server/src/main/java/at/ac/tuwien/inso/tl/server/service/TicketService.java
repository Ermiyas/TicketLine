package at.ac.tuwien.inso.tl.server.service;

import java.util.Map;

import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface TicketService {
	
	// TODO ev. create(Ticket ticket), find(Ticket ticket), update(Ticket ticket), deleteById(Integer id), getAll, ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Returns the ticket object identified by the given id
	 * 
	 * @param id of the ticket object
	 * @return the ticket object
	 * @throws ServiceException
	 */
	public Ticket getById(Integer id) throws ServiceException;	
	
	/**
	 * Erstellt ein neues Ticket wobei entweder show_id oder seat_id null sein muss
	 * @param show_id Die id der Show oder null wenn es Sitzplaetze gibt
	 * @param seat_id Die id des Sitzes oder null wenn es nur Stehplaetze gibt
	 * @param entry_id Die id eines Entry Eintrags kann null sein
	 * @return Das erzeugte Ticket
	 */
	public Ticket createTicket(Integer show_id,Integer seat_id,Integer entry_id) throws ServiceException;
	
	/**
	 * Liefert ein KeyValuePairDto<TicketDto, Boolean> das zu diesem Entry gehoert 
	 * wobei der Boolean = True ist wenn es ein Sitzplatz ist und false wenn es ein Stehplatz ist)
	 * @param entry_id Die id eines Entrys 
	 * @return Ein KeyValuePairDto das zu diesem Entry gehoert 
	 */
	public Map.Entry<Ticket, Boolean> getTicketForEntry(Integer entry_id) throws ServiceException;
	
	/**
	 * Loescht das Ticket, dass zu der uebergebenen ticket_id passt und dessen Entry
	 * @param id Die ID des Tickets, dass man loeschen moechte.
	 */
	public void undoTicket(Integer id) throws ServiceException;
	
	
	/**
	 * Die Methode gibt zu einer Ticket_id ein Map.Entry<Performance, Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>> 
	 * wobei alles "ausgefüllt" ist wenn es sich um einen Sitzplatz handelt und das innerste Map.Entry<Row, Seat> null
	 * @param ticket_id 
	 * @return  Map.Entry<Performance, Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>>
	 * wobei alles ausgefüllt ist wenn es sich um einen Sitzplatz handelt und das innerste Map.Entry<Row, Seat> null
	 *  ist wenn es sich um einen Stehplatz handelt
	 * @throws ServiceException wenn ticket_id null
	 */
	public Map.Entry<Performance, Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>> 
		getPerformanceShowLocationRowSeatByTicket(Integer ticket_id) throws ServiceException;
	
	/**
	 * Liefert das Ticket zu einem Seat
	 * @param seatID Die ID des Seats.
	 * @return Das Ticket.
	 * @throws ServiceException
	 */
	public Ticket getTicketBySeat(int seatID) throws ServiceException;
}
