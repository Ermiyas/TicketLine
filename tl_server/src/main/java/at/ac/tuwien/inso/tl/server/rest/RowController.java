package at.ac.tuwien.inso.tl.server.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
		
		int lastRowID = 0;
		List<KeyValuePairDto<SeatDto, Boolean>> lastSubList = null;
		
		for(Seat s: seatService.getAllSeatForShow(showID))
		{
			Row r = s.getRow();
			int rowID = r.getId();
			
			if(lastSubList == null || lastRowID != rowID)
			{
				lastSubList = findSubList(result, rowID, r);
				lastRowID = rowID;
			}
			
			Boolean value = s.getTicket() == null;
			if(value == false && basketID != null)
			{
				if(s.getTicket().getEntry().getBasket().getId() == basketID)
				{
					value = null;
				}
			}
			
			lastSubList.add(new KeyValuePairDto<SeatDto, Boolean>(EntityToDto.convert(s),value));
		}
		
		for(KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>> e: result)
		{
			Collections.sort(e.getValue(), seatComparator);
		}
		
		return result;
	}
	
	private List<KeyValuePairDto<SeatDto, Boolean>> findSubList(List<KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>>> result, int rowID, Row r)
	{		
		KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>> returnValue = null;
		for(KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>> e: result)
		{
			if(e.getKey().getId() == rowID)
			{
				returnValue = e;
				break;
			}
		}
		
		if(returnValue == null)
		{
			List<KeyValuePairDto<SeatDto, Boolean>> newList = new ArrayList<KeyValuePairDto<SeatDto, Boolean>>();
			RowDto rDto = EntityToDto.convert(r);
		
			returnValue = new KeyValuePairDto<RowDto, List<KeyValuePairDto<SeatDto, Boolean>>>(rDto, newList);
			result.add(returnValue);
			
		}	
		
		return returnValue.getValue();
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
	
	public static Comparator<KeyValuePairDto<SeatDto, Boolean>> seatComparator = new Comparator<KeyValuePairDto<SeatDto, Boolean>>()
			{

				@Override
				public int compare(KeyValuePairDto<SeatDto, Boolean> o1,
						KeyValuePairDto<SeatDto, Boolean> o2) {
					if(o1 == null || o1.getKey() == null || o1.getKey() == null)
					{
						throw new NullPointerException("o1 and its sequence must not be null.");
					}
					if(o2 == null || o2.getKey() == null || o2.getKey() == null)
					{
						throw new NullPointerException("o2 and its sequence must not be null.");
					}
					
					return o1.getKey().getSequence().compareTo(o2.getKey().getSequence());
				}
		
			};
			
}