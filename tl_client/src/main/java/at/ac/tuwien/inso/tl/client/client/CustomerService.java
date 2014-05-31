package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.CustomerDto;

public interface CustomerService {
	
	public Integer create(CustomerDto customer) throws ServiceException;
	
	public List<CustomerDto> find(CustomerDto customer) throws ServiceException;
	
	public CustomerDto getById(Integer id) throws ServiceException;
	
	public void update(CustomerDto customer) throws ServiceException;
	
	public void deleteById(Integer id) throws ServiceException;
	
	public List<CustomerDto> getAll() throws ServiceException;
	
}