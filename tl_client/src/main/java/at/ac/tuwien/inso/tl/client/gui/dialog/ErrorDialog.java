package at.ac.tuwien.inso.tl.client.gui.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import at.ac.tuwien.inso.tl.client.util.BundleManager;

public class ErrorDialog extends Stage{
	private final int WIDTH_DEFAULT = 300;
	private Stage owner = null;
	private String msg = "";
	
	public ErrorDialog(String msg){
		this.msg = msg;
	}
	
	public ErrorDialog(Stage owner, String msg){
		this.owner = owner;
		this.msg = msg;
		
		init();
	}
	
	private void init(){
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
        initStyle(StageStyle.TRANSPARENT);
        
		
		Label label = new Label(msg);
        label.setWrapText(true);
        label.setGraphicTextGap(20);
        Image img = new Image("/images/error.png", 50, 50, false, false);
        
        label.setGraphic(new ImageView(img));
        
        Button button = new Button(BundleManager.getBundle().getString("ok"));
        button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				ErrorDialog.this.close();
			}
		});
        
        HBox hbox1 = new HBox();
        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().add(label);
        
        BorderPane borderPane = new BorderPane();
        borderPane.getStylesheets().add("/gui/error.css");        
        borderPane.setTop(hbox1);
        
        HBox hbox2 = new HBox();
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().add(button);
        borderPane.setBottom(hbox2);
        
        // calculate width of string
        final Text text = new Text(msg);
        text.snapshot(null, null);
        // + 20 because there is padding 10 left and right
        int width = (int) text.getLayoutBounds().getWidth() + 40;

        if (width < WIDTH_DEFAULT){
            width = WIDTH_DEFAULT;
        }

        int height = 100;

        final Scene scene = new Scene(borderPane, width, height);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);
        
        if(null != owner){
	        // make sure this stage is centered on top of its owner
	        setX(owner.getX() + (owner.getWidth() / 2 - width / 2));
	        setY(owner.getY() + (owner.getHeight() / 2 - height / 2));
        }
	}
}
