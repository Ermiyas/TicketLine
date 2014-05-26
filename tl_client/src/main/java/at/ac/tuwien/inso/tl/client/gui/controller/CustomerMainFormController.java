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
	
	public static enum PaneMode {CREATE, MANAGE, SELECT, VIEW, EDIT};				// alle moeglichne Init-Modes der Panes
	
	private PaneMode paneMode;							// eingestellter Display-Mode der Panes
	
	private CustomerDto editDto = new CustomerDto();
	private CustomerDto searchDto = new CustomerDto();
	
	// FXML-injizierte Variablen

	@Autowired private CustomerService customerService;
    @FXML private ResourceBundle resources; 			// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;							// URL location of the FXML file that was given to the FXMLLoader
    
    @FXML private AnchorPane 					apCustomerCreatePane;				// fx:id="apCustomerCreatePane"
    @FXML private CustomerBaseFormController 	apCustomerCreatePaneController;
    @FXML private AnchorPane 					apCustomerDoubleList;				// fx:id="apCustomerDoubleList"
    @FXML private CustomerListFormController 	apCustomerDoubleListController;
    @FXML private AnchorPane 					apCustomerDoubleViewPane;			// fx:id="apCustomerDoubleViewPane"
    @FXML private CustomerBaseFormController 	apCustomerDoubleViewPaneController;
    @FXML private AnchorPane 					apCustomerDoubleFoundPane;			// fx:id="apCustomerDoubleFoundPane"
    @FXML private CustomerBaseFormController 	apCustomerDoubleFoundPaneController;
    @FXML private AnchorPane 					apCustomerEditPane;					// fx:id="apCustomerEditPane"
    @FXML private CustomerBaseFormController 	apCustomerEditPaneController;
    @FXML private AnchorPane 					apCustomerSearchFoundPane;			// fx:id="apCustomerSearchFoundPane"
    @FXML private CustomerBaseFormController 	apCustomerSearchFoundPaneController;
    @FXML private AnchorPane 					apCustomerSearchList;				// fx:id="apCustomerSearchList"
    @FXML private CustomerListFormController 	apCustomerSearchListController;
    @FXML private AnchorPane 					apCustomerSearchViewPane;			// fx:id="apCustomerSearchViewPane"
    @FXML private CustomerBaseFormController 	apCustomerSearchViewPaneController;
    @FXML private AnchorPane 					apCustomerViewPane;					// fx:id="apCustomerViewPane"
    @FXML private CustomerBaseFormController 	apCustomerViewPaneController;

    @FXML private AnchorPane apCustomerMainForm;	// eigenes Root-Pane			// fx:id="apCustomerMainForm"

    // Buttons
    
    @FXML private Button btnCreateCancel;			// fx:id="btnCreateCancel"
    @FXML private Button btnCreateReset;			// fx:id="btnCreateReset"
    @FXML private Button btnCreateSave;             // fx:id="btnCreateSave"
    @FXML private Button btnDuplicatesApply;		// fx:id="btnDuplicatesApply"
    @FXML private Button btnDuplicatesBack;			// fx:id="btnDuplicatesBack"
    @FXML private Button btnDuplicatesDiscard;		// fx:id="btnDuplicatesDiscard"
    @FXML private Button btnDuplicatesOverwrite;	// fx:id="btnDuplicatesOverwrite"
    @FXML private Button btnDuplicatesSave;         // fx:id="btnDuplicatesSave"
    @FXML private Button btnEditCancel;				// fx:id="btnEditCancel"
    @FXML private Button btnEditReset;				// fx:id="btnEditReset"
    @FXML private Button btnEditSave;				// fx:id="btnEditSave"
    @FXML private Button btnSearchApply;			// fx:id="btnSearchApply"
    @FXML private Button btnSearchBack;				// fx:id="btnSearchBack"
    @FXML private Button btnSearchClose;			// fx:id="btnSearchClose"
    @FXML private Button btnSearchCreate;			// fx:id="btnSearchCreate"
    @FXML private Button btnSearchDelete;			// fx:id="btnSearchDelete"
    @FXML private Button btnSearchEdit;				// fx:id="btnSearchEdit"
    @FXML private Button btnSearchReset;            // fx:id="btnSearchReset"
    @FXML private Button btnSearchSearch;           // fx:id="btnSearchSearch"
    @FXML private Button btnSearchView;				// fx:id="btnSearchView"
    @FXML private Button btnShowBack;				// fx:id="btnShowBack"
    @FXML private Button btnViewCancel;				// fx:id="btnViewCancel"
    @FXML private Button btnViewClear;				// fx:id="btnViewClear"  
    
    // sonstige FXML-Elemente
    
    @FXML private Label lbTopTitle;                 // fx:id="lbTopTitle"			// Ueberschrift
    
    @FXML private ListView<String> lvMessageList;   // fx:id="lvMessageList"		// Message-Box
    
    @FXML private StackPane spLeftPanes;            // fx:id="spLeftPanes"			// links Details
    @FXML private StackPane spListPanes;            // fx:id="spListPanes"			// unten Liste
    @FXML private StackPane spRightPanes;           // fx:id="spRightPanes"			// rechts Gefunden
    
    @FXML private ToolBar tbVisibleBar;             // fx:id="tbVisibleBar"			// Button-Leiste

    // ------------ div. Button-Getter, um externe Handler drauf setzen zu koennen

	public Button getBtnDuplicatesApply() {
		return btnDuplicatesApply;
	}
	public Button getBtnSearchApply() {
		return btnSearchApply;
	}
	public Button getBtnDuplicatesBack() {
		return btnDuplicatesBack;
	}
	public Button getBtnSearchBack() {
		return btnSearchBack;
	}
	public Button getBtnShowBack() {
		return btnShowBack;
	}
	public Button getBtnEditCancel() {
		return btnEditCancel;
	}
	public Button getBtnCreateCancel() {
		return btnCreateCancel;
	}
	public Button getBtnViewCancel() {
		return btnViewCancel;
	}
	public Button getBtnSearchEdit() {
		return btnSearchEdit;
	}
	public Button getBtnSearchClose() {
		return btnSearchClose;
	}
	public Button getBtnViewClear() {
		return btnViewClear;
	}
	public Button getBtnSearchDelete() {
		return btnSearchDelete;
	}
	public Button getBtnSearchView() {
		return btnSearchView;
	}
	public Button getBtnDuplicatesDiscard() {
		return btnDuplicatesDiscard;
	}
	public Button getBtnSearchCreate() {
		return btnSearchCreate;
	}
	public Button getBtnDuplicatesOverwrite() {
		return btnDuplicatesOverwrite;
	}
	public Button getBtnEditReset() {
		return btnEditReset;
	}
	public Button getBtnCreateReset() {
		return btnCreateReset;
	}
	public Button getBtnSearchReset() {
		return btnSearchReset;
	}
	public Button getBtnEditSave() {
		return btnEditSave;
	}
	public Button getBtnCreateSave() {
		return btnCreateSave;
	}
	public Button getBtnDuplicatesSave() {
		return btnDuplicatesSave;
	}
	public Button getBtnSearchSearch() {
		return btnSearchSearch;
	}

    // --------- Initialisierung
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
		assert customerService != null : 						"fx:id=\"customerService\" was not injected: check your Interface-file 'customerService.java'.";
        assert resources != null : 								"fx:id=\"resources\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert location != null : 								"fx:id=\"location\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert btnCreateCancel != null : 						"fx:id=\"btnCreateCancel\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnCreateReset != null : 						"fx:id=\"btnCreateReset\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnCreateSave != null : 							"fx:id=\"btnCreateSave\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDuplicatesApply != null : 					"fx:id=\"btnDuplicatesApply\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDuplicatesBack != null : 						"fx:id=\"btnDuplicatesBack\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDuplicatesDiscard != null : 					"fx:id=\"btnDuplicatesDiscard\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDuplicatesOverwrite != null : 				"fx:id=\"btnDuplicatesOverwrite\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDuplicatesSave != null : 						"fx:id=\"btnDuplicatesSave\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnEditCancel != null : 							"fx:id=\"btnEditCancel\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnEditReset != null : 							"fx:id=\"btnEditReset\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnEditSave != null : 							"fx:id=\"btnEditSave\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchApply != null : 						"fx:id=\"btnSearchApply\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchBack != null : 							"fx:id=\"btnSearchBack\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchClose != null : 						"fx:id=\"btnSearchClose\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchCreate != null : 						"fx:id=\"btnSearchCreate\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchDelete != null : 						"fx:id=\"btnSearchDelete\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchEdit != null : 							"fx:id=\"btnSearchEdit\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchReset != null : 						"fx:id=\"btnSearchReset\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchSearch != null : 						"fx:id=\"btnSearchSearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnSearchView != null : 							"fx:id=\"btnSearchView\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnShowBack != null : 							"fx:id=\"btnShowBack\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnViewCancel != null : 							"fx:id=\"btnViewCancel\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnViewClear != null : 							"fx:id=\"btnViewClear\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerCreatePane != null : 					"fx:id=\"apCustomerCreatePane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerCreatePaneController != null : 		"fx:id=\"apCustomerCreatePaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleFoundPane != null : 				"fx:id=\"apCustomerDoubleFoundPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleFoundPaneController != null : 	"fx:id=\"apCustomerDoubleFoundPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleList != null : 					"fx:id=\"apCustomerDoubleList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleListController != null : 		"fx:id=\"apCustomerDoubleListController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleViewPane != null : 				"fx:id=\"apCustomerDoubleViewPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDoubleViewPaneController != null : 	"fx:id=\"apCustomerDoubleViewPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerEditPane != null : 					"fx:id=\"apCustomerEditPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerEditPaneController != null : 			"fx:id=\"apCustomerEditPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchFoundPane != null : 				"fx:id=\"apCustomerSearchFoundPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchFoundPaneController != null : 	"fx:id=\"apCustomerSearchFoundPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchList != null : 					"fx:id=\"apCustomerSearchList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchListController != null : 		"fx:id=\"apCustomerSearchListController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchViewPane != null : 				"fx:id=\"apCustomerSearchViewPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchViewPaneController != null : 	"fx:id=\"apCustomerSearchViewPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerViewPane != null : 					"fx:id=\"apCustomerViewPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerViewPaneController != null : 			"fx:id=\"apCustomerViewPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerMainForm != null : 					"fx:id=\"apCustomerMainForm\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert lbTopTitle != null : 							"fx:id=\"lbTopTitle\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert lvMessageList != null : 							"fx:id=\"lvMessageList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert spLeftPanes != null : 							"fx:id=\"spLeftPanes\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert spListPanes != null : 							"fx:id=\"spListPanes\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert spRightPanes != null : 							"fx:id=\"spRightPanes\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert tbVisibleBar != null : 							"fx:id=\"tbVisibleBar\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";

        // all @FXML variables will have been injected

        // --- restliche Initialisierung ---------------------------------------

