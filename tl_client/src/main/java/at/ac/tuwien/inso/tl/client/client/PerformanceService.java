package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;

public interface PerformanceService {

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**
	 * Liefert die zur Auffuehrung gehoerende Veranstaltung, falls vorhanden - andernfalls null.
	 * 
	 * @param show Die Auffuehrung, zu der die Veranstaltung gesucht wird
	 * @return Gesuchte Veranstaltung oder null.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt.
	 */
	public PerformanceDto getPerformance(ShowDto show) throws ServiceException;
	
	/**
	 * Speichert ein neues Objekt vom Typ Veranstaltung, und gibt dieses zurück.
	 * 
	 * @param performance Das zu speichernde Objekt vom Typ Veranstaltung.
	 * @return Die zugewiesene ID des gespeicherten Objekts vom Typ Veranstaltung.
	 * @throws ServiceException
	 */
	public Integer createPerformance(PerformanceDto performance) throws ServiceException;
	
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
	 * @throws ServiceException
	 */
	public List<KeyValuePairDto<PerformanceDto, Integer>> findPerformancesSortedBySales(String content, String description,
			Integer durationInMinutesFrom, Integer durationInMinutesTo, String performanceType, Integer artistID) throws ServiceException;
	
	/**
	 * Liefert den Minimal- und Maximalwert für die Eigenschaft DauerInMinuten aller Veranstaltungen. 
	 * @return Ein int[] mit dem Minimalwert an Index 0 und dem Maximalwert an Index 1.
	 * @throws ServiceException
	 */
	public int[] getMinAndMaxDuration() throws ServiceException;	
	
	/**
	 * Gibt eine Liste aller Veranstaltungen zurück.
	 * @return java.util.List Eine Liste aller Veranstaltungen.
	 * @throws ServiceException
	 */
	public List<PerformanceDto> getAllPerformances() throws ServiceException;
	
	/**
	 * Lädt eine Liste aller Kategorien.
	 * @return Eine Liste aller Kategorien.
	 * @throws ServiceException
	 */
	public List<String> getAllPerformanceTypes() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Veranstaltung mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Veranstaltung.
	 * @return Objekts vom Typ Veranstaltung.
	 * @throws ServiceException
	 */
	public PerformanceDto getPerformance(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Veranstaltung, und gibt dieses zurück.
	 * 
	 * @param performance Das zu aktualisierende Objekt vom Typ Veranstaltung.	 * 
	 * @throws ServiceException
	 */
	public void updatePerformance(PerformanceDto performance) throws ServiceException;		
	
	/**
	 * Liefert die Veranstaltung zu einer Aufführung.
	 * @param show_id Die ID der Aufführung.
	 * @return Die Veranstaltung.
	 * @throws ServiceException
	 */
	public PerformanceDto findPerformanceByShow(int show_id) throws ServiceException;	
}
