package at.ac.tuwien.inso.tl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import at.ac.tuwien.inso.tl.model.Artist;

@Repository
public interface ArtistDao extends JpaRepository<Artist, Integer>, ArtistDaoCustom{
	
}
