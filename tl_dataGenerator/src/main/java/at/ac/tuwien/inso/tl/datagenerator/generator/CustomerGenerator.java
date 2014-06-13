package at.ac.tuwien.inso.tl.datagenerator.generator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.dao.CustomerDao;
import at.ac.tuwien.inso.tl.model.Customer;

@Component
public class CustomerGenerator implements DataGenerator{
	private static final Logger LOG = Logger.getLogger(CustomerGenerator.class);
	
	// Auch unvollständige Jahresangaben ermoeglichen
	private static final String DATE_FORMAT = "d.M.yy";
	
	@Autowired
	CustomerDao dao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void generate(){
		LOG.info("+++++ Generate Customer Data +++++");

//		public Customer(Integer id, String city, String country, Date dateOfBirth,
//				String email, String firstname, Boolean isFemale, String lastname,
//				Integer points, String postalcode, String street,
//				String telephonenumber, String title, List<Basket> baskets) {...}
		try {
			dao.save(new Customer(1, "Wien", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("01.01.1901"), "tutor_andreas.drach@inso.tuwien.ac.at", "Andreas", false, "Drach", null, null, null, null, "Hr. Tutor", null));
			dao.save(new Customer(2, "Wien", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("01.01.1901"), "e1026466@student.tuwien.ac.at", "Jakob", false, "Kremsner", 10, null, null, "+43 676 88988880", "Hr.", null));
			dao.save(new Customer(3, "Tulln", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("01.01.1901"), "e1129399@student.tuwien.ac.at", "Dominic", false, "Hofbauer", 20, null, null, "+43 699 11566238", "Hr.", null));
			dao.save(new Customer(4, "Wien", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("01.01.1901"), "chen.wenchao.private@gmail.com", "\"Victor\" Wen Chao", false, "Chen", 30, null, null, "+43 699 81356177", "Hr.", null));
			dao.save(new Customer(5, "Wien", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("01.01.1901"), "alexander@gressler.org", "Alexander", false, "Greßler", 40, null, null, "+43 699 19711904", "Hr.", null));
			dao.save(new Customer(6, "Wien", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("01.01.1901"), "florianchlan@punktorg.org", "Florian", false, "Chlan", 50, null, null, "+43 676 3389113", "Hr.", null));
			dao.save(new Customer(7, "Amsterdam", "Niederlande", new SimpleDateFormat(DATE_FORMAT).parse("27.12.1963"), "e8325143@student.tuwien.ac.at", "Robert", false, "Bekker", 1000, "A-1060", "Millergasse 12/14", "+43 699 88458477", "Hr.", null));
			dao.save(new Customer(8, "Wien", "Österreich", new SimpleDateFormat(DATE_FORMAT).parse("20.2.62"), null, "Astrid", true, "Bekker-Rohm", 0, "A-1060", "Millergasse 12/14", null, "Fr.", null));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
