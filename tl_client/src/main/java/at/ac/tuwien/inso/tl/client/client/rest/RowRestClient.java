package at.ac.tuwien.inso.tl.client.client.rest;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import at.ac.tuwien.inso.tl.client.client.RowService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.RowDto;

@Component
public class RowRestClient implements RowService {

	private static final Logger LOG = Logger.getLogger(RowRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public Integer createRow(RowDto row) throws ServiceException {
		LOG.info("createRow called.");
		if(row == null)
			throw new ServiceException("row must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/rows/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<RowDto> entity = new HttpEntity<RowDto>(row, headers);
		
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
			throw new ServiceException("Could not create row: " + e.getMessage(), e);
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
	public void deleteRow(Integer id) throws ServiceException {
	LOG.info("deleteRow called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/rows/delete/{id}");						
		
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
			throw new ServiceException("Could not delete row: " + e.getMessage(), e);
		}				

	}

	@Override
	public List<RowDto> findRows(Integer showID) throws ServiceException {
		LOG.info("findRows called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		
		StringBuilder urlBuilder = new StringBuilder("/rows/find");
		if(showID != null)
			urlBuilder.append("?");		
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		if(showID != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("showID={showID}");
			variables.put("showID", showID);
		}				
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<RowDto> result = null;
		try {
			ParameterizedTypeReference<List<RowDto>> ref = new ParameterizedTypeReference<List<RowDto>>() {};				
			ResponseEntity<List<RowDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find rows: " + e.getMessage(), e);
		}

		return result;		
	}

	@Override
	public List<RowDto> getAllRows() throws ServiceException {
		LOG.info("getAllRows called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/rows/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<RowDto> result = null;
		try {
			ParameterizedTypeReference<List<RowDto>> ref = new ParameterizedTypeReference<List<RowDto>>() {};
			ResponseEntity<List<RowDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve rows: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public RowDto getRow(Integer id) throws ServiceException {
		LOG.info("getRow called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/rows/{id}");						
		
		try {
			return restTemplate.getForObject(url, RowDto.class, id);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve row: " + e.getMessage(), e);
		}			
	}

	@Override
	public void updateRow(RowDto row) throws ServiceException {
		LOG.info("updateRow called.");
		if(row == null)
			throw new ServiceException("row must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/rows/update");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
			
		try {			
			restTemplate.put(url, row);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not update row: " + e.getMessage(), e);
		}			

	}

}
