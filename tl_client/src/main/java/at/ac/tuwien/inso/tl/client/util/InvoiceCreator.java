package at.ac.tuwien.inso.tl.client.util;

import java.text.SimpleDateFormat;
import java.util.List;

import javafx.collections.ObservableList;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;

public class InvoiceCreator {
	
	public static String createInvoice(ReceiptDto receipt, BasketDto basket,
			ObservableList<BasketEntryContainer> basketEntries,
			CustomerDto customer, Integer oldPoints, PaymentTypeDto paymentTypeDto) {
		SimpleDateFormat df = new SimpleDateFormat(BundleManager.getBundle().getString("dateformat"));
		StringBuilder sb = new StringBuilder();
		//BundleManager.getBundle().getString("receipt.")
		//sb.append(String.format("", ));
		
		sb.append(String.format("Ticketline GmbH %s #%d (%s #%d)\n", BundleManager.getBundle().getString("receipt.invoice"), receipt.getId(), BundleManager.getBundle().getString("reservation"), basket.getId()));
		sb.append(String.format("%s: %s\n", BundleManager.getBundle().getString("receipt.invoice_date"), df.format(receipt.getTransactionDate())));
		sb.append("------------------------------------\n");
		if(customer != null) {
			sb.append(String.format("%s:\n", BundleManager.getBundle().getString("receipt.recipient")));
			
			// Registrierter Kunde
			sb.append(String.format("%s %s %s\n", (customer.getIsFemale()?BundleManager.getBundle().getString("mrs") : BundleManager.getBundle().getString("mr")), customer.getFirstname(), customer.getLastname()));
			sb.append(String.format("%s\n", (customer.getStreet() == null)? "" : customer.getStreet()));
			sb.append(String.format("%s%s\n", (customer.getPostalcode() == null)? "" : customer.getPostalcode() + " ", (customer.getCity() == null)? "" : customer.getCity()));
			sb.append(String.format("%s\n", (customer.getCountry() == null)? "" : customer.getCountry()));
			sb.append("------------------------------------\n\n");
		}
		
		// Überschrift
		sb.append(String.format("%-5s %-40s %-15s %-10s %-15s\n", BundleManager.getBundle().getString("receipt.pos"), BundleManager.getBundle().getString("receipt.article_description"), BundleManager.getBundle().getString("receipt.single_price"), BundleManager.getBundle().getString("receipt.amount"), BundleManager.getBundle().getString("receipt.sum")));
		
		// Durchlaufen der Rechnungspositionen
		int i = 1;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected() && !piv.getSold()) {
				sb.append(piv.getInvoicePosition(i++));
			}
		}
		
		// Anzeigen der Gesamtsummen
		sb.append(String.format("%-70s ------------\n", ""));
		sb.append(String.format("%-73s € %.2f\n", "", ((float)calcCheckoutSumInCent(basketEntries))/100));
		
		int checkoutSumInPoints = calcCheckoutSumInPoints(basketEntries);
		if(checkoutSumInPoints > 0) {
		sb.append(String.format("%-73s P %d\n", "", checkoutSumInPoints));
		}
		sb.append('\n');
		sb.append('\n');
		
		// Anzeigen des Zahlungsmittel
		String paymentType = "";
		switch(paymentTypeDto) {
			case CASH:
				paymentType = BundleManager.getBundle().getString("paymenttype.cash");
				break;
			case BANK:
				paymentType = BundleManager.getBundle().getString("paymenttype.bank");
				break;
			case CREDITCARD:
				paymentType = BundleManager.getBundle().getString("paymenttype.creditcard");
				break;
			default:
				break;
		}
		sb.append(String.format("%s: %s\n", BundleManager.getBundle().getString("receipt.received_payment_via"), paymentType));
		sb.append('\n');
		// Falls der Kunde registriert war, zeige die Punkte-informationen an
		if(customer != null) {
			sb.append(String.format("%-35s: %d\n", BundleManager.getBundle().getString("receipt.former_points"), oldPoints));
			if(checkoutSumInPoints > 0) {
				sb.append(String.format("%-35s: %d\n", BundleManager.getBundle().getString("receipt.points_used_for_payment"), checkoutSumInPoints));
			}
			sb.append(String.format("%-35s: %d\n", BundleManager.getBundle().getString("receipt.earned_points"), customer.getPoints() + checkoutSumInPoints - oldPoints));
			sb.append(String.format("%-35s: %d\n", BundleManager.getBundle().getString("receipt.new_points"), customer.getPoints()));
			sb.append('\n');
		}
		sb.append(String.format("%s.\n", BundleManager.getBundle().getString("receipt.admission_to_event")));
		return sb.toString();
	}
	
	public static int calcCheckoutSumInCent(List<BasketEntryContainer> basketEntries) {
		int checkoutSumInCent = 0;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected() && !piv.getEntry().getBuyWithPoints()) {
				checkoutSumInCent += piv.getSumInCent();
			}
			
		}
		return checkoutSumInCent;
	}
	
	public static int calcCheckoutSumInPoints(List<BasketEntryContainer> basketEntries) {
		int checkoutSumInPoints = 0;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected() && piv.getEntry().getBuyWithPoints()) {
				checkoutSumInPoints += piv.getSumInPoints();
			}
			
		}
		return checkoutSumInPoints;
	}
}
