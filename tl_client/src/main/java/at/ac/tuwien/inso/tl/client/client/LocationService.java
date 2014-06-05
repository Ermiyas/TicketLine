package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.LocationDto;

public interface LocationService {
	
	/**
	 * Speichert ein neues Objekt vom Typ Ort, und gibt dieses zurück.
	 * 
	 * @param location Das zu speichernde Objekt vom Typ Ort.
	 * @return Die zugewiesene ID des gespeicherten Objekts vom Typ Ort.
	 * @throws ServiceException
	 */
	public Integer createLocation(LocationDto location) throws ServiceException;
	
	/**
	 * Löscht das Objekt vom Typ Ort mit der angegebenen ID.
	 * 
	 * @param Die ID des zu löschenden Objekts vom Typ Ort.
	 * @throws ServiceException
	 */
	public void deleteLocation(Integer id) throws ServiceException;		
	
	/**
	 * Liefert eine Liste von Orten, die den angegebenen Filterkriterien entspricht.
	 * @param city Der Textfilter für die Eigenschaft Stadt oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param country Der Textfilter für die Eigenschaft Land oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param description Der Textfilter für die Eigenschaft Beschreibung oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param postalCode Der Textfilter für die Eigenschaft Postleitzahl oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @param String Der Textfilter für die Eigenschaft Straße oder NULL, wenn dieser Parameter ignoriert werden soll (case insensitive).
	 * @return Eine Liste von Orten.
	 * @throws ServiceException
	 */
	public List<LocationDto> findLocations(String city, String country, String description, String postalCode, String street) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Orte zurück.
	 * @return java.util.List Eine Liste aller Orte.
	 * @throws ServiceException
	 */
	public List<LocationDto> getAllLocations() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Ort mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Ort.
	 * @return Objekts vom Typ Ort.
	 * @throws ServiceException
	 */
	public LocationDto getLocation(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Ort, und gibt dieses zurück.
	 * 
	 * @param location Das zu aktualisierende Objekt vom Typ Ort. 
	 * @throws ServiceException
	 */
	public void updateLocation(LocationDto location) throws ServiceException;	
}
