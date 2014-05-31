/**
 * Sample Skeleton for "CustomerManageGui.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.CustomerService;

@Controller @Scope("prototype") 
public class CustomerManageController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerManageController.class);
	
	// FXML-injizierte Variablen

	@Autowired
	private CustomerService customerService;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    
    @FXML // fx:id="apCustomerMainForm"
    private AnchorPane apCustomerMainForm;
    @FXML
    private CustomerMainFormController apCustomerMainFormController;

    @FXML // fx:id="apCustomerManageGui"
    private AnchorPane apCustomerManageGui;								// eigenes Root-Pane


    
    // -----------------------------------------------------
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
        assert apCustomerMainForm != null : "fx:id=\"apCustomerMainForm\" was not injected: check your FXML file 'CustomerManageGui.fxml'.";
        assert apCustomerManageGui != null : "fx:id=\"apCustomerManageGui\" was not injected: check your FXML file 'CustomerManageGui.fxml'.";
        assert apCustomerMainFormController != null : "fx:id=\"apCustomerMainFormController\" was not injected: check your FXML file 'CustomerManageGui.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        // ---------------------------------------

		// restliche Initialisierung

        // Display-Mode auf KundenSuche setzen
        apCustomerMainFormController.setPaneMode(CustomerMainFormController.PaneMode.MANAGE);
        
	}
}
