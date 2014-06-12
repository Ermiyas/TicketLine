package at.ac.tuwien.inso.tl.client.gui.pane;

import java.util.List;

import org.apache.log4j.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class TopTenBarChartPane extends GridPane {
	private static final Logger LOG = Logger.getLogger(TopTenBarChartPane.class);
	
	@SuppressWarnings("unchecked")
	public TopTenBarChartPane(List<EventPane> eventList){
		this.setAlignment(Pos.CENTER_LEFT);
		this.setPadding(new Insets(5, 0, 5, 0));
		this.setMaxHeight(200d);
		
		ColumnConstraints column = new ColumnConstraints();
		column.setMinWidth(805);
		this.getColumnConstraints().add(column);
		
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
        barChart.setLegendVisible(false);
        //xAxis.setTickLabelRotation(-90);
        //xAxis.setLabel("Veranstaltung");
        //yAxis.setLabel("Verkaufte Tickets");
        
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();      

        if(eventList.size() >= 10) {
	        for(int i = 0; i < 10; i++) {
	        	EventPane pane = eventList.get(i);
	        	series.getData().add(new XYChart.Data<String, Number>("#"+(i+1), pane.getEventSoldTickets()));
	        }
        } else {
        	LOG.info("eventList size: " + eventList.size());
        	for(int i = 0; i < eventList.size(); i++) {
	        	EventPane pane = eventList.get(i);
	        	series.getData().add(new XYChart.Data<String, Number>("#"+(i+1), pane.getEventSoldTickets()));
	        }
        	for(int j = eventList.size(); j < 10; j++) {
        		series.getData().add(new XYChart.Data<String, Number>("#"+(j+1), 0));
        	}
        }
        
        barChart.getData().addAll(series);
        
        this.add(barChart, 0, 0);
	}
}
