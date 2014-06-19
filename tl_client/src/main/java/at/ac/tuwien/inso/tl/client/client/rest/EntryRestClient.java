package at.ac.tuwien.inso.tl.client.client.rest;

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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class EntryRestClient implements EntryService {

	private static final Logger LOG = Logger.getLogger(EntryRestClient.class);

	@Autowired
	private RestClient restClient;
	
	
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

		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		KeyValuePairDto<EntryDto, Integer> kvp = 
				new KeyValuePairDto<EntryDto, Integer>(entryDto, basket_id);

		HttpEntity<KeyValuePairDto<EntryDto, Integer>> entity = 
				new HttpEntity<KeyValuePairDto<EntryDto, Integer>>(kvp, headers);
		
		
		try {
			return restTemplate.postForObject(url, entity, EntryDto.class);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not create performance: " + e.getMessage(), e);
		}
		
		
		
	}

	@Override
	public EntryDto createEntryForArticle(EntryDto entry, Integer articleID, Integer basket_id) throws ServiceException{
		LOG.info("createEntryForArticle called");
		if(entry == null){
			throw new ServiceException("entry must not be null");
		}
		else{
			if(entry.getBuyWithPoints() == null){
				throw new ServiceException("entry buywithpoints must not be null");
			}
			if(entry.getAmount() == null){
				throw new ServiceException("entry amount must not be null");
			}
		}
		if(articleID == null){
			throw new ServiceException("articleID must not be null");
		}
		if(basket_id == null){
			throw new ServiceException("basket_id must not be null");
		}
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/createForArticle");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		KeyValuePairDto<EntryDto, KeyValuePairDto<Integer, Integer>> kvp = 
				new KeyValuePairDto<EntryDto, KeyValuePairDto<Integer, Integer>>(entry, new KeyValuePairDto<Integer, Integer>(articleID, basket_id));

		HttpEntity<	KeyValuePairDto<EntryDto, KeyValuePairDto<Integer, Integer>>> entity = 
				new HttpEntity<	KeyValuePairDto<EntryDto, KeyValuePairDto<Integer, Integer>>>(kvp, headers);		
		
		try {
			return restTemplate.postForObject(url, entity, EntryDto.class);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not create entry: " + e.getMessage(), e);
			}					
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

	@Override
	public void undoEntry(Integer id) throws ServiceException {
		LOG.info("undoEntry called");
		if(id == null){
			throw new ServiceException("ID must not be null");
		}
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/undoEntry/{id}");	
		
		try {
			restTemplate.delete(url, id);										
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not delete ticket: " + e.getMessage(), e);
		}		
		
	}

	@Override
	public Boolean isReversible(Integer id) throws ServiceException{
		LOG.info("isReversible called");
		if(id == null){
			throw new ServiceException("ID must not be null");
		}
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/isReversible/{id}");	
		
		try {
			return restTemplate.getForObject(url, Boolean.class, id);										
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not get isReversible: " + e.getMessage(), e);
		}		
	}

}
