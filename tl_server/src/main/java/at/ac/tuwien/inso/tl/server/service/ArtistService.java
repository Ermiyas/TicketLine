package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Artist;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface ArtistService {
	
	/**
	 * Speichert ein neues Objekt vom Typ Künstler, und gibt dieses zurück.
	 * 
	 * @param artist Das zu speichernde Objekt vom Typ Künstler.
	 * @return Das gespeicherte Objekt vom Typ Künstler.
	 * @throws ServiceException
	 */
	public Artist createArtist(Artist artist) throws ServiceException;
	
	/**
	 * Löscht das Objekt vom Typ Künstler mit der angegebenen ID.
	 * 
	 * @param Die ID des zu löschenden Objekts vom Typ Künstler.
	 * @throws ServiceException
	 */
	public void deleteArtist(Integer id) throws ServiceException;	
	
	/**
	 * Liefert eine Liste von Künstlern, die den angegebenen Filterkriterien entspricht.
	 * @param firstName Der Textfilter für die Eigenschaft Vorname oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param lastName Der Textfilter für die Eigenschaft Nachname oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @return Eine Liste von Künstlern.
	 * @throws ServiceException
	 */
	public List<Artist> findArtists(String firstName, String lastName) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Künstler zurück.
	 * @return java.util.List Eine Liste aller Künstler.
	 * @throws ServiceException
	 */
	public List<Artist> getAllArtists() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Künstler mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Künstler.
	 * @return Objekts vom Typ Künstler.
	 * @throws ServiceException
	 */
	public Artist getArtist(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Künstler, und gibt dieses zurück.
	 * 
	 * @param artist Das zu aktualisierende Objekt vom Typ Künstler.
	 * @return Das aktualisierte Objekt vom Typ Künstler.
	 * @throws ServiceException
	 */
	public Artist updateArtist(Artist artist) throws ServiceException;		
}
