package at.ac.tuwien.inso.tl.client.gui.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.NewsPane;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CustomerCreateController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerCreateController.class);
	
	// FXML Elemente, direkt in der Haupt-Form
	@FXML
    private Button btnCreate;
	@FXML
    private Button btnReset;
	@FXML
    private Button btnDiscard;

	// @FXML
	// private Tab tbOwnTab;
	@FXML
	private AnchorPane apCustomerCreatePane;				// eigenes Haupt-Pane
	@FXML
	private BorderPane bpCustomerCreatePane;				// Create-SubPane
	@FXML
	private AnchorPane apCustomerEditPane;					// das Pane, in welches das Sub-Pane "CustomerEditBase.fxml" mittels fx:include eingebunden ist 
	@FXML												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	private CustomerEditController apCustomerEditPaneController;	// eingebundener Subcontroller des Sub-Panes "CustomerEditBase.fxml" 
	@Autowired												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	private CustomerEditController customerEditController;	// eingebundener Subcontroller des Sub-Panes "CustomerEditBase.fxml" 
	@FXML
	private BorderPane bpCustomerDuplicatePane;				// Duplicate-SubPane
	@FXML
	private AnchorPane apCustomerDuplicatePane;				// das Pane, in welches das Sub-Pane "CustomerDuplicatesFoundForm.fxml" mittels fx:include eingebunden ist
	// TODO eindeutigen Controller einbinden
	// private CustomerEditController myCustomerEditController;
	
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        
		// Sub-Controller initialisieren
		if (null != customerEditController) {
			// auf Eingaben sofort reagieren
			addChangeActionListener(customerEditController.getTxtTitle());
			addChangeActionListener(customerEditController.getTxtFirstName());
			addChangeActionListener(customerEditController.getTxtLastName());
			addChangeActionListener(customerEditController.getCbSex());
			addChangeActionListener(customerEditController.getTxtDateOfBirth());
			addChangeActionListener(customerEditController.getCbCountry());
			addChangeActionListener(customerEditController.getTxtFirstName());
			addChangeActionListener(customerEditController.getTxtCity());
			addChangeActionListener(customerEditController.getTxtPostalCode());
			addChangeActionListener(customerEditController.getTxtStreet());
			addChangeActionListener(customerEditController.getTxtTelefonNumber());
			addChangeActionListener(customerEditController.getTxtEMail());
			addChangeActionListener(customerEditController.getTxtPoints());

			// Visibility anpassen
	    	customerEditController.getTxtPoints().setVisible(false);
	    	customerEditController.getLblPoints().setVisible(false);
		}

		// TODO weitere Initialisierung
    	
		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				if (null != customerEditController) {
			    	customerEditController.getTxtTitle().requestFocus();
				}
		    }
		});
		
		// TODO Test only!!!
		// Problem: wie greife ich auf den uebergeordneten Controller zu, um das eigene Tab schlieszen zu koennen
		//
		// parent = apCustomerCreatePane.getParent().lookup("#tabPaneMain");
		// parent = apCustomerCreatePane.getParent().lookup("#" + BundleManager.getBundle().getString("startpage.manage_customers")); 
		// tbOwnTab = parent.getChildrenUnmodifiable();
		// System.out.println(apCustomerCreatePane.lookup("#tabPaneMain"));
	}

	private void addChangeActionListener(TextField textField){
		if(null != textField){
			// ueberpruefen, ob durch aktuelle Aenderung Buttons "Create" u. "Reset" enabled werden sollen
			textField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					enableButtons();
				}
		    });

			/*
			// ueberpruefen, ob Feldinhalt korrekt ist und ev. zB Feldrahmenfarbe aendern
			textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean oldValue, Boolean newValue) {
					// TODO Feldinhalt von Service validieren lassen
					if (false) {
						// zB Rahmenfarbe aendern
						LOG.debug(observable);
					}
				}
		    });
			*/
		}
	}
	
	private void addChangeActionListener(ChoiceBox<String> choiceBox){
		if(null != choiceBox){
			// ueberpruefen, ob durch aktuelle Aenderung Buttons "Create" u. "Reset" enabled werden sollen
			choiceBox.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					enableButtons();
				}
		    });

			/*
			// ueberpruefen, ob Feldinhalt korrekt ist und ev. zB Feldrahmenfarbe aendern
			choiceBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean oldValue, Boolean newValue) {
					// TODO Feldinhalt von Service validieren lassen
					if (false) {
						// zB Rahmenfarbe aendern
						LOG.debug(observable);
					}
				}
		    });
			*/
		}
	}

	private void enableButtons(){
		// beide Buttons erst einmal disablen
		btnCreate.setDisable(true);
		btnReset.setDisable(true);
		// falls irgend ein Feld geaendert wurde sofort Reset-Button enablen
		if ( 	
				! checkEmpty(customerEditController.getTxtTitle()) ||
				! checkEmpty(customerEditController.getTxtFirstName()) ||
				! checkEmpty(customerEditController.getTxtLastName()) ||
				! checkEmpty(customerEditController.getCbSex()) ||
				! checkEmpty(customerEditController.getTxtDateOfBirth()) ||
				! checkEmpty(customerEditController.getCbCountry()) ||
				! checkEmpty(customerEditController.getTxtCity()) ||
				! checkEmpty(customerEditController.getTxtPostalCode()) ||
				! checkEmpty(customerEditController.getTxtStreet()) ||
				! checkEmpty(customerEditController.getTxtTelefonNumber()) ||
				! checkEmpty(customerEditController.getTxtEMail()) ||
				! checkEmpty(customerEditController.getTxtPoints())
			) {
			btnReset.setDisable(false);
		}
		// falls die bisher eingegebenen Daten ausreichen, und korrekt sind,
		// "Create"-Button enablen
		// TODO DTO-Object befuellen und dieses von Service validieren lassen
		if ( 	
				// TODO: nicht selbst prüfen, sondern Eingaben von Service validieren
				! checkEmpty(customerEditController.getTxtFirstName()) &&
				! checkEmpty(customerEditController.getTxtLastName()) && (
					! checkEmpty(customerEditController.getTxtTitle()) ||
					! checkEmpty(customerEditController.getCbSex()) ||
					! checkEmpty(customerEditController.getTxtDateOfBirth()) ||
					! checkEmpty(customerEditController.getCbCountry()) ||
					! checkEmpty(customerEditController.getTxtCity()) ||
					! checkEmpty(customerEditController.getTxtPostalCode()) ||
					! checkEmpty(customerEditController.getTxtStreet()) ||
					! checkEmpty(customerEditController.getTxtTelefonNumber()) ||
					! checkEmpty(customerEditController.getTxtEMail()) ||
					! checkEmpty(customerEditController.getTxtPoints())
				)
			) {
			btnCreate.setDisable(false);
		}
	}
	
	private boolean checkEmpty(TextField textField) {
		if (null == textField) return true;
		if (null == textField.textProperty()) return true;
		if (null == textField.textProperty().getValue()) return true;
		if (textField.textProperty().getValue().trim().equals("")) return true;
		return false;
	}
	
	private boolean checkEmpty(ChoiceBox<String> choiceBox) {
		if (null == choiceBox) return true;
		if (null == choiceBox.getValue()) return true;
		if (choiceBox.getValue().trim().equals("")) return true;
		return false;
	}
	
	// TODO DTO-Object befuellen
	
	// --- Button-Actions
	
	@FXML
	// "Speichern"-Button
    private void handleCreate(ActionEvent event) {
		// TODO Felder in DTO-Object einpacken
		// TODO call Duplicate-Check
		if (false) {
			// TODO handle Duplicate
			bpCustomerCreatePane.setVisible(false);
			bpCustomerDuplicatePane.setVisible(true);
			// TODO aktuelle Daten uebernehmen
		} else {
			// TODO call Save-Service
			apCustomerEditPane.setDisable(true);
			btnCreate.setDisable(true);
			btnReset.setDisable(true);
			btnDiscard.setText(BundleManager.getBundle().getString("customerpage.close"));
		}
	}
	
	@FXML
	// "Zurücksetzen"-Button
	private void handleReset(ActionEvent event) {
		// TODO nur fuer Tests bisherige Werte ausgeben
		LOG.debug("\t------------------------------------------------------------------------------");
		LOG.debug("\tTitel:        '" + customerEditController.getTxtTitle().getText() + "'");
		LOG.debug("\tVorname:      '" + customerEditController.getTxtFirstName().getText() + "'");
		LOG.debug("\tNachname:     '" + customerEditController.getTxtLastName().getText() + "'");
		LOG.debug("\tGeschlecht:   '" + customerEditController.getCbSex().getValue() + "'");
		LOG.debug("\tGeboren:      '" + customerEditController.getTxtDateOfBirth().getText() + "'");
		LOG.debug("\tLand:         '" + customerEditController.getCbCountry().getValue() + "'");
		LOG.debug("\tStadt:        '" + customerEditController.getTxtCity().getText() + "'");
		LOG.debug("\tPostleitzahl: '" + customerEditController.getTxtPostalCode().getText() + "'");
		LOG.debug("\tStraße:       '" + customerEditController.getTxtStreet().getText() + "'");
		LOG.debug("\tTelefon:      '" + customerEditController.getTxtTelefonNumber().getText() + "'");
		LOG.debug("\tEMail:        '" + customerEditController.getTxtEMail().getText() + "'");
		LOG.debug("\tPunkte:       '" + customerEditController.getTxtPoints().getText() + "'");
		LOG.debug("\t------------------------------------------------------------------------------");

		customerEditController.clearFields();
		customerEditController.getTxtTitle().requestFocus();
    }
	
	@FXML
	// "Verwerfen"-Button
	private void handleDiscard(ActionEvent event) {
		// TODO uebergeordnetes Tab, Maske o.ä. schlieszen
		apCustomerCreatePane.getParent().visibleProperty().setValue(false);
    }
	
}
