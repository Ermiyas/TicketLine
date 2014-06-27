package at.ac.tuwien.inso.tl.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Show;
import at.ac.tuwien.inso.tl.model.Ticket;

@Repository
public interface TicketDao extends JpaRepository<Ticket, Integer>, TicketDaoCustom {
	
	@Modifying
	@Query(value="DELETE FROM Ticket t WHERE t.id = :id")
	public List<Show> deleteById(@Param("id") Integer id); 
	
	
	@Modifying
	@Query(value="UPDATE Ticket t SET show_id = :show_id WHERE id = :id")
	public List<Show> updateWithShow_id(@Param("show_id") Integer show_id , @Param("id") Integer id); 
	
	/*
	@Query(value="SELECT t.id, t.show FROM Entry e JOIN Ticket t ON e.ticket_id = t.id where e.id = :id")
	public List<Ticket> findByEntry_id(@Param("id") Integer id); 
	*/
	
}
