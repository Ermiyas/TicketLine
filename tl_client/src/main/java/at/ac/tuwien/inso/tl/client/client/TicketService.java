package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
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
	
	/**
	 * Die Methode gibt zu einer Ticket_id ein KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>> 
	 * wobei alles "ausgefüllt" ist wenn es sich um einen Sitzplatz handelt und das innerste KeyValuePairDto<Row, Seat> null
	 * @param ticket_id
	 * @return  KeyValuePairDto<Performance, KeyValuePairDto<Show, KeyValuePairDto<Location, KeyValuePairDto<Row, Seat>>>> 
	 * wobei alles ausgefüllt ist wenn es sich um einen Sitzplatz handelt und das innerste KeyValuePairDto<Row, Seat> null
	 *  ist wenn es sich um einen Stehplatz handelt
	 * @throws ServiceException wenn ticket_id null
	 */
	public KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>> 
		getPerformanceShowLocationRowSeatByTicket(Integer ticket_id) throws ServiceException;
	
	/**
	 * Liefert das Ticket zu einem Seat
	 * @param seatID Die ID des Seats.
	 * @return Das Ticket.
	 * @throws ServiceException
	 */
	public TicketDto getTicketBySeat(int seatID) throws ServiceException;
}
