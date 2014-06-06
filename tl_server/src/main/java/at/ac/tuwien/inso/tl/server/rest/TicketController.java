package at.ac.tuwien.inso.tl.server.rest;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;
import at.ac.tuwien.inso.tl.model.Ticket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.TicketService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/ticket")
public class TicketController {
	
	private static final Logger LOG = Logger.getLogger(TicketController.class);
	
	@Autowired
	private TicketService service;

	// TODO createTicket, findTicket, updateTicket, deleteTicketById, getAll
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public TicketDto findTicketById(@PathVariable String id) throws ServiceException {
		LOG.info("findTicketById() called");

		return EntityToDto.convert(this.service.getById(Integer.parseInt(id)));
	}
	
	@RequestMapping(value = "/ticketForEntry/{entry_id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody KeyValuePairDto<TicketDto, Boolean> getTicketForEntry(@PathVariable("entry_id") Integer entry_id) 
			throws ServiceException{
		Map.Entry<Ticket, Boolean> e = service.getTicketForEntry(entry_id);
		
		return new KeyValuePairDto<TicketDto, Boolean>(EntityToDto.convert(e.getKey()), e.getValue());
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void undoTicket(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("undoTicket called.");
		service.undoTicket(id);
	}
}