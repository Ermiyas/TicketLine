package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope("prototype")
public class ClientShoppingCartController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientShoppingCartController.class);

	@FXML
	private GridPane gpShoppingCart;
	private ClientSellTicketController parentController;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize ClientShoppingCartController");
	}
	
	@FXML
	private void handleFinishProcedure() {
		LOG.info("handleFinishProcedure clicked");
	}
	
	@FXML
	private void handleAbortProcedure() {
		LOG.info("handleAbortProcedure clicked");
	}
	
	@FXML
	private void handleCheckout() {
		LOG.info("handleCheckout clicked");
	}

	@Override
	public void setParentController(ClientSellTicketController cont) {
		this.parentController = cont;
		
	}
}
