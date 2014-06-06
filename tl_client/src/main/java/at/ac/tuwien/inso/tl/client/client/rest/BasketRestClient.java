package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

@Component
public class BasketRestClient implements BasketService {

	private static final Logger LOG = Logger.getLogger(BasketRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public BasketDto createBasket()  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");
	}

	@Override
	public BasketDto getBasket(Integer basket_id)  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");
	}

	@Override
	public void undoBasket(Integer basket_id)  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");

	}

	@Override
	public void setCustomerForBasket(BasketDto basket, Integer customer_id)  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");

	}

	@Override
	public List<BasketDto> findBasket(Integer basket_id, List<Integer> customers) throws ServiceException {
		//TODO
		throw new ServiceException("Not yet implemented");
	}

}
