package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public interface SeatService {

	/**
	 * Liefert jenen Sitz, der dem angegebenen Ticket zugeordnet ist, falls vorhanden - andernfalls null.
	 * @param ticket Das Ticket, zu dem der Sitz gesucht wird
	 * @return Gesuchten Sitz oder null.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt.
	 */
	public SeatDto getSeat(TicketDto ticket) throws ServiceException;
	
	/**
	 * Speichert ein neues Objekt vom Typ Sitzplatz, und gibt dieses zurück.
	 * 
	 * @param seat Das zu speichernde Objekt vom Typ Sitzplatz.
	 * @return Die zugewiesene ID des gespeicherten Objekts vom Typ Sitzplatz.
	 * @throws ServiceException
	 */
	public Integer createSeat(SeatDto seat) throws ServiceException;
	
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
	 * @return Eine Liste von Sitzplätze.
	 * @throws ServiceException
	 */
	public List<SeatDto> findSeats(Integer rowID) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Sitzplätze zurück.
	 * @return java.util.List Eine Liste aller Sitzplätze.
	 * @throws ServiceException
	 */
	public List<SeatDto> getAllSeats() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Sitzplatz mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Sitzplatz.
	 * @return Objekts vom Typ Sitzplatz.
	 * @throws ServiceException
	 */
	public SeatDto getSeat(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Sitzplatz, und gibt dieses zurück.
	 * 
	 * @param seat Das zu aktualisierende Objekt vom Typ Sitzplatz.	 
	 * @throws ServiceException
	 */
	public void updateSeat(SeatDto seat) throws ServiceException;	
}
