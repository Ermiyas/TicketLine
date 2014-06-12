package at.ac.tuwien.inso.tl.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.ArticleDao;
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	
	@Autowired
	private ArticleDao articleDao;

	// TODO ev. create(Article article), find(Article article), update(Article article), deleteById(Integer id), getAll(), ...
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public Article getById(Integer id) throws ServiceException {
		try {
			return articleDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// Zum Testen.
	public void setArticleDao(ArticleDao dao) {
		this.articleDao = dao;
	}

}