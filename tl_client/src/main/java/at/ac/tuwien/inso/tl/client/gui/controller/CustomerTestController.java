/**
 * Sample Skeleton for "CustomerTestGui.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.application.Platform;
import javafx.fxml.Initializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.controller.CustomerBaseFormController.PaneMode;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.CustomerDto;

@Controller @Scope("prototype") 
public class CustomerTestController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerTestController.class);
	
	// FXML-injizierte Variablen

	@Autowired
	private CustomerService customerService;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    
    private EventHandler<ActionEvent> oldApplyHandle;

    
    @FXML // fx:id="apCustomerMainForm"
    private AnchorPane apCustomerMainForm;
    @FXML
    private CustomerMainFormController apCustomerMainFormController;

    @FXML // fx:id="apCustomerTestGui"
    private AnchorPane apCustomerTestGui;								// eigenes Root-Pane


    
    // -----------------------------------------------------
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
        assert apCustomerMainForm != null : "fx:id=\"apCustomerMainForm\" was not injected: check your FXML file 'CustomerTestGui.fxml'.";
        assert apCustomerTestGui != null : "fx:id=\"apCustomerTestGui\" was not injected: check your FXML file 'CustomerTestGui.fxml'.";
        assert apCustomerMainFormController != null : "fx:id=\"apCustomerMainFormController\" was not injected: check your FXML file 'CustomerTestGui.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        // ---------------------------------------

//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				LOG.info("delayed initialization");
//        
//				// TODO restliche Initialisierung
//	
//		    }
//		});

		// restliche Initialisierung

        // TODO Nachfolgender Code ist Tests only!!!
        
        // TODO Test eines SubPane in der Vorgangsverwaltung
        // TODO Display-Mode auf Kundendetails setzen
        apCustomerMainFormController.setMode(CustomerMainFormController.PaneMode.VIEW);
        
        // TODO Test-Kunden injizieren
        CustomerDto customerDto = null;
        List<CustomerDto> customerList = null;
        try {
			customerList = customerService.getAll();
	        if (customerList.size() != 0) {
	        	customerDto = customerList.get(0);
	        }
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        apCustomerMainFormController.setCustomer(customerDto);

        // TODO testweise neuen Handler auf "Uebernehmen"-Button in Search-View setzen
        oldApplyHandle = apCustomerMainFormController.getBtnApplySearch().getOnAction();
        // Configure new Apply-Button-Action
        apCustomerMainFormController.getBtnApplySearch().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LOG.debug("neuer Event-Handler");

				oldApplyHandle.handle(event);
				
			}
		});
        
	}
}
