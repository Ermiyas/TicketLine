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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.MessageDto;
import at.ac.tuwien.inso.tl.dto.MessageType;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.RowService;
import at.ac.tuwien.inso.tl.server.service.SeatService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;

@RestController
@RequestMapping(value = "/rows")
public class RowController {
	
	private static final Logger LOG = Logger.getLogger(RowController.class);
	
	@Autowired
	private RowService service;
	
	@Autowired
	private SeatService seatService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public MessageDto createRow(@Valid @RequestBody RowDto row) throws ServiceException {
		LOG.info("createRow called.");
		
		MessageDto msg = new MessageDto();
		msg.setType(MessageType.SUCCESS);
		msg.setText(service.createRow(DtoToEntity.convert(row)).getId().toString());
		return msg;
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public void deleteRow(@PathVariable("id") Integer id) throws ServiceException {
		LOG.info("deleteRow called.");
		service.deleteRow(id);
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<KeyValuePairDto<RowDto,List<KeyValuePairDto<SeatDto, Boolean>>>> findRowsAndSeats(
			@RequestParam(value="showID", required = false) Integer showID, 
			@RequestParam(value="basketID", required = false) Integer basketID) throws ServiceException {
		LOG.info("findRowsAndSeats called.");
		
		List<KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>>> result =
				new ArrayList<KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>>>();
		
		List<RowDto> rows = EntityToDto.convertRows(service.findRows(showID));
		
		for(RowDto row : rows) {
			List<KeyValuePairDto<SeatDto, Boolean>> seatsInRow = new ArrayList<KeyValuePairDto<SeatDto, Boolean>>();
			for(Map.Entry<Seat, Boolean> e: seatService.findSeats(row.getId(), basketID)) {
				seatsInRow.add(new KeyValuePairDto<SeatDto, Boolean>(EntityToDto.convert(e.getKey()), e.getValue()));
			}
			result.add(new KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>>(row, seatsInRow));
		}
		
		return result;
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
		
		Row ret = service.getRow(id);
		
		if(ret == null)
			return null;
		else		
			return EntityToDto.convert(ret);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
	public void updateRow(@Valid @RequestBody RowDto row) throws ServiceException {
		LOG.info("updateRow called.");
		service.updateRow(DtoToEntity.convert(row));
	}
}
