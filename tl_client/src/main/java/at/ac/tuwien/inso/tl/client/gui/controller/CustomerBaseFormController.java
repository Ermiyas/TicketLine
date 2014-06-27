/**
 * Sample Skeleton for "CustomerBaseForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

//import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.client.util.ValidationEventHandler;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
//import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;

import javax.validation.Validator;

/**
 * @author Robert Bekker 8325143
 *
 */
@Controller @Scope("prototype") 
public class CustomerBaseFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerBaseFormController.class);

	// allgemeine Variablen
	
	// Auch unvollständige Jahresangaben ermoeglichen
	private static final String SHORT_DATE_FORMAT = "d.M.yy";
	private static final String LONG_DATE_FORMAT = "dd.MM.yyyy";
	
    // Eingaben sollten schon waehrend(!) der Eingabe ueberprueft werden,
	// daher muessen regular Expressions ebenso unvollstaendige, aber bis dahin richtige Zeichenketten erlauben
	//
	// regular Expression fuer Deutsches Datums-Format: (T)T.(M)M.(JJ)JJ, 4-stelliges Jahr moeglich fuer 1900-2099
	private static final String DATE_REG_EXPR = "^\\s*((([0-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))|"
			+ "((([1-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))[\\.\\/\\-](([0-9]?)|(0[1-9])|([1][0-2])))|"
			+ "((([1-9]?)|(0[1-9])|([1-2][0-9])|(3[0-1]))[\\.\\/\\-]((0?[1-9])|([1][0-2]))[\\.\\/\\-]"
			+ "(([0-9]{0,2})|([0-9]{2,2}\\s*)|(((19)|(20))[0-9]{1,2})|(((19)|(20))[0-9]{2,2}\\s*))))$";
	
	private static boolean validate = true;		// default activate validation
	
	// alle moeglichen Modes eines Panes
	public static enum PaneMode {CREATE, SEARCH, VIEW, EDIT};	
	
	// moegliche Geschlecht-Werte
	private static enum SexModes {
		FEMALE {
			@Override public String toString() {
				LOG.info("");
		        return intString("customerpage.female");
		    }
		},
		MALE {
			@Override public String toString() {
				LOG.info("");
		        return intString("customerpage.male");
		    }
		};
		
		@Override public String toString() {
			LOG.info("");
			return this.toString();
		} 
	}

	private PaneMode paneMode;									// eingestellter Display-Mode des Pane
	private Integer customerId;									// aktuelle Customer-ID
	private Validator validator;								// Eingabe-Validator
	private HashMap<String, TextInputControl> fxmlInputMap;		// Map aller Eingabefelder

	// injizierte Variablen

	@Autowired private ApplicationContext appContext;			// FXML-Umfeld, fuer Validierung noetig
