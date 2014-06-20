package at.ac.tuwien.inso.tl.dao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import at.ac.tuwien.inso.tl.model.Seat;

public class EntryDaoImpl implements EntryDaoCustom {

	private static final Logger LOG = Logger.getLogger(EntryDaoImpl.class);
	private static final String isSeatSold = 
			"SELECT sold FROM  TICKET t JOIN ENTRY e ON t.id = e.ticket_id JOIN SEAT s ON t.id = s.ticket_id WHERE s.id = :id";
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Boolean isSold(Integer seat_id) {
		LOG.info("isSold called.");
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(isSeatSold, Seat.class);
		query.setParameter("id", seat_id);
		LOG.debug("Executing query.");
		
		Boolean sold = (Boolean) query.getSingleResult();
		LOG.info("Is Entry sold: " + sold);
		return sold;
	}

}
