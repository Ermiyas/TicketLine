package at.ac.tuwien.inso.tl.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.server.integrationtest.service.EmployeeServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.NewsServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.unittest.service.EmployeeServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.NewsServiceTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {NewsServiceTest.class, NewsServiceIntegrationTest.class, EmployeeServiceTest.class, EmployeeServiceIntegrationTest.class})
public class AppTestSuite {

}
