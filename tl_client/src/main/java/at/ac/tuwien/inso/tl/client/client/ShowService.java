package at.ac.tuwien.inso.tl.client.client;

import java.util.Date;
import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public interface ShowService {	
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Liefert die zum Ticket gehoerende Auffuehrung, falls vorhanden - andernfalls null.
	 * 
	 * @param ticket Das Ticket, zu dem die Auffuehrung gesucht wird
	 * @return Gesuchte Auffuehrung oder null.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt.
	 */
	public ShowDto getShow(TicketDto ticket) throws ServiceException;
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Liefert jene Auffuehrung, in der die angegebene Reihe ist, falls vorhanden - andernfalls null.
	 * 
	 * @param row Die Reihe, zu dem die Auffuehrung gesucht wird
	 * @return Gesuchte Auffuehrung oder null.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt.
	 */
	public ShowDto getShow(RowDto row) throws ServiceException;
	
	/**
	 * Speichert ein neues Objekt vom Typ Aufführung, und gibt dieses zurück.
	 * 
	 * @param show Das zu speichernde Objekt vom Typ Aufführung.
	 * @return  Die zugewiesene ID des gespeicherten Objekts vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public Integer createShow(ShowDto show) throws ServiceException;
	
	/**
	 * Löscht das Objekt vom Typ Aufführung mit der angegebenen ID.
	 * 
	 * @param Die ID des zu löschenden Objekts vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public void deleteShow(Integer id) throws ServiceException;		
	
	/**
	 * Liefert eine Liste von ContainerDto mit allen Aufführungen und dessen zugehörigen Ort und Veranstaltung, 
	 * die den angegebenen Filterkriterien entspricht.
	 * @param dateFrom Die Untergrenze für das Aufführungsdatum oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param dateTo Die Obergrenze für das Aufführungsdatum oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param timeFrom Die Untergrenze für den Aufführungszeitpunkt oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param timeTo Die Obergrenze für den Aufführungszeitpunkt oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param priceInCentFrom Die Untergrenze für den Preis oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param priceInCentTo Die Obergrenze für den Preis oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param room Der Textfilter für die Eigenschaft Raum oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param locationID Die ID eines Ortes oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param performanceID Die ID einer Veranstaltung NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von ContainerDto.
	 * @throws ServiceException
	 */
	public List<ContainerDto> findShows(Date dateFrom, Date dateTo, Date timeFrom, Date timeTo, Integer priceInCentFrom, Integer priceInCentTo, String room, Integer locationID, Integer performanceID) throws ServiceException;
	
	/**
	 * Gibt eine Liste von ContainerDto mit allen Aufführungen und dessen zugehörigen Ort und Veranstaltung zurück.
	 * @return java.util.List Eine Liste von ContainerDto, welche Informationen zu Performance, Show und Location beinhalten.
	 * @throws ServiceException
	 */
	public List<ContainerDto> getAllShows() throws ServiceException;
	
	/**
	 * Liefert den Minimal- und Maximalwert für die Eigenschaft PriceInCent aller Aufführungen. 
	 * @return Ein int[] mit dem Minimalwert an Index 0 und dem Maximalwert an Index 1.
	 * @throws ServiceException
	 */
	public int[] getMinMaxPriceInCent() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Aufführung mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Aufführung.
	 * @return Objekts vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public ShowDto getShow(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Aufführung, und gibt dieses zurück.
	 * 
	 * @param show Das zu aktualisierende Objekt vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public void updateShow(ShowDto show) throws ServiceException;	
	
	/**
	 * Sucht nach allen Auffuehrungen, die zu der uebergebenen Veranstaltung gehoeren.
	 * @param performace_id Die id der Veranstaltung zu der man die Auffuehrungen erhalten moechte
	 * @return Liefert eine Liste von ContainerDto, die die gefundenen Auffuehrungen sowie den zugehörigen Veranstaltungen enthalten
	 */
	public List<ContainerDto> getShowsForPerformance(Integer performace_id) throws ServiceException ;
	
	/**
	 * Sucht nach allen Auffuehrungen, die an dem uebergebenen Ort stattfinden.
	 * @param location_id Die id des Ortes zu der man die Auffuehrungen erhalten moechte
	 * @return Liefert eine Liste von ContainerDto, die die gefundenen Auffuehrungen sowie deren zugehörigen Orte enthalten
	 */
	public  List<ContainerDto> getShowsForLocation(Integer location_id) throws ServiceException ;
}
