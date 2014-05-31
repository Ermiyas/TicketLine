package at.ac.tuwien.inso.tl;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.ac.tuwien.inso.tl.dao.NewsDaoTest;

@RunWith(value = Suite.class)
@SuiteClasses(value = {NewsDaoTest.class})
public class AppTestSuite {

}
