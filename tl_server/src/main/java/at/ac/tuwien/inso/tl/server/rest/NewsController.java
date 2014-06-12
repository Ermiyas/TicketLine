package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.NewsService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/news")
public class NewsController {
	private static final Logger LOG = Logger.getLogger(NewsController.class);
	
	@Autowired
	private NewsService newsService;
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public NewsDto getNewsById(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getNewsById() called");
		
		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}
		
		return EntityToDto.convert(newsService.getNews(id));
	}
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<NewsDto> getAll() throws ServiceException {
		LOG.info("getAll() called");

		return EntityToDto.convertNews(newsService.getAllNews());
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
}
