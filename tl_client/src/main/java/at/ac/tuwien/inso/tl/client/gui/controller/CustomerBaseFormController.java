/**
 * Sample Skeleton for "CustomerBaseForm.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
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
	
	public static enum PaneMode {CREATE, SEARCH, VIEW, EDIT};	// alle moeglichne Modes eines Panes
	
	private static enum SexModes {
		FEMALE {
			@Override public String toString() {
				LOG.info("");
				
		        return intString("customerpage.female");
		    }

		    public boolean isFemale() {
				LOG.info("");
				
		    	return true;
		    }
		},
		MALE {
			@Override public String toString() {
				LOG.info("");
				
		        return intString("customerpage.male");
		    }
		    
		    public boolean isFemale() {
				LOG.info("");
				
		    	return false;
		    }
		};
		
		@Override public String toString() {
			LOG.info("");
			
			return this.toString();
		} 

		public boolean isFemale() {
			LOG.info("");
			
			return this.isFemale();
		} 
	}

	private PaneMode paneMode;	// eingestellter Display-Mode des Pane
	
	private Integer customerId;

	// FXML-injizierte Variablen

	@Autowired
	private CustomerService customerService;

	@FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

	// FXML-Felder
    
    @FXML // fx:id="apCustomerBasePane"
    private AnchorPane apCustomerBasePane;		// eigenes Root-Pane

    @FXML // fx:id="cbSex"
    private ChoiceBox<String> cbSex;

    @FXML // fx:id="lbCity"
    private Label lbCity;

    @FXML // fx:id="lbCountry"
    private Label lbCountry;

    @FXML // fx:id="lbDateOfBirth"
    private Label lbDateOfBirth;

    @FXML // fx:id="lbEMail"
    private Label lbEMail;

    @FXML // fx:id="lbFirstName"
    private Label lbFirstName;

    @FXML // fx:id="lbLastName"
    private Label lbLastName;

    @FXML // fx:id="lbPoints"
    private Label lbPoints;

    @FXML // fx:id="lbPostalCode"
    private Label lbPostalCode;

    @FXML // fx:id="lbSex"
    private Label lbSex;

    @FXML // fx:id="lbStreet"
    private Label lbStreet;

    @FXML // fx:id="lbTelefonNumber"
    private Label lbTelefonNumber;

    @FXML // fx:id="lbTitle"
    private Label lbTitle;

    @FXML // fx:id="tpTitlePane"
    private TitledPane tpTitlePane;

    @FXML // fx:id="txtCity"
    private TextField txtCity;

    @FXML // fx:id="txtCountry"
    private TextField txtCountry;

    @FXML // fx:id="txtDateOfBirth"
    private TextField txtDateOfBirth;

    @FXML // fx:id="txtEMail"
    private TextField txtEMail;

    @FXML // fx:id="txtFirstName"
    private TextField txtFirstName;

    @FXML // fx:id="txtLastName"
    private TextField txtLastName;

    @FXML // fx:id="txtPoints"
    private TextField txtPoints;

    @FXML // fx:id="txtPostalCode"
    private TextField txtPostalCode;

    @FXML // fx:id="txtSex"
    private TextField txtSex;

    @FXML // fx:id="txtStreet"
    private TextField txtStreet;

    @FXML // fx:id="txtTelefonNumber"
    private TextField txtTelefonNumber;

    @FXML // fx:id="txtTitle"
    private TextField txtTitle;

    // Initialisierung

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override // This method is called by the FXMLLoader when initialization is complete
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("");

		assert customerService != null : "fx:id=\"customerService\" was not injected: check your Interface-file 'customerService.java'.";
        assert resources != null : "fx:id=\"resources\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
        assert location != null : "fx:id=\"location\" was not injected: check your Controller-file 'CustomerBaseFormController.java'.";
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
        assert tpTitlePane != null : "fx:id=\"tpTitlePane\" was not injected: check your FXML file 'CustomerBaseForm.fxml'.";
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

        // Initialize your logic here: all @FXML variables will have been injected

    // ---------------------------------------------------------------------
        
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

		// Geschlechter-Liste setzen
		cbSex.getItems().clear();
		cbSex.getItems().add(SexModes.FEMALE.toString());
		cbSex.getItems().add(SexModes.MALE.toString());

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

        // Search als Default-Mode einstellen
        setMode(PaneMode.SEARCH);
        
	}

	// -----------------------------
	
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @return the customerService
	 */
	public CustomerService getCustomerService() {
		return customerService;
	}

	/**
	 * @return the apCustomerBasePane
	 */
	public AnchorPane getApCustomerBasePane() {
		return apCustomerBasePane;
	}

	/**
	 * @return the cbSex
	 */
	public ChoiceBox<String> getCbSex() {
		return cbSex;
	}

	/**
	 * @return the lbCity
	 */
	public Label getLbCity() {
		return lbCity;
	}

	/**
	 * @return the lbCountry
	 */
	public Label getLbCountry() {
		return lbCountry;
	}

	/**
	 * @return the lbDateOfBirth
	 */
	public Label getLbDateOfBirth() {
		return lbDateOfBirth;
	}

	/**
	 * @return the lbEMail
	 */
	public Label getLbEMail() {
		return lbEMail;
	}

	/**
	 * @return the lbFirstName
	 */
	public Label getLbFirstName() {
		return lbFirstName;
	}

	/**
	 * @return the lbLastName
	 */
	public Label getLbLastName() {
		return lbLastName;
	}

	/**
	 * @return the lbPoints
	 */
	public Label getLbPoints() {
		return lbPoints;
	}

	/**
	 * @return the lbPostalCode
	 */
	public Label getLbPostalCode() {
		return lbPostalCode;
	}

	/**
	 * @return the lbSex
	 */
	public Label getLbSex() {
		return lbSex;
	}

	/**
	 * @return the lbStreet
	 */
	public Label getLbStreet() {
		return lbStreet;
	}

	/**
	 * @return the lbTelefonNumber
	 */
	public Label getLbTelefonNumber() {
		return lbTelefonNumber;
	}

	/**
	 * @return the lbTitle
	 */
	public Label getLbTitle() {
		return lbTitle;
	}

	/**
	 * @return the tpTitlePane
	 */
	public TitledPane getTpTitlePane() {
		return tpTitlePane;
	}

	/**
	 * @return the txtCity
	 */
	public TextField getTxtCity() {
		return txtCity;
	}

	/**
	 * @return the txtCountry
	 */
	public TextField getTxtCountry() {
		return txtCountry;
	}

	/**
	 * @return the txtDateOfBirth
	 */
	public TextField getTxtDateOfBirth() {
		return txtDateOfBirth;
	}

	/**
	 * @return the txtEMail
	 */
	public TextField getTxtEMail() {
		return txtEMail;
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
	 * @return the txtPoints
	 */
	public TextField getTxtPoints() {
		return txtPoints;
	}

	/**
	 * @return the txtPostalCode
	 */
	public TextField getTxtPostalCode() {
		return txtPostalCode;
	}

	/**
	 * @return the txtSex
	 */
	public TextField getTxtSex() {
		return txtSex;
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
	 * @return the txtTitle
	 */
	public TextField getTxtTitle() {
		return txtTitle;
	}

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
	 * Sichtbarkeiten, etc. des Panels Modus-abhaengig anpassen
	 * 
	 * @param mode
	 */
	public void setMode(PaneMode mode) {
		LOG.info("");
		
		// Save Mode
		this.paneMode = mode;

		if (mode == PaneMode.CREATE) {
			txtSex.setVisible(false);
			cbSex.setVisible(true);
			setAllFieldsEditable(true);
			lbPoints.setVisible(false);
			txtPoints.setVisible(false);
			txtPoints.setEditable(false);
		} else if (mode == PaneMode.SEARCH) {
			txtSex.setVisible(false);
			cbSex.setVisible(true);
			setAllFieldsEditable(true);
			lbPoints.setVisible(true);
			txtPoints.setVisible(true);
			txtPoints.setEditable(true);
		} else if (mode == PaneMode.VIEW) {
			txtSex.setVisible(true);
			cbSex.setVisible(false);
			setAllFieldsEditable(false);
			lbPoints.setVisible(true);
			txtPoints.setVisible(true);
			txtPoints.setEditable(false);
		} else if (mode == PaneMode.EDIT) {
			txtSex.setVisible(false);
			cbSex.setVisible(true);
			setAllFieldsEditable(true);
			lbPoints.setVisible(true);
			txtPoints.setVisible(true);
			txtPoints.setEditable(false);
		} 
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
		getData(customerDto);
		return customerDto;
	}
	
	/**
	 * Alle Felder in bestehendem DTO zurueckgeben
	 * 
	 * @param customerDto
	 */
	public void getData(CustomerDto customerDto) {
		LOG.info("");
		
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
			txtCity.setText(saveText(customerDto.getCity()));
			txtCountry.setText(saveText(customerDto.getCountry()));
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
			txtEMail.setText(saveText(customerDto.getEmail()));
			txtFirstName.setText(saveText(customerDto.getFirstname()));
			txtLastName.setText(saveText(customerDto.getLastname()));
			if (customerDto.getPoints() == null) {
				txtPoints.setText("");
			} else {
				txtPoints.setText(Integer.toString(customerDto.getPoints()));
			}
			txtPostalCode.setText(saveText(customerDto.getPostalcode()));
			setSex(customerDto.getIsFemale());
			txtStreet.setText(saveText(customerDto.getStreet()));
			txtTelefonNumber.setText(saveText(customerDto.getTelephonenumber()));
			txtTitle.setText(saveText(customerDto.getTitle()));
		}
	}
	
	/**
	 * NULL-Value in "" uebersetzen, um TextField sauber zu setzen
	 * 
	 * @param text
	 * @return
	 */
	private String saveText(String text) {
		if (text == null) {
			return "";
		} 
		return text;
	}

	// Daten loeschen
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

// ---------------------------------------------------------------------

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
