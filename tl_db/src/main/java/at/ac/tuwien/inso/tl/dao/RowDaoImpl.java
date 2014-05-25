package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Row;

public class RowDaoImpl implements RowDaoCustom {
	
	private static final Logger LOG = Logger.getLogger(RowDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Row> findRows(Integer showID) {
		LOG.info("findRows called.");	
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM row");	
		
		if(showID != null)
		{
			LOG.debug("Adding WHERE-Clauses.");
			sb.append(" WHERE ");
		}				
		//FOR FUTURE USE
		//boolean isFirstWhereClause = true;
		
		if(showID != null)
		{
			//FOR FUTURE USE
			//isFirstWhereClause = false;
			sb.append("show_id = :SHOWID");
		}
		
		sb.append(";");
		
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Row.class);
		
		LOG.debug("Set Parameters");		

		if(showID != null)
		{			
			query.setParameter("SHOWID", showID);			
		}			
								
		List<Row> result = new ArrayList<Row>();
		
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
		{
			result.add((Row)o);
		}			
	
		return result;			
	}

}
