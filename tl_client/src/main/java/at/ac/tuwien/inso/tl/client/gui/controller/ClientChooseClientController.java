package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.client.CustomerService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.CustomerDto;

@Controller
@Scope("prototype")
public class ClientChooseClientController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientChooseClientController.class);

	private EventHandler<ActionEvent> oldBackHandle;						// ueberschriebener EventHandler fuer btnViewBackExtern
    private EventHandler<ActionEvent> oldApplyHandle;						// ueberschriebener EventHandler fuer btnSearchApply
    private EventHandler<ActionEvent> oldClearHandle;						// ueberschriebener EventHandler fuer btnViewClear
    private EventHandler<ActionEvent> oldForwardHandle;						// ueberschriebener EventHandler fuer btnViewForwardExtern

	// FXML-injizierte Variablen

	@Autowired private CustomerService customerService;						// Kunden-Service
	@Autowired private BasketService basketService;
    //@FXML private ResourceBundle resources;									// ResourceBundle that was given to the FXMLLoader
    //@FXML private URL location;												// URL location of the FXML file that was given to the FXMLLoader
    
    @FXML private AnchorPane apCustomerMainForm;							// fx:id="apCustomerMainForm"
    @FXML private CustomerMainFormController apCustomerMainFormController;
    
    // -----------------------------------------------------
    
	@FXML
	private BorderPane bpChooseClient; 										// eigenes Root-Pane
	private ClientSellTicketController parentController;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize ClientChooseClientController");
		
        // Test eines SubPane in der Vorgangsverwaltung
        // Display-Mode auf Kundendetails setzen
        apCustomerMainFormController.setPaneMode(CustomerMainFormController.PaneMode.VIEW);

        // testweise neuen EventHandler auf "Zurück"-Button in View-Panel setzen
        oldBackHandle = apCustomerMainFormController.getBtnViewBackExtern().getOnAction();
        apCustomerMainFormController.getBtnViewBackExtern().setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				LOG.debug("neuer Event-Handler");

				// auch urspruenglichen EventHandler ausfuehren
				oldBackHandle.handle(event);
				
				// und jetzt einen Schritt zurueck
				getParentController().setCenterContent("/gui/ClientSearchGui.fxml");
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
				CustomerDto customer = apCustomerMainFormController.getData();
				if(customer.getId() == null) {
					parentController.setCustomer(null);
				} else {
					parentController.setCustomer(customer);
				}
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
				parentController.setCustomer(null);
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
				CustomerDto customer = apCustomerMainFormController.getData();
				if(customer.getId() == null) {
					parentController.setCustomer(null);
				} else {
					parentController.setCustomer(customer);
				}
				LOG.debug(customer);
				
				// und jetzt einen Schritt weiter
				getParentController().setCenterContent("/gui/ClientShoppingCartGui.fxml");
			}
		});
	}
	

	public ClientSellTicketController getParentController() {
		return parentController;
	}

	@Override
	public void setParentController(ClientSellTicketController cont) {
		parentController = cont;
		apCustomerMainFormController.setData(parentController.getCustomer());
	}
}
