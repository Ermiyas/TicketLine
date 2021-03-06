/**
 * Sample Skeleton for "ItemListForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.ContainerDto;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;
import at.ac.tuwien.inso.tl.dto.FieldError;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class ItemStornoMainFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ItemStornoMainFormController.class);

	private static final String LONG_DATE_FORMAT = "dd.MM.yyyy";
	
	// div. Services einbinden
	@Autowired private BasketService basketService;				// Basket-Services
	@Autowired private CustomerService customerService;			// Customer-Services	// TODO nur waehrend Entwicklung noetig
	@Autowired private EntryService entryService;				// Entry-Services
	@Autowired private TicketService ticketService;				// Ticket-Services
	
	// FXML-injizierte Variablen

    @FXML private ResourceBundle resources;				// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;							// URL location of the FXML file that was given to the FXMLLoader

    @FXML private Label lbTopTitle;												// Haupt-Titel
    @FXML private AnchorPane apItemStornoMainPane;								// eigenes Root-Pane
    @Autowired private ClientMainController apClientMainController;				// vermutlich uebergeordneter Controller, falls ....

    @FXML private VBox vbItemSearchPane;										// Sammel-Pane der Element-Suche
    
    @FXML private TitledPane tpBasketSearch;
    @FXML private Label lbBasketNumber;	
    @FXML private TextField txtBasketNumber;									// Buchungs-Nummer
    
    @FXML private AnchorPane apCustomerSearchPane;								// eingebettete Kunden-Suche
    @FXML private CustomerBaseFormController apCustomerSearchPaneController;	// zugehoeriger Kontroller
    
    @FXML private AnchorPane apMarkEntryList;									// eingebettete Item-Liste zu markieren der Elemente
    @FXML private ItemListFormController apMarkEntryListController;				// zugehoeriger Kontroller
    
    @FXML private AnchorPane apDeleteEntryList;									// eingebettete Item-Liste zu loeschen der Elemente
    @FXML private ItemListFormController apDeleteEntryListController;			// zugehoeriger Kontroller
    
    @FXML private TitledPane tpBasketList;
    @FXML private TableView<ItemDto> tvBasketList;								// Tabelle der Warenkoerbe
    @FXML private TableColumn<ItemDto, String> tcBasketDate;
    @FXML private TableColumn<ItemDto, String> tcBasketFirstname;
    @FXML private TableColumn<ItemDto, String> tcBasketItemcount;
    @FXML private TableColumn<ItemDto, String> tcBasketLastname;
    @FXML private TableColumn<ItemDto, String> tcBasketNumber;
    
    @FXML private ListView<String> lvMessageList;								// Message-Box
    
    @FXML private ToolBar tbVisibleBar;											// Button-Leiste
    @FXML private Button btnDeleteCancel;
    @FXML private Button btnDeleteConfirm;
    @FXML private Button btnSearchBasket;
    @FXML private Button btnSearchClose;
    @FXML private Button btnSearchDelete;
    @FXML private Button btnSearchMark;
    @FXML private Button btnSearchReset;
    @FXML private Button btnSearchSearch;
    
    
    // --- Initialisierung ---------------------------------------
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
        assert resources != null : 				"\"resources\" was not injected: check your Controller-file 'ItemListFormController.java'.";
        assert location != null : 				"\"location\" was not injected: check your Controller-file 'ItemListFormController.java'.";
        assert apCustomerSearchPane != null : 	"fx:id=\"apCustomerSearchPane\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert apDeleteEntryList != null : 		"fx:id=\"apDeleteEntryList\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert apItemStornoMainPane != null : 	"fx:id=\"apItemStornoMainPane\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert apMarkEntryList != null : 		"fx:id=\"apMarkEntryList\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnDeleteCancel != null : 		"fx:id=\"btnDeleteCancel\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnDeleteConfirm != null : 		"fx:id=\"btnDeleteConfirm\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnSearchBasket != null : 		"fx:id=\"btnSearchBasket\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnSearchClose != null : 		"fx:id=\"btnSearchClose\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnSearchDelete != null : 		"fx:id=\"btnSearchDelete\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnSearchMark != null : 			"fx:id=\"btnSearchMark\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnSearchReset != null : 		"fx:id=\"btnSearchReset\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert btnSearchSearch != null : 		"fx:id=\"btnSearchSearch\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert lbBasketNumber != null : 		"fx:id=\"lbBasketNumber\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert lbTopTitle != null : 			"fx:id=\"lbTopTitle\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert lvMessageList != null : 			"fx:id=\"lvMessageList\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tbVisibleBar != null : 			"fx:id=\"tbVisibleBar\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tcBasketDate != null : 			"fx:id=\"tcBasketDate\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tcBasketFirstname != null : 		"fx:id=\"tcBasketFirstname\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tcBasketItemcount != null : 		"fx:id=\"tcBasketItemcount\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tcBasketLastname != null : 		"fx:id=\"tcBasketLastname\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tcBasketNumber != null : 		"fx:id=\"tcBasketNumber\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tpBasketList != null : 			"fx:id=\"tpBasketList\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tpBasketSearch != null : 		"fx:id=\"tpBasketSearch\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert tvBasketList != null : 			"fx:id=\"tvBasketList\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert txtBasketNumber != null : 		"fx:id=\"txtBasketNumber\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";
        assert vbItemSearchPane != null : 		"fx:id=\"vbItemSearchPane\" was not injected: check your FXML file 'ItemStornoMainForm.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        // --- eigene Initialisierung ---------------------------------
        
        // Ein Listener, um bei selection changes in der BasketList die MarkedEntriesList laufend zu aktualisieren
        tvBasketList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ItemDto>() {
            public void changed(ObservableValue<? extends ItemDto> observable, ItemDto oldValue, ItemDto newValue) {
                LOG.info("Aenderungen Search-Liste");
                
            	hideMessage();
                // Daten in Markierungsliste aktualisieren
                if (newValue != null) {
                    try {
						apMarkEntryListController.setList(newValue.getBasket());
					} catch (ServiceException e) {
						showExcMessage("stornopage.mark." + e.getLocalizedMessage());
					}
                } else {
                	apMarkEntryListController.setList();
                }
            }
        });
        
        // (de-)mark current selected row on DoubleClick
        apMarkEntryListController.getTvItemList().setRowFactory(
        		new Callback<TableView<ItemListFormController.ItemDto>, TableRow<ItemListFormController.ItemDto>>() {  
            @Override  
            public TableRow<ItemListFormController.ItemDto> call(TableView<ItemListFormController.ItemDto> tableView2) {  
            	LOG.info("Mouse-Event Doppel-Klick Handler installieren");
                final TableRow<ItemListFormController.ItemDto> row = new TableRow<>();  
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {  
                    @Override  
                    public void handle(MouseEvent event) {  
                    	LOG.info("Doppel-Klick");
                        final int index = row.getIndex();  
                        if (event.getClickCount() == 2 && index >= 0 && index < apMarkEntryListController.getTvItemList().getItems().size()) {
                        	markItem();
                            event.consume();  
                        }  
                    }  
                });  
                return row;  
            }  
        });  
        
		// Mit Listener Basket-Nummer schon waehrend Eingabe ueberpruefen
		txtBasketNumber.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String alt, String neu) {
				LOG.info("");
				// Nur Integers erlauben
				if (! neu.trim().equals("")) {
					try {
						String.format("%d", Integer.parseInt(neu));
					} catch (NumberFormatException e) {
						txtBasketNumber.setText(alt);
					}
				}
			}
	    });

		// Platzhalter-Text fuer leere Liste festlegen
		tvBasketList.setPlaceholder(new Text(intString("stornopage.emptybasketlist")));
        
		// keine weiteren leeren Spalten anzeigen
//		tvBasketList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tvBasketList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		// Am Anfang die Such-Auswahl anzeigen
        apCustomerSearchPaneController.setPaneMode(CustomerBaseFormController.PaneMode.SEARCH);
        apCustomerSearchPaneController.setTitle("stornopage.search_criteria");
//        apDeleteEntryListController.setTitle("stornopage.deletelist");
//        apMarkEntryListController.setTitle("stornopage.entrylist");
        apDeleteEntryListController.getTvItemList().setFocusTraversable(false);
        hideMessage();
		showSearchPane();
		
		// verzoegerte Initialisierung - Im Init geht ev. noch nicht alles, da das Tab noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				LOG.info("");
				if (null != txtBasketNumber) {
			        txtBasketNumber.requestFocus();
				}
		    }
		});
	}

	// --- Getter fuer alle Tabellenspalten, ... -----------------------------

//	public BasketService 				getBasketService() { 					return basketService; }
//	public CustomerService 				getCustomerService() { 					return customerService; }
//	public AnchorPane 					getApCustomerSearchPane() { 			return apCustomerSearchPane; }
//	public AnchorPane 					getApDeleteItemList() { 				return apDeleteEntryList; }
//	public AnchorPane 					getApItemStornoMainPane() { 			return apItemStornoMainPane; }
//	public AnchorPane 					getApMarkItemList() { 					return apMarkEntryList; }
	public Button 						getBtnDeleteCancel() { 					return btnDeleteCancel; }
	public Button 						getBtnDeleteConfirm() { 				return btnDeleteConfirm; }
	public Button						getBtnSearchBasket() {					return btnSearchBasket; }
	public Button 						getBtnSearchClose() { 					return btnSearchClose; }
	public Button 						getBtnSearchDelete() { 					return btnSearchDelete; }
	public Button 						getBtnSearchMark() { 					return btnSearchMark; }
	public Button 						getBtnSearchReset() { 					return btnSearchReset; }
	public Button 						getBtnSearchSearch() { 					return btnSearchSearch; }
	
//	public CustomerBaseFormController 	getApCustomerSearchPaneController() { 	return apCustomerSearchPaneController; }
//	public ItemListFormController 		getApDeleteItemListController() { 		return apDeleteEntryListController; }
//	public ItemListFormController 		getApMarkItemListController() { 		return apMarkEntryListController; }
//	public Label 						getLbBasketNumber() { 					return lbBasketNumber; }
//	public Label 						getLbTopTitle() { 						return lbTopTitle; }
//	public ListView<String> 			getLvMessageList() { 					return lvMessageList; }
//	public ResourceBundle 				getResources() { 						return resources; }
//	public TableColumn<ItemDto, String> getTcBasketDate() { 					return tcBasketDate; }
//	public TableColumn<ItemDto, String> getTcBasketFirstname() { 				return tcBasketFirstname; }
//	public TableColumn<ItemDto, String> getTcBasketItemcount() { 				return tcBasketItemcount; }
//	public TableColumn<ItemDto, String> getTcBasketLastname() { 				return tcBasketLastname; }
//	public TableColumn<ItemDto, String> getTcBasketNumber() { 					return tcBasketNumber; }
//	public TableView<ItemDto> 			getTvBasketList() { 					return tvBasketList; }
//	public TextField 					getTxtBasketNumber() { 					return txtBasketNumber; }
//	public TitledPane 					getTpBasketList() { 					return tpBasketList; }
//	public TitledPane 					getTpBasketSearch() { 					return tpBasketSearch; }
//	public ToolBar 						getTbVisibleBar() { 					return tbVisibleBar; }
//	public URL 							getLocation() { 						return location; }
//	public VBox 						getVbItemSearchPane() { 				return vbItemSearchPane; }

	// --- Button-Handler ----------------------------------
	
    /**
     * Zum Stornieren ausgewaehlte Eintraege doch nicht loeschen, sondern zurueck zur Auswahl
     * 
     * @param event
     */
    @FXML void handleBtnDeleteCancel(ActionEvent event) {
    	LOG.info("Cancle Storno-Action");
        hideMessage();
    	showSearchPane();
    }

    /**
     * Ausgewaehlte (markierte) Eintraege stornieren (loschen)
     * 
     * @param event
     */
    @FXML void handleBtnDeleteConfirm(ActionEvent event) {
    	LOG.info("Confirm Storno-Action");
    	Boolean deleteOk = true;
        hideMessage();
        List<ContainerDto> delContainer = apDeleteEntryListController.getList();
        List<ContainerDto> markContainer = apMarkEntryListController.getList();
		for (ContainerDto container : apDeleteEntryListController.getList()) {
			if (deleteOk) {
				LOG.debug("Eintrag loeschen: " + container);
				try {
					entryService.undoEntry(container.getEntryDto().getId());
					// Loesch-Meldung anzeigen
					showMessage(intString("stornopage.deleted") + ": " + apDeleteEntryListController.getItem(container).getDescr());
					// Eintrag aus bisherigen Listen entfernen
					markContainer.remove(container);
					delContainer.remove(container);
				} catch (ValidationException e1) {
					showExcMessage("stornopage.delete." + e1.toString());
					showExcMessage(e1.getFieldErrors());
					deleteOk = false;
				} catch (ServiceException e1) {
					showExcMessage("stornopage.delete." + e1.getLocalizedMessage());
					deleteOk = false;
				}
			}
		}
		if (deleteOk) {
			apMarkEntryListController.setList(markContainer);
	    	showSearchPane();
		} else {
			apDeleteEntryListController.setList(delContainer);
			apDeleteEntryListController.markAllItems(true);
			apMarkEntryListController.setList(markContainer);
			apMarkEntryListController.markItems(delContainer, true);
	    	showDeletePane();
		}
    }
    
    /**
     * Startet Warenkorbverwaltung
     * 
     * @param event
     */
    @FXML void handleBtnSearchBasket(ActionEvent event) {
    	LOG.info("Call Basket-Action");
        hideMessage();

        if(tvBasketList.getSelectionModel().getSelectedItem() != null) {
        	apClientMainController.openExistingBasket(tvBasketList.getSelectionModel().getSelectedItem().basket, getCustomer());
        }
    }

    /**
     * Storno-Tab schlieszen
     * 
     * @param event
     */
    @FXML void handleBtnSearchClose(ActionEvent event) {
    	LOG.info("Close Tab");
        hideMessage();
		closeStornoPane();
    }

    /**
     * Das Loeschen der markierten Eintraege bestaetigen lassen
     * 
     * @param event
     */
    @FXML void handleBtnSearchDelete(ActionEvent event) {
    	LOG.info("");
        hideMessage();
    	apDeleteEntryListController.setList(apMarkEntryListController.getMarkedList());
    	apDeleteEntryListController.markAllItems(true);
    	showDeletePane();
    }

    /**
     * Ausgewaehlten Eintrag fuer Storno (de-)markieren
     * @param event
     */
    @FXML void handleBtnSearchMark(ActionEvent event) {
    	LOG.info("");
    	markItem();
    }

    /**
     * (De-)Mark current selected item
     */
    public void markItem() {
    	if (apMarkEntryListController.getItem() != null && 
    			apMarkEntryListController.getItem().getReversable()) {
            hideMessage();
	    	apMarkEntryListController.toggleSelectedItem();
    	}
    }
    /**
     * Such-Kriterien leeren
     * 
     * @param event
     */
    @FXML void handleBtnSearchReset(ActionEvent event) {
    	LOG.info("Clear all Search-Criterias");
        hideMessage();
    	txtBasketNumber.setText("");
    	apCustomerSearchPaneController.setData();
    }

    /**
     * Entsprechend der Suchkriterien (nach Basket-Nr. und/oder Customer-Daten)
     * passende Warenkoerbe suchen
     * 
     * @param event
     */
    @FXML void handleBtnSearchSearch(ActionEvent event) {
    	LOG.info("");
        hideMessage();
        List<KeyValuePairDto<BasketDto, CustomerDto>> basketCustomerList = null;
        try {
			basketCustomerList = basketService.findBasket(Integer.parseInt(txtBasketNumber.getText()), apCustomerSearchPaneController.getData());
		} catch (NumberFormatException e) {
			// Service-Exception, wenn BasketNumber kein Integer
			try {
				basketCustomerList = basketService.findBasket(null, apCustomerSearchPaneController.getData());
			} catch (ServiceException e1) {
				showExcMessage("stornopagege.search." + e.getLocalizedMessage());	// Service-Exception, wenn keine Customers gefunden?!
			}
		} catch (ServiceException e) {
			showExcMessage("stornopage.search." + e.getLocalizedMessage());		// Service-Exception, wenn keine Customers gefunden?!
		}
        setBasketCustomerList(basketCustomerList);
        
        tvBasketList.requestFocus();
        // TODO aus Performancegruenden nicht gleich 1. Zeile auswaehlen
//        if (tvBasketList.getItems().size() > 0) {
//        	tvBasketList.getSelectionModel().select(0);
//        }
    }
    
    // --- Ansichten herrichten -------------------------------
    
    /**
     * Such-Ansicht aktivieren
     */
    private void showSearchPane() {
    	LOG.info("");
    	setSearchButtons();
    	vbItemSearchPane.setVisible(true);						// Such-Auswahl sichtbar machen
		apDeleteEntryList.setVisible(false);					// Loesch-Liste ausblenden
    }

    /**
     * Loesch-Ansicht aktivieren
     */
    private void showDeletePane() {
    	LOG.info("");
    	setDeleteButtons();
    	vbItemSearchPane.setVisible(false);						// Such-Auswahl ausblenden
		apDeleteEntryList.setVisible(true);						// Loesch-Liste sichtbar machen
    }

    /**
     * Such-Buttons anzeigen
     */
    private void setSearchButtons() {
    	LOG.info("");
    	hideAllButtons();
    	btnSearchBasket.setVisible(true);
        btnSearchClose.setVisible(true);
        btnSearchDelete.setVisible(true);
        btnSearchMark.setVisible(true);
        btnSearchReset.setVisible(true);
        btnSearchSearch.setVisible(true);
    	tbVisibleBar.getItems().add(btnSearchSearch);
    	tbVisibleBar.getItems().add(btnSearchReset);
    	tbVisibleBar.getItems().add(btnSearchBasket);
    	tbVisibleBar.getItems().add(btnSearchMark);
    	tbVisibleBar.getItems().add(btnSearchDelete);
    	tbVisibleBar.getItems().add(btnSearchClose);
        btnSearchSearch.setDefaultButton(true);
        btnSearchClose.setCancelButton(true);
    }
    
    /**
     * Loesch-Buttons anzeigen
     */
    private void setDeleteButtons() {
    	LOG.info("");
    	hideAllButtons();
        btnDeleteCancel.setVisible(true);
        btnDeleteConfirm.setVisible(true);
    	tbVisibleBar.getItems().add(btnDeleteCancel);
    	tbVisibleBar.getItems().add(btnDeleteConfirm);
        btnDeleteConfirm.setDefaultButton(true);
        btnDeleteCancel.setCancelButton(true);
    }
    
    /**
     * Alle Buttons ausblenden
     */
    private void hideAllButtons() {
    	LOG.info("");
        btnSearchSearch.setDefaultButton(false);
        btnSearchClose.setCancelButton(false);
        btnDeleteConfirm.setDefaultButton(false);
        btnDeleteCancel.setCancelButton(false);
        
        btnDeleteCancel.setVisible(false);
        btnDeleteConfirm.setVisible(false);
        btnSearchBasket.setVisible(false);
        btnSearchClose.setVisible(false);
        btnSearchDelete.setVisible(false);
        btnSearchMark.setVisible(false);
        btnSearchReset.setVisible(false);
        btnSearchSearch.setVisible(false);
    	tbVisibleBar.getItems().clear();
    }
    
    // --- Basket-Methoden ---------------------------------------
    
	/**
	 * Basket-Liste leeren
	 */
	public void clearBasketList() {
		LOG.info("");
		
		// DTO alle Felder loeschen
		setBasketList();
	}

	/**
	 * Zeilen der Basket-Liste loeschen
	 */
	public void setBasketList() {
		LOG.info("");
		
		setBasketCustomerList((List<KeyValuePairDto<BasketDto, CustomerDto>>) null);
	}

	// TODO unnoetig, sobald setBasketCustomerList voll implementiert ist
