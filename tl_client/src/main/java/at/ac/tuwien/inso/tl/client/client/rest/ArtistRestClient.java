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

import at.ac.tuwien.inso.tl.client.client.ArtistService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.ArtistDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class ArtistRestClient implements ArtistService {

	private static final Logger LOG = Logger.getLogger(ArtistRestClient.class);

	@Autowired
	private RestClient restClient;

	@Override
	public Integer createArtist(ArtistDto artist) throws ServiceException {
		LOG.info("createArtist called.");
		if(artist == null)
			throw new ServiceException("artist must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/artists/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<ArtistDto> entity = new HttpEntity<ArtistDto>(artist, headers);
		
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
			throw new ServiceException("Could not create artist: " + e.getMessage(), e);
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
	public void deleteArtist(Integer id) throws ServiceException {
		LOG.info("deleteArtist called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/artists/delete/{id}");						
		
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
			throw new ServiceException("Could not delete artist: " + e.getMessage(), e);
		}				
	}

	@Override
	public List<ArtistDto> findArtists(String firstName, String lastName)
			throws ServiceException {				
		LOG.info("findArtists called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		
		StringBuilder urlBuilder = new StringBuilder("/artists/find");
		if(firstName != null || lastName != null)
			urlBuilder.append("?");		
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		if(firstName != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("firstName={firstName}");
			variables.put("firstName", firstName);
		}
		
		if(lastName != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("lastName={lastName}");
			variables.put("lastName", lastName);
		}
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());
		
	    HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");

		HttpEntity<String> entity = new HttpEntity<String>(headers);			

		List<ArtistDto> result = null;
		try {
			ParameterizedTypeReference<List<ArtistDto>> ref = new ParameterizedTypeReference<List<ArtistDto>>() {};				
			ResponseEntity<List<ArtistDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find artists: " + e.getMessage(), e);
		}

		return result;									
	}

	@Override
	public List<ArtistDto> getAllArtists() throws ServiceException {
		LOG.info("getAllArtists called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/artists/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ArtistDto> result = null;
		try {
			ParameterizedTypeReference<List<ArtistDto>> ref = new ParameterizedTypeReference<List<ArtistDto>>() {};
			ResponseEntity<List<ArtistDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve artists: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public ArtistDto getArtist(Integer id) throws ServiceException {
		LOG.info("getArtist called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/artists/{id}");						
		
		try {
			return restTemplate.getForObject(url, ArtistDto.class, id);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve artist: " + e.getMessage(), e);
		}				
	}

	@Override
	public void updateArtist(ArtistDto artist) throws ServiceException {
		LOG.info("updateArtist called.");
		if(artist == null)
			throw new ServiceException("artist must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/artists/update");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
			
		try {			
			restTemplate.put(url, artist);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not update artist: " + e.getMessage(), e);
		}			
	}		
}
