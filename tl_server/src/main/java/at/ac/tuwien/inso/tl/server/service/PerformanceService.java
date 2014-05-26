package at.ac.tuwien.inso.tl.server.service;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface PerformanceService {
	
	/**
	 * Speichert ein neues Objekt vom Typ Veranstaltung, und gibt dieses zurück.
	 * 
	 * @param performance Das zu speichernde Objekt vom Typ Veranstaltung.
	 * @return Das gespeicherte Objekt vom Typ Veranstaltung.
	 * @throws ServiceException
	 */
	public Performance createPerformance(Performance performance) throws ServiceException;
	
	/**
	 * Löscht das Objekt vom Typ Veranstaltung mit der angegebenen ID.
	 * 
	 * @param Die ID des zu löschenden Objekts vom Typ Veranstaltung.
	 * @throws ServiceException
	 */
	public void deletePerformance(Integer id) throws ServiceException;	
	
	/**
	 * Liefert eine Liste von Veranstaltungen und die Anzahl an verkauften Tickets, die den angegebenen Filterkriterien entspricht, sortiert nach Ticket-Verkäufen.
	 * @param content Der Textfilter für die Eigenschaft Inhalt oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param description Der Textfilter für die Eigenschaft Beschreibung oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param durationInMinutesFrom Die Untergrenze für die Dauer in Minuten oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param durationInMinutesTo Die Obergrenze für die Dauer in Minuten oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @param performanceType Der Textfilter für die Eigenschaft Typ oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param artistID Der Filter für den teilnehmenden Künstler oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von Veranstaltungen und die Anzahl an verkauften Tickets.
	 */
	public List<Map.Entry<Performance, Integer>> findPerformancesSortedBySales(String content, String description,
			Integer durationInMinutesFrom, Integer durationInMinutesTo, String performanceType, Integer artistID);
	
	/**
	 * Liefert den Minimal- und Maximalwert für die Eigenschaft DauerInMinuten aller Veranstaltungen. 
	 * @return Ein int[] mit dem Minimalwert an Index 0 und dem Maximalwert an Index 1.
	 */
	public int[] getMinAndMaxDuration();	
	
	/**
	 * Gibt eine Liste aller Veranstaltungen zurück.
	 * @return java.util.List Eine Liste aller Veranstaltungen.
	 * @throws ServiceException
	 */
	public List<Performance> getAllPerformances() throws ServiceException;
	
	/**
	 * Lädt eine Liste aller Kategorien.
	 * @return Eine Liste aller Kategorien.
	 */
	public List<String> getAllPerformanceTypes();
	
	/**
	 * Gibt das Objekt vom Typ Veranstaltung mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Veranstaltung.
	 * @return Objekts vom Typ Veranstaltung.
	 * @throws ServiceException
	 */
	public Performance getPerformance(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Veranstaltung, und gibt dieses zurück.
	 * 
	 * @param performance Das zu aktualisierende Objekt vom Typ Veranstaltung.
	 * @return Das aktualisierte Objekt vom Typ Veranstaltung.
	 * @throws ServiceException
	 */
	public Performance updateCustomer(Performance performance) throws ServiceException;		
}
