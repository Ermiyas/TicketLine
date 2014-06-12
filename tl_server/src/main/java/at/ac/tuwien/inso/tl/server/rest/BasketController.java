package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.BasketService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;



@RestController
@RequestMapping(value = "/basket")
public class BasketController {
	
	private static final Logger LOG = Logger.getLogger(BasketController.class);
	
	@Autowired
	private BasketService basketService;
	
	@RequestMapping(value = "/createEmptyBasket", method = RequestMethod.GET, produces = "application/json")
	public BasketDto createBasket () throws ServiceException{
		LOG.info("createTicket called.");
		return EntityToDto.convert(basketService.createBasket());
	}
	
	@RequestMapping(value = "/getById/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BasketDto getBasket (@PathVariable("id") Integer id) throws ServiceException{
		LOG.info("getBasket called.");
		return EntityToDto.convert(basketService.getBasket(id));
	}
	
	@RequestMapping(value = "/undoBasket/{id}", method = RequestMethod.DELETE)
	public void undoBasket(@PathVariable("id") Integer id)  throws ServiceException{
		LOG.info("undoBasket called");
		basketService.undoBasket(id);
	
	}
	
	@RequestMapping(value = "/setCustomerForBasket", method = RequestMethod.PUT)
	public void setCustomerForBasket(@Valid @RequestBody KeyValuePairDto<BasketDto, Integer> kvp)
			throws ServiceException{
		LOG.info("setCustomerForBasket called");
		
		basketService.setCustomerForBasket(DtoToEntity.convert(kvp.getKey()), kvp.getValue());
		
	}

}
