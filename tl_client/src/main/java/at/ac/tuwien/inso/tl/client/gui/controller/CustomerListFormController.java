/**
 * Sample Skeleton for "CustomerListForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import javafx.fxml.Initializable;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class CustomerListFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerListFormController.class);

	// FXML-injizierte Variablen

	@Autowired
	private CustomerService customerService;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    

    @FXML // fx:id="apCustomerListForm"
    private AnchorPane apCustomerListForm;				// eigenes Root-Pane

    @FXML // fx:id="tcID"
    private TableColumn<CustomerDto, Integer> tcID;

    @FXML // fx:id="tcTitle"
    private TableColumn<CustomerDto, String> tcTitle;

    @FXML // fx:id="tcFirstName"
    private TableColumn<CustomerDto, String> tcFirstName;

    @FXML // fx:id="tcLastName"
    private TableColumn<CustomerDto, String> tcLastName;

    @FXML // fx:id="tpCustomerListPane"
    private TitledPane tpCustomerListPane;

    @FXML // fx:id="tvCustomersListView"
    private TableView<CustomerDto> tvCustomersListView;
	

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
		assert customerService != null : "fx:id=\"customerService\" was not injected: check your Interface-file 'customerService.java'.";
        assert resources != null : "fx:id=\"resources\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert location != null : "fx:id=\"location\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert apCustomerListForm != null : "fx:id=\"apCustomerListForm\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcFirstName != null : "fx:id=\"tcFirstName\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcID != null : "fx:id=\"tcID\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcLastName != null : "fx:id=\"tcLastName\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcTitle != null : "fx:id=\"tcTitle\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tpCustomerListPane != null : "fx:id=\"tpCustomerListPane\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tvCustomersListView != null : "fx:id=\"tvCustomersListView\" was not injected: check your FXML file 'CustomerListForm.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        // ------------------------------------
        
//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				LOG.info("");
//        
//				if (null != apCustomerDataPane) {
//        
//					// TODO restliche Initialisierung
//		
//				}
//		    }
//		});

		// keine weiteren leeren Spalten anzeigen
		tvCustomersListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// TODO restliche Initialisierung

	}

	/**
	 * @return the tcFirstName
	 */
	public TableColumn<CustomerDto, String> getTcFirstName() {
		return tcFirstName;
	}

	/**
	 * @return the tcID
	 */
	public TableColumn<CustomerDto, Integer> getTcID() {
		return tcID;
	}

	/**
	 * @return the tcLastName
	 */
	public TableColumn<CustomerDto, String> getTcLastName() {
		return tcLastName;
	}

	/**
	 * @return the tcTitle
	 */
	public TableColumn<CustomerDto, String> getTcTitle() {
		return tcTitle;
	}

	/**
	 * @return the tpCustomerListPane
	 */
	public TitledPane getTpCustomerListPane() {
		return tpCustomerListPane;
	}

	/**
	 * @return the tvCustomersListView
	 */
	public TableView<CustomerDto> getTvCustomersListView() {
		return tvCustomersListView;
	}

	/**
	 * Titel der TitlePane sprachabhaengig setzen
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		LOG.info("");
		
		tpCustomerListPane.setText(intString(title));
	}

	/**
	 * Zeilen loeschen
	 */
	public void setList() {
		LOG.info("");
		
		setList(null);
	}

	/**
	 * Zeilen setzen
	 * 
	 * @param customerDtoList
	 */
	public void setList(List<CustomerDto> customerDtoList) {
		LOG.info("");
		
		// TODO DTO uebernehmen und alle Felder setzen
		
		// Tabelle loeschen
		tvCustomersListView.getItems().clear();
		
		if (customerDtoList != null) {
			// Listen-Daten laden
			ObservableList<CustomerDto> customers = FXCollections.observableArrayList(customerDtoList);
			
			// Daten in Spalten laden
			tcID.setCellValueFactory(new PropertyValueFactory<CustomerDto, Integer>("Id"));					// "Id" -> call 'CustomerDto.getId()'
			tcTitle.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("title"));			// "title" -> call 'CustomerDto.getTitle()'
			tcFirstName.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstname"));	// "firstname" -> call 'CustomerDto.getFirstname()'
			tcLastName.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastname"));		// "lastname" -> call 'CustomerDto.getLastname()'

			// Daten in Tabelle schreiben
			tvCustomersListView.setItems(customers);
		}
		
		// TODO Fuer Tests ersatzweise Daten generieren
		// setTestData();
	}

	/**
	 * Alle Zeilen in neuer Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<CustomerService> getList() {
		LOG.info("");
		
		List<CustomerService> customerDtoList = new ArrayList<CustomerService>();
		getList(customerDtoList);
		return customerDtoList;
	}
	
	/**
	 * Alle Zeilen in bestehender Liste zurueckgeben
	 * 
	 * @param customerDtoList
	 */
	public void getList(List<CustomerService> customerDtoList) {
		LOG.info("");
		
		// TODO (geaenderte) Daten aus Maske auslesen und
		// in aktuellem DTO-Objekt speichern

		// TODO Fuer Tests ersatzweise Daten ausgeben
		logTestData();
	}
	
	public CustomerService getCustomer() {
		LOG.info("");
		
//		tvCustomersListView.getSelectionModel().getSelectedItem();
		// TODO ausgewaehlte Zeile zurueckgeben
		return null;
	}
	
	// TODO Testroutinen, solange es noch keine DTO-Objekte gibt
	
	/**
	 * Liste leeren
	 */
	private void clearList() {
		LOG.info("");
		
		// TODO DTO alle Felder loeschen
		// Fuer Tests ersatzweise Daten loeschen
		clearTestData();
	}

	// TODO Testdaten generieren
	private void setTestData() {
		LOG.info("");
		
	}
	
	// TODO Testdaten loeschen
	private void clearTestData() {
		LOG.info("");
		
	}
	
	// TODO Testdaten ausgeben
	private void logTestData() {
		LOG.info("");
		
	}

	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, NULL in "" uebersetzen
	 * 
	 * @param title
	 */
	private static String intString(String text) {
		LOG.info("");
		return intString(text, true);
	}
	
	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, default NULL in "" uebersetzen
	 * 
	 * @param title
	 */
	private static String intString(String text, Boolean noNull) {
		LOG.info("");
		
		if (noNull == null) {
			noNull = true;
		}
		if (text == null && ! noNull) {
			return null;
		}
		if (text == null || text.trim().equals("")) {
			return "";
		}
		try {
			return BundleManager.getBundle().getString(text).trim();
		} catch (RuntimeException e) {
			return text.trim();
		}
	}
}
