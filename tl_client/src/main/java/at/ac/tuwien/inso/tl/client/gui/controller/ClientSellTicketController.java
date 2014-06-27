package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.BasketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.FXMLContainer;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.BasketDto;
import at.ac.tuwien.inso.tl.dto.CustomerDto;

@Controller
@Scope("prototype")
public class ClientSellTicketController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientSellTicketController.class);
	
	@FXML
	private BorderPane bpSellTicket;
	@FXML
	private ImageView ivWorkflow;
	
	@Autowired
	private BasketService basketService;
	@Autowired
	private ClientMainController startpageController;
	
	/**
	 * Der Basket für diesen Verkaufs- und Reservierungsvorgang
	 */
	private BasketDto basket;
	
	/**
	 * Der Customer für diesen Verkaufs- und Reservierungsvorgang
	 */
	private CustomerDto customer;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {
		LOG.info("initialize SellTicketController");
		
		try {
			basket = basketService.createBasket();
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage)bpSellTicket.getScene().getWindow(), BundleManager.getExceptionBundle().getString("sellticketpage.create_basket_error"));
			err.show();
		}
		setStepImage("/images/TicketStep.png");
		setCenterContent("/gui/ClientSearchGui.fxml");
	}
	
	public BasketDto getBasket() {
		return basket;
	}

	public void setBasket(BasketDto basket) {
		this.basket = basket;
	}
	
	public void loadExistingBasket(BasketDto basket) {
		try {
			basketService.undoBasket(this.basket.getId());
			this.basket = basket;
			setStepImage("/images/ShoppingCartStep.png");
			setCenterContent("/gui/ClientShoppingCartGui.fxml");
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage)bpSellTicket.getScene().getWindow(), BundleManager.getExceptionBundle().getString("sellticketpage.load_basket_error"));
			err.show();
		}
	}
	
	public CustomerDto getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDto customer) {
		this.customer = customer;
		try {
			if(basket != null && customer != null) {
					basketService.setCustomerForBasket(basket, customer.getId());
			} else if(basket != null) {
				basketService.setCustomerForBasket(basket, null);
			}
		} catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage)bpSellTicket.getScene().getWindow(), BundleManager.getExceptionBundle().getString("sellticketpage.setcustomer_error"));
			err.show();
		}
	}
	
	/**
	 * Setzt den content im center-Teil des Haupt-BorderPanes
	 * @param n Der Knoten mit dem Content
	 */
	public void setCenterContent(String path){
		FXMLContainer<ISellTicketSubController> searchPage = SpringFxmlLoader.getInstance().loadExtended(path);
		if(searchPage.getController() != null) {
			searchPage.getController().setParentController(this);
		}
		bpSellTicket.setCenter((Node)searchPage.getContent());
	}
	
	/**
	 * Setzt das Bild mit dem Steps oben
	 * @param url Die URL des Bildes, das angezeigt werden soll
	 */
	public void setStepImage(String url) {
		Image imgWorkflow = new Image(SpringFxmlLoader.class.getResource(url).toString());
		ivWorkflow.setImage(imgWorkflow);
	}
	
	@FXML
	public void handleToSearchpage() {
		LOG.debug("handleToSearchpage clicked");
		setStepImage("/images/TicketStep.png");
		setCenterContent("/gui/ClientSearchGui.fxml");
	}
	
	@FXML
	public void handleToChooseClient() {
		LOG.debug("handleToChooseClient clicked");
		setStepImage("/images/ClientStep.png");
		setCenterContent("/gui/ClientChooseClientGui.fxml");
	}
	
	@FXML
	public void handleToCartpage() {
		LOG.debug("handleToCartpage clicked");
		setStepImage("/images/ShoppingCartStep.png");
		setCenterContent("/gui/ClientShoppingCartGui.fxml");
	}
	
	@FXML
	public void handleToMerchandising() {
		LOG.debug("handleToMerchandising clicked");
		setStepImage("/images/MerchandisingStep.png");
		setCenterContent("/gui/ClientSellArticleGui.fxml");
	}
}
