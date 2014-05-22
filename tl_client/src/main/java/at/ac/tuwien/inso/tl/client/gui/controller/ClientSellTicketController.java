package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;

@Controller
@Scope("prototype")
public class ClientSellTicketController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientSellTicketController.class);
	
	@FXML
	private BorderPane bpSellTicket;
	@FXML
	private ImageView ivWorkflow;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize SellTicketController");
		Image imgWorkflow = new Image(SpringFxmlLoader.class.getResource("/images/TicketStep.png").toString());
		ivWorkflow.setImage(imgWorkflow);
		bpSellTicket.setCenter((Node)SpringFxmlLoader.getInstance().load("/gui/ClientSearchGui.fxml"));
	}
}
