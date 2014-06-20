package at.ac.tuwien.inso.tl.server.unittest.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.dao.NewsDao;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.impl.NewsServiceImpl;

public class NewsServiceTest {
	NewsServiceImpl service = null;
	
	List<News> news = null;
	
	@Before
	public void setUp(){
		service = new NewsServiceImpl();
		news = new ArrayList<News>();
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(2013, 5, 1);
		News n1 = new News(1, gc.getTime(), "first Title", "first newstext");
		news.add(n1);
		News n2 = new News(2, gc.getTime(), "Second Title", "Hudel Dudel");
		news.add(n2);
		gc.set(2013, 7, 4);
		News n3 = new News(3, gc.getTime(), "Bi Ba", "Fischers Fritz fischt frische Fische");
		news.add(n3);
		News n4 = new News(4, gc.getTime(), "Änderungsmitteilung", "Alles muss raus");
		news.add(n4);
		gc.set(2013, 8, 15);
		News n5 = new News(5, gc.getTime(), "It's the last News", "Nix neues im Westen");
		news.add(n5);
	}
	
	@Test
	public void testGetAll(){
		NewsDao dao =Mockito.mock(NewsDao.class);
		Mockito.when(dao.findAllOrderBySubmittedOnDesc()).thenReturn(news);
		service.setNewsDao(dao);
		
		try {
			
			assertEquals(5, service.getNews().size());
			
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testGetAll_shouldThrowServiceException(){
		NewsDao dao =Mockito.mock(NewsDao.class);
		Mockito.when(dao.findAllOrderBySubmittedOnDesc()).thenThrow(new RuntimeException("no db"));
		service.setNewsDao(dao);
		
		try {
			
			service.getNews();
			fail("ServiceException not thrown");
		} catch (ServiceException e) {
			assertNotNull(e.getMessage());
		}
	}
	
	@Test
	public void testSearch_TitleSearchWithWildcardsShouldCallDao() throws ServiceException {
		
		NewsDao dao = Mockito.mock(NewsDao.class);
		ArrayList<News> resultList = new ArrayList<News>();
		resultList.add(news.get(0));
		
		Mockito.when(dao.search("%first Title%")).thenReturn(resultList);
		service.setNewsDao(dao);
		
		News searchObject = new News();
		searchObject.setTitle("first Title");
			
		List<News> results = service.search(searchObject);
		
		assertSame(results.get(0).getId(), news.get(0).getId());
		
	}
	
	@Test
	public void testNewsIsReadByEmployeeReadCount4_ShouldReturnTrue() throws ServiceException {
		
		NewsDao newsDao = Mockito.mock(NewsDao.class);
		EmployeeDao empDao = Mockito.mock(EmployeeDao.class);
		
		Employee marvin = new Employee();
		marvin.setId(1);
		marvin.setUsername("marvin");
		
		ArrayList<Employee> resultList = new ArrayList<Employee>();
		resultList.add(marvin);
		
		Mockito.when(newsDao.getTimesNewsReadByEmployee(1, marvin.getId())).thenReturn(4);
		service.setNewsDao(newsDao);
		
		Mockito.when(empDao.findByUsername(marvin.getUsername())).thenReturn(resultList);
		service.setEmployeeDao(empDao);
		
		Boolean is_read = service.getNewsIsReadByEmployee(1, marvin.getUsername());
		
		assertTrue(is_read);
		
	}
	
	@Test
	public void testNewsIsReadByEmployeeReadCount0_ShouldReturnFalse() throws ServiceException {
		
		NewsDao newsDao = Mockito.mock(NewsDao.class);
		EmployeeDao empDao = Mockito.mock(EmployeeDao.class);
		
		Employee marvin = new Employee();
		marvin.setId(1);
		marvin.setUsername("marvin");
		
		ArrayList<Employee> resultList = new ArrayList<Employee>();
		resultList.add(marvin);
		
		Mockito.when(newsDao.getTimesNewsReadByEmployee(1, marvin.getId())).thenReturn(0);
		service.setNewsDao(newsDao);
		
		Mockito.when(empDao.findByUsername(marvin.getUsername())).thenReturn(resultList);
		service.setEmployeeDao(empDao);
		
		Boolean is_read = service.getNewsIsReadByEmployee(1, marvin.getUsername());
		
		assertFalse(is_read);
		
	}	
}
