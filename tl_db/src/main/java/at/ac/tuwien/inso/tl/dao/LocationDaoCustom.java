package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Location;

public interface LocationDaoCustom {
	/**
	 * Liefert eine Liste von Orten, die den angegebenen Filterkriterien entspricht.
	 * @param city Der Textfilter für die Eigenschaft Stadt oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param country Der Textfilter für die Eigenschaft Land oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param description Der Textfilter für die Eigenschaft Beschreibung oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param postalCode Der Textfilter für die Eigenschaft Postleitzahl oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param String Der Textfilter für die Eigenschaft Straße oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @return Eine Liste von Orten.
	 */
	public List<Location> findLocations(String city, String country, String description, String postalCode, String street);
}
