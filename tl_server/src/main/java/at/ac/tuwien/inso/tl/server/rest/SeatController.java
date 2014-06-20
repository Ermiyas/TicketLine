package at.ac.tuwien.inso.tl.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.SeatService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/seats")
public class SeatController {	
	
private static final Logger LOG = Logger.getLogger(SeatController.class);
	
	@Autowired
	private SeatService service;

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@RequestMapping(value = "/ticket/{id}", method = RequestMethod.GET, produces = "application/json")
	public SeatDto findSeatByTicketId(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("findSeatByTicketId called.");

		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		Seat ret = service.findSeatByTicketId(id); 
		
		if(ret == null)
			return null;
		else		
			return EntityToDto.convert(ret);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createSeat(@Valid @RequestBody SeatDto seat) throws ServiceException {
		LOG.info("createSeat called.");

		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createSeat(DtoToEntity.convert(seat)).getId().toString());
		return msg;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteSeat(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("deleteSeat called.");
		service.deleteSeat(id);

	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<KeyValuePairDto<SeatDto, Boolean>> findSeats(@RequestParam(value="rowID", required = false) Integer rowID, @RequestParam(value="basketID", required = false) Integer basketID) throws ServiceException {
		LOG.info("findSeats called.");
		
		List<KeyValuePairDto<SeatDto, Boolean>> result = new ArrayList<KeyValuePairDto<SeatDto, Boolean>>(); 
		for(Map.Entry<Seat, Boolean> e: service.findSeats(rowID, basketID)) 
		{
			result.add(new KeyValuePairDto<SeatDto, Boolean>(EntityToDto.convert(e.getKey()), e.getValue()));
		}
		return result;
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<SeatDto> getAllSeats() throws ServiceException {
		LOG.info("getAllSeats called.");
		return EntityToDto.convertSeats(service.getAllSeats());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public SeatDto getSeat(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getSeat called.");

		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		Seat ret = service.getSeat(id); 
		
		if(ret == null)
			return null;
		else		
			return EntityToDto.convert(ret);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateSeat(@Valid @RequestBody SeatDto seat) throws ServiceException {
		LOG.info("updateSeat called.");

		service.updateSeat(DtoToEntity.convert(seat));
	}
}
