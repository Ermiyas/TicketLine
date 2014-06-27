package at.ac.tuwien.inso.tl.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

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
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.PerformanceService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/performances")
public class PerformanceController {

private static final Logger LOG = Logger.getLogger(PerformanceController.class);
	
	@Autowired
	private PerformanceService service;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createPerformance(@Valid @RequestBody PerformanceDto performance) throws ServiceException {			
		LOG.info("createPerformance called.");
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createPerformance(DtoToEntity.convert(performance)).getId().toString());
		return msg;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deletePerformance(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("deletePerformance called.");
		service.deletePerformance(id);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<KeyValuePairDto<PerformanceDto, Integer>> findPerformancesSortedBySales(
			@RequestParam(value="content", required = false) String content, @RequestParam(value="description", required = false) String description, @RequestParam(value="durationInMinutesFrom", required = false) Integer durationInMinutesFrom,
			@RequestParam(value="durationInMinutesTo", required = false) Integer durationInMinutesTo, @RequestParam(value="performanceType", required = false) String performanceType,
			@RequestParam(value="artistID", required = false) Integer artistID) throws ServiceException {
		LOG.info("findPerformancesSortedBySales called.");
		List<KeyValuePairDto<PerformanceDto, Integer>> retValue = new ArrayList<KeyValuePairDto<PerformanceDto, Integer>>();
		for(Entry<Performance, Integer> le: service.findPerformancesSortedBySales(content, description, durationInMinutesFrom, durationInMinutesTo, performanceType, artistID))
		{			
			retValue.add(new KeyValuePairDto<PerformanceDto, Integer>(EntityToDto.convert(le.getKey()), le.getValue()));					
		}
		return retValue;
	}

	@RequestMapping(value = "/getMinAndMaxDuration", method = RequestMethod.GET, produces = "application/json")
	public int[] getMinAndMaxDuration() throws ServiceException {
		LOG.info("getMinAndMaxDuration called.");
		return service.getMinAndMaxDuration();
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<PerformanceDto> getAllPerformances() throws ServiceException {
		LOG.info("getAllPerformances called.");
		return EntityToDto.convertPerformances(service.getAllPerformances());
	}

	@RequestMapping(value = "/getAllPerformanceTypes", method = RequestMethod.GET, produces = "application/json")
	public List<String> getAllPerformanceTypes() throws ServiceException {
		LOG.info("getAllPerformanceTypes called.");
		return service.getAllPerformanceTypes();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public PerformanceDto getPerformance(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getPerformance called.");
		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		Performance ret = service.getPerformance(id);
		
		if(ret == null)
			return null;
		else		
			return EntityToDto.convert(ret);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updatePerformance(@Valid @RequestBody PerformanceDto performance) throws ServiceException {			
		LOG.info("updatePerformance called.");
		service.updatePerformance(DtoToEntity.convert(performance));
	}
	
	@RequestMapping(value = "/findPerformanceByShow", method = RequestMethod.GET, produces = "application/json")
	public PerformanceDto findPerformanceByShow(@RequestParam(value="showID", required = true) Integer show_id) throws ServiceException {
		LOG.info("findPerformanceByShow called.");
		return EntityToDto.convert(service.findPerformanceByShow(show_id));
	}
	
}
