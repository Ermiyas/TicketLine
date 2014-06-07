package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.ReceiptService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

@Component
public class ReceiptRestClient implements ReceiptService {

	private static final Logger LOG = Logger.getLogger(ReceiptRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public ReceiptDto createReceiptforEntries(List<EntryDto> entries,PaymentTypeDto pt)  throws ServiceException{
		LOG.info("createReceiptforEntries called");
		
		if(pt == null)
			throw new ServiceException("PaymentType must not be null.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/receipt/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<List<EntryDto>> entity = new HttpEntity<List<EntryDto>>(entries, headers);
		
		//TODO
				throw new ServiceException("Not yet implemented");
		
		//ReceiptDto resp = restTemplate.postForObject(url, request, ReceiptDto.class, entity)
		
	}

}
