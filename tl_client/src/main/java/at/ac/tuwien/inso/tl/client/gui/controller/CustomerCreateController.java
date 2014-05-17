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
public class CustomerCreateController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerCreateController.class);
	// regular Expressions werden auch waehrend(!) der Eingabe ueberprueft und muessen daher ebenso unvollstaendige Zeichenketten erlauben
	// regular Expression fuer Deutsches Datums-Format: (T)T.(M)M.(JJ)JJ
	private static final String dateRegExpr = "^\\s*((([0-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))|"
			+ "((([1-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))[\\.\\/\\-](([0-9]?)|(0[1-9])|([1][0-2])))|"
			+ "((([1-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))[\\.\\/\\-]((0?[1-9])|([1][0-2]))[\\.\\/\\-]"
			+ "(([0-9]{0,2})|([0-9]{2,2}\\s*)|(((19)|(20))[0-9]{1,2})|(((19)|(20))[0-9]{2,2}\\s*))))$";
	
	@FXML
	private Node parent;
	
	// FXML Elemente, direkt in der Form
	@FXML
    private Button btnCreate;
	@FXML
    private Button btnReset;
	@FXML
    private Button btnDiscard;

	// @FXML
	// private Tab tbOwnTab;
	@FXML
	private AnchorPane apCustomerCreatePane;
	@Autowired
	private CustomerBaseController customerBaseController;
	@FXML
	private AnchorPane apCustomerBasePane;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addActionListener(customerBaseController.txtTitle);
		addActionListener(customerBaseController.txtFirstName);
		addActionListener(customerBaseController.txtLastName);
		addActionListener(customerBaseController.cbSex);
		addActionListener(customerBaseController.txtDateOfBirth);
		addActionListener(customerBaseController.cbCountry);
		addActionListener(customerBaseController.txtFirstName);
		addActionListener(customerBaseController.txtCity);
		addActionListener(customerBaseController.txtPostalCode);
		addActionListener(customerBaseController.txtStreet);
		addActionListener(customerBaseController.txtTelefonNumber);
		addActionListener(customerBaseController.txtEMail);
		// TODO: weitere Listener fuer alle Felder
		customerBaseController.txtDateOfBirth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String alt, String neu) {
	    		if (neu != null && ! neu.matches(dateRegExpr)) {
		    		customerBaseController.txtDateOfBirth.setText(alt);
				} 
			}
	    });

		// TODO weitere Initialisierung

		// TODO Test only
		// parent = apCustomerCreatePane.getParent().lookup("#tabPaneMain");
		// parent = apCustomerCreatePane.getParent().lookup("#" + BundleManager.getBundle().getString("startpage.manage_customers")); 
		// tbOwnTab = parent.getChildrenUnmodifiable();
		// System.out.println(apCustomerCreatePane.lookup("#tabPaneMain"));
		
	}

	private void addActionListener(TextField textField){
		if(null != textField){
			textField.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					enableButtons();
				}
		    });

			textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable,
						Boolean oldValue, Boolean newValue) {
					/*
					// TODO Feldinhalt von Service validieren lassen
					if (false) {
						// Rahmenfarbe aendern
						System.out.println(observable);
					}
					*/
				}
		    });
		}
	}
	
	private void addActionListener(ChoiceBox<String> choiceBox){
		if(null != choiceBox){
			choiceBox.valueProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					enableButtons();
				}
		    });
		}
		
		choiceBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable,
					Boolean oldValue, Boolean newValue) {
				/*
				// TODO Feldinhalt von Service validieren lassen
				if (false) {
					// Rahmenfarbe aendern
					System.out.println(observable);
				}
				*/
			}
	    });
	}

	private void enableButtons(){
		btnCreate.setDisable(true);
		btnReset.setDisable(true);
		if ( 	
				! checkEmpty(customerBaseController.txtTitle) ||
				! checkEmpty(customerBaseController.txtFirstName) ||
				! checkEmpty(customerBaseController.txtLastName) ||
				! checkEmpty(customerBaseController.cbSex) ||
				! checkEmpty(customerBaseController.txtDateOfBirth) ||
				! checkEmpty(customerBaseController.cbCountry) ||
				! checkEmpty(customerBaseController.txtCity) ||
				! checkEmpty(customerBaseController.txtPostalCode) ||
				! checkEmpty(customerBaseController.txtStreet) ||
				! checkEmpty(customerBaseController.txtTelefonNumber) ||
				! checkEmpty(customerBaseController.txtEMail)
			) {
			btnReset.setDisable(false);
		}
		// TODO: nicht selbst prüfen, sondern Eingaben von Service validieren
		if ( 	
				! checkEmpty(customerBaseController.txtFirstName) &&
				! checkEmpty(customerBaseController.txtLastName) && (
					! checkEmpty(customerBaseController.txtTitle) ||
					! checkEmpty(customerBaseController.cbSex) ||
					! checkEmpty(customerBaseController.txtDateOfBirth) ||
					! checkEmpty(customerBaseController.cbCountry) ||
					! checkEmpty(customerBaseController.txtCity) ||
					! checkEmpty(customerBaseController.txtPostalCode) ||
					! checkEmpty(customerBaseController.txtStreet) ||
					! checkEmpty(customerBaseController.txtTelefonNumber) ||
					! checkEmpty(customerBaseController.txtEMail)
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
	
	@FXML
    void handleCreate(ActionEvent event) {
		// TODO call Save-Service
		apCustomerBasePane.setDisable(true);
		btnCreate.setDisable(true);
		btnReset.setDisable(true);
		btnDiscard.setText(BundleManager.getBundle().getString("customerpage.close"));
	}
	@FXML
    void handleReset(ActionEvent event) {
		// TODO
		customerBaseController.resetFields();
    }
	@FXML
    void handleDiscard(ActionEvent event) {
		// TODO uebergeordnetes Tab, Maske o.ä. schlieszen
		apCustomerCreatePane.getParent().visibleProperty().setValue(false);
    }
	
}
