package at.ac.tuwien.inso.tl.server.util;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.inso.tl.dto.ArticleDto;
import at.ac.tuwien.inso.tl.dto.ArtistDto;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.EmployeeDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;
import at.ac.tuwien.inso.tl.model.Article;
import at.ac.tuwien.inso.tl.model.Artist;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.model.Employee;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.Location;
import at.ac.tuwien.inso.tl.model.News;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.model.Ticket;

public class EntityToDto {	
	
	public static ArticleDto convert(Article article)
	{
		ArticleDto dto = new ArticleDto();
		dto.setId(article.getId());
		dto.setDescription(article.getDescription());
		dto.setName(article.getName());
		dto.setPriceInCent(article.getPriceInCent());
		dto.setPriceInPoints(article.getPriceInPoints());		
		return dto;
	}
	
	public static List<ArticleDto> convertArticles(List<Article> articles){
		List<ArticleDto> ret = new ArrayList<ArticleDto>();
		if(null != articles){
			for(Article a : articles){
				ArticleDto dto = convert(a);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static ArtistDto convert(Artist artist)
	{
		ArtistDto dto = new ArtistDto();
		dto.setId(artist.getId());
		dto.setFirstname(artist.getFirstname());
		dto.setLastname(artist.getLastname());		
		return dto;
	}
	
	public static List<ArtistDto> convertArtists(List<Artist> artists){
		List<ArtistDto> ret = new ArrayList<ArtistDto>();
		if(null != artists){
			for(Artist a : artists){
				ArtistDto dto = convert(a);				
				ret.add(dto);
			}
		}
		return ret;
	}	
	
	public static BasketDto convert(Basket basket)
	{
		BasketDto dto = new BasketDto();
		dto.setId(basket.getId());
		dto.setCreationdate(basket.getCreationdate());
		return dto;
	}	
	
	public static List<BasketDto> convertBaskets(List<Basket> baskets){
		List<BasketDto> ret = new ArrayList<BasketDto>();
		if(null != baskets){
			for(Basket b : baskets){
				BasketDto dto = convert(b);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static List<CustomerDto> convertCustomers(List<Customer> customers){
		List<CustomerDto> ret = new ArrayList<CustomerDto>();
		if(null != customers){
			for(Customer c : customers){
				CustomerDto dto = convert(c);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static EmployeeDto convert(Employee employee)
	{
		EmployeeDto dto = new EmployeeDto();		
		dto.setId(employee.getId());
		dto.setFirstname(employee.getFirstname());
		dto.setIsadmin(employee.getIsadmin());
		dto.setLastname(employee.getLastname());
		dto.setPasswordHash(employee.getPasswordHash());
		dto.setUsername(employee.getUsername());
		dto.setWrongPasswordCounter(employee.getWrongPasswordCounter());
		dto.setId(employee.getId());
		return dto;
	}
	
	public static List<EmployeeDto> convertEmployees(List<Employee> employees){
		List<EmployeeDto> ret = new ArrayList<EmployeeDto>();
		if(null != employees){
			for(Employee e : employees){
				EmployeeDto dto = convert(e);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static EntryDto convert(Entry entry)
	{
		EntryDto dto = new EntryDto();		
		dto.setId(entry.getId());
		dto.setAmount(entry.getAmount());
		dto.setBuyWithPoints(entry.getBuyWithPoints());
		dto.setSold(entry.getSold());		
		return dto;
	}
	
	public static List<EntryDto> convertEntries(List<Entry> entries){
		List<EntryDto> ret = new ArrayList<EntryDto>();
		if(null != entries){
			for(Entry e : entries){
				EntryDto dto = convert(e);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static LocationDto convert(Location location)
	{
		LocationDto dto = new LocationDto();	
		dto.setId(location.getId());
		dto.setCity(location.getCity());
		dto.setCountry(location.getCountry());
		dto.setDescription(location.getDescription());
		dto.setPostalcode(location.getPostalcode());
		dto.setStreet(location.getStreet());
		return dto;
	}	
	
	
	public static List<LocationDto> convertLocations(List<Location> locations){
		List<LocationDto> ret = new ArrayList<LocationDto>();
		if(null != locations){
			for(Location l : locations){
				LocationDto dto = convert(l);				
				ret.add(dto);
			}
		}
		return ret;
	}		
	
	public static NewsDto convert(News news){
		
		NewsDto dto = new NewsDto();
		dto.setId(news.getId());
		dto.setTitle(news.getTitle());
		dto.setNewsText(news.getNewsText());
		dto.setSubmittedOn(news.getSubmittedOn());		
		return dto;
	}
	
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
	
	public static PaymentTypeDto convert(PaymentType paymentType)
	{
		switch(paymentType)
		{
			case CASH:
				return PaymentTypeDto.CASH;
			case CREDITCARD:
				return PaymentTypeDto.CREDITCARD;
			default:
				return PaymentTypeDto.BANK;
		}
	}
	
	public static List<PaymentTypeDto> convertPaymentTypes(List<PaymentType> paymentTypes){
		List<PaymentTypeDto> ret = new ArrayList<PaymentTypeDto>();
		if(null != paymentTypes){
			for(PaymentType p : paymentTypes){
				PaymentTypeDto dto = convert(p);				
				ret.add(dto);
			}
		}
		return ret;
	}	
	
	public static PerformanceDto convert(Performance performance)
	{
		PerformanceDto dto = new PerformanceDto();
		dto.setId(performance.getId());
		dto.setContent(performance.getContent());
		dto.setDescription(performance.getDescription());		
		dto.setDurationInMinutes(performance.getDurationInMinutes());
		dto.setPerformancetype(performance.getPerformancetype());					
		return dto;
	}
	
	public static List<PerformanceDto> convertPerformances(List<Performance> performances){
		List<PerformanceDto> ret = new ArrayList<PerformanceDto>();
		if(null != performances){
			for(Performance p : performances){
				PerformanceDto dto = convert(p);				
				ret.add(dto);
			}
		}
		return ret;
	}	
	
	public static ReceiptDto convert(Receipt receipt)
	{
		ReceiptDto dto = new ReceiptDto();
		dto.setId(receipt.getId());
		dto.setTransactionDate(receipt.getTransactionDate());
		dto.setPaymentType(convert(receipt.getPaymentType()));
		return dto;
	}
	
	public static List<ReceiptDto> convertReceipts(List<Receipt> receipts){
		List<ReceiptDto> ret = new ArrayList<ReceiptDto>();
		if(null != receipts){
			for(Receipt r : receipts){
				ReceiptDto dto = convert(r);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static RowDto convert(Row row)
	{
		RowDto dto = new RowDto();
		dto.setId(row.getId());
		dto.setSequence(row.getSequence());
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
	
	public static List<RowDto> convertRows(List<Row> rows){
		List<RowDto> ret = new ArrayList<RowDto>();
		if(null != rows){
			for(Row r : rows){
				RowDto dto = convert(r);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static SeatDto convert(Seat seat)
	{
		SeatDto dto = new SeatDto();
		dto.setId(seat.getId());
		dto.setSequence(seat.getSequence());
		return dto;
	}
	
	public static List<SeatDto> convertSeats(List<Seat> seats){
		List<SeatDto> ret = new ArrayList<SeatDto>();
		if(null != seats){
			for(Seat r : seats){
				SeatDto dto = convert(r);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static ShowDto convert(Show show)
	{
		ShowDto dto = new ShowDto();
		dto.setId(show.getId());
		dto.setDateOfPerformance(show.getDateOfPerformance());
		dto.setPriceInCent(show.getPriceInCent());
		dto.setRoom(show.getRoom());
		return dto;
	}
	
	public static List<ShowDto> convertShows(List<Show> shows){
		List<ShowDto> ret = new ArrayList<ShowDto>();
		if(null != shows){
			for(Show s : shows){
				ShowDto dto = convert(s);				
				ret.add(dto);
			}
		}
		return ret;
	}
	
	public static TicketDto convert(Ticket ticket)
	{
		TicketDto dto = new TicketDto();	
		dto.setId(ticket.getId());
		return dto;
	}	
	
	public static List<TicketDto> convertTickets(List<Ticket> tickets){
		List<TicketDto> ret = new ArrayList<TicketDto>();
		if(null != tickets){
			for(Ticket t : tickets){
				TicketDto dto = convert(t);				
				ret.add(dto);
			}
		}
		return ret;
	}	
}
