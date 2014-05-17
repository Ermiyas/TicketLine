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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CustomerBaseController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerBaseController.class);
	
	// keine "private" FXML-Elemente, um im uebergeordnetem Controller Listener setzen zu koennen 
	
	// FXML Elemente
	@FXML
	 TextField txtTitle;
	@FXML
	 TextField txtFirstName;
	@FXML
	 TextField txtLastName;
	@FXML
	 ChoiceBox<String> cbSex;
	@FXML
	 TextField txtDateOfBirth;
	@FXML
	 ChoiceBox<String> cbCountry;
	@FXML
	 TextField txtCity;
	@FXML
	 TextField txtPostalCode;
	@FXML
	 TextField txtStreet;
	@FXML
	 TextField txtTelefonNumber;
	@FXML
	 TextField txtEMail;
	@FXML
	 TextField txtPoints;

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

	public void resetFields() {
		txtTitle.setText(""); 
		txtFirstName.setText(""); 
		txtLastName.setText(""); 
		cbSex.setValue("");
		cbSex.getSelectionModel().clearSelection();
		txtDateOfBirth.setText("");
		cbCountry.setValue("");
		cbCountry.getSelectionModel().clearSelection();
		txtCity.setText(""); 
		txtPostalCode.setText(""); 
		txtStreet.setText(""); 
		txtTelefonNumber.setText(""); 
		txtEMail.setText(""); 
	}
}
