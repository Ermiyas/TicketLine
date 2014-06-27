package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Artist;

public interface ArtistDaoCustom {
	/**
	 * Liefert eine Liste von Künstlern, die den angegebenen Filterkriterien entspricht.
	 * @param firstName Der Textfilter für die Eigenschaft Vorname oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param lastName Der Textfilter für die Eigenschaft Nachname oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @return Eine Liste von Künstlern.
	 */
	public List<Artist> findArtists(String firstName, String lastName);
}
