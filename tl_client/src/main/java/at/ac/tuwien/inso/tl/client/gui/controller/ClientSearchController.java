package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.NewsPane;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class ClientSearchController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientSearchController.class);
	
	// just for mock up
	@Autowired
	private NewsService newsService;
	
	@FXML
    VBox vbSearchBox;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {				
		if(null != vbSearchBox){
			initSearchBox();
		}
	}
	
	/**
	 * TODO: initialize box with top ten events from either category
	 */
	private void initSearchBox(){
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		
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
	        	
	       	vbSearchBox.getChildren().add(new NewsPane(title, df.format(n.getSubmittedOn()), newsText));
		}
	}
}
