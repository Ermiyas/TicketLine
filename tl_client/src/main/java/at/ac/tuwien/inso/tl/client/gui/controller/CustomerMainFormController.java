/**
 * Sample Skeleton for "CustomerMainForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
	
	public static enum PaneMode {CREATE, MANAGE, SELECT, VIEW, EDIT, DELETE};				// alle moeglichne Init-Modes der Panes (ohne DUPLICATES)
	
	private PaneMode paneMode;							// eingestellter Display-Mode der Panes
	
	private CustomerDto editDto = new CustomerDto();
	private CustomerDto viewDto = new CustomerDto();
//	private CustomerDto searchDto = new CustomerDto();

	private Validator validator;								// Eingabe-Validator

	// injizierte Variablen

	@Autowired private ApplicationContext appContext;			// FXML-Umfeld, fuer Validierung noetig
	@Autowired private CustomerService customerService;			// Kunden-Services
    @FXML private ResourceBundle resources; 			// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;							// URL location of the FXML file that was given to the FXMLLoader
    
    @FXML private AnchorPane 					apCustomerCreatePane;					// fx:id="apCustomerCreatePane"
    @FXML private CustomerBaseFormController 	apCustomerCreatePaneController;
    @FXML private AnchorPane 					apCustomerDeletePane;					// fx:id="apCustomerDeletePane"
    @FXML private CustomerBaseFormController 	apCustomerDeletePaneController;
    @FXML private AnchorPane 					apCustomerDuplicatesList;				// fx:id="apCustomerDuplicatesList"
    @FXML private CustomerListFormController 	apCustomerDuplicatesListController;
    @FXML private AnchorPane 					apCustomerDuplicatesPane;				// fx:id="apCustomerDuplicatesPane"
    @FXML private CustomerBaseFormController 	apCustomerDuplicatesPaneController;
    @FXML private AnchorPane 					apCustomerDuplicatesFoundPane;			// fx:id="apCustomerDuplicatesFoundPane"
    @FXML private CustomerBaseFormController 	apCustomerDuplicatesFoundPaneController;
    @FXML private AnchorPane 					apCustomerEditPane;						// fx:id="apCustomerEditPane"
    @FXML private CustomerBaseFormController 	apCustomerEditPaneController;
    @FXML private AnchorPane 					apCustomerSearchFoundPane;				// fx:id="apCustomerSearchFoundPane"
    @FXML private CustomerBaseFormController 	apCustomerSearchFoundPaneController;
    @FXML private AnchorPane 					apCustomerSearchList;					// fx:id="apCustomerSearchList"
    @FXML private CustomerListFormController 	apCustomerSearchListController;
    @FXML private AnchorPane 					apCustomerSearchPane;					// fx:id="apCustomerSearchPane"
    @FXML private CustomerBaseFormController 	apCustomerSearchPaneController;
    @FXML private AnchorPane 					apCustomerViewPane;						// fx:id="apCustomerViewPane"
    @FXML private CustomerBaseFormController 	apCustomerViewPaneController;

    @FXML private AnchorPane apCustomerMainForm;	// eigenes Root-Pane				// fx:id="apCustomerMainForm"
    @Autowired private ClientMainController apClientMainController;		// vermutlich uebergeordneter Controller, falls ....
    
    // Buttons
    
    @FXML private Button btnCreateCancel;			// fx:id="btnCreateCancel"
    @FXML private Button btnCreateReset;			// fx:id="btnCreateReset"
    @FXML private Button btnDeleteCancel;			// fx:id="btnDeleteCancel"
    @FXML private Button btnDeleteConfirm;			// fx:id="btnDeleteConfirm"
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
    @FXML private Button btnViewBack;				// fx:id="btnViewBack"
    @FXML private Button btnViewBackExtern;			// fx:id="btnViewBackExtern"
    @FXML private Button btnViewSearch;				// fx:id="btnViewSearch"
    @FXML private Button btnViewClear;				// fx:id="btnViewClear"  
    @FXML private Button btnViewForwardExtern;		// fx:id="btnViewForwardExtern"
    
    // sonstige FXML-Elemente
    
    @FXML private Label lbTopTitle;                 // fx:id="lbTopTitle"			// Ueberschrift
    
    @FXML private ListView<String> lvMessageList;   // fx:id="lvMessageList"		// Message-Box
    
    @FXML private StackPane spLeftPanes;            // fx:id="spLeftPanes"			// links Details
    @FXML private StackPane spListPanes;            // fx:id="spListPanes"			// unten Liste
    @FXML private StackPane spRightPanes;           // fx:id="spRightPanes"			// rechts Gefunden
    
    @FXML private ToolBar tbVisibleBar;             // fx:id="tbVisibleBar"			// Button-Leiste

    // ------------ div. Button-Getter, um externe Handler drauf setzen zu koennen

	public Button getBtnCreateCancel() { return btnCreateCancel; }
	public Button getBtnCreateReset() { return btnCreateReset; }
	public Button getBtnCreateSave() { return btnCreateSave; }
	public Button getBtnDeleteCancel() { return btnDeleteCancel; }
	public Button getBtnDeleteConfirm() { return btnDeleteConfirm; }
	public Button getBtnDuplicatesApply() { return btnDuplicatesApply; }
	public Button getBtnDuplicatesBack() { return btnDuplicatesBack; }
	public Button getBtnDuplicatesDiscard() { return btnDuplicatesDiscard; }
	public Button getBtnDuplicatesOverwrite() { return btnDuplicatesOverwrite; }
	public Button getBtnDuplicatesSave() { return btnDuplicatesSave; }
	public Button getBtnEditCancel() { return btnEditCancel; }
	public Button getBtnEditReset() { return btnEditReset; }
	public Button getBtnEditSave() { return btnEditSave; }
	public Button getBtnSearchApply() { return btnSearchApply; }
	public Button getBtnSearchBack() { return btnSearchBack; }
	public Button getBtnSearchClose() { return btnSearchClose; }
	public Button getBtnSearchCreate() { return btnSearchCreate; }
	public Button getBtnSearchDelete() { return btnSearchDelete; }
	public Button getBtnSearchEdit() { return btnSearchEdit; }
	public Button getBtnSearchReset() { return btnSearchReset; }
	public Button getBtnSearchSearch() { return btnSearchSearch; }
	public Button getBtnSearchView() { return btnSearchView; }
	public Button getBtnViewBack() { return btnViewBack; }
	public Button getBtnViewBackExtern() { return btnViewBackExtern; }
	public Button getBtnViewSearch() { return btnViewSearch; }
	public Button getBtnViewClear() { return btnViewClear; }
	public Button getBtnViewForwardExtern() { return btnViewForwardExtern; }

    // --------- Initialisierung
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
		assert customerService != null : 						"\"customerService\" was not injected: check your Interface-file 'CustomerService.java'.";
        assert resources != null : 								"\"resources\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert location != null : 								"\"location\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert btnCreateCancel != null : 						"fx:id=\"btnCreateCancel\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnCreateReset != null : 						"fx:id=\"btnCreateReset\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDeleteCancel != null : 						"fx:id=\"btnDeleteCancel\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnDeleteConfirm != null : 						"fx:id=\"btnDeleteConfirm\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
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
        assert btnViewBack != null : 							"fx:id=\"btnViewBack\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnViewSearch != null : 							"fx:id=\"btnViewSearch\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert btnViewClear != null : 							"fx:id=\"btnViewClear\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerCreatePane != null : 					"fx:id=\"apCustomerCreatePane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerCreatePaneController != null : 		"fx:id=\"apCustomerCreatePaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDeletePane != null : 					"fx:id=\"apCustomerDeletePane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDeletePaneController != null : 		"fx:id=\"apCustomerDeletePaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDuplicatesFoundPane != null : 			"fx:id=\"apCustomerDuplicatesFoundPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDuplicatesFoundPaneController != null :"fx:id=\"apCustomerDuplicatesFoundPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDuplicatesList != null : 				"fx:id=\"apCustomerDuplicatesList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDuplicatesListController != null : 	"fx:id=\"apCustomerDuplicatesListController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDuplicatesPane != null : 				"fx:id=\"apCustomerDuplicatesPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerDuplicatesPaneController != null : 	"fx:id=\"apCustomerDuplicatesPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerEditPane != null : 					"fx:id=\"apCustomerEditPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerEditPaneController != null : 			"fx:id=\"apCustomerEditPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchFoundPane != null : 				"fx:id=\"apCustomerSearchFoundPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchFoundPaneController != null : 	"fx:id=\"apCustomerSearchFoundPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchList != null : 					"fx:id=\"apCustomerSearchList\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchListController != null : 		"fx:id=\"apCustomerSearchListController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchPane != null : 					"fx:id=\"apCustomerSearchPane\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
        assert apCustomerSearchPaneController != null : 		"fx:id=\"apCustomerSearchPaneController\" was not injected: check your FXML file 'CustomerMainForm.fxml'.";
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

        // Listener fuer selection changes in SearchList um SearchFoundPane laufend zu aktualisieren
        apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDto>() {
            public void changed(ObservableValue<? extends CustomerDto> observable, CustomerDto oldValue, CustomerDto newValue) {
                LOG.info("Aenderungen Search-Liste");
                
                // Daten aktualisieren
                apCustomerSearchFoundPaneController.setData(newValue);
            }
        });
        
        // Listener fuer selection changes in DuplicatesList um DuplicatesFoundPane laufend zu aktualisieren
        apCustomerDuplicatesListController.getTvCustomersListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerDto>() {
            public void changed(ObservableValue<? extends CustomerDto> observable, CustomerDto oldValue, CustomerDto newValue) {
                LOG.info("Aenderungen Duplicates-Liste");

                // Daten aktualisieren
                apCustomerDuplicatesFoundPaneController.setData(newValue);
}
        });
        
        // einzelne Panes initialisieren
        apCustomerCreatePaneController.setPaneMode(CustomerBaseFormController.PaneMode.CREATE);
        apCustomerCreatePaneController.setTitle("customerpage.new_customer_data");

        apCustomerDeletePaneController.setPaneMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDeletePaneController.setTitle("customerpage.delete_customer");
        apCustomerDeletePaneController.getCbSex().setVisible(false);
        apCustomerDeletePaneController.getTxtSex().setVisible(true);
        
        apCustomerDuplicatesFoundPaneController.setPaneMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDuplicatesFoundPaneController.setTitle("customerpage.selected_duplicate");
        apCustomerDuplicatesFoundPaneController.getCbSex().setVisible(false);
        apCustomerDuplicatesFoundPaneController.getTxtSex().setVisible(true);
        
        apCustomerDuplicatesListController.setTitle("customerpage.found_duplicates");
        
        apCustomerDuplicatesPaneController.setPaneMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerDuplicatesPaneController.setTitle("customerpage.new_customer_data");
        apCustomerDuplicatesPaneController.getCbSex().setVisible(false);
        apCustomerDuplicatesPaneController.getTxtSex().setVisible(true);

        apCustomerEditPaneController.setPaneMode(CustomerBaseFormController.PaneMode.EDIT);
        apCustomerEditPaneController.setTitle("customerpage.customer_data");
        
        apCustomerSearchFoundPaneController.setPaneMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerSearchFoundPaneController.setTitle("customerpage.search_select");
        apCustomerSearchFoundPaneController.getCbSex().setVisible(false);
        apCustomerSearchFoundPaneController.getTxtSex().setVisible(true);

        apCustomerSearchListController.setTitle("customerpage.search_results");
        
        apCustomerSearchPaneController.setPaneMode(CustomerBaseFormController.PaneMode.SEARCH);
        apCustomerSearchPaneController.setTitle("customerpage.search_criteria");
        
        apCustomerViewPaneController.setPaneMode(CustomerBaseFormController.PaneMode.VIEW);
        apCustomerViewPaneController.setTitle("customerpage.customer_data");
        apCustomerViewPaneController.getCbSex().setVisible(false);
        apCustomerViewPaneController.getTxtSex().setVisible(true);
        
        // gesamtes Form-Setup initialisieren, Default = Display aktuellen Customer
        hideMessage();
        setPaneMode(PaneMode.VIEW);
		
        // Panes sicherheitshalber mit leeren Daten initialisieren
	    
		apCustomerCreatePaneController.setData();			// fx:id="apCustomerCreatePane"
		apCustomerDeletePaneController.setData();			// fx:id="apCustomerDeletePane"
		apCustomerDuplicatesPaneController.setData();		// fx:id="apCustomerDuplicatesPane"
		apCustomerEditPaneController.setData();				// fx:id="apCustomerEditPane"
		apCustomerSearchPaneController.setData();	    	// fx:id="apCustomerSearchPane"
		apCustomerViewPaneController.setData();	    		// fx:id="apCustomerViewPane"
		
		// TODO Duplicates-Liste initialisieren				// fx:id="apCustomerDuplicatesList"
		// TODO Search-Liste initialisieren					// fx:id="apCustomerSearchList"

		apCustomerDuplicatesFoundPaneController.setData();	// fx:id="apCustomerDuplicatesFoundPane"
		apCustomerSearchFoundPaneController.setData();	    // fx:id="apCustomerSearchFoundPane"
        
	}

	// ------------ Button-Action-Handler

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
		initSearchPane();
		
		// und Focus auf Tabelle setzen
		// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(
			new Runnable() {
			    public void run() {
					LOG.info("delayed focus");
	        
					apCustomerSearchListController.getTvCustomersListView().requestFocus();		
			    }
			}
		);
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
        apCustomerCreatePaneController.hideAllErrors();

        // Felder validieren
        Set<ConstraintViolation<CustomerDto>> violations = null;
        if (apCustomerCreatePaneController.getValidate()) {
        	violations = validator.validate(apCustomerCreatePaneController.getData());
        }
		if ( violations != null && ! violations.isEmpty()) {
			LOG.debug("Input validation unsuccessful");
			for(ConstraintViolation<CustomerDto> cv : violations) {
				TextInputControl errControl = apCustomerCreatePaneController.getFxmlInputMap().get(cv.getPropertyPath().toString()); 
				if(errControl != null) {
					apCustomerCreatePaneController.showError(errControl, cv.getMessage());
				}
			}
		} else {
			// check for duplicates
			List<CustomerDto> customerList = null;
			try {
				customerList = customerService.find(apCustomerCreatePaneController.getData());		// TODO flexiblere Duplikatssuche (dont filter NULL-Values in DB)
			} catch (ServiceException e1) {
				customerList = new ArrayList<CustomerDto>();
			}
			
			if (customerList.size() != 0) {
				// Duplicates-Pane anzeigen
	
				// DTO uebernehmen
				apCustomerDuplicatesPaneController.setData(apCustomerCreatePaneController.getData());
				
				// passende Search-List setzen
				apCustomerDuplicatesListController.setList(customerList);
				apCustomerDuplicatesListController.getTvCustomersListView().getSelectionModel().selectFirst();
	
				// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
				
				// zur Duplicatsanzeige wechseln
				initDuplicatesPane();					// zur Duplikatsanzeige
			} else {
				
				// Neuanlage abspeichern, ID holen
				LOG.debug("Customer anlegen: " + apCustomerCreatePaneController);
				try {
					Integer newId = customerService.create(apCustomerCreatePaneController.getData());
					apCustomerCreatePaneController.getData().setId(newId);
					showMessage(intString("customerpage.created"));
	
					// richtiges Pane anzeigen
					if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
						// Details-Pane anzeigen
	
						// DTO uebernehmen
						apCustomerViewPaneController.setData(apCustomerCreatePaneController.getData());
						
						// zur Detailanzeige wechseln
						initViewPane();
					}
					if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
						// Search-Pane anzeigen
	
						// passende Search-List setzen
						try {
							// nach akuellen Daten aus dem VIEW-Panel filtern
							apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
							apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
							for (CustomerDto customer : apCustomerSearchListController.getTvCustomersListView().getItems()) {
								if (customer.getId() == newId) {
									apCustomerSearchListController.getTvCustomersListView().getSelectionModel().select(customer);
								}
							}
						} catch (ServiceException e) {
							apCustomerSearchListController.setList();
							apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
						}
	
						// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
						
						// zur Suchauswahl wechseln
						initSearchPane();
	
						// und Focus auf Tabelle setzen
						// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
						Platform.runLater(
							new Runnable() {
							    public void run() {
									LOG.info("delayed focus");
					        
									apCustomerSearchListController.getTvCustomersListView().requestFocus();		
							    }
							}
						);
					}
				} catch (ValidationException e1) {
					showExcMessage("customerpage.create." + e1.toString());
					showExcMessage(e1.getFieldErrors());
				} catch (ServiceException e1) {
					showExcMessage("customerpage.create." + e1.getLocalizedMessage());
				}
			}
		}
    }

    // Handler for Button[fx:id="btnDeleteCancel"] onAction
    /**
     * Kunden nicht loeschen und weiter in Suche
     * 
     * @param event
     */
    @FXML
    void handleBtnDeleteCancel(ActionEvent event) {		// DTO nicht loeschen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// zurueck zur Suche wechseln
		initSearchPane();

		// und Focus auf Tabelle setzen
		// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(
			new Runnable() {
			    public void run() {
					LOG.info("delayed focus");
	        
					apCustomerSearchListController.getTvCustomersListView().requestFocus();		
			    }
			}
		);
    }

    // Handler for Button[fx:id="btnDeleteConfirm"] onAction
    /**
     * Kunden loeschen und weiter in Suche
     * 
     * @param event
     */
    @FXML
    void handleBtnDeleteConfirm(ActionEvent event) {		// DTO loeschen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();
		
		// DTO loeschen
		LOG.debug("Customer loeschen: " + apCustomerDeletePaneController.getData());
		try {
			Integer customerID = apCustomerDeletePaneController.getData().getId();
			customerService.deleteById(customerID);
			showMessage(intString("customerpage.deleted"));
			// prüfen, ob aktueller Kunde geloescht wurde
			if (viewDto.getId() == customerID) {
				viewDto = new CustomerDto();
				apCustomerViewPaneController.setData(viewDto);
			}

			// passende Search-List setzen
			try {
				// nach akuellen Daten aus dem SEARCH-Panel filtern
				apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
			} catch (ServiceException e) {
				apCustomerSearchListController.setList();
			}
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

			// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
			
		} catch (ValidationException e1) {
			showExcMessage("customerpage.delete." + e1.toString());
			showExcMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showExcMessage("customerpage.delete." + e1.getLocalizedMessage());
		}
		
		// zur Suche wechseln
		initSearchPane();
    }

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
			apCustomerViewPaneController.setData(apCustomerDuplicatesFoundPaneController.getData());
			
			// zur Detailanzeige wechseln
			initViewPane();
		}
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
			// Search-Pane anzeigen

			// passende Search-List setzen
			try {
				// nach akuellen Daten aus dem VIEW-Panel filtern
				apCustomerSearchListController.setList(customerService.find(apCustomerDuplicatesFoundPaneController.getData()));
			} catch (ServiceException e) {
				apCustomerSearchListController.setList();
			}
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();

			// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
			
			// zur Suchauswahl wechseln
			initSearchPane();
		}
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
		initCreatePane();
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
		initSearchPane();
		
		// und Focus auf Tabelle setzen
		// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(
			new Runnable() {
			    public void run() {
					LOG.info("delayed focus");
	        
					apCustomerSearchListController.getTvCustomersListView().requestFocus();		
			    }
			}
		);
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
		LOG.debug("Customer: " + apCustomerDuplicatesFoundPaneController);
		LOG.debug("überschreiben mit: " + apCustomerDuplicatesPaneController);
		CustomerDto changeDto = apCustomerDuplicatesPaneController.getData();
		changeDto.setId(apCustomerDuplicatesFoundPaneController.getData().getId());
		changeDto.setPoints(apCustomerDuplicatesFoundPaneController.getData().getPoints());
		try {
			customerService.update(changeDto);
			showMessage(intString("customerpage.changed"));
			
			// richtiges Pane anzeigen
			if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
				// Details-Pane anzeigen

				// DTO uebernehmen
				apCustomerViewPaneController.setData(changeDto);
				
				// zur Detailanzeige wechseln
				initViewPane();
			}
			if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
				// Search-Pane anzeigen

				// passende Search-List setzen
				try {
					// nach akuellen Daten aus dem VIEW-Panel filtern
					apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
					apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
					for (CustomerDto customer : apCustomerSearchListController.getTvCustomersListView().getItems()) {
						if (customer.getId() == changeDto.getId()) {
							apCustomerSearchListController.getTvCustomersListView().getSelectionModel().select(customer);
						}
					}
				} catch (ServiceException e) {
					apCustomerSearchListController.setList();
					apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
				}

				// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
				
				// zur Suchauswahl wechseln
				initSearchPane();
				
				// und Focus auf Tabelle setzen
				// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
				Platform.runLater(
					new Runnable() {
					    public void run() {
							LOG.info("delayed focus");
			        
							apCustomerSearchListController.getTvCustomersListView().requestFocus();		
					    }
					}
				);
			}
		} catch (ValidationException e1) {
			showExcMessage("customerpage.overwrite." + e1.toString());
			showExcMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showExcMessage("customerpage.overwrite." + e1.getLocalizedMessage());
		}
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
		LOG.debug("Customer anlegen: " + apCustomerDuplicatesPaneController);
		try {
			Integer newId = customerService.create(apCustomerDuplicatesPaneController.getData());
			apCustomerDuplicatesPaneController.getData().setId(newId);
			showMessage(intString("customerpage.created"));

			// richtiges Pane anzeigen
			if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
				// Details-Pane anzeigen

				// DTO uebernehmen
				apCustomerViewPaneController.setData(apCustomerDuplicatesPaneController.getData());
				
				// zur Detailanzeige wechseln
				initViewPane();
			}
			if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
				// Search-Pane anzeigen

				// passende Search-List setzen
				try {
					// nach akuellen Daten aus dem VIEW-Panel filtern
					apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
					apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
					for (CustomerDto customer : apCustomerSearchListController.getTvCustomersListView().getItems()) {
						if (customer.getId() == newId) {
							apCustomerSearchListController.getTvCustomersListView().getSelectionModel().select(customer);
						}
					}
				} catch (ServiceException e) {
					apCustomerSearchListController.setList();
					apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
				}

				// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
				
				// zur Suchauswahl wechseln
				initSearchPane();
				
				// und Focus auf Tabelle setzen
				// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
				Platform.runLater(
					new Runnable() {
					    public void run() {
							LOG.info("delayed focus");
			        
							apCustomerSearchListController.getTvCustomersListView().requestFocus();		
					    }
					}
				);
			}
		} catch (ValidationException e1) {
			showExcMessage("customerpage.create." + e1.toString());
			showExcMessage(e1.getFieldErrors());
		} catch (ServiceException e1) {
			showExcMessage("customerpage.create." + e1.getLocalizedMessage());
		}
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
		initSearchPane();
		
		// und Focus auf Tabelle setzen
		// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(
			new Runnable() {
			    public void run() {
					LOG.info("delayed focus");
	        
					apCustomerSearchListController.getTvCustomersListView().requestFocus();		
			    }
			}
		);
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
		apCustomerEditPaneController.setData(editDto);			// TODO zwischengespeicherte DTO wiederherstellen

		// und Focus auf Titel-Feld setzen
		apCustomerEditPaneController.getTxtTitle().requestFocus();		
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
        apCustomerEditPaneController.hideAllErrors();

        // Felder validieren
        Set<ConstraintViolation<CustomerDto>> violations = null;
        if (apCustomerEditPaneController.getValidate()) {
        	violations = validator.validate(apCustomerEditPaneController.getData());
        }
		if ( violations != null && ! violations.isEmpty()) {
			LOG.debug("Input validation unsuccessful");
			for(ConstraintViolation<CustomerDto> cv : violations) {
				TextInputControl errControl = apCustomerEditPaneController.getFxmlInputMap().get(cv.getPropertyPath().toString()); 
				if(errControl != null) {
					apCustomerEditPaneController.showError(errControl, cv.getMessage());
				}
			}
		} else {
			// geaendertes DTO speichern
			LOG.debug("Customer aktualisieren: " + apCustomerEditPaneController);
			try {
				customerService.update(apCustomerEditPaneController.getData());
				showMessage(intString("customerpage.changed"));
				editDto = apCustomerEditPaneController.getData();
				
				// richtiges Pane anzeigen
				if (this.paneMode == PaneMode.VIEW) {			// default zurueck zur Kundenanzeige
					// Details-Pane anzeigen
	
					// DTO uebernehmen
					apCustomerViewPaneController.setData(editDto);
					
					// zur Detailanzeige wechseln
					initViewPane();
				}
				if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {			// default zurueck zur Kundensuche
					// Search-Pane anzeigen
	
					// passende Search-List setzen
					try {
						// nach akuellen Daten aus dem VIEW-Panel filtern
						apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
						apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
						for (CustomerDto customer : apCustomerSearchListController.getTvCustomersListView().getItems()) {
							if (customer.getId() == editDto.getId()) {
								apCustomerSearchListController.getTvCustomersListView().getSelectionModel().select(customer);						
							}
						}
					} catch (ServiceException e) {
						apCustomerSearchListController.setList();
						apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
					}
	
					// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
					
					// zur Suchauswahl wechseln
					initSearchPane();
	
					// und Focus auf Tabelle setzen
					// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
					Platform.runLater(
						new Runnable() {
						    public void run() {
								LOG.info("delayed focus");
				        
								apCustomerSearchListController.getTvCustomersListView().requestFocus();		
						    }
						}
					);
				}
			} catch (ValidationException e1) {
				showExcMessage("customerpage.change." + e1.toString());
				showExcMessage(e1.getFieldErrors());
			} catch (ServiceException e1) {
				showExcMessage("customerpage.change." + e1.getLocalizedMessage());
			}
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
		initViewPane();
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
		initViewPane();
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
        CustomerDto newDto = apCustomerSearchPaneController.getData();
        newDto.setPoints(null);
		apCustomerCreatePaneController.setData(newDto);

		// zur Neuerstellung wechseln
		initCreatePane();
    }

    // Handler for Button[fx:id="btnSearchDelete"] onAction
    /**
     * Sicherheitsabfrage aufrufen
     * 
     * @param event
     */
    @FXML
    void handleBtnSearchDelete(ActionEvent event) {		// DTO loeschen - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		if (apCustomerSearchFoundPaneController.getData().getId() != null) {
			// remove old messages
	        hideMessage();
			
			// DTO uebernehmen
			apCustomerDeletePaneController.setData(apCustomerSearchFoundPaneController.getData());
	
			// zur Neuerstellung wechseln
			initDeletePane();
		}
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

		if (apCustomerSearchFoundPaneController.getData().getId() != null) {
			// remove old messages
	        hideMessage();
			
			// DTO uebernehmen
			apCustomerEditPaneController.setData(apCustomerSearchFoundPaneController.getData());
			
			// zur Datenaenderung wechseln
			initEditPane();
		}
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
		apCustomerSearchPaneController.setData();
		
		// und Focus auf Titel-Feld setzen
		apCustomerSearchPaneController.getTxtTitle().requestFocus();		
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
			CustomerDto searchDto = apCustomerSearchFoundPaneController.getData();
			apCustomerSearchListController.setList(customerService.find(apCustomerSearchPaneController.getData()));
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
			for (CustomerDto customer : apCustomerSearchListController.getTvCustomersListView().getItems()) {
				if (customer.getId() == searchDto.getId()) {
					apCustomerSearchListController.getTvCustomersListView().getSelectionModel().select(customer);
				}
			}
		} catch (ServiceException e) {
			apCustomerSearchListController.setList();
			apCustomerSearchListController.getTvCustomersListView().getSelectionModel().selectFirst();
		}

		// das Found-Panel wird automatisch ueber den Event-Handler gesetzt
		
		// und Focus auf Tabelle setzen
		// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(
			new Runnable() {
			    public void run() {
					LOG.info("delayed focus");
	        
					apCustomerSearchListController.getTvCustomersListView().requestFocus();		
			    }
			}
		);
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

		if (apCustomerSearchFoundPaneController.getData().getId() != null) {
			// remove old messages
	        hideMessage();
	
			// DTO uebernehmen
			apCustomerViewPaneController.setData(apCustomerSearchFoundPaneController.getData());
	
			// zur Datenaenderung wechseln
			initViewPane();
		}
    }

    // Handler for Button[fx:id="btnViewBack"] onAction
    /**
     * Detailfenster schlieszen, Suchauswahl oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnViewBack(ActionEvent event) {		// Zurueck von Suche ohne Neuauswahl, default zur Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO zuruecksetzen
		viewDto = new CustomerDto();		// aktuelles DTO speichern!!!

		// zur Suchanzeige wechseln
		initSearchPane();

		// und Focus auf Tabelle setzen
		// ev. geht noch nicht alles, da Ziel-Pane vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(
			new Runnable() {
			    public void run() {
					LOG.info("delayed focus");
	        
					apCustomerSearchListController.getTvCustomersListView().requestFocus();		
			    }
			}
		);
    }

    // Handler for Button[fx:id="btnViewBackExtern"] onAction
    /**
     * Detailfenster extern verlassen
     * 
     * @param event
     */
    @FXML
    void handleBtnViewBackExtern(ActionEvent event) {		// Zurueck von aktueller Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

        // Nichts selbst machen, auf externen Event-Handler warten
    }

    // Handler for Button[fx:id="btnViewSearch"] onAction
    /**
     * Kundenanzeige schlieszen, Kundensuche oeffnen
     * 
     * @param event
     */
    @FXML
    void handleBtnViewSearch(ActionEvent event) {		// Kundensuche 
		LOG.info("");

		// remove old messages
        hideMessage();

		// DTO uebernehmen
        CustomerDto customer = apCustomerViewPaneController.getData();
        customer.setId(null);
		apCustomerSearchPaneController.setData(customer);
		
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
		initSelectionPane();
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
		viewDto = new CustomerDto();

		// und Focus auf Titel-Feld setzen
		apCustomerViewPaneController.getTxtTitle().requestFocus();		
    }

    // Handler for Button[fx:id="btnViewForwardExtern"] onAction
    /**
     * Detailfenster extern verlassen
     * 
     * @param event
     */
    @FXML
    void handleBtnViewForwardExtern(ActionEvent event) {		// Weiter von aktueller Anzeige - in aufrufender Funktion ueberschreiben!
		LOG.info("");

		// remove old messages
        hideMessage();

        // Nichts selbst machen, auf externen Event-Handler warten
    }

	// -----------------------------------------------------------------------

	/**
	 * Panes-Setup fuer alle anderen Setups initialisieren
	 */
	private void initAllPanes() {
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
	 * Panes-Setup um neuen Kunden anzulegen,
	 * innerhalb eines anderen Tabs als Untermaske aufgerufen
	 */
	private void initCreatePane() {
		LOG.info("");
		
		// View initialisieren
		initAllPanes();
		
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
	 * Panes-Setup um bestehenden Kunden zu loeschen,
	 * innerhalb des selben Tabs statt der Suche anzeigen
	 */
	private void initDeletePane() {
		LOG.info("");
		
		// View initialisieren
		initAllPanes();
		
		// Seitentitel setzen
		setTitle("customerpage.delete_customer");
		
		// Delete Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerDeletePane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		tbVisibleBar.getItems().add(btnDeleteCancel);		// Abbruch ohne loeschen
		tbVisibleBar.getItems().add(btnDeleteConfirm);		// Kunden loeschen

//		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				// Focus auf 1. Feld setzen
//				apCustomerCreatePaneController.getTxtTitle().requestFocus();		
//		    }
//		});
	}

	/**
	 * Panes-Setup um Duplikate bei einer Neuanlage Duplikate anzuzeigen,
	 * innerhalb des selben Tabs statt der Neuanlage anzeigen
	 */
	private void initDuplicatesPane() {
		LOG.info("");

		// View initialisieren
		initAllPanes();
		
		// Seitentitel setzen
		setTitle("customerpage.duplicates_title");
		
		// Create-Duplicates Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerDuplicatesPane);
		spRightPanes.getChildren().add(apCustomerDuplicatesFoundPane);
		spListPanes.getChildren().add(apCustomerDuplicatesList);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnDuplicatesDiscard);			// Anlegen verwerfen (und ev. Tab schlieszen)
		}
		tbVisibleBar.getItems().add(btnDuplicatesBack);				// Zurueck zum Aendern der neuen daten
		tbVisibleBar.getItems().add(btnDuplicatesOverwrite);		// Auswahl durch neue Daten ersetzen
		tbVisibleBar.getItems().add(btnDuplicatesSave);				// Neuanlage zusaetzlich als eigenen Kunden speichern
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnDuplicatesApply);			// Auswahl uebernehmen und zrueck
		}

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {

				// Focus auf Liste setzen
				apCustomerDuplicatesListController.getTvCustomersListView().requestFocus();
					
				// TODO Focus auf 1. Zeile setzen
		    }
		});
	}

	/**
	 * Panes-Setup um Kundendaten zu aendern,
	 * innerhalb eines anderen Tabs anzeigen
	 */
	private void initEditPane() {
		LOG.info("");

		// View initialisieren
		initAllPanes();
		
		// Seitentitel setzen
		setTitle("customerpage.customer_data");
		
		// Edit Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerEditPane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (null != tbVisibleBar) {
			tbVisibleBar.getItems().add(btnEditCancel);		// Abbruch ohne speichern
			tbVisibleBar.getItems().add(btnEditReset);		// Eingaben zuruecksetzen
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
	 * Panes-Setup fuer die Kundenverwaltung von der Hauptseite aus
	 * als eigenes Tab angezeigt, DTO nicht zurueckgeben
	 */
	private void initManagementPane() {
		LOG.info("");

		// gemeinsamer Panel-Aufbau
		initSearchPane();
		
	}

	/**
	 * Panes-Setup fuer die Kundenverwaltung 
	 * von einer Unter-Seite aus, DTO zurueckgeben
	 */
	private void initSelectionPane() {
		LOG.info("");

		// gemeinsamer Panel-Aufbau
		initSearchPane();
		
	}

	/**
	 * Panes-Setup um Kundendaten zu suchen,
	 */
	private void initSearchPane() {
		LOG.info("");

		// View initialisieren
		initAllPanes();

		// Seitentitel setzen
		if (this.paneMode == PaneMode.MANAGE) {
			setTitle("customerpage");
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			setTitle("customerpage.search_title");
		}

		// Search Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerSearchPane);
		spRightPanes.getChildren().add(apCustomerSearchFoundPane);
		spListPanes.getChildren().add(apCustomerSearchList);

		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		tbVisibleBar.getItems().add(btnSearchSearch);			// nochmals suchen
		tbVisibleBar.getItems().add(btnSearchReset);			// Suchkriterien loeschen
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchView);			// Details der Suchauswahl
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchApply);		// Auswahl uebernehmen und zrueck
		}
