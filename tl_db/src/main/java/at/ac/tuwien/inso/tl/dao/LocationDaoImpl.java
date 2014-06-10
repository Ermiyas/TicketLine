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

import at.ac.tuwien.inso.tl.model.Location;

public class LocationDaoImpl implements LocationDaoCustom {

	private static final Logger LOG = Logger.getLogger(LocationDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	
	@Override
	public List<Location> findLocations(String city, String country,
			String description, String postalCode, String street) {
		LOG.info("findLocations called.");	
		CriteriaBuilder cb =  em.getCriteriaBuilder();		
		CriteriaQuery<Location> cq = cb.createQuery(Location.class);
		Root<Location> location = cq.from(Location.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();					
		
		if(city != null)
		{
			predicates.add(cb.like(cb.upper(location.<String>get("city")), "%" + city.toUpperCase() + "%"));
		}
		if(country != null)
		{
			predicates.add(cb.like(cb.upper(location.<String>get("country")), "%" + country.toUpperCase() + "%"));
		}
		if(description != null)
		{
			predicates.add(cb.like(cb.upper(location.<String>get("description")), "%" + description.toUpperCase() + "%"));
		}		
		if(postalCode != null)
		{
			predicates.add(cb.like(cb.upper(location.<String>get("postalcode")), "%" + postalCode.toUpperCase() + "%"));
		}
		if(street != null)
		{
			predicates.add(cb.like(cb.upper(location.<String>get("street")), "%" + street.toUpperCase() + "%"));
		}		
		
	    cq.select(location).where(predicates.toArray(new Predicate[]{}));
		return em.createQuery(cq).getResultList();				
	}

}
