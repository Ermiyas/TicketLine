package at.ac.tuwien.inso.tl.server.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ReceiptService;
import at.ac.tuwien.inso.tl.server.util.DtoToEntity;
import at.ac.tuwien.inso.tl.server.util.EntityToDto;


@RestController
@RequestMapping(value = "/receipt")
public class ReceiptController {
	private static final Logger LOG = Logger.getLogger(ReceiptController.class);
	
	@Autowired
	private ReceiptService receiptService;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public KeyValuePairDto<ReceiptDto, Integer> createReceipt(@Valid @RequestBody KeyValuePairDto<List<EntryDto>, PaymentTypeDto> kvp) throws ServiceException{

		LOG.info("createReceipt called");
		
		ArrayList<Entry> entries = new ArrayList<Entry>();
		for(EntryDto eDto: kvp.getKey()){
			entries.add(DtoToEntity.convert(eDto));
		}
		KeyValuePairDto<Receipt, Integer> result = receiptService.createReceiptforEntries(entries, DtoToEntity.convert(kvp.getValue()));
		
		return new KeyValuePairDto<ReceiptDto, Integer>(EntityToDto.convert(result.getKey()), result.getValue());
		
	}
}
