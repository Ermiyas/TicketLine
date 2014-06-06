package at.ac.tuwien.inso.tl.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.server.integrationtest.service.CustomerServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.EmployeeServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.NewsServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.RowServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.SeatServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.unittest.service.CustomerServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.EmployeeServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.NewsServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.RowServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.SeatServiceTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {CustomerServiceIntegrationTest.class, CustomerServiceTest.class, EmployeeServiceIntegrationTest.class, EmployeeServiceTest.class, NewsServiceIntegrationTest.class, NewsServiceTest.class, RowServiceIntegrationTest.class, RowServiceTest.class, SeatServiceIntegrationTest.class, SeatServiceTest.class})
public class AppTestSuite {

}
