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

import at.ac.tuwien.inso.tl.model.Row;

public class RowDaoImpl implements RowDaoCustom {
	
	private static final Logger LOG = Logger.getLogger(RowDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Row> findRows(Integer showID) {
		LOG.info("findRows called.");	
		CriteriaBuilder cb =  em.getCriteriaBuilder();		
		CriteriaQuery<Row> cq = cb.createQuery(Row.class);
		Root<Row> row = cq.from(Row.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(showID != null)
		{		
			predicates.add(cb.equal(row.get("show"), showID));			
		}				
		
	    cq.select(row).where(predicates.toArray(new Predicate[]{}));
		return em.createQuery(cq).getResultList();						
	}

}
