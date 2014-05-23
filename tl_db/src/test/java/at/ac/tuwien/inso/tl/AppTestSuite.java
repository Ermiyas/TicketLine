package at.ac.tuwien.inso.tl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.dao.NewsDaoTest;
import at.ac.tuwien.inso.tl.dao.ArtistDaoTest;
import at.ac.tuwien.inso.tl.dao.PerformanceDaoTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {NewsDaoTest.class, ArtistDaoTest.class, PerformanceDaoTest.class})
public class AppTestSuite {

}
