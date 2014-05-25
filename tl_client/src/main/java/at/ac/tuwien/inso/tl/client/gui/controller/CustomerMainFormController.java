/**
 * Sample Skeleton for "CustomerMainForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.exception.ValidationException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.FieldError;

@Controller @Scope("prototype") 
public class CustomerMainFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerMainFormController.class);
	
	public static enum PaneMode {CREATE, MANAGE, SELECT, VIEW, EDIT};	// alle moeglichne Init-Modes der Panes
	
	private PaneMode paneMode;	// eingestellter Display-Mode der Panes
	
	private CustomerDto editDto = new CustomerDto();
	private CustomerDto searchDto = new CustomerDto();
	
	// FXML-injizierte Variablen

	@Autowired
	private CustomerService customerService;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    

    
    @FXML // fx:id="apCustomerCreatePane"
    private AnchorPane apCustomerCreatePane;
    @FXML
    private CustomerBaseFormController apCustomerCreatePaneController;

    @FXML // fx:id="apCustomerDoubleList"
    private AnchorPane apCustomerDoubleList;
    @FXML
    private CustomerListFormController apCustomerDoubleListController;

    @FXML // fx:id="apCustomerDoubleViewPane"
    private AnchorPane apCustomerDoubleViewPane;
    @FXML
    private CustomerBaseFormController apCustomerDoubleViewPaneController;

    @FXML // fx:id="apCustomerDoubleFoundPane"
    private AnchorPane apCustomerDoubleFoundPane;
    @FXML
    private CustomerBaseFormController apCustomerDoubleFoundPaneController;

    @FXML // fx:id="apCustomerEditPane"
    private AnchorPane apCustomerEditPane;
    @FXML
    private CustomerBaseFormController apCustomerEditPaneController;

    @FXML // fx:id="apCustomerMainForm"
    private AnchorPane apCustomerMainForm;										// eigenes Root-Pane

    @FXML // fx:id="apCustomerSearchFoundPane"
    private AnchorPane apCustomerSearchFoundPane;
    @FXML
    private CustomerBaseFormController apCustomerSearchFoundPaneController;

    @FXML // fx:id="apCustomerSearchList"
    private AnchorPane apCustomerSearchList;
    @FXML
    private CustomerListFormController apCustomerSearchListController;

    @FXML // fx:id="apCustomerSearchPane"
    private AnchorPane apCustomerSearchPane;
    @FXML
    private CustomerBaseFormController apCustomerSearchPaneController;

    @FXML // fx:id="apCustomerViewPane"
    private AnchorPane apCustomerViewPane;
    @FXML
    private CustomerBaseFormController apCustomerViewPaneController;

    // Buttons
    
    @FXML // fx:id="btnApplyDuplicates"
    private Button btnApplyDuplicates;

    @FXML // fx:id="btnApplySearch"
    private Button btnApplySearch;

    @FXML // fx:id="btnBackDuplicates"
    private Button btnBackDuplicates;

    @FXML // fx:id="btnBackSearch"
    private Button btnBackSearch;

    @FXML // fx:id="btnBackShow"
    private Button btnBackShow;

    @FXML // fx:id="btnCancelChange"
    private Button btnCancelChange;

    @FXML // fx:id="btnCancelCreate"
    private Button btnCancelCreate;

    @FXML // fx:id="btnChangeShow"
    private Button btnChangeShow;

    @FXML // fx:id="btnChangeSearch"
    private Button btnChangeSearch;

    @FXML // fx:id="btnClear"
    private Button btnClear;

    @FXML // fx:id="btnClose"
    private Button btnClose;

    @FXML // fx:id="btnDelete"
    private Button btnDelete;

    @FXML // fx:id="btnDetails"
    private Button btnDetails;

    @FXML // fx:id="btnDiscard"
    private Button btnDiscard;

    @FXML // fx:id="btnNew"
    private Button btnNew;

    @FXML // fx:id="btnOverwrite"
    private Button btnOverwrite;

    @FXML // fx:id="btnResetChange"
    private Button btnResetChange;

    @FXML // fx:id="btnResetCreate"
    private Button btnResetCreate;

    @FXML // fx:id="btnResetSearch"
    private Button btnResetSearch;

    @FXML // fx:id="btnSaveChange"
    private Button btnSaveChange;

    @FXML // fx:id="btnSaveCreate"
    private Button btnSaveCreate;

    @FXML // fx:id="btnSaveDuplicates"
    private Button btnSaveDuplicates;

    @FXML // fx:id="btnSearch"
    private Button btnSearch;

    @FXML // fx:id="lvMessageList"
    private ListView<String> lvMessageList;

    @FXML // fx:id="lbTopTitle"
    private Label lbTopTitle;

    @FXML // fx:id="spLeftPanes"
    private StackPane spLeftPanes;

    @FXML // fx:id="spListPanes"
    private StackPane spListPanes;

    @FXML // fx:id="spRightPanes"
    private StackPane spRightPanes;

    @FXML // fx:id="tbVisibleBar"
    private ToolBar tbVisibleBar;

    // ------------ Button-Getter

    /**
	 * @return the btnApplyDuplicates
	 */
	public Button getBtnApplyDuplicates() {
		return btnApplyDuplicates;
	}

	/**
	 * @return the btnApplySearch
	 */
	public Button getBtnApplySearch() {
		return btnApplySearch;
	}

	/**
	 * @return the btnBackDuplicates
	 */
	public Button getBtnBackDuplicates() {
		return btnBackDuplicates;
	}

	/**
	 * @return the btnBackSearch
	 */
	public Button getBtnBackSearch() {
		return btnBackSearch;
	}

	/**
	 * @return the btnBackShow
	 */
	public Button getBtnBackShow() {
		return btnBackShow;
	}

	/**
	 * @return the btnCancelChange
	 */
	public Button getBtnCancelChange() {
		return btnCancelChange;
	}

	/**
	 * @return the btnCancelCreate
	 */
	public Button getBtnCancelCreate() {
		return btnCancelCreate;
	}

	/**
	 * @return the btnChangeShow
	 */
	public Button getBtnChangeShow() {
		return btnChangeShow;
	}

	/**
	 * @return the btnChangeSearch
	 */
	public Button getBtnChangeSearch() {
		return btnChangeSearch;
	}

	/**
	 * @return the btnClose
	 */
	public Button getBtnClose() {
		return btnClose;
	}

	/**
	 * @return the btnClear
	 */
	public Button getBtnClear() {
		return btnClear;
	}

	/**
	 * @return the btnDelete
	 */
	public Button getBtnDelete() {
		return btnDelete;
	}

	/**
	 * @return the btnDetails
	 */
	public Button getBtnDetails() {
		return btnDetails;
	}

	/**
	 * @return the btnDiscard
	 */
	public Button getBtnDiscard() {
		return btnDiscard;
	}

	/**
	 * @return the btnNew
	 */
	public Button getBtnNew() {
		return btnNew;
	}

	/**
	 * @return the btnOverwrite
	 */
	public Button getBtnOverwrite() {
		return btnOverwrite;
	}

	/**
	 * @return the btnResetChange
	 */
	public Button getBtnResetChange() {
		return btnResetChange;
	}

	/**
	 * @return the btnResetCreate
	 */
	public Button getBtnResetCreate() {
		return btnResetCreate;
	}

	/**
	 * @return the btnResetSearch
	 */
	public Button getBtnResetSearch() {
		return btnResetSearch;
	}

	/**
	 * @return the btnSaveChange
	 */
	public Button getBtnSaveChange() {
		return btnSaveChange;
	}

	/**
	 * @return the btnSaveCreate
	 */
	public Button getBtnSaveCreate() {
		return btnSaveCreate;
	}

	/**
	 * @return the btnSaveDuplicates
	 */
	public Button getBtnSaveDuplicates() {
		return btnSaveDuplicates;
	}

	/**
	 * @return the btnSearch
	 */
	public Button getBtnSearch() {
		return btnSearch;
	}

    // ------------ Button-Action-Handler

	// Handler for Button[fx:id="btnApplyDuplicates"] onAction
    /**
     * Duplikatfenster schlieszen, Duplikat statt Neuanlage uebernehmen
     * 
     * @param event
     */
    @FXML
    void handleBtnApplyDuplicates(ActionEvent event) {	// Duplikat statt Neuanlage uebernehmen und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// richtiges Pane anzeigen
		if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
			// Details-Pane anzeigen

			// DTO uebernehmen
			apCustomerViewPaneController.setData(apCustomerDoubleFoundPaneController.getData());
			
			// zur Detailanzeige wechseln
			setShowSetup();
		}
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
			// Search-Pane anzeigen

			// DTO uebernehmen
			apCustomerSearchPaneController.setData(apCustomerDoubleFoundPaneController.getData());
			
			// passende Search-List setzen
			try {
				// nach akuellen Daten aus dem VIEW-Panel filtern
				apCustomerSearchListController.setList(customerService.find(apCustomerDoubleFoundPaneController.getData()));
			} catch (ServiceException e) {
				apCustomerSearchListController.setList();
			}
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

			// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
			
			// zur Suchauswahl wechseln
			setSearchSetup();
		}
    }

    // Handler for Button[fx:id="btnApplySearch"] onAction
    /**
     * Suchfenster schlieszen, Suchauswahl uebernehmen
     * 
     * @param event
     */
    @FXML
    void handleBtnApplySearch(ActionEvent event) {		// Suchauswahl uebernehmen und zurueck, default zur Kundenansicht - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO uebernehmen
		apCustomerViewPaneController.setData(apCustomerSearchFoundPaneController.getData());
		
		// zur Detailanzeige wechseln
		setShowSetup();
    }

    // Handler for Button[fx:id="btnBackDuplicates"] onAction
    /**
     * Duplikatfenster schlieszen ohne Uebernahlem eines Duplikates, Neuanlage abaendern
     * 
     * @param event
     */
    @FXML
    void handleBtnBackDuplicates(ActionEvent event) {	// Zurueck von Duplicates zur Neuanlage
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zur Neuanlage wechseln
		setCreateSetup();
    }

    // Handler for Button[fx:id="btnBackSearch"] onAction
    /**
     * Suchfenster schlieszen ohne Uebernahme der Suchauswahl, Kundenanzeige oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnBackSearch(ActionEvent event) {		// Zurueck von Suche ohne Neuauswahl, default zur Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// zur Detailanzeige wechseln
		setShowSetup();
    }

    // Handler for Button[fx:id="btnBackShow"] onAction
    /**
     * Detailfenster schlieszen, Suchauswahl oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnBackShow(ActionEvent event) {		// Zurueck von Suche ohne Neuauswahl, default zur Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// zur Detailanzeige wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnCancelChange"] onAction
    /**
     * Kundenaenderung abbrechen, Fenster schlieszen und zurueck zur Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnCancelChange(ActionEvent event) {		// Zurueck ohne Aenderung, default zur Suchauswahl - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zur Suche wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnCancelCreate"] onAction
    /**
     * Kundenanlage abbrechen, Fenster schlieszen und zurueck zur Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnCancelCreate(ActionEvent event) {		// Zurueck ohne Neuanlage, default zur Suchauswahl - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zur Suche wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnChange"] onAction
    /**
     * Kundenanzeige schlieszen, Kundensuche oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnChangeShow(ActionEvent event) {		// Kundensuche 
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
		apCustomerSearchPaneController.setData(apCustomerViewPaneController.getData());
		
		// passende Search-List setzen
		try {
			// nach akuellen Daten aus dem VIEW-Panel filtern
			apCustomerSearchListController.setList(customerService.find(apCustomerViewPaneController.getData()));
		} catch (ServiceException e) {
			apCustomerSearchListController.setList();
		}
		apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

		// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		
		// zur Suchauswahl wechseln
		setSelectionSetup();
    }

    // Handler for Button[fx:id="btnChange"] onAction
    /**
     * Kundensuche schlieszen, Kundenaenderung oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnChangeSearch(ActionEvent event) {		// Kunden aendern
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO uebernehmen
		apCustomerEditPaneController.setData(apCustomerSearchFoundPaneController.getData());
		
		// zur Datenaenderung wechseln
		setChangeSetup();
    }

    // Handler for Button[fx:id="btnCancelClear"] onAction
    /**
     * Statt angezeigtem Kunden anonymen Kunden in Kundenansicht verwenden
     * 
     * @param event
     */
    @FXML
    void handleBtnClear(ActionEvent event) {		// anonymen Kunden verwenden, in Kundenansicht bleiben - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO entfernen
		apCustomerViewPaneController.setData();

    }

    // Handler for Button[fx:id="btnClose"] onAction
    /**
     * Kundensuche schlieszen ohne Auswahl, uebergeordnetes Pane (Tab?) von aussen (extern) schlieszen
     * 
     * @param event
     */
    @FXML
    void handleBtnClose(ActionEvent event) {		// Zurueck ohne Auswahl, default Tab schlieszen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// und tschuess...
		closeCustomerPane();
    }

    // Handler for Button[fx:id="btnDelete"] onAction
    /**
     * Sicherheitsabfrage aufrufen, danach weiter in Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnDelete(ActionEvent event) {		// DTO loeschen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO loeschen (Sicherheitsabfrage!)
		LOG.debug("Customer loeschen: " + apCustomerSearchFoundPaneController.getData());
		try {
			customerService.deleteById(apCustomerSearchFoundPaneController.getData().getId());
			showMessage(BundleManager.getBundle().getString("customerpage.deleted"));
			
			// passende Search-List aktualisieren
			try {
				// nach akuellen Daten aus dem VIEW-Panel filtern
				apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
			} catch (ServiceException e) {
				apCustomerSearchListController.setList();
			}
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

			// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		} catch (ValidationException e1) {
			showMessage(e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage(e1.getLocalizedMessage());
		}
		
    }

    // Handler for Button[fx:id="btnDetails"] onAction
    /**
     * Kundendetails anzeigen
     * 
     * @param event
     */
    @FXML
    void handleBtnDetails(ActionEvent event) {		// Kundendetails anzeigen
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
		apCustomerViewPaneController.setData(apCustomerSearchFoundPaneController.getData());

		// zur Datenaenderung wechseln
		setShowSetup();
    }

    // Handler for Button[fx:id="btnDiscard"] onAction
    /**
     * Neuanlage abbrechen, zurueck zur Suchauswahl
     * 
     * @param event
     */
    @FXML
    void handleBtnDiscard(ActionEvent event) {		// Neuanlage abbrechen und zurueck, default zur Suchauswahl - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// zur Suchauswahl wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnNew"] onAction
    /**
     * Suchauswahl schlieszen, Neuanlage oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnNew(ActionEvent event) {			// zur Neuanlage gehen
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
		apCustomerCreatePaneController.setData();

		// zur Neuerstellung wechseln
		setCreateSetup();
    }

    // Handler for Button[fx:id="btnOverwrite"] onAction
    /**
     * bestehenden Kunden mit neu eigegebenen Daten (Neuanlage) ueberschreiben, Kundendaten uebernehmen und zurueck zur Kundenansich bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnOverwrite(ActionEvent event) {	// bestehenden Kunden mit neuen Daten ueberschreiben und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO ueberschreiben, ID uebernehmen
		LOG.debug("Customer: " + apCustomerDoubleFoundPaneController);
		LOG.debug("überschreiben mit: " + apCustomerDoubleViewPaneController);
		apCustomerDoubleViewPaneController.getData().setId(apCustomerDoubleFoundPaneController.getData().getId());
		try {
			customerService.update(apCustomerDoubleViewPaneController.getData());
			showMessage(BundleManager.getBundle().getString("customerpage.changed"));
			
			// richtiges Pane anzeigen
			if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
				// Details-Pane anzeigen

				// DTO uebernehmen
				apCustomerViewPaneController.setData(apCustomerDoubleViewPaneController.getData());
				
				// zur Detailanzeige wechseln
				setShowSetup();
			}
			if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
				// Search-Pane anzeigen

				// DTO uebernehmen
				apCustomerSearchPaneController.setData(apCustomerDoubleViewPaneController.getData());
				
				// passende Search-List setzen
				try {
					// nach akuellen Daten aus dem VIEW-Panel filtern
					apCustomerSearchListController.setList(customerService.find(apCustomerDoubleViewPaneController.getData()));
				} catch (ServiceException e) {
					apCustomerSearchListController.setList();
				}
				apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

				// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
				
				// zur Suchauswahl wechseln
				setSearchSetup();
			}
		} catch (ValidationException e1) {
			showMessage(e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage(e1.getLocalizedMessage());
		}
    }

    // Handler for Button[fx:id="btnResetChange"] onAction
    /**
     * geaenderte Kundendaten auf urspruengliche, gespeicherte zuruecksetzen
     * 
     * @param event
     */
    @FXML
    void handleBtnResetChange(ActionEvent event) {	// geaenderte Daten zuruecksetzen
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO zuruecksetzen
		apCustomerEditPaneController.setData(editDto);			// TODO DTO zwischenspeichern!!!

    }

    // Handler for Button[fx:id="btnResetCreate"] onAction
    /**
     * neu eingegebene Kundenaten zuruecksetzen (entleeren)
     * 
     * @param event
     */
    @FXML
    void handleBtnResetCreate(ActionEvent event) {	// Eingabefelder leeren
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO zuruecksetzen
		apCustomerCreatePaneController.setData();
		
    }

    // Handler for Button[fx:id="btnResetSearch"] onAction
    /**
     * eingegebene Suchkriterien zuruecksetzen (entleeren)
     * 
     * @param event
     */
    @FXML
    void handleBtnResetSearch(ActionEvent event) {	// Suchkriterien zuruecksetzen
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO zuruecksetzen
//		apCustomerSearchPaneController.setData(searchDto);
		apCustomerSearchPaneController.setData();
		
		// passende Search-List setzen
		try {
			// nach akuellen Daten aus dem VIEW-Panel filtern
			apCustomerSearchListController.setList(customerService.find(apCustomerDoubleViewPaneController.getData()));
		} catch (ServiceException e) {
			apCustomerSearchListController.setList();
		}
		apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
		
		// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		
    }

    // Handler for Button[fx:id="btnSaveDuplicates"] onAction
    /**
     * neue Kundendaten zusaetzlich als Duplikat abspeichern und zurueck zur Kundenansicht bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnSaveDuplicates(ActionEvent event) {	// neue Daten zusaetzlich speichern und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO anlegen, ID holen
		LOG.debug("Customer anlegen: " + apCustomerDoubleViewPaneController);
		try {
			apCustomerDoubleViewPaneController.getData().setId(customerService.create(apCustomerDoubleViewPaneController.getData()));
			showMessage(BundleManager.getBundle().getString("customerpage.created"));

			// richtiges Pane anzeigen
			if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
				// Details-Pane anzeigen

				// DTO uebernehmen
				apCustomerViewPaneController.setData(apCustomerDoubleViewPaneController.getData());
				
				// zur Detailanzeige wechseln
				setShowSetup();
			}
			if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
				// Search-Pane anzeigen

				// DTO uebernehmen
				apCustomerSearchPaneController.setData(apCustomerDoubleViewPaneController.getData());
				
				// passende Search-List setzen
				try {
					// nach akuellen Daten aus dem VIEW-Panel filtern
					apCustomerSearchListController.setList(customerService.find(apCustomerDoubleViewPaneController.getData()));
				} catch (ServiceException e) {
					apCustomerSearchListController.setList();
				}
				apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

				// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
				
				// zur Suchauswahl wechseln
				setSearchSetup();
			}
		} catch (ValidationException e1) {
			showMessage(e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage(e1.getLocalizedMessage());
		}
    }

    // Handler for Button[fx:id="btnSaveChange"] onAction
    /**
     * geaenderte Kundendaten abspeichern und zurueck zur Kundenansicht bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnSaveChange(ActionEvent event) {	// geaenderte Daten speichern und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// geaendertes DTO speichern
		LOG.debug("Customer aktualisieren: " + apCustomerEditPaneController);
		try {
			customerService.update(apCustomerEditPaneController.getData());
			showMessage(BundleManager.getBundle().getString("customerpage.changed"));

			// richtiges Pane anzeigen
			if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
				// Details-Pane anzeigen

				// DTO uebernehmen
				apCustomerViewPaneController.setData(apCustomerEditPaneController.getData());
				
				// zur Detailanzeige wechseln
				setShowSetup();
			}
			if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
				// Search-Pane anzeigen

				// DTO uebernehmen
				apCustomerSearchPaneController.setData(apCustomerEditPaneController.getData());
				
				// passende Search-List setzen
				try {
					// nach akuellen Daten aus dem VIEW-Panel filtern
					apCustomerSearchListController.setList(customerService.find(apCustomerEditPaneController.getData()));
				} catch (ServiceException e) {
					apCustomerSearchListController.setList();
				}
				apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

				// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
				
				// zur Suchauswahl wechseln
				setSearchSetup();
			}
		} catch (ValidationException e1) {
			showMessage(e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage(e1.getLocalizedMessage());
		}
    }

    // Handler for Button[fx:id="btnSaveCreate"] onAction
    /**
     * neue Kundendaten auf Duplikate pruefen und entweder zur Duplikatsauswahl oder abspeichern und zurueck zur Kundenansicht bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnSaveCreate(ActionEvent event) {	// neue Daten auf Duplicate pruefen, ev. zur Duplikatspruefung, sonst zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// TODO check duplicates
		List<CustomerDto> customerList = null;
		try {
			customerList = customerService.find(apCustomerCreatePaneController.getData());
		} catch (ServiceException e1) {
			customerList = new ArrayList<CustomerDto>();
		}
		
		if (customerList.size() != 0) {
			// Duplicates-Pane anzeigen

			// DTO uebernehmen
			apCustomerDoubleViewPaneController.setData(apCustomerCreatePaneController.getData());
			
			// passende Search-List setzen
			apCustomerDoubleListController.setList(customerList);
			apCustomerDoubleListController.getTvCustomersListView().getSelectionModel().selectFirst();

			// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
			
			// zur Duplicatsanzeige wechseln
			setDuplicatesSetup();					// zur Duplikatsanzeige
		} else {
			
			// TODO Neuanlage abspeichern, ID holen
			LOG.debug("Customer anlegen: " + apCustomerCreatePaneController);
			try {
				apCustomerCreatePaneController.getData().setId(customerService.create(apCustomerCreatePaneController.getData()));
				showMessage(BundleManager.getBundle().getString("customerpage.created"));

				// richtiges Pane anzeigen
				if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
					// Details-Pane anzeigen

					// DTO uebernehmen
					apCustomerViewPaneController.setData(apCustomerCreatePaneController.getData());
					
					// zur Detailanzeige wechseln
					setShowSetup();
				}
				if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
					// Search-Pane anzeigen

					// DTO uebernehmen
					apCustomerSearchPaneController.setData(apCustomerCreatePaneController.getData());
					
					// passende Search-List setzen
					try {
						// nach akuellen Daten aus dem VIEW-Panel filtern
						apCustomerSearchListController.setList(customerService.find(apCustomerCreatePaneController.getData()));
					} catch (ServiceException e) {
						apCustomerSearchListController.setList();
					}
					apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

					// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
					
					// zur Suchauswahl wechseln
					setSearchSetup();
				}
			} catch (ValidationException e1) {
				showMessage(e1.toString());
				showMessage(e1.getFieldErrors());
			} catch (ServiceException e1) {
				showMessage(e1.getLocalizedMessage());
			}
		}
    }

    // Handler for Button[fx:id="btnSearch"] onAction
    /**
     * nach eingegebenen Suchkriterien suchen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearch(ActionEvent event) {		// neue Suche ausloesen
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// passende Search-List setzen
		try {
			// nach akuellen Daten aus dem VIEW-Panel filtern
			apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
		} catch (ServiceException e) {
			apCustomerSearchListController.setList();
		}
		apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

		// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		
    }
    
    // -----------------------------------------------------
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
		assert customerService != null : "fx:id=\"customerService\" was not injected: check your Interface-file 'customerService.java'.";
        assert resources != null : "fx:id=\"resources\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert location != null : "fx:id=\"location\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert apCustomerCreatePane != null : "fx:id=\"apCustomerCreatePane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerCreatePaneController != null : "fx:id=\"apCustomerCreatePaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleFoundPane != null : "fx:id=\"apCustomerDoubleFoundPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleFoundPaneController != null : "fx:id=\"apCustomerDoubleFoundPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleList != null : "fx:id=\"apCustomerDoubleList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleListController != null : "fx:id=\"apCustomerDoubleListController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleViewPane != null : "fx:id=\"apCustomerDoubleViewPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleViewPaneController != null : "fx:id=\"apCustomerDoubleViewPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerEditPane != null : "fx:id=\"apCustomerEditPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerEditPaneController != null : "fx:id=\"apCustomerEditPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerMainForm != null : "fx:id=\"apCustomerMainForm\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchFoundPane != null : "fx:id=\"apCustomerSearchFoundPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchFoundPaneController != null : "fx:id=\"apCustomerSearchFoundPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchList != null : "fx:id=\"apCustomerSearchList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchListController != null : "fx:id=\"apCustomerSearchListController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchPane != null : "fx:id=\"apCustomerSearchPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchPaneController != null : "fx:id=\"apCustomerSearchPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerViewPane != null : "fx:id=\"apCustomerViewPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerViewPaneController != null : "fx:id=\"apCustomerViewPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnApplyDuplicates != null : "fx:id=\"btnApplyDuplicates\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnApplySearch != null : "fx:id=\"btnApplySearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnBackDuplicates != null : "fx:id=\"btnBackDuplicates\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnBackSearch != null : "fx:id=\"btnBackSearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnBackShow != null : "fx:id=\"btnBackShow\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnCancelChange != null : "fx:id=\"btnCancelChange\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnCancelCreate != null : "fx:id=\"btnCancelCreate\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnChangeShow != null : "fx:id=\"btnChangeShow\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnChangeSearch != null : "fx:id=\"btnChangeSearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDetails != null : "fx:id=\"btnDetails\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDiscard != null : "fx:id=\"btnDiscard\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnOverwrite != null : "fx:id=\"btnOverwrite\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnResetChange != null : "fx:id=\"btnResetChange\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnResetCreate != null : "fx:id=\"btnResetCreate\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnResetSearch != null : "fx:id=\"btnResetSearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSaveChange != null : "fx:id=\"btnSaveChange\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSaveCreate != null : "fx:id=\"btnSaveCreate\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSaveDuplicates != null : "fx:id=\"btnSaveDuplicates\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert lbTopTitle != null : "fx:id=\"lbTopTitle\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert lvMessageList != null : "fx:id=\"lvMessageList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert spLeftPanes != null : "fx:id=\"spLeftPanes\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert spListPanes != null : "fx:id=\"spListPanes\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert spRightPanes != null : "fx:id=\"spRightPanes\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert tbVisibleBar != null : "fx:id=\"tbVisibleBar\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        // ---------------------------------------

//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				LOG.info("delayed initialization");
//        
//				// TODO restliche Initialisierung
//	
//		    }
//		});

		// restliche Initialisierung

        // Listen for selection changes in SearchList
        apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDto>() {
            public void changed(ObservableValue<? extends CustomerDto> observable, CustomerDto oldValue, CustomerDto newValue) {
                LOG.info("Aenderungen Search-Liste");
                
                // Daten aktualisieren
                apCustomerSearchFoundPaneController.setData(newValue);
            }
        });
        
        // Listen for selection changes in SearchList
        apCustomerDoubleListController.getTvCustomersListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDto>() {
            public void changed(ObservableValue<? extends CustomerDto> observable, CustomerDto oldValue, CustomerDto newValue) {
                LOG.info("Aenderungen Duplicates-Liste");

                // Daten aktualisieren
                apCustomerDoubleFoundPaneController.setData(newValue);
}
        });
        
        // einzelne Panes initialisieren
        apCustomerCreatePaneController.setMode(CustomerBaseFormController.PaneMode.CREATE);
        apCustomerCreatePaneController.setTitle("%customerpage.new_customer_data");
        apCustomerDoubleFoundPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDoubleFoundPaneController.setTitle("%customerpage.selected_duplicate");
        apCustomerDoubleFoundPaneController.getCbSex().setVisible(false);
        apCustomerDoubleFoundPaneController.getTxtSex().setVisible(true);
//      apCustomerDoubleListController
        apCustomerDoubleListController.setTitle("%customerpage.found_duplicates");
        apCustomerDoubleViewPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDoubleViewPaneController.setTitle("%customerpage.new_customer_data");
        apCustomerDoubleViewPaneController.getCbSex().setVisible(false);
        apCustomerDoubleViewPaneController.getTxtSex().setVisible(true);
        apCustomerEditPaneController.setMode(CustomerBaseFormController.PaneMode.EDIT);
        apCustomerEditPaneController.setTitle("%customerpage.customer_data");
        apCustomerSearchFoundPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerSearchFoundPaneController.setTitle("%customerpage.search_select");
        apCustomerSearchFoundPaneController.getCbSex().setVisible(false);
        apCustomerSearchFoundPaneController.getTxtSex().setVisible(true);
//      apCustomerSearchListController
        apCustomerSearchListController.setTitle("%customerpage.search_results");
        apCustomerSearchPaneController.setMode(CustomerBaseFormController.PaneMode.SEARCH);
        apCustomerSearchPaneController.setTitle("%customerpage.search_criteria");
        apCustomerSearchPaneController.getCbSex().getItems().add("");
        apCustomerViewPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerViewPaneController.setTitle("%customerpage.customer_data");
        apCustomerViewPaneController.getCbSex().setVisible(false);
        apCustomerViewPaneController.getTxtSex().setVisible(true);
        
        // gesamtes Form-Setup initialisieren, Default = Display aktuellen Customer
        hideMessage();
        setMode(PaneMode.VIEW);
		
        // Panes mit leeren Daten initialisieren
	    // fx:id="apCustomerCreatePane"
		apCustomerCreatePaneController.setData();
	    // fx:id="apCustomerDoubleViewPane"
		apCustomerDoubleViewPaneController.setData();
	    // fx:id="apCustomerEditPane"
		apCustomerEditPaneController.setData();
	    // fx:id="apCustomerSearchPane"
		apCustomerSearchPaneController.setData();
	    // fx:id="apCustomerViewPane"
		apCustomerViewPaneController.setData();

		// fx:id="apCustomerDoubleList"
		// TODO Liste initialisieren
	    // fx:id="apCustomerSearchList"
		// TODO Liste initialisieren

		// fx:id="apCustomerDoubleFoundPane"
		apCustomerDoubleFoundPaneController.setData();
	    // fx:id="apCustomerSearchFoundPane"
		apCustomerSearchFoundPaneController.setData();
        
	}
	
	// -----------------------------------------------------------------------

	/**
	 * View-Setup fuer alle anderen Setups initialisieren
	 */
	private void setInitSetup() {
		LOG.info("");

		// standardmaessige Init-View 
		if (this.paneMode == null) {
			this.paneMode = PaneMode.VIEW;
		} 

		// Seitentitel setzen
		setTitle("%customerpage.customer_data");
		
		// empty Pane-Setup initialisieren
		spLeftPanes.getChildren().clear();
		spRightPanes.getChildren().clear();
		spListPanes.getChildren().clear();
		
		// Buttons aus sichtbarer Toolbar entfernen
		tbVisibleBar.getItems().clear();

	}
	
	/**
	 * View-Setup um neuen Kunden anzulegen,
	 * innerhalb eines anderen Tabs als Untermaske aufgerufen
	 */
	private void setCreateSetup() {
		LOG.info("");
		
		// View initialisieren
		setInitSetup();
		
		// Seitentitel setzen
		setTitle("%customerpage.new_customer_data");
		
		// Create-New Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerCreatePane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		tbVisibleBar.getItems().add(btnCancelCreate);		// Abbruch ohne speichern
		tbVisibleBar.getItems().add(btnResetCreate);		// Eingaben zuruecksetzen
		tbVisibleBar.getItems().add(btnSaveCreate);			// Neuanlage speichern

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Focus auf 1. Feld setzen
				apCustomerCreatePaneController.getTxtTitle().requestFocus();		
		    }
		});
	}

	/**
	 * View-Setup um Duplikate bei einer Neuanlage Duplikate anzuzeigen,
	 * innerhalb des selben Tabs statt der Neuanlage anzeigen
	 */
	private void setDuplicatesSetup() {
		LOG.info("");

		// View initialisieren
		setInitSetup();
		
		// Seitentitel setzen
		setTitle("%customerpage.duplicates_title");
		
		// Create-Duplicates Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerDoubleViewPane);
		spRightPanes.getChildren().add(apCustomerDoubleFoundPane);
		spListPanes.getChildren().add(apCustomerDoubleList);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		tbVisibleBar.getItems().add(btnDiscard);			// Anlegen verwerfen (und ev. Tab schlieszen)
		tbVisibleBar.getItems().add(btnBackDuplicates);		// Zurueck zum Aendern der neuen daten
		tbVisibleBar.getItems().add(btnOverwrite);			// Auswahl durch neue Daten ersetzen
		tbVisibleBar.getItems().add(btnSaveDuplicates);		// Neuanlage zusaetzlich als eigenen Kunden speichern
		tbVisibleBar.getItems().add(btnApplyDuplicates);		// Auswahl uebernehmen und zrueck

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {

				// Focus auf Liste setzen
				apCustomerDoubleListController.getTvCustomersListView().requestFocus();
					
				// TODO Focus auf 1. Zeile setzen
		    }
		});
	}

	/**
	 * View-Setup um Kundendaten zu aendern,
	 * innerhalb eines anderen Tabs anzeigen
	 */
	private void setChangeSetup() {
		LOG.info("");

		// View initialisieren
		setInitSetup();
		
		// Seitentitel setzen
		setTitle("%customerpage.customer_data");
		
		// Change Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerEditPane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (null != tbVisibleBar) {
			tbVisibleBar.getItems().add(btnCancelChange);	// Abbruch ohne speichern
			tbVisibleBar.getItems().add(btnResetChange);	// Eingaben zuruecksetzen
			tbVisibleBar.getItems().add(btnSaveChange);		// Aenderungen speichern
		}

		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		Platform.runLater(new Runnable() {
		    public void run() {
				// Focus auf 1. Feld setzen
				apCustomerEditPaneController.getTxtTitle().requestFocus();							
		    }
		});

		// customerDto fuer Reset zwischenspeichern
		editDto = apCustomerEditPaneController.getData();

	}

	/**
	 * View-Setup um Kundendaten anzuzeigen,
	 * innerhalb eines anderen Tabs anzeigen
	 */
	private void setShowSetup() {
		LOG.info("");

		// View initialisieren
		setInitSetup();
		
		// Seitentitel setzen
		setTitle("%customerpage.customer_data");
		
		// Show Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerViewPane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnBackShow);			// zurueck zur Suchauswahl
		}
		if (this.paneMode == PaneMode.VIEW) {
			tbVisibleBar.getItems().add(btnChangeShow);			// Kunden aendern
			tbVisibleBar.getItems().add(btnClear);				// Kunden aendern
		}

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Focus auf Toolbar setzen
				tbVisibleBar.requestFocus();
		    }
		});
	}

	/**
	 * View-Setup fuer die Kundenverwaltung von der Hauptseite aus
	 * als eigenes Tab angezeigt, DTO nicht zurueckgeben
	 */
	private void setManagementSetup() {
		LOG.info("");

		// gemeinsamer Panel-Aufbau
		setSearchSetup();
		
	}

	/**
	 * View-Setup fuer die Kundenverwaltung 
	 * von einer Unter-Seite aus, DTO zurueckgeben
	 */
	private void setSelectionSetup() {
		LOG.info("");

		// gemeinsamer Panel-Aufbau
		setSearchSetup();
		
	}

	/**
	 * View-Setup um Kundendaten zu suchen,
	 */
	private void setSearchSetup() {
		LOG.info("");

		// View initialisieren
		setInitSetup();

		// Seitentitel setzen
		if (this.paneMode == PaneMode.MANAGE) {
			setTitle("%customerpage");
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			setTitle("%customerpage.search_title");
		}

		// Search Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerSearchPane);
		spRightPanes.getChildren().add(apCustomerSearchFoundPane);
		spListPanes.getChildren().add(apCustomerSearchList);

		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnClose);		// Schlieszen ohne Auswahl
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnBackSearch);	// Cancel ohne Auswahl
		}
		tbVisibleBar.getItems().add(btnSearch);			// nochmals suchen
		tbVisibleBar.getItems().add(btnResetSearch);	// Suchkriterien loeschen
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnDetails);	// Details der Suchauswahl
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnApplySearch);		// Auswahl uebernehmen und zrueck
		}
		tbVisibleBar.getItems().add(btnDelete);			// ausgewaehlten Kunden loeschen
		tbVisibleBar.getItems().add(btnNew);			// Neuen Kunden anlegen
		tbVisibleBar.getItems().add(btnChangeSearch);	// ausgewaehlten Kunden aendern
		
		// ev. geht manches nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {

				// Focus auf 1. Feld setzen
				apCustomerSearchPaneController.getTxtTitle().requestFocus();							
		    }
		});
		
		// Suchkriterium fuer Reset zwischenspeichern
		searchDto = apCustomerSearchPaneController.getData();
		
	}
	
	// ------------------------------------
	
	/**
	 * Soll die Kundenverwaltung schliessen
	 * 
	 * Ev. von Aufrufender Methode ueberschreiben,
	 * da hier zB. kein uebergeordnetes Tab geschlossen werden kaan
	 */
	public void closeCustomerPane() {
		LOG.info("");
		
		// pro-forma alle Panes entfernen
		apCustomerMainForm.getChildren().clear();
	}
	
	/**
	 * Sichtbarkeiten, etc. des Panels Modus-abhaengig anpassen
	 * 
	 * @param mode
	 */
	public void setMode(PaneMode mode) {
		LOG.info("");
		
		// Save Mode
		this.paneMode = mode;

		if (mode == PaneMode.CREATE) {
			setCreateSetup();
		} else if (mode == PaneMode.EDIT) {
			setChangeSetup();
		} else if (mode == PaneMode.MANAGE) {
			setManagementSetup();
		} else if (mode == PaneMode.SELECT) {
			setSelectionSetup();
		} else if (mode == PaneMode.VIEW) {
			setShowSetup();
		} 
	}

	/**
	 * Titel der TitlePane sprachabhaengig setzen
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		LOG.info("");
		
		if (title == null) {
			lbTopTitle.setText("");
		} else if (title.charAt(0) == '%') {
			lbTopTitle.setText(BundleManager.getBundle().getString(title.substring(1)).trim());
		} else {
			lbTopTitle.setText(title.trim());
		}
	}

	public void setCustomer(CustomerDto customerDto) {
		setCustomer(customerDto, paneMode);
	}
	
	public void setCustomer(CustomerDto customerDto, PaneMode mode) {
		
		if (mode == PaneMode.CREATE) {
		    // fx:id="apCustomerCreatePane"
			apCustomerCreatePaneController.setData(customerDto);
		} else if (mode == PaneMode.EDIT) {
		    // fx:id="apCustomerEditPane"
			apCustomerEditPaneController.setData(customerDto);
		} else if (mode == PaneMode.MANAGE) {
		    // fx:id="apCustomerSearchPane"
			apCustomerSearchPaneController.setData(customerDto);
		} else if (mode == PaneMode.SELECT) {
		    // fx:id="apCustomerSearchPane"
			apCustomerSearchPaneController.setData(customerDto);
		} else if (mode == PaneMode.VIEW) {
		    // fx:id="apCustomerViewPane"
			apCustomerViewPaneController.setData(customerDto);
		} 
	}

	private void showMessage(List<FieldError> list) {
		for (FieldError errMsg : list) {
			showMessage(errMsg.getField() + ": " + errMsg.getMessage());
		}
	}
	
	private void showMessage(String msg) {
		lvMessageList.setVisible(true);
		lvMessageList.setPrefHeight(100);
		lvMessageList.getItems().add(msg);
	}
	
	private void hideMessage() {
		// Message-List entleeren und verbergen
		lvMessageList.setVisible(false);
		lvMessageList.getItems().clear();
		lvMessageList.setPrefHeight(0);
	}
}
