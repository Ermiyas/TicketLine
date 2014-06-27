package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;

@Repository
public interface SeatDao extends JpaRepository<Seat, Integer>, SeatDaoCustom{
	
	@Modifying
	@Query(value="UPDATE Seat SET ticket_id = :ticket_id WHERE id = :id")
	public List<Show> updateWithTicket_id(@Param("ticket_id") Integer ticket_id , @Param("id") Integer id); 
}
