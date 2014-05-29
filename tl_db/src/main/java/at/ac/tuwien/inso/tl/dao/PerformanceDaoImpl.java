package at.ac.tuwien.inso.tl.dao;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Performance;
import at.ac.tuwien.inso.tl.model.Row;
import at.ac.tuwien.inso.tl.model.Seat;
import at.ac.tuwien.inso.tl.model.Show;

public class PerformanceDaoImpl implements PerformanceDaoCustom {
	
	private static final Logger LOG = Logger.getLogger(PerformanceDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Map.Entry<Performance, Integer>> findPerformancesSortedBySales(String content,
			String description, Integer durationInMinutesFrom,
			Integer durationInMinutesTo, String performanceType,
			Integer artistID) {
		LOG.info("findPerformancesSortedBySales called.");	
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT p.id, p.content, p.description, p.durationInMinutes, p.performanceType FROM performance p");
		if(artistID != null)
		{
			LOG.debug("Adding join.");
			sb.append(" INNER JOIN participation pa ON (p.ID = pa.performance_id)");
		}		
		
		if(content != null || description != null || durationInMinutesFrom != null || durationInMinutesTo != null
				|| performanceType != null || artistID != null)
		{
			LOG.debug("Adding WHERE-Clauses.");
			sb.append(" WHERE ");
		}				
		
		boolean isFirstWhereClause = true;
		if(content != null)
		{			
			isFirstWhereClause = false;
			sb.append("lower(p.content) LIKE CONCAT('%', lower(:CONTENT), '%')");			
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
			sb.append("lower(p.description) LIKE CONCAT('%', lower(:DESCRIPTION), '%')");
		}
		if(durationInMinutesFrom != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");				
			}
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("p.durationInMinutes >= :DURATIONFROM");
		}
		if(durationInMinutesTo != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");				
			}
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("p.durationInMinutes <= :DURATIONTO");			
		}
		if(performanceType != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");						
			}
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("lower(p.performanceType) LIKE CONCAT('%', lower(:PERFORMANCETYPE), '%')");
		}
		if(artistID != null)
		{
			if(!isFirstWhereClause)
			{
				sb.append(" AND ");
			}
			else
			{
				isFirstWhereClause = false;
			}
			sb.append("pa.artist_id = :ARTISTID");
		}		
		
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Performance.class);
		
		LOG.debug("Set Parameters");
		if(content != null)
		{		
			query.setParameter("CONTENT", content);						
		}
		if(description != null)
		{
			query.setParameter("DESCRIPTION", description);			
		}
		if(durationInMinutesFrom != null)
		{
			query.setParameter("DURATIONFROM", durationInMinutesFrom);				
		}
		if(durationInMinutesTo != null)
		{
			query.setParameter("DURATIONTO", durationInMinutesTo);						
		}
		if(performanceType != null)
		{
			query.setParameter("PERFORMANCETYPE", performanceType);								
		}
		if(artistID != null)
		{
			query.setParameter("ARTISTID", artistID);			
		}
			
		List<Map.Entry<Performance, Integer>> result = new ArrayList<Map.Entry<Performance, Integer>>();
		
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
		{
			Performance p = (Performance)o;
			int sales = 0;
			
			for(Show s: p.getShows())
			{
				Set<Row> rows = s.getRows();
				sales += s.getTickets().size();							
				for(Row r: rows)
					for(Seat se: r.getSeats())
						if(se.getTicket() != null)
							sales++;
			}			
			result.add(new AbstractMap.SimpleEntry<Performance, Integer>(p, sales));
		}		
		
		Collections.sort(result, new Comparator<Map.Entry<Performance, Integer>>()
				{
						public int compare(Map.Entry<Performance, Integer> o1, Map.Entry<Performance, Integer> o2)
						{
							return -1 * (o1.getValue().compareTo(o2.getValue()));
						}
				});
		
		return result;				
	}		

	@Override
	public int[] getMinAndMaxDuration() {
		LOG.info("getMinAndMaxDuration called.");			
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery("SELECT nvl(min(durationInMinutes), 0),  nvl(max(durationInMinutes), 0) FROM performance;");
		LOG.debug("Executing query.");
		Object[] result = (Object[])query.getSingleResult();
		LOG.debug("Extracting results.");
		return new int[] {(int)result[0], (int)result[1]};
	}

	@Override
	public List<String> getAllPerformanceTypes() {

		LOG.info("getAllPerformanceTypes called.");			
		LOG.debug("Perparing SQL-Statement.");		
		Query query = em.createNativeQuery("SELECT DISTINCT performancetype FROM performance;");
		List<String> result = new ArrayList<String>();
		LOG.debug("Executing query.");				
		for(Object o: query.getResultList())
			result.add((String)o);
		return result;			
	}	
	
}
