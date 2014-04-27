package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.Date;
import java.util.List;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.NewsDao;
import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsDao newsDao;
	
	@Override
	public News getNews(Integer id) throws ServiceException {
		try {
			return newsDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public News save(News news) throws ServiceException {
		try {
			news.setSubmittedOn(new Date());
			return newsDao.save(news);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	public List<News> getAllNews() throws ServiceException{
		try {
			return newsDao.findAllOrderBySubmittedOnDesc();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	// -------------------- For Testing purposes --------------------
	
	public void setNewsDao(NewsDao dao){
		this.newsDao = dao;
	}
	
	// -------------------- For Testing purposes --------------------
}
