package at.ac.tuwien.inso.tl.server.rest;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.model.Show;
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

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteShow(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("deleteShow called.");
		service.deleteShow(id);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<ContainerDto> findShows(@RequestParam(value="dateFrom", required = false) @DateTimeFormat(iso=ISO.DATE) Date dateFrom, @RequestParam(value="dateTo", required = false) @DateTimeFormat(iso=ISO.DATE) Date dateTo, @RequestParam(value="timeFrom", required = false) @DateTimeFormat(pattern="HHmmss") Date timeFrom,
			@RequestParam(value="timeTo", required = false) @DateTimeFormat(pattern="HHmmss") Date timeTo, @RequestParam(value="priceInCentFrom", required = false) Integer priceInCentFrom, @RequestParam(value="priceInCentTo", required = false) Integer priceInCentTo,
			@RequestParam(value="room", required = false) String room, @RequestParam(value="locationID", required = false) Integer locationID, @RequestParam(value="performanceID", required = false) Integer performanceID)
			throws ServiceException {
		LOG.info("findShows called.");
		return service.findShows(dateFrom, dateTo, timeFrom, timeTo, priceInCentFrom, priceInCentTo, room, locationID, performanceID);
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<ContainerDto> getAllShows() throws ServiceException {
		LOG.info("getAllShows called.");
		return service.getAllShows();
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
		
		Show ret = service.getShow(id);
		
		if(ret == null)
			return null;
		else		
			return EntityToDto.convert(ret);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateShow(@Valid @RequestBody ShowDto show) throws ServiceException {
		LOG.info("updateShow called.");
		service.updateShow(DtoToEntity.convert(show));
	}
	
	@RequestMapping(value = "/getShowsForPerformance/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<ContainerDto> getShowsForPerformance(@PathVariable("id") Integer performace_id) throws ServiceException {
		LOG.info("getShowsForPerformance called");
		return service.getShowsForPerformance(performace_id);
	}
	
	@RequestMapping(value = "/getShowsForLocation/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<ContainerDto> getShowsForLocation(@PathVariable("id") Integer location_id) throws ServiceException {
		LOG.info("getShowsForLocation called");
		return service.getShowsForLocation(location_id);		
	}
	
}
