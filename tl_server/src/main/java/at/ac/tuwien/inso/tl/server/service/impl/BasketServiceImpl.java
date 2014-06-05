package at.ac.tuwien.inso.tl.server.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.ac.tuwien.inso.tl.dao.BasketDao;
import at.ac.tuwien.inso.tl.dao.PropertySpecifiations;
import at.ac.tuwien.inso.tl.model.Basket;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;
import at.ac.tuwien.inso.tl.server.service.BasketService;

@Service
public class BasketServiceImpl implements BasketService {
	
	@Autowired
	private BasketDao basketDao;

	// TODO create(Basket basket), find(Basket basket), update(Basket basket), deleteById(Integer id), 
	
	@Override
	public Basket getById(Integer id) throws ServiceException {
		try {
			return basketDao.findOne(id);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Basket> getAll() throws ServiceException {
		try {
			return basketDao.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	// Zum Testen.
	public void setBasketDao(BasketDao dao) {
		this.basketDao = dao;
	}

}