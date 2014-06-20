package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.ArticleDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArticleService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {
	private static final Logger LOG = Logger.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService service;
	
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ArticleDto> getAllArticles() throws ServiceException  {
		LOG.info("getAllArticles called.");
		return EntityToDto.convertArticles(service.getAll());
	}
	
	@RequestMapping(value = "/getAllMerchandising", method = RequestMethod.GET, produces = "application/json")
	public List<ArticleDto> getAllMerchandising() throws ServiceException  {
		LOG.info("getAllMerchandising called.");
		return EntityToDto.convertArticles(service.getAllMerchandising());
	}
	
	@RequestMapping(value = "/getAllBonus", method = RequestMethod.GET, produces = "application/json")
	public List<ArticleDto> getAllBonus() throws ServiceException  {
		LOG.info("getAllBonus called.");
		return EntityToDto.convertArticles(service.getAllBonus());
	}
	
	

}
