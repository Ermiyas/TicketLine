package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CustomerDataAreaController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerDataAreaController.class);
	
	@FXML
	private Node parent;
	
	// FXML Elemente, direkt in dieser Form
	@FXML
    private Button btnChange;
	@FXML
    private Button btnReset;
	@FXML
    private Button btnDelete;
	@FXML
    private Button btnClose;

	// @FXML
	// private Tab tbOwnTab;
	@FXML
	private AnchorPane apCustomerDataAreaPane;				// eigenes Haupt-Pane
	@FXML
	private AnchorPane apCustomerEditPane;					// das Pane, in welches das Sub-Pane "CustomerEditBase.fxml" mit fx:include eingebunden ist 
	@Autowired												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	private CustomerEditController customerEditController;	// eingebundener Subcontroller des Sub-Panes "CustomerEditBase.fxml" 
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Sub-Controller initialisieren
		if (null != customerEditController) {
			// Daten einlesen
	    	customerEditController.resetFields();

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

			// reset Buttons
			btnChange.setDisable(true);
			btnReset.setDisable(true);
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
		// parent = apCustomerDataAreaPane.getParent().lookup("#tabPaneMain");
		// parent = apCustomerDataAreaPane.getParent().lookup("#" + BundleManager.getBundle().getString("startpage.manage_customers")); 
		// tbOwnTab = parent.getChildrenUnmodifiable();
		// System.out.println(apCustomerDataAreaPane.lookup("#tabPaneMain"));
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
		btnChange.setDisable(true);
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
			btnChange.setDisable(false);
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
	
	// TODO Methode zm DTO-Object befuellen
	
	// --- Button-Actions
	
	@FXML
	// "Ändern"-Button
    private void handleChange(ActionEvent event) {
		// TODO Felder in DTO-Object einpacken
		// TODO call Save-Service
		btnChange.setDisable(true);
		btnReset.setDisable(true);
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

		customerEditController.resetFields();
		btnChange.setDisable(true);
		btnReset.setDisable(true);
		customerEditController.getTxtTitle().requestFocus();
    }
	
	@FXML
	// "Löschen"-Button
	private void handleDelete(ActionEvent event) {
		// TODO Kunden erst nach Sicherheitsabfrage loeschen
		if (true) {
			// TODO DTO-Objekt an Loeschen-Service uebergeben
			apCustomerEditPane.setDisable(true);
			btnChange.setDisable(true);
			btnReset.setDisable(true);
			btnDelete.setDisable(true);
		}
    }
	
	@FXML
	// "Schließen"-Button
	private void handleClose(ActionEvent event) {
		// TODO uebergeordnetes Tab, Maske o.ä. schlieszen
		apCustomerDataAreaPane.getParent().visibleProperty().setValue(false);
    }
	
}
