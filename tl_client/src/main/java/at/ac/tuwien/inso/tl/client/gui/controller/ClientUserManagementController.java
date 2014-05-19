package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.EmployeeService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.EmployeeDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

@Controller
@Scope("prototype")
public class ClientUserManagementController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientUserManagementController.class);
	
	private static final int MAX_WRONG_PASSWORD_ATTEMPTS = 5;
	
	@Autowired
	private EmployeeService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
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
	private Label lblDetailsHeadline;
	@FXML
	private Label lblRole;
	@FXML
	private Label lblFirstname;
	@FXML
	private Label lblLastname;
	@FXML
	private Label lblLockStatus;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LOG.info("Opening user management page");
		
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
					loadDetails(newValue);
				} else {
					//hideDetails();
				}

			}
		});
		
		try {
			tvUsers.setItems(FXCollections.observableList(service.getAllEmployees()));
		} catch (ServiceException e) {
			LOG.error(String.format("Error on populating TableView: %s", e.toString()));
			lblError.setText(BundleManager.getBundle().getString("userpage.load_error"));
			lblError.setVisible(true);
		}
		hideDetails();
	}

	@FXML
	private void handleNewUser(ActionEvent event){}
	
	@FXML
	private void handleSaveChanges(ActionEvent event){}
	
	@FXML
	private void handleDiscardChanges(ActionEvent event){}
	
	@FXML
	private void handleEdit(ActionEvent event){}
	
	/**
	 * Lädt die Detailansicht zu dem übergebenen Employee in die rechte Seite.
	 * @param emp Der Employee, dessen Daten in der Detailsansicht angezeigt werden sollen
	 */
	private void loadDetails(EmployeeDto emp) {
		LOG.debug(String.format("Loading details to employee #%d %s", emp.getId(), emp.getUsername()));
		
		lblDetailsHeadline.setText(String.format("%s \"%s\"",BundleManager.getBundle().getString("userpage.details_of"), emp.getUsername()));
		lblFirstname.setText(emp.getFirstname());
		lblLastname.setText(emp.getLastname());
		lblLockStatus.setText(getLockStatusText(emp.getWrongPasswordCounter()));
		lblRole.setText(getRoleText(emp.getIsadmin()));
		hboxNoUserSelected.setVisible(false);
		bpDetails.setVisible(true);
		btnEdit.setVisible(true);
	}
	
	/**
	 * Versteckt die Detailansicht und zeigt eine Standardmeldung an. 
	 */
	private void hideDetails() {
		bpDetails.setVisible(false);
		hboxNoUserSelected.setVisible(true);
		btnEdit.setVisible(false);
		
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
	
	/**
	 * Gibt den übersetzten und ausgeschriebenen Sperrstatus zurück
	 * @param wrongPasswordCounter Die Anzahl der falschen Passworteingaben
	 * @return Den ausgeschriebenen Sperrstatus in der eingestellten Sprache
	 */
	private String getLockStatusText(int wrongPasswordCounter) {
		if (wrongPasswordCounter < MAX_WRONG_PASSWORD_ATTEMPTS) {
			return BundleManager.getBundle().getString("userpage.active");
		} else {
			return BundleManager.getBundle().getString("userpage.locked");
		}
	}
}
