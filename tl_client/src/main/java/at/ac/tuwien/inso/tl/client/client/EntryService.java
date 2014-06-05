package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;

public interface EntryService {
	
	// TODO create(EntryDto entry), find(EntryDto entry), getById(Integer id), update(EntryDto entry), deleteById(Integer id), getAll(), ...

	public List<EntryDto> getList(BasketDto basket) throws ServiceException;		// get all Entries of a basket
	
}