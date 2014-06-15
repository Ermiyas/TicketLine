package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.NewsService;

public class NewsServiceIntegrationTest extends AbstractServiceIntegrationTest{
	@Autowired
	NewsService service;
	
	@Before
	public void setUp(){
	}
	
	@Test
	public void testGetAll(){
		try {
			List<News> news = service.getAllNews();
			
			assertEquals(10, news.size());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testGetAll_CheckOrder(){
		try {
			List<News> news = service.getAllNews();

			assertEquals(10, news.size());
			
			Date date = news.get(0).getSubmittedOn();
			assertTrue(!date.before(news.get(1).getSubmittedOn()));
			
			date = news.get(1).getSubmittedOn();
			assertTrue(!date.before(news.get(2).getSubmittedOn()));
			
			date = news.get(2).getSubmittedOn();
			assertTrue(!date.before(news.get(3).getSubmittedOn()));
			
			date = news.get(3).getSubmittedOn();
			assertTrue(!date.before(news.get(4).getSubmittedOn()));
			
			date = news.get(4).getSubmittedOn();
			assertTrue(!date.before(news.get(5).getSubmittedOn()));
			
			date = news.get(5).getSubmittedOn();
			assertTrue(!date.before(news.get(6).getSubmittedOn()));
			
			date = news.get(6).getSubmittedOn();
			assertTrue(!date.before(news.get(7).getSubmittedOn()));
			
			date = news.get(7).getSubmittedOn();
			assertTrue(!date.before(news.get(8).getSubmittedOn()));
			
			date = news.get(8).getSubmittedOn();
			assertTrue(!date.before(news.get(9).getSubmittedOn()));
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
}
