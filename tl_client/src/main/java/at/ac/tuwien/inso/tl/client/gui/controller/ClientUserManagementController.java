package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.w3c.dom.Node;

import com.sun.glass.events.KeyEvent;

import at.ac.tuwien.inso.tl.client.client.EmployeeService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.ValidationEventHandler;
import at.ac.tuwien.inso.tl.dto.EmployeeDto;

@Controller
@Scope("prototype")
public class ClientUserManagementController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientUserManagementController.class);
	
	private static final int MAX_WRONG_PASSWORD_ATTEMPTS = 5;
	
	@Autowired
	private EmployeeService service;
	@Autowired
	private ApplicationContext appContext;
	private Validator validator;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private boolean isNewUser = true;
	
	@FXML
	private Label lblError;	
	@FXML
	private Button btnEdit;
	
	@FXML
	private TableView<EmployeeDto> tvUsers;
	@FXML
	private TableColumn<EmployeeDto, String> tcUsername;
	@FXML
	private TableColumn<EmployeeDto, Boolean> tcRole;
	@FXML
	private TableColumn<EmployeeDto, Integer> tcLockStatus;
	
	@FXML
	private HBox hboxNoUserSelected;
	@FXML
	private BorderPane bpDetails;
	
	@FXML
	private Button btnNewUser;
	@FXML
	private Label lblDetailsHeadline;
	@FXML
	private Label lblRole;
	@FXML
	private Label lblUsername;
	@FXML
	private Label lblFirstname;
	@FXML
	private Label lblLastname;
	@FXML
	private Label lblLockStatus;
	
	@FXML
	private ChoiceBox<String> cbRole;
	@FXML
	private CheckBox cboLockStatus;
	@FXML
	private PasswordField pfPassword;
	@FXML
	private TextField tfUsername;
	@FXML
	private TextField tfFirstname;
	@FXML
	private TextField tfLastname;
	@FXML
	private Label lblPassword;
	@FXML
	private Button btnDiscardChanges;
	@FXML
	private Button btnSaveChanges;

	private HashMap<String, TextInputControl> dtoInputMap;
	/**
	 * Speichert die in der GUI eingegebenen Werte in dem übergebenen EmployeeDto-Objekt
	 * @return Das DTO-Objekt mit den aus der GUI übernommenen Werten
	 */
	private EmployeeDto popluateDto(EmployeeDto emp) {
		emp.setFirstname((tfFirstname.getText() == null) ? "" : tfFirstname.getText());
		emp.setLastname((tfLastname.getText() == null) ? "" : tfLastname.getText());
		emp.setUsername((tfUsername.getText() == null) ? "" : tfUsername.getText());
		if(cbRole.getSelectionModel().getSelectedIndex() != -1) {
			if(cbRole.getSelectionModel().getSelectedIndex() == 0) {
				emp.setIsadmin(false);
			} else {
				emp.setIsadmin(true);
			}
		}
		if(pfPassword.getText() == null || pfPassword.getText().isEmpty()) {
			emp.setPasswordHash("");
		} else {
			emp.setPasswordHash(encoder.encode(pfPassword.getText()));
		}
		if(cboLockStatus.isSelected()) {
			emp.setWrongPasswordCounter(5);
		} else {
			emp.setWrongPasswordCounter(0);
		}
		return emp;
	}

	/**
	 * Leert die Eingabefelder für die Benutzerdetails
	 */
	private void emptyEditDetails() {
		tfUsername.setText(null);
		cbRole.getSelectionModel().select(0);
		cboLockStatus.selectedProperty().set(false);
		pfPassword.setText(null);
		tfFirstname.setText(null);
		tfLastname.setText(null);
	}

	/**
	 * Gibt zurück, ob der Benutzer zur Zeit gesperrt ist.
	 * @param wrongPasswordCounter Die Anzahl der falschen Passworteingaben
	 * @return true, wenn der Benutzer gesperrt ist, andernfalls false
	 */
	private boolean getIsLocked(Integer wrongPasswordCounter) {
		if(wrongPasswordCounter == null) {
			return false;
		} else {
			return wrongPasswordCounter >= MAX_WRONG_PASSWORD_ATTEMPTS;
		}
	}

	/**
	 * Gibt den übersetzten und ausgeschriebenen Sperrstatus zurück
	 * @param wrongPasswordCounter Die Anzahl der falschen Passworteingaben
	 * @return Den ausgeschriebenen Sperrstatus in der eingestellten Sprache
	 */
	private String getLockStatusText(int wrongPasswordCounter) {
		if (getIsLocked(wrongPasswordCounter)) {
			return BundleManager.getBundle().getString("userpage.locked");
		} else {
			return BundleManager.getBundle().getString("userpage.active");
		}
	}
	
	/**
	 * Gibt den übersetzten und ausgeschriebenen Rollennamen zurück
	 * @param isAdmin Gibt an, ob der Benutzer ein Administrator ist
	 * @return Den ausgeschriebenen Rollennamen in der eingestellten Sprache
	 */
	private String getRoleText(boolean isAdmin) {
		if (isAdmin) {
			return BundleManager.getBundle().getString("admin");
		} else {
			return BundleManager.getBundle().getString("salesperson");
		}
	}
	
	@FXML
	private void handleDiscardChanges(ActionEvent event){
		btnNewUser.setDisable(false);
		hideErrors();
		
		if(isNewUser) {
			hideDetailsPane();
			tvUsers.setDisable(false);
		} else {
			showInfoLabels();
			btnEdit.setDisable(false);
			btnSaveChanges.setDisable(true);
			btnDiscardChanges.setDisable(true);
			btnNewUser.setDisable(false);
			tvUsers.setDisable(false);
			loadDetails(tvUsers.getSelectionModel().selectedItemProperty().get());
		}
	}

	@FXML
	private void handleEdit(ActionEvent event){
		isNewUser = false;
		showDetailsPane();
		showEditDetails();
		emptyEditDetails();
		loadSelectionForEdit();
		tvUsers.setDisable(true);
		btnEdit.setDisable(true);
		btnNewUser.setDisable(true);
		btnSaveChanges.setDisable(false);
		btnDiscardChanges.setDisable(false);
	}
	
	/**
	 * Lädt den gerade ausgewählten Employee in die Input-Felder
	 */
	private void loadSelectionForEdit() {
		EmployeeDto emp = tvUsers.getSelectionModel().selectedItemProperty().get();
		lblDetailsHeadline.setText(String.format("\"%s\" %s", emp.getUsername(), BundleManager.getBundle().getString("edit_small")));
		tfUsername.setText(emp.getUsername());
		tfLastname.setText(emp.getLastname());
		tfFirstname.setText(emp.getFirstname());
		if(emp.getIsadmin()) {
			cbRole.getSelectionModel().select(1);
		} else {
			cbRole.getSelectionModel().select(0);
		}
		cboLockStatus.setSelected(getIsLocked(emp.getWrongPasswordCounter()));
		pfPassword.setText(null);
	}

	@FXML
	private void handleNewUser(ActionEvent event){
		showDetailsPane();
		showEditDetails();
		emptyEditDetails();
		tvUsers.getSelectionModel().clearSelection();
		tvUsers.setDisable(true);
		btnEdit.setDisable(true);
		btnSaveChanges.setDisable(false);
		btnDiscardChanges.setDisable(false);
		btnNewUser.setDisable(true);
		lblDetailsHeadline.setText(BundleManager.getBundle().getString("userpage.create_new_user"));
		isNewUser = true;
	}
	
	@FXML
	private void handleSaveChanges(ActionEvent event){
		hideErrors();
		
		EmployeeDto emp = null;
		if(isNewUser) {
			emp = popluateDto(new EmployeeDto());
		} else {
			emp = popluateDto(tvUsers.getSelectionModel().selectedItemProperty().get());
		}
		Set<ConstraintViolation<EmployeeDto>> violations = validator.validate(emp);
		if(violations.isEmpty()) {
			if(isNewUser) {
				try {
					LOG.info(String.format("Invoking service createEmployee for '%s'", emp.getUsername()));
					emp.setId(service.createEmployee(emp));
				} catch (ServiceException e) {
					LOG.error(String.format("Couldn't create employee: %s", e.getMessage()));
					lblError.setText(BundleManager.getExceptionBundle().getString("create_error"));
					lblError.setVisible(true);
				}
			} else {
				try {
					LOG.info(String.format("Invoking service updateEmployee for '%s'", emp.getUsername()));
					service.updateEmployee(emp);
				} catch (ServiceException e) {
					LOG.error(String.format("Couldn't update employee: %s", e.getMessage()));
					lblError.setText(BundleManager.getExceptionBundle().getString("update_error"));
					lblError.setVisible(true);
				}
			}
			
			tvUsers.setDisable(false);
			tvUsers.getSelectionModel().clearSelection();
			loadDefaultView();
		} else {
			for(ConstraintViolation<EmployeeDto> cv : violations) {
				TextInputControl erroneousControl = dtoInputMap.get(cv.getPropertyPath().toString()); 
				if(erroneousControl != null) {
					showError(erroneousControl, cv.getMessage());
				}
			}
		}

	}
	
	/**
	 * Zeigt eine Fehlermeldung bei dem übergebenen TextField an
	 * @param erroneousControl Das Control, bei dem der Fehler aufgetreten ist
	 * @param message Die Fehlermeldung
	 */
	private void showError(TextInputControl erroneousControl, String message) {
		erroneousControl.setStyle("-fx-border-color: red");
		erroneousControl.setTooltip(new Tooltip(message));
	}

	/**
	 * Versteckt alle Fehlermeldungen in den Eingabe-Controls
	 */
	private void hideErrors() {
		for(TextInputControl tic : dtoInputMap.values()) {
			if(tic != null) {
				tic.setStyle("-fx-border-color: null");
				tic.setTooltip(null);
			}
		}
	}
	/**
	 * Versteckt die Detailansicht und zeigt eine Standardmeldung an. 
	 */
	private void hideDetailsPane() {
		bpDetails.setVisible(false);
		hboxNoUserSelected.setVisible(true);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LOG.info("Opening user management page");

		// Der Validator für die Benutzereingaben
		validator = ((LocalValidatorFactoryBean)appContext.getBean("localizedvalidator")).getValidator();
		
		dtoInputMap = new HashMap<>();
		dtoInputMap.put("username", tfUsername);
		dtoInputMap.put("firstname", tfFirstname);
		dtoInputMap.put("lastname", tfLastname);
		dtoInputMap.put("passwordHash", pfPassword);
		
		tfUsername.setOnKeyTyped(new ValidationEventHandler<EmployeeDto>(EmployeeDto.class, "username", validator));
		tfFirstname.setOnKeyTyped(new ValidationEventHandler<EmployeeDto>(EmployeeDto.class, "firstname", validator));
		tfLastname.setOnKeyTyped(new ValidationEventHandler<EmployeeDto>(EmployeeDto.class, "lastname", validator));
		pfPassword.setOnKeyTyped(new ValidationEventHandler<EmployeeDto>(EmployeeDto.class, "passwordHash", validator));
		
		// Darstellungsform der Daten in der Tabelle definieren
		tcUsername.setCellValueFactory(new PropertyValueFactory<EmployeeDto, String>("username"));
		tcRole.setCellValueFactory(new PropertyValueFactory<EmployeeDto, Boolean>("isadmin"));
		tcLockStatus.setCellValueFactory(new PropertyValueFactory<EmployeeDto, Integer>("wrongPasswordCounter"));
		tcRole.setCellFactory(new Callback<TableColumn<EmployeeDto, Boolean>, TableCell<EmployeeDto, Boolean>>() {
			@Override
			public TableCell<EmployeeDto, Boolean> call(
					TableColumn<EmployeeDto, Boolean> param) {
				return new TableCell<EmployeeDto, Boolean>() {
					@Override
					public void updateItem(Boolean item, boolean empty) {
						if (empty) {
							setText(null);
						} else {
							setText(getRoleText(item));
						}
					}
				};
			}
		});
		tcLockStatus.setCellFactory(new Callback<TableColumn<EmployeeDto, Integer>, TableCell<EmployeeDto, Integer>>() {
			@Override
			public TableCell<EmployeeDto, Integer> call(
					TableColumn<EmployeeDto, Integer> param) {
				return new TableCell<EmployeeDto, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						if (empty) {
							setText(null);
						} else {
							setText(getLockStatusText(item));
						}
					}
				};
			}
		});
		
		tvUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EmployeeDto>() {
			@Override
			public void changed(
					ObservableValue<? extends EmployeeDto> observable,
					EmployeeDto oldValue, EmployeeDto newValue) {
				if (newValue != null) {
					showInfoLabels();
					loadDetails(newValue);
					showDetailsPane();
					btnEdit.setDisable(false);
					btnSaveChanges.setDisable(true);
					btnDiscardChanges.setDisable(true);
					btnNewUser.setDisable(false);
				}
			}
		}
			);
		
		// Rollen zur ChoiceBox hinzufügen
		cbRole.getItems().add(BundleManager.getBundle().getString("salesperson"));
		cbRole.getItems().add(BundleManager.getBundle().getString("admin"));
		
		// Standardmäßig alle Benutzer, aber keine Details anzeigen
		loadDefaultView();
	}
	
	/**
	 * Lädt die standardmäßige Ansicht zur Auswahl des Employees
	 */
	private void loadDefaultView() {
		hideDetailsPane();
		btnEdit.setDisable(true);
		btnSaveChanges.setDisable(true);
		btnDiscardChanges.setDisable(true);
		btnNewUser.setDisable(false);
		tvUsers.setDisable(false);
		try {
			tvUsers.setItems(FXCollections.observableList(service.getAllEmployees()));
		} catch (ServiceException e) {
			LOG.error(String.format("Error on populating TableView: %s", e.toString()));
			lblError.setText(BundleManager.getBundle().getString("userpage.load_error"));
			lblError.setVisible(true);
		}
	}
	
	/**
	 * Lädt die Detailansicht zu dem übergebenen Employee in die rechte Seite.
	 * @param emp Der Employee, dessen Daten in der Detailsansicht angezeigt werden sollen
	 */
	private void loadDetails(EmployeeDto emp) {
		LOG.debug(String.format("Loading details to employee #%d %s", emp.getId(), emp.getUsername()));
		
		lblDetailsHeadline.setText(String.format("%s \"%s\"",BundleManager.getBundle().getString("userpage.details_of"), emp.getUsername()));
		lblUsername.setText(emp.getUsername());
		lblFirstname.setText(emp.getFirstname());
		lblLastname.setText(emp.getLastname());
		lblLockStatus.setText(getLockStatusText(emp.getWrongPasswordCounter()));
		lblRole.setText(getRoleText(emp.getIsadmin()));
	}
	
	/**
	 * Zeigt die Felder zum Editieren an
	 */
	private void showEditDetails() {
		lblFirstname.setVisible(false);
		lblLastname.setVisible(false);
		lblLockStatus.setVisible(false);
		lblRole.setVisible(false);
		
		cbRole.setVisible(true);
		cboLockStatus.setVisible(true);
		pfPassword.setVisible(true);
		tfUsername.setVisible(true);
		tfFirstname.setVisible(true);
		tfLastname.setVisible(true);
		lblPassword.setVisible(true);
	}
	
	/**
	 * Zeigt die Detail-Seite rechts an
	 */
	private void showDetailsPane() {
		hboxNoUserSelected.setVisible(false);
		bpDetails.setVisible(true);
	}
	
	/**
	 * Versteckt alle Eingabefelder und zeigt die Labels mit den Benutzerinfos an.
	 */
	private void showInfoLabels() {
		lblFirstname.setVisible(true);
		lblLastname.setVisible(true);
		lblLockStatus.setVisible(true);
		lblRole.setVisible(true);
		
		cbRole.setVisible(false);
		cboLockStatus.setVisible(false);
		pfPassword.setVisible(false);
		tfUsername.setVisible(false);
		tfFirstname.setVisible(false);
		tfLastname.setVisible(false);
		lblPassword.setVisible(false);
	}
}
