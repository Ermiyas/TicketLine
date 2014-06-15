package at.ac.tuwien.inso.tl.server.integrationtest.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration(locations="classpath:test-context.xml")
@Transactional
public abstract class AbstractServiceIntegrationTest {
	
}
