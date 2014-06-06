package at.ac.tuwien.inso.tl.client.client.rest;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.client.client.ReceiptService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

@Component
public class ReceiptRestClient implements ReceiptService {

	private static final Logger LOG = Logger.getLogger(ReceiptRestClient.class);

	@Autowired
	private RestClient restClient;
	
	@Override
	public ReceiptDto createReceiptforEntries(List<EntryDto> enties,PaymentTypeDto pt)  throws ServiceException{
		//TODO
		throw new ServiceException("Not yet implemented");
	}

}
