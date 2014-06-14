package at.ac.tuwien.inso.tl.server;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.server.integrationtest.service.ArtistServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.BasketServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.CustomerServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.EmployeeServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.EntryServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.LocationServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.NewsServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.PerformanceServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.ReceiptServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.RowServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.SeatServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.integrationtest.service.TicketServiceIntegrationTest;
import at.ac.tuwien.inso.tl.server.unittest.service.ArtistServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.CustomerServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.EmployeeServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.LocationServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.NewsServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.PerformanceServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.RowServiceTest;
import at.ac.tuwien.inso.tl.server.unittest.service.SeatServiceTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {ArtistServiceTest.class, ArtistServiceIntegrationTest.class, BasketServiceIntegrationTest.class, CustomerServiceIntegrationTest.class, CustomerServiceTest.class, EmployeeServiceIntegrationTest.class, EmployeeServiceTest.class, EntryServiceIntegrationTest.class, LocationServiceIntegrationTest.class, LocationServiceTest.class, NewsServiceIntegrationTest.class, NewsServiceTest.class, PerformanceServiceIntegrationTest.class, PerformanceServiceTest.class, ReceiptServiceIntegrationTest.class, RowServiceIntegrationTest.class, RowServiceTest.class, SeatServiceIntegrationTest.class, SeatServiceTest.class, TicketServiceIntegrationTest.class})
public class AppTestSuite {

}
