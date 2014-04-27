package at.ac.tuwien.inso.tl.client.client.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RestClient {

	private static final String USER_AGENT = "Ticketline Client/2.1";

	private HttpComponentsClientHttpRequestFactory clientFactory;
	
	private List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Value("#{'${server_url}'}")
	private String baseUrl;

	@PostConstruct
	public void init() {
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		
		CloseableHttpClient client = HttpClients
			.custom()
			.setConnectionManager(cm)
			.build();
		
		this.clientFactory = new HttpComponentsClientHttpRequestFactory(client);
		
		this.interceptors.add(new UserAgentInterceptor());
	}
	
	public RestTemplate getRestTemplate() {
		RestTemplate template = new RestTemplate(this.clientFactory);
		template.setInterceptors(this.interceptors);
		return template;
	}
	
	public HttpHeaders getHttpHeaders() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.getAcceptCharset().clear();
		return httpHeaders;
	}

	public String createServiceUrl(String service) {
		return this.baseUrl + service;
	}
	
	public MessageDto mapExceptionToMessage(HttpStatusCodeException exception) {
		MessageDto m = new MessageDto();
		m.setType(MessageType.ERROR);
		m.setText(exception.getResponseBodyAsString());
		
		if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
			m.setText("Unauthorized access");
			return m;
		}
		
		if (MediaType.APPLICATION_JSON.equals(exception.getResponseHeaders().getContentType()) == false) {
			return m;
		}
		
		try {
			m = this.mapper.readValue(exception.getResponseBodyAsString(), MessageDto.class);	
		} catch (Exception e) {
			/* ignore - message text already set */
		}
		
		return m;
	}

	private static class UserAgentInterceptor implements ClientHttpRequestInterceptor {

	    @Override
	    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
	        HttpHeaders headers = request.getHeaders();
	        headers.add("User-Agent", USER_AGENT);
	        return execution.execute(request, body);
	    }
	}
}
