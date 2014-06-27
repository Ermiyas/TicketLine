package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface ArticleService {

	/**
	 * Liefert eine Liste aller Artikel
	 * @return Eine Liste aller Artikel.
	 * @throws ServiceException
	 */
	public List<Article> getAll() throws ServiceException;
	
	/**
	 * Liefert eine Liste aller Mechandising-Artikel.
	 * @return Eine Liste von Artikel mit PreisInCent gesetzt.
	 * @throws ServiceException
	 */
	public List<Article> getAllMerchandising() throws ServiceException;
	
	/**
	 * Liefert eine Liste aller Prämien-Artikel.
	 * @return Eine Liste von Artikel mit PreisInPoints gesetzt.
	 * @throws ServiceException
	 */
	public List<Article> getAllBonus() throws ServiceException;		
	
	/**
	 * Returns the article object identified by the given id
	 * 
	 * @param id of the article object
	 * @return the article object
	 * @throws ServiceException
	 */
	public Article getById(Integer id) throws ServiceException;	
}
