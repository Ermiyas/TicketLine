package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public interface TicketService {

	/**
	 * Erstellt ein neuen Ticket.
	 * 
	 * Es darf nur showID oder seatID gesetzt sein. Das andere Argument muss null sein.
	 * @param showID Falls das Ticket ein Stehplatz ist, die Show-ID der Show, für die das Ticket ist. Ansonsten null.
	 * @param seatID Falls das Ticket ein Sitzplatz ist, der Sitz, für den das Ticket ist. Ansonsten null.
	 * @param entryID Der zu diesem Ticket zugehörige Warenkorb-Eintrag
	 * @return Das neu erstellte Ticket
	 */
	public TicketDto createTicket(Integer showID, Integer seatID, Integer entryID);
	
	/**
	 * Liefert das zu einem Entry zugehörige Ticket.
	 * @param entryID Der Warenkorb-Eintrag, dessen Ticket retourniert werden soll.
	 * @return Das dazugehörige Ticket. Einen bool-Wert, welcher true ist, wenn es sich um einen Sitzplatz handelt und false bei einem Stehplatz.
	 */
	public KeyValuePairDto<TicketDto, Boolean> getTicketForEntry(Integer entryID);
	
	/**
	 * Löscht das Ticket und den entsprechenden Warenkorb-Eintrag.
	 * 
	 * Der Warenkorb-Eintrag darf nicht Teil einer Rechnung sein.
	 * @param ticketID Die ID des Tickets, welches gelöscht werden soll.
	 */
	public void undoTicket(Integer ticketID);
}
