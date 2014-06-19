package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import at.ac.tuwien.inso.tl.model.Article;

public interface ArticleDao extends JpaRepository<Article, Integer> {		
	
	@Query(value="SELECT a FROM Article a WHERE a.priceInCent is not null")
	public List<Article> getAllMerchandising();
	
	@Query(value="SELECT a FROM Article a WHERE a.priceInPoints is not null")
	public List<Article> getAllBonus();
	
}
