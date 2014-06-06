package at.ac.tuwien.inso.tl.server.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.ArticleDto;
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ArticleService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {
	
	private static final Logger LOG = Logger.getLogger(Article.class);
	
	@Autowired
	private ArticleService articleService;

	// TODO createArticle, findArticle, updateArticle, deleteArticleById, getAll
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public ArticleDto findArticleById(@PathVariable String id) throws ServiceException {
		LOG.info("findArticleById() called");

		return EntityToDto.convert(this.articleService.getById(Integer.parseInt(id)));
	}
	
}