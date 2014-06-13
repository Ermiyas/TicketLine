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

import at.ac.tuwien.inso.tl.client.client.EmployeeService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.EmployeeDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class EmployeeRestClient implements EmployeeService {
	private static final Logger LOG = Logger.getLogger(EmployeeRestClient.class);
	
	@Autowired
	private RestClient restClient;
	
	@Override
	public List<EmployeeDto> getAllEmployees() throws ServiceException {
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/users/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
	
		LOG.info("Retrieving all employees from " + url);

		List<EmployeeDto> allEmployees = null;
		try {
			ParameterizedTypeReference<List<EmployeeDto>> ref = new ParameterizedTypeReference<List<EmployeeDto>>() {};
			ResponseEntity<List<EmployeeDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			allEmployees = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve employees: " + e.getMessage(), e);
		}

		return allEmployees;
	}

	@Override
	public Integer createEmployee(EmployeeDto employee) throws ServiceException {
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/users/create");
		
		LOG.info("Create employee at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<EmployeeDto> entity = new HttpEntity<EmployeeDto>(employee, headers);
		
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
			throw new ServiceException("Could not create employee: " + e.getMessage(), e);
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
	public void updateEmployee(EmployeeDto employee) throws ServiceException {
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/users/update");
		
		LOG.info("Update employee at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<EmployeeDto> entity = new HttpEntity<EmployeeDto>(employee, headers);
		
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
			throw new ServiceException("Could not update employee: " + e.getMessage(), e);
		}
	}

}
