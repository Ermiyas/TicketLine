package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface EntryService {

	// TODO getById(Integer id), create(Entry entry), find(Entry entry), update(Entry entry), deleteById(Integer id), getAll(), ...

	/**
	 * Finds the given basket object and returns the found entities
	 * 
	 * @param entry object with its properties as search parameters
	 * @return the found entities
	 * @throws ServiceException
	 */
	public List<Entry> getListByBasketId(Integer id) throws ServiceException;
	
}