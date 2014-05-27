package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.ShowDao;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ShowService;

@Service
public class ShowServiceImpl implements ShowService {

	private static final Logger LOG = Logger.getLogger(ShowServiceImpl.class);
	
	@Autowired
	private ShowDao showDao;
	
	@Override
	public Show createShow(Show show) throws ServiceException {
		LOG.info("createShow called.");
		if(show.getId() != null)
			throw new ServiceException("ID must be null.");		
		try {
			return showDao.save(show);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public void deleteShow(Integer id) throws ServiceException {
		LOG.info("deleteShow called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			showDao.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Show> findShows(Date dateFrom, Date dateTo, Date timeFrom,
			Date timeTo, Integer priceInCentFrom, Integer priceInCentTo,
			String room, Integer locationID, Integer performanceID)
			throws ServiceException {
		LOG.info("findShows called.");
		try {	
			return showDao.findShows(dateFrom, dateTo, timeFrom, timeTo, priceInCentFrom, priceInCentTo, room, locationID, performanceID);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Show> getAllShows() throws ServiceException {
		LOG.info("getAllShows called.");
		try {
			return showDao.findAll();		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public int[] getMinMaxPriceInCent() throws ServiceException {
		LOG.info("getMinMaxPriceInCent called.");
		try {
			return showDao.getMinMaxPriceInCent();		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Show getShow(Integer id) throws ServiceException {
		LOG.info("getShow called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return showDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Show updateShow(Show show) throws ServiceException {
		LOG.info("updateShow called.");
		if(show.getId() == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return showDao.save(show);		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
