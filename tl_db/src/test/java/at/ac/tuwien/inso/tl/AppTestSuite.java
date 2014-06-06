package at.ac.tuwien.inso.tl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.dao.ArtistDaoTest;
import at.ac.tuwien.inso.tl.dao.LocationDaoTest;
import at.ac.tuwien.inso.tl.dao.NewsDaoTest;
import at.ac.tuwien.inso.tl.dao.PerformanceDaoTest;
import at.ac.tuwien.inso.tl.dao.RowDaoTest;
import at.ac.tuwien.inso.tl.dao.SeatDaoTest;
import at.ac.tuwien.inso.tl.dao.ShowDaoTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {ArtistDaoTest.class, LocationDaoTest.class, NewsDaoTest.class, PerformanceDaoTest.class, RowDaoTest.class, SeatDaoTest.class, ShowDaoTest.class})
public class AppTestSuite {

}
