package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.TicketDao;
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

	// TODO create(Ticket ticket), find(Ticket ticket), update(Ticket ticket), deleteById(Integer id), getAll(), ...
	
	@Override
	public Ticket getById(Integer id) throws ServiceException {
		LOG.info("");
		try {
			return ticketDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public Ticket createTicket(Integer show_id, Integer seat_id,
			Integer entry_id) throws ServiceException {
		LOG.info("");
		if((show_id == null && seat_id == null) ||
				(show_id != null && seat_id != null)){
			throw new ServiceException("Show_id OR Seat_id must be NULL, but not both");
		}
		try {	
			return ticketDao.createTicket(show_id,seat_id,entry_id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Entry<Ticket, Boolean> getTicketForEntry(Integer entry_id)
			throws ServiceException {
		LOG.info("");
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
	public void undoTicket(Integer id) throws ServiceException {
		LOG.info("");
		if(id == null)
			throw new ServiceException("ticket_id must not be null.");
		
		try {	
			ticketDao.undoTicket(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

	@Override
	public Entry<Performance, Entry<Show, Entry<Location, Entry<Row, Seat>>>> getPerformanceShowLocationRowSeatByTicket(
			Integer ticket_id) throws ServiceException {
		if(ticket_id == null)
			throw new ServiceException("ticket_id must not be null.");
		
		Ticket t = ticketDao.findOne(ticket_id);
		if(t.getSeat() != null){
			// TODO Throw not implemented yet exception!!!
		}
		
		// TODO Throw not implemented yet exception!!!
		return null;
	}

	// Zum Testen.
	public void setTicketDao(TicketDao dao) {
		LOG.info("");
		this.ticketDao = dao;
	}

}