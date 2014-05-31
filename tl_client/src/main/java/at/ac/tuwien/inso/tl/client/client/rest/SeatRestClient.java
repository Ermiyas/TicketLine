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

import at.ac.tuwien.inso.tl.client.client.SeatService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;

@Component
public class SeatRestClient implements SeatService {

	private static final Logger LOG = Logger.getLogger(SeatRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public Integer createSeat(SeatDto seat) throws ServiceException {
		LOG.info("createSeat called.");
		if(seat == null)
			throw new ServiceException("seat must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/seats/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<SeatDto> entity = new HttpEntity<SeatDto>(seat, headers);
		
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
			throw new ServiceException("Could not create seat: " + e.getMessage(), e);
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
	public void deleteSeat(Integer id) throws ServiceException {
		LOG.info("deleteSeat called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/seats/delete/{id}");						
		
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
			throw new ServiceException("Could not delete seat: " + e.getMessage(), e);
		}				

	}

	@Override
	public List<SeatDto> findSeats(Integer rowID) throws ServiceException {					
			LOG.info("findSeats called.");
			
			RestTemplate restTemplate = this.restClient.getRestTemplate();
			
			StringBuilder urlBuilder = new StringBuilder("/seats/find");
			if(rowID != null)
				urlBuilder.append("?");		
			
			Map<String, Object> variables = new HashMap<String, Object>();
			
			boolean isFirst = true;		
			if(rowID != null)
			{
				if(isFirst)
					isFirst = false;
				else
					urlBuilder.append("&");
					
				urlBuilder.append("rowID={rowID}");
				variables.put("rowID", rowID);
			}					
			
			String url = this.restClient.createServiceUrl(urlBuilder.toString());

			HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

			List<SeatDto> result = null;
			try {
				ParameterizedTypeReference<List<SeatDto>> ref = new ParameterizedTypeReference<List<SeatDto>>() {};				
				ResponseEntity<List<SeatDto>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
				result = response.getBody();

			} catch (RestClientException e) {
				throw new ServiceException("Could not find seats: " + e.getMessage(), e);
			}

			return result;	
	}

	@Override
	public List<SeatDto> getAllSeats() throws ServiceException {
		LOG.info("getAllSeats called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/seats/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<SeatDto> result = null;
		try {
			ParameterizedTypeReference<List<SeatDto>> ref = new ParameterizedTypeReference<List<SeatDto>>() {};
			ResponseEntity<List<SeatDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve seats: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public SeatDto getSeat(Integer id) throws ServiceException {
		LOG.info("getSeat called.");
		
		if(id == null)
			throw new ServiceException("id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/seats/{id}");						
		
		try {
			return restTemplate.getForObject(url, SeatDto.class, id);									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve seat: " + e.getMessage(), e);
		}				
	}

	@Override
	public void updateSeat(SeatDto seat) throws ServiceException {
		LOG.info("updateSeat called.");
		if(seat == null)
			throw new ServiceException("seat must not be null.");
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/seats/update");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		
			
		try {			
			restTemplate.put(url, seat);
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not update seat: " + e.getMessage(), e);
		}		

	}

}
