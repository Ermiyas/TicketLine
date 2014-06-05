package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.BasketService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/basket")
public class BasketController {
	
	private static final Logger LOG = Logger.getLogger(Basket.class);
	
	@Autowired
	private BasketService basketService;

//	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public MessageDto createBasket(@Valid @RequestBody BasketDto basket) throws ServiceException {
//		
//		LOG.info("createBasket() called");
//
//		Integer id = this.basketService.create(DtoToEntity.convert(basket)).getId();
//		
//		MessageDto msg = new MessageDto();
//		
//		msg.setType(MessageType.SUCCESS);
//		msg.setText(id.toString());
//
//		return msg;
//		
//	}
//	
//	@RequestMapping(value = "/find", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public List<BasketDto> findBasket(@RequestBody BasketDto basket) throws ServiceException {
//		
//		LOG.info("findBasket() called");
//
//		List<Basket> results = this.basketService.find(DtoToEntity.convert(basket));
//
//		return EntityToDto.convertBasket(results);
//		
//	}
//	
//	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	public MessageDto updateBasket(@Valid @RequestBody BasketDto basket) throws ServiceException {
//		
//		LOG.info("updateBasket() called");
//
//		this.basketService.update(DtoToEntity.convert(basket));
//
//		MessageDto msg = new MessageDto();
//		msg.setType(MessageType.SUCCESS);
//		msg.setText(basket.getId().toString());
//
//		return msg;
//		
//	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public BasketDto findBasketById(@PathVariable String id) throws ServiceException {
		
		LOG.info("findBasketById() called");

		return EntityToDto.convert(this.basketService.getById(Integer.parseInt(id)));
		
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public List<BasketDto> getAll() throws ServiceException {
		
		LOG.info("getAll() called");

		return EntityToDto.convertBaskets((basketService.getAll()));
		
	}
	
//	@RequestMapping(value = "/delete/id/{id}", method = RequestMethod.GET, produces = "application/json")
//	public MessageDto deleteBasketById(@PathVariable String id) throws ServiceException {
//		
//		LOG.info("deleteBasketById() called");
//
//		this.basketService.deleteById(Integer.parseInt(id));
//		
//		MessageDto msg = new MessageDto();
//		msg.setType(MessageType.SUCCESS);
//		msg.setText(id.toString());
//
//		return msg;
//		
//	}
}