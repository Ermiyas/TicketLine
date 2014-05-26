package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Show;

public class ShowDaoImpl implements ShowDaoCustom {

	private static final Logger LOG = Logger.getLogger(ShowDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Show> findShows(Date dateFrom, Date dateTo, Date timeFrom,
			Date timeTo, Integer priceInCentFrom, Integer priceInCentTo,
			String room, Integer locationID, Integer performanceID) {
		LOG.info("findShows called.");	
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM show");	
		
		if(dateFrom != null || dateTo != null || timeFrom != null || timeTo != null
				|| priceInCentFrom != null || priceInCentTo != null 
				|| room != null || locationID != null || performanceID != null)
		{
			LOG.debug("Adding WHERE-Clauses.");
			sb.append(" WHERE ");
		}				
		
		boolean isFirstWhereClause = true;
		
		if(dateFrom != null)
		{
			isFirstWhereClause = false;
			sb.append("CAST(dateofperformance AS DATE) >=  :DATEFROM");
		}
		
		if(dateTo != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("CAST(dateofperformance AS DATE) <=  :DATETO");
		}

		if(timeFrom != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("CAST(dateofperformance AS TIME) >=  :TIMEFROM");
		}
		
		if(timeTo != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("CAST(dateofperformance AS TIME) <=  :TIMETO");
		}
		
		if(priceInCentFrom != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("priceincent >= :PRICEFROM");
		}
		if(priceInCentTo != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("priceincent <= :PRICETO");			
		}
		
		if(room != null)
		{		
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}					
			sb.append("lower(room) LIKE %lower(:ROOM)%");			
		}
		
		if(locationID != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("location_ID = :LOCATIONID");
		}
	
		if(performanceID != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
				isFirstWhereClause = false;
			}
			sb.append("performance_ID = :PERFORMANCEID");
		}					
		
		sb.append(";");
		
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Show.class);
		
		LOG.debug("Set Parameters");		
		
		if(dateFrom != null)
		{			
			query.setParameter("DATEFROM", dateFrom, TemporalType.DATE);			
		}
		
		if(dateTo != null)
		{
			query.setParameter("DATETO", dateTo, TemporalType.DATE);			
		}

		if(timeFrom != null)
		{
			query.setParameter("TIMEFROM", timeFrom, TemporalType.TIME);			
		}
		
		if(timeTo != null)
		{
			query.setParameter("TIMETO", timeTo, TemporalType.TIME);					
		}
		
		if(priceInCentFrom != null)
		{
			query.setParameter("PRICEFROM", priceInCentFrom);			
		}
		
		if(priceInCentTo != null)
		{
			query.setParameter("PRICETO", priceInCentTo);					
		}
		
		if(room != null)
		{		
			query.setParameter("ROOM", room);								
		}
		
		if(locationID != null)
		{
			query.setParameter("LOCATIONID", locationID);					
		}
	
		if(performanceID != null)
		{				
			query.setParameter("PERFORMANCEID", performanceID);				
		}				
								
		List<Show> result = new ArrayList<Show>();
		
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
		{
			result.add((Show)o);
		}			
	
		return result;				
	}

	@Override
	public int[] getMinMaxPriceInCent() {
		LOG.info("getMinMaxPriceInCent called.");			
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery("SELECT nvl(min(priceInCent), 0),  nvl(max(priceInCent), 0) FROM show;");
		LOG.debug("Executing query.");
		Object[] result = (Object[])query.getSingleResult();
		LOG.debug("Extracting results.");
		return new int[] {(int)result[0], (int)result[1]};		
	}

}
