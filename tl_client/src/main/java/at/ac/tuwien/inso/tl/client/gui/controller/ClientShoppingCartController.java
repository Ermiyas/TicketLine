package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

@Controller
@Scope("prototype")
public class ClientShoppingCartController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientShoppingCartController.class);

	@Autowired private TicketService ticketService;
	@Autowired private EntryService entryService; 
	@FXML private TableView<EntryDto> tvCart;
	@FXML private TableColumn<EntryDto, EntryDto> tcCartDescription;
//	@FXML private TableColumn<EntryDto,> tcCartStatus;
//	@FXML private TableColumn tcCartSinglePrice;
	@FXML private TableColumn tcCartAmount;
	@FXML private TableColumn tcCartSum;
	@FXML private TableColumn tcCartSelection;
	
	private ClientSellTicketController parentController;
	
	/**
	 * Enthält alle Entries des Baskets
	 */
	private List<KeyValuePairDto<EntryDto, Boolean>> basketEntries;
	/**
	 * <EntryID, dazugehöriges Ticket>
	 */
	private HashMap<Integer, TicketDto> basketTickets;
	/**
	 * <TicketID, Sitzplatznummer>
	 */
	private HashMap<Integer, Integer> ticketSeats;
	/**
	 * <TicketID, Reihennummer>
	 */
	private HashMap<Integer, Integer> ticketRows;
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.debug("initialize ClientShoppingCartController");
		
		loadData();
		tcCartDescription.setCellFactory(new Callback<TableColumn<EntryDto, EntryDto>, TableCell<EntryDto, EntryDto>>() {

			@Override
			public TableCell<EntryDto, EntryDto> call(
					TableColumn<EntryDto, EntryDto> param) {
				return new TableCell<EntryDto, EntryDto>() {
					@Override
					public void updateItem(EntryDto item, boolean empty) {
						if(empty) {
							setText(null);
						} else {
							setWrapText(true);
							
							setText(String.format(""));
						}
					}
					
				};
			}
		});
	}
	
	/**
	 * Lädt alle Daten für den Warenkorb
	 */
	private void loadData() {
//		basketEntries = new List<KeyValuePairDto<EntryDto, Boolean>>();
//		basketTickets = new HashMap<Integer, TicketDto>();
//		for(KeyValuePairDto<EntryDto, Boolean> piv : entryService.getEntry(parentController.getBasket().getId());) {
//			// Nur nicht-stornierte Tickets anzeigen
//			if(piv.getValue() != null) {
//				basketEntries.add(piv);
//				KeyValuePairDto<TicketDto, Boolean> ticket = ticketService.getTicketForEntry(piv.getKey().getId());
//				basketTickets.put(piv.getKey().getId(), ticket.getKey());
//				if(ticket.getValue() == true) {
//					//ticketSeats und ticketRows befüllen
//				} 
//			}
//		}
	}

	@FXML
	private void handleFinishProcedure() {
		LOG.debug("handleFinishProcedure clicked");
	}
	
	@FXML
	private void handleAbortProcedure() {
		LOG.debug("handleAbortProcedure clicked");
	}
	
	@FXML
	private void handleCheckout() {
		LOG.debug("handleCheckout clicked");
	}

	@Override
	public void setParentController(ClientSellTicketController cont) {
		this.parentController = cont;
		
	}
}