//		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
//			tbVisibleBar.getItems().add(btnSearchDelete);			// ausgewaehlten Kunden loeschen
//		}
		tbVisibleBar.getItems().add(btnSearchCreate);			// Neuen Kunden anlegen
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchEdit);				// ausgewaehlten Kunden aendern
		}
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchClose);		// Schlieszen ohne Auswahl
		}
		if (this.paneMode == PaneMode.VIEW || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnSearchBack);			// Cancel ohne Auswahl
		}
		
		// ev. geht manches nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {

				// Focus auf 1. Feld setzen
				apCustomerSearchPaneController.getTxtTitle().requestFocus();							
		    }
		});
		
		// Suchkriterium fuer Reset zwischenspeichern
//		searchDto = apCustomerSearchPaneController.getData();
		
	}
	
	/**
	 * Panes-Setup um Kundendaten anzuzeigen,
	 * z.B. auch innerhalb eines anderen Tabs 
	 */
	private void initViewPane() {
		LOG.info("");

		// View initialisieren
		initAllPanes();
		
		// Seitentitel setzen
		setTitle("customerpage.customer_data");
		
		// View Pane-Setup initialisieren
		spLeftPanes.getChildren().add(apCustomerViewPane);
		
		// benoetigte Buttons in gewuenschter Reihenfolge in die sichtbare Toolbar kopieren
		if (this.paneMode == PaneMode.MANAGE || this.paneMode == PaneMode.SELECT) {
			tbVisibleBar.getItems().add(btnViewBack);			// zurueck zur Suchauswahl
		}
		if (this.paneMode == PaneMode.VIEW) {
			tbVisibleBar.getItems().add(btnViewBackExtern);		// zurueck aus aktueller Ansicht
			tbVisibleBar.getItems().add(btnViewSearch);			// Kunden wechseln
			tbVisibleBar.getItems().add(btnViewClear);			// Kunden entleeren
			tbVisibleBar.getItems().add(btnViewForwardExtern);	// weiter aus aktueller Ansicht
		}

		// DTO zuruecksetzen
		viewDto = apCustomerViewPaneController.getData();		// aktuelles DTO speichern!!!

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Focus auf Toolbar setzen
				tbVisibleBar.requestFocus();
		    }
		});
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

		// Tab schlieszen - der sichere Weg: eigenes Tab suchen 
		Parent parent = apCustomerMainForm.getParent();
		TabPane tabPane = null;
		List<Object> objList = new ArrayList<Object>();
		objList.add(apCustomerMainForm);
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
	
	/**
	 * Sichtbarkeiten, etc. des Panels Modus-abhaengig anpassen
	 * 
	 * @param mode
	 */
	public void resetPaneMode() {
		LOG.info("");

		setPaneMode(this.paneMode);
	}

	/**
	 * Sichtbarkeiten, etc. des Panels Modus-abhaengig anpassen
	 * 
	 * @param mode
	 */
	public void setPaneMode(PaneMode mode) {
		LOG.info("");
		
		// Save Mode
		if (mode == null) {
			mode = PaneMode.VIEW;					// default Pane Mode
		}
		this.paneMode = mode;

		if (mode == PaneMode.CREATE) {
			initCreatePane();
		} else if (mode == PaneMode.EDIT) {
			initEditPane();
		} else if (mode == PaneMode.MANAGE) {
			initManagementPane();
		} else if (mode == PaneMode.SELECT) {
			initSelectionPane();
		} else if (mode == PaneMode.VIEW) {
			initViewPane();
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

	private void setCustomer(CustomerDto customerDto) {
		setCustomer(customerDto, paneMode);
	}
	
	private void setCustomer(CustomerDto customerDto, PaneMode mode) {
		
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

	private void showExcMessage(List<FieldError> list) {
		for (FieldError errMsg : list) {
			showMessage(intExcString("customerpage." + errMsg.getField()) + ": " + intExcString("customerpage." + errMsg.getMessage()));
		}
	}
	
	private void showExcMessage(String msg) {
		showMessage(intExcString(msg));
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

	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
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
	 * Kunden-Daten zurueckgeben
	 * 
	 * @return
	 */
	public CustomerDto getData() {
		LOG.info("");

		return viewDto;
	}
	
	/**
	 * VIEW-Daten loeschen
	 */
	public void setData() {
		LOG.info("");
		
		setData(null);
	}	
	
	/**
	 * VIEW-Daten setzen
	 * 
	 * @param customerDto
	 */
	public void setData(CustomerDto customerDto) {
		LOG.info("");
		
		viewDto = customerDto;
		apCustomerViewPaneController.setData(customerDto);
	}

	/**
	 * Suchkriterium loeschen
	 */
	public void setCriteria() {
		LOG.info("");
		
		setCriteria(null);
	}	
	
	/**
	 * Suchkriterium setzen
	 * 
	 * @param customerDto
	 */
	public void setCriteria(CustomerDto customerDto) {
		LOG.info("");
		
//		searchDto = customerDto;
		apCustomerSearchPaneController.setData(customerDto);
	}
	
}
