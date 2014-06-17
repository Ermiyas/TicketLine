package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;

import at.ac.tuwien.inso.tl.dao.NewsDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.model.News;

public class NewsDaoTest extends AbstractDaoTest {
	
	@Autowired
	private NewsDao ndao;
	
	private static LinkedList<Integer> realNewsReadByMarvinJones;
	private static LinkedList<Integer> realNewsUnreadByMarvinJones;
	private Integer marvinJonesId = 1;
	private static LinkedList<Integer> realNewsReadByManuelaOster;
	private static LinkedList<Integer> realNewsUnreadByManuelaOster;
	private Integer manuelaOsterId = 2;
	private Integer pacificRimNewsId = 8;
	private Integer newsItemsSize = 10;
	private Integer lebenOhneMuellId = 3;
	private Integer laBohemeNewsId = 2;
	
	@BeforeClass
	public static void init() {

		realNewsReadByMarvinJones = new LinkedList<Integer>();
		realNewsUnreadByMarvinJones = new LinkedList<Integer>();		
		realNewsReadByManuelaOster = new LinkedList<Integer>();
		realNewsUnreadByManuelaOster = new LinkedList<Integer>();
		
		realNewsReadByMarvinJones.add(1);
		realNewsReadByMarvinJones.add(2);
		realNewsReadByMarvinJones.add(3);
		
		realNewsUnreadByMarvinJones.add(4);
		realNewsUnreadByMarvinJones.add(5);
		realNewsUnreadByMarvinJones.add(6);
		realNewsUnreadByMarvinJones.add(7);
		realNewsUnreadByMarvinJones.add(7);
		realNewsUnreadByMarvinJones.add(8);
		realNewsUnreadByMarvinJones.add(9);
		realNewsUnreadByMarvinJones.add(10);
		
		realNewsReadByManuelaOster.add(3);
		realNewsReadByManuelaOster.add(4);
		
		realNewsUnreadByManuelaOster.add(1);
		realNewsUnreadByManuelaOster.add(2);
		realNewsUnreadByManuelaOster.add(5);
		realNewsUnreadByManuelaOster.add(6);
		realNewsUnreadByManuelaOster.add(7);
		realNewsUnreadByManuelaOster.add(8);
		realNewsUnreadByManuelaOster.add(9);
		realNewsUnreadByManuelaOster.add(10);
		
	}
	
	@Test
	public void testFindAll() {
		
		List<News> news = ndao.findAll();
		assertEquals("Check DB initial data - is ten first", 10, news.size());
		
	}
	
	@Test
	public void testFindAllOrderBySubmittedOnDesc_CheckOrder() {
		
		List<News> news = ndao.findAllOrderBySubmittedOnDesc();

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
		
	}
	
	@Test
	public void testFindOneById() {
		
		News n = ndao.findOne(6);
		assertEquals(6, n.getId().longValue());
		assertEquals("Ein Sommernachtstraum", n.getTitle());
		
	}
	
	@Test
	public void testFindOneById_NegativId() {
		
		assertNull(ndao.findOne(-1));
		
	}
	
	@Test
	public void testFindOneById_InvalidId() {
		
		assertNull(ndao.findOne(0));
		
	}
	
	@Test
	public void testAddNews() {
		
		assertEquals("Check DB initial data - is ten first", 10, ndao.count());
		News n = new News();
		n.setTitle("NewsDao test");
		n.setNewsText("test text,test text,test text,test text,test text,test text");
		n.setSubmittedOn(new GregorianCalendar().getTime());
		
		News saved = ndao.save(n);
		assertEquals("Check News count - should be 11", 11, ndao.count());
		
		n= null;
		n = ndao.findOne(saved.getId());
		assertNotNull(n);
		
	}
	
	@Test
	public void testAddNews_shouldThrowException_TitleNull() {
		
		assertEquals("Check DB initial data - is ten first", 10, ndao.count());
		News n = new News();
		n.setNewsText("test text,test text,test text,test text,test text,test text");
		n.setSubmittedOn(new GregorianCalendar().getTime());
		
		try {
			
			ndao.save(n);
			fail("DataIntegrityViolationException not thrown");
			
		} catch (DataIntegrityViolationException e) {
			
			assertNotNull(e.getMessage());
			
		}
	}
	
