package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Article;

public class ArticleDaoTest extends AbstractDaoTest {
	
	@Autowired
	private ArticleDao adao;

	private static final Logger LOG = Logger.getLogger(ArticleDaoTest.class);
	
	@Before
	public void setUp() throws Exception {
		LOG.info("setUp called.");
	}

	@After
	public void tearDown() throws Exception {
		LOG.info("tearDown called.");
	}	
	
	@Test
	public void testgetAllMerchandising_ShouldReturnAllArticlesWithPriceInCentSet()
	{
		List<Article> merchandising = adao.getAllMerchandising();
		for(Article a: adao.findAll())
		{
			if(a.getPriceInCent() != null)
			{
				assertTrue(merchandising.contains(a));
			}
		}
	}
	
	@Test
	public void testgetAllBonus_ShouldReturnAllArticlesWithPriceInPointsSet()
	{
		List<Article> bonuses = adao.getAllMerchandising();
		for(Article a: adao.findAll())
		{
			if(a.getPriceInPoints() != null)
			{
				assertTrue(bonuses.contains(a));
			}
		}
	}

}
