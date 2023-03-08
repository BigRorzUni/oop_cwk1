import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Rory Edmonds
 */
public class Track 
{
  private ArrayList<Point> points; 

  // Constructors;
  public Track()
  {
    points = new ArrayList<Point>();
  }

  public Track(String filename) throws IOException
  {
    readFile(filename);
  }

  // Reads from the file;
  public void readFile(String filename) throws IOException
  {
    // Resets points list;
    points = new ArrayList<Point>();

    File file = new File(filename);
    Scanner input = new Scanner(file);

    // Skips formatting line;
    input.nextLine();

    // Reads each line;
    while(input.hasNextLine())
    {
      // Splits each line into its components;
      String line = input.nextLine();
      String[] toParse = line.split(",", 4);

      //Checks for invalid formatting;
      Point toAdd = parseLine(toParse, input);

      //Add verified point to list;
      points.add(toAdd);
    }

    //Close file once finished;
    input.close();
  }

  //Method to convert lines in file into points;
  public Point parseLine(String[] line, Scanner input)
  {
    //Check for correct data layout;
    if(line.length != 4)
    {
      input.close();
      throw new GPSException("File does not have the correct columns");
    }

    //Attempt to parse each element as it's predicted data type;
    try
    {
      ZonedDateTime t = ZonedDateTime.parse(line[0]);
      Double lo = Double.parseDouble(line[1]);
      Double la = Double.parseDouble(line[2]);
      Double e = Double.parseDouble(line[3]);

      return new Point(t, lo, la, e);
    }
    catch (DateTimeParseException | NumberFormatException e)
    {
      input.close();
      throw new GPSException("Issue with data formats");
    }
  }

  //Method to create a KML file from a track;
  public void writeKML(String filename) throws IOException
  {
    //These strings contain the skeleton for a KML file;
    String start = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" + "\t<Placemark>\n" + "\t\t<LineString>\n" + "\t\t\t<altitudeMode>absolute</altitudeMode>\n"+"\t\t\t<coordinates>\n";
    
    String end = "\t\t\t</coordinates>\n" + "\t\t</LineString>\n" + "\t</Placemark>\n" + "</kml>";

    //Make a new KML file with filename as its name;
    FileWriter writer = new FileWriter(filename);

    //Write in the initial KML skeleton;
    writer.write(start);

    for(Point current : points)
    {
      //This string contains the correct KML format for the coordinates;
      String element = "\t\t\t" + current.getLongitude() + "," + current.getLatitude() + "," + current.getElevation() + "\n";
      
      //Write the coordinates into the file;
      writer.write(element);
    }
    
    //Finish the KML skeleton and close the file;
    writer.write(end);
    writer.close();
  }

  // Adds a point to the track;
  public void add(Point p)
  {
    points.add(p);
  }

  // Returns a point at a given index;
  public Point get(int index)
  {
    if(size() > 0)
    {
      if(index >= 0 && index < points.size())
        return points.get(index);
    }
    throw new GPSException(null);
  }

  // Returns the size of the track;
  public int size()
  {
    return points.size();
  }

  // Returns the lowest point in the track;
  public Point lowestPoint()
  {
    if(size() > 0)
    {
      Point currentLowest = points.get(0);

      for(Point current : points)
      {
        if(current.getElevation() < currentLowest.getElevation())
        {
          currentLowest = current;
        }
      }

      return currentLowest;
    }

    throw new GPSException(null);
  }

  // Returns the highest point;
  public Point highestPoint()
  {
    if(size() > 0)
    {
      Point currentHighest = points.get(0);

      for(Point current : points)
      {
        if(current.getElevation() > currentHighest.getElevation())
        {
          currentHighest = current;
        }
      }

      return currentHighest;
    }

    throw new GPSException(null);
  }

  // Returns the total distance travelled;
  public double totalDistance()
  {
    double dist = 0.0;

    if(size() >= 2)
    {
      for(int i = 0; i < size() - 1; i++)
      {
        dist += getDistance(points.get(i), points.get(i+1));
      }
      return dist;
    }

    throw new GPSException(null);
  }

  public double getDistance(Point a, Point b)
  {
    return Point.greatCircleDistance(a, b);
  }

  // Returns the average speed;
  public double averageSpeed()
  {
    if(size() >= 2)
    { 
      return totalDistance() / totalTime();
    }

    throw new GPSException(null);
  }

  //Returns the total time taken;
  public double totalTime()
  {
    if(size() >= 2)
    {
      Point start = points.get(0);
      Point end = points.get(size() - 1);

      return ChronoUnit.SECONDS.between(start.getTime(), end.getTime());
    }

    throw new GPSException(null);
  }

}
