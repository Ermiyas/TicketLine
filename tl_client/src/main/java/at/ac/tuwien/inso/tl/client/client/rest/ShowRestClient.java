package at.ac.tuwien.inso.tl.client.client.rest;

import java.net.URI;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
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

import at.ac.tuwien.inso.tl.client.client.ShowService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;

@Component
public class ShowRestClient implements ShowService {

	private static final Logger LOG = Logger.getLogger(ShowRestClient.class);
	private static final Format dayFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final Format timeFormat = new SimpleDateFormat("HHmmss");

	@Autowired
	private RestClient restClient;
	
	@Override
	public Integer createShow(ShowDto show) throws ServiceException {
		LOG.info("createShow called.");
		if(show == null)
			throw new ServiceException("show must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<ShowDto> entity = new HttpEntity<ShowDto>(show, headers);
		
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
			throw new ServiceException("Could not create show: " + e.getMessage(), e);
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
	public void deleteShow(Integer id) throws ServiceException {
		LOG.info("deleteShow called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/delete/{id}");						
		
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
			throw new ServiceException("Could not delete show: " + e.getMessage(), e);
		}				

	}

	@Override
	public List<ShowDto> findShows(Date dateFrom, Date dateTo, Date timeFrom,
			Date timeTo, Integer priceInCentFrom, Integer priceInCentTo,
			String room, Integer locationID, Integer performanceID)
			throws ServiceException {
		LOG.info("findShows called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		
		StringBuilder urlBuilder = new StringBuilder("/shows/find");
		if(dateFrom != null || dateTo != null || timeFrom != null || timeTo != null || priceInCentFrom != null
				 || priceInCentTo != null || room != null || locationID != null || performanceID != null)
			urlBuilder.append("?");		
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		if(dateFrom != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("dateFrom={dateFrom}");
			variables.put("dateFrom", dayFormat.format(dateFrom));
		}
		
		if(dateTo != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("dateTo={dateTo}");
			variables.put("dateTo", dayFormat.format(dateTo));
		}
		
		if(timeFrom != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("timeFrom={timeFrom}");			 
			variables.put("timeFrom", timeFormat.format(timeFrom));
		}
		
		if(timeTo != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("timeTo={timeTo}");
			variables.put("timeTo", timeFormat.format(timeTo));
		}
		
		if(priceInCentFrom != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("priceInCentFrom={priceInCentFrom}");
			variables.put("priceInCentFrom", priceInCentFrom);
		}
		
		if(priceInCentTo != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("priceInCentTo={priceInCentTo}");
			variables.put("priceInCentTo", priceInCentTo);
		}
		
		if(room != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("room={room}");
			variables.put("room", room);
		}
		
		if(locationID != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("locationID={locationID}");
			variables.put("locationID", locationID);
		}
		
		if(performanceID != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("performanceID={performanceID}");
			variables.put("performanceID", performanceID);
		}
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ShowDto> result = null;
		try {
			ParameterizedTypeReference<List<ShowDto>> ref = new ParameterizedTypeReference<List<ShowDto>>() {};				
			ResponseEntity<List<ShowDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find shows: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public List<ShowDto> getAllShows() throws ServiceException {
		LOG.info("getAllShows called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ShowDto> result = null;
		try {
			ParameterizedTypeReference<List<ShowDto>> ref = new ParameterizedTypeReference<List<ShowDto>>() {};
			ResponseEntity<List<ShowDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve shows: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public int[] getMinMaxPriceInCent() throws ServiceException {
		LOG.info("getMinMaxPriceInCent called.");	
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/getMinMaxPriceInCent");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		int[] result = null;
		try {
			ParameterizedTypeReference<int[]> ref = new ParameterizedTypeReference<int[]>() {};
			ResponseEntity<int[]> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve min/max priceInCent: " + e.getMessage(), e);
		}

		return result;	
	}

	@Override
	public ShowDto getShow(Integer id) throws ServiceException {
		LOG.info("getShow called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/{id}");						
		
		try {
			return restTemplate.getForObject(url, ShowDto.class, id);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve show: " + e.getMessage(), e);
		}			
	}

	@Override
	public void updateShow(ShowDto show) throws ServiceException {
		LOG.info("updateShow called.");
		if(show == null)
			throw new ServiceException("show must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/update");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
			
		try {			
			restTemplate.put(url, show);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not update show: " + e.getMessage(), e);
		}		
	}

	@Override
	public List<ShowDto> getShowsForPerformance(Integer performace_id) throws ServiceException {
		LOG.info("getShowsForPerformance called.");
		if(performace_id == null)
			throw new ServiceException("performace_id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/getShowsForPerformance/{id}");						
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ShowDto> result = null;
		try {
			ParameterizedTypeReference<List<ShowDto>> ref = new ParameterizedTypeReference<List<ShowDto>>() {};
			ResponseEntity<List<ShowDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, performace_id);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve shows: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public List<ShowDto> getShowsForLocation(Integer location_id) throws ServiceException {
		LOG.info("getShowsForLocation called.");
		if(location_id == null)
			throw new ServiceException("location_id must not be null.");
		
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/shows/getShowsForLocation/{id}");						
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ShowDto> result = null;
		try {
			ParameterizedTypeReference<List<ShowDto>> ref = new ParameterizedTypeReference<List<ShowDto>>() {};
			ResponseEntity<List<ShowDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, location_id);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve shows: " + e.getMessage(), e);
		}

		return result;
	}
}
