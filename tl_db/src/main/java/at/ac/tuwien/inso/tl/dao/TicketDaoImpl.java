package at.ac.tuwien.inso.tl.dao;

import java.util.AbstractMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import at.ac.tuwien.inso.tl.model.Ticket;

@Transactional
public class TicketDaoImpl implements TicketDaoCustom {

	private static final Logger LOG = Logger.getLogger(TicketDaoImpl.class);
	
	private static final String ticketForEntryQuery = 
			"SELECT t.id, t.show_id from ENTRY e JOIN TICKET t ON e.ticket_id = t.id where e.id = :id ";
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public Map.Entry<Ticket, Boolean> getTicketForEntry(
			Integer entry_id) {

		LOG.info("getTicketForEntry called.");	
		
		
		Query query = em.createNativeQuery(ticketForEntryQuery, Ticket.class);
		query.setParameter("id", entry_id);
		
		LOG.debug("Executing query");
		
		Ticket tResult = (Ticket) query.getSingleResult();
		Boolean sitzplatz = null;
		if(tResult.getShow() == null){
			sitzplatz = true;
		}
		else sitzplatz = false;
		
		return new AbstractMap.SimpleEntry<Ticket, Boolean>(tResult,sitzplatz);
	}


}

