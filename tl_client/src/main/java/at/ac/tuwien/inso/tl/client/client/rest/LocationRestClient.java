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

import at.ac.tuwien.inso.tl.client.client.LocationService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class LocationRestClient implements LocationService {

	private static final Logger LOG = Logger.getLogger(LocationRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public Integer createLocation(LocationDto location) throws ServiceException {
		LOG.info("LocationDto called.");
		if(location == null)
			throw new ServiceException("location must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/locations/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<LocationDto> entity = new HttpEntity<LocationDto>(location, headers);
		
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
			throw new ServiceException("Could not create location: " + e.getMessage(), e);
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
	public void deleteLocation(Integer id) throws ServiceException {
		LOG.info("deleteLocation called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/locations/delete/{id}");						
		
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
			throw new ServiceException("Could not delete location: " + e.getMessage(), e);
		}				
	}

	@Override
	public List<LocationDto> findLocations(String city, String country,
			String description, String postalCode, String street)
			throws ServiceException {
		LOG.info("findLocations called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		
		StringBuilder urlBuilder = new StringBuilder("/locations/find");
		if(city != null || country != null || description != null || postalCode != null || street != null)
			urlBuilder.append("?");		
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		if(city != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("city={city}");
			variables.put("city", city);
		}
		
		if(country != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("country={country}");
			variables.put("country", country);
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
		
		if(postalCode != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("postalCode={postalCode}");
			variables.put("postalCode", postalCode);
		}
		
		if(street != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("street={street}");
			variables.put("street", street);
		}
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());

		HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);			

		List<LocationDto> result = null;
		try {
			ParameterizedTypeReference<List<LocationDto>> ref = new ParameterizedTypeReference<List<LocationDto>>() {};				
			ResponseEntity<List<LocationDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find locations: " + e.getMessage(), e);
		}

		return result;				
	}

	@Override
	public List<LocationDto> getAllLocations() throws ServiceException {
		LOG.info("getAllLocations called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/locations/");

		HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		
		HttpEntity<String> entity = new HttpEntity<String>(headers);			

		List<LocationDto> result = null;
		try {
			ParameterizedTypeReference<List<LocationDto>> ref = new ParameterizedTypeReference<List<LocationDto>>() {};
			ResponseEntity<List<LocationDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve locations: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public LocationDto getLocation(Integer id) throws ServiceException {
		LOG.info("getLocation called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/locations/{id}");						
		
		try {
			return restTemplate.getForObject(url, LocationDto.class, id);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve location: " + e.getMessage(), e);
		}				
	}

	@Override
	public void updateLocation(LocationDto getLocation) throws ServiceException {
		LOG.info("LocationDto called.");
		if(getLocation == null)
			throw new ServiceException("location must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/locations/update");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
			
		try {			
			restTemplate.put(url, getLocation);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not update location: " + e.getMessage(), e);
		}			
	}

	@Override
	public LocationDto findLocationByShowID(int showID) throws ServiceException {
		LOG.info("findLocationByShowID called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();				           
		
		StringBuilder urlBuilder = new StringBuilder("/locations/findLocationByShowID?");			
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		
		if(isFirst)
			isFirst = false;
		else
			urlBuilder.append("&");
			
		urlBuilder.append("showID={showID}");
		variables.put("showID", showID);				
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());

        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        
		HttpEntity<String> entity = new HttpEntity<String>(headers);			

		LocationDto result = null;
		try {
			ParameterizedTypeReference<LocationDto> ref = new ParameterizedTypeReference<LocationDto>() {};				
			ResponseEntity<LocationDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find location: " + e.getMessage(), e);
		}

		return result;	
	}	
}
