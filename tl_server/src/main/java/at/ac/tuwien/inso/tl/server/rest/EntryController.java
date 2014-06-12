package at.ac.tuwien.inso.tl.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EntryService;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/entry")
public class EntryController {
	
	private static final Logger LOG = Logger.getLogger(EntryController.class);
	
	@Autowired
	private EntryService service;

	// TODO ev. createEntry, findEntry, updateEntry, deleteEntryById, findEntryById, getAll, ...
	
	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
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
}