import javax.security.auth.login.Configuration.Parameters;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.scene.*;
import java.util.List;
/**
 * JavaFX application to plot elevations of a GPS track, for the
 * Advanced task of COMP1721 Coursework 1.
 *
 * @author Rory Edmonds
 */
public class PlotApplication extends Application
{
  @Override
  public void start(Stage stage) throws Exception
  {

    Parameters pms = getParameters();
    List<String> l = pms.getRaw();
    String trackArg = l.get(0);

    Track track = new Track(trackArg);

    stage.setTitle("Elevation Plot");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Distance (m)");
        yAxis.setLabel("Elevation (m)");

        //creating the chart
        final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);
                
        lineChart.setTitle("Elevation Plot");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Points");

        double elev;
        double dist = 0.0;
        Point current;

        //populating the series with data
        for(int i = 0; i < track.size(); i++)
        {
          current = track.get(i);

          elev = current.getElevation();
          if(i != track.size() - 1)
            dist += track.getDistance(current, track.get(i+1));

          series.getData().add(new XYChart.Data(dist, elev));
        }
        
        Scene scene  = new Scene(lineChart,800,600);
        lineChart.getData().add(series);
        lineChart.setCreateSymbols(false);
       
        stage.setScene(scene);
        stage.show();
  }

  public static void main(String[] args)
  {
    // If attempting the Advanced task, uncomment the line below
    launch(args);
  }
}
