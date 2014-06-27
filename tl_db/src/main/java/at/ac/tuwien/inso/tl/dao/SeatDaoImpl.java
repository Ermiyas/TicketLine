package at.ac.tuwien.inso.tl.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Seat;

public class SeatDaoImpl implements SeatDaoCustom {
	private static final Logger LOG = Logger.getLogger(SeatDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	// TODO Temporaerloesung v. Robert, durch endgueltige Implementierung ersetzen
	@Override
	public Seat findSeatByTicketId(Integer id) {
		LOG.info("findSeatByTicketId called.");	
		if(id == null)
		{
			return null;
		}

		Seat result = new Seat();
		
		LOG.debug("Creating SQL-Statement.");
		StringBuilder sb = new StringBuilder("SELECT * FROM seat WHERE ticket_id = :ID");
				
		String sqlQuery = sb.toString();
		LOG.debug("Query: " + sqlQuery);
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery(sqlQuery, Seat.class);
		
		LOG.debug("Set Parameters");
		query.setParameter("ID", id);						
			
		LOG.debug("Executing query");
		result = (Seat) query.getSingleResult();
		return result;	
	}		

	@Override
	public List<Seat> findSeats(Integer rowID) {		
		LOG.info("findSeats called.");	
	
		CriteriaBuilder cb =  em.getCriteriaBuilder();		
		CriteriaQuery<Seat> cq = cb.createQuery(Seat.class);
		Root<Seat> seat = cq.from(Seat.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(rowID != null)
		{		
			predicates.add(cb.equal(seat.get("row"), rowID));			
		}				
		
	    cq.select(seat).where(predicates.toArray(new Predicate[]{}));
	    cq.orderBy(cb.asc(seat.get("sequence")));
		return em.createQuery(cq).getResultList();							
	}

	@Override
	public List<Seat> getAllSeatsForShow(int showID) {
		LOG.info("getAllSeatsForShow called.");	
		
		Query query = em.createNativeQuery(
				"SELECT se.ID, se.Sequence, se.Row_ID, se.Ticket_ID "+
				"FROM SEAT se "+
				"LEFT OUTER JOIN Ticket t ON (se.TICKET_ID = t.ID) "+
				"LEFT OUTER JOIN ENTRY e ON (t.ID = e.Ticket_ID) "+
				"LEFT OUTER JOIN ROW ro ON (se.ROW_ID = ro.ID) "+
				"INNER JOIN SHOW s ON (s.ID = t.SHOW_ID OR s.ID = ro.SHOW_ID) "+
				"WHERE t.Show_ID = :sID OR s.ID = :sID ;", Seat.class); 
		query.setParameter("sID", showID);		
		LOG.debug("Executing query");
			
		List<Seat> result = new ArrayList<Seat>();
		for(Object o: query.getResultList())
		{
			result.add((Seat)o);
		}
		return result;	
	}		

	
}
