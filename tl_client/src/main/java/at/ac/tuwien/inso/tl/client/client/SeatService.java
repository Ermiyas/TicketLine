package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public interface SeatService {

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Liefert jenen Sitz, der dem angegebenen Ticket zugeordnet ist, falls vorhanden - andernfalls null.
	 * 
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
	 * @param basketID Die ID des aktuellen Baskets. Wenn NULL wird sie ignoriert, wenn nicht, wird im Rückgabewert der Value auf NULL gesetzt, wenn der Sitzplatz im angegebenen basket reserviert ist.
	 * @return Eine Liste von KeyValuePairDto mit Key = Sitzplatz und Value = true wenn der Sitzplatz frei ist, ansonsten false.
	 * @throws ServiceException
	 */
	public List<KeyValuePairDto<SeatDto, Boolean>> findSeats(Integer rowID, Integer basketID) throws ServiceException;
	
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

