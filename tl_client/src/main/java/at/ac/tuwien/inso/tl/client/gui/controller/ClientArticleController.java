package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.CustomerDto;

@Controller
@Scope("prototype")
public class ClientArticleController  implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientArticleController.class);	
	
	@FXML TextField txAmount;
	@FXML TableView tvArticles;
	@FXML TableColumn tcDescription;
	@FXML TableColumn tcPriceInEuro;
	@FXML TableColumn tcPriceInPoints;
	@FXML Label lblPoints;
	@FXML ChoiceBox<String> cbCurrency;	

	
	private ClientSellTicketController parentController;	
	private boolean isInitialized = false;
	
	@Override
	public void setParentController(ClientSellTicketController cont) {
		this.parentController = cont;
		if(!isInitialized) {			
			reloadArticlePage();
			isInitialized = true;
		}
		
	}

	@Override
	public void initialize(URL url, ResourceBundle resBundle) {		
		
				
	}

	private void reloadArticlePage()
	{
		CustomerDto customer = parentController.getCustomer();
		if(customer == null)
		{
			lblPoints.setText(BundleManager.getBundle().getString("articlePage.anonymousCustomer"));
		}
		else
		{
			lblPoints.setText(BundleManager.getBundle().getString("articlePage.points") + customer.getPoints());
		}				
		updateChoiceBox();
	}
	
	private void updateChoiceBox()
	{	
		List<String> currencies = new ArrayList<String>();
		currencies.add(0, BundleManager.getBundle().getString("articlePage.allCurrencies"));
		currencies.add(1, BundleManager.getBundle().getString("articlePage.onlyCash"));
		currencies.add(2, BundleManager.getBundle().getString("articlePage.onlyPoints"));		
		cbCurrency.setItems(FXCollections.observableArrayList(currencies));
	}
	
	@FXML
	private void handleBuyWithCash()
	{
		
	}
	
	@FXML
	private void handleBuyWithPoints()
	{
		
	}
	
	@FXML
	private void handleBackProcedure()
	{
		parentController.handleToChooseClient();
	}
	@FXML
	private void handleNextProcedure()
	{
		parentController.handleToCartpage();
	}		
}
