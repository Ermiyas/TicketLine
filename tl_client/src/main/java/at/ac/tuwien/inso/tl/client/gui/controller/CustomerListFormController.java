/**
 * Sample Skeleton for "CustomerListForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

//import at.ac.tuwien.inso.tl.client.client.CustomerService;
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

	// Auch unvollständige Jahresangaben ermoeglichen
//	private static final String SHORT_DATE_FORMAT = "d.M.yy";
	private static final String LONG_DATE_FORMAT = "dd.MM.yyyy";

	private static enum SexModes {
		FEMALE {
			@Override public String toString() {
				LOG.info("");
		        return intString("customerpage.female");
		    }
		    public boolean isFemale = true;
		},
		MALE {
			@Override public String toString() {
				LOG.info("");
		        return intString("customerpage.male");
		    }
		    public boolean isFemale = false;
		};
		
		@Override public String toString() {
			LOG.info("");
			
			return this.toString();
		} 

		public static String sexToString(Boolean isFemale) {
			LOG.info("");
			if (isFemale == null) {
				return null;
			}
			if (isFemale) {
				return FEMALE.toString();
			}
			return MALE.toString();
		} 
	}

	// FXML-injizierte Variablen

//	@Autowired private CustomerService customerService;				// Kunden-Service
    @FXML private ResourceBundle resources;							// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;										// URL location of the FXML file that was given to the FXMLLoader

    @FXML private AnchorPane apCustomerListForm;					// fx:id="apCustomerListForm"		// eigenes Root-Pane
    @FXML private TableColumn<CustomerDto, Integer> tcID;			// fx:id="tcID"
    @FXML private TableColumn<CustomerDto, String> tcCity;			// fx:id="tcCity"
    @FXML private TableColumn<CustomerDto, String> tcCountry;		// fx:id="tcCountry"
    @FXML private TableColumn<CustomerDto, String> tcDateOfBirth;	// fx:id="tcDateOfBirth"
    @FXML private TableColumn<CustomerDto, String> tcEMail;			// fx:id="tcEMail"
    @FXML private TableColumn<CustomerDto, String> tcFirstName;		// fx:id="tcFirstName"
    @FXML private TableColumn<CustomerDto, String> tcLastName;		// fx:id="tcLastName"
    @FXML private TableColumn<CustomerDto, String> tcPoints;		// fx:id="tcPoints"
    @FXML private TableColumn<CustomerDto, String> tcPostalCode;	// fx:id="tcPostalCode"
    @FXML private TableColumn<CustomerDto, String> tcSex;			// fx:id="tcSex"
    @FXML private TableColumn<CustomerDto, String> tcStreet;		// fx:id="tcStreet"
    @FXML private TableColumn<CustomerDto, String> tcTelefonNumber;	// fx:id="tcTelefonNumber"
    @FXML private TableColumn<CustomerDto, String> tcTitle;			// fx:id="tcTitle"
    @FXML private TitledPane tpCustomerListPane;					// fx:id="tpCustomerListPane"
    @FXML private TableView<CustomerDto> tvCustomersListView;		// fx:id="tvCustomersListView"
	

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
//		assert customerService != null : "\"customerService\" was not injected: check your Interface-file 'CustomerService.java'.";
        assert resources != null : "\"resources\" was not injected: check your Controller-file 'CustomerListFormController.java'.";
        assert location != null : "\"location\" was not injected: check your Controller-file 'CustomerListFormController.java'.";
        assert apCustomerListForm != null : "fx:id=\"apCustomerListForm\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcCity != null : "fx:id=\"tcCity\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcCountry != null : "fx:id=\"tcCountry\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcDateOfBirth != null : "fx:id=\"tcDateOfBirth\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcEMail != null : "fx:id=\"tcEMail\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcFirstName != null : "fx:id=\"tcFirstName\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcID != null : "fx:id=\"tcID\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcLastName != null : "fx:id=\"tcLastName\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcPoints != null : "fx:id=\"tcPoints\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcPostalCode != null : "fx:id=\"tcPostalCode\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcSex != null : "fx:id=\"tcSex\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcStreet != null : "fx:id=\"tcStreet\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
        assert tcTelefonNumber != null : "fx:id=\"tcTelefonNumber\" was not injected: check your FXML file 'CustomerListForm.fxml'.";
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
//		tvCustomersListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tvCustomersListView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

		// TODO restliche Initialisierung

	}

	// Getter aller Tabellenspalten, ...
    public TableColumn<CustomerDto, Integer> getTcID()			    { return tcID; }
    public TableColumn<CustomerDto, String> getTcCity()			    { return tcCity; }
    public TableColumn<CustomerDto, String> getTcCountry()		    { return tcCountry; }
    public TableColumn<CustomerDto, String> getTcDateOfBirth()	    { return tcDateOfBirth; }
    public TableColumn<CustomerDto, String> getTcEMail()			{ return tcEMail; }
    public TableColumn<CustomerDto, String> getTcFirstName()	    { return tcFirstName; }
    public TableColumn<CustomerDto, String> getTcLastName()	    	{ return tcLastName; }
    public TableColumn<CustomerDto, String> getTcPoints()		    { return tcPoints; }
    public TableColumn<CustomerDto, String> getTcPostalCode()	    { return tcPostalCode; }
    public TableColumn<CustomerDto, String> getTcSex()			    { return tcSex; }
    public TableColumn<CustomerDto, String> getTcStreet()		    { return tcStreet; }
    public TableColumn<CustomerDto, String> getTcTelefonNumber()	{ return tcTelefonNumber; }
    public TableColumn<CustomerDto, String> getTcTitle()		    { return tcTitle; }
    public TitledPane getTpCustomerListPane() 						{ return tpCustomerListPane; }
	public TableView<CustomerDto> getTvCustomersListView() 			{ return tvCustomersListView; }

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
	 * Liste leeren
	 */
	public void clearList() {
		LOG.info("");
		
		// DTO alle Felder loeschen
		setList();
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
		
		// DTO uebernehmen und alle Felder setzen
		
		// Tabelle loeschen
		tvCustomersListView.getItems().clear();
		
		if (customerDtoList != null) {
			// Listen-Daten laden
			ObservableList<CustomerDto> customers = FXCollections.observableArrayList(customerDtoList);
			
			// Daten in Spalten laden
			tcID.setCellValueFactory(new PropertyValueFactory<CustomerDto, Integer>("Id"));							// "Id" -> call 'CustomerDto.getId()'
			tcTitle.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("title"));					// "title" -> call 'CustomerDto.getTitle()'
			tcFirstName.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("firstname"));			// "firstname" -> call 'CustomerDto.getFirstname()'
			tcLastName.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("lastname"));				// "lastname" -> call 'CustomerDto.getLastname()'
			// TODO Datum umformatieren
