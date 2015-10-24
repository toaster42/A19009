import javax.swing.*;
import java.awt.event.*;

public class A19009 extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    static public JComboBox cmbLocation;
    static public JComboBox cmbAltitude;
    private JButton btnReport;
    public JLabel lblWindDir;
    public JLabel lblWindSpeed;
    public JLabel lblWindTemp;
    private JLabel lblLatitude;
    private JLabel lblLongitude;
    private JLabel lblAltitude;
    static String strWindsAloftFilename = "data/FBIN.txt";
    static String strWindsAloftURL = "http://www.aviationweather.gov/products/nws/all";
    static String strWorldStationsFilename = "data/World.txt";
    static String strWorldStationsURL = "http://weather.noaa.gov/data/nsd_cccc.txt";
    static String[] aryAltitudes  = {"03", "06", "09", "12", "18", "24", "30", "34", "39"};
    static int intLoadState = -1;

    public A19009() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        btnReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onReport();
            }
        });

        /*buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });*/

        //Call ItemChangeListener()
        cmbLocation.addItemListener(new ItemChangeListener());
// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    /**
     * goes through the stations in the stations class and writes out the station ID, latitude, longitude and altitude along with the Wind Direction, Speed and Temperature for each of the nine altitudes.  You will need to use the PrintWriter class for this and the file name should be FBOUT.txt.  See sample output below.
     */
    private void onReport() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    /**
     * The purpose of this method is to provide an entry point for this program and load/download the data files, and
     * provide a JDialog with combo boxes to select a Station ID and Altitude.  It then updates the JLabels to reflect
     * the relevant information.
     * @param args A place holder for any arguments which may be passed to the program. Does nothing.
     */
    public static void main(String[] args) throws Exception {
        /*
        When your program first starts
            1. Call getFile() in the main class with the file path of the input file (FBIN.txt) as a parameter.
            2. Call getFile() with the World.txt parameter.
            3. Instantiate your WorldStations object
            4. Populate your Locations combo box with the station ID, a dash and the proper case (from your INET object) of the station name using the getName() method in your WorldStations object.
            5. Populate your Altitude combo box.
         */

        getFile(strWindsAloftFilename, strWindsAloftURL, true);
        getFile(strWorldStationsFilename, strWorldStationsURL, false);
        WorldStations worldStations = new WorldStations(strWindsAloftFilename, strWorldStationsFilename);
        A19009 dialog = new A19009();
        dialog.pack();
        dialog.loadAirports(worldStations);
        dialog.loadAltitudes(worldStations);
        dialog.setTitle("A19007 - Michael P. Troester");
        dialog.setVisible(true);
        System.exit(0);
    }

/**
 * The purpose of this method is to check to see if the strFileName exists. If it does exist, the method exits. If it
 * does not exist it downloads it from Internet. If blnExtract is true, it uses the getPREData() method to extract the
 * data from the web page. Finally, it saves the data to disk using the given strFileName.
 */
    private static void getFile(String strFileName, String strURL, boolean blnExtract) throws Exception {
        INET net = new INET();
        String strContent = "";
        if (!net.fileExists(strFileName)) {
            System.out.println("File " + strFileName + " does not exist.");
            if (blnExtract) {
                System.out.println("Downloading file...");
                strContent = net.getURLRaw(strURL);
                System.out.println("Extracting data between PRE tags...");
                strContent = net.getPREData(strContent);
            }
            else {
                System.out.println("Downloading file...");
                strContent = net.getURLRaw(strURL);
            }
            System.out.println("Saving file to " + strFileName);
            net.saveToFile(strFileName, strContent);
        }
    }

    /**
     * The purpose of this method is to load the altitudes into the corresponding JComboBox, then update the output
     * JLables if both the altitude and stations have been loaded.
     */
    public void loadAltitudes(Stations db) {

        for (int x=0; x<=8; x++) {
            cmbAltitude.addItem(aryAltitudes[x]+"000");
            //System.out.println(aryAltitudes[x]+"000"); //For debugging purposes
        }
        intLoadState++;
        if (intLoadState==1)
            updateLabels(cmbLocation.getSelectedItem().toString(), cmbAltitude.getSelectedItem().toString(), db);
    }

    /**
     * The purpose of this method is to load the station IDs from the Stations object and populate the corresponding
     * JComboBox.
     * @param db the Stations object.
     */
    private void loadAirports(Stations db) {
        for (int i = 0; i < db.length(); i++) {
            cmbLocation.addItem(db.getStationID(i));
            //System.out.println(db.getStationID(i)); //for debugging purposes
        }
        intLoadState++;
        if (intLoadState==1)
            updateLabels(cmbLocation.getSelectedItem().toString(), cmbAltitude.getSelectedItem().toString(), db);

    }

    /**
     * The purpose of this method is to update the JLables in the program dialog with the appropriate wind speed, temp,
     * and direction.
     * @param strStationID The three letter station id
     * @param strAltitude the altitude in feet
     */
    public void updateLabels(String strStationID, String strAltitude, Stations db) {
        //System.out.println("StationID: " + strStationID); //used in testing
        //System.out.println("Altitude: " + strAltitude);   //used in testing

        String strStationWeather = db.getStaWea(strStationID);
        //System.out.println("Station Weather: " + strStationWeather); // used in testing

        //Create NWSFB05 object from Station Weather string
        NWSFB05 fb = new NWSFB05(strStationWeather);

        //Update the labels.
        lblWindDir.setText(fb.getWindDirection(strAltitude));
        lblWindSpeed.setText(fb.getWindSpeed(strAltitude));
        lblWindTemp.setText(fb.getWindTemp(strAltitude));


    }
}

//need to restructure updateLabels() fix static/private scopes
//get selected item
class ItemChangeListener implements ItemListener{
    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getStateChange() == ItemEvent.SELECTED) {
            Object item = event.getItem();
            A19009.updateLabels(A19009.cmbLocation.getSelectedItem().toString(), A19009.cmbAltitude.getSelectedItem().toString(), A19009.worldStations);
        }
    }
}