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
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class BasketRestClient implements BasketService {
	
	private static final Logger LOG = Logger.getLogger(BasketRestClient.class);

	@Autowired
	private RestClient restClient;

	// TODO create(BasketDto basket), find(BasketDto basket), update(BasketDto basket), deleteById(Integer id), ...

	@Override
	public List<BasketDto> getAll() throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/basket/all");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
	
		LOG.info("Receiving all Baskets at " + url);

		List<BasketDto> baskets = null;
		
		try {
			ParameterizedTypeReference<List<BasketDto>> ref = new ParameterizedTypeReference<List<BasketDto>>() {};
			ResponseEntity<List<BasketDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			baskets = response.getBody();
		} catch (RestClientException e) {
			throw new ServiceException("Could not receive all Baskets: " + e.getMessage(), e);
		}
		for (BasketDto basket : baskets) {
			LOG.info(basket.toString());
		}
		return baskets;
	}

	@Override
	public BasketDto getById(Integer id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/basket/id/" + id);
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		LOG.info("Getting basket by ID at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		BasketDto result = null;
		
		try {
			result = restTemplate.getForObject(url, BasketDto.class, entity);
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve Basket by Id " + e.getMessage(), e);
		}
		LOG.info(result.toString());
		
		return result;
	}

	@Override
	public BasketDto createBasket()  throws ServiceException{
		LOG.info("createBasket called");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/basket/createEmptyBasket");
		
		try {
			return restTemplate.getForObject(url, BasketDto.class);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve basket: " + e.getMessage(), e);
		}
	}

	@Override
	public BasketDto getBasket(Integer id)  throws ServiceException{
		LOG.info("getBasket called.");
		
		if(id == null)
			throw new ServiceException("basket_id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/basket/getById/{id}");						
		
		try {
			return restTemplate.getForObject(url, BasketDto.class, id);										
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not get basket: " + e.getMessage(), e);
		}			
	}

	@Override
	public void undoBasket(Integer basket_id)  throws ServiceException{
		LOG.info("undoBasket called");
		if(basket_id == null)
			throw new ServiceException("basket_id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/basket/undoBasket/{id}");						
		
		try {
			restTemplate.delete(url, basket_id);										
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not undo basket: " + e.getMessage(), e);
		}		

	}

	@Override
	public void setCustomerForBasket(BasketDto basket, Integer customer_id)  throws ServiceException{
		LOG.info("setCustomerForBasket called");
		
		if(basket == null){
			throw new ServiceException("basket must not be null.");
		}
		if(basket.getId() == null){
			throw new ServiceException("basket_id must not be null.");
		}
		if(basket.getCreationdate() == null){
			throw new ServiceException("basket creatindate must not be null.");
		}
		if(customer_id == null){
			throw new ServiceException("customer_id must not be null.");
		}
		
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/basket/setCustomerForBasket");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		KeyValuePairDto<BasketDto, Integer> kvp = 
				new KeyValuePairDto<BasketDto, Integer>(basket, customer_id);

		HttpEntity<KeyValuePairDto<BasketDto, Integer>> entity = 
				new HttpEntity<KeyValuePairDto<BasketDto, Integer>>(kvp, headers);

		
		
		try {
			restTemplate.put(url, entity);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not undo basket: " + e.getMessage(), e);
		}		
		
		

	}

	@Override
	public List<BasketDto> findBasket(Integer basket_id, List<Integer> customers) throws ServiceException {
		LOG.info(" called");
		//TODO
		throw new ServiceException("Not yet implemented");
	}

}