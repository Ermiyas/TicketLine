package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.client.client.EmployeeService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.EmployeeDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Component
public class ClientUserManagementController implements Initializable {

	@Autowired
	private EmployeeService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	@FXML
	private void handleNewUser(ActionEvent event){}
	
	@FXML
	private void handleSaveChanges(ActionEvent event){}
	
	@FXML
	private void handleDiscardChanges(ActionEvent event){}

}
