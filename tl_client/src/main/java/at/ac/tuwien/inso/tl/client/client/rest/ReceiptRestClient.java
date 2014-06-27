package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.ReceiptService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

@Component
public class ReceiptRestClient implements ReceiptService {

	private static final Logger LOG = Logger.getLogger(ReceiptRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public KeyValuePairDto<ReceiptDto, Integer> createReceiptforEntries(List<EntryDto> entries,PaymentTypeDto pt)  throws ServiceException{
		LOG.info("createReceiptforEntries called");
		
		if(entries == null){
			throw new ServiceException("List<EntryDto> must not be null.");
		}
		if(entries.isEmpty()){
			throw new ServiceException("List<EntryDto> must contain at least 1 object.");
		}
		if(pt == null){
			throw new ServiceException("PaymentType must not be null.");
		}
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/receipt/create");
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		KeyValuePairDto<List<EntryDto>, PaymentTypeDto> kvp = 
				new KeyValuePairDto<List<EntryDto>, PaymentTypeDto>(entries, pt);

		HttpEntity<KeyValuePairDto<List<EntryDto>, PaymentTypeDto>> entity = 
				new HttpEntity<KeyValuePairDto<List<EntryDto>, PaymentTypeDto>>(kvp, headers);
		
		ParameterizedTypeReference<KeyValuePairDto<ReceiptDto, Integer>> ref = new ParameterizedTypeReference<KeyValuePairDto<ReceiptDto, Integer>>(){};
		try
		{
			return restTemplate.exchange(url, HttpMethod.POST, entity, ref).getBody();		
		} catch (RestClientException e) {
			throw new ServiceException("Could not create location: " + e.getMessage(), e);
		}
	}

}
