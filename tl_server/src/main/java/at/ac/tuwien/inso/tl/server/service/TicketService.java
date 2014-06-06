package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface TicketService {
	
	// TODO create(Ticket ticket), find(Ticket ticket), update(Ticket ticket), deleteById(Integer id), getAll, ...

	/**
	 * Returns the ticket object identified by the given id
	 * 
	 * @param id of the ticket object
	 * @return the ticket object
	 * @throws ServiceException
	 */
	public Ticket getById(Integer id) throws ServiceException;	
	
}