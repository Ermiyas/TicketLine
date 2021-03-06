package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public Seat findSeatByTicketId(Integer id) throws ServiceException {
		LOG.info("findSeatsByTicketId called.");
		try {	
			return seatDao.findSeatByTicketId(id);
		} catch (Exception e) {
			LOG.error("An exception was raised during findSeatsByTicketId: ." + e.toString());
			throw new ServiceException(e);
		}
	}
	
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
	public List<Map.Entry<Seat, Boolean>> findSeats(Integer rowID, Integer basketID) throws ServiceException {
		LOG.info("findSeats called.");
		try {	
			List<Map.Entry<Seat, Boolean>> result = new ArrayList<Map.Entry<Seat, Boolean>>();
			for(Seat s: seatDao.findSeats(rowID))
			{				
				Boolean value = s.getTicket() == null;
				if(value == false && basketID != null)
				{
					if(s.getTicket().getEntry().getBasket().getId() == basketID)
					{
						value = null;
					}
				}
				result.add(new AbstractMap.SimpleEntry<Seat, Boolean>(s, value));
			}
			return result;
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
	
	@Override
	public List<Seat> getAllSeatForShow(int show_id) throws ServiceException {
			LOG.info("getAllSeatForShow called.");
			return seatDao.getAllSeatsForShow(show_id);
	}

	
	
	// -------------------- For Testing purposes --------------------
	
	
		public void setSeatDao(SeatDao dao){
			LOG.info("setSeatDao called.");
			this.seatDao = dao;
		}
		
   // -

}
