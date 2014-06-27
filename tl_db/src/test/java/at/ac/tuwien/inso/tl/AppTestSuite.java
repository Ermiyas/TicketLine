package at.ac.tuwien.inso.tl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.dao.ArticleDaoTest;
import at.ac.tuwien.inso.tl.dao.ArtistDaoTest;
import at.ac.tuwien.inso.tl.dao.BasketDaoTest;
import at.ac.tuwien.inso.tl.dao.EntryDaoTest;
import at.ac.tuwien.inso.tl.dao.LocationDaoTest;
import at.ac.tuwien.inso.tl.dao.NewsDaoTest;
import at.ac.tuwien.inso.tl.dao.PerformanceDaoTest;
import at.ac.tuwien.inso.tl.dao.ReceiptDaoTest;
import at.ac.tuwien.inso.tl.dao.RowDaoTest;
import at.ac.tuwien.inso.tl.dao.SeatDaoTest;
import at.ac.tuwien.inso.tl.dao.ShowDaoTest;
import at.ac.tuwien.inso.tl.dao.TicketDaoTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {ArticleDaoTest.class, ArtistDaoTest.class, BasketDaoTest.class, EntryDaoTest.class, LocationDaoTest.class, NewsDaoTest.class, PerformanceDaoTest.class, ReceiptDaoTest.class, RowDaoTest.class, SeatDaoTest.class, ShowDaoTest.class, TicketDaoTest.class})
public class AppTestSuite {

}