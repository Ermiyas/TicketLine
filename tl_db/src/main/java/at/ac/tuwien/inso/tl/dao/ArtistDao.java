package at.ac.tuwien.inso.tl.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import at.ac.tuwien.inso.tl.model.Artist;

public interface ArtistDao extends JpaRepository<Artist, Integer>, ArtistDaoCustom{

}
