package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.*;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.NewsDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Controller
@Scope("prototype")
public class ClientSearchController implements Initializable {
	private static final Logger LOG = Logger.getLogger(ClientSearchController.class);
	private EventHandler<MouseEvent> handler;

	@Autowired
	private NewsService newsService;
	@FXML
	private StackPane spSearchStack;
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
	private GridPane gpSearchEvents;
	@FXML
	private GridPane gpSearchPerformances;
	@FXML
	private GridPane gpTopTen;
	@FXML
	private GridPane gpTopTenChart;
	@FXML
	private VBox vbSearchBox;
	@FXML
	private VBox vbSearchEventsBox;
	@FXML
	private VBox vbSearchPerformancesBox;
	@FXML
	private VBox vbTopTenBox;
	@FXML
	private ChoiceBox<String> chbTopTenCategory;

	@Override
	public void initialize(URL url, ResourceBundle resBundle) {				
		if(null != vbSearchBox){
			handler = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						if(mouseEvent.getClickCount() == 2){
							updateResultList();
						}
					}
				}
			};
			initFilterTabsBehaviour();
			initEventTab();
		}
	}

	private void initFilterTabsBehaviour() {		
		tpFilterTabs.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> arg0, Tab arg1, Tab arg2) {
				if(arg2.equals(tpEventTab)) {
					initEventTab();
				} else if(arg2.equals(tpPerformanceTab)) {
					initPerformanceTab();
				} else if(arg2.equals(tpLocationTab)) {
					initLocationTab();
				} else {
					initArtistTab();
				}
			}
		});
	}    

	private void initEventTab() {
		LOG.info("initEventTab clicked");
		vbSearchBox.getChildren().clear();

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<EventPane> eventList = new ArrayList<EventPane>();
		for(NewsDto n : news){
			String newsText = new String(n.getNewsText());
			String title = new String(n.getTitle());
			eventList.add(new EventPane(title, "Veranstaltungstyp", 30, newsText));
		}

		ListView<EventPane> listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setMinWidth(vbTopTenBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initPerformanceTab() {
		LOG.info("initPerformanceTab clicked");
		vbSearchBox.getChildren().clear();
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
		for(NewsDto n : news){
			String newsText = new String(n.getNewsText());
			String title = new String(n.getTitle());
			performanceList.add(new PerformancePane(title, df.format(n.getSubmittedOn()), "00:30", 19.99, newsText));
		}

		ListView<PerformancePane> listview = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initLocationTab() {
		LOG.info("initLocationTab clicked");
		vbSearchBox.getChildren().clear();

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<LocationPane> locationList = new ArrayList<LocationPane>();
		for(NewsDto n : news){
			String title = new String(n.getTitle());	        	
			locationList.add(new LocationPane(title, "Straße", "Ort", 1234, "Land"));
		}

		ListView<LocationPane> listview = new ListView<LocationPane>(FXCollections.observableArrayList(locationList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initArtistTab() {
		LOG.info("initArtistTab clicked");
		vbSearchBox.getChildren().clear();

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<ArtistPane> artistList = new ArrayList<ArtistPane>();
		for(NewsDto n : news){	        	
			artistList.add(new ArtistPane("Vorname" + " " + "Nachame", "Flavortext"));
		}

		ListView<ArtistPane> listview = new ListView<ArtistPane>(FXCollections.observableArrayList(artistList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initTopTen() {
		LOG.info("initEventTab clicked");
		gpTopTenChart.add(new TopTenBarChartPane(),0,1);
		chbTopTenCategory.getSelectionModel().selectFirst();
		vbTopTenBox.getChildren().clear();

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<EventPane> eventList = new ArrayList<EventPane>();
		for(NewsDto n : news){
			String newsText = new String(n.getNewsText());
			String title = new String(n.getTitle());
			eventList.add(new EventPane(title, "Konzert", 30, newsText));
		}

		ListView<EventPane> listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
		listview.setMinWidth(vbTopTenBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbTopTenBox.getChildren().add(listview);
	}

	@FXML
	void handleGoToTopTen(ActionEvent event) {
		gpSearch.setVisible(false);
		initTopTen();
		gpTopTen.setVisible(true);
	}

	@FXML
	void handleGoToSearch(ActionEvent event) {
		gpTopTen.setVisible(false);
		initEventTab();
		gpSearch.setVisible(true);
	}

	private void updateResultList() {
		if(gpTopTen.isVisible()) {
			gpTopTen.setVisible(false);
			findPerformancesByEvent();
			gpSearchPerformances.setVisible(true);
		} else if(gpSearchEvents.isVisible()) {
			gpSearchEvents.setVisible(false);
			findPerformancesByEvent();
			gpSearchPerformances.setVisible(true);
		} else if(gpSearchPerformances.isVisible()) {
			gpSearchPerformances.setVisible(false);
			findPerformancesByEvent();
			gpChooseSeats.setVisible(true);
		} else {
			Tab current = tpFilterTabs.getSelectionModel().getSelectedItem();
			if(current.equals(tpEventTab)) {
				gpSearch.setVisible(false);
				findPerformancesByEvent();
				gpSearchPerformances.setVisible(true);
			} else if(current.equals(tpPerformanceTab)) {
				gpSearch.setVisible(false);
				gpChooseSeats.setVisible(true);
			} else if(current.equals(tpLocationTab)) {
				gpSearch.setVisible(false);
				findPerformancesByLocation();
				gpSearchPerformances.setVisible(true);
			} else {
				gpSearch.setVisible(false);
				findEventsByArtist();
				gpSearchEvents.setVisible(true);
			}
		}
	}
	
	private void findPerformancesByEvent() {
		LOG.info("getPerformancesByEvent");
		vbSearchPerformancesBox.getChildren().clear();
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
		for(NewsDto n : news){
			String newsText = new String(n.getNewsText());
			String title = new String(n.getTitle());
			performanceList.add(new PerformancePane(title, df.format(n.getSubmittedOn()), "00:30", 19.99, newsText));
		}

		ListView<PerformancePane> listview = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
		listview.setMinWidth(vbSearchPerformancesBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchPerformancesBox.getChildren().add(listview);
	}
	
	private void findPerformancesByLocation() {
		LOG.info("getPerformancesByEvent");
		vbSearchPerformancesBox.getChildren().clear();
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
		for(NewsDto n : news){
			String newsText = new String(n.getNewsText());
			String title = new String(n.getTitle());
			performanceList.add(new PerformancePane(title, df.format(n.getSubmittedOn()), "00:30", 19.99, newsText));
		}

		ListView<PerformancePane> listview = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
		listview.setMinWidth(vbSearchPerformancesBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchPerformancesBox.getChildren().add(listview);
	}
	
	private void findEventsByArtist() {
		LOG.info("initEventTab clicked");
		vbSearchEventsBox.getChildren().clear();

		List<NewsDto> news = null;
		try {
			news = this.newsService.getNews();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<EventPane> eventList = new ArrayList<EventPane>();
		for(NewsDto n : news){
			String newsText = new String(n.getNewsText());
			String title = new String(n.getTitle());
			eventList.add(new EventPane(title, "Veranstaltungstyp", 30, newsText));
		}

		ListView<EventPane> listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
		listview.setMinWidth(vbSearchEventsBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchEventsBox.getChildren().add(listview);
	}
	
	@FXML
	void handleSelectEventsFromSearchEvents(ActionEvent event) {
		LOG.info("handleSelectPerformanceFromSearchPerformances clicked");
		gpSearchEvents.setVisible(false);
		findPerformancesByEvent();
		gpSearchPerformances.setVisible(true);
	}
	
	@FXML
	void handleReturnFromSearchEvents(ActionEvent event) {
		LOG.info("handleReturnFromSearchEvents clicked");
		gpSearchEvents.setVisible(false);
		gpSearch.setVisible(true);
	}
	
	@FXML
	void handleSelectPerformanceFromSearchPerformances(ActionEvent event) {
		LOG.info("handleSelectPerformanceFromSearchPerformances clicked");
		gpSearchPerformances.setVisible(false);
		gpChooseSeats.setVisible(true);
	}
	
	@FXML
	void handleReturnFromSearchPerformances(ActionEvent event) {
		LOG.info("handleReturnFromSearchPerformances clicked");
		gpSearchPerformances.setVisible(false);
		gpSearch.setVisible(true);
	}
	
	@FXML
	void handleReserveSeats(ActionEvent event) {
		LOG.info("handleReserveSeats clicked");
		BorderPane parent = (BorderPane)spSearchStack.getParent().lookup("#bpSellTicket");
		Image imgWorkflow = new Image(SpringFxmlLoader.class.getResource("/images/ClientStep.png").toString());
		ImageView iv = (ImageView)spSearchStack.getParent().lookup("#ivWorkflow");
		iv.setImage(imgWorkflow);
		parent.setCenter((Node)SpringFxmlLoader.getInstance().load("/gui/ClientChooseClientGui.fxml"));
	}
}
