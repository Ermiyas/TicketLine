package at.ac.tuwien.inso.tl.client.util;

import java.text.SimpleDateFormat;

import org.hibernate.validator.internal.util.privilegedactions.SetAccessibility;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import at.ac.tuwien.inso.tl.dto.ArticleDto;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

/**
 * Ein Container für ein einzelnes Warenkorb-Item
 */
public class BasketEntryContainer {
	
	private EntryDto entry;
	private Boolean isTicket;
	private TicketDto ticket;
	private Boolean hasSeat;
	private PerformanceDto performance;
	private ShowDto show;
	private LocationDto location;
	private RowDto row;
	private SeatDto seat;
	private ArticleDto article;
	private SimpleBooleanProperty selected = new SimpleBooleanProperty();
	private SimpleBooleanProperty sold = new SimpleBooleanProperty();
	
	/**
	 * Initialisiert einen neuen BasketEntryContainer
	 * @param cDto Das ContainerDto, welches als Basis für dieses neue Objekt gelten soll
	 */
	public BasketEntryContainer(ContainerDto cDto) {
			setEntry(cDto.getEntryDto());
			setIsTicket(cDto.getTicketDto() != null);
			
			//Wenn der Entry ein Ticket ist, füge die Ticket-Informationen hinzu
			if(getIsTicket()) {
				setTicket(cDto.getTicketDto());
				setHasSeat(cDto.getSeatDto() != null);
				if (getHasSeat()) {
					setRow(cDto.getRowDto());
					setSeat(cDto.getSeatDto());
				}
				setPerformance(cDto.getPerformanceDto());
				setShow(cDto.getShowDto());
				setLocation(cDto.getLocationDto());
			} else {
				//Füge die Artikel-Informationen hinzu
				setArticle(cDto.getArticleDto());
			}
			
			
	}
	public EntryDto getEntry() {
		return entry;
	}
	public void setEntry(EntryDto entry) {
		this.entry = entry;
		setSold(entry.getSold());
	}
	public Boolean getIsTicket() {
		return isTicket;
	}
	public void setIsTicket(Boolean isTicket) {
		this.isTicket = isTicket;
	}
	public TicketDto getTicket() {
		return ticket;
	}
	public void setTicket(TicketDto ticket) {
		this.ticket = ticket;
	}
	public Boolean getHasSeat() {
		return hasSeat;
	}
	public void setHasSeat(Boolean hasSeat) {
		this.hasSeat = hasSeat;
	}
	public PerformanceDto getPerformance() {
		return performance;
	}
	public void setPerformance(PerformanceDto performance) {
		this.performance = performance;
	}
	public ShowDto getShow() {
		return show;
	}
	public void setShow(ShowDto show) {
		this.show = show;
	}
	public LocationDto getLocation() {
		return location;
	}
	public void setLocation(LocationDto location) {
		this.location = location;
	}
	public RowDto getRow() {
		return row;
	}
	public void setRow(RowDto row) {
		this.row = row;
	}
	public SeatDto getSeat() {
		return seat;
	}
	public void setSeat(SeatDto seat) {
		this.seat = seat;
	}

	public boolean getSelected() {
		return selected.getValue();
	}
	public ArticleDto getArticle() {
		return article;
	}
	public void setArticle(ArticleDto article) {
		this.article = article;
	}
	public void setSelected(boolean selected) {
		this.selected.set(selected);;
	}
	
	public BooleanProperty selectedProperty() {
		return selected;
	}
	
	public boolean getSold() {
		return sold.getValue();
	}
	public void setSold(boolean sold) {
		this.sold.set(sold);
	}
	
	public BooleanProperty soldProperty() {
		return sold;
	}
	
	public int getSumInCent() {
		return getSinglePriceInCent() * getAmount();
	}

	/**
	 * Gibt einen zusammenfassenden String des Entrys zurück
	 * @return einen zusammenfassenden String, der die wichtigsten Informationen der Bestellung/Reservierung enthält.
	 */
	public String getDescription() {
		if(isTicket) {
			SimpleDateFormat df = new SimpleDateFormat(BundleManager.getBundle().getString("dateformat"));
			return String.format("%s, %s\n%s, %s", performance.getDescription(), location.getDescription(), df.format(show.getDateOfPerformance()), getSeatingDescription());
		} else {
			return String.format("%s \"%s\"\n ", BundleManager.getBundle().getString("cartpage.article"), article.getName());
		}
	}

	public String getSeatingDescription() {
		if(hasSeat) {
			return String.format("%s: %d, %s: %d", BundleManager.getBundle().getString("cartpage.row"), row.getSequence(), BundleManager.getBundle().getString("cartpage.seat"), seat.getSequence());
		} else {
			return BundleManager.getBundle().getString("cartpage.standing");
		}
	}
	public int getSinglePriceInCent() {
		if(isTicket) {
			return show.getPriceInCent();
		} else {
			return article.getPriceInCent();
		}
	}
	
	public int getSinglePriceInPoints() {
		return article.getPriceInPoints();
	}
	
	public int getSumInPoints() {
		return getSinglePriceInPoints() * getAmount();
	}
	/**
	 * Liefert den Einzelpreis des Entrys als String zurück (€ oder P)
	 * @return Den Einzelpreis des Entrys als String
	 */
	public String getSinglePriceString() {
		if(isTicket || !entry.getBuyWithPoints() ) {
			return String.format("€ %.2f", ((float)getSinglePriceInCent())/100);
		} else {
			return String.format("P %d", article.getPriceInPoints());
		}
	}
	
	/**
	 * Liefert den Gesamtpreis des Entrys als String zurück (€ oder P)
	 * @return Den Gesamtpreis des Entrys als String
	 */
	public String getSumPriceString() {
		if(isTicket || !entry.getBuyWithPoints()) {
			return String.format("€ %.2f", ((float)getSumInCent())/100);
		} else {
			return String.format("P %d",getSumInPoints());
		}
	}
	
	public int getAmount() {
		return entry.getAmount();
	}
	
	public String getInvoicePosition(int posNum) {
		SimpleDateFormat df = new SimpleDateFormat(BundleManager.getBundle().getString("dateformat"));
		StringBuilder sb = new StringBuilder();
		if(getIsTicket()) {
			sb.append(String.format("%-5d %-50s\n", posNum, getPerformance().getDescription()));
			sb.append(String.format("      %-50s\n", getLocation().getDescription()));
			sb.append(String.format("      %-16s, %-20s\n", df.format(getShow().getDateOfPerformance()), getSeatingDescription()));
			sb.append(String.format("      %-12s #%-26d %s\n", BundleManager.getBundle().getString("receipt.ticket_number"), getTicket().getId(), getInvoicePriceLine()));
		} else {
			sb.append(String.format("%-5d %-40s %s\n", posNum, getArticle().getName(), getInvoicePriceLine()));
		}
		sb.append('\n');
		return sb.toString();
	}
	
	private String getInvoicePriceLine() {
		return String.format("%-15s %-10d %-17s", getSinglePriceString(), getAmount(), getSumPriceString());
		
	}
}
