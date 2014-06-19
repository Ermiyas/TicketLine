package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.ArticleDao;
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	private static final Logger LOG = Logger.getLogger(ArticleServiceImpl.class);
	
	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public List<Article> getAll() throws ServiceException {
		LOG.info("getAll called.");		
		try {	
			return articleDao.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Article> getAllMerchandising() throws ServiceException {
		LOG.info("getAllMerchandising called.");		
		try {	
			return articleDao.getAllMerchandising();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Article> getAllBonus() throws ServiceException {
		LOG.info("getAllBonus called.");
		try {	
			return articleDao.getAllBonus();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// -------------------- For Testing purposes --------------------
	
		public void setArticleDao(ArticleDao dao){
			LOG.info("articleDao called.");
			this.articleDao = dao;
		}
			
	// -
}
