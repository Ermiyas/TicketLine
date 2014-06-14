package at.ac.tuwien.inso.tl.server.service;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface SeatService {

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
	 * @return Eine Liste von Map.Entires mit Key = Sitzplatz und Value = true wenn der Sitzplatz frei ist, ansonsten false.
	 * @throws ServiceException
	 */
	public List<Map.Entry<Seat, Boolean>> findSeats(Integer rowID) throws ServiceException;
	
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
}