//		// Im Init geht ev. noch nicht alles, da Parent-Tab vielleicht noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				LOG.info("delayed initialization");
//        
//				// TODO zeitversetzte Initialisierung
//	
//		    }
//		});

		// restliche Initialisierung

        // Listener fuer selection changes in SearchList
        apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDto>() {
            public void changed(ObservableValue<? extends CustomerDto> observable, CustomerDto oldValue, CustomerDto newValue) {
                LOG.info("Aenderungen Search-Liste");
                
                // Daten aktualisieren
                apCustomerSearchFoundPaneController.setData(newValue);
            }
        });
        
        // Listener fuer selection changes in SearchList
        apCustomerDoubleListController.getTvCustomersListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDto>() {
            public void changed(ObservableValue<? extends CustomerDto> observable, CustomerDto oldValue, CustomerDto newValue) {
                LOG.info("Aenderungen Duplicates-Liste");

                // Daten aktualisieren
                apCustomerDoubleFoundPaneController.setData(newValue);
}
        });
        
        // einzelne Panes initialisieren
        apCustomerCreatePaneController.setMode(CustomerBaseFormController.PaneMode.CREATE);
        apCustomerCreatePaneController.setTitle("customerpage.new_customer_data");
        apCustomerDoubleFoundPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDoubleFoundPaneController.setTitle("customerpage.selected_duplicate");
        apCustomerDoubleFoundPaneController.getCbSex().setVisible(false);
        apCustomerDoubleFoundPaneController.getTxtSex().setVisible(true);
