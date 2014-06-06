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

import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.TicketDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;

@Component
public class TicketRestClient implements TicketService {
	
	private static final Logger LOG = Logger.getLogger(TicketRestClient.class);

	@Autowired
	private RestClient restClient;

	// TODO create(TicketDto ticket), find(TicketDto ticket), update(TicketDto ticket), deleteById(Integer id), getAll(), ...

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
		
		return result;
	}

}