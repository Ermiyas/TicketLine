package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Artist;

public class ArtistDaoImpl implements ArtistDaoCustom {

	private static final Logger LOG = Logger.getLogger(ArtistDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Artist> findArtists(String firstName, String lastName) {
		LOG.info("findArtistsByName called.");	
		CriteriaBuilder cb =  em.getCriteriaBuilder();		
		CriteriaQuery<Artist> cq = cb.createQuery(Artist.class);
		Root<Artist> artist = cq.from(Artist.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();					
		
		if(firstName != null)
		{
			predicates.add(cb.like(cb.upper(artist.<String>get("firstname")), "%" + firstName.toUpperCase() + "%"));
		}
		if(lastName != null)
		{
			predicates.add(cb.like(cb.upper(artist.<String>get("lastname")), "%" + lastName.toUpperCase() + "%"));
		}
				
	    cq.select(artist).where(predicates.toArray(new Predicate[]{}));
		return em.createQuery(cq).getResultList();			
	}

}
