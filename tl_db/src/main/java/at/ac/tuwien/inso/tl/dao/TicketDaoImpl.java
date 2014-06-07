package at.ac.tuwien.inso.tl.dao;

import java.util.AbstractMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Ticket;


public class TicketDaoImpl implements TicketDaoCustom {

	private static final Logger LOG = Logger.getLogger(TicketDaoImpl.class);
	
	private static final String ticketForEntryQuery = 
			"SELECT t.id, t.show_id from ENTRY e JOIN TICKET t ON e.ticket_id = t.id where e.id = :ID ";
	private static final String deleteEntryWithTicketId = 
			"DELETE FROM entry WHERE ticket_id = :ID";
	private static final String deleteTicketWithTicketId = 
			"DELETE FROM ticket WHERE id = :ID";
	private static final String updateTicketWithShowId =
			"UPDATE Ticket SET show_id = :show_id WHERE id = :id";
	private static final String updateEntryWithTicketId =
			"UPDATE Entry SET ticket_id = :ticket_id WHERE id = :id";
	private static final String updateSeatWithTicketId =
			"UPDATE Seat SET ticket_id = :ticket_id WHERE id = :id";
	
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	TicketDao tdao;
	
	@Autowired
	EntryDao edao;
	
	@Override
	public Ticket createTicket(Integer show_id, Integer seat_id,
			Integer entry_id) {
		LOG.info("createTicket called.");	
		
		Ticket t = new Ticket();
		t = tdao.saveAndFlush(t);
		
		if(entry_id != null){
			Query query = em.createNativeQuery(updateEntryWithTicketId);
			query.setParameter("ticket_id", t.getId());
			query.setParameter("id", entry_id);
			query.executeUpdate();
		}
		
		if(show_id == null && seat_id != null){
			Query query = em.createNativeQuery(updateSeatWithTicketId);
			query.setParameter("ticket_id", t.getId());
			query.setParameter("id", seat_id);
			query.executeUpdate();
		}
		else if(seat_id == null){
			Query query = em.createNativeQuery(updateTicketWithShowId);
			query.setParameter("show_id", show_id);
			query.setParameter("id", t.getId());
			query.executeUpdate();
		}
		
		
		return t;
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
		
		tdao.delete(ticket_id);
		
		/*
		
		
		Query query1 = em.createNativeQuery(deleteEntryWithTicketId);
		query1.setParameter("ID", ticket_id);
		
		LOG.debug("Executing query");
		query1.executeUpdate();
		
		Query query2 = em.createNativeQuery(deleteTicketWithTicketId);
		query2.setParameter("ID", ticket_id);
		
		LOG.debug("Executing query");
		query2.executeUpdate();
		*/
		
	}

}
