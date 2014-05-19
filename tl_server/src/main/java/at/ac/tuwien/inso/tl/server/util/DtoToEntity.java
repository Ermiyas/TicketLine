package at.ac.tuwien.inso.tl.server.util;

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

public class DtoToEntity {
	
	public static Article convert(ArticleDto artcielDto)
	{
		Article article = new Article();
		article.setId(artcielDto.getId());
		article.setDescription(artcielDto.getDescription());
		article.setName(artcielDto.getName());
		article.setPriceInCent(artcielDto.getPriceInCent());
		article.setPriceInPoints(artcielDto.getPriceInPoints());				
		return article;
	}
	
	public static Artist convert(ArtistDto artistDto)
	{
		Artist artist = new Artist();
		artist.setId(artistDto.getId());
		artist.setFirstname(artistDto.getFirstname());
		artist.setLastname(artistDto.getLastname());		
		return artist;
	}
	
	public static Basket convert(BasketDto basketDto)
	{
		Basket basket = new Basket();
		basket.setId(basketDto.getId());
		basket.setCreationdate(basketDto.getCreationdate());		
		return basket;
	}	
	
	public static Customer convert(CustomerDto customerDto)
	{
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
	
	public static Employee convert(EmployeeDto employeeDto)
	{
		Employee employee = new Employee();		
		employee.setId(employeeDto.getId());
		employee.setFirstname(employeeDto.getFirstname());
		employee.setIsadmin(employeeDto.getIsadmin());
		employee.setLastname(employeeDto.getLastname());
		employee.setPasswordHash(employeeDto.getPasswordHash());
		employee.setUsername(employeeDto.getUsername());
		employee.setWrongPasswordCounter(employeeDto.getWrongPasswordCounter());			
		return employee;
	}
	
	public static Entry convert(EntryDto entryDto)
	{
		Entry entry = new Entry();		
		entry.setId(entryDto.getId());
		entry.setAmount(entryDto.getAmount());
		entry.setBuyWithPoints(entryDto.getBuyWithPoints());
		entry.setSold(entryDto.getSold());			
		return entry;
	}
	
	public static Location convert(LocationDto locationDto)
	{
		Location location = new Location();		
		location.setId(locationDto.getId());
		location.setCity(locationDto.getCity());
		location.setCountry(locationDto.getCountry());
		location.setDescription(locationDto.getDescription());
		location.setPostalcode(locationDto.getPostalcode());
		location.setStreet(locationDto.getStreet());		
		return location;
	}	
	
	public static News convert(NewsDto newsDto) {
		News news = new News();
		news.setId(newsDto.getId());
		news.setNewsText(newsDto.getNewsText());
		news.setSubmittedOn(newsDto.getSubmittedOn());
		news.setTitle(newsDto.getTitle());			
		return news;
	}	
	
	public static PaymentType convert(PaymentTypeDto paymentTypeDto)
	{
		switch(paymentTypeDto)
		{
			case CASH:
				return PaymentType.CASH;
			case CREDITCARD:
				return PaymentType.CREDITCARD;
			default:
				return PaymentType.BANK;
		}
	}
	
	public static Performance convert(PerformanceDto performanceDto)
	{
		Performance performance = new Performance();
		performance.setId(performanceDto.getId());
		performance.setContent(performanceDto.getContent());
		performance.setDescription(performanceDto.getDescription());		
		performance.setDurationInMinutes(performanceDto.getDurationInMinutes());
		performance.setPerformancetype(performanceDto.getPerformancetype());		
		return performance;
	}
	
	public static Receipt convert(ReceiptDto receiptDto)
	{
		Receipt receipt = new Receipt();
		receipt.setId(receiptDto.getId());
		receipt.setTransactionDate(receiptDto.getTransactionDate());
		receipt.setPaymentType(convert(receiptDto.getPaymentType()));		
		return receipt;
	}
	
	public static Row convert(RowDto rowDto)
	{
		Row row = new Row();
		row.setId(rowDto.getId());
		row.setSequence(rowDto.getSequence());		
		return row;
	}
	
	public static Seat convert(SeatDto seatDto)
	{
		Seat seat = new Seat();
		seat.setId(seatDto.getId());
		seat.setSequence(seatDto.getSequence());		
		return seat;
	}
	
	public static Show convert(ShowDto showDto)
	{
		Show show = new Show();
		show.setId(showDto.getId());
		show.setDateOfPerformance(showDto.getDateOfPerformance());
		show.setPriceInCent(showDto.getPriceInCent());
		show.setRoom(showDto.getRoom());		
		return show;
	}

	public static Ticket convert(TicketDto ticketDto)
	{
		Ticket ticket = new Ticket();
		ticket.setId(ticketDto.getId());
		return ticket;
	}		
}
