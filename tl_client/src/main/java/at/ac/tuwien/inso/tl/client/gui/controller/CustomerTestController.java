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
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.CustomerDto;

@Controller @Scope("prototype") 
public class CustomerTestController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerTestController.class);

    private EventHandler<ActionEvent> oldBackHandle;						// ueberschriebener EventHandler fuer btnViewBackExtern
    private EventHandler<ActionEvent> oldApplyHandle;						// ueberschriebener EventHandler fuer btnSearchApply
    private EventHandler<ActionEvent> oldClearHandle;						// ueberschriebener EventHandler fuer btnViewClear
    private EventHandler<ActionEvent> oldForwardHandle;						// ueberschriebener EventHandler fuer btnViewForwardExtern
    private CustomerDto customer = new CustomerDto();						// uebernommene (aktuelle) Kunden-Daten

	// FXML-injizierte Variablen

	@Autowired private CustomerService customerService;						// Kunden-Service
	
    @FXML private ResourceBundle resources;									// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;												// URL location of the FXML file that was given to the FXMLLoader
    
    @FXML private AnchorPane apCustomerMainForm;							// fx:id="apCustomerMainForm"
    @FXML private CustomerMainFormController apCustomerMainFormController;

    @FXML private AnchorPane apCustomerTestGui;		// eigenes Root-Pane	// fx:id="apCustomerTestGui"
    
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

        // all @FXML variables will have been injected

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
        
        // Test eines SubPane in der Vorgangsverwaltung
        // Display-Mode auf Kundendetails setzen
        apCustomerMainFormController.setPaneMode(CustomerMainFormController.PaneMode.VIEW);
        
        // testweise 1. gefundenen Kunden injizieren
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
        apCustomerMainFormController.setData(customerDto);

        // testweise neuen EventHandler auf "Zurück"-Button in View-Panel setzen
        oldBackHandle = apCustomerMainFormController.getBtnViewBackExtern().getOnAction();
        apCustomerMainFormController.getBtnViewBackExtern().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LOG.debug("neuer Event-Handler");

				// auch urspruenglichen EventHandler ausfuehren
				oldBackHandle.handle(event);
				
				// aktuelle Kunden-Dto auslesen
				customer = apCustomerMainFormController.getData();
				LOG.debug(customer);
				
				// TODO und jetzt einen Schritt zurueck
				
			}
		});
        
        // testweise neuen EventHandler auf "Übernehmen"-Button in Search-Panel setzen
        oldApplyHandle = apCustomerMainFormController.getBtnSearchApply().getOnAction();
        apCustomerMainFormController.getBtnSearchApply().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LOG.debug("neuer Event-Handler");

				// auch urspruenglichen EventHandler ausfuehren
				oldApplyHandle.handle(event);
				
				// aktuelle Kunden-Dto auslesen
				customer = apCustomerMainFormController.getData();
				LOG.debug(customer);
			}
		});
        
        // testweise neuen EventHandler auf "Anonym"-Button in View-Panel setzen
        oldClearHandle = apCustomerMainFormController.getBtnViewClear().getOnAction();
        apCustomerMainFormController.getBtnViewClear().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LOG.debug("neuer Event-Handler");

				// auch urspruenglichen EventHandler ausfuehren
				oldClearHandle.handle(event);
				
				// aktuelle Kunden-Dto auslesen
				customer = apCustomerMainFormController.getData();
				LOG.debug(customer);
			}
		});
        
        // testweise neuen EventHandler auf "Weiter"-Button in View-Panel setzen
        oldForwardHandle = apCustomerMainFormController.getBtnViewForwardExtern().getOnAction();
        apCustomerMainFormController.getBtnViewForwardExtern().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LOG.debug("neuer Event-Handler");

				// auch urspruenglichen EventHandler ausfuehren
				oldForwardHandle.handle(event);
				
				// aktuelle Kunden-Dto auslesen
				customer = apCustomerMainFormController.getData();
				LOG.debug(customer);
				
				// TODO und jetzt einen Schritt weiter
				
			}
		});
	}
}
