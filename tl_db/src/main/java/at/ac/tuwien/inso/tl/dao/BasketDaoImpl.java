package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Basket;

public class BasketDaoImpl implements BasketDaoCustom {
	
	private static final Logger LOG = Logger.getLogger(BasketDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	@Override
	public void undoBasket(Integer basket_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCustomerForBasket(Basket basket, Integer customer_id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Basket> findBasket(Integer basket_id, List<Integer> customers) {
		// TODO Auto-generated method stub
		return null;
	}

}
