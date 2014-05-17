package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

@Component
public class ClientUserManagementController implements Initializable {

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
