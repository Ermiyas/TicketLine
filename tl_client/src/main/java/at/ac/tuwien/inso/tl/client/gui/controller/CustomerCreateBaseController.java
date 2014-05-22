package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller @Scope("prototype") 
public class CustomerCreateBaseController implements Initializable {
	private static final Logger LOG = Logger.getLogger(CustomerCreateBaseController.class);

	// FXML Elemente
	@FXML
	private AnchorPane apCustomerCreateBasePane;		// eigenes root pane
	@FXML
	private AnchorPane apCustomerDataBasePane;
	@FXML
	private CustomerDataBaseController apCustomerDataBasePaneController;

	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize controller");
		
		// Felder vorbereiten
		getFieldsController().setAllFieldsEditable(true);							// ist eigentlich eh per Default
		getFieldsController().getTxtPoints().setVisible(false);						// Punkte ausblenden
		getFieldsController().getLbPoints().setVisible(false);						// Punkte ausblenden
		getFieldsController().setTitle("%customerpage.new_customer_data");			
		
//		// Im Init geht noch nicht alles, da Tab noch gar nicht vorhanden ist.
//		Platform.runLater(new Runnable() {
//		    public void run() {
//				if (null != getFieldsController()) {
//					// TODO restliche Initialisierung
//		
//					// TODO Test only
//					// getFieldsController().setFields();
//				}
//		    }
//		});

		// TODO restliche Initialisierung
		
		 // TODO Test only
		getFieldsController().clearFields();
	}
	
	public CustomerDataBaseController getFieldsController() {
		return apCustomerDataBasePaneController;
	} 
}
