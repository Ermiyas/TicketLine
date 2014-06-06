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
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;

@Component
public class EntryRestClient implements EntryService {
	
	private static final Logger LOG = Logger.getLogger(EntryRestClient.class);

	@Autowired
	private RestClient restClient;

	// TODO create(EntryDto entry), find(EntryDto entry), getById(Integer id), update(EntryDto entry), deleteById(Integer id), getAll(), ...

	@Override
	public List<EntryDto> getList(BasketDto basket) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/entry/basket/" + basket.getId());
		
		LOG.info("Getting entries by Basket-ID at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		ParameterizedTypeReference<List<EntryDto>> ref = new ParameterizedTypeReference<List<EntryDto>>() {};
		ResponseEntity<List<EntryDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
		List<EntryDto> results = response.getBody();
		for (EntryDto entry : results) {
			LOG.info(entry.toString());
		}
		
		return results;
	}

}