package at.ac.tuwien.inso.tl.client.client.rest;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

@Component
public class EntryRestClient implements EntryService {
	
	private static final Logger LOG = Logger.getLogger(EntryRestClient.class);

	@Autowired
	private RestClient restClient;

	// TODO ev. create(EntryDto entry), find(EntryDto entry), getById(Integer id), update(EntryDto entry), deleteById(Integer id), getAll(), ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public List<EntryDto> getList(BasketDto basket) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/basket/" + basket.getId());
		
		LOG.info("Getting entries by Basket-ID at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		ParameterizedTypeReference<List<EntryDto>> ref = new ParameterizedTypeReference<List<EntryDto>>() {};
		ResponseEntity<List<EntryDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
		List<EntryDto> results = response.getBody();
		for (EntryDto entry : results) {
			LOG.info(entry.toString());
		}
		
		return results;
	}

	@Override
	public EntryDto createEntry(EntryDto entryDto, Integer basket_id)  throws ServiceException{
		LOG.info("createEntry called");
		if(entryDto == null){
			throw new ServiceException("entry must not be null");
		}
		else{
			if(entryDto.getBuyWithPoints() == null){
				throw new ServiceException("entry buywithpoints must not be null");
			}
			if(entryDto.getAmount() == null){
				throw new ServiceException("entry amount must not be null");
			}
		}
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		//TODO
		throw new ServiceException("Not yet implemented");
	}

	@Override
	public List<KeyValuePairDto<EntryDto, Boolean>> getEntry(Integer basket_id) throws ServiceException {
		LOG.info("getEntry called");
		
		if(basket_id == null){
			throw new ServiceException("Basket_id must not be null");
		}
		
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