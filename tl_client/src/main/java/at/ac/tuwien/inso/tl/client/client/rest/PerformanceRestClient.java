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

import at.ac.tuwien.inso.tl.client.client.PerformanceService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;

@Component
public class PerformanceRestClient implements PerformanceService {

	private static final Logger LOG = Logger.getLogger(PerformanceRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public Integer createPerformance(PerformanceDto performance)
			throws ServiceException {
		LOG.info("createPerformance called.");
		if(performance == null)
			throw new ServiceException("performance must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<PerformanceDto> entity = new HttpEntity<PerformanceDto>(performance, headers);
		
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
			throw new ServiceException("Could not create performance: " + e.getMessage(), e);
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
	public void deletePerformance(Integer id) throws ServiceException {
		LOG.info("deletePerformance called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/delete/{id}");						
		
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
			throw new ServiceException("Could not delete performance: " + e.getMessage(), e);
		}				
		
	}

	@Override
	public List<KeyValuePairDto<PerformanceDto, Integer>> findPerformancesSortedBySales(
			String content, String description, Integer durationInMinutesFrom,
			Integer durationInMinutesTo, String performanceType,
			Integer artistID) throws ServiceException {
		LOG.info("findPerformancesSortedBySales called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		
		StringBuilder urlBuilder = new StringBuilder("/performances/find");
		if(content != null || description != null || durationInMinutesFrom != null || durationInMinutesTo != null
				|| performanceType != null || artistID != null)
			urlBuilder.append("?");		
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		if(content != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("content={content}");
			variables.put("content", content);
		}
		
		if(description != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("description={description}");
			variables.put("description", description);
		}
		
		if(durationInMinutesFrom != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("durationInMinutesFrom={durationInMinutesFrom}");
			variables.put("durationInMinutesFrom", durationInMinutesFrom);
		}
		
		if(durationInMinutesTo != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("durationInMinutesTo={durationInMinutesTo}");
			variables.put("durationInMinutesTo", durationInMinutesTo);
		}
		
		if(performanceType != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("performanceType={performanceType}");
			variables.put("performanceType", performanceType);
		}
		
		if(artistID != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("artistID={artistID}");
			variables.put("artistID", artistID);
		}
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<KeyValuePairDto<PerformanceDto, Integer>> result = null;
		try {
			ParameterizedTypeReference<List<KeyValuePairDto<PerformanceDto, Integer>>> ref = new ParameterizedTypeReference<List<KeyValuePairDto<PerformanceDto, Integer>>>() {};				
			ResponseEntity<List<KeyValuePairDto<PerformanceDto, Integer>>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find performances: " + e.getMessage(), e);			
		}		
		return result;		
	}

	@Override
	public int[] getMinAndMaxDuration() throws ServiceException {
		LOG.info("getMinAndMaxDuration called.");	
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/getMinAndMaxDuration");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		int[] result = null;
		try {
			ParameterizedTypeReference<int[]> ref = new ParameterizedTypeReference<int[]>() {};
			ResponseEntity<int[]> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve min/max duration: " + e.getMessage(), e);
		}

		return result;						
	}

	@Override
	public List<PerformanceDto> getAllPerformances() throws ServiceException {
		LOG.info("getAllPerformances called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<PerformanceDto> result = null;
		try {
			ParameterizedTypeReference<List<PerformanceDto>> ref = new ParameterizedTypeReference<List<PerformanceDto>>() {};
			ResponseEntity<List<PerformanceDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve performances: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public List<String> getAllPerformanceTypes() throws ServiceException {
		LOG.info("getAllPerformanceTypes called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/getAllPerformanceTypes");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<String> result = null;
		try {
			ParameterizedTypeReference<List<String>> ref = new ParameterizedTypeReference<List<String>>() {};
			ResponseEntity<List<String>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve performances: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public PerformanceDto getPerformance(Integer id) throws ServiceException {
	LOG.info("getPerformance called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/{id}");						
		
		try {
			return restTemplate.getForObject(url, PerformanceDto.class, id);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve performance: " + e.getMessage(), e);
		}			
	}

	@Override
	public void updatePerformance(PerformanceDto performance)
			throws ServiceException {
		LOG.info("updatePerformance called.");
		if(performance == null)
			throw new ServiceException("performance must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/performances/update");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
			
		try {			
			restTemplate.put(url, performance);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not update performance: " + e.getMessage(), e);
		}			
		
	}

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public PerformanceDto getPerformance(ShowDto show) throws ServiceException {
		LOG.info("getPerformance of Show is called.");
		
		if(show == null)
			throw new ServiceException("Show must not be null.");

		Integer id = show.getPerformanceId();
		if(id == null)
			return null;

		return getPerformance(id);
	}
}
