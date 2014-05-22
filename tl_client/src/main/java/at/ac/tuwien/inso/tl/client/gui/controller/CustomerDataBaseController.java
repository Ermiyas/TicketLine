package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.util.BundleManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class CustomerDataBaseController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerDataBaseController.class);

    // Eingaben sollten schon waehrend(!) der Eingabe ueberprueft werden,
	// daher muessen regular Expressions ebenso unvollstaendige, aber bis dahin richtige Zeichenketten erlauben
	
	// regular Expression fuer Deutsches Datums-Format: (T)T.(M)M.(JJ)JJ, 4-stelliges Jahr moeglich fuer 1900-2099
	private static final String dateRegExpr = "^\\s*((([0-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))|"
			+ "((([1-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))[\\.\\/\\-](([0-9]?)|(0[1-9])|([1][0-2])))|"
			+ "((([1-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))[\\.\\/\\-]((0?[1-9])|([1][0-2]))[\\.\\/\\-]"
			+ "(([0-9]{0,2})|([0-9]{2,2}\\s*)|(((19)|(20))[0-9]{1,2})|(((19)|(20))[0-9]{2,2}\\s*))))$";

	// FXML Elemente
	@FXML
	private TextField txtTitle;
	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtLastName;
	@FXML
	private ChoiceBox<String> cbSex;
	@FXML
	private TextField txtDateOfBirth;
	@FXML
	private TextField txtCountry;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtPostalCode;
	@FXML
	private TextField txtStreet;
	@FXML
	private TextField txtTelefonNumber;
	@FXML
	private TextField txtEMail;
	@FXML
	private TextField txtPoints;
	@FXML
	private Label lbPoints;
	@FXML
	private AnchorPane apCustomerDataBasePane;		// eigenes root pane
	@FXML
	private TitledPane tpTitlePane;					// Titled Pane der Felder

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
		// Geschlechts-Liste setzen
		cbSex.getItems().clear();
		cbSex.getItems().add(BundleManager.getBundle().getString("customerpage.male"));
		cbSex.getItems().add(BundleManager.getBundle().getString("customerpage.female"));

		// Mit Listener Geburtsdatum schon waehrend Eingabe ueberpruefen
		txtDateOfBirth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String alt, String neu) {
				// TODO Service sollte pruefen
	    		if (neu != null && ! neu.matches(dateRegExpr)) {
		    		txtDateOfBirth.setText(alt);
				} 
			}
	    });
		// TODO: ev. noch weitere Listener fuer Syntaxpruefung weiterer Felder einbauen

//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				if (null != apCustomerDataPane) {
//					// Focus auf 1. Feld setzen
//					getTxtTitle().requestFocus();							
//
//					// TODO restliche Initialisierung
//		
////					// TODO Test only
////					setFields();
//				}
//		    }
//		});

		// TODO restliche Initialisierung
		
