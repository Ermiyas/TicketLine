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

import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class CustomerRestClient implements CustomerService {
	
	private static final Logger LOG = Logger.getLogger(CustomerRestClient.class);

	@Autowired
	private RestClient restClient;

	@Override
	public List<CustomerDto> getAll() throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/customer/all");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
	
		LOG.info("Receiving all Customers at " + url);

		List<CustomerDto> customers = null;
		
		try {
			
			ParameterizedTypeReference<List<CustomerDto>> ref = new ParameterizedTypeReference<List<CustomerDto>>() {};
			ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			customers = response.getBody();

		} catch (RestClientException e) {
			
			throw new ServiceException("Could not receive all Customers: " + e.getMessage(), e);
			
		}

		return customers;
		
	}

	@Override
	public Integer create(CustomerDto customer) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/customer/create");
		
		LOG.info("Creating customer at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<CustomerDto> entity = new HttpEntity<CustomerDto>(customer, headers);
		
		MessageDto msg = null;
		
		try {
			
			msg = restTemplate.postForObject(url, entity, MessageDto.class);
			
		} catch (HttpStatusCodeException e) {
			
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				
				throw new ValidationException(errorMsg.getFieldErrors());
				
			} else {
				
				throw new ServiceException(errorMsg.getText());
				
			}
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not create Customer: " + e.getMessage(), e);
			
		}
		
		Integer id = null;
		
		try {
			
			id = Integer.valueOf(msg.getText());
			
		} catch (NumberFormatException e) {
			
			throw new ServiceException("Invalid ID: " + msg.getText());
			
		}
		
		return id;
		
	}

	@Override
	public List<CustomerDto> find(CustomerDto customer) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/customer/find");
		
		LOG.info("Finding customer at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<CustomerDto> entity = new HttpEntity<CustomerDto>(customer, headers);

		ParameterizedTypeReference<List<CustomerDto>> ref = new ParameterizedTypeReference<List<CustomerDto>>() {};
		ResponseEntity<List<CustomerDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, entity, ref);
		List<CustomerDto> results = response.getBody();
		
		return results;
		
	}

	@Override
	public CustomerDto getById(Integer id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/customer/id/" + id);
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		LOG.info("Getting customer by ID at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		CustomerDto result = null;
		
		try {
			
			result = restTemplate.getForObject(url, CustomerDto.class, entity);
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not retrieve Customer by Id " + e.getMessage(), e);
			
		}
		
		return result;
		
	}

	@Override
	public void update(CustomerDto customer) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/customer/update");
		
		LOG.info("Updating customer at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<CustomerDto> entity = new HttpEntity<CustomerDto>(customer, headers);
		
		try {
			
			restTemplate.postForObject(url, entity, MessageDto.class);
			
		} catch (HttpStatusCodeException e) {
			
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				
				throw new ValidationException(errorMsg.getFieldErrors());
				
			} else {
				
				throw new ServiceException(errorMsg.getText());
				
			}
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not update Customer: " + e.getMessage(), e);
			
		}
	}

	@Override
	public void deleteById(Integer id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/customer/delete/id/" + id);

		LOG.info("Deleting Customer with ID " + id + " at " + url);

		try {
			
			restTemplate.getForObject(url, String.class);
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not delete Customer: " + e.getMessage(), e);
			
		}
	}
}