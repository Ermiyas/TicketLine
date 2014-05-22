/**
 * Sample Skeleton for "CustomerMultiPaneBase.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller @Scope("prototype") 
public class CustomerMainFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerMainFormController.class);

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="apCustomerCreatePane"
    private AnchorPane apCustomerCreatePane; 								
    @FXML
    private CustomerCreateBaseController apCustomerCreatePaneController; 	

    @FXML // fx:id="apCustomerEditPane"
    private AnchorPane apCustomerEditPane; 									
    @FXML
    private CustomerEditBaseController apCustomerEditPaneController; 		

    @FXML // fx:id="apCustomerViewPane"
    private AnchorPane apCustomerViewPane; 									
    @FXML
    private CustomerViewBaseController apCustomerViewPaneController; 

    @FXML // fx:id="apCustomerSearchPane"
    private AnchorPane apCustomerSearchPane; 								
    @FXML
    private CustomerSearchBaseController apCustomerSearchPaneController; 	

    @FXML // fx:id="apCustomerListPane"
    private AnchorPane apCustomerListPane; 									
    @FXML
    private CustomerListBaseController apCustomerListPaneController; 		

    @FXML // fx:id="apCustomerFoundPane"
    private AnchorPane apCustomerFoundPane; 								
    @FXML
    private CustomerViewBaseController apCustomerFoundPaneController; 		

    @FXML // fx:id="apMultiPaneBasePane"										// eigenes Root-Pane
    private AnchorPane apMultiPaneBasePane; 

    @FXML // fx:id="btnApply"
    private Button btnApply; 

    @FXML // fx:id="btnBack"
    private Button btnBack; 

    @FXML // fx:id="btnCancel"
    private Button btnCancel; 

    @FXML // fx:id="btnChange"
    private Button btnChange; 

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

    @FXML // fx:id="btnReset"
    private Button btnReset; 

    @FXML // fx:id="btnSave"
    private Button btnSave; 

    @FXML // fx:id="btnSearch"
    private Button btnSearch; 

    @FXML // fx:id="tbInvisibleBar"
    private ToolBar tbInvisibleBar;

    @FXML // fx:id="tbVisibleBar"
    private ToolBar tbVisibleBar;


    // Handler for Button[fx:id="btnApply"] onAction
    @FXML
    void handleBtnApply(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnBack"] onAction
    @FXML
    void handleBtnBack(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnCancel"] onAction
    @FXML
    void handleBtnCancel(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnChange"] onAction
    @FXML
    void handleBtnChange(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnClose"] onAction
    @FXML
    void handleBtnClose(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnDelete"] onAction
    @FXML
    void handleBtnDelete(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnDetails"] onAction
    @FXML
    void handleBtnDetails(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnDiscard"] onAction
    @FXML
    void handleBtnDiscard(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnNew"] onAction
    @FXML
    void handleBtnNew(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnOverwrite"] onAction
    @FXML
    void handleBtnOverwrite(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnReset"] onAction
    @FXML
    void handleBtnReset(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnSave"] onAction
    @FXML
    void handleBtnSave(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

    // Handler for Button[fx:id="btnSearch"] onAction
    @FXML
    void handleBtnSearch(ActionEvent event) {
		LOG.info("");
        // handle the event here
    }

//    @FXML // This method is called by the FXMLLoader when initialization is complete
//    void initialize() {
//    }
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
        assert apCustomerCreatePane != null : "fx:id=\"apCustomerCreatePane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert apCustomerEditPane != null : "fx:id=\"apCustomerEditPane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert apCustomerViewPane != null : "fx:id=\"apCustomerViewPane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert apCustomerSearchPane != null : "fx:id=\"apCustomerSearchPane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert apCustomerListPane != null : "fx:id=\"apCustomerListPane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert apCustomerFoundPane != null : "fx:id=\"apCustomerFoundPane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert apMultiPaneBasePane != null : "fx:id=\"apMultiPaneBasePane\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnApply != null : "fx:id=\"btnApply\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnBack != null : "fx:id=\"btnBack\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnCancel != null : "fx:id=\"btnCancel\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnChange != null : "fx:id=\"btnChange\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnClose != null : "fx:id=\"btnClose\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnDelete != null : "fx:id=\"btnDelete\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnDetails != null : "fx:id=\"btnDetails\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnDiscard != null : "fx:id=\"btnDiscard\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnNew != null : "fx:id=\"btnNew\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnOverwrite != null : "fx:id=\"btnOverwrite\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert btnSearch != null : "fx:id=\"btnSearch\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert tbInvisibleBar != null : "fx:id=\"tbInvisibleBar\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";
        assert tbVisibleBar != null : "fx:id=\"tbVisibleBar\" was not injected: check your FXML file 'CustomerMultiPaneBase.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected


    // ---------------------------------------
    
//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				if (null != getFieldsController()) {
//					// TODO restliche Initialisierung
//		
//					// TODO Test only
//					// getFieldsController().setFields();
//				}
//		    }
//		});

		// TODO restliche Initialisierung
		
		 // TODO Test only
        setSearchSetup();
	}

	/**
	 * @return the apCustomerCreatePaneController
	 */
	public CustomerCreateBaseController getCreatePaneController() {
		return apCustomerCreatePaneController;
	}

	/**
	 * @return the apCustomerEditPaneController
	 */
	public CustomerEditBaseController getEditPaneController() {
		return apCustomerEditPaneController;
	}

	/**
	 * @return the apCustomerSearchPaneController
	 */
	public CustomerSearchBaseController getSearchPaneController() {
		return apCustomerSearchPaneController;
	}

	/**
	 * @return the apCustomerViewPaneController
	 */
	public CustomerViewBaseController getViewPaneController() {
		return apCustomerViewPaneController;
	}

	/**
	 * @return the apCustomerFoundPaneController
	 */
	public CustomerViewBaseController getFoundPaneController() {
		return apCustomerFoundPaneController;
	}

	/**
	 * @return the apCustomerListPaneController
	 */
	public CustomerListBaseController getListPaneController() {
		return apCustomerListPaneController;
	}

	/**
	 * @return the btnApply
	 */
	public Button getBtnApply() {
		return btnApply;
	}

	/**
	 * @return the btnBack
	 */
	public Button getBtnBack() {
		return btnBack;
	}

	/**
	 * @return the btnCancel
	 */
	public Button getBtnCancel() {
		return btnCancel;
	}

	/**
	 * @return the btnChange
	 */
	public Button getBtnChange() {
		return btnChange;
	}

	/**
	 * @return the btnClose
	 */
	public Button getBtnClose() {
		return btnClose;
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
	 * @return the btnReset
	 */
	public Button getBtnReset() {
		return btnReset;
	}

	/**
	 * @return the btnSave
	 */
	public Button getBtnSave() {
		return btnSave;
	}

	/**
	 * @return the btnSearch
	 */
	public Button getBtnSearch() {
		return btnSearch;
	}

	/**
	 * @return the tbInvisibleBar
	 */
	public ToolBar getTbInvisibleBar() {
		return tbInvisibleBar;
	}

	/**
	 * @return the tbVisibleBar
	 */
	public ToolBar getTbVisibleBar() {
		return tbVisibleBar;
	}
	
	/**
	 * 
	 */
	public void setCreateSetup() {
		LOG.info("");
		// TODO neues DTO-Objekt erstellen
		
		// TODO neues DTO-Objekt an Sub-Controller übergeben
		
		// Create-New Pane-Setup initialisieren
		apCustomerCreatePane.setVisible(true);
		apCustomerEditPane.setVisible(false);
		apCustomerViewPane.setVisible(false);
		apCustomerSearchPane.setVisible(false);
		apCustomerListPane.setVisible(false);
		apCustomerFoundPane.setVisible(false);
		
		// Buttons in sichtbare Toolbar kopieren
		getTbVisibleBar().getChildrenUnmodifiable().clear();
		// getTbVisibleBar().getChildrenUnmodifiable().add(btnApply);

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Buttons in sichtbare Toolbar kopieren
				if (null != getTbVisibleBar()) {
					getTbVisibleBar().getItems().clear();
					getTbVisibleBar().getItems().add(getBtnCancel());		// Abbruch ohne speichern
					getTbVisibleBar().getItems().add(getBtnReset());		// Eingaben zuruecksetzen
					getTbVisibleBar().getItems().add(getBtnSave());			// Neuanlage speichern
//					getTbVisibleBar().getItems().add(getBtnDiscard());		// Anlage verwerfen
//					getTbVisibleBar().getItems().add(getBtnBack());			// Zurueck zum Aendern
//					getTbVisibleBar().getItems().add(getBtnOverwrite());	// Auswahl ersetzen
//					getTbVisibleBar().getItems().add(getBtnSearch());		// nochmals suchen
//					getTbVisibleBar().getItems().add(getBtnDetails());		// Details der Suchauswahl
//					getTbVisibleBar().getItems().add(getBtnApply());		// Auswahl uebernehmen
//					getTbVisibleBar().getItems().add(getBtnNew());			// Neuen Kunden anlegen
//					getTbVisibleBar().getItems().add(getBtnChange());
//					getTbVisibleBar().getItems().add(getBtnClose());
//					getTbVisibleBar().getItems().add(getBtnDelete());
				}

				// Focus auf 1. Feld setzen
				if (null != getCreatePaneController()) {
					getCreatePaneController().getFieldsController().getTxtTitle().requestFocus();		
				}
		    }
		});
	}

	/**
	 * 
	 */
	public void setDuplicatesSetup() {
		LOG.info("");
		// TODO erstelltes DTO-Objekt an Sub-Controller übergeben
		
		// TODO Liste der doppelten DTO-Objekt lesen
		
		// TODO Liste der doppelten DTO-Objekt an Sub-Controller übergeben
		
		// TODO angewaehltes DTO-Objekt aus Liste lesen
		
		// TODO angewaehltes DTO-Objekt an Sub-Controller übergeben

		// Create-Duplicates Pane-Setup initialisieren
		apCustomerCreatePane.setVisible(false);
		apCustomerEditPane.setVisible(false);
		apCustomerViewPane.setVisible(true);
		apCustomerSearchPane.setVisible(false);
		apCustomerListPane.setVisible(true);
		apCustomerFoundPane.setVisible(true);
		
		// Buttons in sichtbare Toolbar kopieren
		getTbVisibleBar().getChildrenUnmodifiable().clear();
		// getTbVisibleBar().getChildrenUnmodifiable().add(btnApply);

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Buttons in sichtbare Toolbar kopieren
				if (null != getTbVisibleBar()) {
					getTbVisibleBar().getItems().clear();
					getTbVisibleBar().getItems().add(getBtnDiscard());		// Anlage verwerfen
					getTbVisibleBar().getItems().add(getBtnBack());			// Zurueck zum Aendern
					getTbVisibleBar().getItems().add(getBtnOverwrite());	// Auswahl ersetzen
					getTbVisibleBar().getItems().add(getBtnSave());			// Neuanlage speichern
//					getTbVisibleBar().getItems().add(getBtnCancel());		// Abbruch ohne speichern
//					getTbVisibleBar().getItems().add(getBtnReset());		// Eingaben zuruecksetzen
//					getTbVisibleBar().getItems().add(getBtnSearch());		// nochmals suchen
//					getTbVisibleBar().getItems().add(getBtnDetails());		// Details der Suchauswahl
//					getTbVisibleBar().getItems().add(getBtnApply());		// Auswahl uebernehmen
//					getTbVisibleBar().getItems().add(getBtnNew());			// Neuen Kunden anlegen
//					getTbVisibleBar().getItems().add(getBtnChange());
//					getTbVisibleBar().getItems().add(getBtnClose());
//					getTbVisibleBar().getItems().add(getBtnDelete());
				}

				// Focus auf Liste setzen
				if (null != getListPaneController()) {
					getListPaneController().getTvCustomersFoundView().requestFocus();		
				}
		    }
		});
	}

	/**
	 * 
	 */
	public void setChangeSetup() {
		LOG.info("");
		// TODO bestehendes DTO-Objekt laden
		
		// TODO bestehendes DTO-Objekt an Sub-Controller übergeben
		
		// Change Pane-Setup initialisieren
		apCustomerCreatePane.setVisible(false);
		apCustomerEditPane.setVisible(true);
		apCustomerViewPane.setVisible(false);
		apCustomerSearchPane.setVisible(false);
		apCustomerListPane.setVisible(false);
		apCustomerFoundPane.setVisible(false);
		
		// Buttons in sichtbare Toolbar kopieren
		getTbVisibleBar().getChildrenUnmodifiable().clear();
		// getTbVisibleBar().getChildrenUnmodifiable().add(btnApply);

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Buttons in sichtbare Toolbar kopieren
				if (null != getTbVisibleBar()) {
					getTbVisibleBar().getItems().clear();
					getTbVisibleBar().getItems().add(getBtnCancel());		// Abbruch ohne speichern
					getTbVisibleBar().getItems().add(getBtnReset());		// Eingaben zuruecksetzen
					getTbVisibleBar().getItems().add(getBtnSave());			// Aenderungen speichern
//					getTbVisibleBar().getItems().add(getBtnBack());			// Cancel ohne Auswahl
//					getTbVisibleBar().getItems().add(getBtnSearch());		// nochmals suchen
//					getTbVisibleBar().getItems().add(getBtnDetails());		// Details der Suchauswahl
//					getTbVisibleBar().getItems().add(getBtnApply());		// Auswahl uebernehmen
//					getTbVisibleBar().getItems().add(getBtnNew());			// Neuen Kunden anlegen
//					getTbVisibleBar().getItems().add(getBtnChange());
//					getTbVisibleBar().getItems().add(getBtnClose());
//					getTbVisibleBar().getItems().add(getBtnDelete());
//					getTbVisibleBar().getItems().add(getBtnDiscard());
//					getTbVisibleBar().getItems().add(getBtnOverwrite());
				}

				// Focus auf 1. Feld setzen
				if (null != getEditPaneController()) {
					getEditPaneController().getFieldsController().getTxtTitle().requestFocus();							
				}
		    }
		});
	}

	/**
	 * 
	 */
	public void setSearchSetup() {
		LOG.info("");
		// TODO neues DTO-Objekt erstellen
		
		// TODO neues DTO-Objekt an Sub-Controller übergeben
		
		// TODO leere Liste an Sub-Controller übergeben
		
		// TODO leeres Objekt an Sub-Controller übergeben
		
		// Search Pane-Setup initialisieren
		apCustomerCreatePane.setVisible(false);
		apCustomerEditPane.setVisible(false);
		apCustomerViewPane.setVisible(false);
		apCustomerSearchPane.setVisible(true);
		apCustomerListPane.setVisible(true);
		apCustomerFoundPane.setVisible(true);
		

		// ev. geht manches nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Buttons in sichtbare Toolbar kopieren
				if (null != getTbVisibleBar()) {
					getTbVisibleBar().getItems().clear();
					getTbVisibleBar().getItems().add(getBtnClose());		// Schlieszen ohne Auswahl
					getTbVisibleBar().getItems().add(getBtnSearch());		// nochmals suchen
					getTbVisibleBar().getItems().add(getBtnReset());		// Suchkriterien loeschen
					getTbVisibleBar().getItems().add(getBtnDetails());		// Details der Suchauswahl
					getTbVisibleBar().getItems().add(getBtnApply());		// Auswahl uebernehmen
					getTbVisibleBar().getItems().add(getBtnDelete());		// ausgewaehlten Kunden loeschen
					getTbVisibleBar().getItems().add(getBtnNew());			// Neuen Kunden anlegen
//					getTbVisibleBar().getItems().add(getBtnBack());			// Cancel ohne Auswahl
//					getTbVisibleBar().getItems().add(getBtnCancel());
//					getTbVisibleBar().getItems().add(getBtnChange());
//					getTbVisibleBar().getItems().add(getBtnDiscard());
//					getTbVisibleBar().getItems().add(getBtnOverwrite());
//					getTbVisibleBar().getItems().add(getBtnSave());
				}

				// Focus auf 1. Feld setzen
				if (null != getSearchPaneController()) {
					getSearchPaneController().getFieldsController().getTxtTitle().requestFocus();							
				}
		    }
		});
	}

	/**
	 * 
	 */
	public void setShowSetup() {
		LOG.info("");
		// TODO bestehendes DTO-Objekt laden
		
		// TODO bestehendes DTO-Objekt an Sub-Controller übergeben
		
		// Show Pane-Setup initialisieren
		apCustomerCreatePane.setVisible(false);
		apCustomerEditPane.setVisible(false);
		apCustomerViewPane.setVisible(true);
		apCustomerSearchPane.setVisible(false);
		apCustomerListPane.setVisible(false);
		apCustomerFoundPane.setVisible(false);
		
		// Buttons in sichtbare Toolbar kopieren
		getTbVisibleBar().getChildrenUnmodifiable().clear();
		// getTbVisibleBar().getChildrenUnmodifiable().add(btnApply);

		// geht ev. nicht gleich, da Tab vielleicht noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				// Buttons in sichtbare Toolbar kopieren
				if (null != getTbVisibleBar()) {
					getTbVisibleBar().getItems().clear();
					getTbVisibleBar().getItems().add(getBtnBack());			// Cancel ohne Auswahl
//					getTbVisibleBar().getItems().add(getBtnSearch());		// nochmals suchen
//					getTbVisibleBar().getItems().add(getBtnReset());		// Suchkriterien loeschen
//					getTbVisibleBar().getItems().add(getBtnDetails());		// Details der Suchauswahl
					getTbVisibleBar().getItems().add(getBtnApply());		// Auswahl uebernehmen
//					getTbVisibleBar().getItems().add(getBtnNew());			// Neuen Kunden anlegen
//					getTbVisibleBar().getItems().add(getBtnCancel());
//					getTbVisibleBar().getItems().add(getBtnChange());
//					getTbVisibleBar().getItems().add(getBtnClose());
//					getTbVisibleBar().getItems().add(getBtnDelete());
//					getTbVisibleBar().getItems().add(getBtnDiscard());
//					getTbVisibleBar().getItems().add(getBtnOverwrite());
//					getTbVisibleBar().getItems().add(getBtnSave());
				}

				// Focus auf Toolbar setzen
				if (null != getTbVisibleBar()) {
					getTbVisibleBar().requestFocus();
				}
		    }
		});
	}
}
