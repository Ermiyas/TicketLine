package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.EmployeeDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EmployeeService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/users")
public class EmployeeController {
	private static final Logger LOG = Logger.getLogger(EmployeeController.class);
	
	@Autowired
	private EmployeeService service;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<EmployeeDto> getAll() throws ServiceException {
		LOG.info("getAll() called");

		return EntityToDto.convertEmployees(service.retrieveAllEmployees());
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createEmployee(@Valid @RequestBody EmployeeDto employee) throws ServiceException {
		LOG.info("createEmployee() called");

		Integer id = service.createEmployee(DtoToEntity.convert(employee)).getId();
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(id.toString());

		return msg;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto updateEmployee(@Valid @RequestBody EmployeeDto employee) throws ServiceException {
		LOG.info("updateEmployee() called");

		Integer id = service.updateEmployee(DtoToEntity.convert(employee)).getId();
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(id.toString());

		return msg;
	}
}
