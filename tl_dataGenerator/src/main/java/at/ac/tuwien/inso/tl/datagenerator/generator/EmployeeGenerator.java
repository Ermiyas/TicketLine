package at.ac.tuwien.inso.tl.datagenerator.generator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.dao.EmployeeDao;
import at.ac.tuwien.inso.tl.model.Employee;

@Component
public class EmployeeGenerator implements DataGenerator{
	private static final Logger LOG = Logger.getLogger(EmployeeGenerator.class);
	
	@Autowired
	EmployeeDao dao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void generate(){
		LOG.info("+++++ Generate Employee Data +++++");

		String firstname = "Marvin";
		String lastname = "Jones";
		String username = "marvin";
		String password = "42";
		Boolean isAdmin = true;
		Integer wrongPasswordCounter = 0;
		String pwHash = this.encoder.encode(password);
		Employee e1 = new Employee(firstname, lastname, username, pwHash, isAdmin, wrongPasswordCounter);
		dao.save(e1);
	}
}
