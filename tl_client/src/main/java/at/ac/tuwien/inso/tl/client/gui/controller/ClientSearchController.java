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
import at.ac.tuwien.inso.tl.client.client.EntryService;
import at.ac.tuwien.inso.tl.client.client.LocationService;
import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.client.PerformanceService;
import at.ac.tuwien.inso.tl.client.client.RowService;
import at.ac.tuwien.inso.tl.client.client.SeatService;
import at.ac.tuwien.inso.tl.client.client.ShowService;
import at.ac.tuwien.inso.tl.client.client.TicketService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.gui.pane.*;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.ArtistDto;
import at.ac.tuwien.inso.tl.dto.KeyValuePairDto;
import at.ac.tuwien.inso.tl.dto.LocationDto;
import at.ac.tuwien.inso.tl.dto.PerformanceDto;
import at.ac.tuwien.inso.tl.dto.RowDto;
import at.ac.tuwien.inso.tl.dto.SeatDto;
import at.ac.tuwien.inso.tl.dto.ShowDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Controller
@Scope("prototype")
public class ClientSearchController implements Initializable, ISellTicketSubController {
	private static final Logger LOG = Logger.getLogger(ClientSearchController.class);
	private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	private static final SimpleDateFormat df2 = new SimpleDateFormat("HH:mm");
	
	//TODO: Eventuell Scrollbar Verhalten verbessern
	private ListView<?> listview;
	private ListView<?> listviewEvents;
	private ListView<?> listviewPerformances;
	
	private SeatingPlanPane seatingPlanPane;
	private boolean seatingPlanAlreadyVisited;
	
