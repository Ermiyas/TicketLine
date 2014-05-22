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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class CustomerListBaseController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerListBaseController.class);

	@FXML
	private AnchorPane apCustomerListBasePane;		// eigenes root pane
	@FXML
	private TitledPane tpTitlePane;					// Titled Pane der Felder
	@FXML
	private TableView tvCustomersFoundView;			// Tabellen-Huelle
	@FXML
	private TableColumn tcID;						// Spalte 1
	@FXML
	private TableColumn tcTitle;					// Spalte 2
	@FXML
	private TableColumn tcFirstName;				// Spalte 3
	@FXML
	private TableColumn tcLastName;					// Spalte 4


	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		

//		// Mit Listener Geburtsdatum schon waehrend Eingabe ueberpruefen
//		txtDateOfBirth.textProperty().addListener(new ChangeListener<String>() {
//			@Override
//			public void changed(ObservableValue<? extends String> arg0, String alt, String neu) {
//				// TODO Service sollte pruefen
//	    		if (neu != null && ! neu.matches(dateRegExpr)) {
//		    		// txtDateOfBirth.setText(alt);
//				} 
//			}
//	    });
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
	 * @return the apCustomerDataPane
	 */
	public AnchorPane getApCustomerListBasePane() {
		return apCustomerListBasePane;
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
	 * @return the tvCustomersFoundView
	 */
	public TableView getTvCustomersFoundView() {
		return tvCustomersFoundView;
	}

	/**
	 * @return the tcID
	 */
	public TableColumn getTcID() {
		return tcID;
	}

	/**
	 * @return the tcTitle
	 */
	public TableColumn getTcTitle() {
		return tcTitle;
	}

	/**
	 * @return the tcFirstName
	 */
	public TableColumn getTcFirstName() {
		return tcFirstName;
	}

	/**
	 * @return the tcLastName
	 */
	public TableColumn getTcLastName() {
		return tcLastName;
	}

	/**
	 * Liste setzen
	 */
	public void setList() {
		// TODO DTO uebergeben und alle Felder setzen
		// Fuer Tests ersatzweise Daten generieren
		setTestData();
	}

	/**
	 * Liste leeren
	 */
	public void clearList() {
		// TODO DTO alle Felder loeschen
		// Fuer Tests ersatzweise Daten loeschen
		clearTestData();
	}

	/**
	 * Alle Felder zurueckgeben
	 */
	public void getList() {
		// TODO (geaenderte) Daten aus Maske auslesen und
		// in aktuellem DTO-Objekt speichern
		// Fuer Tests ersatzweise Daten ausgeben
		logTestData();
	}
	
	// TODO Testroutinen, solange es noch keine DTO-Objekte gibt
	
	// TODO Testdaten generieren
	private void setTestData() {
	}
	
	// TODO Testdaten loeschen
	private void clearTestData() {
	}
	
	// TODO Testdaten ausgeben
	private void logTestData() {
	}
}
