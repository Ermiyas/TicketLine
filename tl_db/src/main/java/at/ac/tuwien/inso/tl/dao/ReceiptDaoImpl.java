package at.ac.tuwien.inso.tl.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;

public class ReceiptDaoImpl implements ReceiptDaoCustom {
	
	private static final Logger LOG = Logger.getLogger(ReceiptDaoImpl.class);
	
	private static final String updateEntryWithTicketId =
			"UPDATE Entry SET receipt_id = :receipt_id WHERE id = :ID";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	ReceiptDao rdao;

	@Override
	public Receipt createReceiptforEntries(List<Entry> entries, PaymentType pt) {
		LOG.info("createReceiptforEntries called.");	
		
		Receipt r = new Receipt();
		
		r.setEntries(entries);
		r.setPaymentType(pt);
		r.setTransactionDate(new Date(System.currentTimeMillis()));
		r = rdao.saveAndFlush(r);
		
		Query query = em.createNativeQuery(updateEntryWithTicketId);
		query.setParameter("receipt_id", r.getId());
		
		for(Entry e: entries){
			LOG.debug("Update Entry "+e.getId()+" with receipt_id "+r.getId());
			query.setParameter("ID", e.getId());
			query.executeUpdate();
		}
		
		return r;
	}

}
