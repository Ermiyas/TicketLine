package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Show;

@Repository
public interface ShowDao extends JpaRepository<Show, Integer>, ShowDaoCustom{

	@Query(value="SELECT s FROM Show s WHERE performance_id = :id")
	public List<Show> findByPerformance_id(@Param("id") Integer id); 
	
	@Query(value="SELECT s FROM Show s WHERE location_id = :id")
	public List<Show> findByLocation_id(@Param("id") Integer id); 

}
