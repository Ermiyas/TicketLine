package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Basket;


public interface BasketDaoCustom {
	
	
	/**
	 * Setzt einen customer fuer den Basket
	 * @param basket 
	 * @param customer_id 
	 */
	public void setCustomerForBasket(Basket basket,Integer customer_id);
	
	/**
	 * 
	 * @param basket_id
	 * @param customers
	 * @return List<BasketDto>, wobei entweder die ID uebereinstimmt und/oder die ID des Kunden in der customer-Liste enthalten ist.
	 */
	public List<Basket> findBasket(Integer basket_id, List<Integer> customers);

}
