package at.ac.tuwien.inso.tl.datagenerator.generator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.dao.LocationDao;
import at.ac.tuwien.inso.tl.model.Location;

@Component
public class LocationGenerator implements DataGenerator {
	private static final Logger LOG = Logger.getLogger(DataGenerator.class);
	
	@Autowired
	LocationDao dao;
	
	public void generate(){
		LOG.info("+++++ Generate Location Data +++++");
		
		dao.save(new Location("Nickelsdorf","Österreich","Pannonia Fields II","2425",null));
		dao.save(new Location("Unterpremstätten","Österreich","Schwarzlsee","8141",null));
		dao.save(new Location("Wien","Österreich","Flex","1010","Augartenbrücke 1"));
		dao.save(new Location("Wien","Österreich","Pratersauna","1020","Waldsteingartenstraße 135"));
		dao.save(new Location("Wien","Österreich","Lugner Kino City","1150","Gablenzgasse 3"));
		dao.save(new Location("Wien","Österreich","Wiener Staatsoper","1010","Opernring 2"));
		dao.save(new Location("Wien","Österreich","Burgtheater","1010","Universitätsring 2"));
		
	}
}
