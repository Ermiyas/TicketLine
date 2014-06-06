package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Seat;

public interface SeatDaoCustom {
	
	/**
	 * Liefert eine Liste aller Sitzplätze, die den angegebenen Filterkriterien entspricht.
	 * @param rowID Die ID einer Sitzplatzreihe oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von Sitzplätze.
	 */
	public List<Seat> findSeats(Integer rowID);

	/**
	 * Liefert einen Sitz, der einem Ticket zugeordnet ist
	 * @param id Die ID des Tickets
	 * @return Einen Sitz oder null.
	 */
	public Seat findSeatByTicketId(Integer id);
}
