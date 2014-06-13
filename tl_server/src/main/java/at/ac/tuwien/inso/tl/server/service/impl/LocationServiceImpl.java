package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.LocationDao;
import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	
	private static final Logger LOG = Logger.getLogger(LocationServiceImpl.class);
	
	@Autowired
	private LocationDao locationDao;

	@Override
	public Location createLocation(Location location) throws ServiceException {
		LOG.info("createLocation called.");
		if(location.getId() != null)
			throw new ServiceException("ID must be null.");
		try {	
			return locationDao.save(location);	
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteLocation(Integer id) throws ServiceException {
		LOG.info("deleteLocation called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			locationDao.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Location> findLocations(String city, String country,
			String description, String postalCode, String street)
			throws ServiceException {
		LOG.info("findLocations called.");
		try {	
			return locationDao.findLocations(city, country, description, postalCode, street);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Location> getAllLocations() throws ServiceException {
		LOG.info("getAllLocations called.");
		try {	
			return locationDao.findAll();	
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Location getLocation(Integer id) throws ServiceException {
		LOG.info("getLocation called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return locationDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Location updateLocation(Location location) throws ServiceException {
		LOG.info("updateLocation called.");
		if(location.getId() == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return locationDao.save(location);	
		} catch (Exception e) {
			throw new ServiceException(e);
		}	
	}

	// -------------------- For Testing purposes --------------------
	
		public void setLocationDao(LocationDao dao){
			this.locationDao = dao;
		}
		
		// -------------------- For Testing purposes --------------------
}
