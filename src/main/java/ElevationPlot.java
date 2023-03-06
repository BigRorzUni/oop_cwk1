import java.io.IOException;

/**
 * Launcher for application to plot elevations of a GPS track, needed
 * for the Advanced task of COMP1721 Coursework 1.
 *
 * @author Nick Efford
 */
public class ElevationPlot {
  public static void main(String[] args) {
    if(args.length == 1)
    {
      PlotApplication.main(args);

      System.exit(0);
    }
    System.err.printf("No filename supplied\n");
    System.exit(0);
  }
}
