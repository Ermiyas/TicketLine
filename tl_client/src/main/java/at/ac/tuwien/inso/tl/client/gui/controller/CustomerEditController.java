package at.ac.tuwien.inso.tl.client.gui.controller;

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
import at.ac.tuwien.inso.tl.dto.NewsDto;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CustomerEditController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerEditController.class);

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
	private ChoiceBox<String> cbCountry;
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
	private AnchorPane apCustomerEditPane;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(null != cbSex) {
			cbSex.getItems().clear();
			cbSex.getItems().add(BundleManager.getBundle().getString("customerpage.male"));
			cbSex.getItems().add(BundleManager.getBundle().getString("customerpage.female"));
		}
		if(null != cbCountry) {
			cbCountry.getItems().clear();
			// TODO Country-Liste von Service lesen und neu setzen, 
		}

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
		// TODO: weitere Listener fuer Syntax weiterer Felder

//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				if (null != apCustomerEditPane) {
//					clearFields();
//				}
//		    }
//		});

		// TODO restliche Initialisierung
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
	public ChoiceBox<String> getCbCountry() {
		return cbCountry;
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
	 * @return the lblPoints
	 */
	public Label getLblPoints() {
		return lbPoints;
	}

	/**
	 * Alle Felder leeren
	 */
	public void clearFields() {
		txtTitle.setText(""); 
		txtFirstName.setText(""); 
		txtLastName.setText(""); 
		cbSex.getSelectionModel().clearSelection();
		cbSex.setValue("");
		txtDateOfBirth.setText("");
		cbCountry.getSelectionModel().clearSelection();
		cbCountry.setValue("");
		txtCity.setText(""); 
		txtPostalCode.setText(""); 
		txtStreet.setText(""); 
		txtTelefonNumber.setText(""); 
		txtEMail.setText(""); 
		txtPoints.setText(""); 
	}

	/**
	 * Alle Felder zuruecksetzen
	 */
	public void resetFields() {
		// TODO Daten aus aktuellem (dh geladenem/gespeichertem) DTO-Objekt lesen
		txtTitle.setText("Hr."); 
		txtFirstName.setText("Robert"); 
		txtLastName.setText("Bekker"); 
		cbSex.getSelectionModel().clearSelection();
		cbSex.setValue("Männlich");
		txtDateOfBirth.setText("27.12.1963");
		cbCountry.getSelectionModel().clearSelection();
		cbCountry.setValue("Österreich");
		txtCity.setText("Wien"); 
		txtPostalCode.setText("1060"); 
		txtStreet.setText("Millergasse 12/14"); 
		txtTelefonNumber.setText("+43 1 555 55 55"); 
		txtEMail.setText("e8325143@student.tuwien.ac.at"); 
		txtPoints.setText("1.000.000"); 
	}
}
