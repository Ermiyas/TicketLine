package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Basket;

public class BasketDaoImpl implements BasketDaoCustom {
	
	private static final Logger LOG = Logger.getLogger(BasketDaoImpl.class);
	private static final String updateBasketWithCustomerId =
			"UPDATE Basket SET customer_id = :customer_id WHERE id = :id";
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public void setCustomerForBasket(Basket basket, Integer customer_id) {
		LOG.info("setCustomerForBasket called");
		Query query = em.createNativeQuery(updateBasketWithCustomerId);
		query.setParameter("customer_id", customer_id);
		query.setParameter("id", basket.getId());
		query.executeUpdate();

	}

	@Override
	public List<Basket> findBasket(Integer basket_id, List<Integer> customers) {
		LOG.info("findBasket called");
		// TODO Auto-generated method stub
		return null;
	}

}