//		// TODO Test only
//		setFields();
	}

	/**
	 * @return the txtTitle
	 */
	public TextField getTxtTitle() {
		return txtTitle;
	}

	/**
	 * @return the txtFirstName
	 */
	public TextField getTxtFirstName() {
		return txtFirstName;
	}

	/**
	 * @return the txtLastName
	 */
	public TextField getTxtLastName() {
		return txtLastName;
	}

	/**
	 * @return the cbSex
	 */
	public ChoiceBox<String> getCbSex() {
		return cbSex;
	}

	/**
	 * @return the txtDateOfBirth
	 */
	public TextField getTxtDateOfBirth() {
		return txtDateOfBirth;
	}

	/**
	 * @return the cbCountry
	 */
	public TextField getTxtCountry() {
		return txtCountry;
	}

	/**
	 * @return the txtCity
	 */
	public TextField getTxtCity() {
		return txtCity;
	}

	/**
	 * @return the txtPostalCode
	 */
	public TextField getTxtPostalCode() {
		return txtPostalCode;
	}

	/**
	 * @return the txtStreet
	 */
	public TextField getTxtStreet() {
		return txtStreet;
	}

	/**
	 * @return the txtTelefonNumber
	 */
	public TextField getTxtTelefonNumber() {
		return txtTelefonNumber;
	}

	/**
	 * @return the txtEMail
	 */
	public TextField getTxtEMail() {
		return txtEMail;
	}

	/**
	 * @return the txtPoints
	 */
	public TextField getTxtPoints() {
		return txtPoints;
	}

	/**
	 * @return the lbPoints
	 */
	public Label getLbPoints() {
		return lbPoints;
	}

	/**
	 * @return the apCustomerDataPane
	 */
	public AnchorPane getApCustomerDataBasePane() {
		return apCustomerDataBasePane;
	}

	/**
	 * @return the tpTitlePane
	 */
	public TitledPane getTpTitlePane() {
		return tpTitlePane;
	}

	/**
	 * 
	 */
	public void setTitle(String title) {
		if (title.charAt(0) == '%') {
			tpTitlePane.setText(BundleManager.getBundle().getString(title.substring(1)));
		} else {
			tpTitlePane.setText(title);
		}
	}

	/**
	 * Alle Felder setzen
	 */
	public void setFields() {
		// TODO DTO uebergeben und alle Felder setzen
		// Fuer Tests ersatzweise Daten generieren
		setTestData();
	}

	/**
	 * Alle Felder leeren
	 */
	public void clearFields() {
		// TODO DTO alle Felder loeschen
		// Fuer Tests ersatzweise Daten loeschen
		clearTestData();
	}

	/**
	 * Alle Felder zurueckgeben
	 */
	public void getFields() {
		// TODO (geaenderte) Daten aus Maske auslesen und
		// in aktuellem DTO-Objekt speichern
		// Fuer Tests ersatzweise Daten ausgeben
		logTestData();
	}
	
	/**
	 * @param editable
	 */
	public void setAllFieldsEditable(Boolean editable) {
		txtTitle.setEditable(editable); 
		txtFirstName.setEditable(editable); 
		txtLastName.setEditable(editable); 
		cbSex.setDisable(! editable);
		txtDateOfBirth.setEditable(editable);
		txtCountry.setEditable(editable);
		txtCity.setEditable(editable); 
		txtPostalCode.setEditable(editable); 
		txtStreet.setEditable(editable); 
		txtTelefonNumber.setEditable(editable); 
		txtEMail.setEditable(editable); 
		txtPoints.setEditable(editable); 
	}

	// TODO Testroutinen, solange es noch keine DTO-Objekte gibt
	
	// TODO Testdaten generieren
	private void setTestData() {
		txtTitle.setText("Hr."); 
		txtFirstName.setText("Robert"); 
		txtLastName.setText("Bekker"); 
		cbSex.getSelectionModel().clearSelection();
		cbSex.setValue("Männlich");
		txtDateOfBirth.setText("27.12.1963");
		txtCountry.setText("Österreich");
		txtCity.setText("Wien"); 
		txtPostalCode.setText("1060"); 
		txtStreet.setText("Millergasse 12/14"); 
		txtTelefonNumber.setText("+43 1 555 55 55"); 
		txtEMail.setText("e8325143@student.tuwien.ac.at"); 
		txtPoints.setText("1.000.000"); 
	}
	
	// TODO Testdaten loeschen
	private void clearTestData() {
		txtTitle.setText(""); 
		txtFirstName.setText(""); 
		txtLastName.setText(""); 
		cbSex.getSelectionModel().clearSelection();
		cbSex.setValue("");
		txtDateOfBirth.setText("");
		txtCountry.setText("");
		txtCity.setText(""); 
		txtPostalCode.setText(""); 
		txtStreet.setText(""); 
		txtTelefonNumber.setText(""); 
		txtEMail.setText(""); 
		txtPoints.setText(""); 
	}
	
	// TODO Testdaten ausgeben
	private void logTestData() {
		LOG.debug(txtTitle.getText()); 
		LOG.debug(txtFirstName.getText()); 
		LOG.debug(txtLastName.getText()); 
		LOG.debug(cbSex.getValue()); 
		LOG.debug(txtDateOfBirth.getText()); 
		LOG.debug(txtCountry.getText()); 
		LOG.debug(txtCity.getText()); 
		LOG.debug(txtPostalCode.getText()); 
		LOG.debug(txtStreet.getText()); 
		LOG.debug(txtTelefonNumber.getText()); 
		LOG.debug(txtEMail.getText()); 
		LOG.debug(txtPoints.getText()); 
	}
}
