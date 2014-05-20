package at.ac.tuwien.inso.tl.server.util;

import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.model.News;

public class DtoToEntity {

	public static News convert(NewsDto newsDto) {
		
		News news = new News();
		news.setTitle(newsDto.getTitle());
		news.setNewsText(newsDto.getNewsText());
		return news;
		
	}
	
	public static Customer convert(CustomerDto customerDto) {
		
		Customer customer = new Customer();
		
		customer.setId(customerDto.getId());
		customer.setCity(customerDto.getCity());
		customer.setCountry(customerDto.getCountry());
		customer.setDateOfBirth(customerDto.getDateOfBirth());
		customer.setEmail(customerDto.getEmail());
		customer.setFirstname(customerDto.getFirstname());
		customer.setIsFemale(customerDto.getIsFemale());
		customer.setLastname(customerDto.getLastname());
		customer.setPoints(customerDto.getPoints());
		customer.setPostalcode(customerDto.getPostalcode());
		customer.setStreet(customerDto.getStreet());
		customer.setTelephonenumber(customerDto.getTelephonenumber());
		customer.setTitle(customerDto.getTitle());
		
		return customer;
		
	}
}