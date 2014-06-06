package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.EntryDto;
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

	// TODO createEntry, findEntry, updateEntry, deleteEntryById, findEntryById, getAll, ...
	
	@RequestMapping(value = "/basket/{id}", method = RequestMethod.GET, produces = "application/json")
	public List<EntryDto> findEntryByBasketId(@PathVariable String id) throws ServiceException {
		LOG.info("findEntryByBasketId() called");

		List<Entry> results = this.service.getListByBasketId(Integer.parseInt(id));
		return EntityToDto.convertEntries(results);
	}

}