package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.ArtistDto;


public interface ArtistService {

	/**
	 * Speichert ein neues Objekt vom Typ Künstler, und gibt dieses zurück.
	 * 
	 * @param artist Das zu speichernde Objekt vom Typ Künstler.
	 * @return Die zugewiesene ID des gespeicherten Objekts vom Typ Künstler.
	 * @throws ServiceException
	 */
	public Integer createArtist(ArtistDto artist) throws ServiceException;
	
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
	public List<ArtistDto> findArtists(String firstName, String lastName) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Künstler zurück.
	 * @return java.util.List Eine Liste aller Künstler.
	 * @throws ServiceException
	 */
	public List<ArtistDto> getAllArtists() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Künstler mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Künstler.
	 * @return Objekts vom Typ Künstler.
	 * @throws ServiceException
	 */
	public ArtistDto getArtist(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Künstler, und gibt dieses zurück.
	 * 
	 * @param artist Das zu aktualisierende Objekt vom Typ Künstler. 
	 * @throws ServiceException
	 */
	public void updateArtist(ArtistDto artist) throws ServiceException;		
	
}
