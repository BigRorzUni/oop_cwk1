import java.io.IOException;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author YOUR NAME HERE
 */
public class TrackInfo {
  public static void main(String[] args) 
  {
    if(args.length == 1)
    {
      try
      {
        Track track = new Track(args[0]);

        System.out.printf("%d points in track\n", track.size());
        System.out.printf("Lowest point is %s\n", track.lowestPoint().toString());
        System.out.printf("Highest point is %s\n", track.highestPoint().toString());
        System.out.printf("Total distance = %f km\n", track.totalDistance());
        System.out.printf("Average speed = %f m/s\n", track.averageSpeed());
        System.exit(0);
      }
      catch(IOException ex)
      {
        ex.printStackTrace();
        System.exit(-1);
      }
    }
    System.err.printf("No filename supplied\n");
    System.exit(0);
  }
}
