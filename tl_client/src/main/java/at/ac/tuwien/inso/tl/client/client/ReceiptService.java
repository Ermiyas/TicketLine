package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

public interface ReceiptService {

	/**
	 * Erstellt einen Rechnungsbeleg fuer die uebergebenen Entries mit einem bestimmten PaymentType 
	 * @param entries Liste der Entries fuer die ein Rechnungsbeleg erstellt werden soll
	 * @param pt Die Art der Bezahlung in einer Enumeration PaymentType
	 * @return Der erstellte Rechnungsbeleg Dto sowie die neuen Punkte des customers
	 */
	public KeyValuePairDto<ReceiptDto, Integer>  createReceiptforEntries(List<EntryDto> entries,PaymentTypeDto pt)  throws ServiceException;
}
