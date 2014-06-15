package at.ac.tuwien.inso.tl.server.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.model.Customer;
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
	
	@RequestMapping(value = "/findBasket", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public  List<KeyValuePairDto<BasketDto, CustomerDto>> findBasket(@RequestBody KeyValuePairDto<Integer, CustomerDto> kvp) 
			throws ServiceException{
		LOG.info("findBasket called");
		
		List<KeyValuePairDto<BasketDto, CustomerDto>> retValue = new ArrayList<KeyValuePairDto<BasketDto, CustomerDto>>();
		for(Map.Entry<Basket, Customer> e: basketService.findBasket(kvp.getKey(), kvp.getValue() == null ? null : DtoToEntity.convert(kvp.getValue())))
		{			
			retValue.add(new KeyValuePairDto<BasketDto, CustomerDto>(EntityToDto.convert(e.getKey()), EntityToDto.convert(e.getValue())));					
		}
		return retValue;
	}

}
