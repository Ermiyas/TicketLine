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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class ClientSearchController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientSearchController.class);
	
	// just for mock up
	@Autowired
	private NewsService newsService;

    @FXML
    private TabPane tpFilterTabs;
    @FXML
    private Tab tpEventTab;
    @FXML
    private Tab tpPerformanceTab;
    @FXML
    private Tab tpLocationTab;
    @FXML
    private Tab tpArtistTab;
    @FXML
	private GridPane gpChooseSeats;
    @FXML
    private GridPane gpSearch;
    @FXML
    private GridPane gpSearch2;
    @FXML
    private GridPane gpTopTen;
	@FXML
    private BarChart<?, ?> bcTopTenChart;
    
	@FXML
    VBox vbSearchBox;
	
	@Override
	public void initialize(URL url, ResourceBundle resBundle) {				
		if(null != vbSearchBox){
			initFilterTabsBehaviour();
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
	
	private void initFilterTabsBehaviour() {		
		tpFilterTabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if(arg2.equals(tpEventTab)) {
					initEventTab();
				} else if(arg2.equals(tpPerformanceTab)) {
					initLocationTab();
				} else if(arg2.equals(tpPerformanceTab)) {
					initPerformanceTab();
				} else {
					initArtistTab();
				}
			}
		});
	}    
	
	private void initEventTab() {
		vbSearchBox.getChildren().clear();
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
	
	private void initPerformanceTab() {
		vbSearchBox.getChildren().clear();
	}
	
	private void initLocationTab() {
		vbSearchBox.getChildren().clear();
	}
	
	private void initArtistTab() {
		vbSearchBox.getChildren().clear();
	}
	
	@FXML
    void handleGoToTopTen(ActionEvent event) {
		gpSearch.setVisible(false);
		gpTopTen.setVisible(true);
    }
	
	@FXML
	void handleGoToSearch(ActionEvent event) {
		gpTopTen.setVisible(false);
		gpSearch.setVisible(true);
	}
}
