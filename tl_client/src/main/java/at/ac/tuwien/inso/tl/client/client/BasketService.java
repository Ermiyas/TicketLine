package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

public interface BasketService {
	
//	public Integer create(BasketDto basket) throws ServiceException;
	
//	public List<BasketDto> find(BasketDto basket) throws ServiceException;
	
	public BasketDto getById(Integer id) throws ServiceException;
	
//	public void update(BasketDto basket) throws ServiceException;
	
//	public void deleteById(Integer id) throws ServiceException;
	
	public List<BasketDto> getAll() throws ServiceException;
	
}