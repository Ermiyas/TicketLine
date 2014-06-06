package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.List;

import at.ac.tuwien.inso.tl.client.client.ReceiptService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

public class ReceiptRestClient implements ReceiptService {

	@Override
	public ReceiptDto createReceiptforEntries(List<EntryDto> enties,PaymentTypeDto pt)  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");
	}

}
