package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.NewsDto;

public interface NewsService {

	public List<NewsDto> getNews() throws ServiceException;
	
	public Integer publishNews(NewsDto news) throws ServiceException;
}