//	/**
//	 * Zeilen der Basket-Liste setzen
//	 * 
//	 * @param basketDtoList
//	 */
//	public void setBasketList(List<BasketDto> basketDtoList) {
//		LOG.info("");
//		
//		// DTO uebernehmen und alle Felder setzen
//		
//		// Tabelle loeschen
//		tvBasketList.getItems().clear();
//		
//		if (basketDtoList != null) {
//			// Listen-Daten laden
//			ObservableList<ItemDto> baskets = FXCollections.observableArrayList();
//			for (BasketDto basket : basketDtoList) {
//				baskets.add(new ItemDto(basket));
//			}
//			
//			// Daten in Spalten laden
//		    tcBasketNumber.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("basketId"));		// "basketId" -> call 'ItemDto.getBasketId()'
//		    tcBasketDate.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("basketDate"));		// "basketDate" -> call 'ItemDto.getBasketDate()'
//		    tcBasketItemcount.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("itemCount"));	// "itemCount" -> call 'ItemDto.getItemCount()'
//		    tcBasketFirstname.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("firstname"));	// "firstname" -> call 'ItemDto.getFirstname()'
//		    tcBasketLastname.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("lastname"));	// "lastname" -> call 'ItemDto.getLastname()'
//			
////			tvBasketList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//			tvBasketList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
//			
//			// Daten in Tabelle schreiben
//			tvBasketList.setItems(baskets);
//		}
//	}
	
	/**
	 * Zeilen der Basket-Liste setzen
	 * 
	 * @param basketDtoList
	 */
	public void setBasketCustomerList(List<KeyValuePairDto<BasketDto, CustomerDto>> basketCustomerList) {
		LOG.info("");
		
		// DTO uebernehmen und alle Felder setzen
		
		// Tabelle loeschen
		tvBasketList.getItems().clear();
		
		if (basketCustomerList != null) {
			// Listen-Daten laden
			ObservableList<ItemDto> baskets = FXCollections.observableArrayList();
			for (KeyValuePairDto<BasketDto, CustomerDto> basketCustomer : basketCustomerList) {
				baskets.add(new ItemDto(basketCustomer));
			}
			
			// Daten in Spalten laden
		    tcBasketNumber.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("basketId"));		// "basketId" -> call 'ItemDto.getBasketId()'
		    tcBasketDate.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("basketDate"));		// "basketDate" -> call 'ItemDto.getBasketDate()'
		    tcBasketItemcount.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("itemCount"));	// "itemCount" -> call 'ItemDto.getItemCount()'
		    tcBasketFirstname.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("firstname"));	// "firstname" -> call 'ItemDto.getFirstname()'
		    tcBasketLastname.setCellValueFactory(new PropertyValueFactory<ItemDto, String>("lastname"));	// "lastname" -> call 'ItemDto.getLastname()'
			
