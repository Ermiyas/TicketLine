package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.AbstractMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.dao.SeatDao;
import at.ac.tuwien.inso.tl.dao.ShowDao;
import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.TicketService;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

	private static final Logger LOG = Logger.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private EntryDao entryDao;
	
	@Autowired
	private SeatDao seatDao;
	
	@Autowired
	private ShowDao showDao;
	
	@Override
	@Transactional
	public Ticket createTicket(Integer show_id, Integer seat_id,
			Integer entry_id) throws ServiceException {
		LOG.info("createTicket called");
		if((show_id == null && seat_id == null) ||
				(show_id != null && seat_id != null)){
			throw new ServiceException("Show_id OR Seat_id must be NULL, but not both");
		}
		
		Ticket t = new Ticket();		
		Seat seat = null;
		if(show_id != null)
		{
			Show s = showDao.findOne(show_id);
			if(s == null) throw new ServiceException("Show with id="+show_id+" not found");
			t.setShow(s);
		}
		else
		{
			seat = seatDao.findOne(seat_id);
			if(seat == null) throw new ServiceException("Seat with id="+seat_id+" not found");
			if(seat.getTicket() != null) throw new ServiceException("This seat is already reserved.");
		}
		
		t = ticketDao.save(t);
		
		if(seat_id != null){				
			t.setSeat(seat);
			seat.setTicket(t);
			seatDao.save(seat);
		}										
		
		if(entry_id != null){
			Entry e = entryDao.findOne(entry_id);
			if(e == null) throw new ServiceException("Entry with id="+entry_id+" not found");
			t.setEntry(e);			
			e.setTicket(t);
			entryDao.save(e);
		}
				
		return t;
	}

	@Transactional
	@Override
	public Map.Entry<Ticket, Boolean> getTicketForEntry(Integer entry_id)
			throws ServiceException {
		LOG.info("getTicketForEntry called");
		if(entry_id == null)
			throw new ServiceException("entry_id must not be null.");
		
		try {	
			return ticketDao.getTicketForEntry(entry_id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	@Modifying
	public void undoTicket(Integer id) throws ServiceException {
		LOG.info("undoTicket called");
		
		if(id == null){
			throw new ServiceException("ticket_id must not be null.");
		}
			
		
		Ticket t = ticketDao.findOne(id);
		if(t == null){
			throw new ServiceException("No Ticket found for ticket_id "+id);
		}
		
		try{
			Entry e = entryDao.findByTicket_id(id);
			if(e != null){
				if(e.getReceipt() == null){
					entryDao.delete(e);
					entryDao.flush();
				}
				else{
					e.setTicket(null);
					entryDao.save(e);
					entryDao.flush();
				}
			}
			Seat s = t.getSeat();
			if(s != null){
				s = seatDao.findOne(s.getId());
				s.setTicket(null);
				seatDao.saveAndFlush(s);
			}
			
			ticketDao.delete(t);
			ticketDao.flush();
			
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new ServiceException(e);
		}
	}

	@Override
	public Map.Entry<Performance, Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>> getPerformanceShowLocationRowSeatByTicket(
			Integer ticket_id) throws ServiceException {
		LOG.info("getPerformanceShowLocationRowSeatByTicket called");
		
		if(ticket_id == null)
			throw new ServiceException("ticket_id must not be null.");
		
		Ticket t = ticketDao.findOne(ticket_id);
		
		Map.Entry<Row, Seat> rowSeat = null;
		Map.Entry<Location, Map.Entry<Row, Seat>> locRowSeat = null;
		Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>> showLocRowSeat= null;
		Map.Entry<Performance, Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>> perfShowLocRowSeat = null;
		
		Seat seat = t.getSeat();
		Show show = null;
		if(seat != null){
			Row row = seat.getRow();
			rowSeat = new AbstractMap.SimpleEntry<Row, Seat>(row, seat);
			show = row.getShow();
		}
		else{
			show = t.getShow();
		}
		Location location = show.getLocation();
		Performance performance = show.getPerformance();
		
		locRowSeat = new AbstractMap.SimpleEntry<Location, Map.Entry<Row, Seat>>(location, rowSeat);
		showLocRowSeat = new AbstractMap.SimpleEntry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>(show,locRowSeat);
		perfShowLocRowSeat = 
				new AbstractMap.SimpleEntry<Performance, Map.Entry<Show, Map.Entry<Location, Map.Entry<Row, Seat>>>>(performance,showLocRowSeat);
		
		
		return perfShowLocRowSeat;
	}

}
