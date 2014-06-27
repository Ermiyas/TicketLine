package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.ArticleDto;

public interface ArticleService {
	/**
	 * Liefert eine Liste aller Artikel
	 * @return Eine Liste aller Artikel.
	 * @throws ServiceException
	 */
	public List<ArticleDto> getAll() throws ServiceException;
	
	/**
	 * Liefert eine Liste aller Mechandising-Artikel.
	 * @return Eine Liste von Artikel mit PreisInCent gesetzt.
	 * @throws ServiceException
	 */
	public List<ArticleDto> getAllMerchandising() throws ServiceException;
	
	/**
	 * Liefert eine Liste aller Prämien-Artikel.
	 * @return Eine Liste von Artikel mit PreisInPoints gesetzt.
	 * @throws ServiceException
	 */
	public List<ArticleDto> getAllBonus() throws ServiceException;		
	

	// TODO ev. create(ArticleDto article), find(ArticleDto article), update(ArticleDto article), deleteById(Integer id), getAll(), ...
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	/**	 
	 * Gibt das Objekt vom Typ Artikel mit der angegebenen ID zurück.
	 * 
	 * @param Die ID des gesuchten Objekts vom Typ Artikel.
	 * @return Objekts vom Typ Artikel.
	 * @throws ServiceException Wenn zu der ID kein Artikel gefunden werden kann
	 */
	public ArticleDto getById(Integer id) throws ServiceException;
}
