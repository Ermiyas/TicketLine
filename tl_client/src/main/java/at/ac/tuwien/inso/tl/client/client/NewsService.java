package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.NewsDto;

public interface NewsService {

	public List<NewsDto> getNews() throws ServiceException;
	
	public Integer publishNews(NewsDto news) throws ServiceException;
	
	public List<NewsDto> search(NewsDto input) throws ServiceException;

	public void addNewsReadByEmployee(Integer news_id) throws ServiceException;
	
	public void addNewsReadByEmployeeForce(Integer news_id) throws ServiceException;

	public List<NewsDto> findAllUnreadNewsOfEmployee() throws ServiceException;

	public NewsDto getById(Integer id) throws ServiceException;
	
	public Boolean getNewsIsReadByEmployee(Integer news_id) throws ServiceException;
	
}
