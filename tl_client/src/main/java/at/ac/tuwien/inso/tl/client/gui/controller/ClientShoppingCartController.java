package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.dto.EntryDto;

@Controller
@Scope("prototype")
public class ClientShoppingCartController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientShoppingCartController.class);

	@FXML
	private TableView<EntryDto> tvCart;
	@FXML
	private TableColumn tcCartDescription;
	@FXML
	private TableColumn tcCartStatus;
	@FXML
	private TableColumn tcCartSinglePrice;
	@FXML
	private TableColumn tcCartAmount;
	@FXML
	private TableColumn tcCartSum;
	@FXML
	private TableColumn tcCartSelection;
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
