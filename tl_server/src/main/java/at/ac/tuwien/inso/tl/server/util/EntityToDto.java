package at.ac.tuwien.inso.tl.server.util;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.model.News;

public class EntityToDto {
	
	public static List<NewsDto> convertNews(List<News> news){
		
		List<NewsDto> ret = new ArrayList<NewsDto>();
		
		if(null != news){
			
			for(News n : news){
				
				NewsDto dto = convert(n);
				
				ret.add(dto);
				
			}
		}
		
		return ret;
		
	}
	
	public static NewsDto convert(News news){
		
		NewsDto dto = new NewsDto();

		dto.setTitle(news.getTitle());
		dto.setNewsText(news.getNewsText());
		dto.setSubmittedOn(news.getSubmittedOn());
		
		return dto;
		
	}
	
	public static List<CustomerDto> convertCustomer(List<Customer> customer){
		
		List<CustomerDto> ret = new ArrayList<CustomerDto>();
		if(null != customer){
			for(Customer c : customer){
				CustomerDto dto = convert(c);
				
				ret.add(dto);
			}
		}
		
		return ret;
		
	}
	
	public static CustomerDto convert(Customer customer){
		
		CustomerDto dto = new CustomerDto();
		
		dto.setId(customer.getId());
		dto.setCity(customer.getCity());
		dto.setCountry(customer.getCountry());
		dto.setDateOfBirth(customer.getDateOfBirth());
		dto.setEmail(customer.getEmail());
		dto.setFirstname(customer.getFirstname());
		dto.setIsFemale(customer.getIsFemale());
		dto.setLastname(customer.getLastname());
		dto.setPoints(customer.getPoints());
		dto.setPostalcode(customer.getPostalcode());
		dto.setStreet(customer.getStreet());
		dto.setTelephonenumber(customer.getTelephonenumber());
		dto.setTitle(customer.getTitle());
		
		return dto;
		
	}
}