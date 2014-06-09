package at.ac.tuwien.inso.tl.dao;


import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;



import at.ac.tuwien.inso.tl.model.Performance;

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

		CriteriaBuilder cb =  em.getCriteriaBuilder();		
		CriteriaQuery<Performance> cq = cb.createQuery(Performance.class);
		Root<Performance> performance = cq.from(Performance.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();					
		
		if(content != null)
		{
			predicates.add(cb.like(cb.upper(performance.<String>get("content")), "%" + content.toUpperCase() + "%"));
		}
		if(description != null)
		{
			predicates.add(cb.like(cb.upper(performance.<String>get("description")), "%" + description.toUpperCase() + "%"));
		}
		if(durationInMinutesFrom != null)
		{
			predicates.add(cb.greaterThanOrEqualTo(performance.<Integer>get("durationInMinutes"), durationInMinutesFrom));
		}
		if(durationInMinutesTo != null)
		{
			predicates.add(cb.lessThanOrEqualTo(performance.<Integer>get("durationInMinutes"), durationInMinutesTo));			
		}
		if(performanceType != null)
		{
			predicates.add(cb.like(cb.upper(performance.<String>get("performancetype")), "%" + performanceType.toUpperCase() + "%"));
		}
		if(artistID != null)
		{								
			predicates.add(cb.isMember(artistID, performance.<Collection<Integer>>get("artists")));
		}
				
	    cq.select(performance).where(predicates.toArray(new Predicate[]{}));		
		
		List<Map.Entry<Performance, Integer>> result = new ArrayList<Map.Entry<Performance, Integer>>();
		
		for(Performance p: em.createQuery(cq).getResultList())
		{						
			StoredProcedureQuery ticketSales = em.createStoredProcedureQuery("ticketSales");
			ticketSales.registerStoredProcedureParameter("p_ID", Integer.class, ParameterMode.IN);
		    ticketSales.setParameter("p_ID", p.getId());				
			result.add(new AbstractMap.SimpleEntry<Performance, Integer>(p, (Integer)ticketSales.getSingleResult()));
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