//	@Autowired private CustomerService customerService;			// Kunden-Services
	@FXML private ResourceBundle resources;						// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;									// URL location of the FXML file that was given to the FXMLLoader

	// FXML-Felder
    
    @FXML private AnchorPane apCustomerBasePane;	// fx:id="apCustomerBasePane"		// eigenes Root-Pane
    @FXML private ChoiceBox<String> cbSex;			// fx:id="cbSex"
    @FXML private Label lbCity;						// fx:id="lbCity"
    @FXML private Label lbCountry;					// fx:id="lbCountry"
    @FXML private Label lbDateOfBirth;				// fx:id="lbDateOfBirth"
    @FXML private Label lbEMail;					// fx:id="lbEMail"
    @FXML private Label lbFirstName;				// fx:id="lbFirstName"
    @FXML private Label lbLastName;					// fx:id="lbLastName"
    @FXML private Label lbPoints;					// fx:id="lbPoints"
    @FXML private Label lbPostalCode;				// fx:id="lbPostalCode"
    @FXML private Label lbSex;						// fx:id="lbSex"
    @FXML private Label lbStreet;					// fx:id="lbStreet"
    @FXML private Label lbTelefonNumber;			// fx:id="lbTelefonNumber"
    @FXML private Label lbTitle;					// fx:id="lbTitle"
    @FXML private TextField txtCity;				// fx:id="txtCity"
    @FXML private TextField txtCountry;				// fx:id="txtCountry"
    @FXML private TextField txtDateOfBirth;			// fx:id="txtDateOfBirth"
    @FXML private TextField txtEMail;				// fx:id="txtEMail"
    @FXML private TextField txtFirstName;			// fx:id="txtFirstName"
    @FXML private TextField txtLastName;			// fx:id="txtLastName"
    @FXML private TextField txtPoints;				// fx:id="txtPoints"
    @FXML private TextField txtPostalCode;			// fx:id="txtPostalCode"
    @FXML private TextField txtSex;					// fx:id="txtSex"
    @FXML private TextField txtStreet;				// fx:id="txtStreet"
    @FXML private TextField txtTelefonNumber;		// fx:id="txtTelefonNumber"
    @FXML private TextField txtTitle;				// fx:id="txtTitle"
    @FXML private TitledPane tpTitlePane;			// fx:id="tpTitlePane"

    // Initialisierung

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("");

		assert appContext != null : "\"appContext\" was not injected: check your Spring-Application.";
//		assert customerService != null : "\"customerService\" was not injected: check your Interface-file 'CustomerService.java'.";
        assert resources != null : "\"resources\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert location != null : "\"location\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert apCustomerBasePane != null : "fx:id=\"apCustomerBasePane\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert cbSex != null : "fx:id=\"cbSex\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbCity != null : "fx:id=\"lbCity\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbCountry != null : "fx:id=\"lbCountry\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbDateOfBirth != null : "fx:id=\"lbDateOfBirth\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbEMail != null : "fx:id=\"lbEMail\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbFirstName != null : "fx:id=\"lbFirstName\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbLastName != null : "fx:id=\"lbLastName\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbPoints != null : "fx:id=\"lbPoints\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbPostalCode != null : "fx:id=\"lbPostalCode\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbSex != null : "fx:id=\"lbSex\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbStreet != null : "fx:id=\"lbStreet\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbTelefonNumber != null : "fx:id=\"lbTelefonNumber\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert lbTitle != null : "fx:id=\"lbTitle\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtCity != null : "fx:id=\"txtCity\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtCountry != null : "fx:id=\"txtCountry\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtDateOfBirth != null : "fx:id=\"txtDateOfBirth\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtEMail != null : "fx:id=\"txtEMail\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtFirstName != null : "fx:id=\"txtFirstName\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtLastName != null : "fx:id=\"txtLastName\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtPoints != null : "fx:id=\"txtPoints\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtPostalCode != null : "fx:id=\"txtPostalCode\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtSex != null : "fx:id=\"txtSex\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtStreet != null : "fx:id=\"txtStreet\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtTelefonNumber != null : "fx:id=\"txtTelefonNumber\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert txtTitle != null : "fx:id=\"txtTitle\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
        assert tpTitlePane != null : "fx:id=\"tpTitlePane\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

    // ---------------------------------------------------------------------
        
        // Alle Eingabefelder in neuer, durchsuchbarer Map ablegen
        fxmlInputMap = new HashMap<>();
        fxmlInputMap.put("city",			txtCity);
        fxmlInputMap.put("country",			txtCountry);
//		fxmlInputMap.put("dateOfBirth",		txtDateOfBirth);			// TODO String to Date 
        fxmlInputMap.put("email",			txtEMail);
        fxmlInputMap.put("firstname",		txtFirstName);
        fxmlInputMap.put("lastname",		txtLastName);
//		fxmlInputMap.put("points",			txtPoints);					// TODO String to Integer
        fxmlInputMap.put("postalcode",		txtPostalCode);
