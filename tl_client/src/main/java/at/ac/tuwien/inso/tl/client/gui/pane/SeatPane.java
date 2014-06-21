package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

public class SeatPane extends ToggleButton {
	private static final Logger LOG = Logger.getLogger(SeatPane.class);
	
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
					SeatingPlanPane seatingPlanPane, Integer seatId, Integer basketId, Boolean reserved) {
		this.seatId = seatId;
		this.basketId = basketId;
		this.seatingPlanPane = seatingPlanPane;
		if(reserved == null) {
			reserved = null;
		} else {
			this.reserved = !reserved;
		}
		this.spSearchStack = spSearchStack;
		
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
						LOG.info("Create entry for basket (ID): " + basketId);
						seatEntry = entryService.createEntry(entryDto, basketId);
						LOG.debug("Entry (ID): " + seatEntry.getId());
						try {
							LOG.info("Create ticket for seat (ID): " + seatId + " and entry (ID): " + seatEntry.getId());
							ticket = ticketService.createTicket(null, seatId, seatEntry.getId());
							LOG.debug("Ticket (ID): " + ticket.getId() + " created");
							LOG.debug("Seat has been reserved.");
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
		try {
			EntryDto entry = entryService.findEntryBySeat(seatId);
			if(entry.getSold()) {
				setStyle("-fx-background-color: #f75555;");
				return;
			}
		} catch (ServiceException e) {
			LOG.error("Failed to determine whether seat is already sold: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, "Es konnte nicht herausgefunden werden, ob der Sitz reserviert ist.\n" + 
												   "Laden Sie den Sitzplan bitte etwas später erneut!");
			error.show();
			return;
		}
		
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
					Stage current = (Stage) spSearchStack.getScene().getWindow();
					if(isSelected()) {
						try {
							if(ticket == null) {
								try {
									LOG.info("Seat Id: " + seatId);
									ticket = ticketService.getTicketBySeat(seatId);
								} catch (ServiceException e) {
									LOG.error("Could not find ticket by seat id: " + e.getMessage(), e);
									Stage error = new ErrorDialog(current, "Zum Sitz zugehöriges Ticket konnte nicht gefunden werden!");
									error.show();
								}
							}
							ticketService.undoTicket(ticket.getId());
							setStyle("-fx-background-color: #b6e7c9;");
							seatingPlanPane.undoReservation();
						} catch (ServiceException e) {
							LOG.error("Could not undo entry: " + e.getMessage(), e);
							Stage error = new ErrorDialog(current, "Reservierung konnte nicht rückgängig gemacht werden!");
							error.show();
						}
					} else {
						EntryDto entryDto = new EntryDto();
						entryDto.setAmount(1);
						entryDto.setBuyWithPoints(false);
						entryDto.setSold(false);
						try {
							LOG.info("Create entry for basket (ID): " + basketId);
							seatEntry = entryService.createEntry(entryDto, basketId);
							LOG.debug("Entry (ID): " + seatEntry.getId());
							try {
								LOG.info("Create ticket for seat (ID): " + seatId + " and entry (ID): " + seatEntry.getId());
								ticket = ticketService.createTicket(null, seatId, seatEntry.getId());
								LOG.debug("Ticket (ID): " + ticket.getId() + " created");
								LOG.debug("Seat has been reserved.");
							} catch (ServiceException e) {
								LOG.error("Could not create ticket: " + e.getMessage(), e);
								entryService.undoEntry(seatEntry.getId());
								setSelected(false);
								deactivateButton();
								Stage error = new ErrorDialog(current, "Sitz ist bereits von jemanden reserviert worden. Bitte wählen Sie einen anderen Sitz!");
								error.show();
								return;
							}
							seatingPlanPane.addReservation();
							setStyle("-fx-background-color: #fccf62;");
						} catch (ServiceException e) {
							LOG.error("Could not create entry: " + e.getMessage(), e);
							Stage error = new ErrorDialog(current, "Ticket konnte nicht erstellt werden. Versuchen Sie es bitte später erneut!");
							error.show();
							return;
						}
					}
				}			
		});
	}
}
