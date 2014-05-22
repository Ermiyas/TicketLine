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
public class ClientChooseClientController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientChooseClientController.class);

	@FXML
	private BorderPane bpChooseClient;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize ClientChooseClientController");
	}
	
	@FXML
	private void handleReserveAsAnonymousClient() {
		LOG.info("handleReserveAsAnonymousClient clicked");		
		BorderPane parent = (BorderPane)bpChooseClient.getParent().lookup("#bpSellTicket");
		Image imgWorkflow = new Image(SpringFxmlLoader.class.getResource("/images/ShoppingCartStep.png").toString());
		ImageView iv = (ImageView)bpChooseClient.getParent().lookup("#ivWorkflow");
		iv.setImage(imgWorkflow);
		parent.setCenter((Node)SpringFxmlLoader.getInstance().load("/gui/ClientShoppingCartGui.fxml"));
	}
}
