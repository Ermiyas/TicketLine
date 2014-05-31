package at.ac.tuwien.inso.tl.dao;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RowDaoTest extends AbstractDaoTest {
	
	@Autowired
	private RowDao rdao;
	
	@Before
	public void setUp() throws Exception {		
	}

	@After
	public void tearDown() throws Exception {
	}	
	
	@Test
	public void testfindRows_ShouldNotReturnNull()
	{
		assertNotNull(rdao.findRows(null));
	}
}
