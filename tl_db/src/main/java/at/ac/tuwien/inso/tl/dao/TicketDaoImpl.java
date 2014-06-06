package at.ac.tuwien.inso.tl.dao;

import java.util.AbstractMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.model.Ticket;


public class TicketDaoImpl implements TicketDaoCustom {

	private static final Logger LOG = Logger.getLogger(TicketDaoImpl.class);
	
	private static final String ticketForEntryQuery = 
			"SELECT t.id, t.show_id from ENTRY e JOIN TICKET t ON e.ticket_id = t.id where e.id = :ID ";
	private static final String deleteEntryWithTicketId = 
			"DELETE FROM entry WHERE ticket_id = :ID";
	private static final String deleteTicketWithTicketId = 
			"DELETE FROM ticket WHERE id = :ID";
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Ticket createTicket(Integer show_id, Integer seat_id,
			Integer entry_id) {
		LOG.info("createTicket called.");	
		
		//LOG.debug("Creating SQL-Statement.");
		Ticket ticket = null;
		
		
		
		//TODO STORED PROCEDURE
		
		/*
		StringBuilder sb = new StringBuilder("INSERT INTO ticket(show_id) VALUES ");
		
		
		if(show_id != null){
			LOG.debug("show_id = "+show_id);
			
		}
		*/
		
		/*
		if( != null)
		{
			LOG.debug("Adding join.");
			sb.append(" INNER JOIN participation pa ON (p.ID = pa.performance_id)");
		}	
				
		LOG.debug("Perparing SQL-Statement.");
		Query query = em.createNativeQuery("I nvl(min(durationInMinutes), 0),  nvl(max(durationInMinutes), 0) FROM performance;");
		LOG.debug("Executing query.");
		Object[] result = (Object[])query.getSingleResult();
		LOG.debug("Extracting results.");
		
		*/
		
		return ticket;
	}

	@Override
	public Map.Entry<Ticket, Boolean> getTicketForEntry(
			Integer entry_id) {

		LOG.info("getTicketForEntry called.");	
		
		
		Query query = em.createNativeQuery(ticketForEntryQuery, Ticket.class);
		query.setParameter("ID", entry_id);
		
		LOG.debug("Executing query");
		
		Ticket tResult = (Ticket) query.getSingleResult();
		Boolean sitzplatz = null;
		if(tResult.getShow() == null){
			sitzplatz = true;
		}
		else sitzplatz = false;
		
		return new AbstractMap.SimpleEntry<Ticket, Boolean>(tResult,sitzplatz);
	}

	@Override
	public void undoTicket(Integer ticket_id) {
		LOG.info("undoTicket called.");	
		
		Query query1 = em.createNativeQuery(deleteEntryWithTicketId);
		query1.setParameter("ID", ticket_id);
		
		LOG.debug("Executing query");
		query1.executeUpdate();
		
		Query query2 = em.createNativeQuery(deleteTicketWithTicketId);
		query2.setParameter("ID", ticket_id);
		
		LOG.debug("Executing query");
		query2.executeUpdate();
		
	}

}
