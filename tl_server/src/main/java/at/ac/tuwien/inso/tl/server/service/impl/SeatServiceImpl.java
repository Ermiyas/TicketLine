package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.SeatDao;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.SeatService;

@Service
public class SeatServiceImpl implements SeatService {

	private static final Logger LOG = Logger.getLogger(SeatServiceImpl.class);
	
	@Autowired
	private SeatDao seatDao;
	
	@Override
	public Seat createSeat(Seat seat) throws ServiceException {
		LOG.info("createSeat called.");
		if(seat.getId() != null)
			throw new ServiceException("ID must be null.");		
		try {
			return seatDao.save(seat);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}

	@Override
	public void deleteSeat(Integer id) throws ServiceException {
		LOG.info("deleteSeat called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			seatDao.delete(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public List<Seat> findSeats(Integer rowID) throws ServiceException {
		LOG.info("findSeats called.");
		try {	
			return seatDao.findSeats(rowID);
		} catch (Exception e) {
			LOG.error("An exception was raised during findSeats: ." + e.toString());
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Seat> getAllSeats() throws ServiceException {
		LOG.info("getAllSeats called.");
		try {
			return seatDao.findAll();		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Seat getSeat(Integer id) throws ServiceException {
		LOG.info("getSeat called.");
		if(id == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return seatDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Seat updateSeat(Seat seat) throws ServiceException {
		LOG.info("updateSeat called.");
		if(seat.getId() == null)
			throw new ServiceException("ID must not be null.");
		try {	
			return seatDao.save(seat);		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	// -------------------- For Testing purposes --------------------
	
		public void setSeatDao(SeatDao dao){
			LOG.info("setSeatDao called.");
			this.seatDao = dao;
		}
		
   // -

}
