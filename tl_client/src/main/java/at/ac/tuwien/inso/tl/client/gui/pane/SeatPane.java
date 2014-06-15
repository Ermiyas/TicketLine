package at.ac.tuwien.inso.tl.client.gui.pane;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SeatPane extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(SeatPane.class);
	
	private Integer performanceId;
	private Integer seatId;
	private Integer basketId;
	
	private EntryDto seatEntry;
	private TicketDto ticket;
	
	private boolean reserved;
	
	SeatingPlanPane seatingPlanPane;
	
	public SeatPane(EntryService entryService, TicketService ticketService, SeatingPlanPane seatingPlanPane, 
					Integer performanceId, Integer seatId, Integer basketId, boolean reserved) {
		this.performanceId = performanceId;
		this.seatId = seatId;
		this.basketId = basketId;
		this.seatingPlanPane = seatingPlanPane;
		this.reserved = reserved;
		
		if(reserved) {
			setStyle("-fx-background-color: #f75555;");
		} else {
			init(entryService, ticketService);
		}		
	}
	
	private void init(final EntryService entryService, final TicketService ticketService) {
		seatEntry = new EntryDto();
		setStyle("-fx-background-color: #b6e7c9;");
		
		this.addEventHandler(MouseEvent.MOUSE_ENTERED,
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					setEffect(new DropShadow());
				}
		});
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED,
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					setEffect(null);
				}
		});
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED,
			new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					LOG.info("Basket mit Id: " + basketId);
					LOG.info("Sitz mit Id: " + seatId + " wurde für die Aufführung: " + performanceId + " reserviert.");
					if(isSelected()) {
						EntryDto entryDto = new EntryDto();
						entryDto.setAmount(1);
						entryDto.setBuyWithPoints(false);
						entryDto.setSold(false);
						try {
							seatEntry = entryService.createEntry(entryDto, basketId);
							ticket = ticketService.createTicket(null, seatId, seatEntry.getId());
							seatingPlanPane.addReservation();
							setStyle("-fx-background-color: #fccf62;");
						} catch (ServiceException e) {
							LOG.error("Could not create entry: " + e.getMessage(), e);
							Stage error = new ErrorDialog(e.getMessage());
							error.show();
							return;
						}
					} else {
						try {
							ticketService.undoTicket(ticket.getId());
							setStyle("-fx-background-color: #b6e7c9;");
							seatingPlanPane.undoReservation();
						} catch (ServiceException e) {
							LOG.error("Could not undo entry: " + e.getMessage(), e);
							Stage error = new ErrorDialog(e.getMessage());
							error.show();
							return;
						}
					}
				}			
		});
	}
}
