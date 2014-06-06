package at.ac.tuwien.inso.tl.client.client;

import java.util.List;

import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

public interface ReceiptService {
	/**
	 * Erstellt zu einer Liste von Einträgen eine Rechnung
	 * @param entries Die Liste der Warenkorb-Einträge. Kein Eintrag darf bereits einer Rechnung zugewiesen sein.
	 * @param paymentType Die Zahlungsart
	 * @return Die neu erstellte Rechnung
	 */
	public ReceiptDto createReceiptForEntries(List<EntryDto> entries, PaymentTypeDto paymentType);
}
