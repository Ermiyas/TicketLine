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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.LocationService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/locations")
public class LocationController {

private static final Logger LOG = Logger.getLogger(LocationController.class);
	
	@Autowired
	private LocationService service;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createLocation(@Valid @RequestBody LocationDto location) throws ServiceException {
		LOG.info("createLocation called.");

		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createLocation(DtoToEntity.convert(location)).getId().toString());
		return msg;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteLocation(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("deleteLocation called.");
		service.deleteLocation(id);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<LocationDto> findLocations(@RequestParam(value="city", required = false) String city, @RequestParam(value=" country", required = false) String country,
			@RequestParam(value="description", required = false) String description, @RequestParam(value="postalCode", required = false) String postalCode, @RequestParam(value="street", required = false) String street)
			throws ServiceException {
		LOG.info("findLocations called.");
		return EntityToDto.convertLocations(service.findLocations(city, country, description, postalCode, street));
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<LocationDto> getAllLocations() throws ServiceException {
		LOG.info("getAllLocations called.");
		return EntityToDto.convertLocations(service.getAllLocations());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public LocationDto getLocation(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getLocation called.");
		
		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		Location ret = service.getLocation(id);		
		
		if(ret == null)
			return null;
		else		
			return EntityToDto.convert(ret);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateLocation(@Valid @RequestBody LocationDto location) throws ServiceException {
		LOG.info("updateLocation called.");
		service.updateLocation(DtoToEntity.convert(location));
	}
	
}
