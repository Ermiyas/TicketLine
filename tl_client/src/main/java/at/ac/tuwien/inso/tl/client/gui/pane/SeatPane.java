package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
		this.spSearchStack = spSearchStack;
		
		if(reserved == null) {
			initReserved(entryService, ticketService);
		} else if(!reserved) {
			setStyle("-fx-background-color: #f75555, linear-gradient(#e97676 0%, #e75959 20%, #d23a3a 100%), "
					+ "linear-gradient(#e44c4c, #d23a3a), radial-gradient(center 50% 0%, radius 100%, rgba(238,159,1659,0.9), rgba(255,255,255,0));"
					+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
		} else {
			init(entryService, ticketService);
		}		
	}
	
	private void init(final EntryService entryService, final TicketService ticketService) {
		seatEntry = new EntryDto();
		setStyle("-fx-background-color: #9bd765, linear-gradient(#c4eca0 0%, #a4d479 20%, #83ba51 100%), "
				+ "linear-gradient(#92d753, #73b13b), radial-gradient(center 50% 0%, radius 100%, rgba(202,236,180,0.9), rgba(255,255,255,0));"
				+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
		
		handler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
					if(mouseEvent.getClickCount() == 1) {
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
								setStyle("-fx-background-color: #fccf62, linear-gradient(#fccf62 0%, #eec256 20%, #eea556 100%), "
										+ "linear-gradient(#fccf62, #f29400), radial-gradient(center 50% 0%, radius 100%, rgba(239,220,134,0.9), rgba(255,255,255,0));"
										+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
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
								setStyle("-fx-background-color: #9bd765, linear-gradient(#c4eca0 0%, #a4d479 20%, #83ba51 100%), "
										+ "linear-gradient(#92d753, #73b13b), radial-gradient(center 50% 0%, radius 100%, rgba(202,236,180,0.9), rgba(255,255,255,0));"
										+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
								seatingPlanPane.undoReservation();
							} catch (ServiceException e) {
								LOG.error("Could not undo entry: " + e.getMessage(), e);
								Stage error = new ErrorDialog(e.getMessage());
								error.show();
								return;
							}
						}
					}
				}
			}
		};
		
		handlerEntered = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setEffect(new DropShadow(BlurType.THREE_PASS_BOX, new Color(0,0,0,0.6), 5, 0, 0, 1));
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
		setStyle("-fx-background-color: #f75555, linear-gradient(#e97676 0%, #e75959 20%, #d23a3a 100%), "
				+ "linear-gradient(#e44c4c, #d23a3a), radial-gradient(center 50% 0%, radius 100%, rgba(238,159,1659,0.9), rgba(255,255,255,0));"
				+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
		this.removeEventHandler(MouseEvent.MOUSE_CLICKED, handler);
	}

	private void initReserved(final EntryService entryService, final TicketService ticketService) {		
		seatEntry = new EntryDto();
		setStyle("-fx-background-color: #fccf62, linear-gradient(#fccf62 0%, #eec256 20%, #eea556 100%), "
				+ "linear-gradient(#fccf62, #f29400), radial-gradient(center 50% 0%, radius 100%, rgba(239,220,134,0.9), rgba(255,255,255,0));"
				+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
		
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
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						if(mouseEvent.getClickCount() == 1) {
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
									setStyle("-fx-background-color: #9bd765, linear-gradient(#c4eca0 0%, #a4d479 20%, #83ba51 100%), "
											+ "linear-gradient(#92d753, #73b13b), radial-gradient(center 50% 0%, radius 100%, rgba(202,236,180,0.9), rgba(255,255,255,0));"
											+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
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
									setStyle("-fx-background-color: #fccf62, linear-gradient(#fccf62 0%, #eec256 20%, #eea556 100%), "
											+ "linear-gradient(#fccf62, #f29400), radial-gradient(center 50% 0%, radius 100%, rgba(239,220,134,0.9), rgba(255,255,255,0));"
											+ "-fx-text-fill: linear-gradient(#233c4f, #0e1d28);");
								} catch (ServiceException e) {
									LOG.error("Could not create entry: " + e.getMessage(), e);
									Stage error = new ErrorDialog(current, "Ticket konnte nicht erstellt werden. Versuchen Sie es bitte später erneut!");
									error.show();
									return;
								}
							}
						}
					}
				}
		});
	}
}
