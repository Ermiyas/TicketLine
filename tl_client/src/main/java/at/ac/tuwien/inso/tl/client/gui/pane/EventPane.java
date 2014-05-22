package at.ac.tuwien.inso.tl.client.gui.pane;

import at.ac.tuwien.inso.tl.client.util.BundleManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class EventPane extends Pane{
	private String title;
	private String type;
	private int duration;
	private String eventText;
	
	private Double textWidth = 700d;
	
	private Text tx_title;
	private Label lbl_details;
	private Label lbl_text;
	
	public EventPane(String title, String type, int duration, String eventText){
		this.title = title;
		this.type = type;
		this.duration = duration;
		this.eventText = eventText;
		
		init();
	}
	
	private void init(){
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER_LEFT);
		grid.setHgap(5);
		grid.setVgap(5);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(300);
		grid.getColumnConstraints().add(column);
		int row = 0;
		
		tx_title = new Text(title);
		tx_title.setWrappingWidth(textWidth);
		tx_title.setId("tx_title");
		grid.add(tx_title, 0, row++);
		
		lbl_details = new Label(BundleManager.getBundle().getString("searchpage.event.type") + " " + type + ", "
							  + BundleManager.getBundle().getString("searchpage.event.duration") + " " + duration + " Min.");
		lbl_details.setWrapText(true);
		lbl_details.setMaxWidth(textWidth);
		grid.add(lbl_details, 0, row++);
		
		grid.add(new Separator(), 0, row++);
		
		lbl_text = new Label(eventText);
		lbl_text.setWrapText(true);
		lbl_text.setMaxWidth(textWidth);
		grid.add(lbl_text, 0, row);
		
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
}
