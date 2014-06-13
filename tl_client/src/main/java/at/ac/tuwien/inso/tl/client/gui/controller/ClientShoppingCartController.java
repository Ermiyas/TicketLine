package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BasketEntryContainer;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

@Controller
@Scope("prototype")
public class ClientShoppingCartController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientShoppingCartController.class);

	@Autowired private TicketService ticketService;
	@Autowired private EntryService entryService; 
	@FXML private TableView<BasketEntryContainer> tvCart;
	@FXML private TableColumn<BasketEntryContainer, String> tcCartDescription;
	@FXML private TableColumn<BasketEntryContainer, Boolean> tcCartStatus;
	@FXML private TableColumn<BasketEntryContainer, Integer> tcCartSinglePrice;
	@FXML private TableColumn<BasketEntryContainer, Integer> tcCartAmount;
	@FXML private TableColumn<BasketEntryContainer, Integer> tcCartSum;
	// TODO @FXML private TableColumn<BasketEntryContainer,> tcCartSelection;
	@FXML private Label lblTotalSum;
	
	private ClientSellTicketController parentController;
	private boolean isInitialized = false;
	/**
	 * Enthält alle Entries des Baskets
	 */
	private ObservableList<BasketEntryContainer> basketEntries;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.debug("initialize ClientShoppingCartController");
		
		// Setzt die Eigenschaften, welche in den Spalten angezeigt werden sollen
		tcCartDescription.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, String>(
				"description"));
		tcCartSinglePrice.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, Integer>(
				"singlePriceInCent"));
		tcCartAmount.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, Integer>(
				"amount"));
		tcCartSum.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, Integer>(
				"sumInCent"));
		
		// Setzt das Format der Eigenschaften in den Spalten
		tcCartDescription.setCellFactory(new Callback<TableColumn<BasketEntryContainer, String>, TableCell<BasketEntryContainer, String>>() {

			@Override
			public TableCell<BasketEntryContainer, String> call(
					TableColumn<BasketEntryContainer, String> param) {
				return new TableCell<BasketEntryContainer, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if(empty) {
							setText(null);
						} else {
							setWrapText(true);
							setText(item);
						}
					}
					
				};
			}
		});
		
		tcCartSinglePrice.setCellFactory(new Callback<TableColumn<BasketEntryContainer, Integer>, TableCell<BasketEntryContainer, Integer>>() {

			@Override
			public TableCell<BasketEntryContainer, Integer> call(
					TableColumn<BasketEntryContainer, Integer> param) {
				return new TableCell<BasketEntryContainer, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						if(empty) {
							setText(null);
						} else {
							setText(String.format("€ %.2f", ((float)item)/100));
						}
					}
					
				};
			}
		});
		
		tcCartSum.setCellFactory(new Callback<TableColumn<BasketEntryContainer, Integer>, TableCell<BasketEntryContainer, Integer>>() {

			@Override
			public TableCell<BasketEntryContainer, Integer> call(
					TableColumn<BasketEntryContainer, Integer> param) {
				return new TableCell<BasketEntryContainer, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						if(empty) {
							setText(null);
						} else {							
							setText(String.format("€ %.2f", ((float)item)/100));
						}
					}
					
				};
			}
		});
	}
	
	private void reloadTable() {
		loadServiceData();
		loadBasketSum();
	}

	private void loadBasketSum() {
		int basketSumInCent = 0;
		for(BasketEntryContainer piv : basketEntries) {
			basketSumInCent += piv.getSumInCent();
		}
		lblTotalSum.setText(String.format("€ %.2f", ((float)basketSumInCent)/100));
	}

	/**
	 * Lädt alle Daten für den Warenkorb
	 */
	private void loadServiceData() {
		basketEntries = FXCollections.observableList(new ArrayList<BasketEntryContainer>());
		// Durchlaufe alle Entries des Baskets
		try {
			for(KeyValuePairDto<EntryDto, Boolean> piv : entryService.getEntry(parentController.getBasket().getId())) {
				// Nur nicht-stornierte Tickets anzeigen
				if(piv.getValue() != null) {
					BasketEntryContainer entry = new BasketEntryContainer();
					entry.setEntry(piv.getKey());
					entry.setIsTicket(piv.getValue());
					//Wenn der Entry ein Ticket ist, füge die Ticket-Informationen hinzu
					if(entry.getIsTicket()) {
						KeyValuePairDto<TicketDto, Boolean> ticket = ticketService.getTicketForEntry(piv.getKey().getId());
						entry.setTicket(ticket.getKey());
						entry.setHasSeat(ticket.getValue());
						KeyValuePairDto<PerformanceDto, KeyValuePairDto<ShowDto, KeyValuePairDto<LocationDto, KeyValuePairDto<RowDto, SeatDto>>>> ticketInfo = ticketService.getPerformanceShowLocationRowSeatByTicket(ticket.getKey().getId());
						entry.setPerformance(ticketInfo.getKey());
						entry.setShow(ticketInfo.getValue().getKey());
						entry.setLocation(ticketInfo.getValue().getValue().getKey());
						if(entry.getHasSeat()) {
							entry.setRow(ticketInfo.getValue().getValue().getValue().getKey());
							entry.setSeat(ticketInfo.getValue().getValue().getValue().getValue());
						}
					}
					basketEntries.add(entry);
				}
			}
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog(e.getLocalizedMessage());
			err.showAndWait();
		}
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
		if(!isInitialized) {
			// Lädt die Daten in den Table
			reloadTable();
			isInitialized = true;
		}
		
	}
}
