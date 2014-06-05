package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Entry;

public class EntryDaoImpl implements EntryDaoCustom {

	private static final Logger LOG = Logger.getLogger(EntryDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Entry> findEntriesByBasketId(Integer id) {
		LOG.info("findEntriesByBasketId called.");	
		List<Entry> result = new ArrayList<Entry>();
		if (id == null) {
			return result;
		}
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM entry WHERE Basket_ID = :ID");
				
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Entry.class);
		
		LOG.debug("Set Parameters");
		query.setParameter("ID", id);						
			
		LOG.debug("Executing query");
		for(Object o: query.getResultList())
			result.add((Entry)o);
		return result;	
	}

}
