package at.ac.tuwien.inso.tl.server.rest;

import java.util.Date;
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
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ShowService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/shows")
public class ShowController {	
	private static final Logger LOG = Logger.getLogger(ShowController.class);

	@Autowired
	private ShowService service;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createShow(@Valid @RequestBody ShowDto show) throws ServiceException {
		LOG.info("createShow called.");

		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createShow(DtoToEntity.convert(show)).getId().toString());
		return msg;	
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public void deleteShow(@RequestParam(value="id") Integer id) throws ServiceException {
		LOG.info("deleteShow called.");
		service.deleteShow(id);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<ShowDto> findShows(@RequestParam(value="dateFrom") Date dateFrom, @RequestParam(value="dateTo") Date dateTo, @RequestParam(value="timeFrom") Date timeFrom,
			@RequestParam(value="timeTo") Date timeTo, @RequestParam(value="priceInCentFrom") Integer priceInCentFrom, @RequestParam(value="priceInCentTo") Integer priceInCentTo,
			@RequestParam(value="room") String room, @RequestParam(value="locationID") Integer locationID, @RequestParam(value="performanceID") Integer performanceID)
			throws ServiceException {
		LOG.info("findShows called.");
		return EntityToDto.convertShows(service.findShows(dateFrom, dateTo, timeFrom, timeTo, priceInCentFrom, priceInCentTo, room, locationID, performanceID));
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ShowDto> getAllShows() throws ServiceException {
		LOG.info("getAllShows called.");
		return EntityToDto.convertShows(service.getAllShows());
	}

	@RequestMapping(value = "/getMinMaxPriceInCent", method = RequestMethod.GET, produces = "application/json")
	public int[] getMinMaxPriceInCent() throws ServiceException {
		LOG.info("getMinMaxPriceInCent called.");
		return service.getMinMaxPriceInCent();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public ShowDto getShow(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getShow called.");

		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		return EntityToDto.convert(service.getShow(id));
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateShow(@Valid @RequestBody ShowDto show) throws ServiceException {
		LOG.info("updateShow called.");
		service.updateShow(DtoToEntity.convert(show));
	}
}
