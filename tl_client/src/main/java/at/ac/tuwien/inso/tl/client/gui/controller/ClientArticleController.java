package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.ArticleService;
import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BasketEntryContainer;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.ArticleDto;
import at.ac.tuwien.inso.tl.dto.CustomerDto;
import at.ac.tuwien.inso.tl.dto.EntryDto;

@Controller
@Scope("prototype")
public class ClientArticleController  implements Initializable, ISellTicketSubController {
	
	private static final Logger LOG = Logger.getLogger(ClientArticleController.class);	
	
	@Autowired ArticleService articleService;
	@Autowired EntryService entryService;
	
	@FXML BorderPane bpArticle;
	@FXML TextField txAmount;
	@FXML TableView<ArticleDto> tvArticles;
	@FXML TableColumn<ArticleDto, String> tcName;
	@FXML TableColumn<ArticleDto, String> tcDescription;
	@FXML TableColumn<ArticleDto, Integer> tcPriceInEuro;
	@FXML TableColumn<ArticleDto, Integer> tcPriceInPoints;
	@FXML Label lblPoints;
	@FXML ChoiceBox<String> cbCurrency;	
	@FXML Button btBuyWithPoints;	

	private ObservableList<String> currencies = FXCollections.observableList(new ArrayList<String>());
	private ObservableList<ArticleDto> articles = FXCollections.observableList(new ArrayList<ArticleDto>());
	private ClientSellTicketController parentController;	
	private boolean isInitialized = false;
	
	@Override
	public void setParentController(ClientSellTicketController cont) {
		LOG.debug("setParentController called.");
		this.parentController = cont;
		if(!isInitialized) {			
			reloadArticlePage();
			isInitialized = true;
		}
		
	}

