package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

public interface ReceiptService {

	/**
	 * Erstellt einen Rechnungsbeleg fuer die uebergebenen Entries mit einem bestimmten PaymentType 
	 * @param enties Liste der Entries fuer die ein Rechnungsbeleg erstellt werden soll
	 * @param pt Die Art der Bezahlung in einer Enumeration PaymentType
	 * @return Das erstellte Rechnungsbeleg Dto
	 */
	public ReceiptDto createReceiptforEntries(List<EntryDto> enties,PaymentTypeDto pt)  throws ServiceException;
}
