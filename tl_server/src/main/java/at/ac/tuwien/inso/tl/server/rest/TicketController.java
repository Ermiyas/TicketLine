package at.ac.tuwien.inso.tl.server.rest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.TicketDto;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.TicketService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {
	
	private static final Logger LOG = Logger.getLogger(Ticket.class);
	
	@Autowired
	private TicketService ticketService;

	// TODO createTicket, findTicket, updateTicket, deleteTicketById, getAll
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public TicketDto findTicketById(@PathVariable String id) throws ServiceException {
		LOG.info("findTicketById() called");

		return EntityToDto.convert(this.ticketService.getById(Integer.parseInt(id)));
	}
	
}