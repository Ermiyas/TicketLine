package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.ArtistService;
import at.ac.tuwien.inso.tl.client.client.LocationService;
import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.client.PerformanceService;
import at.ac.tuwien.inso.tl.client.client.RowService;
import at.ac.tuwien.inso.tl.client.client.SeatService;
import at.ac.tuwien.inso.tl.client.client.ShowService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.*;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.ArtistDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

@Controller
@Scope("prototype")
public class ClientSearchController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientSearchController.class);
	private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	private static final SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
	
	private ListView<?> listview;
	private ListView<?> listviewEvents;
	private ListView<?> listviewPerformances;
	
	/**
	 * Enthält eine Referenz zum darüberliegenden SellTicketController.
	 * Dort ist die BasketDto für den gesamten Vorgang abgelegt.
	 * Wird während beim Initialisieren dieses Controllers gesetzt.
	 */
	private ClientSellTicketController parentController;
	private EventHandler<MouseEvent> handler;

	@Autowired
	private ArtistService artistService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private NewsService newsService;
	@Autowired
	private PerformanceService eventService;
	@Autowired
	private ShowService performanceService;
	@Autowired
	private RowService rowService;
	@Autowired
	private SeatService seatService;
	
	@FXML private StackPane spSearchStack;
	@FXML private TabPane tpFilterTabs;
	@FXML private Tab tpEventTab;
	@FXML private Tab tpPerformanceTab;
	@FXML private Tab tpLocationTab;
	@FXML private Tab tpArtistTab;
	@FXML private GridPane gpChooseSeats;
	@FXML private GridPane gpSearch;
	@FXML private GridPane gpSearchEvents;
	@FXML private GridPane gpSearchPerformances;
	@FXML private GridPane gpTopTen;
	@FXML private GridPane gpTopTenChart;
	@FXML private VBox vbSearchBox;
	@FXML private VBox vbSearchEventsBox;
	@FXML private VBox vbSearchPerformancesBox;
	@FXML private VBox vbTopTenBox;
	@FXML private ChoiceBox<String> chbTopTenCategory;
	@FXML private TextField tfEventTitle;
	@FXML private ChoiceBox<String> cbEventType;
	@FXML private Slider sldEventDuration;
	@FXML private TextField tfEventContent;
	@FXML private TextField tfPerformanceDateFrom;
	@FXML private TextField tfPerformanceDateTo;
	@FXML private TextField tfPerformanceTime1From;
	@FXML private TextField tfPerformanceTime2From;
	@FXML private TextField tfPerformanceTime1To;
	@FXML private TextField tfPerformanceTime2To;
	@FXML private Slider sldPerformancePrice;
	@FXML private TextField tfPerformanceRooms;
	@FXML private TextField tfLocationTitle;
	@FXML private TextField tfLocationStreet;
	@FXML private TextField tfLocationName;
	@FXML private TextField tfLocationPostalCode;
	@FXML private TextField tfLocationCountry;
	@FXML private TextField tfArtistFirstname;
	@FXML private TextField tfArtistLastname;
	
	@FXML private BorderPane bpChooseSeats1;
	@FXML private BorderPane bpChooseSeats2;
	@FXML private TableView<ObservableList<StringProperty>> tvChooseSeats;

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
			initControlListener();
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
	
	private void initControlListener() {
		sldEventDuration.valueProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				updateEventList();
			}
		});
		sldPerformancePrice.valueProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
				updatePerformanceList();
			}
		});
		cbEventType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				updateEventList();
			}
		});
		chbTopTenCategory.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
				updateTopTenList();
			}
		});
	} 

	private void initEventTab() {
		LOG.info("initEventTab clicked");
		vbSearchBox.getChildren().clear();

		List<PerformanceDto> events = null;
		int[] minMaxDuration = null;
		try {
			cbEventType.getItems().clear();
			List<String> categories = new ArrayList<String>();
			categories.add("");
			categories.addAll(this.eventService.getAllPerformanceTypes());
			cbEventType.getItems().addAll(categories);
			events = this.eventService.getAllPerformances();
			minMaxDuration = this.eventService.getMinAndMaxDuration();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve performances: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		listview = new ListView<EventPane>();
		listview.setMinWidth(vbSearchBox.getWidth());
		List<EventPane> eventList = new ArrayList<EventPane>();
		for(PerformanceDto p : events){
			Integer duration = p.getDurationInMinutes();
			eventList.add(new EventPane(p.getId(), p.getDescription(), 
										p.getPerformancetype(), duration, p.getContent()));
		}

		sldEventDuration.setMin(minMaxDuration[0]);
		sldEventDuration.setMax(minMaxDuration[1]);
		sldEventDuration.setBlockIncrement(10);
		listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initPerformanceTab() {
		LOG.info("initPerformanceTab clicked");
		vbSearchBox.getChildren().clear();

		List<ShowDto> performances = null;
		int[] minMaxPrice = null;
		try {
			performances = this.performanceService.getAllShows();
			minMaxPrice = this.performanceService.getMinMaxPriceInCent();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
		for(ShowDto s : performances){
			Date performanceDate = s.getDateOfPerformance();
			performanceList.add(new PerformancePane(s.getId(), "Titel der Aufführung", df.format(performanceDate), 
													df2.format(performanceDate), s.getPriceInCent(), s.getRoom()));
		}

		sldPerformancePrice.setMin(0);
		sldPerformancePrice.setMax((double)((minMaxPrice[1]-minMaxPrice[0])/100));
		sldPerformancePrice.setBlockIncrement(5);
		listview = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initLocationTab() {
		LOG.info("initLocationTab clicked");
		vbSearchBox.getChildren().clear();

		List<LocationDto> locations = null;
		try {
			locations = this.locationService.getAllLocations();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<LocationPane> locationList = new ArrayList<LocationPane>();
		for(LocationDto l : locations){	        	
			locationList.add(new LocationPane(l.getId(), l.getDescription(), l.getStreet(), l.getCity(), l.getPostalcode(), l.getCountry()));
		}

		listview = new ListView<LocationPane>(FXCollections.observableArrayList(locationList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initArtistTab() {
		LOG.info("initArtistTab clicked");
		vbSearchBox.getChildren().clear();

		List<ArtistDto> artists = null;
		try {
			artists = this.artistService.getAllArtists();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}

		List<ArtistPane> artistList = new ArrayList<ArtistPane>();
		for(ArtistDto a : artists){	        	
			artistList.add(new ArtistPane(a.getId(), a.getFirstname(), a.getLastname()));
		}

		listview = new ListView<ArtistPane>(FXCollections.observableArrayList(artistList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		vbSearchBox.getChildren().add(listview);
	}

	private void initTopTen() {
		try {
			LOG.info("initEventTab clicked");
			gpTopTenChart.getChildren().clear();
			gpTopTenChart.add(new TopTenBarChartPane(), 0, 1);
			vbTopTenBox.getChildren().clear();
			chbTopTenCategory.getItems().clear();
			List<String> categories = new ArrayList<String>();
			categories.addAll(this.eventService.getAllPerformanceTypes());
			chbTopTenCategory.getItems().addAll(categories);
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = 
					this.eventService.findPerformancesSortedBySales("", "", null, null, categories.get(0), null);
			
			List<EventPane> eventList = new ArrayList<EventPane>();
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				eventList.add(new EventPane(p.getId(), p.getDescription(), 
						p.getPerformancetype(), p.getDurationInMinutes(), p.getContent()));
			}
			
			listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listview.setMinWidth(vbTopTenBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbTopTenBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
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
	
	private void updateEventList() {
		try {
			vbSearchBox.getChildren().clear();
			List<EventPane> eventList = new ArrayList<EventPane>();
			String description = tfEventTitle.getText().isEmpty() ? null : tfEventTitle.getText();
			Integer duration = (int)sldEventDuration.getValue();
			Integer durationMin = ((duration-30) < (int)sldEventDuration.getMin()) ? (int)sldEventDuration.getMin() : duration-30;
			Integer durationMax = ((duration+30) > (int)sldEventDuration.getMax()) ? (int)sldEventDuration.getMax() : duration+30;
			String type = null;
			if(cbEventType.getSelectionModel().getSelectedItem() != null) {
				type = cbEventType.getSelectionModel().getSelectedItem().equals("") ? null : cbEventType.getSelectionModel().getSelectedItem();
			}
			String content = tfEventContent.getText().isEmpty() ? null : tfEventContent.getText();
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = eventService.findPerformancesSortedBySales(
					content, description, durationMin, durationMax, type, null);
						
			LOG.info("keyValueList is empty: " + keyValues.isEmpty());
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				eventList.add(new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), p.getDurationInMinutes(), p.getContent()));
			}

			listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listview.setMinWidth(vbSearchBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbSearchBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not update events: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void updatePerformanceList() {
		try {
			vbSearchBox.getChildren().clear();
			List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
			Date dateFrom = tfPerformanceDateFrom.getText().isEmpty() ? null : df.parse(tfPerformanceDateFrom.getText());
			Date dateTo = tfPerformanceDateTo.getText().isEmpty() ? null : df.parse(tfPerformanceDateTo.getText());
			String time1From = tfPerformanceTime1From.getText().isEmpty() ? null : tfPerformanceTime1From.getText();
			String time2From = tfPerformanceTime2From.getText().isEmpty() ? null : tfPerformanceTime2From.getText();
			String time1To = tfPerformanceTime1To.getText().isEmpty() ? null : tfPerformanceTime1To.getText();
			String time2To = tfPerformanceTime2To.getText().isEmpty() ? null : tfPerformanceTime2To.getText();
			Date timeFrom = (time1From != null && time2From != null) ? df2.parse(time1From + ":" + time2From) : null;
			Date timeTo = (time1To != null && time2To != null) ? df2.parse(time1To + ":" + time2To) : null;
			int[] minMaxPrice = this.performanceService.getMinMaxPriceInCent();
			Integer price = (int)sldPerformancePrice.getValue()*100;
			Integer priceMin = ((price-1000) < (int)sldPerformancePrice.getMin()*100) ? (int)sldPerformancePrice.getMin()*100 : price-1000;
			Integer priceMax = ((price+1000) > (int)sldPerformancePrice.getMax()*100) ? (int)sldPerformancePrice.getMax()*100 : price+1000;
			String room = tfPerformanceRooms.getText().isEmpty() ? null : tfPerformanceRooms.getText();
			List<ShowDto> performances = performanceService.findShows(dateFrom, dateTo, timeFrom, timeTo, minMaxPrice[0]/100-priceMin, 
																	  minMaxPrice[0]+priceMax, room, null, null);
			for(ShowDto s : performances) {
				String date = df.format(s.getDateOfPerformance());
				String time = df2.format(s.getDateOfPerformance());
				//TODO: Methode, die mir die einer Aufführung zugehörigen Veranstaltungen liefert.
				performanceList.add(new PerformancePane(s.getId(), "Titel der Aufführung", date, time, s.getPriceInCent(), s.getRoom()));
			}
			listview = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
			listview.setMinWidth(vbSearchBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbSearchBox.getChildren().add(listview);
		} catch (ServiceException | ParseException e) {
			LOG.error("Could not update events: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void updateLocationList() {
		try {
			vbSearchBox.getChildren().clear();
			List<LocationPane> locationList = new ArrayList<LocationPane>();
			String title = tfLocationTitle.getText().isEmpty() ? null : tfLocationTitle.getText();
			String street = tfLocationStreet.getText().isEmpty() ? null : tfLocationStreet.getText();
			String name = tfLocationName.getText().isEmpty() ? null : tfLocationName.getText();
			String postal = tfLocationPostalCode.getText().isEmpty() ? null : tfLocationPostalCode.getText();
			String country = tfLocationCountry.getText().isEmpty() ? null : tfLocationCountry.getText();
			List<LocationDto> locations = locationService.findLocations(name, country, title, postal, street);
			for(LocationDto l : locations) {
				locationList.add(new LocationPane(l.getId(), l.getDescription(), l.getStreet(), l.getCity(), l.getPostalcode(), l.getCountry()));
			}
			listview = new ListView<LocationPane>(FXCollections.observableArrayList(locationList));
			listview.setMinWidth(vbSearchBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbSearchBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not update events: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void updateArtistList() {
		try {
			vbSearchBox.getChildren().clear();
			List<ArtistPane> artistList = new ArrayList<ArtistPane>();
			String firstname = tfArtistFirstname.getText().isEmpty() ? null : tfArtistFirstname.getText();
			String lastname = tfArtistLastname.getText().isEmpty() ? null : tfArtistLastname.getText();
			List<ArtistDto> artists = artistService.findArtists(firstname, lastname);
			for(ArtistDto a : artists) {
				artistList.add(new ArtistPane(a.getId(), a.getFirstname(), a.getLastname()));
			}
			listview = new ListView<ArtistPane>(FXCollections.observableArrayList(artistList));
			listview.setMinWidth(vbSearchBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbSearchBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not update events: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void updateTopTenList() {
		try {
			vbTopTenBox.getChildren().clear();
			List<EventPane> eventList = new ArrayList<EventPane>();
			String type = null;
			if(chbTopTenCategory.getSelectionModel().getSelectedItem() != null) {
				type = chbTopTenCategory.getSelectionModel().getSelectedItem().equals("") ? null : chbTopTenCategory.getSelectionModel().getSelectedItem();
			}
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = eventService.findPerformancesSortedBySales(null, null, null, null, type, null);
						
			LOG.info("keyValueList is empty: " + keyValues.isEmpty());
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				eventList.add(new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), p.getDurationInMinutes(), p.getContent()));
			}

			listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listview.setMinWidth(vbTopTenBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbTopTenBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not update events: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
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
			findSeatsByPerformance();
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
		try {
			LOG.info("getPerformancesByEvent");
			EventPane eventPane = (EventPane)listview.getSelectionModel().getSelectedItem();
			String title = eventPane.getEventTitle();
			List<ShowDto> performances = performanceService.findShows(null, null, null, null, null, null, null, null, eventPane.getEventId());

			vbSearchPerformancesBox.getChildren().clear();
			List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
			for(ShowDto s : performances) {
				String date = df.format(s.getDateOfPerformance());
				String time = df2.format(s.getDateOfPerformance());
				performanceList.add(new PerformancePane(s.getId(), title, date, time, s.getPriceInCent(), s.getRoom()));
			}
			
			listviewPerformances = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
			listviewPerformances.setMinWidth(vbSearchPerformancesBox.getWidth());
			listviewPerformances.setOnMouseClicked(handler);
			vbSearchPerformancesBox.getChildren().add(listviewPerformances);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void findPerformancesByLocation() {
		try {
			LOG.info("getPerformancesByEvent");
			LocationPane locationPane = (LocationPane)listview.getSelectionModel().getSelectedItem();
			List<ShowDto> performances = performanceService.findShows(null, null, null, null, null, null, null, locationPane.getLocationId(), null);

			vbSearchPerformancesBox.getChildren().clear();
			List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
			for(ShowDto s : performances){
				String date = df.format(s.getDateOfPerformance());
				String time = df2.format(s.getDateOfPerformance());
				performanceList.add(new PerformancePane(s.getId(), "Titel", date, time, s.getPriceInCent(), s.getRoom()));
			}
	
			listviewPerformances = new ListView<PerformancePane>(FXCollections.observableArrayList(performanceList));
			listviewPerformances.setMinWidth(vbSearchPerformancesBox.getWidth());
			listviewPerformances.setOnMouseClicked(handler);
			vbSearchPerformancesBox.getChildren().add(listviewPerformances);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void findEventsByArtist() {		
		try {
			LOG.info("initEventTab clicked");
			ArtistPane artistPane = (ArtistPane)listview.getSelectionModel().getSelectedItem();
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = 
					this.eventService.findPerformancesSortedBySales(null, null, null, null, null, artistPane.getArtistId());
			
			vbSearchEventsBox.getChildren().clear();
			List<EventPane> eventList = new ArrayList<EventPane>();
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				eventList.add(new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), p.getDurationInMinutes(), p.getContent()));
			}
			
			listviewEvents = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listviewEvents.setMinWidth(vbSearchEventsBox.getWidth());
			listviewEvents.setOnMouseClicked(handler);
			vbSearchEventsBox.getChildren().add(listviewEvents);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	private void findSeatsByPerformance() {
		try {
			//tvChooseSeats = new TableView();
			PerformancePane performancePane = (PerformancePane)listviewPerformances.getSelectionModel().getSelectedItem();
			List<RowDto> rows = rowService.findRows(performancePane.getPerformanceId());
			for(RowDto r : rows) {
				List<SeatDto> seats = seatService.findSeats(r.getId());
				for(SeatDto s : seats) {
					s.getId();
					//TODO: Create the table view
				}
			}
			//temporary solution of the table view
			String[] dataValues = new String[10];
			for(int i = 0; i < dataValues.length; i++) {
				dataValues[i] = "";
				TableColumn<ObservableList<StringProperty>, String> tc = new TableColumn<>("Column");
				tc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList<StringProperty>, String>, ObservableValue<String>>() {
		          @Override
		          public ObservableValue<String> call(CellDataFeatures<ObservableList<StringProperty>, String> cellDataFeatures) {
		            //ObservableList<StringProperty> values = cellDataFeatures.getValue();
		            return cellDataFeatures.getValue().get(0);
		          }
		        });
				tc.setSortable(false);
				tvChooseSeats.getColumns().add(tc);
			}
			for(int i = 0; i < tvChooseSeats.getColumns().size(); i++) {
				ObservableList<StringProperty> data = FXCollections.observableArrayList();
		        for (String value : dataValues) {
		        	data.add(new SimpleStringProperty(value));
		        }
		        tvChooseSeats.getItems().add(data);
			}
		} catch (ServiceException e) {
			LOG.error("Could not retrieve news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	@FXML
	void handleEventTitleChanged(KeyEvent event) {
		updateEventList();
	}
	
	@FXML
	void handleEventContentChanged(KeyEvent event) {
		updateEventList();
	}
	
	@FXML
	void handlePerformanceDateFromChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceDateToChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime1FromChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime2FromChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime1ToChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime2ToChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceRoomsChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handleLocationTitleChanged(KeyEvent event) {
		updateLocationList();
	}
	
	@FXML
	void handleLocationStreetChanged(KeyEvent event) {
		updateLocationList();
	}
	
	@FXML
	void handleLocationNameChanged(KeyEvent event) {
		updateLocationList();
	}
	
	@FXML
	void handleLocationPostalCodeChanged(KeyEvent event) {
		updateLocationList();
	}
	
	@FXML
	void handleLocationCountryChanged(KeyEvent event) {
		updateLocationList();
	}

	@FXML
	void handleArtistFirstnameChanged(KeyEvent event) {
		updateArtistList();
	}
	
	@FXML
	void handleArtistLastnameChanged(KeyEvent event) {
		updateArtistList();
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
		Image imgWorkflow = new Image(SpringFxmlLoader.class.getResource("/images/ClientStep.png").toString());
		ImageView iv = (ImageView)spSearchStack.getParent().lookup("#ivWorkflow");
		iv.setImage(imgWorkflow);
		getParentController().setCenterContent("/gui/ClientChooseClientGui.fxml");
		
	}

	private ClientSellTicketController getParentController() {
		return parentController;
	}

	public void setParentController(ClientSellTicketController cont) {
		this.parentController = cont;
	}
}