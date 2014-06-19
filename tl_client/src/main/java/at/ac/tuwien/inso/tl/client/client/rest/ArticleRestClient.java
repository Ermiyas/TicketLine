package at.ac.tuwien.inso.tl.client.client.rest;

import java.net.URI;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.client.client.ArticleService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.ArticleDto;

public class ArticleRestClient implements ArticleService {

	private static final Logger LOG = Logger.getLogger(ArticleRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public List<ArticleDto> getAll() throws ServiceException {
		LOG.info("getAll called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/articles/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ArticleDto> result = null;
		try {
			ParameterizedTypeReference<List<ArticleDto>> ref = new ParameterizedTypeReference<List<ArticleDto>>() {};
			ResponseEntity<List<ArticleDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve articles: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public List<ArticleDto> getAllMerchandising() throws ServiceException {
		LOG.info("getAllMerchandising called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/getAllMerchandising/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ArticleDto> result = null;
		try {
			ParameterizedTypeReference<List<ArticleDto>> ref = new ParameterizedTypeReference<List<ArticleDto>>() {};
			ResponseEntity<List<ArticleDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve articles: " + e.getMessage(), e);
		}

		return result;
	}

	@Override
	public List<ArticleDto> getAllBonus() throws ServiceException {
	LOG.info("getAllBonus called.");
		
		RestTemplate restTemplate = this.restClient.getRestTemplate();
		String url = this.restClient.createServiceUrl("/getAllBonus/");

		HttpEntity<String> entity = new HttpEntity<String>(this.restClient.getHttpHeaders());			

		List<ArticleDto> result = null;
		try {
			ParameterizedTypeReference<List<ArticleDto>> ref = new ParameterizedTypeReference<List<ArticleDto>>() {};
			ResponseEntity<List<ArticleDto>> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
			result = response.getBody();

		} catch (RestClientException e) {
			throw new ServiceException("Could not retrieve articles: " + e.getMessage(), e);
		}

		return result;
	}

}
