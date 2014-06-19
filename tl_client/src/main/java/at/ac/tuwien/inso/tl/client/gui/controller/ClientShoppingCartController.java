package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.ReceiptService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BasketEntryContainer;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.InvoiceCreator;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PaymentTypeDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.ReceiptDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import at.ac.tuwien.inso.tl.dto.TicketDto;

@Controller
@Scope("prototype")
public class ClientShoppingCartController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientShoppingCartController.class);

	@Autowired private TicketService ticketService;
	@Autowired private BasketService basketService;
	@Autowired private ReceiptService receiptService;
	@FXML private BorderPane bpCart;
	@FXML private TableView<BasketEntryContainer> tvCart;
	@FXML private TableColumn<BasketEntryContainer, String> tcCartDescription;
	@FXML private TableColumn<BasketEntryContainer, Boolean> tcCartStatus;
	@FXML private TableColumn<BasketEntryContainer, String> tcCartSinglePrice;
	@FXML private TableColumn<BasketEntryContainer, Integer> tcCartAmount;
	@FXML private TableColumn<BasketEntryContainer, String> tcCartSum;
	@FXML private TableColumn<BasketEntryContainer, Boolean> tcCartSelection;
	@FXML private Label lblTotalSum;
	@FXML private Label lblCartHeadline;
	
	@FXML private BorderPane bpPayment;
	@FXML private ChoiceBox<String> cbPaymentType;
	@FXML private Label lblOpenAmount;
	@FXML private Button btnPaymentReceived;
	@FXML private Button btnPaymentBackToCart;
	@FXML private Button btnAbortProcedure;
	@FXML private Button btnCheckout;
	@FXML private Button btnRemoveSelected;
	
	@FXML private BorderPane bpReceipt;
	@FXML private TextArea taReceipt;
	
	private ClientSellTicketController parentController;
	@Autowired private ClientMainController startpageController;
	private boolean isInitialized = false;
	private int soldSelected = 0;
	private int reservedSelected = 0;
	
	/**
	 * Enthält alle Entries des Baskets
	 */
	private ObservableList<BasketEntryContainer> basketEntries;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.debug("initialize ClientShoppingCartController");
		
		// Schriftart von Rechnungsfeld setzen
		taReceipt.setStyle("-fx-font-family: 'Courier New', Lucida Console, monospace");
		
		// Setzt die Eigenschaften, welche in den Spalten angezeigt werden sollen
		tcCartDescription.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, String>(
				"description"));
		tcCartStatus.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer,Boolean>("sold"));
		tcCartSinglePrice.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, String>(
				"singlePriceString"));
		tcCartAmount.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, Integer>(
				"amount"));
		tcCartSum.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer, String>(
				"sumPriceString"));
		tcCartSelection.setCellValueFactory(new PropertyValueFactory<BasketEntryContainer,Boolean>("selected"));
        
		// Setzt das Format der Eigenschaften in den Spalten
		tcCartSelection.setCellFactory(CheckBoxTableCell.forTableColumn(tcCartSelection));
		
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
							setStyle("-fx-font-weight: bold;");
							setText(item);
						}
					}
					
				};
			}
		});
		
		tcCartStatus.setCellFactory(new Callback<TableColumn<BasketEntryContainer, Boolean>, TableCell<BasketEntryContainer, Boolean>>() {

			@Override
			public TableCell<BasketEntryContainer, Boolean> call(
					TableColumn<BasketEntryContainer, Boolean> param) {
				return new TableCell<BasketEntryContainer, Boolean>() {
					@Override
					public void updateItem(Boolean item, boolean empty) {
						if(empty) {
							setText(null);
						} else {
							setAlignment(Pos.CENTER);
							if(item) {
								setText(BundleManager.getBundle().getString("cartpage.sold"));
								setEditable(false);
							} else {
								setText(BundleManager.getBundle().getString("cartpage.reserved"));
								setEditable(true);
							}
						}
					}
					
				};
			}
		});
		
		tcCartSinglePrice.setCellFactory(new Callback<TableColumn<BasketEntryContainer, String>, TableCell<BasketEntryContainer, String>>() {

			@Override
			public TableCell<BasketEntryContainer, String> call(
					TableColumn<BasketEntryContainer, String> param) {
				return new TableCell<BasketEntryContainer, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if(empty) {
							setText(null);
						} else {
							setAlignment(Pos.CENTER);
							setText(item);
						}
					}
					
				};
			}
		});
		
		tcCartAmount.setCellFactory(new Callback<TableColumn<BasketEntryContainer, Integer>, TableCell<BasketEntryContainer, Integer>>() {

			@Override
			public TableCell<BasketEntryContainer, Integer> call(
					TableColumn<BasketEntryContainer, Integer> param) {
				return new TableCell<BasketEntryContainer, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						if(empty) {
							setText(null);
						} else {				
							setAlignment(Pos.CENTER);
							setText(item.toString());
						}
					}
					
				};
			}
		});
		
		tcCartSum.setCellFactory(new Callback<TableColumn<BasketEntryContainer, String>, TableCell<BasketEntryContainer, String>>() {

			@Override
			public TableCell<BasketEntryContainer, String> call(
					TableColumn<BasketEntryContainer, String> param) {
				return new TableCell<BasketEntryContainer, String>() {
					@Override
					public void updateItem(String item, boolean empty) {
						if(empty) {
							setText(null);
						} else {
							setAlignment(Pos.CENTER);
							setText(item);
						}
					}
					
				};
			}
		});
		
		
		// Zahlungsarten zur ChoiceBox hinzufügen
		cbPaymentType.getItems().add(BundleManager.getBundle().getString("paymenttype.cash"));
		cbPaymentType.getItems().add(BundleManager.getBundle().getString("paymenttype.bank"));
		cbPaymentType.getItems().add(BundleManager.getBundle().getString("paymenttype.creditcard"));
	}
	
	private void reloadCartPage() {
		loadServiceData();
		loadBasketSum();
		setAbortButton();
		reservedSelected = 0;
		soldSelected = 0;
		validateSelection();
		lblCartHeadline.setText(String.format("%s #%d", BundleManager.getBundle().getString("cartpage"), getParentController().getBasket().getId()));
	}

	private void setAbortButton() {
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSold()) {
				btnAbortProcedure.setDisable(true);
			}
		}
	}

	private void loadBasketSum() {
		int basketSumInCent = 0;
		int basketSumInPoints = 0;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getEntry().getBuyWithPoints()) {
				basketSumInPoints += piv.getSumInPoints();
			} else {
				basketSumInCent += piv.getSumInCent();
			}
		}
		
		String pointsPrice = "";
		if(basketSumInPoints != 0) {
			pointsPrice = String.format(" + P %d", basketSumInPoints);
		}
		lblTotalSum.setText(String.format("€ %.2f%s", ((float)basketSumInCent)/100, pointsPrice));
	}

	/**
	 * Lädt alle Daten für den Warenkorb
	 */
	private void loadServiceData() {
		basketEntries = FXCollections.observableList(new ArrayList<BasketEntryContainer>());
		// Durchlaufe alle Entries des Baskets
		try {
			for (ContainerDto cDto : basketService.getEntryTicketArticlePerformanceRowSeatContainers(getParentController().getBasket().getId())) {
				// Nur nicht-stornierte Tickets anzeigen
				BasketEntryContainer entry = new BasketEntryContainer(cDto);
				if (entry.getSold() == true) {
					entry.selectedProperty().addListener(
							new ChangeListener<Boolean>() {

								@Override
								public void changed(
										ObservableValue<? extends Boolean> arg0,
										Boolean arg1, Boolean arg2) {
									if (arg2 == true) {
										incrementSoldSelected();
									} else {
										decrementSoldSelected();
									}

								}
							});
				} else {
					entry.selectedProperty().addListener(
							new ChangeListener<Boolean>() {

								@Override
								public void changed(
										ObservableValue<? extends Boolean> arg0,
										Boolean arg1, Boolean arg2) {
									if (arg2 == true) {
										incrementReservedSelected();
									} else {
										decrementReservedSelected();
									}

								}
							});
				}

				basketEntries.add(entry);
			}
			tvCart.setItems(basketEntries);
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage) bpCart.getParent()
					.getScene().getWindow(), BundleManager.getExceptionBundle()
					.getString("cartpage.load_cart_error"));
			err.show();
		}
	}

	@FXML
	private void handleFinishProcedure() {
		LOG.debug("handleFinishProcedure clicked");
		startpageController.closeSelectedTab();
	}
	
	@FXML
	private void handleAbortProcedure() {
		LOG.debug("handleAbortProcedure clicked");
		try {
			basketService.undoBasket(getParentController().getBasket().getId());
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage)bpCart.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("cartpage.undobasket_error"));
			err.show();
		}
		startpageController.closeSelectedTab();
	}
	
	@FXML
	private void handleCheckout() {
		LOG.debug("handleCheckout clicked");
		purgeSold();
		bpCart.setVisible(false);
		cbPaymentType.getSelectionModel().select(0);
		loadCheckoutSum();
		bpPayment.setVisible(true);
	}

	@FXML
	private void handleSelectNone() {
		LOG.debug("handleSelectNone clicked");
		for(BasketEntryContainer piv : basketEntries) {
			piv.setSelected(false);
		}
	}

	@FXML
	private void handleSelectAll() {
		LOG.debug("handleSelectAll clicked");
		for(BasketEntryContainer piv : basketEntries) {
			if(!piv.getSold()) {
				piv.setSelected(true);
			}
		}
	}
	
	@FXML
	private void handlePaymentBackToCart() {
		LOG.debug("handlePaymentBackToCart clicked");
		bpPayment.setVisible(false);
		bpCart.setVisible(true);
	}
	
	@FXML
	private void handlePaymentReceived() {
		LOG.debug("handlePaymentReceived clicked");
		List<EntryDto> receiptList = new ArrayList<EntryDto>();
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected()) {
				receiptList.add(piv.getEntry());
			}
		}
		try {
			ReceiptDto r = receiptService.createReceiptforEntries(receiptList, PaymentTypeDto.values()[cbPaymentType.getSelectionModel().getSelectedIndex()]);
			bpPayment.setVisible(false);
			taReceipt.setText(InvoiceCreator.createInvoice(r, getParentController().getBasket(), basketEntries, getParentController().getCustomer(), PaymentTypeDto.values()[cbPaymentType.getSelectionModel().getSelectedIndex()]));
			bpReceipt.setVisible(true);
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage)bpCart.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("cartpage.create_receipt_error"));
			err.show();
		}
		
	}
	
	@FXML
	private void handleRemoveSelected() {
		LOG.debug("handle clicked");
		purgeSold();
		List<BasketEntryContainer> deleteList = new ArrayList<BasketEntryContainer>();
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected()) {
				deleteList.add(piv);
			}
		}
		
		for(BasketEntryContainer d : deleteList) {
			try {
				ticketService.undoTicket(d.getTicket().getId());
				basketEntries.remove(d);
			} catch (ServiceException e) {
				ErrorDialog err = new ErrorDialog((Stage)bpCart.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("cartpage.undo_article_error"));
				err.show();
			}
		}
		loadBasketSum();
	}
	
	/**
	 * Deselektiert alle Entries, die schon verkauft sind.
	 */
	private void purgeSold() {
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected() && piv.getSold()) {
				piv.setSelected(false);
			}
		}
	}

	@FXML
	private void handleReceiptBackToCart() {
		LOG.debug("handleReceiptBackToCart clicked");
		getParentController().setCenterContent("/gui/ClientShoppingCartGui.fxml");
	}
	
	@Override
	public void setParentController(ClientSellTicketController cont) {
		this.parentController = cont;
		if(!isInitialized) {
			// Lädt die Daten in den Table
			reloadCartPage();
			isInitialized = true;
		}
		
	}
	
	private ClientSellTicketController getParentController() {
		return parentController;
	}
	
	private void loadCheckoutSum() {
		int checkoutSumInCent = 0;
		for(BasketEntryContainer piv : basketEntries) {
			if(piv.getSelected()) {
				checkoutSumInCent += piv.getSumInCent();
			}
			lblOpenAmount.setText(String.format("€ %.2f", ((float)checkoutSumInCent)/100));
			
		}
	}
	
	private void incrementSoldSelected() {
		soldSelected += 1;
		validateSelection();
	}
	
	private void decrementSoldSelected() {
		soldSelected -= 1;
		validateSelection();
	}
	
	private void incrementReservedSelected() {
		reservedSelected += 1;
		validateSelection();
	}
	
	private void decrementReservedSelected() {
		reservedSelected -= 1;
		validateSelection();
	}
	
	private void validateSelection() {
		if(soldSelected == 0 && reservedSelected != 0) {
			btnCheckout.setDisable(false);
			btnRemoveSelected.setDisable(false);
		} else {
			btnCheckout.setDisable(true);
			btnRemoveSelected.setDisable(true);
		}
	}
}
