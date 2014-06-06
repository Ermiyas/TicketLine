package at.ac.tuwien.inso.tl.dao;

import java.util.List;

import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;


public interface ReceiptDaoCustom {
	
	/**
	 * Erstellt einen Rechnungsbeleg fuer die uebergebenen Entries mit einem bestimmten PaymentType 
	 * @param entries Liste der Entries fuer die ein Rechnungsbeleg erstellt werden soll
	 * @param pt Die Art der Bezahlung in einer Enumeration PaymentType
	 * @return Das erstellte Rechnungsbeleg Dto
	 */
	public Receipt createReceiptforEntries(List<Entry> entries,PaymentType pt) ;

}