	@Override
	public void initialize(URL url, ResourceBundle resBundle) {		
		LOG.debug("initialize called.");
		// Setzt die Eigenschaften, welche in den Spalten angezeigt werden sollen		
		tcName.setCellValueFactory(new PropertyValueFactory<ArticleDto, String>("Name"));
		tcDescription.setCellValueFactory(new PropertyValueFactory<ArticleDto, String>("Description"));
		tcPriceInEuro.setCellValueFactory(new PropertyValueFactory<ArticleDto, Integer>("PriceInCent"));
		tcPriceInPoints.setCellValueFactory(new PropertyValueFactory<ArticleDto, Integer>("PriceInPoints"));
	
		//Überschreibe das Zeichenverhalten für PreisInCent zur Anzeige von Preis in Euro bzw. zum Zentrieren
		tcPriceInEuro.setCellFactory(new Callback<TableColumn<ArticleDto, Integer>, TableCell<ArticleDto, Integer>>() 
		{
			@Override
			public TableCell<ArticleDto, Integer> call(TableColumn<ArticleDto, Integer> param) 
			{
				return new TableCell<ArticleDto, Integer>()
				{
					@Override
					public void updateItem(Integer item, boolean empty)
					{					
						if(empty)
						{
							setText(null);
						}
						else
						{
							setAlignment(Pos.CENTER);
							setText(String.format("€ %.2f", ((float)item)/100));
						}
					}
			};
		}});	
		
		tcPriceInPoints.setCellFactory(new Callback<TableColumn<ArticleDto, Integer>, TableCell<ArticleDto, Integer>>() 
				{
					@Override
					public TableCell<ArticleDto, Integer> call(TableColumn<ArticleDto, Integer> param) 
					{
						return new TableCell<ArticleDto, Integer>()
						{
							@Override
							public void updateItem(Integer item, boolean empty)
							{					
								if(empty)
								{
									setText(null);
								}
								else
								{
									setAlignment(Pos.CENTER);
									setText(item.toString());
								}
							}
					};
				}});	
		
		cbCurrency.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>()
				{
					public void changed(@SuppressWarnings("rawtypes") ObservableValue ov, Number value, Number new_value)
					{
						if(new_value.intValue() == 0)
						{
							loadServiceData(null);
						}
						else
						{
							if(new_value.intValue() == 1)
							{
							loadServiceData(true);
							}
							else
							{
								loadServiceData(false);
							}
						}
					}
				});
		updateChoiceBox();
		cbCurrency.getSelectionModel().select(0);
		
	}

	private void reloadArticlePage()
	{
		LOG.debug("reloadArticlePage called.");
		CustomerDto customer = parentController.getCustomer();
		if(customer == null)
		{
			lblPoints.setText(BundleManager.getBundle().getString("articlePage.anonymousCustomer"));
			btBuyWithPoints.setDisable(true);
			
		}
		else
		{
			lblPoints.setText(BundleManager.getBundle().getString("articlePage.points") + customer.getPoints());
			btBuyWithPoints.setDisable(false);
		}				
	}
	
	public void loadServiceData(Boolean loadMerchandising)
	{		
		LOG.debug("loadServiceData called.");
		try
		{
			articles.clear();
			if(loadMerchandising == null) //Alle Artikel
			{
				articles.addAll(articleService.getAll());
			}
			else
			{
				if(loadMerchandising == true) //Nur Merchandising (PriceInCent != null)
				{
					articles.addAll(articleService.getAllMerchandising());
				}
				else //Nur Prämien (PriceInPoints != null)
				{
					articles.addAll(articleService.getAllBonus());
				}
			}
			tvArticles.setItems(articles);
		}
		catch (ServiceException e) {
			ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("articlePage.load_articles_error"));
			err.show();
		}	
	}
	
	private void updateChoiceBox()
	{
		LOG.debug("updateChoiceBox called.");
		currencies.clear();
		currencies.add(0, BundleManager.getBundle().getString("articlePage.allCurrencies"));
		currencies.add(1, BundleManager.getBundle().getString("articlePage.onlyCash"));
		currencies.add(2, BundleManager.getBundle().getString("articlePage.onlyPoints"));
		cbCurrency.setItems(currencies);
	}
	
	@FXML
	private void handleBuyWithCash()
	{
		buy(false);
	}
	
	@FXML
	private void handleBuyWithPoints()
	{
		buy(true);
	}	
	
	private void buy(boolean withPoints)
	{
		LOG.debug("buy called.");
		Integer amount = tryParseAmount();
		if(amount != null)
		{
			ArticleDto selectedArticle = tvArticles.getSelectionModel().getSelectedItem();
			if(selectedArticle != null)
			{
				if(withPoints && selectedArticle.getPriceInPoints() == null)
				{
					ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("articlePage.cannotBuyWithPoints"));
					err.show();
					return;
				}
				
				if(!withPoints && selectedArticle.getPriceInCent() == null)
				{
					ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("articlePage.cannotBuyWithMoney"));
					err.show();
					return;
				}
				
				try
				{
					EntryDto e = new EntryDto();
					e.setAmount(amount);
					e.setBuyWithPoints(withPoints);
					e.setSold(false);
					entryService.createEntryForArticle(e, selectedArticle.getId(), parentController.getBasket().getId());
					txAmount.setText("");
					tvArticles.getSelectionModel().clearSelection();
					
				}
				catch (ServiceException e) {
					ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("articlePage.createEntryFailed"));
					err.show();
				}				
			}
			else
			{
				ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), BundleManager.getExceptionBundle().getString("articlePage.noSelection"));
				err.show();
			}
		}
	}
	
	private Integer tryParseAmount()
	{
		LOG.debug("tryParseAmount called.");
		Integer amount = null;
		try
		{
			amount = Integer.parseInt(txAmount.getText());
			if(amount < 1)
			{
				String amountLessThanOneString = BundleManager.getExceptionBundle().getString("articlePage.amountMustBeGreaterThanZero");
				amount = null;
				ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), amountLessThanOneString);
				err.show();				
				txAmount.setTooltip(new Tooltip(amountLessThanOneString));
				txAmount.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
			}				
			else
			{
				txAmount.setTooltip(null);
				txAmount.setStyle("-fx-border-color: transparent;");
			}		
		}
		catch(NumberFormatException  ex)
		{
			String amountInvalidString = BundleManager.getExceptionBundle().getString("articlePage.amountInvalid");
			ErrorDialog err = new ErrorDialog((Stage)bpArticle.getParent().getScene().getWindow(), amountInvalidString);
			err.show();
			
			txAmount.setTooltip(new Tooltip(amountInvalidString));
			txAmount.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		}		
		return amount;		
	}	
	
	@FXML
	private void handleNext()
	{
		parentController.handleToCartpage();
	}
	@FXML
	private void handlePrevious()
	{
		parentController.handleToChooseClient();
	}
	
}
