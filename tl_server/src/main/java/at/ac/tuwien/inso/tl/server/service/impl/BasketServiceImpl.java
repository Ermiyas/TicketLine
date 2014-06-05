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

	@Override
	public Basket getById(Integer id) throws ServiceException {
		
		try {
			
			return basketDao.findOne(id);
			
		} catch (Exception e) {
			
			throw new ServiceException(e);
			
		}
	}

//	@Override
//	public Basket create(Basket basket) throws ServiceException {
//		
//		try {
//			
//			return basketDao.save(basket);
//			
//		} catch (Exception e) {
//			
//			throw new ServiceException(e);
//			
//		}
//	}
//
//	@Override
//	public List<Basket> find(Basket basket) throws ServiceException {
//		
//		List<Basket> results = null;
//
//		results = basketDao.findAll(PropertySpecifiations.searchMatch(basket));
//
//		return results;
//		
//	}
//
//	@Override
//	public Basket update(Basket basket) throws ServiceException {
//		
//		try {
//			
//			return basketDao.save(basket);
//			
//		} catch (Exception e) {
//			
//			throw new ServiceException(e);
//			
//		}
//	}
//
//	@Override
//	public void deleteById(Integer id) throws ServiceException {
//		
//		try {
//			
//			basketDao.delete(id);
//			
//		} catch (Exception e) {
//			
//			throw new ServiceException(e);
//			
//		}
//		
//	}

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