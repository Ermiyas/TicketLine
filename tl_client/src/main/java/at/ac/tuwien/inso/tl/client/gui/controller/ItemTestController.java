/**
 * Sample Skeleton for "CustomerTestGui.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.dto.BasketDto;

@Controller @Scope("prototype") 
public class ItemTestController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ItemTestController.class);

	// FXML-injizierte Variablen

	@Autowired private BasketService basketService;							// Warenkorb-Service
	
    @FXML private ResourceBundle resources;									// ResourceBundle that was given to the FXMLLoader
    @FXML private URL location;												// URL location of the FXML file that was given to the FXMLLoader
    
    @FXML private AnchorPane apItemList;									// fx:id="apItemList"
    @FXML private ItemListFormController apItemListController;
    
    @FXML private AnchorPane apItemTestGui;			// eigenes Root-Pane	// fx:id="apItemTestGui"
    
    // -----------------------------------------------------
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
        assert apItemTestGui != null : 					"fx:id=\"apCustomerTestGui\" was not injected: check your FXML file 'ItemTestGui.fxml'.";
        assert apItemList != null : 					"fx:id=\"apItemList\" was not injected: check your FXML file 'ItemTestGui.fxml'.";
        assert apItemListController != null : 			"fx:id=\"apItemListController\" was not injected: check your FXML file 'ItemTestGui.fxml'.";

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

        // ---------------------------------------------- Storno ----------------------------------------
        
        // TODO Nachfolgender Code ist Tests only!!!
        
        // testweise 1. gefundenen Basket injizieren
        BasketDto basketDto = null;
        List<BasketDto> basketList = null;
        try {
			basketList = basketService.getAll();
	        if (basketList.size() != 0) {
	        	basketDto = basketList.get(0);
	        }
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        apItemListController.setList(basketDto);

	}
}
