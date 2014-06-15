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

import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.NewsDto;

@Component
public class NewsRestClient implements NewsService {

	private static final Logger LOG = Logger.getLogger(NewsRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public List<NewsDto> getNews() throws ServiceException {
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
	
		LOG.info("Retrieving news from " + url);

		List<NewsDto> news = null;
		try {
			ParameterizedTypeReference<List<NewsDto>> ref = new ParameterizedTypeReference<List<NewsDto>>() {};
			ResponseEntity<List<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			news = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve news: " + e.getMessage(), e);
		}

		return news;
	}

	@Override
	public Integer publishNews(NewsDto news) throws ServiceException {
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/publish");
		
		LOG.info("Publish news at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<NewsDto> entity = new HttpEntity<NewsDto>(news, headers);
		
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
			throw new ServiceException("Could not publish news: " + e.getMessage(), e);
		}
		
		Integer id = null;
		try {
			id = Integer.valueOf(msg.getText());
		} catch (NumberFormatException e) {
			throw new ServiceException("Invalid ID: " + msg.getText());
		}
		
		return id;
	}

}