//			tvBasketList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvBasketList.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
			
			// Daten in Tabelle schreiben
			tvBasketList.setItems(baskets);
		}
		
	}

	/**
	 * Alle Zeilen aus der Basket-Liste in neuer BasketList zurueckgeben
	 * 
	 * @return
	 */
	public List<BasketDto> getBasketList() {
		LOG.info("");
		
		ObservableList<ItemDto> itemList = tvBasketList.getItems();
		List<BasketDto> basketList = new ArrayList<BasketDto>();
		for (ItemDto item : itemList) {
			basketList.add(item.getBasket());
		}
		return basketList;
	}
	
	/**
	 * Aktuell ausgewaehltes Basket zurueckgeben
	 * 
	 * @return
	 */
	public BasketDto getBasket() {
		LOG.info("");
		
		// ausgewaehlte Zeile zurueckgeben
		return tvBasketList.getSelectionModel().getSelectedItem().getBasket();
	}
	
	/**
	 * Customer des aktuell ausgewaehlten Baskets zurueckgeben
	 * 
	 * @return
	 */
	public CustomerDto getCustomer() {
		LOG.info("");
		
		// ausgewaehlte Zeile zurueckgeben
		return tvBasketList.getSelectionModel().getSelectedItem().getCustomer();
	}
	
	// --- Methoden der MarkierungsListe ------------------------------------------
	
	/**
	 * Alle Zeilen der Markierungs-Liste mit Entries eines Warenkorbs befuellen
	 * 
	 * @return
	 */
	public void setMarkEntryList(BasketDto basket) {
		LOG.info("");
		
			try {
				apMarkEntryListController.setList(basket);
			} catch (ServiceException e) {
				showExcMessage("stornopage.mark." + e.getLocalizedMessage());
			}
	}
	
	/**
	 * Alle Zeilen der Markierungs-Liste in neuer Entry-Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<ContainerDto> getMarkEntryList() {
		LOG.info("");
		
		return apMarkEntryListController.getList();
	}
	
	/**
	 * Aktuell ausgewaehltes Entry der Markierungs-Liste zurueckgeben
	 * 
	 * @return
	 */
	public EntryDto getMarktEntry() {
		LOG.info("");
		
		// ausgewaehlte Zeile zurueckgeben
		return apMarkEntryListController.getEntry();
	}
	
	// --- Methoden der LoeschListe --------------------------------------------------

	/**
	 * Alle Zeilen der Loesch-Liste aus markierten Entries der Markierungs-Liste befuellen
	 * 
	 * @return
	 */
	public void setDeleteEntryList() {
		LOG.info("");
		apDeleteEntryListController.setList(apMarkEntryListController.getMarkedList());
	}
	
	/**
	 * Alle Zeilen der Loesch-Liste in neuer Entry-Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<ContainerDto> getDeleteEntryList() {
		LOG.info("");
		
		return apDeleteEntryListController.getList();
	}
	
	// --- Sonstige Getter, Setter und Hilfsmethoden ------------------------------------------------ 
    
	/**
	 * Titel der MainPane sprachabhaengig setzen
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		LOG.info("");
		
		lbTopTitle.setText(intString(title));
	}

	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, NULL in "" uebersetzt
	 * 
	 * @param title
	 */
	private static String intString(String text) {
		LOG.info("");
		return intString(text, true);
	}
	
	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, default NULL in "" uebersetzen
	 * 
	 * @param title
	 */
	private static String intString(String text, Boolean noNull) {
		LOG.info("");
		
		if (noNull == null) {
			noNull = true;
		}
		if (text == null && ! noNull) {
			return null;
		}
		if (text == null || text.trim().equals("")) {
			return "";
		}
		try {
			return BundleManager.getBundle().getString(text).trim();
		} catch (RuntimeException e) {
			return text.trim();
		}
	}

	/**
	 * versuchen Exception-Text sprachabhaengig international zu uebersetzen
	 * getrimmt, NULL in "" uebersetzen
	 * 
	 * @param title
	 */
	private static String intExcString(String text) {
		LOG.info("");
		return intExcString(text, true);
	}
	
	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, default NULL in "" uebersetzen
	 * 
	 * @param title
	 */
	private static String intExcString(String text, Boolean noNull) {
		LOG.info("");
		
		if (noNull == null) {
			noNull = true;
		}
		if (text == null && ! noNull) {
			return null;
		}
		if (text == null || text.trim().equals("")) {
			return "";
		}
		try {
			return BundleManager.getExceptionBundle().getString(text).trim();
		} catch (RuntimeException e) {
			return text.trim();
		}
	}

	/**
	 * Liste von Felder-Fehlern internationalisiert anzeigen, falls moeglich
	 * 
	 * @param list Liste von fehlern, die bei der Validierung von Feldern aufgetreten sind
	 */
	private void showExcMessage(List<FieldError> list) {
		for (FieldError errMsg : list) {
			showMessage(intExcString("customerpage." + errMsg.getField()) + ": " + intExcString("customerpage." + errMsg.getMessage()));
		}
	}
	
	/**
	 * Fehler anzeigen
	 * 
	 * @param msg Fehler-Message
	 */
	private void showExcMessage(String msg) {
		showMessage(intExcString(msg));
	}
	
	/**
	 * Mitteilung internationalisiert anzeigen, falls moeglich
	 * 
	 * @param msg
	 */
	private void showMessage(String msg) {
		lvMessageList.setVisible(true);
		lvMessageList.getItems().add(intString(msg));
		lvMessageList.setPrefHeight(12 + lvMessageList.getItems().size()*25);	// TODO dynamisch an Schrifthoehe anpassen
	}
	
	/**
	 * Mitteilungs-Box ausblenden
	 */
	private void hideMessage() {
		// Message-List entleeren und verbergen
		lvMessageList.setVisible(false);
		lvMessageList.getItems().clear();
		lvMessageList.setPrefHeight(0);
	}

	/**
	 * Soll die Kundenverwaltung schliessen
	 * 
	 * Ev. von Aufrufender Methode ueberschreiben,
	 * da hier zB. kein uebergeordnetes Tab geschlossen werden kaan
	 */
	public void closeStornoPane() {
		LOG.info("");
		
		// pro-forma alle Panes entfernen
		apItemStornoMainPane.getChildren().clear();

		// Tab schlieszen - der sichere Weg: eigenes Tab suchen 
		Parent parent = apItemStornoMainPane.getParent();
		TabPane tabPane = null;
		List<Object> objList = new ArrayList<Object>();
		objList.add(apItemStornoMainPane);
		while (parent != null && tabPane == null) {
			if (parent instanceof TabPane ) {
				tabPane = (TabPane) parent;
			} else {
				objList.add(parent);
			}
			parent = parent.getParent();
		}
		if (tabPane != null) {
			ObservableList<Tab> tabList = tabPane.getTabs();
			Tab myTab = null;
			for (Tab someTab : tabList) {
				Node someNode = someTab.getContent();
				if (objList.contains(someNode)) {
					myTab = someTab;
				}
			}
			if (myTab != null) {
				tabPane.getTabs().remove(myTab);
			} else {
				// TODO Alternativ aktuelles Tab schlieszen - neue Version v. Flo
				apClientMainController.closeSelectedTab();
			}
		} else {
			// TODO Alternativ aktuelles Tab schlieszen - neue Version v. Flo
			apClientMainController.closeSelectedTab();
		}
	}
	
	// --- ItemDto der BasketListe -------------------------------------------------
	
	/**
	 * Sub-Klasse, um die zusammengesetzen Listenelemente der Basket-Liste aufbauen zu koennen
	 * 
	 * @author Robert Bekker 8325143
	 *
	 */
	public class ItemDto {
		private BasketDto basket;
		private CustomerDto customer;
		private List<EntryDto> entryList;

		private String basketId;
		private String basketDate;
		private String itemCount;
		private String firstname;
		private String lastname;
		
		// TODO alter Konstruktor
//		public ItemDto(BasketDto basket) {
//			LOG.info("");
//
//			if (basket == null) {
//				basket = new BasketDto();
//			}
//
//			this.basket = basket;
//			// TODO testweise div. verknuepfte Dto's haendisch geholt, ist aber zu langsam
//			// TODO schlussendlich muss dies am Server zusammengestoepselt werden.
//			if (basket.getCustomerId() != null) {
//				try {
//					this.customer = customerService.getById(basket.getCustomerId());			// get Customer of Basket
//				} catch (ServiceException e) {
//					showExcMessage("stornopage.search." + e.getLocalizedMessage());
//				}
//				try {
//					this.entryList = entryService.getList(basket);				// get Entry-List by Basket
//				} catch (ServiceException e) {
//					showExcMessage("stornopage.search." + e.getLocalizedMessage());
//				}					
//			}
//			
//			// Werte von uebergebener BasketDto abspeichern
//			this.basketId = String.format("%d", basket.getId());
//			this.basketDate = new SimpleDateFormat(LONG_DATE_FORMAT).format(basket.getCreationdate());
//			if (this.customer != null) {
//				this.firstname = this.customer.getFirstname();
//				this.lastname = this.customer.getLastname();
//			} else {
//				this.firstname = "";
//				this.lastname = "";
//			}
//			if (this.entryList != null) {
//				this.itemCount = String.format("%d", this.entryList.size());
//			} else {
//				this.itemCount = "0";
//			}
//		}

		// TODO neuer Konstruktor
		public ItemDto(KeyValuePairDto<BasketDto, CustomerDto> basketCustomer) {
			LOG.info("");

			if (basket == null) {
				basket = new BasketDto();
			}

			this.basket = basketCustomer.getKey();
			this.customer = basketCustomer.getValue();
			// TODO Anzahl der Eintraege pro Basket verlangsamt Anzeige
			try {
				this.entryList = entryService.getList(basket);				// get Entry-List by Basket
			} catch (ServiceException e) {
				showExcMessage("stornopage.search." + e.getLocalizedMessage());
			}					
			
			// Werte von uebergebener BasketDto abspeichern
			this.basketId = String.format("%d", basket.getId());
			this.basketDate = new SimpleDateFormat(LONG_DATE_FORMAT).format(basket.getCreationdate());
			if (this.customer != null) {
				this.firstname = this.customer.getFirstname();
				this.lastname = this.customer.getLastname();
			} else {
				this.firstname = "";
				this.lastname = "";
			}
			if (this.entryList != null) {
				this.itemCount = String.format("%d", this.entryList.size());
			} else {
				this.itemCount = "0";
			}
		}

		public BasketDto 		getBasket() 	{ return basket; }
		public CustomerDto 		getCustomer() 	{ return customer; }
		public List<EntryDto> 	getEntryList() 	{ return entryList; }
		public String 			getBasketDate() { return basketDate; }
		public String 			getBasketId() 	{ return basketId; }
		public String 			getFirstname() 	{ return firstname; }
		public String 			getItemCount() 	{ return itemCount; }
		public String 			getLastname() 	{ return lastname; }
		
	}

}