//		fxmlInputMap.put("isFemale",		txtSex);					// TODO String to Boolean (unnoetig!)
        fxmlInputMap.put("street",			txtStreet);
        fxmlInputMap.put("telephonenumber",	txtTelefonNumber);
        fxmlInputMap.put("title",			txtTitle);
        
		// Mit Listener Geburtsdatum schon waehrend Eingabe ueberpruefen
		txtDateOfBirth.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String alt, String neu) {
				LOG.info("");
				
				// TODO eigentlich sollte das Service dies ueberpruefen (keine Logik in GUI)
	    		if (neu != null && ! neu.trim().equals("") && ! neu.matches(DATE_REG_EXPR)) {
		    		txtDateOfBirth.setText(alt);
				} 
			}
	    });

		// Mit Listener Punkte schon waehrend Eingabe ueberpruefen
		txtPoints.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> arg0, String alt, String neu) {
				LOG.info("");
				
				// Nur Integers erlauben
				if (! neu.trim().equals("")) {
					try {
						String.format("%d", Integer.parseInt(neu));
					} catch (NumberFormatException e) {
						txtPoints.setText(alt);
					}
				}
			}
	    });

		// Geschlechter-Liste setzen
		cbSex.getItems().clear();
		cbSex.getItems().add(SexModes.FEMALE.toString());
		cbSex.getItems().add(SexModes.MALE.toString());
		cbSex.getItems().add("");

		// TODO ev. delayed-initialization aktivieren 
//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				LOG.info("");
//				if (null != apCustomerBasePane) {
//
//					// TODO restliche Initialisierung
//		
//				}
//		    }
//		});

        // View als Default-Mode einstellen
        setPaneMode(PaneMode.VIEW);
        
	}

	// -----------------------------
	

	// Getter aller Labels, Eingabefelder, etc.
	
	public Integer getCustomerId() { return customerId; }
