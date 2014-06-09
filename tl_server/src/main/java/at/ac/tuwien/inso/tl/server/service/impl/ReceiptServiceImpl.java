package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.dao.ReceiptDao;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.ReceiptService;

@Service
public class ReceiptServiceImpl implements ReceiptService {
	
	private static final Logger LOG = Logger.getLogger(ReceiptServiceImpl.class);
	
	@Autowired
	private ReceiptDao receiptDao;

	@Override
	@Transactional
	public Receipt createReceiptforEntries(List<Entry> entries, PaymentType pt)
			throws ServiceException {
		
		LOG.info("createReceiptforEntries called");
		
		if(entries == null){
			throw new ServiceException("List<EntryDto> must not be null.");
		}
		if(entries.isEmpty()){
			throw new ServiceException("List<EntryDto> must contain at least 1 object.");
		}
		if(pt == null){
			throw new ServiceException("PaymentType must not be null.");
		}
		
		try {	
			return receiptDao.createReceiptforEntries(entries, pt);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

}
