package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.NewsService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.dto.NewsDto;

@Controller
public class NewsArchiveFormController implements Initializable {
	private static final Logger LOG = Logger.getLogger(NewsArchiveFormController.class);
	
	@FXML
	private static ListView<NewsDto> lvNewsArchiveEntries;
	
	private static List<NewsDto> allNewsList;

	private static NewsDto selectedNewsItem;
	
	@FXML
	private static Label lblNewsHeadline;
	
	@FXML
	private static Label lbNewsText;
	
	@FXML
	private static TextField txtSearchText;

	@FXML
	private static Button btnToggleRead;
	
	private static SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
	
	private static NewsService newsService;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.FillWithNewsList(true);
	}
	
	@FXML
	private void btnClearClicked(MouseEvent event) {
		
		txtSearchText.clear();
		handleSearchClick(null);
	}
	
	@FXML
	private void handleSearchClick(ActionEvent event){
	
		String searchString = txtSearchText.getText();
		NewsDto searchObj = new NewsDto();
		searchObj.setTitle(searchString);
		
		try {
			
			allNewsList = newsService.search(searchObj);
			
		} catch (ServiceException e) {
			LOG.error("Could not search for news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
		}
		
		FillWithNewsList(false);
		
	}
	
	private static void ToggleInternal(NewsDto selectedNewsItem) {
		
		Boolean isRead;
		
		try {
			isRead = newsService.getNewsIsReadByEmployee(selectedNewsItem.getId());
		} catch (ServiceException e) {
			LOG.error("Could not request if news is read by employee: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
		
		if (isRead) {

			btnToggleRead.setText(BundleManager.getBundle().getString("news.toggle_unread"));
			
		} else {
			
			btnToggleRead.setText(BundleManager.getBundle().getString("news.toggle_read"));
			
		}
		
		ObservableList<NewsDto> obsList = lvNewsArchiveEntries.getItems();
		NewsDto savedObject = new NewsDto();
		int index;
		
		for (NewsDto n : obsList) {
			
			if (n.getId() == selectedNewsItem.getId()) {
				
				index = obsList.indexOf(n);
				savedObject = n;
				
				obsList.set(index, null);
				obsList.set(index, savedObject);
				
			}
		}
	}
	
	@FXML
	private void btnToggleReadClicked(MouseEvent event) {
		try {
			
			newsService.addNewsReadByEmployee(selectedNewsItem.getId());
			ToggleInternal(selectedNewsItem);
			
		} catch (ServiceException e) {
			LOG.error("Could not toggle read news: " + e.getMessage(), e);
			Stage error = new ErrorDialog(e.getMessage());
			error.show();
			return;
		}
	}
	
	@FXML
	private void handleSearchKeyPress(KeyEvent event) {
		
		if (event.getCode() == KeyCode.ENTER) {
			handleSearchClick(null);
		}
	}
	
	@FXML
	private void lvNewsArchiveEntriesClick(MouseEvent event){
		NewsDto selectedItem = lvNewsArchiveEntries.getSelectionModel().getSelectedItem();
		NewsArchiveFormController.setSelectedNewsItem(selectedItem);
	}
	
	public void FillWithNewsList(Boolean fetch) {
		
		if (fetch) {	
			try {
				allNewsList = newsService.getNews();
	
			} catch (ServiceException e) {
				LOG.error("Could not retrieve news: " + e.getMessage(), e);
				Stage error = new ErrorDialog(e.getMessage());
				error.show();
				return;
			}
		}

		ObservableList<NewsDto> obsList =	FXCollections.observableArrayList(allNewsList);

		lvNewsArchiveEntries.setItems(obsList);
		lvNewsArchiveEntries.setCellFactory(new Callback<ListView<NewsDto>, ListCell<NewsDto>>() {

		@Override
		public ListCell<NewsDto> call(ListView<NewsDto> arg0) {
			ListCell<NewsDto> cell = new ListCell<NewsDto>(){
				
			@Override
			protected void updateItem(NewsDto n, boolean empty) {
				super.updateItem(n,  empty);
				
				if (n != null) {
					setText(String.format("[%s] %s", df.format(n.getSubmittedOn()), n.getTitle()));
					
					if (selectedNewsItem != null && n.getId() == selectedNewsItem.getId())
					{
					setHover(true);
					}
					
					Boolean isRead;
					
					try {
						isRead = newsService.getNewsIsReadByEmployee(n.getId());
					} catch (ServiceException e) {
						LOG.error("Could not request if news is read by employee: " + e.getMessage(), e);
						Stage error = new ErrorDialog(e.getMessage());
						error.show();
						return;
					}
					
					if (isRead) {
						
						setStyle("-fx-font-weight: normal;");
						
					} else {
						
						setStyle("-fx-font-weight: bold;");
						
					}

				}
				
			}
			};
			return cell;
		}
	});

	}
	
	public static List<NewsDto> getNewsList() {
		return allNewsList;
	}

	public static void setNewsList(List<NewsDto> newsList) {
		NewsArchiveFormController.allNewsList = newsList;
	}
 
	public static NewsDto getSelectedNewsItem() {
		return selectedNewsItem;
	}

	public static void setSelectedNewsItem(NewsDto selectedNewsItem) {

		if (selectedNewsItem == null) {
			return;
		}
		
		NewsArchiveFormController.selectedNewsItem = selectedNewsItem;
		ToggleInternal(selectedNewsItem);
		
		lblNewsHeadline.setText(selectedNewsItem.getTitle());
		lbNewsText.setText(selectedNewsItem.getNewsText());

		btnToggleRead.setDisable(false);

	}
	
	public static NewsService getNewsService() {
		return newsService;
	}

	public static void setNewsService(NewsService newsService) {
		NewsArchiveFormController.newsService = newsService;
	}
}
