package at.ac.tuwien.inso.tl.server.service;

import java.util.Date;
import java.util.List;

import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface ShowService {

	/**
	 * Speichert ein neues Objekt vom Typ Aufführung, und gibt dieses zurück.
	 * 
	 * @param show Das zu speichernde Objekt vom Typ Aufführung.
	 * @return Das gespeicherte Objekt vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public Show createShow(Show show) throws ServiceException;
	
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
	 */
	public List<Show> findShows(Date dateFrom, Date dateTo, Date timeFrom, Date timeTo, Integer priceInCentFrom, Integer priceInCentTo, String room, Integer locationID, Integer performanceID);
	
	/**
	 * Gibt eine Liste aller Aufführungen zurück.
	 * @return java.util.List Eine Liste aller Aufführungen.
	 * @throws ServiceException
	 */
	public List<Show> getAllShows() throws ServiceException;
	
	/**
	 * Liefert den Minimal- und Maximalwert für die Eigenschaft PriceInCent aller Aufführungen. 
	 * @return Ein int[] mit dem Minimalwert an Index 0 und dem Maximalwert an Index 1.
	 */
	public int[] getMinMaxPriceInCent();
	
	/**
	 * Gibt das Objekt vom Typ Aufführung mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Aufführung.
	 * @return Objekts vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public Show getShow(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Aufführung, und gibt dieses zurück.
	 * 
	 * @param show Das zu aktualisierende Objekt vom Typ Aufführung.
	 * @return Das aktualisierte Objekt vom Typ Aufführung.
	 * @throws ServiceException
	 */
	public Show updateCustomer(Show show) throws ServiceException;		
}
