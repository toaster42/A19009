import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Michael P. Troester
 * @version 1.01 - 10/17/2015
 * @studentid 5061001
 * @email michaelp.troester@gmail.com
 * @assignment.number PACKAGE_NAME
 * @screenprint <a href='PACKAGE_NAME.gif'>ScreenPrint</a>
 * @prgm.usage Called directly from OS
 * @link <a href='http://jcouture.net/cisc190/PACKAGE_NAME.php'>Program Specification</a>
 * @link <br><a href='http://docs.oracle.com/javase/8/docs/
 * technotes/guides/Javadoc/index.html'>Javadoc Documentation</a>
 * IntelliJ Template 14.10 - 10/19/2014
 */
public class WorldStations extends Stations {

    private enum sta {BlockNumber, StationNumber, ID, Name, State, Country, WMORegion, Latitude, Longitude, UALatitude, UALongitude, Altitude, UAElevation, RBSN}
    public WorldStations(String strInputFileName, String strWorldStationsFilename) throws Exception {
        super(strInputFileName);

        INET net = new INET();
        String strFileContents = net.getFromFile(strWorldStationsFilename);
        StringTokenizer st = new StringTokenizer(strFileContents, "\r\n", true);
        String[] aryWorld = new String[length()];
        int intLineCounter = 0;

        Scanner inputFile = new Scanner(strWorldStationsFilename);
        // Initialize a Counter
        //int intCount = 0;
        while (inputFile.hasNext()) {
            strRecord = inputFile.nextLine();
            if (strRecord.substring(7,8).equals("K")) {
                if (exists(strRecord.substring(8,11))) {
                    aryWorld[staIndex.indexOf(strRecord.substring(8,11))] = strRecord;
                    //intCount++;
                }
            }
        }

        for (String s : aryWorld = new String[length()]) {
            
        }
        ;
        /*
        3. Add an array (not an arraylist) called aryWorld that has the World.txt data in it.  Set the size of the array to the size returned by the length() method in Stations.
        4. The World file has about 11,000 records in it.  Don't waste computer memory by loading the entire World.txt file into an array or an arraylist.  As you read through the World file, check to see if that station exists in the staIndex arraylist).  If it does, add the entire record to the aryWorld array at the same index point returned by the arraylist get method.
        5. Create a getStaData(strStationID) method to the WorldStations class that will look up the station id in the staIndex and return the contents of aryWorld at that position.
        6. Create a getStaField(strStationID, intField) method that will return the field data from the World record.  The intField will need to be an enumerated field.  In use this will look like getStaField("KSAN",sta.Country). sta.Country will be an enumerated field equated to the number 5.
        7. Create a enumerated type called sta (see pg 535) that substitutes the text names for ordinal positions to fields in the World file.  For example, field #5 is the country.  Note that the list below is not in any sequence and you may need to add some others.
        8. Create PUBLIC get methods for the following:
            1. Name - in other words you are going to create a getName() method.
            2. Latitude
            3. Longitude
            4. Altitude
            5. State
            6. Country

        For example, create getName(strStationID) method to return the name of the station from the World file.  This method will use an enumerated type and call the getStaField() method as follows:

        return getStaField(strStationID,sta.Name)
         */

    }

    /**
     * The purpose of this method is to look up the station id in the staIndex and return
     * the contents of aryWorld at that position.
     * @param strStationID The three letter Station ID.
     * @return strStaData - the Station Data for the given Station ID.
     */
    public String getStaData(String strStationID) {
        String strStaData = "";
        return strStaData;
    }

    /**
     * The purpose of this method is to return the field data from the World record.  The intField will need to be an enumerated field.  In use this will look like getStaField("KSAN",sta.Country). sta.Country will be an enumerated field equated to the number 5.
     * @return strStaField - The field info for the requested Station ID and Field
     */
    public String getStaField(String strStationID, int intField) {
        String strStaField ="";
        return strStaField;
    }

    public String getName(String strStationID) {
        return getStaField(strStationID,sta.Name.ordinal());
    }

    public String getLatitude(String strStationID) {
        return getStaField(strStationID,sta.Latitude.ordinal());
    }

    public String getLongitude(String strStationID) {
        return getStaField(strStationID,sta.Longitude.ordinal());
    }

    public String getAltitude(String strStationID) {
        return getStaField(strStationID,sta.Altitude.ordinal());
    }

    public String getState(String strStationID) {
        return getStaField(strStationID,sta.State.ordinal());
    }

    public String getCountry(String strStationID) {
        return getStaField(strStationID,sta.Country.ordinal());
    }
}
