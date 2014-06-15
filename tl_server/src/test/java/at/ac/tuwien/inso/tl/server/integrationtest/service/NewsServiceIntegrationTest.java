package at.ac.tuwien.inso.tl.server.integrationtest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.security.TicketlineUser;
import at.ac.tuwien.inso.tl.server.service.NewsService;

public class NewsServiceIntegrationTest extends AbstractServiceIntegrationTest{
	@Autowired
	NewsService service;
	
	private static LinkedList<Integer> realNewsReadByMarvinJones;
	private static LinkedList<Integer> realNewsReadByManuelaOster;
	private String marvinJonesUsername = "marvin";
	private String manuelaOsterUsername = "m.oster";
	private Integer pacificRimNewsId = 8;
	private String pacificRimNewsTitle = "Pacific Rim (Kinostart: 18. Juli 2013)";
	private Integer newsItemsSize = 10;
	private Integer laBohemeNewsId = 2;
	
	@BeforeClass
	public static void init() {
		
		// Sollwerte hinterlegen
		realNewsReadByMarvinJones = new LinkedList<Integer>();
		realNewsReadByManuelaOster = new LinkedList<Integer>();
		
		realNewsReadByMarvinJones.add(1);
		realNewsReadByMarvinJones.add(2);
		realNewsReadByMarvinJones.add(3);
		
		realNewsReadByManuelaOster.add(3);
		realNewsReadByManuelaOster.add(4);
		
		ArrayList<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		
		TicketlineUser user = new TicketlineUser("marvin", "123", roles,  "Marvin", "Jones", 0);
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}
	
	@Test
	public void testGetAll(){
		try {
			
			List<News> news = service.getNews();
			
			assertEquals(10, news.size());
		} catch (ServiceException e) {
			fail("ServiceException thrown");
		}
	}
	
	@Test
	public void testGetAll_CheckOrder(){
		try {

			List<News> news = service.getNews();

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

	@Test
	public void testSearch_TitleSearchNoWildcardsShouldWork() throws ServiceException {
		
		News searchObject = new News();
		searchObject.setTitle("Pacific Rim (Kinostart: 18. Juli 2013)");
			
		List<News> results = service.search(searchObject);
		
		assertSame(pacificRimNewsId, results.get(0).getId());
		
	}
	
	@Test
	public void testSearch_TitleSearchWithWildcardsShouldWork() throws ServiceException {
		
		News searchObject = new News();
		searchObject.setTitle("Pacific");
			
		List<News> results = service.search(searchObject);
		
		assertSame(pacificRimNewsId, results.get(0).getId());
		
	}
	
	@Test
	public void testSearch_TitleSearchWithWildcardsAndWrongCasingShouldWork() throws ServiceException {
		
		News searchObject = new News();
		searchObject.setTitle("paci");
			
		List<News> results = service.search(searchObject);
		
		assertSame(pacificRimNewsId, results.get(0).getId());
		
	}
	
	@Test
	public void testSearch_TitleSearchWithEmptySearchstringShouldFindAll() throws ServiceException {
		
		News searchObject = new News();
		searchObject.setTitle("");
			
		List<News> results = service.search(searchObject);
		
		assertSame(newsItemsSize , results.size());
		
	}
	
	@Test
	public void testNewsIsReadByEmployee_ForMarvinJonesShouldGetReadStatusCorrect() throws ServiceException {
		
		News searchObject = new News();
		searchObject.setTitle("");
			
		for (int counter = 1; counter <= newsItemsSize; counter++) {
			
			Boolean isRead = service.getNewsIsReadByEmployee(counter, marvinJonesUsername);
			assertEquals(realNewsReadByMarvinJones.contains(counter), isRead);		
		}

	}
	
	@Test
	public void testNewsIsReadByEmployee_ForManuelaOsterShouldGetReadStatusCorrect() throws ServiceException {
		
		News searchObject = new News();
		searchObject.setTitle("");
			
		for (int counter = 1; counter <= newsItemsSize; counter++) {
			
			Boolean isRead = service.getNewsIsReadByEmployee(counter, manuelaOsterUsername);
			assertEquals(realNewsReadByManuelaOster.contains(counter), isRead);		
		}

	}	
	
	@Test
	public void testGetById_ForPacificRimShouldReturnCorrectTitle() throws ServiceException {
		
		News foundObject = service.getById(pacificRimNewsId);
		
		assertEquals(pacificRimNewsTitle, foundObject.getTitle());
		
	}
	
	@Test
	public void testToggleOnce_ForLaBohemeAndMarvinShouldSetUnreadForEmployee() throws ServiceException {
		
		service.addNewsReadByEmployee(laBohemeNewsId, marvinJonesUsername);
		
		Boolean isReadNow = service.getNewsIsReadByEmployee(laBohemeNewsId, marvinJonesUsername);
		
		assertFalse(isReadNow);
		
	}	
	
	@Test
	public void testToggleThreeTimes_ForLaBohemeAndMarvinShouldSetUnreadForEmployee() throws ServiceException {
		
		service.addNewsReadByEmployee(laBohemeNewsId, marvinJonesUsername);
		service.addNewsReadByEmployee(laBohemeNewsId, marvinJonesUsername);
		service.addNewsReadByEmployee(laBohemeNewsId, marvinJonesUsername);

		Boolean isReadNow = service.getNewsIsReadByEmployee(laBohemeNewsId, marvinJonesUsername);
		
		assertFalse(isReadNow);
		
	}
	
	@Test
	public void testForceNewsIsReadForAll_ForLaBohemeAndMarvinShouldSetUnreadForEmployee() throws ServiceException {

		for (int counter = 1; counter <= newsItemsSize; counter++) {
			
			service.addNewsReadByEmployeeForce(counter, marvinJonesUsername);
			Boolean isRead = service.getNewsIsReadByEmployee(counter, marvinJonesUsername);
			assertTrue(isRead);		
			
		}
		
	}
	
	@Test
	public void testGetAllUnreadForMarvinJones_ShouldNotReturnReadNews() throws ServiceException {

		List<News> allUnreadNewsFetched = service.findAllUnreadNewsOfEmployee();
		
		for (int counter = 0; counter < allUnreadNewsFetched.size(); counter++) {
			
			assertFalse(realNewsReadByMarvinJones.contains(allUnreadNewsFetched.get(counter)));
			
		}
	}
}
