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

    //Take in filename;
    Parameters pms = getParameters();
    List<String> l = pms.getRaw();

    //Check for correct amount of args;
    if(l.size() == 1)
    {
      String trackArg = l.get(0);

      //Creating the track
      Track track = new Track(trackArg);

      stage.setTitle("Elevation Plot");
      
      //Defining the axes;
      final NumberAxis xAxis = new NumberAxis();
      xAxis.setLabel("Distance (m)");

      final NumberAxis yAxis = new NumberAxis();
      yAxis.setLabel("Elevation (m)");

      //Creating the chart;
      final LineChart<Number,Number> lineChart = new LineChart<Number,Number>(xAxis,yAxis);       
      lineChart.setTitle("Elevation Plot");
      
      //Defining a series;
      XYChart.Series series = new XYChart.Series();
      series.setName("Points");

      double elev;
      double dist = 0.0;
      Point current;

      //Populating the series with data
      for(int i = 0; i < track.size(); i++)
      {
        current = track.get(i);

        //Storing point elevation;
        elev = current.getElevation();

        //Calculating and storing distance between points;
        if(i != track.size() - 1) //Make sure not to go out of bounds of the list;
          dist += track.getDistance(current, track.get(i+1));

        series.getData().add(new XYChart.Data(dist, elev));
      }

      //Drawing data onto the chart  
      Scene scene  = new Scene(lineChart,800,600);
      lineChart.getData().add(series);
      lineChart.setCreateSymbols(false);
       
      stage.setScene(scene);
      stage.show();
    }
    else
    {
      System.err.printf("No filename supplied\n");
      System.exit(-1);
    }
  }

  public static void main(String[] args)
  {
    launch(args);
  }
}