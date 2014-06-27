package at.ac.tuwien.inso.tl.server.service;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface SeatService {

	// TODO ev. find(Seat seat), ... 

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Findet den Sitz zum Ticket, falls vorhanden
	 * 
	 * @param Integer Die Ticket-ID, zu der der Sitz gesucht wird
	 * @return Den gesuchten Sitz, oder null
	 * @throws ServiceException Bei Fehler waehrend suche.
	 */
	public Seat findSeatByTicketId(Integer id) throws ServiceException;
	
	/**
	 * Speichert ein neues Objekt vom Typ Sitzplatz, und gibt dieses zurück.
	 * 
	 * @param seat Das zu speichernde Objekt vom Typ Sitzplatz.
	 * @return Das gespeicherte Objekt vom Typ Sitzplatz.
	 * @throws ServiceException
	 */
	public Seat createSeat(Seat seat) throws ServiceException;
	
	/**
	 * Löscht das Objekt vom Typ Sitzplatz mit der angegebenen ID.
	 * 
	 * @param Die ID des zu löschenden Objekts vom Typ Sitzplatz.
	 * @throws ServiceException
	 */
	public void deleteSeat(Integer id) throws ServiceException;	
	
	/**
	 * Liefert eine Liste aller Sitzplätze, die den angegebenen Filterkriterien entspricht.
	 * @param rowID Die ID einer Sitzplatzreihe oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param basketID Die ID des aktuellen Baskets. Wenn NULL wird sie ignoriert, wenn nicht, wird im Rückgabewert der Value auf NULL gesetzt, wenn der Sitzplatz im angegebenen basket reserviert ist.
	 * @return Eine Liste von Map.Entires mit Key = Sitzplatz und Value = true wenn der Sitzplatz frei ist, NULL wenn reserviert und im angegebenen Basket, sonst false.
	 * @throws ServiceException
	 */
	public List<Map.Entry<Seat, Boolean>> findSeats(Integer rowID, Integer basketID) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Sitzplätze zurück.
	 * @return java.util.List Eine Liste aller Sitzplätze.
	 * @throws ServiceException
	 */
	public List<Seat> getAllSeats() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Sitzplatz mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Sitzplatz.
	 * @return Objekts vom Typ Sitzplatz.
	 * @throws ServiceException
	 */
	public Seat getSeat(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Sitzplatz, und gibt dieses zurück.
	 * 
	 * @param seat Das zu aktualisierende Objekt vom Typ Sitzplatz.
	 * @return Das aktualisierte Objekt vom Typ Sitzplatz.
	 * @throws ServiceException
	 */
	public Seat updateSeat(Seat seat) throws ServiceException;	
	
	/**
	 * Liefert eine Liste alle Sitze zu einer Show.
	 * @param show_id Die ID der Show.
	 * @return Eine Liste von Show.
	 * @throws ServiceException
	 */
	public List<Seat> getAllSeatForShow(int show_id) throws ServiceException;	
}
