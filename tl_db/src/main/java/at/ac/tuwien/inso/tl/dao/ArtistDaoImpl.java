package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Artist;

public class ArtistDaoImpl implements ArtistDaoCustom {

	private static final Logger LOG = Logger.getLogger(ArtistDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Artist> findArtists(String firstName, String lastName) {
		LOG.info("findArtistsByName called.");	
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM artist");
				
		
		if(firstName != null || lastName != null)
		{
			LOG.debug("Adding WHERE-Clauses.");
			sb.append(" WHERE ");
		}					
		
		if(firstName != null)
		{				
			sb.append("lower(firstname) LIKE CONCAT('%', lower(:FIRSTNAME), '%')");			
		}
		if(lastName != null)
		{
			if(firstName != null)
			{
				sb.append(" AND ");				
			}
			sb.append("lower(lastname) LIKE CONCAT('%', lower(:LASTNAME), '%')");
		}			
		
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Artist.class);
		
		LOG.debug("Set Parameters");
		if(firstName != null)
		{		
			query.setParameter("FIRSTNAME", firstName);						
		}
		if(lastName != null)
		{
			query.setParameter("LASTNAME", lastName);			
		}		
			
		List<Artist> result = new ArrayList<Artist>();
		
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
			result.add((Artist)o);
		return result;	
	}

}
