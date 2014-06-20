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
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EntryService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/entry")
public class EntryController {

	private static final Logger LOG = Logger.getLogger(EntryController.class);
	
	@Autowired
	private EntryService service;

	@RequestMapping(value = "/basket/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<EntryDto> findEntryByBasketId(@PathVariable String id) throws ServiceException {
		LOG.info("findEntryByBasketId() called");

		List<Entry> results = this.service.getListByBasketId(Integer.parseInt(id));
		return EntityToDto.convertEntries(results);
	}

	@RequestMapping(value = "/findByBasketId/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<KeyValuePairDto<EntryDto, Boolean>> getEntry(@PathVariable("id") Integer basket_id)
			throws ServiceException {
		LOG.info("getEntry called");
		List<KeyValuePairDto<EntryDto, Boolean>> retValue = new ArrayList<KeyValuePairDto<EntryDto, Boolean>>();
		for(Map.Entry<at.ac.tuwien.inso.tl.model.Entry, Boolean> e: service.getEntry(basket_id))
		{			
			retValue.add(new KeyValuePairDto<EntryDto, Boolean>(EntityToDto.convert(e.getKey()), e.getValue()));					
		}
		return retValue;
		
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
	public EntryDto createEntry(@Valid @RequestBody KeyValuePairDto<EntryDto, Integer> kvp) 
			throws ServiceException{
		LOG.info("createEntry called");
		return EntityToDto.convert(service.createEntry(DtoToEntity.convert(kvp.getKey()), kvp.getValue()));
	}
	

	@RequestMapping(value = "/createForArticle", method = RequestMethod.POST, produces = "application/json")
	public EntryDto createEntryForArticle(@Valid @RequestBody KeyValuePairDto<EntryDto, KeyValuePairDto<Integer, Integer>> kvp) 
			throws ServiceException{
		LOG.info("createEntryForArticle called");
		KeyValuePairDto<Integer, Integer> inner = kvp.getValue();
		return EntityToDto.convert(service.createEntryForArticle(DtoToEntity.convert(kvp.getKey()), inner.getKey(), inner.getValue()));

	}
	
	@RequestMapping(value = "/undoEntry/{id}", method = RequestMethod.DELETE)
	public void undoEntry(@PathVariable("id")Integer id) throws ServiceException{
		LOG.info("undoEntry called");
		service.undoEntry(id);
	}
	
	@RequestMapping(value = "/isReversible/{id}", method = RequestMethod.GET, produces = "application/json")
	public Boolean isReversible(@PathVariable("id")Integer id) throws ServiceException{
		LOG.info("isReversible called");

		return service.isReversible(id);
	}
	
	@RequestMapping(value = "/findEntryBySeat", method = RequestMethod.GET, produces = "application/json")
	public EntryDto findEntryBySeat(@RequestParam(value="seatID", required = true) Integer seat_id) throws ServiceException {
		LOG.info("findEntryBySeat called");
		return EntityToDto.convert(service.findEntryBySeat(seat_id));
	}
}
