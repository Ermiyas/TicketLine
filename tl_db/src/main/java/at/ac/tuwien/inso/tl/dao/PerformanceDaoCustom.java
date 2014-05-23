package at.ac.tuwien.inso.tl.dao;

import java.util.List;
import java.util.Map;

import at.ac.tuwien.inso.tl.model.Performance;

public interface PerformanceDaoCustom {
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
}
