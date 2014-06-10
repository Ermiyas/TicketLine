package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.AuthService;
import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.client.rest.AuthRestClient;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.NewsPane;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.NewsDto;

@Controller
public class ClientMainController implements Initializable{
	private static final Logger LOG = Logger.getLogger(ClientMainController.class);
	
	private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AuthRestClient authRestClient;
	
	@FXML
    VBox vbNewsBox;
	
	@FXML
	TabPane tabPaneMain;
	
	@FXML
	private Button btnManageUsers;
	
	@FXML
	private Label lblLoginStatus;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {				
		if(null != vbNewsBox){
			initNewsBox();
		}
		String role = null;
		if(!authRestClient.getUserStatus().getRoles().contains("ADMIN")) {
			btnManageUsers.setVisible(false);
			role = BundleManager.getBundle().getString("salesperson");
		} else {
			role = BundleManager.getBundle().getString("admin");
		}
		lblLoginStatus.setText(String.format("%s %s %s %s.", BundleManager.getBundle().getString("startpage.logged_in_message"), role, authRestClient.getUserStatus().getFirstname(), authRestClient.getUserStatus().getLastname()));
	}
	
	private void initNewsBox(){
		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		
		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
		
		for(NewsDto n : news){
	        String newsText = new String(n.getNewsText());
	       	String title = new String(n.getTitle());
	        	
	       	vbNewsBox.getChildren().add(new NewsPane(title, df.format(n.getSubmittedOn()), newsText));
		}
	}
	
	@FXML
	private void handleExit(ActionEvent event){
		try {
			this.authService.logout();
		} catch (ServiceException e) {
			LOG.error("Logout failed: " + e.getMessage(), e);
		}
    	
    	Platform.exit();
	}
	
	@FXML
	private void handleLogout(ActionEvent event){
		try {
			this.authService.logout();
		} catch (ServiceException e) {
			LOG.error("Logout failed: " + e.getMessage(), e);
		}
		
		((Node) event.getSource()).setCursor(Cursor.WAIT);
		
		AnchorPane page = (AnchorPane) SpringFxmlLoader.getInstance().load("/gui/ClientLogin.fxml");
		Scene scene = new Scene(page);
		scene.getStylesheets().add("/gui/style.css");
		        
		Stage clientStage = new Stage();
		clientStage.setResizable(false);
		clientStage.setScene(scene);
		clientStage.setTitle(BundleManager.getBundle().getString("app_name"));
		clientStage.show();
		        
		((Node) event.getSource()).setCursor(Cursor.DEFAULT);
		        
		Node source = (Node)  event.getSource(); 
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleShowRewards(ActionEvent event){}
	
	@FXML
	private void handleNewTicket(ActionEvent event){}
	
	@FXML
	private void handleCancelTicket(ActionEvent event){
		createNewTab(BundleManager.getBundle().getString("stornopage"), "/gui/ItemStornoMainForm.fxml");
	}
	
	@FXML
	private void handleCancelReservation(ActionEvent event){}
	
	@FXML
	private void handleManageCustomers(ActionEvent event){
		createNewTab(BundleManager.getBundle().getString("startpage.manage_customers"), "/gui/CustomerManageGui.fxml");
    }
	
	
	@FXML	
	private void handeManageUsers(ActionEvent event){
		createNewTab(BundleManager.getBundle().getString("startpage.manage_users"), "/gui/ClientUserManagementGui.fxml");
	}
	
	/**
	 * Erstellt ein neues Tab auf der Hauptseite.
	 * @param tabText Der Text auf dem Tab
	 * @param fxmlPath Der Pfad zur FXML-Datei, welche in den Tab geladen werden soll
	 * @return Den neu erzeugten Tab
	 */
	private Tab createNewTab(String tabText, String fxmlPath){
		LOG.info(String.format("Creating tab '%s'", tabText));
		Tab tab = new Tab();
		tab.setClosable(true);
		tab.setText(tabText);
		tab.setContent((Node)SpringFxmlLoader.getInstance().load(fxmlPath));
		tabPaneMain.getTabs().add(tab);
		tabPaneMain.getSelectionModel().selectLast();
		return tab;
	}
	
	/**
	 * Schließt das gerade ausgewählte Tab
	 */
	public void closeSelectedTab() {
		tabPaneMain.getTabs().remove(tabPaneMain.getSelectionModel().getSelectedIndex());
	}
}
