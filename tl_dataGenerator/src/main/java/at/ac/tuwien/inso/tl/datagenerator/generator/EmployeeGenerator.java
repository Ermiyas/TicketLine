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

		dao.save(new Employee("Marvin", "Jones", "marvin", this.encoder.encode("42"), true, 0));
		dao.save(new Employee("Manuela", "Oster", "m.oster", this.encoder.encode("42"), false, 0));
		dao.save(new Employee("Jennifer", "Fuerst", "j.fuerst", this.encoder.encode("42"), true, 1));
		dao.save(new Employee("Janina", "Scholz", "j.scholz", this.encoder.encode("42"), false, 4));
		dao.save(new Employee("Jakob", "Scholz", "ja.scholz", this.encoder.encode("42"), false, 0));
		
		
	}
}
