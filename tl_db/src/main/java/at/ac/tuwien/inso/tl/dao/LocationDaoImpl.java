package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM location");
				
		
		if(city != null || country != null || description != null || postalCode != null || street != null)
		{
			LOG.debug("Adding WHERE-Clauses.");
			sb.append(" WHERE ");
		}					
		
		boolean isFirstWhereClause = true;
		
		if(city != null)
		{				
			isFirstWhereClause = false;
			sb.append("lower(city) LIKE CONCAT('%', lower(:CITY), '%')");			
		}
		
		if(country != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");				
			}						
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("lower(country) LIKE CONCAT('%', lower(:COUNTRY), '%')");
		}
		
		if(description != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");				
			}
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("lower(description) LIKE CONCAT('%', lower(:DESCRIPTION), '%')");
		}

		if(postalCode != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");				
			}	
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("lower(postalcode) LIKE CONCAT('%', lower(:POSTALCODE), '%')");
		}

		if(street != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");				
			}	
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("lower(street) LIKE CONCAT('%', lower(:STREET), '%')");
		}

		
		
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Location.class);
		
		LOG.debug("Set Parameters");
		
		if(city != null)
		{				
			query.setParameter("CITY", city);						
		}
		
		if(country != null)
		{
			query.setParameter("COUNTRY", country);			
		}
		
		if(description != null)
		{
			query.setParameter("DESCRIPTION", description);		
		}

		if(postalCode != null)
		{
			query.setParameter("POSTALCODE", postalCode);				
		}

		if(street != null)
		{
			query.setParameter("STREET", street);			
		}		
			
		List<Location> result = new ArrayList<Location>();
		
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
			result.add((Location)o);
		return result;	
	}

}
