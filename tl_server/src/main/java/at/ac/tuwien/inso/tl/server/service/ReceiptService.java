package at.ac.tuwien.inso.tl.server.service;

import java.util.List;

import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.model.Entry;
import at.ac.tuwien.inso.tl.model.PaymentType;
import at.ac.tuwien.inso.tl.model.Receipt;
import at.ac.tuwien.inso.tl.server.exception.ServiceException;

public interface ReceiptService {
	/**
	 * Erstellt einen Rechnungsbeleg fuer die uebergebenen Entries mit einem bestimmten PaymentType 
	 * @param entries Liste der Entries fuer die ein Rechnungsbeleg erstellt werden soll
	 * @param pt Die Art der Bezahlung in einer Enumeration PaymentType
	 * @return Der erstellte Rechnungsbeleg Dto sowie die neuen Punkte des customers
	 */
	public KeyValuePairDto<Receipt, Integer> createReceiptforEntries(List<Entry> entries,PaymentType pt)  throws ServiceException;

}
