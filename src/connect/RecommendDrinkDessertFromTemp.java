package connect;

import entity.FoodCategory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jing Xiang on 19/3/2017.
 */
public class RecommendDrinkDessertFromTemp {
    private final static String KEYREF = "781CF461BB6606AD80A87393DAFA402ABEEC7A7B1C282634";
    private final static String DATASET1 = "24hrs_forecast";
    private final static String DATASET2 = "2hr_nowcast";

    private static final String SQLSELECT = "SELECT * FROM category WHERE hot_cold = ? AND course in (?,?)";

    public static void main(String[] args) {
        recommendDrinkDessertFromTemp();
    }

    public static ArrayList<FoodCategory> recommendDrinkDessertFromTemp() {
        ArrayList<FoodCategory> result = new ArrayList<>();
        double temp = getCurrentTemp();
        String hotOrCold = "hot";
        if (temp >= 24.0) {
            // recommend cold stuff
            hotOrCold = "cold";
        }

        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;

        try {
            pstmt = conn.prepareStatement(SQLSELECT);
            pstmt.setString(1, hotOrCold);
            pstmt.setString(2, "drinks");
            pstmt.setString(3, "dessert");

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                String itemID = resultSet.getString(1);
                String itemDesc = resultSet.getString(2);
                String course = resultSet.getString(3);
                String origin = resultSet.getString(4);
                String tags = resultSet.getString(5);
                String hotCold = resultSet.getString(6);
                FoodCategory foodCategory = new FoodCategory(itemID, itemDesc, course, origin, tags, hotCold);
                System.out.println(""+itemID + " " + itemDesc +" " + course + " " + origin + " " + tags + " " +hotCold);
                result.add(foodCategory);
            }
            resultSet.close();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


    public static double getCurrentTemp(){
        double tempAvg = 40.0;
        try {
            String input = callWebAPI(DATASET1);
            if (input != null) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                StringBuilder xmlStringBuilder = new StringBuilder();
                xmlStringBuilder.append(input);
                ByteArrayInputStream byteArrayInputStreamnput = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
                Document doc = builder.parse(byteArrayInputStreamnput);

                NodeList temperatureNodeList = doc.getElementsByTagName("temperature");
                Node temperatureNode = temperatureNodeList.item(0);
                String temperatureHigh = temperatureNode.getAttributes().getNamedItem("high").getNodeValue();
                String temperatureLow = temperatureNode.getAttributes().getNamedItem("low").getNodeValue();
                double tempHigh = Double.parseDouble(temperatureHigh);
                double tempLow = Double.parseDouble(temperatureLow);
                tempAvg = (tempHigh + tempLow) / 2;
                System.out.println("Average temperature now is: " + tempAvg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempAvg;
    }


    private static String callWebAPI(String datasetName) throws Exception {
        // Step 1: Construct URL
        String url = "http://api.nea.gov.sg/api/WebAPI/?dataset=" + datasetName + "&keyref=" + KEYREF;

        // Step 2: Call API Url
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection)obj.openConnection();
        con.setRequestMethod("GET");
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : "+ url);
        System.out.println("Response Code : "+ responseCode);

        // Step 3: Check the response status
        if (responseCode == 200) {
            // Step 3a: If response status== 200
            // print the received xml
            return readStream(con.getInputStream());
        }
        else
        {
            // Step 3b: If response status != 200
            // print the error received from server
            System.out.println("Error in accessing API - " + readStream(con.getErrorStream()));
            return null;
        }
    }
    // Read the responded result
    private static String readStream(InputStream inputStream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
}
