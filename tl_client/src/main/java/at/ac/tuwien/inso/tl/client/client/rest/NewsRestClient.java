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
		String url = this.restClient.createServiceUrl("/news");

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

	@Override
	public void addNewsReadByEmployee(Integer news_id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/" + news_id + "/addreadby");

		LOG.info("Adding current employee to read by List of news with id " + news_id + " at " + url);

		try {
			
			restTemplate.getForObject(url, String.class);
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not add employee to read by list of news: " + e.getMessage(), e);
			
		}
	}
	
	@Override
	public void addNewsReadByEmployeeForce(Integer news_id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/" + news_id + "/addreadby/force");

		LOG.info("Force adding current employee to read by List of news with id " + news_id + " at " + url);

		try {
			
			restTemplate.getForObject(url, String.class);
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not force add employee to read by list of news: " + e.getMessage(), e);
			
		}
	}
	
	@Override
	public List<NewsDto> findAllUnreadNewsOfEmployee() throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/findallunread");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
	
		LOG.info("Retrieving all unread news for current user from " + url);

		List<NewsDto> news = null;
		
		try {
			
			ParameterizedTypeReference<List<NewsDto>> ref = new ParameterizedTypeReference<List<NewsDto>>() {};
			ResponseEntity<List<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			news = response.getBody();
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not retrieve all unread news for current user: " + e.getMessage(), e);
			
		}
		
		return news;
	}

	@Override
	public List<NewsDto> search(NewsDto input) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/search");

		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		HttpEntity<NewsDto> entity = new HttpEntity<NewsDto>(input, headers);
	
		LOG.info("Search for news from " + url);

		List<NewsDto> news = null;
		
		try {
			
			ParameterizedTypeReference<List<NewsDto>> ref = new ParameterizedTypeReference<List<NewsDto>>() {};
			ResponseEntity<List<NewsDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.POST, entity, ref);
			news = response.getBody();
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not search for news: " + e.getMessage(), e);
			
		}
		
		return news;
	}

	@Override
	public NewsDto getById(Integer id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/id/" + id);
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		LOG.info("Getting news by ID at " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		NewsDto result = null;
		
		try {
			
			result = restTemplate.getForObject(url, NewsDto.class, entity);
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not retrieve news by ID at " + e.getMessage(), e);
			
		}
		
		return result;
	}

	@Override
	public Boolean getNewsIsReadByEmployee(Integer news_id) throws ServiceException {
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/news/" + news_id + "/getisread");
		
		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());
		
		LOG.info("Requesting if news is read by employee " + url);
		
		HttpHeaders headers = this.restClient.getHttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		Boolean result = null;
		
		try {
			
			result = restTemplate.getForObject(url, Boolean.class, entity);
			
		} catch (RestClientException e) {
			
			throw new ServiceException("Could not retrieve news by ID at " + e.getMessage(), e);
			
		}
		
		return result;
	}
}
