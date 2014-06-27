package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Basket;

@Repository
public interface BasketDao extends JpaRepository<Basket, Integer>  {
	
	@Query(value="SELECT b FROM Basket b WHERE customer_id IN :customers")
	public List<Basket> findByCustomer_ids(@Param("customers") List<Integer> customers);
	
	@Query(value="SELECT b FROM Basket b WHERE id = :id AND customer_id IN :customers")
	public List<Basket> findByBasket_idAndCustomer_ids(@Param("id") Integer id, @Param("customers") List<Integer> customers);
	
}
