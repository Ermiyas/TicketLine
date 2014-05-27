package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface RowService {

	/**
	 * Speichert ein neues Objekt vom Typ Sitzplatzreihe, und gibt dieses zurück.
	 * 
	 * @param row Das zu speichernde Objekt vom Typ Sitzplatzreihe.
	 * @return Das gespeicherte Objekt vom Typ Sitzplatzreihe.
	 * @throws ServiceException
	 */
	public Row createRow(Row row) throws ServiceException;
	
	/**
	 * Löscht das Objekt vom Typ Sitzplatzreihe mit der angegebenen ID.
	 * 
	 * @param Die ID des zu löschenden Objekts vom Typ Sitzplatzreihe.
	 * @throws ServiceException
	 */
	public void deleteRow(Integer id) throws ServiceException;		
	
	/**
	 * Liefert eine Liste aller Sitzplatzreihen, die den angegebenen Filterkriterien entspricht.
	 * @param showID Die ID einer Aufführung oder NULL, wenn dieser Parameter ignoriert werden soll.
	 * @return Eine Liste von Sitzplatzreihen.
	 * @throws ServiceException
	 */
	public List<Row> findRows(Integer showID) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Sitzplatzreihen zurück.
	 * @return java.util.List Eine Liste aller Sitzplatzreihen.
	 * @throws ServiceException
	 */
	public List<Row> getAllRows() throws ServiceException;
	
	/**
	 * Gibt das Objekt vom Typ Sitzplatzreihe mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Sitzplatzreihe.
	 * @return Objekts vom Typ Sitzplatzreihe.
	 * @throws ServiceException
	 */
	public Row getRow(Integer id) throws ServiceException;	
	
	/**
	 * Aktualisiert ein Objekt vom Typ Sitzplatzreihe, und gibt dieses zurück.
	 * 
	 * @param row Das zu aktualisierende Objekt vom Typ Sitzplatzreihe.
	 * @return Das aktualisierte Objekt vom Typ Sitzplatzreihe.
	 * @throws ServiceException
	 */
	public Row updateRow(Row row) throws ServiceException;	
}
