package at.ac.tuwien.inso.tl.client.gui.pane;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
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
	
	private Boolean reserved;
	
	private SeatingPlanPane seatingPlanPane;
	
	EventHandler<MouseEvent> handler;
	EventHandler<MouseEvent> handlerEntered;
	EventHandler<MouseEvent> handlerExited;
	
	@FXML private StackPane spSearchStack;
	
	public SeatPane(StackPane spSearchStack, EntryService entryService, TicketService ticketService, 
					SeatingPlanPane seatingPlanPane, Integer performanceId, Integer seatId, 
					Integer basketId, Boolean reserved) {
		this.performanceId = performanceId;
		this.seatId = seatId;
		this.basketId = basketId;
		this.seatingPlanPane = seatingPlanPane;
		if(reserved == null) {
			reserved = null;
		} else {
			this.reserved = !reserved;
		}
		this.spSearchStack = spSearchStack;
		
		LOG.info("Reserved: " + reserved);
		if(reserved == null) {
			initReserved(entryService, ticketService);
		} else if(!reserved) {
			setStyle("-fx-background-color: #f75555;");
		} else {
			init(entryService, ticketService);
		}		
	}
	
	private void init(final EntryService entryService, final TicketService ticketService) {
		seatEntry = new EntryDto();
		setStyle("-fx-background-color: #b6e7c9;");
		
		handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
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
							deactivateButton();
							Stage current = (Stage) spSearchStack.getScene().getWindow();
							Stage error = new ErrorDialog(current, "Sitz ist bereits von jemanden reserviert worden. Bitte wählen Sie einen anderen Sitz!");
							error.show();
							return;
						}
						seatingPlanPane.addReservation();
						setStyle("-fx-background-color: #fccf62;");
					} catch (ServiceException e) {
						LOG.error("Could not create entry: " + e.getMessage(), e);
						Stage current = (Stage) spSearchStack.getScene().getWindow();
						Stage error = new ErrorDialog(current, "Ticket konnte nicht erstellt werden. Versuchen Sie es bitte später erneut!");
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
		};
		
		handlerEntered = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setEffect(new DropShadow());
			}
		};
		
		handlerExited = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setEffect(null);
			}
		};
		
		this.addEventHandler(MouseEvent.MOUSE_ENTERED, handlerEntered);
		
		this.addEventHandler(MouseEvent.MOUSE_EXITED, handlerExited);
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
	}
	
	private void deactivateButton() {
		this.setStyle("-fx-background-color: #f75555;");
		this.removeEventHandler(MouseEvent.MOUSE_CLICKED, handler);
	}

	private void initReserved(final EntryService entryService, final TicketService ticketService) {
		seatEntry = new EntryDto();
		setStyle("-fx-background-color: #fccf62;");
		
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
						try {
							if(ticket == null) {
								try {
									LOG.info("Seat Id: " + seatId);
									ticket = ticketService.getTicketBySeat(seatId);
								} catch (ServiceException e) {
									LOG.error("Could not find ticket by seat id: " + e.getMessage(), e);
									Stage current = (Stage) spSearchStack.getScene().getWindow();
									Stage error = new ErrorDialog(current, "Zum Sitz zugehöriges Ticket konnte nicht gefunden werden!");
									error.show();
									return;
								}
							}
							ticketService.undoTicket(ticket.getId());
							setStyle("-fx-background-color: #b6e7c9;");
							seatingPlanPane.undoReservation();
						} catch (ServiceException e) {
							LOG.error("Could not undo entry: " + e.getMessage(), e);
							Stage current = (Stage) spSearchStack.getScene().getWindow();
							Stage error = new ErrorDialog(current, "Reservierung konnte nicht rückgängig gemacht werden!");
							error.show();
							return;
						}
					} else {
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
								deactivateButton();
								Stage current = (Stage) spSearchStack.getScene().getWindow();
								Stage error = new ErrorDialog(current, "Sitz ist bereits von jemanden reserviert worden. Bitte wählen Sie einen anderen Sitz!");
								error.show();
								return;
							}
							seatingPlanPane.addReservation();
							setStyle("-fx-background-color: #fccf62;");
						} catch (ServiceException e) {
							LOG.error("Could not create entry: " + e.getMessage(), e);
							Stage current = (Stage) spSearchStack.getScene().getWindow();
							Stage error = new ErrorDialog(current, "Ticket konnte nicht erstellt werden. Versuchen Sie es bitte später erneut!");
							error.show();
							return;
						}
					}
				}			
		});
	}
}
