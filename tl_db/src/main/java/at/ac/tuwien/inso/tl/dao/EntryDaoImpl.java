package at.ac.tuwien.inso.tl.dao;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import at.ac.tuwien.inso.tl.model.Entry;

public class EntryDaoImpl implements EntryDaoCustom {

	private static final Logger LOG = Logger.getLogger(EntryDaoImpl.class);
	
	private static final String getAllEntriesWithBasketId =
			"SELECT e.id, e.amount, e.buyWithPoints, e.sold, e.article_id, e.basket_id, e.receipt_id, e.ticket_id FROM Entry e WHERE e.basket_id = :basket_id";
	
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
	public List<java.util.Map.Entry<Entry, Boolean>> getEntry(Integer basket_id) {
		
		LOG.info("getEntry called");
		
		ArrayList<Map.Entry<Entry, Boolean>> result = new ArrayList<Map.Entry<Entry, Boolean>>();
		
		Query query = em.createNativeQuery(getAllEntriesWithBasketId, Entry.class);
		query.setParameter("basket_id", basket_id);
		
		for(Object o: query.getResultList()){
			Entry e = (Entry) o;
			if(e.getTicket() == null && e.getArticle() == null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, null));
			}
			else if(e.getTicket() != null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, true));
			}
			else if(e.getArticle() != null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, false));
			}
		}
		
		/*
		List<Entry> entries = edao.getAllEntriesForBasketId(basket_id);
		
		for(Entry e: entries){
			if(e.getTicket() == null && e.getArticle() == null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, null));
			}
			else if(e.getTicket() != null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, true));
			}
			else if(e.getArticle() != null){
				result.add(new AbstractMap.SimpleEntry<Entry, Boolean>(e, false));
			}
		}
		*/
		
		return result;
	}

}
