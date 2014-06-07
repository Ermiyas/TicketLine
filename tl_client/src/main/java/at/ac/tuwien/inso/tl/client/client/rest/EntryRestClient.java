package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
	public EntryDto createEntry(EntryDto entryDto, Integer basket_id)  throws ServiceException{
		LOG.info("createEntry called");
		//TODO
		throw new ServiceException("Not yet implemented");
	}

	@Override
	public List<KeyValuePairDto<EntryDto, Boolean>> getEntry(Integer basket_id) throws ServiceException {
		LOG.info("getEntry called");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/findByBasketId/{id}");	
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		List<KeyValuePairDto<EntryDto, Boolean>> result = null;
		try {
			ParameterizedTypeReference<List<KeyValuePairDto<EntryDto, Boolean>>> ref = new ParameterizedTypeReference<List<KeyValuePairDto<EntryDto, Boolean>>>() {};				
			ResponseEntity<List<KeyValuePairDto<EntryDto, Boolean>>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, basket_id);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find Entry: " + e.getMessage(), e);			
		}		
		return result;
	}

}
