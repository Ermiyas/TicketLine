package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.News;

@Repository
public interface NewsDao extends JpaRepository<News, Integer>{
	@Query(value="SELECT n FROM News n ORDER BY n.submittedOn DESC")
	public List<News> findAllOrderBySubmittedOnDesc(); 	
}
