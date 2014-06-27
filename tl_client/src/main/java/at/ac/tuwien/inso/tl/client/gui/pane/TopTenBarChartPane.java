package at.ac.tuwien.inso.tl.client.gui.pane;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import org.apache.log4j.Logger;

import at.ac.tuwien.inso.tl.client.util.BundleManager;

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
		xAxis.setLabel(BundleManager.getBundle().getString("searchpage.topten.sold_last_month") + 
					   " 30 " + BundleManager.getBundle().getString("searchpage.topten.days"));
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setAutoRanging(false);
        yAxis.setUpperBound(10d);
        
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        
        if(eventList.size() >= 10) {
	        for(int i = 0; i < 10; i++) {
	        	EventPane pane = eventList.get(i);
	        	if(i == 0) {
	        		yAxis.setTickUnit(pane.getEventSoldTickets()/10);
	        	}
	        	if(yAxis.getUpperBound() < (double)pane.getEventSoldTickets()) {
	        		yAxis.setUpperBound((double)pane.getEventSoldTickets());
	        	}
	        	series.getData().add(new XYChart.Data<String, Number>("#"+(i+1), pane.getEventSoldTickets()));
	        }
        } else {
        	for(int i = 0; i < eventList.size(); i++) {
	        	EventPane pane = eventList.get(i);
	        	if(i == 0) {
	        		yAxis.setTickUnit(pane.getEventSoldTickets()/10);
	        	}
	        	if(yAxis.getUpperBound() < (double)pane.getEventSoldTickets()) {
	        		yAxis.setUpperBound((double)pane.getEventSoldTickets());
	        	}
	        	series.getData().add(new XYChart.Data<String, Number>("#"+(i+1), pane.getEventSoldTickets()));
	        }
        	for(int j = eventList.size(); j < 10; j++) {
        		series.getData().add(new XYChart.Data<String, Number>("#"+(j+1), 0));
        	}
        }
        
        final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis,yAxis);
        barChart.setLegendVisible(false);
        barChart.getData().addAll(series);
        
        this.add(barChart, 0, 0);
	}
}