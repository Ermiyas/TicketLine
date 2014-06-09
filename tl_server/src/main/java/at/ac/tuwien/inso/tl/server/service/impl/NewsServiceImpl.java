package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.dao.NewsDao;
import at.ac.tuwien.inso.tl.dto.UserStatusDto;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.security.AuthUtil;
import at.ac.tuwien.inso.tl.server.service.NewsService;

@Service
public class NewsServiceImpl implements NewsService {
	
	private static final Logger LOG = Logger.getLogger(NewsServiceImpl.class);
	
	@Autowired
	private NewsDao newsDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Override
	public News getById(Integer id) throws ServiceException {
		
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
	public List<News> getNews() throws ServiceException {
		
		try {
			
			return newsDao.findAllOrderBySubmittedOnDesc();
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
	}

	@Override
	public void addNewsReadByEmployee(Integer news_id, String employee_username) throws ServiceException {

		Employee foundEmpl = employeeDao.findByUsername(employee_username).get(0);
		News fullNestedObject = newsDao.getNewsWithReadBy(news_id);

		List<Employee> readBy = null;
			
		if (fullNestedObject.getReadBy() == null) {
				
			readBy = new ArrayList<Employee>();
				
		} else {
				
			readBy = fullNestedObject.getReadBy();
				
		}
			
		Boolean isRead = getNewsIsReadByEmployee(news_id, foundEmpl.getUsername());

		if (isRead == false) {
				
			LOG.info(String.format("News with ID %s NOT marked as read for Username %s ",employee_username,news_id));
				
			if (readBy.contains(foundEmpl) == false ) {
					
				readBy = fullNestedObject.getReadBy();
				readBy.add(foundEmpl);
					
			}
				
			fullNestedObject.setReadBy(readBy);
			newsDao.saveAndFlush(fullNestedObject);
		}
			
		if (isRead == true) {
				
			LOG.info(String.format("News with ID %s IS marked as read for Username %s ",employee_username,news_id));
			newsDao.deleteNewsreadEntry(news_id,foundEmpl.getId());
				
		}
	}
	
	@Override
	public List<News> findAllUnreadNewsOfEmployee() throws ServiceException {
		
		UserStatusDto usrStatDto = AuthUtil.getUserStatusDto(SecurityContextHolder.getContext().getAuthentication());
		Employee foundEmpl = employeeDao.findByUsername(usrStatDto.getUsername()).get(0);
		
		List<News> foundNews = newsDao.findAllUnreadNewsOfEmployee(foundEmpl.getId());
		return foundNews;
		
	}
	
	@Override
	public List<News> search(News news) throws ServiceException {
		
		List<News> foundNews = newsDao.search("%" + news.getTitle() + "%");
		
		return foundNews;
	}
	
	@Override
	public Boolean getNewsIsReadByEmployee(Integer news_id, String username) {
		
		Employee foundEmpl = employeeDao.findByUsername(username).get(0);
		Integer readcount = newsDao.getTimesNewsReadByEmployee(news_id, foundEmpl.getId());
		Boolean is_read = (readcount > 0);
		
		return is_read;
	}
	
	@Override
	public void addNewsReadByEmployeeForce(Integer news_id, String username) {

		Employee foundEmpl = employeeDao.findByUsername(username).get(0);
		News fullNestedObject = newsDao.getNewsWithReadBy(news_id);
	
		List<Employee> readBy = null;
		
		if (fullNestedObject.getReadBy() == null) {
			
			readBy = new ArrayList<Employee>();
			
		} else {
			
			readBy = fullNestedObject.getReadBy();
			
		}
	
		if (readBy.contains(foundEmpl) == false ) {
			
			readBy = fullNestedObject.getReadBy();
			readBy.add(foundEmpl);
			
			fullNestedObject.setReadBy(readBy);
			newsDao.saveAndFlush(fullNestedObject);
			
		}
	}
	
	// -------------------- For Testing purposes --------------------
	
	public void setNewsDao(NewsDao dao) {
		
		this.newsDao = dao;
		
	}
	
	public void setEmployeeDao(EmployeeDao dao) {
		
		this.employeeDao = dao;
		
	}
	
	// -------------------- For Testing purposes --------------------
	
	
}
