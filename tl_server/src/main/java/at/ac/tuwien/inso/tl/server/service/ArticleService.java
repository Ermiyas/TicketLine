package at.ac.tuwien.inso.tl.server.service;

import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface ArticleService {
	
	// TODO create(Article article), find(Article article), update(Article article), deleteById(Integer id), getAll, ...

	/**
	 * Returns the article object identified by the given id
	 * 
	 * @param id of the article object
	 * @return the article object
	 * @throws ServiceException
	 */
	public Article getById(Integer id) throws ServiceException;	
	
}