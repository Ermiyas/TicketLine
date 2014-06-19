package at.ac.tuwien.inso.tl.client.gui.pane;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.NewsDto;

public class NewsPane extends Pane {
	
	public TableView<NewsDto> tvNews;
	private Integer tableViewHeight = 455;
	private Integer tableViewWidth = 418;
	private Integer tcSubmittedOnPrefWidth = 95;
	private Integer tcTitlePrefWidth = 321;
	
	public NewsPane() {
		GridPane grid = new GridPane();
		
		tvNews = new TableView<NewsDto>();
		tvNews.setPrefHeight(tableViewHeight);
		tvNews.setPrefWidth(tableViewWidth);
		tvNews.setPlaceholder(new Label(BundleManager.getBundle().getString("news.empty_list")));
	
		TableColumn<NewsDto, String> tcTitle = new TableColumn<NewsDto, String>("Titel");
		TableColumn<NewsDto, String> tcSubmittedOn = new TableColumn<NewsDto, String>("Datum");
		tcSubmittedOn.setPrefWidth(tcSubmittedOnPrefWidth);
		tcTitle.setPrefWidth(tcTitlePrefWidth);

       	tcTitle.setCellValueFactory(new PropertyValueFactory<NewsDto, String>("title"));
       	tcSubmittedOn.setCellValueFactory(new PropertyValueFactory<NewsDto, String>("submittedOn"));
       	
       	tcSubmittedOn.setCellValueFactory(
			new Callback<TableColumn.CellDataFeatures<NewsDto, String>, ObservableValue<String>>() {
				@Override
			    public ObservableValue<String> call(TableColumn.CellDataFeatures<NewsDto, String> suo) {
			         SimpleStringProperty property = new SimpleStringProperty();
			         DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			         property.setValue(dateFormat.format(suo.getValue().getSubmittedOn()));
			         return property;
			    }});
       
		tvNews.getColumns().add(tcTitle);
		tvNews.getColumns().add(tcSubmittedOn);
		
		grid.add(tvNews, 0, 0);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
	
	public void BindToList(List<NewsDto> input) {
       	ObservableList<NewsDto> tmp =	FXCollections.observableArrayList(input);
       	tvNews.setItems(tmp);
	}
	
	public void Clear() {
		tvNews.setItems(null);
	}
}
