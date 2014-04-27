package at.ac.tuwien.inso.tl.client;

import java.util.Locale;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.apache.log4j.Logger;
import org.springframework.context.i18n.LocaleContextHolder;

import at.ac.tuwien.inso.tl.client.gui.dialog.ErrorDialog;
import at.ac.tuwien.inso.tl.client.util.BundleManager;
import at.ac.tuwien.inso.tl.client.util.SpringFxmlLoader;

public class TicketlineClient extends Application{
	private static final Logger LOG = Logger.getLogger(TicketlineClient.class);
	
	public static void main(String[] args) {
        launch(args);
    }
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		LOG.info("Starting Ticketline Client");
		
		LocaleContextHolder.setLocale(Locale.GERMAN);
		
		try {
			
            AnchorPane page = (AnchorPane) SpringFxmlLoader.getInstance().load("/gui/ClientLogin.fxml");
            Scene scene = new Scene(page);
            scene.getStylesheets().add(TicketlineClient.class.getResource("/gui/style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle(BundleManager.getBundle().getString("app_name"));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            Stage error = new ErrorDialog(primaryStage, BundleManager.getExceptionBundle().getString("start_error"));
            error.show();
        }
	}
}
