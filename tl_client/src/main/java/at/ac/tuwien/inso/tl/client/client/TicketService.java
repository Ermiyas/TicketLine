package at.ac.tuwien.inso.tl.client.client;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public interface TicketService {
	
	// TODO create(TicketDto ticket), find(TicketDto ticket), update(TicketDto ticket), deleteById(Integer id), getAll(), ...
	
	public TicketDto getById(Integer id) throws ServiceException;
	
}