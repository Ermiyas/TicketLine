package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.model.News;

@Repository
public interface NewsDao extends JpaRepository<News, Integer>{
	
	final String orderByExpression = " ORDER BY n.submittedOn DESC"; 
	
	@Query(value="SELECT n FROM News n" + orderByExpression)
	public List<News> findAllOrderBySubmittedOnDesc(); 	
	
	@Query(value="SELECT * FROM NEWS n WHERE NOT EXISTS (SELECT * FROM NEWSREAD nr WHERE nr.NEWS_ID = n.ID AND EMPLOYEE_ID = :employee_id)" + orderByExpression, nativeQuery=true)
	public List<News> findAllUnreadNewsOfEmployee(@Param("employee_id") Integer employee_id);

	@Query(value="SELECT n FROM News n WHERE lower(n.title) LIKE lower(:title)" + orderByExpression)
	public List<News> search(@Param("title") String title);
	
	@Query(value="SELECT n FROM News n LEFT JOIN FETCH n.readBy WHERE n.id = :news_id" + orderByExpression)
	public News getNewsWithReadBy(@Param("news_id") Integer news_id);
	
	@Query(value="SELECT COUNT(NEWS_ID) FROM NEWSREAD WHERE NEWS_ID = :news_id AND EMPLOYEE_ID = :employee_id", nativeQuery=true)
	public Integer getTimesNewsReadByEmployee(@Param("news_id") Integer news_id, @Param("employee_id") Integer employee_id);

	@Modifying
	@Transactional(readOnly=false)
	@Query(value="DELETE FROM NEWSREAD WHERE NEWS_ID = :news_id AND EMPLOYEE_ID = :employee_id", nativeQuery=true)
	public void deleteNewsreadEntry(@Param("news_id") Integer news_id, @Param("employee_id") Integer employee_id);

}
