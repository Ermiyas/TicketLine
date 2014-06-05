package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

public interface BasketService {
	
	// TODO create(BasketDto basket), find(BasketDto basket), update(BasketDto basket), deleteById(Integer id), ...
	
	public BasketDto getById(Integer id) throws ServiceException;
	
	public List<BasketDto> getAll() throws ServiceException;
	
}