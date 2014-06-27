package at.ac.tuwien.inso.tl.dao;

import java.util.Date;
import java.util.List;

import at.ac.tuwien.inso.tl.model.Show;

public interface ShowDaoCustom {
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
	 * Liefert den Minimal- und Maximalwert für die Eigenschaft PriceInCent aller Aufführungen. 
	 * @return Ein int[] mit dem Minimalwert an Index 0 und dem Maximalwert an Index 1.
	 */
	public int[] getMinMaxPriceInCent();
}
