package at.ac.tuwien.inso.tl.client.gui.controller;

import java.net.URL;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;

import at.ac.tuwien.inso.tl.client.client.AuthService;
import at.ac.tuwien.inso.tl.client.exception.ServiceException;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;
import at.ac.tuwien.inso.tl.dto.UserEvent;

@Controller
public class LoginController implements Initializable{
	private static final Logger LOG = Logger.getLogger(LoginController.class);

	@Autowired
	private AuthService authService;
	
	@FXML
	TextField txtUsername;
	
	@FXML
	PasswordField txtPassword;
	
	@FXML
	Button btnLogin, btnExit;
	
	@FXML
	Label lblError;
	
	@FXML
	ComboBox<String> cbLanguage;
	
	@FXML
	ImageView imgLogo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		if(null != cbLanguage){
			cbLanguage.getItems().clear();
			cbLanguage.getItems().addAll(Arrays.asList("DE", "EN"));
			cbLanguage.setValue("DE");
		}
		
		if(null != lblError){
			lblError.setVisible(false);
			lblError.setId("lb_error");
		}
		
		if(null != imgLogo){
			Image img = new Image("/images/TicketlineLogo.png", 440, 100, false, false);
			imgLogo.setImage(img);
			imgLogo.setFitWidth(440);
			imgLogo.setFitHeight(100);
		}
		
		addActionListener();
	}
	
	private void addActionListener(){
		if(null != cbLanguage){
			cbLanguage.valueProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					if ("EN".equals(newValue)) {
						LocaleContextHolder.setLocale(Locale.ENGLISH);
						BundleManager.changeLocale(Locale.ENGLISH);
					} else if ("DE".equals(newValue)){
						LocaleContextHolder.setLocale(Locale.GERMAN);
						BundleManager.changeLocale(Locale.GERMAN);
					} else {
						return;
					}
					
					btnExit.setText(BundleManager.getBundle().getString("exit"));
					btnLogin.setText(BundleManager.getBundle().getString("login"));
					txtPassword.setPromptText(BundleManager.getBundle().getString("password"));
					txtUsername.setPromptText(BundleManager.getBundle().getString("username"));
				}
			});
		}
		
		if(null != txtUsername){
			txtUsername.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					lblError.setVisible(false);
				}
		    });
		}
		
		if(null != txtPassword){
			txtPassword.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable,
						String oldValue, String newValue) {
					lblError.setVisible(false);
				}
		    });
		}
	}
	
	@FXML
	private void handleLogin(ActionEvent event){
		UserEvent login;
		try {
			
			login = this.authService.login(txtUsername.getText(), txtPassword.getText());
		} catch (ServiceException e) {
			LOG.error(e.getMessage(), e);
			lblError.setVisible(true);
			lblError.setText(BundleManager.getExceptionBundle().getString("connection_error"));
			return;
		}
		
		if(login == UserEvent.WRONG_CREDENTIALS) {
			lblError.setVisible(true);
			lblError.setText(BundleManager.getExceptionBundle().getString("login_data_error"));
			return;
		} else if(login == UserEvent.USER_LOCKED) {
			lblError.setVisible(true);
			lblError.setText(BundleManager.getExceptionBundle().getString("login_user_locked_error"));
			return;
		}

		((Node) event.getSource()).setCursor(Cursor.WAIT);
				
		AnchorPane page = (AnchorPane) SpringFxmlLoader.getInstance().load("/gui/ClientMainGui.fxml");
		Scene scene = new Scene(page);
		scene.getStylesheets().add("/gui/style.css");
		        
		Stage clientStage = new Stage();
		clientStage.setResizable(false);
		clientStage.setScene(scene);
		clientStage.setTitle(BundleManager.getBundle().getString("app_name"));
		clientStage.centerOnScreen();
		clientStage.show();
		        
		((Node) event.getSource()).setCursor(Cursor.DEFAULT);
		        
		Node source = (Node)  event.getSource(); 
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	private void handleExit(ActionEvent event){
		Platform.exit();
	}
}
