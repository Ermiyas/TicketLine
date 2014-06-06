package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

public interface BasketService {
	
	// TODO create(BasketDto basket), find(BasketDto basket), update(BasketDto basket), deleteById(Integer id), ...
	
	/**
	 * Gibt das Objekt vom Typ Warenkorb mit der angegebenen ID zurück.
	 * @param Die ID des gesuchten Objekts vom Typ Warenkorb.
	 * @return Objekts vom Typ Warenkorb.
	 * @throws ServiceException Wenn zu der ID kein Warenkorb gefunden werden kann
	 */
	public BasketDto getById(Integer id) throws ServiceException;
	
	/**
	 * Gibt eine Liste aller Warenkörbe zurück.
	 * @return java.util.List Eine Liste aller Warenkörbe.
	 * @throws ServiceException Wenn es bei der Suche zu einem Fehler kommt
	 */
	public List<BasketDto> getAll() throws ServiceException;
	
}