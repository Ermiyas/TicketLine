package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.ArticleDto;

public interface ArticleService {
	
	// TODO create(ArticleDto article), find(ArticleDto article), update(ArticleDto article), deleteById(Integer id), getAll(), ...
	
	public ArticleDto getById(Integer id) throws ServiceException;
	
}