	@Test
	public void testAddNews_shouldThrowException_NewsTextNull() {
		
		assertEquals("Check DB initial data - is ten first", 10, ndao.count());
		News n = new News();
		n.setTitle("NewsDao test");
		n.setSubmittedOn(new GregorianCalendar().getTime());
		
		try {
			
			ndao.save(n);
			fail("DataIntegrityViolationException not thrown");
			
		} catch (DataIntegrityViolationException e) {
			
			assertNotNull(e.getMessage());
			
		}
	}
	
	@Test
	public void testDeleteNews() {
		
		assertEquals("Check DB initial data - is ten first", 10, ndao.count());
		ndao.delete(1);

		assertEquals("Check News count - should be 9", 9, ndao.count());
		
	}
	
	@Test
	public void testDeleteNews_shouldThrowException_InvalidId() {
		
		assertEquals("Check DB initial data - is ten first", 10, ndao.count());
		
		try {
			
			ndao.delete(-1);
			fail("EmptyResultDataAccessException not thrown");
			
		} catch (EmptyResultDataAccessException e) {
			
			assertNotNull(e.getMessage());
			
		}
	}

	@Test
	public void testFindAllUnreadNewsForMarvin_DaoShouldFindAll() {
	
		List<News> allUnread = ndao.findAllUnreadNewsOfEmployee(marvinJonesId);
		
		for (int i = 0; i < realNewsUnreadByMarvinJones.size(); i++) {
		
			Boolean found_it = false;
			
			for (News item : allUnread) {
				
				if (item.getId() == realNewsUnreadByMarvinJones.get(i)) {
					
					found_it = true;
					
				}
			}
			
			assertTrue(found_it);
			
		}
	}
	
	@Test
	public void testFindAllUnreadNewsForManuela_DaoShouldFindAll() {
	
		List<News> allUnread = ndao.findAllUnreadNewsOfEmployee(manuelaOsterId);
		
		for (int i = 0; i < realNewsUnreadByManuelaOster.size(); i++) {
		
			Boolean found_it = false;
			
			for (News item : allUnread) {
				
				if (item.getId() == realNewsUnreadByManuelaOster.get(i)) {
					
					found_it = true;
					
				}
			}
			
			assertTrue(found_it);
	
		}
	}	

	@Test
	public void testSearch_DaoTitleSearchNoWildcardsShouldWork() {
			
		List<News> results = ndao.search("Pacific Rim (Kinostart: 18. Juli 2013)");
		
		assertSame(pacificRimNewsId, results.get(0).getId());
		
	}
	
	@Test
	public void testSearch_DaoTitleSearchWithWildcardsShouldWork() {
			
		List<News> results = ndao.search("%Pacific%");
 		assertSame(pacificRimNewsId, results.get(0).getId());
		
	}
	
	@Test
	public void testSearch_DaoTitleSearchWithWildcardsAndWrongCasingShouldWork() {

		List<News> results = ndao.search("%paci%");
		
		assertSame(pacificRimNewsId, results.get(0).getId());
		
	}
	
	@Test
	public void testSearch_TitleSearchWithEmptySearchstringShouldFindAll() {

		List<News> results = ndao.search("%%");
		
		assertSame(newsItemsSize , results.size());
		
	}
	
	@Test
	public void testGetNewsWithReadBy_VerifyLebenOhneMuellReadByManuelaAndMarvin() {

		Boolean findError = false;
		News result = ndao.getNewsWithReadBy(lebenOhneMuellId);
		
		for (Employee item : result.getReadBy()) {
			
			if (item.getId() != manuelaOsterId && item.getId() != marvinJonesId) {
				
				findError = true;
				
			}
		}
		
		assertFalse(findError);
		
	}	
	
	@Test
	public void testDeleteNewsReadItem_VerifyIsRemoved() {
		
		// Marvin Jones aus der readBy-Liste von News "Pacific Rim" entfernen
		
		ndao.deleteNewsreadEntry(laBohemeNewsId, marvinJonesId);
		List<News> allNewsFetched =  ndao.findAll();
		
		for (News item : allNewsFetched) {
			
			for (Employee reader : item.getReadBy()) {
				
				if (item.getId() == laBohemeNewsId &&reader.getId() == marvinJonesId) {
					
					fail();
					
				}
			}
		}
	}
}
