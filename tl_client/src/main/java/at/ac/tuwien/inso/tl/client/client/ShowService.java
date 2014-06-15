package at.ac.tuwien.inso.tl.client.client;

import java.util.Date;
import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.ShowDto;

public interface ShowService {	
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
	 * Liefert eine Liste von Aufführungen, die den angegebenen Filterkriterien entspricht.
	 * @param dateFrom Die Untergrenze für das Aufführungsdatum oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param dateTo Die Obergrenze für das Aufführungsdatum oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param timeFrom Die Untergrenze für den Aufführungszeitpunkt oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param timeTo Die Obergrenze für den Aufführungszeitpunkt oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param priceInCentFrom Die Untergrenze für den Preis oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param priceInCentTo Die Obergrenze für den Preis oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param room Der Textfilter für die Eigenschaft Raum oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param locationID Die ID eines Ortes oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param performanceID Die ID einer Veranstaltung NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von Aufführungen.
	 * @throws ServiceException
	 */
	public List<ShowDto> findShows(Date dateFrom, Date dateTo, Date timeFrom, Date timeTo, Integer priceInCentFrom, Integer priceInCentTo, String room, Integer locationID, Integer performanceID) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Aufführungen zurück.
	 * @return java.util.List Eine Liste aller Aufführungen.
	 * @throws ServiceException
	 */
	public List<ShowDto> getAllShows() throws ServiceException;
	
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
	 * @return Liefert eine Liste der gefunden Auffuehrungen, die die ID der Veranstaltung enthalten
	 */
	public List<ShowDto> getShowsForPerformance(Integer performace_id) throws ServiceException ;
	
	/**
	 * Sucht nach allen Auffuehrungen, die an dem uebergebenen Ort stattfinden.
	 * @param location_id Die id des Ortes zu der man die Auffuehrungen erhalten moechte
	 * @return Liefert eine Liste der gefunden Auffuehrungen, die die ID des Ortes enthalten
	 */
	public  List<ShowDto> getShowsForLocation(Integer location_id) throws ServiceException ;
}
