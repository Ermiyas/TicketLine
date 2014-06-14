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
			CustomerDto customer, PaymentTypeDto paymentTypeDto) {
		SimpleDateFormat df = new SimpleDateFormat(BundleManager.getBundle().getString("dateformat"));
		StringBuilder sb = new StringBuilder();
		//BundleManager.getBundle().getString("receipt.")
		//sb.append(String.format("", ));
		sb.append(String.format("Ticketline GmbH %s #%d\n", BundleManager.getBundle().getString("receipt.invoice"), receipt.getId()));
		sb.append(String.format("%s: %s\n", BundleManager.getBundle().getString("receipt.invoice_date"), df.format(receipt.getTransactionDate())));
		sb.append("------------------------------------\n");
		if(customer != null) {
			sb.append(String.format("%s:\n", BundleManager.getBundle().getString("receipt.recipient")));
			// Registrierter Kunde
			sb.append(String.format("%s %s %s\n", (customer.getIsFemale()?BundleManager.getBundle().getString("mrs") : BundleManager.getBundle().getString("mr")), customer.getFirstname(), customer.getLastname()));
			sb.append(String.format("%s\n", customer.getStreet()));
			sb.append(String.format("%s %s\n", customer.getPostalcode(), customer.getCity()));
			sb.append(String.format("%s\n", customer.getCountry()));
			sb.append("------------------------------------\n\n");
		}
		sb.append(String.format("%-5s %-40s %-15s %-10s %-15s\n", BundleManager.getBundle().getString("receipt.pos"), BundleManager.getBundle().getString("receipt.article_description"), BundleManager.getBundle().getString("receipt.single_price"), BundleManager.getBundle().getString("receipt.amount"), BundleManager.getBundle().getString("receipt.sum")));
		int i = 1;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected() && !piv.getExistsReceipt()) {
				sb.append(String.format("%-5d %-50s\n", i, piv.getPerformance().getDescription()));
				sb.append(String.format("      %-50s\n", piv.getLocation().getDescription()));
				sb.append(String.format("      %-16s, %-20s\n", df.format(piv.getShow().getDateOfPerformance()), piv.getSeatingDescription()));
				sb.append(String.format("      %-12s #%-26d € %-13.2f %-10d € %-15.2f\n", BundleManager.getBundle().getString("receipt.ticket_number"), piv.getTicket().getId(), ((float)piv.getSinglePriceInCent())/100, piv.getAmount(), ((float)piv.getSumInCent())/100));
				sb.append('\n');
				
				i++;
			}
		}
		sb.append(String.format("%-70s -----------\n", ""));
		sb.append(String.format("%-72s € %.2f\n", "", ((float)getCheckoutSumInCent(basketEntries))/100));
		sb.append('\n');
		sb.append('\n');
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
		sb.append(String.format("%s: %s.\n", BundleManager.getBundle().getString("receipt.received_payment_via"), paymentType));
		return sb.toString();
	}
	
	private static int getCheckoutSumInCent(List<BasketEntryContainer> basketEntries) {
		int checkoutSumInCent = 0;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected()) {
				checkoutSumInCent += piv.getSumInCent();
			}
			
		}
		return checkoutSumInCent;
	}
	
}
