package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Seat;

public interface SeatDaoCustom {
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Liefert einen Sitz, der einem Ticket zugeordnet ist
	 * @param id Die ID des Tickets
	 * @return Einen Sitz oder null.
	 */
	public Seat findSeatByTicketId(Integer id);
	
	/**
	 * Liefert eine Liste aller Sitzplätze, die den angegebenen Filterkriterien entspricht.
	 * @param rowID Die ID einer Sitzplatzreihe oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von Sitzplätze.
	 */
	public List<Seat> findSeats(Integer rowID);
	
	/**
	 * Liefert alle Seats einer Show.
	 * @param showID Die ID der Show.
	 * @return Eine Liste von Seats.
	 */
	public List<Seat> getAllSeatsForShow(int showID);
}
