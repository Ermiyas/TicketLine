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
	
	@RequestMapping(value = "/create", method = RequestMethod.GET, produces = "application/json")
	public TicketDto createTicket (
			@RequestParam(value="show_id", required = false) Integer show_id, 
			@RequestParam(value="seat_id", required = false) Integer seat_id,
			@RequestParam(value="entry_id", required = false) Integer entry_id) 
					throws ServiceException{
		LOG.info("createTicket called.");
		LOG.debug("show_id="+show_id+"seat_id"+seat_id+"entry_id"+entry_id);
		
		return EntityToDto.convert(service.createTicket(show_id, seat_id, entry_id));
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