//      apCustomerDoubleListController
        apCustomerDoubleListController.setTitle("customerpage.found_duplicates");
        apCustomerDoubleViewPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDoubleViewPaneController.setTitle("customerpage.new_customer_data");
        apCustomerDoubleViewPaneController.getCbSex().setVisible(false);
        apCustomerDoubleViewPaneController.getTxtSex().setVisible(true);
        apCustomerEditPaneController.setMode(CustomerBaseFormController.PaneMode.EDIT);
        apCustomerEditPaneController.setTitle("customerpage.customer_data");
        apCustomerSearchFoundPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerSearchFoundPaneController.setTitle("customerpage.search_select");
        apCustomerSearchFoundPaneController.getCbSex().setVisible(false);
        apCustomerSearchFoundPaneController.getTxtSex().setVisible(true);
//      apCustomerSearchListController
        apCustomerSearchListController.setTitle("customerpage.search_results");
        apCustomerSearchViewPaneController.setMode(CustomerBaseFormController.PaneMode.SEARCH);
        apCustomerSearchViewPaneController.setTitle("customerpage.search_criteria");
        apCustomerSearchViewPaneController.getCbSex().getItems().add("");
        apCustomerViewPaneController.setMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerViewPaneController.setTitle("customerpage.customer_data");
        apCustomerViewPaneController.getCbSex().setVisible(false);
        apCustomerViewPaneController.getTxtSex().setVisible(true);
        
        // gesamtes Form-Setup initialisieren, Default = Display aktuellen Customer
        hideMessage();
        setMode(PaneMode.VIEW);
		
        // Panes sicherheitshalber mit leeren Daten initialisieren
	    // fx:id="apCustomerCreatePane"
		apCustomerCreatePaneController.setData();
	    // fx:id="apCustomerDoubleViewPane"
		apCustomerDoubleViewPaneController.setData();
	    // fx:id="apCustomerEditPane"
		apCustomerEditPaneController.setData();
	    // fx:id="apCustomerSearchViewPane"
		apCustomerSearchViewPaneController.setData();
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

	// ------------ Button-Action-Handler

	// Handler for Button[fx:id="btnDuplicatesApply"] onAction
    /**
     * Duplikatfenster schlieszen, Duplikat statt Neuanlage uebernehmen
     * 
     * @param event
     */
    @FXML
    void handleBtnDuplicatesApply(ActionEvent event) {	// Duplikat statt Neuanlage uebernehmen und zurueck - in aufrufender Funktion ueberschreiben!
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
			apCustomerSearchViewPaneController.setData(apCustomerDoubleFoundPaneController.getData());
			
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

    // Handler for Button[fx:id="btnSearchApply"] onAction
    /**
     * Suchfenster schlieszen, Suchauswahl uebernehmen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchApply(ActionEvent event) {		// Suchauswahl uebernehmen und zurueck, default zur Kundenansicht - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO uebernehmen
		apCustomerViewPaneController.setData(apCustomerSearchFoundPaneController.getData());
		
		// zur Detailanzeige wechseln
		setShowSetup();
    }

    // Handler for Button[fx:id="btnDuplicatesBack"] onAction
    /**
     * Duplikatfenster schlieszen ohne Uebernahlem eines Duplikates, Neuanlage abaendern
     * 
     * @param event
     */
    @FXML
    void handleBtnDuplicatesBack(ActionEvent event) {	// Zurueck von Duplicates zur Neuanlage
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zur Neuanlage wechseln
		setCreateSetup();
    }

    // Handler for Button[fx:id="btnSearchBack"] onAction
    /**
     * Suchfenster schlieszen ohne Uebernahme der Suchauswahl, Kundenanzeige oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchBack(ActionEvent event) {		// Zurueck von Suche ohne Neuauswahl, default zur Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// zur Detailanzeige wechseln
		setShowSetup();
    }

    // Handler for Button[fx:id="btnShowBack"] onAction
    /**
     * Detailfenster schlieszen, Suchauswahl oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnShowBack(ActionEvent event) {		// Zurueck von Suche ohne Neuauswahl, default zur Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// zur Detailanzeige wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnEditCancel"] onAction
    /**
     * Kundenaenderung abbrechen, Fenster schlieszen und zurueck zur Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnEditCancel(ActionEvent event) {		// Zurueck ohne Aenderung, default zur Suchauswahl - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zur Suche wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnCreateCancel"] onAction
    /**
     * Kundenanlage abbrechen, Fenster schlieszen und zurueck zur Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnCreateCancel(ActionEvent event) {		// Zurueck ohne Neuanlage, default zur Suchauswahl - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zur Suche wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnEdit"] onAction
    /**
     * Kundenanzeige schlieszen, Kundensuche oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnViewCancel(ActionEvent event) {		// Kundensuche 
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
		apCustomerSearchViewPaneController.setData(apCustomerViewPaneController.getData());
		
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

    // Handler for Button[fx:id="btnEdit"] onAction
    /**
     * Kundensuche schlieszen, Kundenaenderung oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchEdit(ActionEvent event) {		// Kunden aendern
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO uebernehmen
		apCustomerEditPaneController.setData(apCustomerSearchFoundPaneController.getData());
		
		// zur Datenaenderung wechseln
		setEditSetup();
    }

    // Handler for Button[fx:id="btnCancelClear"] onAction
    /**
     * Statt angezeigtem Kunden anonymen Kunden in Kundenansicht verwenden
     * 
     * @param event
     */
    @FXML
    void handleBtnViewClear(ActionEvent event) {		// anonymen Kunden verwenden, in Kundenansicht bleiben - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO entfernen
		apCustomerViewPaneController.setData();

		// und Focus auf Titel-Feld setzen
		apCustomerViewPaneController.getTxtTitle().requestFocus();		
    }

    // Handler for Button[fx:id="btnSearchClose"] onAction
    /**
     * Kundensuche schlieszen ohne Auswahl, uebergeordnetes Pane (Tab?) von aussen (extern) schlieszen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchClose(ActionEvent event) {		// Zurueck ohne Auswahl, default Tab schlieszen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// und tschuess...
		closeCustomerPane();
    }

    // Handler for Button[fx:id="btnSearchDelete"] onAction
    /**
     * Sicherheitsabfrage aufrufen, danach weiter in Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchDelete(ActionEvent event) {		// DTO loeschen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO loeschen (TODO Sicherheitsabfrage!)
		LOG.debug("Customer loeschen: " + apCustomerSearchFoundPaneController.getData());
		try {
			customerService.deleteById(apCustomerSearchFoundPaneController.getData().getId());
			showMessage(intString("customerpage.deleted"));
			
			// passende Search-List aktualisieren
			try {
				// nach akuellen Daten aus dem VIEW-Panel filtern
				apCustomerSearchListController.setList(customerService.find(apCustomerSearchViewPaneController.getData()));
			} catch (ServiceException e) {
				apCustomerSearchListController.setList();
			}
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

			// das Found-Panel wird automatisch ueber den Event-Handler gesetzt

		} catch (ValidationException e1) {
			showMessage("customerpage.delete." + e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage("customerpage.delete." + e1.getLocalizedMessage());
		}
		
		// und Focus auf 1. Zeile setzen
		apCustomerSearchListController.getTvCustomersListView().requestFocus();		
}

    // Handler for Button[fx:id="btnSearchView"] onAction
    /**
     * Kundendetails anzeigen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchView(ActionEvent event) {		// Kundendetails anzeigen
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
		apCustomerViewPaneController.setData(apCustomerSearchFoundPaneController.getData());

		// zur Datenaenderung wechseln
		setShowSetup();
    }

    // Handler for Button[fx:id="btnDuplicatesDiscard"] onAction
    /**
     * Neuanlage abbrechen, zurueck zur Suchauswahl
     * 
     * @param event
     */
    @FXML
    void handleBtnDuplicatesDiscard(ActionEvent event) {		// Neuanlage abbrechen und zurueck, default zur Suchauswahl - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// zur Suchauswahl wechseln
		setSearchSetup();
    }

    // Handler for Button[fx:id="btnSearchCreate"] onAction
    /**
     * Suchauswahl schlieszen, Neuanlage oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchCreate(ActionEvent event) {			// zur Neuanlage gehen
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
		apCustomerCreatePaneController.setData();

		// zur Neuerstellung wechseln
		setCreateSetup();
    }

    // Handler for Button[fx:id="btnDuplicatesOverwrite"] onAction
    /**
     * bestehenden Kunden mit neu eigegebenen Daten (Neuanlage) ueberschreiben, Kundendaten uebernehmen und zurueck zur Kundenansich bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnDuplicatesOverwrite(ActionEvent event) {	// bestehenden Kunden mit neuen Daten ueberschreiben und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO ueberschreiben, ID uebernehmen
		LOG.debug("Customer: " + apCustomerDoubleFoundPaneController);
		LOG.debug("überschreiben mit: " + apCustomerDoubleViewPaneController);
		apCustomerDoubleViewPaneController.getData().setId(apCustomerDoubleFoundPaneController.getData().getId());
		try {
			customerService.update(apCustomerDoubleViewPaneController.getData());
			showMessage(intString("customerpage.changed"));
			
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
				apCustomerSearchViewPaneController.setData(apCustomerDoubleViewPaneController.getData());
				
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
			showMessage("customerpage.overwrite." + e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage("customerpage.overwrite." + e1.getLocalizedMessage());
		}
    }

    // Handler for Button[fx:id="btnEditReset"] onAction
    /**
     * geaenderte Kundendaten auf urspruengliche, gespeicherte zuruecksetzen
     * 
     * @param event
     */
    @FXML
    void handleBtnEditReset(ActionEvent event) {	// geaenderte Daten zuruecksetzen
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO zuruecksetzen
		apCustomerEditPaneController.setData(editDto);			// TODO DTO zwischenspeichern!!!

		// und Focus auf Titel-Feld setzen
		apCustomerEditPaneController.getTxtTitle().requestFocus();		
    }

    // Handler for Button[fx:id="btnCreateReset"] onAction
    /**
     * neu eingegebene Kundenaten zuruecksetzen (entleeren)
     * 
     * @param event
     */
    @FXML
    void handleBtnCreateReset(ActionEvent event) {	// Eingabefelder leeren
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO zuruecksetzen
		apCustomerCreatePaneController.setData();
		
		// und Focus auf Titel-Feld setzen
		apCustomerCreatePaneController.getTxtTitle().requestFocus();		
    }

    // Handler for Button[fx:id="btnSearchReset"] onAction
    /**
     * eingegebene Suchkriterien zuruecksetzen (entleeren)
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchReset(ActionEvent event) {	// Suchkriterien zuruecksetzen
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO zuruecksetzen
//		apCustomerSearchViewPaneController.setData(searchDto);
		apCustomerSearchViewPaneController.setData();
		
//		// passende Search-List setzen
//		try {
//			// nach akuellen Daten aus dem VIEW-Panel filtern
//			apCustomerSearchListController.setList(customerService.find(apCustomerDoubleViewPaneController.getData()));
//		} catch (ServiceException e) {
//			apCustomerSearchListController.setList();
//		}
//		apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
//		
//		// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		
		// und Focus auf Titel-Feld setzen
		apCustomerSearchViewPaneController.getTxtTitle().requestFocus();		
    }

    // Handler for Button[fx:id="btnDuplicatesSave"] onAction
    /**
     * neue Kundendaten zusaetzlich als Duplikat abspeichern und zurueck zur Kundenansicht bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnDuplicatesSave(ActionEvent event) {	// neue Daten zusaetzlich speichern und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO anlegen, ID holen
		LOG.debug("Customer anlegen: " + apCustomerDoubleViewPaneController);
		try {
			apCustomerDoubleViewPaneController.getData().setId(customerService.create(apCustomerDoubleViewPaneController.getData()));
			showMessage(intString("customerpage.created"));

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
				apCustomerSearchViewPaneController.setData(apCustomerDoubleViewPaneController.getData());
				
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
			showMessage("customerpage.create." + e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage("customerpage.create." + e1.getLocalizedMessage());
		}
    }

    // Handler for Button[fx:id="btnEditSave"] onAction
    /**
     * geaenderte Kundendaten abspeichern und zurueck zur Kundenansicht bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnEditSave(ActionEvent event) {	// geaenderte Daten speichern und zurueck - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// geaendertes DTO speichern
		LOG.debug("Customer aktualisieren: " + apCustomerEditPaneController);
		try {
			customerService.update(apCustomerEditPaneController.getData());
			showMessage(intString("customerpage.changed"));

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
				apCustomerSearchViewPaneController.setData(apCustomerEditPaneController.getData());
				
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
			showMessage("customerpage.change." + e1.toString());
			showMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showMessage("customerpage.change." + e1.getLocalizedMessage());
		}
    }

    // Handler for Button[fx:id="btnCreateSave"] onAction
    /**
     * neue Kundendaten auf Duplikate pruefen und entweder zur Duplikatsauswahl oder abspeichern und zurueck zur Kundenansicht bzw. Kundensuche
     * 
     * @param event
     */
    @FXML
    void handleBtnCreateSave(ActionEvent event) {	// neue Daten auf Duplicate pruefen, ev. zur Duplikatspruefung, sonst zurueck - in aufrufender Funktion ueberschreiben!
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
				showMessage(intString("customerpage.created"));

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
					apCustomerSearchViewPaneController.setData(apCustomerCreatePaneController.getData());
					
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
				showMessage("customerpage.create." + e1.toString());
				showMessage(e1.getFieldErrors());
			} catch (ServiceException e1) {
				showMessage("customerpage.create." + e1.getLocalizedMessage());
			}
		}
    }

    // Handler for Button[fx:id="btnSearchSearch"] onAction
    /**
     * nach eingegebenen Suchkriterien suchen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchSearch(ActionEvent event) {		// neue Suche ausloesen
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// passende Search-List setzen
		try {
			// nach akuellen Daten aus dem VIEW-Panel filtern
			apCustomerSearchListController.setList(customerService.find(apCustomerSearchViewPaneController.getData()));
		} catch (ServiceException e) {
			apCustomerSearchListController.setList();
		}
		apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

		// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		
		// und Focus auf 1. Zeile setzen
		apCustomerSearchListController.getTvCustomersListView().requestFocus();		
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
		setTitle("customerpage.customer_data");
		
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
		setTitle("customerpage.new_customer_data");
		
		// Create-New Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerCreatePane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		tbVisibleBar.getItems().add(btnCreateCancel);		// Abbruch ohne speichern
		tbVisibleBar.getItems().add(btnCreateReset);		// Eingaben zuruecksetzen
		tbVisibleBar.getItems().add(btnCreateSave);			// Neuanlage speichern

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
		setTitle("customerpage.duplicates_title");
		
		// Create-Duplicates Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerDoubleViewPane);
		spRightPanes.getChildren().add(apCustomerDoubleFoundPane);
		spListPanes.getChildren().add(apCustomerDoubleList);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		tbVisibleBar.getItems().add(btnDuplicatesDiscard);			// Anlegen verwerfen (und ev. Tab schlieszen)
		tbVisibleBar.getItems().add(btnDuplicatesBack);		// Zurueck zum Aendern der neuen daten
		tbVisibleBar.getItems().add(btnDuplicatesOverwrite);			// Auswahl durch neue Daten ersetzen
		tbVisibleBar.getItems().add(btnDuplicatesSave);		// Neuanlage zusaetzlich als eigenen Kunden speichern
		tbVisibleBar.getItems().add(btnDuplicatesApply);		// Auswahl uebernehmen und zrueck

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
	private void setEditSetup() {
		LOG.info("");

		// View initialisieren
		setInitSetup();
		
		// Seitentitel setzen
		setTitle("customerpage.customer_data");
		
		// Edit Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerEditPane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (null != tbVisibleBar) {
			tbVisibleBar.getItems().add(btnEditCancel);	// Abbruch ohne speichern
			tbVisibleBar.getItems().add(btnEditReset);	// Eingaben zuruecksetzen
			tbVisibleBar.getItems().add(btnEditSave);		// Aenderungen speichern
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
		setTitle("customerpage.customer_data");
		
		// Show Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerViewPane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnShowBack);			// zurueck zur Suchauswahl
		}
		if (this.paneMode == PaneMode.VIEW) {
			tbVisibleBar.getItems().add(btnViewCancel);			// Kunden aendern
			tbVisibleBar.getItems().add(btnViewClear);				// Kunden aendern
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
			setTitle("customerpage");
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			setTitle("customerpage.search_title");
		}

		// Search Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerSearchViewPane);
		spRightPanes.getChildren().add(apCustomerSearchFoundPane);
		spListPanes.getChildren().add(apCustomerSearchList);

		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchClose);		// Schlieszen ohne Auswahl
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchBack);	// Cancel ohne Auswahl
		}
		tbVisibleBar.getItems().add(btnSearchSearch);			// nochmals suchen
		tbVisibleBar.getItems().add(btnSearchReset);	// Suchkriterien loeschen
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchView);	// Details der Suchauswahl
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchApply);		// Auswahl uebernehmen und zrueck
		}
		tbVisibleBar.getItems().add(btnSearchDelete);			// ausgewaehlten Kunden loeschen
		tbVisibleBar.getItems().add(btnSearchCreate);			// Neuen Kunden anlegen
		tbVisibleBar.getItems().add(btnSearchEdit);	// ausgewaehlten Kunden aendern
		
		// ev. geht manches nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {

				// Focus auf 1. Feld setzen
				apCustomerSearchViewPaneController.getTxtTitle().requestFocus();							
		    }
		});
		
		// Suchkriterium fuer Reset zwischenspeichern
		searchDto = apCustomerSearchViewPaneController.getData();
		
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
			setEditSetup();
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
		
		lbTopTitle.setText(intString(title));
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
		    // fx:id="apCustomerSearchViewPane"
			apCustomerSearchViewPaneController.setData(customerDto);
		} else if (mode == PaneMode.SELECT) {
		    // fx:id="apCustomerSearchViewPane"
			apCustomerSearchViewPaneController.setData(customerDto);
		} else if (mode == PaneMode.VIEW) {
		    // fx:id="apCustomerViewPane"
			apCustomerViewPaneController.setData(customerDto);
		} 
	}

	private void showMessage(List<FieldError> list) {
		for (FieldError errMsg : list) {
			showMessage(intString("customerpage." + errMsg.getField()) + ": " + intString("customerpage." + errMsg.getMessage()));
		}
	}
	
	private void showMessage(String msg) {
		lvMessageList.setVisible(true);
		lvMessageList.getItems().add(intString(msg));
		lvMessageList.setPrefHeight(12 + lvMessageList.getItems().size()*25);	// TODO dynamisch an Schrifthoehe anpassen
	}
	
	private void hideMessage() {
		// Message-List entleeren und verbergen
		lvMessageList.setVisible(false);
		lvMessageList.getItems().clear();
		lvMessageList.setPrefHeight(0);
	}

	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, NULL in "" uebersetzen
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
}
