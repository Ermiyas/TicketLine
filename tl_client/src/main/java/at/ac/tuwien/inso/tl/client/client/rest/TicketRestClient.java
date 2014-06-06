package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

@Component
public class TicketRestClient implements TicketService {

	private static final Logger LOG = Logger.getLogger(TicketRestClient.class);

	@Autowired
	private RestClient restClient;
	
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
		String url = this.restClient.createServiceUrl("ticket/ticketForEntry/{entry_id}");	
		
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
			throw new ServiceException("Could not delete performance: " + e.getMessage(), e);
		}				
		
	}

}