	/**
	 * Enthält eine Referenz zum darüberliegenden SellTicketController.
	 * Dort ist die BasketDto für den gesamten Vorgang abgelegt.
	 * Wird während beim Initialisieren dieses Controllers gesetzt.
	 */
	private ClientSellTicketController parentController;
	private EventHandler<MouseEvent> handler;
	private EventHandler<MouseEvent> handlerEventsOfArtist;

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
	@Autowired
	private EntryService entryService;
	@Autowired
	private TicketService ticketService;
	
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
	@FXML private TextField tfEventDuration;
	@FXML private TextField tfEventContent;
	@FXML private TextField tfPerformanceDateFrom;
	@FXML private TextField tfPerformanceDateTo;
	@FXML private TextField tfPerformanceTime1From;
	@FXML private TextField tfPerformanceTime2From;
	@FXML private TextField tfPerformanceTime1To;
	@FXML private TextField tfPerformanceTime2To;
	@FXML private TextField tfPerformancePrice;
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
			handlerEventsOfArtist = new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
						if(mouseEvent.getClickCount() == 2){
							gpSearchEvents.setVisible(false);
							findPerformancesByEventOfArtist();
							gpSearchPerformances.setVisible(true);
							gpSearchEvents.toBack();
						}
					}
				}
			};
			initFilterTabsBehaviour();
			initEventTab();
			initControlListener();
			seatingPlanAlreadyVisited = false;
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

		List<PerformanceDto> events = null;
		int[] minMaxDuration = null;
		try {
			cbEventType.getItems().clear();
			List<String> categories = new ArrayList<String>();
			categories.add(BundleManager.getBundle().getString("searchpage.event.allTypes"));
			categories.addAll(this.eventService.getAllPerformanceTypes());
			cbEventType.getItems().addAll(categories);
			cbEventType.getSelectionModel().selectFirst();
			events = this.eventService.getAllPerformances();
			minMaxDuration = this.eventService.getMinAndMaxDuration();
		} catch (ServiceException e) {
			LOG.error("Could not retrieve events: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.load_events_error"));
			error.show();
			return;
		}

		listview = new ListView<EventPane>();
		listview.setMinWidth(vbSearchBox.getWidth());
		List<EventPane> eventList = new ArrayList<EventPane>();
		for(PerformanceDto p : events){
			Integer duration = p.getDurationInMinutes();
			eventList.add(new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), 
										duration, p.getContent(), null, false));
		}

		sldEventDuration.setMin(0);
		sldEventDuration.setMax((double)(minMaxDuration[1]-minMaxDuration[0]));
		sldEventDuration.setBlockIncrement(10);
		tfEventDuration.setText(String.valueOf(minMaxDuration[0]));
		listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
		listview.setMinWidth(vbSearchBox.getWidth());
		listview.setOnMouseClicked(handler);
		if(vbSearchBox.getChildren().isEmpty()) {
			vbSearchBox.getChildren().add(listview);
		}
	}

	private void initPerformanceTab() {
		LOG.info("initPerformanceTab clicked");
		vbSearchBox.getChildren().clear();

		List<ShowDto> performances = null;
		List<PerformancePane> performanceList = new ArrayList<PerformancePane>();
		int[] minMaxPrice = null;
		try {
			performances = this.performanceService.getAllShows();
			minMaxPrice = this.performanceService.getMinMaxPriceInCent();
			
			for(ShowDto s : performances){
				Date performanceDate = s.getDateOfPerformance();
				PerformanceDto p = this.eventService.findPerformanceByShow(s.getId());
				performanceList.add(new PerformancePane(s.getId(), p.getDescription(), df.format(performanceDate), 
														df2.format(performanceDate), s.getPriceInCent(), s.getRoom()));
			}
		} catch (ServiceException e) {
			LOG.error("Could not retrieve performances: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.load_performances_error"));
			error.show();
			return;
		}

		sldPerformancePrice.setMin(0);
		sldPerformancePrice.setMax((double)((minMaxPrice[1]-minMaxPrice[0])/100));
		sldPerformancePrice.setBlockIncrement(5);
		tfPerformancePrice.setText(String.valueOf((double)(minMaxPrice[0]/100)));
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
			LOG.error("Could not retrieve locations: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.load_locations_error"));
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
			LOG.error("Could not retrieve artists: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.load_artists_error"));
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
			vbTopTenBox.getChildren().clear();
			chbTopTenCategory.getItems().clear();
			List<String> categories = new ArrayList<String>();
			categories.add(BundleManager.getBundle().getString("searchpage.topten.allCategories"));
			categories.addAll(this.eventService.getAllPerformanceTypes());
			chbTopTenCategory.getItems().addAll(categories);
			chbTopTenCategory.getSelectionModel().selectFirst();
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = null;
				keyValues = this.eventService.findPerformancesSortedBySales(null, null, null, null, null, null);
			if(keyValues == null) {
				gpTopTen.setVisible(false);
				gpSearch.setVisible(true);
			}
			List<EventPane> eventList = new ArrayList<EventPane>();
			int ranking = 1;
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				EventPane pane = new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), p.getDurationInMinutes(), 
											   p.getContent(), keyValue.getValue(), true);
				pane.initForTopTen(ranking);
				eventList.add(pane);
				ranking++;
			}
			
			gpTopTenChart.getChildren().clear();
			gpTopTenChart.add(new TopTenBarChartPane(eventList), 0, 0);
			
			listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listview.setMinWidth(vbTopTenBox.getWidth());
			listview.setMinHeight(vbTopTenBox.getHeight());
			listview.setOnMouseClicked(handler);
			vbTopTenBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve the Top Ten: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("topten.load_error"));
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
			int[] minMaxDuration = this.eventService.getMinAndMaxDuration();
			LOG.info("DAO minDuration: " + minMaxDuration[0] + ", maxDuration: " + minMaxDuration[1]);
			Integer duration = (int)sldEventDuration.getValue();
			tfEventDuration.setText(String.valueOf(minMaxDuration[0]+duration));
			Integer durationMin = ((duration-30) < (int)sldEventDuration.getMin()) ? (int)sldEventDuration.getMin() : duration-30;
			Integer durationMax = ((duration+30) > (int)sldEventDuration.getMax()) ? (int)sldEventDuration.getMax() : duration+30;
			String type = null;
			if(cbEventType.getSelectionModel().getSelectedItem() != null) {
				String allTypes = BundleManager.getBundle().getString("searchpage.event.allTypes");
				type = cbEventType.getSelectionModel().getSelectedItem().equals(allTypes) ? null : cbEventType.getSelectionModel().getSelectedItem();
			}
			String content = tfEventContent.getText().isEmpty() ? null : tfEventContent.getText();
			LOG.info("minDuration: " + (minMaxDuration[0]+durationMin) + ", maxDuration: " + (minMaxDuration[0]+durationMax));
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = eventService.findPerformancesSortedBySales(
					content, description, minMaxDuration[0]+durationMin, minMaxDuration[0]+durationMax, type, null);
			
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				eventList.add(new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), 
											p.getDurationInMinutes(), p.getContent(), keyValue.getValue(), false));
			}

			listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listview.setMinWidth(vbSearchBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbSearchBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not update events: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.update_events_error"));
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
			tfPerformancePrice.setText(String.valueOf((double)((minMaxPrice[0]+price)/100)));
			Integer priceMin = ((price-1000) < (int)sldPerformancePrice.getMin()*100) ? (int)sldPerformancePrice.getMin()*100 : price-1000;
			Integer priceMax = ((price+1000) > (int)sldPerformancePrice.getMax()*100) ? (int)sldPerformancePrice.getMax()*100 : price+1000;
			String room = tfPerformanceRooms.getText().isEmpty() ? null : tfPerformanceRooms.getText();
			List<ShowDto> performances = null;
			if(dateFrom == null && dateFrom == null && timeFrom == null && timeTo == null) {
				performances = performanceService.findShows(null, null, null, null, null, null, room, null, null);
			} else {
				performances = performanceService.findShows(dateFrom, dateTo, timeFrom, timeTo, minMaxPrice[0]+priceMin, 
																		  minMaxPrice[0]+priceMax, room, null, null);
			}
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
			LOG.error("Could not update performances: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.update_performances_error"));
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
			LOG.error("Could not update locations: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.update_locations_error"));
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
			LOG.error("Could not update artists: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.update_artists_error"));
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
				String allCategories = BundleManager.getBundle().getString("searchpage.topten.allCategories");
				type = chbTopTenCategory.getSelectionModel().getSelectedItem().equals(allCategories) ? null : chbTopTenCategory.getSelectionModel().getSelectedItem();
			}
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = eventService.findPerformancesSortedBySales(null, null, null, null, type, null);
			
			int ranking = 1;
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				EventPane pane = new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), 
											   p.getDurationInMinutes(), p.getContent(), keyValue.getValue(), true);
				pane.initForTopTen(ranking);
				eventList.add(pane);
				ranking++;
			}
			
			gpTopTenChart.getChildren().clear();
			gpTopTenChart.add(new TopTenBarChartPane(eventList), 0, 0);

			listview = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listview.setMinWidth(vbTopTenBox.getWidth());
			listview.setOnMouseClicked(handler);
			vbTopTenBox.getChildren().add(listview);
		} catch (ServiceException e) {
			LOG.error("Could not update the Top Ten: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("toten.update_error"));
			error.show();
			return;
		}
	}

	private void updateResultList() {
		if(gpTopTen.isVisible()) {
			if(listview.getSelectionModel().getSelectedItem() == null) {
				return;
			}
			gpTopTen.setVisible(false);
			findPerformancesByEvent();
			gpSearchPerformances.setVisible(true);
			gpTopTen.toBack();
		} else if(gpSearchEvents.isVisible()) {
			if(listviewEvents.getSelectionModel().getSelectedItem() == null) {
				return;
			}
			gpSearchEvents.setVisible(false);
			findPerformancesByEvent();
			gpSearchPerformances.setVisible(true);
			gpSearchEvents.toBack();
		} else if(gpSearchPerformances.isVisible()) {
			if(listviewPerformances.getSelectionModel().getSelectedItem() == null) {
				return;
			}
			gpSearchPerformances.setVisible(false);
			findSeatsByPerformance();
			gpChooseSeats.setVisible(true);
			gpSearchPerformances.toBack();
		} else {
			Tab current = tpFilterTabs.getSelectionModel().getSelectedItem();
			if(current.equals(tpEventTab)) {
				if(listview.getSelectionModel().getSelectedItem() == null) {
					return;
				}
				findPerformancesByEvent();
				gpSearchPerformances.setVisible(true);
			} else if(current.equals(tpPerformanceTab)) {
				if(listview.getSelectionModel().getSelectedItem() == null) {
					return;
				}
				findSeatsByPerformanceSearch();
				gpChooseSeats.setVisible(true);
			} else if(current.equals(tpLocationTab)) {
				if(listview.getSelectionModel().getSelectedItem() == null) {
					return;
				}
				findPerformancesByLocation();
				gpSearchPerformances.setVisible(true);
			} else {
				if(listview.getSelectionModel().getSelectedItem() == null) {
					return;
				}
				findEventsByArtist();
				gpSearchEvents.setVisible(true);
			}
			gpSearch.setVisible(false);
			gpSearch.toBack();
		}
	}
	
	private void findPerformancesByEvent() {
		try {
			LOG.info("findPerformancesByEvent");
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
			listviewPerformances.setMinHeight(vbSearchPerformancesBox.getHeight());
			listviewPerformances.setOnMouseClicked(handler);
			vbSearchPerformancesBox.getChildren().add(listviewPerformances);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve performances by event: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.performance_event_error"));
			error.show();
			return;
		}
	}
	
	private void findPerformancesByEventOfArtist() {
		try {
			LOG.info("findPerformancesByEventOfArtist");
			EventPane eventPane = (EventPane)listviewEvents.getSelectionModel().getSelectedItem();
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
			listviewPerformances.setMinHeight(vbSearchPerformancesBox.getHeight());
			listviewPerformances.setOnMouseClicked(handler);
			vbSearchPerformancesBox.getChildren().add(listviewPerformances);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve performances by event of an artist: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.performance_event_artist_error"));
			error.show();
			return;
		}
	}
	
	private void findPerformancesByLocation() {
		try {
			LOG.info("findPerformancesByLocation");
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
			listviewPerformances.setMinHeight(vbSearchPerformancesBox.getHeight());
			listviewPerformances.setOnMouseClicked(handler);
			vbSearchPerformancesBox.getChildren().add(listviewPerformances);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve performances in a location: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.performance_location_error"));
			error.show();
			return;
		}
	}
	
	private void findEventsByArtist() {		
		try {
			LOG.info("findEventsByArtist clicked");
			ArtistPane artistPane = (ArtistPane)listview.getSelectionModel().getSelectedItem();
			List<KeyValuePairDto<PerformanceDto, Integer>> keyValues = 
					this.eventService.findPerformancesSortedBySales(null, null, null, null, null, artistPane.getArtistId());
			
			vbSearchEventsBox.getChildren().clear();
			List<EventPane> eventList = new ArrayList<EventPane>();
			for(KeyValuePairDto<PerformanceDto, Integer> keyValue : keyValues) {
				PerformanceDto p = keyValue.getKey();
				eventList.add(new EventPane(p.getId(), p.getDescription(), p.getPerformancetype(), 
											p.getDurationInMinutes(), p.getContent(), keyValue.getValue(), false));
			}
			
			listviewEvents = new ListView<EventPane>(FXCollections.observableArrayList(eventList));
			listviewEvents.setMinWidth(vbSearchEventsBox.getWidth());
			listviewEvents.setMinHeight(vbSearchEventsBox.getHeight());
			listviewEvents.setOnMouseClicked(handlerEventsOfArtist);
			vbSearchEventsBox.getChildren().add(listviewEvents);
		} catch (ServiceException e) {
			LOG.error("Could not retrieve events of an artist: " + e.getMessage(), e);
			Stage current = (Stage) spSearchStack.getScene().getWindow();
			Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.event_artist_error"));
			error.show();
			return;
		}
	}
	
	private void findSeatsByPerformanceSearch() {
		//TODO: Stehplatz für nächste Woche
		LOG.info("seatingPlanAlreadyVisited: " + seatingPlanAlreadyVisited);
		if(!seatingPlanAlreadyVisited) {
			try {
				int row = 1;
				PerformancePane performancePane = (PerformancePane)listview.getSelectionModel().getSelectedItem();
				seatingPlanPane = new SeatingPlanPane();
				List<RowDto> rows = rowService.findRows(performancePane.getPerformanceId());
				for(RowDto r : rows) {
					int column = 1;
					seatingPlanPane.addRow(row);
					List<KeyValuePairDto<SeatDto, Boolean>> seats = seatService.findSeats(r.getId());
					for(KeyValuePairDto<SeatDto, Boolean> s : seats) {
						SeatPane seatPane = new SeatPane(entryService, ticketService, seatingPlanPane, performancePane.getPerformanceId(), 
								 						 s.getKey().getId(), getParentController().getBasket().getId(), !s.getValue());
						seatingPlanPane.addElement(column++, row, seatPane);
					}
					row++;
				}
				
				bpChooseSeats1.setCenter(seatingPlanPane);
			} catch (ServiceException e) {
				LOG.error("Could not retrieve seats of a performance: " + e.getMessage(), e);
				Stage current = (Stage) spSearchStack.getScene().getWindow();
				Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.seats_performance_error"));
				error.show();
				return;
			}
		}
	}
	
	private void findSeatsByPerformance() {
		//TODO: Stehplatz für nächste Woche
		/*LOG.info("seatingPlanAlreadyVisited: " + seatingPlanAlreadyVisited);
		if(!seatingPlanAlreadyVisited) {*/
			try {
				int row = 1;
				PerformancePane performancePane = (PerformancePane)listviewPerformances.getSelectionModel().getSelectedItem();
				if(seatingPlanPane == null) {
					seatingPlanPane = new SeatingPlanPane();
					List<RowDto> rows = rowService.findRows(performancePane.getPerformanceId());
					for(RowDto r : rows) {
						int column = 1;
						seatingPlanPane.addRow(row);
						List<KeyValuePairDto<SeatDto, Boolean>> seats = seatService.findSeats(r.getId());
						for(KeyValuePairDto<SeatDto, Boolean> s : seats) {
							SeatPane seatPane = new SeatPane(entryService, ticketService, seatingPlanPane, performancePane.getPerformanceId(), 
									 						 s.getKey().getId(), getParentController().getBasket().getId(), !s.getValue());
							seatingPlanPane.addElement(column++, row, seatPane);
						}
						row++;
					}
				} else {
					//TODO:
				}
				
				bpChooseSeats1.setCenter(seatingPlanPane);
			} catch (ServiceException e) {
				LOG.error("Could not retrieve seats of a performance: " + e.getMessage(), e);
				Stage current = (Stage) spSearchStack.getScene().getWindow();
				Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.seats_performance_error"));
				error.show();
				return;
			}
		//}
	}
	
	@FXML
	void handleEventTitleChanged(KeyEvent event) {
		if(tfEventTitle.getText().length() > 50) {
			tfEventTitle.setTooltip(new Tooltip("Input is longer than the maximum of 50 characters"));
			tfEventTitle.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfEventTitle.setTooltip(new Tooltip(""));
			tfEventTitle.setStyle("-fx-border-color: transparent;");
		}
		updateEventList();
	}
	
	@FXML
	void handleEventDurationChanged(ActionEvent event) {
		if(!tfEventDuration.getText().isEmpty()) {
			try {
				Integer.parseInt(tfEventDuration.getText());
				tfEventDuration.setTooltip(new Tooltip(""));
				tfEventDuration.setStyle("-fx-border-color: transparent;");
				int duration = Integer.valueOf(tfEventDuration.getText());
				int[] minMaxDuration = this.eventService.getMinAndMaxDuration();
				if((duration-minMaxDuration[0]) < 0) {
					duration = 0;
				} else if ((duration-minMaxDuration[0]) > minMaxDuration[1]) {
					duration = minMaxDuration[1];
				} else {
					duration -= minMaxDuration[0];
				}
				sldEventDuration.setValue(duration);
			} catch (ServiceException e) {
				LOG.error("Could not handle event duration textfield change: " + e.getMessage(), e);
				Stage current = (Stage) spSearchStack.getScene().getWindow();
				Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.duration_change_error"));
				error.show();
				return;
			} catch (NumberFormatException e) {
				tfEventDuration.setTooltip(new Tooltip("Not a valid number. Please input a duration."));
				tfEventDuration.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
			}
		} else {
			updateEventList();
		}
	}
	
	@FXML
	void handleEventContentChanged(KeyEvent event) {
		if(tfEventContent.getText().length() > 1024) {
			tfEventContent.setTooltip(new Tooltip("Input is longer than the maximum of 1024 characters"));
			tfEventContent.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfEventContent.setTooltip(new Tooltip(""));
			tfEventContent.setStyle("-fx-border-color: transparent;");
		}
		updateEventList();
	}
	
	@FXML
	void handlePerformanceDateFromChanged(ActionEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceDateToChanged(ActionEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime1FromChanged(ActionEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime2FromChanged(ActionEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime1ToChanged(ActionEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceTime2ToChanged(ActionEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformanceRoomsChanged(KeyEvent event) {
		updatePerformanceList();
	}
	
	@FXML
	void handlePerformancePriceChanged(ActionEvent event) {
		if(!tfPerformancePrice.getText().isEmpty()) {
			try {
				Double.parseDouble(tfPerformancePrice.getText());
				tfPerformancePrice.setTooltip(new Tooltip(""));
				tfPerformancePrice.setStyle("-fx-border-color: transparent;");
				double price = Double.valueOf(tfPerformancePrice.getText())*100;
				int[] minMaxPrice = this.performanceService.getMinMaxPriceInCent();
				if((price-minMaxPrice[0]) < 0) {
					price = 0;
				} else if ((price-minMaxPrice[0]) > minMaxPrice[1]) {
					price = minMaxPrice[1];
				} else {
					price -= minMaxPrice[0];
				}
				sldPerformancePrice.setValue(price/100);
			} catch (ServiceException e) {
				LOG.error("Could not handle performance price textfield change: " + e.getMessage(), e);
				Stage current = (Stage) spSearchStack.getScene().getWindow();
				Stage error = new ErrorDialog(current, BundleManager.getExceptionBundle().getString("searchpage.price_change_error"));
				error.show();
				return;
			} catch (NumberFormatException e) {
				tfPerformancePrice.setTooltip(new Tooltip("Not a valid price. A valid price is for example 8.0."));
				tfPerformancePrice.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
			}
		}
	}
	
	@FXML
	void handleLocationTitleChanged(KeyEvent event) {
		if(tfLocationTitle.getText().length() > 1024) {
			tfLocationTitle.setTooltip(new Tooltip("Input is longer than the maximum of 1024 characters"));
			tfLocationTitle.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfLocationTitle.setTooltip(new Tooltip(""));
			tfLocationTitle.setStyle("-fx-border-color: transparent;");
		}
		updateLocationList();
	}
	
	@FXML
	void handleLocationStreetChanged(KeyEvent event) {
		if(tfLocationStreet.getText().length() > 50) {
			tfLocationStreet.setTooltip(new Tooltip("Input is longer than the maximum of 50 characters"));
			tfLocationStreet.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfLocationStreet.setTooltip(new Tooltip(""));
			tfLocationStreet.setStyle("-fx-border-color: transparent;");
		}
		updateLocationList();
	}
	
	@FXML
	void handleLocationNameChanged(KeyEvent event) {
		if(tfLocationName.getText().length() > 50) {
			tfLocationName.setTooltip(new Tooltip("Input is longer than the maximum of 50 characters"));
			tfLocationName.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfLocationName.setTooltip(new Tooltip(""));
			tfLocationName.setStyle("-fx-border-color: transparent;");
		}
		updateLocationList();
	}
	
	@FXML
	void handleLocationPostalCodeChanged(KeyEvent event) {
		if(tfLocationPostalCode.getText().length() > 25) {
			tfLocationPostalCode.setTooltip(new Tooltip("Input is longer than the maximum of 25 characters"));
			tfLocationPostalCode.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfLocationPostalCode.setTooltip(new Tooltip(""));
			tfLocationPostalCode.setStyle("-fx-border-color: transparent;");
		}
		updateLocationList();
	}
	
	@FXML
	void handleLocationCountryChanged(KeyEvent event) {
		if(tfLocationCountry.getText().length() > 50) {
			tfLocationCountry.setTooltip(new Tooltip("Input is longer than the maximum of 50 characters"));
			tfLocationCountry.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfLocationCountry.setTooltip(new Tooltip(""));
			tfLocationCountry.setStyle("-fx-border-color: transparent;");
		}
		updateLocationList();
	}

	@FXML
	void handleArtistFirstnameChanged(KeyEvent event) {
		if(tfArtistFirstname.getText().length() > 50) {
			tfArtistFirstname.setTooltip(new Tooltip("Input is longer than the maximum of 50 characters"));
			tfArtistFirstname.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfArtistFirstname.setTooltip(new Tooltip(""));
			tfArtistFirstname.setStyle("-fx-border-color: transparent;");
		}
		updateArtistList();
	}
	
	@FXML
	void handleArtistLastnameChanged(KeyEvent event) {
		if(tfArtistLastname.getText().length() > 50) {
			tfArtistLastname.setTooltip(new Tooltip("Input is longer than the maximum of 50 characters"));
			tfArtistLastname.setStyle("-fx-border-color: red; -fx-border-radius: 4px;");
		} else {
			tfArtistLastname.setTooltip(new Tooltip(""));
			tfArtistLastname.setStyle("-fx-border-color: transparent;");
		}
		updateArtistList();
	}
	
	@FXML
	void handleSelectFromSearch(ActionEvent event) {
		updateResultList();
	}
	
	@FXML
	void handleSelectFromTopTenEvents(ActionEvent event) {
		updateResultList();
	}
	
	@FXML
	void handleSelectEventsFromSearchEvents(ActionEvent event) {
		LOG.info("handleSelectPerformanceFromSearchPerformances clicked");
		gpSearchEvents.setVisible(false);
		findPerformancesByEventOfArtist();
		gpSearchPerformances.setVisible(true);
		gpSearchEvents.toBack();
	}
	
	@FXML
	void handleReturnFromSearchEvents(ActionEvent event) {
		LOG.info("handleReturnFromSearchEvents clicked");
		gpSearchEvents.setVisible(false);
		spSearchStack.getChildren().get(0).setVisible(true);
		spSearchStack.getChildren().get(0).toFront();
	}
	
	@FXML
	void handleSelectPerformanceFromSearchPerformances(ActionEvent event) {
		LOG.info("handleSelectPerformanceFromSearchPerformances clicked");
		gpSearchPerformances.setVisible(false);
		gpChooseSeats.setVisible(true);
		gpSearchPerformances.toBack();
	}
	
	@FXML
	void handleReturnFromSearchPerformances(ActionEvent event) {
		LOG.info("handleReturnFromSearchPerformances clicked");
		gpSearchPerformances.setVisible(false);
		spSearchStack.getChildren().get(0).setVisible(true);
		spSearchStack.getChildren().get(0).toFront();
	}
	
	@FXML
	void handleReserveSeats(ActionEvent event) {
		LOG.info("handleReserveSeats clicked");
		if(seatingPlanPane != null) {
			if(seatingPlanPane.getReservedSeats() > 0) {
				seatingPlanAlreadyVisited = true;
				getParentController().setStepImage("/images/ClientStep.png");
				getParentController().setCenterContent("/gui/ClientChooseClientGui.fxml");
			}
		}
	}
	
	@FXML
	void handleReturnFromReserveSeats(ActionEvent event) {
		LOG.info("handleReturnFromReserveSeats clicked");
		if(seatingPlanPane != null) {
			if(seatingPlanPane.getReservedSeats() > 0) {
				seatingPlanAlreadyVisited = true;
			}
		}
		gpChooseSeats.setVisible(false);
		spSearchStack.getChildren().get(0).setVisible(true);
		spSearchStack.getChildren().get(0).toFront();
	}
	
	@FXML
	void handleGoToCustomerStep(ActionEvent event) {
		LOG.info("handleGoToCustomerStep");
		getParentController().setStepImage("/images/ClientStep.png");
		getParentController().setCenterContent("/gui/ClientChooseClientGui.fxml");
	}

	@FXML
	void handleGoToShoppingCart(ActionEvent event) {
		LOG.info("handleGoToShoppingCart");
		getParentController().setStepImage("/images/ShoppingCartStep.png");
		getParentController().setCenterContent("/gui/ClientShoppingCartGui.fxml");
	}
	
	private ClientSellTicketController getParentController() {
		return parentController;
	}

	public void setParentController(ClientSellTicketController cont) {
		this.parentController = cont;
	}
}