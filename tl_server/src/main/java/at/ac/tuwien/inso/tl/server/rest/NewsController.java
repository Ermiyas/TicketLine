package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EmployeeService;
import at.ac.tuwien.inso.tl.server.service.NewsService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
	private static final Logger LOG = Logger.getLogger(NewsController.class);
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public NewsDto getById(@PathVariable("id") Integer id) throws ServiceException {
		
		LOG.info("getById() called");
		
		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}
		
		return EntityToDto.convert(newsService.getById(id));
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<NewsDto> getNews() throws ServiceException {
		
		LOG.info("getNews() called");

		return EntityToDto.convertNews(newsService.getNews());
	}
	
	@RequestMapping(value = "/publish", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto publishNews(@Valid @RequestBody NewsDto news) throws ServiceException {
		LOG.info("publishNews() called");

		Integer id = this.newsService.save(DtoToEntity.convert(news)).getId();
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(id.toString());

		return msg;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<NewsDto> search(@RequestBody NewsDto news) throws ServiceException {
		
		LOG.info("search() called");

		return EntityToDto.convertNews(this.newsService.search(DtoToEntity.convert(news)));
	}

	@RequestMapping(value ="/findallunread", method = RequestMethod.GET, produces = "application/json")
	public List<NewsDto> findAllUnreadNewsOfEmployee() throws ServiceException {
		
		LOG.info("findAllUnreadNewsOfEmployee() called");

		return EntityToDto.convertNews(newsService.findAllUnreadNewsOfEmployee());
	}
	
	@RequestMapping(value = "/{news_id}/addreadby", method = RequestMethod.GET, produces = "application/json")
	public MessageDto addNewsReadByEmployee(@PathVariable("news_id") Integer news_id) throws ServiceException {
		
		LOG.info("addNewsReadByEmployee() called");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		this.newsService.addNewsReadByEmployee(news_id, username);
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);

		return msg;
	}
	
	@RequestMapping(value = "/{news_id}/addreadby/force", method = RequestMethod.GET, produces = "application/json")
	public MessageDto addNewsReadByEmployeeForce(@PathVariable("news_id") Integer news_id) throws ServiceException {
		
		LOG.info("addNewsReadByEmployeeForce() called");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		this.newsService.addNewsReadByEmployeeForce(news_id, username);
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);

		return msg;
	}
	
	@RequestMapping(value = "/{news_id}/getisread", method = RequestMethod.GET, produces = "application/json")
	public Boolean getNewsIsReadByEmployee(@PathVariable("news_id") Integer news_id) throws ServiceException {
		
		LOG.info("getNewsIsReadByEmployee() called");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return this.newsService.getNewsIsReadByEmployee(news_id, username);
	}
}
