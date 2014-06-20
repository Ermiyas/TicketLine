package at.ac.tuwien.inso.tl.client.gui.pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import at.ac.tuwien.inso.tl.client.util.BundleManager;

public class EventPane extends Pane{
	private Integer id;
	private String title;
	private String type;
	private Integer duration;
	private String eventText;
	
	private Integer soldTickets;
	
	private Double textWidth = 700d;
	
	private Text tx_title;
	private Label lbl_ranking;
	private Label lbl_details;
	private Label lbl_text;
	
	public EventPane(Integer id, String title, String type, Integer duration, String eventText, Integer soldTickets, boolean topTen){
		this.id = id;
		this.title = title;
		this.type = type;
		this.duration = duration;
		this.eventText = eventText;
		this.soldTickets = soldTickets;
		
		if(!topTen) {
			init();
		}
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
	
	public void initForTopTen(Integer ranking) {
		GridPane grid = new GridPane();
		
		GridPane gridleft = new GridPane();
		gridleft.setAlignment(Pos.CENTER);
		
		GridPane gridright = new GridPane();
		gridright.setAlignment(Pos.CENTER_LEFT);
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(50);
		grid.getColumnConstraints().add(column);
		gridleft.getColumnConstraints().add(column);
		column = new ColumnConstraints();
		grid.getColumnConstraints().add(column);
		gridright.getColumnConstraints().add(column);
		int row = 0;
		
		String rankingString = String.valueOf(ranking);
		lbl_ranking = new Label("#" + rankingString);
		lbl_ranking.setWrapText(true);
		lbl_ranking.setMaxWidth(50);
		lbl_ranking.setAlignment(Pos.CENTER);
		gridleft.add(lbl_ranking, 0, 0);
		
		tx_title = new Text(title);
		tx_title.setWrappingWidth(textWidth);
		tx_title.setId("tx_title");
		gridright.add(tx_title, 0, row++);
		
		lbl_details = new Label(BundleManager.getBundle().getString("searchpage.event.type") + " " + type + ", "
							  + BundleManager.getBundle().getString("searchpage.event.duration") + " " + duration + " Min.");
		lbl_details.setWrapText(true);
		lbl_details.setMaxWidth(textWidth);
		gridright.add(lbl_details, 0, row++);
		
		gridright.add(new Separator(), 0, row++);
		
		lbl_text = new Label(eventText);
		lbl_text.setWrapText(true);
		lbl_text.setMaxWidth(textWidth);
		gridright.add(lbl_text, 0, row);
		
		grid.add(gridleft, 0, 0);
		grid.add(gridright, 1, 0);
		this.getChildren().add(grid);
		this.getStylesheets().add("/gui/style.css");
	}
	
	public Integer getEventId() {
		return id;
	}
	
	public String getEventTitle() {
		return title;
	}
	
	public Integer getEventSoldTickets() {
		return soldTickets;
	}
}
