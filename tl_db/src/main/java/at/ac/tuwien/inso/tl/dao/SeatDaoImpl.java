package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Seat;

public class SeatDaoImpl implements SeatDaoCustom {
	private static final Logger LOG = Logger.getLogger(SeatDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Seat> findSeats(Integer rowID) {
		
		LOG.info("findSeats called.");	
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM seat");	
		
		if(rowID != null)
		{
			LOG.debug("Adding WHERE-Clauses.");
			sb.append(" WHERE ");
		}				
		//FOR FUTURE USE
		//boolean isFirstWhereClause = true;
		
		if(rowID != null)
		{
			//FOR FUTURE USE
			//isFirstWhereClause = false;
			sb.append("row_id = :ROWID");
		}
		
		sb.append(";");
		
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Seat.class);
		
		LOG.debug("Set Parameters");		

		if(rowID != null)
		{			
			query.setParameter("ROWID", rowID);			
		}			
								
		List<Seat> result = new ArrayList<Seat>();
		
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
		{
			result.add((Seat)o);
		}			
	
		return result;								
	}		
}
