import java.io.IOException;

/**
 * Program to generate a KML file from GPS track data stored in a file,
 * for the Advanced task of COMP1721 Coursework 1.
 *
 * @author Rory Edmonds
 */
public class ConvertToKML {
  public static void main(String[] args)
  {
    //Take in 2 command line arguments;
    if(args.length == 2)
    {
      try
      {
        Track track = new Track(args[0]);

        track.writeKML(args[1]);

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
