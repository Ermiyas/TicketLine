package at.ac.tuwien.inso.tl.client.gui.pane;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class SeatPane extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(SeatPane.class);
	
	private Integer performanceId;
	private Integer seatId;
	private Integer basketId;
	
	private EntryDto seatEntry;
	private TicketDto ticket;
	
	private boolean reserved;
	
	private SeatingPlanPane seatingPlanPane;
	
	@FXML private StackPane spSearchStack;
	
	public SeatPane(StackPane spSearchStack, EntryService entryService, TicketService ticketService, 
					SeatingPlanPane seatingPlanPane, Integer performanceId, Integer seatId, 
					Integer basketId, boolean reserved) {
		this.performanceId = performanceId;
		this.seatId = seatId;
		this.basketId = basketId;
		this.seatingPlanPane = seatingPlanPane;
		this.reserved = reserved;
		this.spSearchStack = spSearchStack;
		
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
							try {
								ticket = ticketService.createTicket(null, seatId, seatEntry.getId());
							} catch (ServiceException e) {
								LOG.error("Could not create ticket: " + e.getMessage(), e);
								entryService.undoEntry(seatEntry.getId());
								setSelected(false);
								Stage current = (Stage) spSearchStack.getScene().getWindow();
								Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchcontroller.seat_already_reserved"));
								error.show();
								return;
							}
							seatingPlanPane.addReservation();
							setStyle("-fx-background-color: #fccf62;");
						} catch (ServiceException e) {
							LOG.error("Could not create entry: " + e.getMessage(), e);
							Stage current = (Stage) spSearchStack.getScene().getWindow();
							Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchcontroller.create_ticket_failed"));
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
