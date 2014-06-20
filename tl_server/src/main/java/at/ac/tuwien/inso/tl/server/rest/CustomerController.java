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

import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.model.Customer;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.CustomerService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {
	
	private static final Logger LOG = Logger.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createCustomer(@Valid @RequestBody CustomerDto customer) throws ServiceException {
		
		LOG.info("createCustomer() called");

		Integer id = this.customerService.create(DtoToEntity.convert(customer)).getId();
		
		MessageDto msg = new MessageDto();
		
		msg.setType(MessageType.SUCCESS);
		msg.setText(id.toString());

		return msg;
		
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public List<CustomerDto> findCustomer(@RequestBody CustomerDto customer) throws ServiceException {
		
		LOG.info("findCustomer() called");

		List<Customer> results = this.customerService.find(DtoToEntity.convert(customer));

		return EntityToDto.convertCustomers(results);
		
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto updateCustomer(@Valid @RequestBody CustomerDto customer) throws ServiceException {
		
		LOG.info("updateCustomer() called");

		this.customerService.update(DtoToEntity.convert(customer));

		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(customer.getId().toString());

		return msg;
		
	}
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public CustomerDto findCustomerById(@PathVariable String id) throws ServiceException {
		
		LOG.info("findCustomerById() called");

		return EntityToDto.convert(this.customerService.getById(Integer.parseInt(id)));
		
	}
	
	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
	public List<CustomerDto> getAll() throws ServiceException {
		
		LOG.info("getAll() called");

		return EntityToDto.convertCustomers((customerService.getAll()));
		
	}
	
	@RequestMapping(value = "/delete/id/{id}", method = RequestMethod.GET, produces = "application/json")
	public MessageDto deleteCustomerById(@PathVariable String id) throws ServiceException {
		
		LOG.info("deleteCustomerById() called");

		this.customerService.deleteById(Integer.parseInt(id));
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(id.toString());

		return msg;
		
	}
}