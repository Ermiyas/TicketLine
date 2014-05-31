package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.PerformanceDao;
import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.PerformanceService;

@Service
public class PerformanceServiceImpl implements PerformanceService {

	private static final Logger LOG = Logger.getLogger(PerformanceServiceImpl.class);
	
	@Autowired
	private PerformanceDao performanceDao;
	
	@Override
	public Performance createPerformance(Performance performance) throws ServiceException {			
		LOG.info("createPerformance called.");
		if(performance.getId() != null)
			throw new ServiceException("ID must be null.");
		try {	
			return performanceDao.save(performance);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deletePerformance(Integer id) throws ServiceException {
		LOG.info("deletePerformance called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			performanceDao.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Entry<Performance, Integer>> findPerformancesSortedBySales(
			String content, String description, Integer durationInMinutesFrom,
			Integer durationInMinutesTo, String performanceType,
			Integer artistID) throws ServiceException {
		LOG.info("findPerformancesSortedBySales called.");
		try {	
			return performanceDao.findPerformancesSortedBySales(content, description, durationInMinutesFrom, durationInMinutesTo, performanceType, artistID);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public int[] getMinAndMaxDuration() throws ServiceException {
		LOG.info("getMinAndMaxDuration called.");
		try {	
			return performanceDao.getMinAndMaxDuration();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Performance> getAllPerformances() throws ServiceException {
		LOG.info("getAllPerformances called.");
		try {	
			return performanceDao.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<String> getAllPerformanceTypes() throws ServiceException {
		LOG.info("getAllPerformanceTypes called.");
		try {	
			return performanceDao.getAllPerformanceTypes();	
		} catch (Exception e) {
			throw new ServiceException(e);
		}	
	}

	@Override
	public Performance getPerformance(Integer id) throws ServiceException {
		LOG.info("getPerformance called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return performanceDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Performance updatePerformance(Performance performance) throws ServiceException {			
		LOG.info("updatePerformance called.");
		if(performance.getId() == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return performanceDao.save(performance);	
		} catch (Exception e) {
			throw new ServiceException(e);
		}	
	}

}
