package at.ac.tuwien.inso.tl.client.util;

import java.text.SimpleDateFormat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
	private SimpleBooleanProperty selected = new SimpleBooleanProperty();
	public EntryDto getEntry() {
		return entry;
	}
	public void setEntry(EntryDto entry) {
		this.entry = entry;
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
	public void setSelected(boolean selected) {
		this.selected.set(selected);;
	}
	
	public BooleanProperty selectedProperty() {
		return selected;
	}
	public int getSumInCent() {
		return getSinglePriceInCent() * getAmount();
	}

	/**
	 * Gibt einen zusammenfassenden String des Entrys zurück
	 * @return einen zusammenfassenden String, der die wichtigsten Informationen der Bestellung/Reservierung enthält.
	 */
	public String getDescription() {
		SimpleDateFormat df = new SimpleDateFormat(BundleManager.getBundle().getString("dateformat"));
		return String.format("%s, %s\n%s, %s", performance.getDescription(), location.getDescription(), df.format(show.getDateOfPerformance()), getSeatingDescription());
	}

	public String getSeatingDescription() {
		if(hasSeat) {
			return String.format("%s: %d, %s: %d", BundleManager.getBundle().getString("cartpage.row"), row.getSequence(), BundleManager.getBundle().getString("cartpage.seat"), seat.getSequence());
		} else {
			return BundleManager.getBundle().getString("cartpage.standing");
		}
	}
	public int getSinglePriceInCent() {
		return show.getPriceInCent();
	}
	
	public int getAmount() {
		return entry.getAmount();
	}
	public boolean getExistsReceipt() {
		// TODO Temporär, bis eine Servicemethode geschrieben ist. Dann soll beim Laden des Warenkorbs das gleich mitgesetzt werden.
		return false;
	}
}
