package at.ac.tuwien.inso.tl.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.TicketDao;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.TicketService;

@Service
public class TicketServiceImpl implements TicketService {
	
	@Autowired
	private TicketDao ticketDao;

	// TODO create(Ticket ticket), find(Ticket ticket), update(Ticket ticket), deleteById(Integer id), getAll(), ...
	
	@Override
	public Ticket getById(Integer id) throws ServiceException {
		try {
			return ticketDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// Zum Testen.
	public void setTicketDao(TicketDao dao) {
		this.ticketDao = dao;
	}

}