//			tcDateOfBirth.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("dateOfBirth"));		// "dateOfBirth" -> call 'CustomerDto.getDateOfBirth()'
	        tcDateOfBirth.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomerDto, String>, ObservableValue<String>>() {
					@Override
				    public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomerDto, String> tcBirthday) {
				         SimpleStringProperty property = new SimpleStringProperty();
				         DateFormat dateFormat = new SimpleDateFormat(LONG_DATE_FORMAT);
				         property.setValue(dateFormat.format(tcBirthday.getValue().getDateOfBirth()));
				         return property;
				    }
				}
	        );
			// TODO Geschlecht umformatieren
//			tcSex.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("isFemale"));					// "isFemale" -> call 'CustomerDto.getIsFemale()'
			tcSex.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<CustomerDto, String>, ObservableValue<String>>() {
					@Override
				    public ObservableValue<String> call(TableColumn.CellDataFeatures<CustomerDto, String> tcGeschlecht) {
				         SimpleStringProperty property = new SimpleStringProperty();
				         property.setValue(SexModes.sexToString(tcGeschlecht.getValue().getIsFemale()));
				         return property;
				    }
				}
	        );
			tcPostalCode.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("postalcode"));			// "postalcode" -> call 'CustomerDto.getPostalcode()'
			tcCity.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("city"));						// "city" -> call 'CustomerDto.getCity()'
			tcCountry.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("country"));				// "country" -> call 'CustomerDto.getCoutry()'
			tcStreet.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("street"));					// "street" -> call 'CustomerDto.getStreet()'
			tcTelefonNumber.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("telephonenumber"));	// "telephonenumber" -> call 'CustomerDto.getTelephonenumber()'
			tcEMail.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("email"));					// "email" -> call 'CustomerDto.getEmail()'
			tcPoints.setCellValueFactory(new PropertyValueFactory<CustomerDto, String>("points"));					// "points" -> call 'CustomerDto.getPoints()'

//			tvCustomersListView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
			tvCustomersListView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
			
			// Daten in Tabelle schreiben
			tvCustomersListView.setItems(customers);
		}
		
	}

	/**
	 * Alle Zeilen in neuer Liste zurueckgeben
	 * 
	 * @return
	 */
	public List<CustomerDto> getList() {
		LOG.info("");
		
		return tvCustomersListView.getItems();
	}
	
	/**
	 * Aktuell ausgewaehlten Customer zurueckgeben
	 * @return
	 */
	public CustomerDto getCustomer() {
		LOG.info("");
		
//		tvCustomersListView.getSelectionModel().getSelectedItem();
		// ausgewaehlte Zeile zurueckgeben
		return tvCustomersListView.getSelectionModel().getSelectedItem();
	}
	
	// TODO Testroutinen, solange es noch keine DTO-Objekte gibt
	
	/**
	 * versuchen Text sprachabhaengig international zu uebersetzen
	 * getrimmt, NULL in "" uebersetzt
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