//	public CustomerService getCustomerService() { return customerService; }
	public AnchorPane getApCustomerBasePane() { return apCustomerBasePane; }		// eigenes Root-Pane
	public ChoiceBox<String> getCbSex() { return cbSex; }
	public Label getLbCity() { return lbCity; }
	public Label getLbCountry() { return lbCountry; }
	public Label getLbDateOfBirth() { return lbDateOfBirth; }
	public Label getLbEMail() { return lbEMail; }
	public Label getLbFirstName() { return lbFirstName; }
	public Label getLbLastName() { return lbLastName; }
	public Label getLbPoints() { return lbPoints; }
	public Label getLbPostalCode() { return lbPostalCode; }
	public Label getLbSex() { return lbSex; }
	public Label getLbStreet() { return lbStreet; }
	public Label getLbTelefonNumber() { return lbTelefonNumber; }
	public Label getLbTitle() { return lbTitle; }
	public TextField getTxtCity() { return txtCity; }
	public TextField getTxtCountry() { return txtCountry; }
	public TextField getTxtDateOfBirth() { return txtDateOfBirth; }
	public TextField getTxtEMail() { return txtEMail; }
	public TextField getTxtFirstName() { return txtFirstName; }
	public TextField getTxtLastName() { return txtLastName; }
	public TextField getTxtPoints() { return txtPoints; }
	public TextField getTxtPostalCode() { return txtPostalCode; }
	public TextField getTxtSex() { return txtSex; }
	public TextField getTxtStreet() { return txtStreet; }
	public TextField getTxtTelefonNumber() { return txtTelefonNumber; }
	public TextField getTxtTitle() { return txtTitle; }
	public TitledPane getTpTitlePane() { return tpTitlePane; }
	public HashMap<String, TextInputControl> getFxmlInputMap() { return fxmlInputMap; }
	public boolean getValidate() { return validate; }

	// Setter fuer Titel und Geschlecht
	
	/**
	 * Titel der TitlePane sprachabhaengig setzen
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		LOG.info("");
		
		tpTitlePane.setText(intString(title));
	}
	
	/**
	 * Geschlecht setzen
	 * 
	 * @param isFemale
	 */
	private void setSex(Boolean isFemale) {
		LOG.info("");
		
		if (isFemale == null) {
			setSex("");
		} else if (isFemale) {
			setSex(SexModes.FEMALE.toString());
		} else if (! isFemale) {
			setSex(SexModes.MALE.toString());
		} 
	}

	/**
	 * Geschlecht setzen
	 * 
	 * @param sex
	 */
	private void setSex(String sex) {
		LOG.info("");
		
		if (sex == null || sex.equals("")) {
			txtSex.setText("");
			cbSex.setValue("");
			cbSex.getSelectionModel().clearSelection();
		} else {
			txtSex.setText(intString(sex));
			cbSex.setValue(intString(sex));
		}
	}
	
	/**
	 * Liest das Geschlecht Mode-abhaengig aus dem angezeigten Pane aus
	 * 
	 * @return Geschlecht lt. Pane
	 */
	private Boolean getSex() {
		LOG.info("");
		
		Boolean isFemale = null;
		if (this.paneMode.equals(PaneMode.VIEW)) {
			if (getFieldValue(txtSex) == null) {
				isFemale = null;
			} else if (getFieldValue(txtSex).equals(SexModes.FEMALE.toString())) {
				isFemale = true;
			} else if (getFieldValue(txtSex).equals(SexModes.MALE.toString())) {
				isFemale = false;
			} 
		} else {
			if (getFieldValue(cbSex) == null) {
				isFemale = null;
			} else if (getFieldValue(cbSex).equals(SexModes.FEMALE.toString())) {
				isFemale = true;
			} else if (getFieldValue(cbSex).equals(SexModes.MALE.toString())) {
				isFemale = false;
			} 
		}
		return isFemale;
	}

	/**
	 * Sichtbarkeiten, etc. des Panels zuruecksetzen
	 * 
	 * @param mode
	 */
	public void resetPaneMode() {
		LOG.info("");

		setPaneMode(this.paneMode);
	}

	/**
	 * Sichtbarkeiten, etc. des Panels Modus-abhaengig anpassen
	 * 
	 * @param mode
	 */
	public void setPaneMode(PaneMode mode) {
		LOG.info("");
		
		// Save Mode
		if (mode == null) {
			mode = PaneMode.VIEW;					// default Pane Mode
		}
		this.paneMode = mode;
        
		if (mode == PaneMode.CREATE) {
			txtSex.setVisible(false);
			cbSex.setVisible(true);
			setAllFieldsEditable(true);
			setAllFieldsFocusTraversable(true);
			lbPoints.setVisible(false);
			txtPoints.setVisible(false);
			txtPoints.setEditable(false);
			validate = true;
		} else if (mode == PaneMode.SEARCH) {
			txtSex.setVisible(false);
			cbSex.setVisible(true);
			setAllFieldsEditable(true);
			setAllFieldsFocusTraversable(true);
			lbPoints.setVisible(true);
			txtPoints.setVisible(true);
			txtPoints.setEditable(true);
			validate = false;
		} else if (mode == PaneMode.VIEW) {
			txtSex.setVisible(true);
			cbSex.setVisible(false);
			setAllFieldsEditable(false);
			setAllFieldsFocusTraversable(false);
			lbPoints.setVisible(true);
			txtPoints.setVisible(true);
			txtPoints.setEditable(false);
			validate = false;
		} else if (mode == PaneMode.EDIT) {
			txtSex.setVisible(false);
			cbSex.setVisible(true);
			setAllFieldsEditable(true);
			setAllFieldsFocusTraversable(true);
			lbPoints.setVisible(true);
			txtPoints.setVisible(true);
			txtPoints.setEditable(true);
			validate = true;
		} 

		setValidator();								// Eingabefelder-Ueberpruefung (de-) aktivieren
	}

	
	/**
	 * Editierbarkeit aller Felder gemeinsam setzen
	 * 
	 * @param editable
	 */
	private void setAllFieldsEditable(Boolean editable) {
		LOG.info("");
		
		txtTitle.setEditable(editable); 
		txtFirstName.setEditable(editable); 
		txtLastName.setEditable(editable); 
		cbSex.setDisable(! editable);			// ersatzweise disablen
		txtSex.setEditable(editable);
		txtDateOfBirth.setEditable(editable);
		txtCountry.setEditable(editable);
		txtCity.setEditable(editable); 
		txtPostalCode.setEditable(editable); 
		txtStreet.setEditable(editable); 
		txtTelefonNumber.setEditable(editable); 
		txtEMail.setEditable(editable); 
		txtPoints.setEditable(editable); 
	}

	/**
	 * Editierbarkeit aller Felder gemeinsam setzen
	 * 
	 * @param focusTraversable
	 */
	private void setAllFieldsFocusTraversable(Boolean focusTraversable) {
		LOG.info("");
		
		txtTitle.setFocusTraversable(focusTraversable); 
		txtFirstName.setFocusTraversable(focusTraversable); 
		txtLastName.setFocusTraversable(focusTraversable); 
		cbSex.setFocusTraversable(focusTraversable);
		txtSex.setFocusTraversable(focusTraversable);
		txtDateOfBirth.setFocusTraversable(focusTraversable);
		txtCountry.setFocusTraversable(focusTraversable);
		txtCity.setFocusTraversable(focusTraversable); 
		txtPostalCode.setFocusTraversable(focusTraversable); 
		txtStreet.setFocusTraversable(focusTraversable); 
		txtTelefonNumber.setFocusTraversable(focusTraversable); 
		txtEMail.setFocusTraversable(focusTraversable); 
		txtPoints.setFocusTraversable(focusTraversable); 
	}

	/**
	 * NULL oder getrimmten Text eines Textfeldes holen
	 * 
	 * @param fxmlField
	 * @return
	 */
	private String getFieldValue(TextField fxmlField) {
		LOG.info("");
		
		if (fxmlField.getText() == null) {
			return null;
		}
		String text = fxmlField.getText().trim();
		if (text.equals("")) {
			return null;
		}
		return text;
	}
	
	/**
	 * 	 NULL oder getrimmten Text einer String-Choicebox holen
	 * 
	 * @param fxmlField
	 * @return
	 */
	private String getFieldValue(ChoiceBox<String> fxmlField) {
		LOG.info("");
		
		if (fxmlField.getValue() == null) {
			return null;
		}
		String text = fxmlField.getValue().trim();
		if (text.equals("")) {
			return null;
		}
		return text;
	}
	
	// DTO verarbeiten
	
	/**
	 * Alle Felder in neuem DTO zurueckgeben
	 * 
	 * @return
	 */
	public CustomerDto getData() {
		LOG.info("");

		CustomerDto customerDto = new CustomerDto();
		
		// (geaenderte) Daten aus Maske auslesen und
		// in uebergebenen DTO-Objekt speichern
		if (customerDto != null) {
			if (customerId == null) {
				customerDto.setId(null);
			} else {
				customerDto.setId(customerId);
			}
			if (txtCity.getText() == null || txtCity.getText().trim().equals("")) {
				customerDto.setCity(null);
			} else {
				customerDto.setCity(getFieldValue(txtCity));
			}
			if (txtCountry.getText() == null || txtCountry.getText().trim().equals("")) {
				customerDto.setCountry(null);
			} else {
				customerDto.setCountry(getFieldValue(txtCountry));
			}
			if (txtDateOfBirth.getText() == null || txtDateOfBirth.getText().trim().equals("")) {
				customerDto.setDateOfBirth(null);
			} else {
				try {
					Calendar date = Calendar.getInstance();
					date.setTime(new SimpleDateFormat(SHORT_DATE_FORMAT).parse(txtDateOfBirth.getText()));
					if (date.get(Calendar.YEAR) < 1900) {
						date.add(Calendar.YEAR, 1900);
					}
					customerDto.setDateOfBirth(date.getTime());
				} catch (ParseException e) {
					customerDto.setDateOfBirth(null);
					txtDateOfBirth.setText(null);
				}
			}
			if (txtEMail.getText() == null || txtEMail.getText().trim().equals("")) {
				customerDto.setEmail(null);
			} else {
				customerDto.setEmail(getFieldValue(txtEMail));
			}
			if (txtFirstName.getText() == null || txtFirstName.getText().trim().equals("")) {
				customerDto.setFirstname(null);
			} else {
				customerDto.setFirstname(getFieldValue(txtFirstName));
			}
			customerDto.setIsFemale(getSex());
			if (txtLastName.getText() == null || txtLastName.getText().trim().equals("")) {
				customerDto.setLastname(null);
			} else {
				customerDto.setLastname(getFieldValue(txtLastName));
			}
			if (txtPoints.getText() == null || txtPoints.getText().trim().equals("")) {
				customerDto.setPoints(null);
			} else {
				customerDto.setPoints(Integer.parseInt(getFieldValue(txtPoints)));
			}
			if (txtPostalCode.getText() == null || txtPostalCode.getText().trim().equals("")) {
				customerDto.setPostalcode(null);
			} else {
				customerDto.setPostalcode(getFieldValue(txtPostalCode));
			}
			if (txtStreet.getText() == null || txtStreet.getText().trim().equals("")) {
				customerDto.setStreet(null);
			} else {
				customerDto.setStreet(getFieldValue(txtStreet));
			}
			if (txtTelefonNumber.getText() == null || txtTelefonNumber.getText().trim().equals("")) {
				customerDto.setTelephonenumber(null);
			} else {
				customerDto.setTelephonenumber(getFieldValue(txtTelefonNumber));
			}
			if (txtTitle.getText() == null || txtTitle.getText().trim().equals("")) {
				customerDto.setTitle(null);
			} else {
				customerDto.setTitle(getFieldValue(txtTitle));
			}
		}
		
		// TODO Fuer Tests ersatzweise Daten ausgeben
		logTestData();
		
		return customerDto;
	}

	/**
	 * Alle Felder zuruecksetzen
	 */
	public void setData() {
		LOG.info("");
		
		setData(null);
	}	
	
	/**
	 * Alle Felder setzen
	 * 
	 * @param customerDto
	 */
	public void setData(CustomerDto customerDto) {
		LOG.info("");
		
		// DTO uebergeben und alle Felder setzen
		if (customerDto == null) {
			// ohne DTO keine Daten
			clearData();
		} else {
			// alle Felder setzen
			customerId = customerDto.getId();
			txtCity.setText(noNullText(customerDto.getCity()));
			txtCountry.setText(noNullText(customerDto.getCountry()));
			if (customerDto.getDateOfBirth() == null) {
				txtDateOfBirth.setText("");
			} else {
				Calendar date = Calendar.getInstance();
				date.setTime(customerDto.getDateOfBirth());
				if (date.get(Calendar.YEAR) < 1900) {
					date.add(Calendar.YEAR, 1900);
				}
				txtDateOfBirth.setText(new SimpleDateFormat(LONG_DATE_FORMAT).format(date.getTime()));
			}
			txtEMail.setText(noNullText(customerDto.getEmail()));
			txtFirstName.setText(noNullText(customerDto.getFirstname()));
			txtLastName.setText(noNullText(customerDto.getLastname()));
			if (customerDto.getPoints() == null) {
				txtPoints.setText("");
			} else {
				txtPoints.setText(Integer.toString(customerDto.getPoints()));
			}
			txtPostalCode.setText(noNullText(customerDto.getPostalcode()));
			setSex(customerDto.getIsFemale());
			txtStreet.setText(noNullText(customerDto.getStreet()));
			txtTelefonNumber.setText(noNullText(customerDto.getTelephonenumber()));
			txtTitle.setText(noNullText(customerDto.getTitle()));
		}
	}
	
	/**
	 * NULL-Value in "" uebersetzen, um TextField sauber zu setzen
	 * 
	 * @param text
	 * @return
	 */
	private String noNullText(String text) {
		if (text == null) {
			return "";
		} 
		return text.trim();
	}

	/**
	 * angezeigte Daten loeschen
	 */
	private void clearData() {
		LOG.info("");
		
		customerId = null;
		txtTitle.setText(""); 
		txtFirstName.setText(""); 
		txtLastName.setText(""); 
		cbSex.getSelectionModel().clearSelection();
		cbSex.setValue("");
		txtSex.setText("");
		txtDateOfBirth.setText("");
		txtCountry.setText("");
		txtCity.setText(""); 
		txtPostalCode.setText(""); 
		txtStreet.setText(""); 
		txtTelefonNumber.setText(""); 
		txtEMail.setText(""); 
		txtPoints.setText(""); 
	}
	
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

	/**
	 * Versteckt alle Fehlermeldungen in den Eingabefeldern
	 */
	public void hideAllErrors() {
		for(TextInputControl tiController : fxmlInputMap.values()) {
			hideError(tiController);
		}
	}
	
	/**
	 * Versteckt Fehlermeldungen eines Eingabefeldes
	 */
	public void hideError(TextInputControl tiController) {
		if(tiController != null) {
			tiController.setStyle("-fx-border-color: null");
			tiController.setTooltip(null);
		}
	}
	
	/**
	 * Zeigt eine Fehlermeldung bei dem übergebenen TextField an
	 * @param erroneousControl Das Control, bei dem der Fehler aufgetreten ist
	 * @param message Die Fehlermeldung
	 */
	public void showError(TextInputControl errControl, String message) {
		errControl.setStyle("-fx-border-color: red");
		errControl.setTooltip(new Tooltip(message));
	}

	/**
	 * (De-) Aktiviert dynamische Feldueberpreufung
	 * abhaengig vom aktuellen PaneMode
	 */
	public void setValidator() {
		setValidator(validate);
	}
	
	/**
	 * (De-) Aktiviert dynamische Feldueberpreufung
	 * 
	 * @param validate
	 */
	public void setValidator(boolean validate) {
		if (validate) {
		    // Validator fuer die Benutzereingaben
		    validator = ((LocalValidatorFactoryBean)appContext.getBean("localizedvalidator")).getValidator();
		    
		    // Eventhandler fuer die Markierung der fehlerhaften Felder setzen
		    for (String control : fxmlInputMap.keySet()) {
		    	fxmlInputMap.get(control).setOnKeyTyped(
		    			new ValidationEventHandler<CustomerDto>(CustomerDto.class, control, validator)
		    	);
		    }
		} else {
		    // kein Validator fuer Benutzereingaben
		    validator = null;
			
		    // Eventhandler fuer die Markierung der fehlerhaften Felder loeschen
		    for (String control : fxmlInputMap.keySet()) {
		    	fxmlInputMap.get(control).setOnKeyTyped(null);
		    }
		}
	}
    
	
// ----------------------
	
	// TODO Testroutinen, solange es noch keine DTO-Objekte gibt
	
	// TODO Testdaten generieren
	public void setTestData() {
		LOG.info("");
		
		customerId = 9999;
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
	
	// TODO Testdaten ausgeben
	private void logTestData() {
		LOG.info("");
		
		LOG.debug(customerId); 
		LOG.debug(txtTitle.getText()); 
		LOG.debug(txtFirstName.getText()); 
		LOG.debug(txtLastName.getText()); 
		LOG.debug(cbSex.getValue()); 
		LOG.debug(txtSex.getText()); 
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
