package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.PerformanceDao;
import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {

	private static final Logger LOG = Logger.getLogger(TicketServiceImpl.class);
	
	@Autowired
	private TicketDao ticketDao;
	
	@Override
	public Ticket createTicket(Integer show_id, Integer seat_id,
			Integer entry_id) throws ServiceException {
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
		if(entry_id == null)
			throw new ServiceException("entry_id must not be null.");
		
		try {	
			return ticketDao.getTicketForEntry(entry_id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void undoTicket(Integer id) throws ServiceException {
		if(id == null)
			throw new ServiceException("ticket_id must not be null.");
		
		try {	
			ticketDao.undoTicket(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}

	}

}
