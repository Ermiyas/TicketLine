package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

@Component
public class EntryRestClient implements EntryService {

	private static final Logger LOG = Logger.getLogger(EntryRestClient.class);

	@Autowired
	private RestClient restClient;
	
	
	@Override
	public EntryDto createEntry(Integer basket_id)  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");
	}

	@Override
	public List<KeyValuePairDto<EntryDto, Boolean>> getEntry(Integer basket_id) throws ServiceException {
		//TODO
		throw new ServiceException("Not yet implemented");
	}

}
