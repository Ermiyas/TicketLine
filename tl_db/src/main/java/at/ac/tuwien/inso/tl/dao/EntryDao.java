package at.ac.tuwien.inso.tl.dao;


import at.ac.tuwien.inso.tl.model.Entry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntryDao extends JpaRepository<Entry, Integer>, EntryDaoCustom{
	
	@Query(value="SELECT e FROM Entry e WHERE basket_id = :id")
	public List<Entry> findByBasket_id(@Param("id") Integer id);
	
	@Query(value="SELECT e FROM Entry e WHERE ticket_id = :id")
	public Entry findByTicket_id(@Param("id") Integer id);
	
}
