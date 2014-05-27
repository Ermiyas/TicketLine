package at.ac.tuwien.inso.tl.server.rest;

import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.RowService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/rows")
public class RowController {
	
	private static final Logger LOG = Logger.getLogger(RowController.class);
	
	@Autowired
	private RowService service;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createRow(@Valid @RequestBody RowDto row) throws ServiceException {
		LOG.info("createRow called.");
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createRow(DtoToEntity.convert(row)).getId().toString());
		return msg;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
	public void deleteRow(@RequestParam(value="id") Integer id) throws ServiceException {
		LOG.info("deleteRow called.");
		service.deleteRow(id);
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public List<RowDto> findRows(@RequestParam(value="showID") Integer showID) throws ServiceException {
		LOG.info("findRows called.");
		return EntityToDto.convertRows(service.findRows(showID));
	}

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public List<RowDto> getAllRows() throws ServiceException {
		LOG.info("getAllRows called.");
		return EntityToDto.convertRows(service.getAllRows());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public RowDto getRow(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("getRow called.");

		if (id < 1) {
			throw new ServiceException("Invalid ID");
		}		
		
		return EntityToDto.convert(service.getRow(id));
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateRow(@Valid @RequestBody RowDto row) throws ServiceException {
		LOG.info("updateRow called.");
		service.updateRow(DtoToEntity.convert(row));
	}
}
