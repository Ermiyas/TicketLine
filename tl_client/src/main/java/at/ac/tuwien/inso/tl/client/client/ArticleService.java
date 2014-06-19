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
}
