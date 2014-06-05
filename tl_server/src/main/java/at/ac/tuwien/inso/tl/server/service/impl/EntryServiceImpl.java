package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.EntryDao;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.EntryService;

@Service
public class EntryServiceImpl implements EntryService {
	
	private static final Logger LOG = Logger.getLogger(EntryServiceImpl.class);
	
	@Autowired
	private EntryDao entryDao;

	// TODO getById(Integer id), create(Entry entry), find(Entry entry), update(Entry entry), deleteById(Integer id), getAll(), ...

	@Override
	public List<Entry> getListByBasketId(Integer id) throws ServiceException {
		LOG.info("getListByBasketId called.");

		try {
			return entryDao.findEntriesByBasketId(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// Zum Testen.
	public void setEntryDao(EntryDao dao) {
		this.entryDao = dao;
	}

}