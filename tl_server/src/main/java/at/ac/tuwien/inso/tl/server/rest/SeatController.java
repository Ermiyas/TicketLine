package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.SeatDto;
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

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createSeat(@Valid @RequestBody SeatDto seat) throws ServiceException {
		LOG.info("createSeat called.");

		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createSeat(DtoToEntity.convert(seat)).getId().toString());
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public void deleteSeat(@RequestParam(value="id") Integer id) throws ServiceException {
		LOG.info("deleteSeat called.");
		service.deleteSeat(id);

	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<SeatDto> findSeats(@RequestParam(value="rowID") Integer rowID) throws ServiceException {
		LOG.info("findSeats called.");
		return EntityToDto.convertSeats(service.findSeats(rowID));
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
		
		return EntityToDto.convert(service.getSeat(id));
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateSeat(@Valid @RequestBody SeatDto seat) throws ServiceException {
		LOG.info("updateSeat called.");

		service.updateSeat(DtoToEntity.convert(seat));
	}
}
