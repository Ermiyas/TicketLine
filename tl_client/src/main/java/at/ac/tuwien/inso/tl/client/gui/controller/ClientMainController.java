package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.client.client.AuthService;
import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.NewsPane;
import at.ac.tuwien.inso.tl.dto.NewsDto;

@Component
public class ClientMainController implements Initializable{
	private static final Logger LOG = Logger.getLogger(ClientMainController.class);
	
	private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm";
	
	@Autowired
	private NewsService newsService;
	
	@Autowired
	private AuthService authService;
		
	@FXML
    VBox vbNewsBox;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {				
		if(null != vbNewsBox){
			initNewsBox();
		}
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
}
