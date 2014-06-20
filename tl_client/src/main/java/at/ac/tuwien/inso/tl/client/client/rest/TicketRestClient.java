package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.Collections;
import java.util.HashMap;
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

import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

@Component
public class TicketRestClient implements TicketService {

	private static final Logger LOG = Logger.getLogger(TicketRestClient.class);

	@Autowired
	private RestClient restClient;

	// TODO ev. find(TicketDto ticket), update(TicketDto ticket), deleteById(Integer id), getAll(), ...

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public TicketDto getById(Integer id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/ticket/id/" + id);
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		LOG.info("Getting ticket by ID at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		TicketDto result = null;
		
		try {
			result = restTemplate.getForObject(url, TicketDto.class, entity);
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve Ticket by Id " + e.getMessage(), e);
		}
		LOG.info(result.toString());
		
		return result;
	}

	@Override
	public TicketDto createTicket(Integer show_id, Integer seat_id,
			Integer entry_id) throws ServiceException {
		LOG.info("createTicket called.");
		
		if((show_id == null && seat_id == null) ||
				(show_id != null && seat_id != null)){
			throw new ServiceException("Show_id OR Seat_id must be NULL, but not both");
		}
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		Map<String, Object> variables = new HashMap<String, Object>();
		boolean isFirst = true;	
		StringBuilder urlBuilder = new StringBuilder("/ticket/create?");
		
		if(show_id != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("show_id={show_id}");
			variables.put("show_id", show_id);
		}
		if(seat_id != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("seat_id={seat_id}");
			variables.put("seat_id", seat_id);
		}
		if(entry_id != null)
		{
			if(isFirst)
				isFirst = false;
			else
				urlBuilder.append("&");
				
			urlBuilder.append("entry_id={entry_id}");
			variables.put("entry_id", entry_id);
		}
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());
		
		try {
			return restTemplate.getForObject(url, TicketDto.class, variables );									
						
		} catch (HttpStatusCodeException e) {
			MessageDto errorMsg = this.restClient.mapExceptionToMessage(e);
			
			if (errorMsg.hasFieldErrors()) {
				throw new ValidationException(errorMsg.getFieldErrors());
			} else {
				throw new ServiceException(errorMsg.getText());
			}
		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve ticket: " + e.getMessage(), e);
		}			
		
		
	}

	@Override
	public KeyValuePairDto<TicketDto, Boolean> getTicketForEntry(
			Integer entry_id) throws ServiceException {
		LOG.info("getTicketForEntry called.");
		
		if(entry_id == null)
			throw new ServiceException("entry_id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/ticket/ticketForEntry/{entry_id}");	
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		KeyValuePairDto<TicketDto, Boolean> result = null;
		try {
			ParameterizedTypeReference<KeyValuePairDto<TicketDto, Boolean>> ref = new ParameterizedTypeReference<KeyValuePairDto<TicketDto, Boolean>>() {};				
			ResponseEntity<KeyValuePairDto<TicketDto, Boolean>> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, entry_id);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find performances: " + e.getMessage(), e);			
		}		
		
		return result;
	}

	@Override
	public void undoTicket(Integer id) throws ServiceException {
		LOG.info("undoTicket called.");
		
		if(id == null)
			throw new ServiceException("ticket_id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/ticket/delete/{id}");						
		
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
	public KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>> 
			getPerformanceShowLocationRowSeatByTicket(Integer ticket_id) throws ServiceException {
		LOG.info("getPerformanceShowLocationRowSeatByTicket called");
		
		if(ticket_id == null)
			throw new ServiceException("ticket_id must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/ticket/getPerformanceShowLocationRowSeatByTicket/{id}");	
		
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>> result = null;
		try {
			ParameterizedTypeReference<KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>>> ref = 
					new ParameterizedTypeReference<KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>>>() {};				
			ResponseEntity<KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>>> response = 
					restTemplate.exchange(url, HttpMethod.GET, entity, ref, ticket_id);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not get KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>> : " + e.getMessage(), e);			
		}		
		return result;			
	}

	@Override
	public TicketDto getTicketBySeat(int seatID) throws ServiceException {
	LOG.info("getTicketBySeat called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();				           
		
		StringBuilder urlBuilder = new StringBuilder("/ticket/getTicketBySeat?");			
		
		Map<String, Object> variables = new HashMap<String, Object>();
		
		boolean isFirst = true;		
		
		if(isFirst)
			isFirst = false;
		else
			urlBuilder.append("&");
			
		urlBuilder.append("seatID={seatID}");
		variables.put("seatID", seatID);				
		
		String url = this.restClient.createServiceUrl(urlBuilder.toString());

        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        
		HttpEntity<String> entity = new HttpEntity<String>(headers);			

		TicketDto result = null;
		try {
			ParameterizedTypeReference<TicketDto> ref = new ParameterizedTypeReference<TicketDto>() {};				
			ResponseEntity<TicketDto> response = restTemplate.exchange(url, HttpMethod.GET, entity, ref, variables);						
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not find ticket: " + e.getMessage(), e);
		}

		return result;	
	}
	
}
