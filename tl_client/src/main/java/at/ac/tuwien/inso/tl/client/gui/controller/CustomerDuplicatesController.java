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
public class CustomerDuplicatesController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerDuplicatesController.class);
	
	@FXML
	private Node parent;
	
	// FXML Elemente, direkt in dieser Form
	@FXML
    private Button btnSave;
	@FXML
    private Button btnBack;
	@FXML
    private Button btnOverwrite;
	@FXML
    private Button btnDiscard;
	@FXML
    private Button btnApply;

	// @FXML
	// private Tab tbOwnTab;
	@FXML
	private AnchorPane apDuplicatesFoundPane;				// eigenes Haupt-Pane
	@FXML
	private AnchorPane apCustomerEditPane;					// das Pane, in welches das Sub-Pane "CustomerEditBase.fxml" mittels fx:include eingebunden ist 
	@FXML												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	private CustomerEditController apCustomerEditPaneController;	// eingebundener Subcontroller des Sub-Panes "CustomerEditBase.fxml" 
	// @Autowired												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	// private CustomerEditController customerEditController;	// eingebundener Subcontroller des Sub-Panes "CustomerEditBase.fxml" 
	@FXML
	private AnchorPane apCustomerListPane;					// das Pane, in welches das Sub-Pane "CustomerListBase.fxml" mittels fx:include eingebunden ist 
	@Autowired												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	private CustomerListController customerListController;	// eingebundener Subcontroller des Sub-Panes "CustomerListBase.fxml" 
	@FXML
	private AnchorPane apCustomerViewPane;					// das Pane, in welches das Sub-Pane "CustomerViewBase.fxml" mittels fx:include eingebunden ist
	@Autowired												// TODO [ticket #] Autowired verbindet immer nur zum selben Controller (Singleton)
	private CustomerViewController customerViewController;	// eingebundener Subcontroller des Sub-Panes "CustomerViewBase.fxml" 
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
		Platform.runLater(new Runnable() {
		    public void run() {
		    	if (null != apCustomerEditPaneController) {
		    		apCustomerEditPaneController.resetFields();
				}
//		    	if (null != customerEditController) {
//					customerEditController.resetFields();
//				}
//				if (null != customerListController) {
//					customerListController.setTableColumns();
//				}
//				if (null != customerViewController) {
//					customerViewController.setFields();
//				}
		    }
		});

		// TODO weitere Initialisierung
	}

	// --- Button-Actions
	
	@FXML
	// "Speichern"-Button: speichert neuen Kunden als Duplikat ab
    private void handleSave(ActionEvent event) {
		// TODO 
	}
	
	@FXML
	// "Zurück"-Button: Daten des neuen Kunden aendern
    private void handleBack(ActionEvent event) {
		// TODO 
	}
	
	@FXML
	// "Überschreiben"-Button: ueberschreibt gefundenes Duplikat mit neuen Daten
    private void handleOverwrite(ActionEvent event) {
		// TODO 
	}
	
	@FXML
	// "Verwerfen"-Button: Aktion wir abgebrochen, kein neuer Kunde wird angelegt
	private void handleDiscard(ActionEvent event) {
		// TODO uebergeordnetes Tab, Maske o.ä. schlieszen
		apDuplicatesFoundPane.getParent().visibleProperty().setValue(false);
    }
	
	@FXML
	// "Übernehmen"-Button: das Duplikat wird statt der neuen daten verwendet
	private void handleApply(ActionEvent event) {
		// TODO nur fuer Tests bisherige Werte ausgeben
    }
	
}
