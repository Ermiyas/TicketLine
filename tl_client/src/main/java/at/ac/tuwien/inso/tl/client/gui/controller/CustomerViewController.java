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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CustomerViewController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerViewController.class);

	// FXML Elemente
	@FXML
	private Label lbTitle;
	@FXML
	private Label lbFirstName;
	@FXML
	private Label lbLastName;
	@FXML
	private Label lbSex;
	@FXML
	private Label lbDateOfBirth;
	@FXML
	private Label lbCountry;
	@FXML
	private Label lbCity;
	@FXML
	private Label lbPostalCode;
	@FXML
	private Label lbStreet;
	@FXML
	private Label lbTelefonNumber;
	@FXML
	private Label lbEMail;
	@FXML
	private Label lbPoints;
	@FXML
	private AnchorPane apCustomerViewPane;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
				if (null != apCustomerViewPane) {
					setFields();
				}
		    }
		});

		// TODO weitere Initialisierung
	}

	/**
	 * @return the lbTitle
	 */
	public Label getLblTitle() {
		return lbTitle;
	}

	/**
	 * @return the lbFirstName
	 */
	public Label getLblFirstName() {
		return lbFirstName;
	}

	/**
	 * @return the lbLastName
	 */
	public Label getLblLastName() {
		return lbLastName;
	}

	/**
	 * @return the cbSex
	 */
	public Label getLblSex() {
		return lbSex;
	}

	/**
	 * @return the lbDateOfBirth
	 */
	public Label getLblDateOfBirth() {
		return lbDateOfBirth;
	}

	/**
	 * @return the cbCountry
	 */
	public Label getLblCountry() {
		return lbCountry;
	}

	/**
	 * @return the lbCity
	 */
	public Label getLblCity() {
		return lbCity;
	}

	/**
	 * @return the lbPostalCode
	 */
	public Label getLblPostalCode() {
		return lbPostalCode;
	}

	/**
	 * @return the lbStreet
	 */
	public Label getLblStreet() {
		return lbStreet;
	}

	/**
	 * @return the lbTelefonNumber
	 */
	public Label getLblTelefonNumber() {
		return lbTelefonNumber;
	}

	/**
	 * @return the lbEMail
	 */
	public Label getLblEMail() {
		return lbEMail;
	}

	/**
	 * @return the lbPoints
	 */
	public Label getLblPoints() {
		return lbPoints;
	}

	/**
	 * Alle Felder zuruecksetzen
	 */
	public void setFields() {
		// TODO Daten aus aktuellem (dh geladenem) DTO-Objekt lesen
		if (null != apCustomerViewPane) {
			lbTitle.setText("Hr."); 
			lbFirstName.setText("Robert"); 
			lbLastName.setText("Bekker"); 
			lbSex.setText("Männlich");
			lbDateOfBirth.setText("27.12.1963");
			lbCountry.setText("Österreich");
			lbCity.setText("Wien"); 
			lbPostalCode.setText("1060"); 
			lbStreet.setText("Millergasse 12/14"); 
			lbTelefonNumber.setText("+43 1 555 55 55"); 
			lbEMail.setText("e8325143@student.tuwien.ac.at"); 
			lbPoints.setText("1.000.000");
		}
	}
}
