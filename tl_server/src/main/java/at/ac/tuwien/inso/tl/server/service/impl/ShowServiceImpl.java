package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.ShowDao;
import at.ac.tuwien.inso.tl.dto.ShowContainerDto;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ShowService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@Service
public class ShowServiceImpl implements ShowService {

	private static final Logger LOG = Logger.getLogger(ShowServiceImpl.class);
	
	@Autowired
	private ShowDao showDao;
	
	@Override
	@Transactional
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
	@Transactional
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
	@Transactional
	public List<ShowContainerDto> findShows(Date dateFrom, Date dateTo, Date timeFrom,
			Date timeTo, Integer priceInCentFrom, Integer priceInCentTo,
			String room, Integer locationID, Integer performanceID)
			throws ServiceException {
		LOG.info("findShows called.");
		try {
			List<Show> shows = showDao.findShows(dateFrom, dateTo, timeFrom, timeTo, priceInCentFrom, priceInCentTo, room, locationID, performanceID);
			List<ShowContainerDto> containers = new ArrayList<ShowContainerDto>();
			
			for(Show s : shows) {
				ShowContainerDto container = new ShowContainerDto();
				container.setShowDto(EntityToDto.convert(s));
				container.setLocationDesc(s.getLocation().getDescription());
				container.setPerformanceDesc(s.getPerformance().getDescription());
				containers.add(container);
			}
			
			return containers;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public List<ShowContainerDto> getAllShows() throws ServiceException {
		LOG.info("getAllShows called.");
		try {
			List<Show> shows = showDao.findAll();
			List<ShowContainerDto> containers = new ArrayList<ShowContainerDto>();

			for(Show s : shows) {
				ShowContainerDto container = new ShowContainerDto();
				container.setShowDto(EntityToDto.convert(s));
				container.setLocationDesc(s.getLocation().getDescription());
				container.setPerformanceDesc(s.getPerformance().getDescription());
				containers.add(container);
			}
			
			return containers;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public int[] getMinMaxPriceInCent() throws ServiceException {
		LOG.info("getMinMaxPriceInCent called.");
		try {
			return showDao.getMinMaxPriceInCent();		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
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
	@Transactional
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

	@Override
	@Transactional
	public List<ShowContainerDto> getShowsForPerformance(Integer performace_id)
			throws ServiceException {
		
		LOG.info("getShowsForPerformance called");
		
		if(performace_id == null){
			throw new ServiceException("performance_id must not be null");
		}
		if(performace_id < 1){
			throw new ServiceException("performance_id must be greater than 0");
		}
		
		List<Show> shows = showDao.findByPerformance_id(performace_id);
		List<ShowContainerDto> containers = new ArrayList<ShowContainerDto>();

		for(Show s : shows) {
			ShowContainerDto container = new ShowContainerDto();
			container.setShowDto(EntityToDto.convert(s));
			container.setLocationDesc(s.getLocation().getDescription());
			container.setPerformanceDesc(s.getPerformance().getDescription());
			containers.add(container);
		}
		
		return containers;
	}

	@Override
	@Transactional
	public List<ShowContainerDto> getShowsForLocation(Integer location_id)
			throws ServiceException {
		
		LOG.info("getShowsForLocation called");
		
		if(location_id == null){
			throw new ServiceException("performance_id must not be null");
		}
		if(location_id < 1){
			throw new ServiceException("location_id must be greater than 0");
		}
		
		List<Show> shows = showDao.findByLocation_id(location_id);
		List<ShowContainerDto> containers = new ArrayList<ShowContainerDto>();

		for(Show s : shows) {
			ShowContainerDto container = new ShowContainerDto();
			container.setShowDto(EntityToDto.convert(s));
			container.setLocationDesc(s.getLocation().getDescription());
			container.setPerformanceDesc(s.getPerformance().getDescription());
			containers.add(container);
		}
		
		return containers;
	}

}
