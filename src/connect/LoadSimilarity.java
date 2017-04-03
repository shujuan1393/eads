package connect;

import entity.FoodCategory;
import entity.FoodSimilarity;
import entity.SatisfactionValues;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by Jing Xiang on 1/4/2017.
 */
public class LoadSimilarity {

    private static final String SELECTALLCATEGORY = "SELECT *  " +
            "FROM category";
    private static final String INSERTSIMILARITYSQL = "INSERT INTO similarity VALUES (?,?,?,?,?)";


    private static final String SELECTUSERCATEGORY = "SELECT s.cust_id, c.item_id, c.item_desc, s.satisfaction_value \n"+
            "FROM (SELECT * FROM satisfaction_values WHERE cust_id = ?) \n"+
            " AS s RIGHT JOIN category c ON s.item_id = c.item_id AND s.item_desc = c.item_desc";


    private static final String SELECTSIMILARITY = "SELECT item_id2, item_desc2, similarityval \n"+
            "FROM similarity \n"+
            "WHERE item_id = ? AND item_desc = ?";


    public static void processSimilarity(int custID) {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;

        try {
            pstmt = conn.prepareStatement(SELECTUSERCATEGORY);
            pstmt.setInt(1, custID);
            rs = pstmt.executeQuery();

            HashMap<String, SatisfactionValues> toBeReturned = new HashMap<>();
            ArrayList<SatisfactionValues> satisfactionValuesArrayList= new ArrayList<>();
            while (rs.next()) {
                int customerID = rs.getInt(1);
                String itemID = rs.getString(2);
                String itemDesc = rs.getString(3);
                double satisfaction = rs.getDouble(4);

                if (satisfaction != 0) {
                    satisfactionValuesArrayList.add(new SatisfactionValues(custID, itemID, itemDesc, satisfaction));
                } else {
                    toBeReturned.put(itemID+"|"+itemDesc, new SatisfactionValues(custID, itemID, itemDesc, satisfaction));
                }
            }

            System.out.println("Existing");
            for (int n = 0; n < satisfactionValuesArrayList.size(); n++) {
                SatisfactionValues s = satisfactionValuesArrayList.get(n);
                double frequency = s.getSatisfaction_value();

                System.out.println(s.getCust_id() + ", " + s.getItem_id() + ", " + s.getItem_desc() + ", " + s.getSatisfaction_value());

                pstmt2 = conn.prepareStatement(SELECTSIMILARITY);
                pstmt2.setString(1, s.getItem_id());
                pstmt2.setString(2, s.getItem_desc());
                rs2 = pstmt2.executeQuery();

                while (rs2.next()) {
                    String itemID2 = rs2.getString(1);
                    String itemDesc2 = rs2.getString(2);
                    double similarityVal = rs2.getDouble(3);

                    String key = itemID2+"|"+itemDesc2;
                    SatisfactionValues satisfaction = toBeReturned.get(key);
                    if (satisfaction == null) {
                        continue;
                    }
                    double newSatisfactionValue = Math.max(satisfaction.getSatisfaction_value(),similarityVal*frequency);
                    satisfaction.setSatisfaction_value(newSatisfactionValue);
                    toBeReturned.put(key, satisfaction);
                }
            }

            ArrayList<SatisfactionValues> result = new ArrayList<>(toBeReturned.values());
            System.out.println("New ");
            for (SatisfactionValues s: result) {
                System.out.println(s.getCust_id() + ", " + s.getItem_id() + ", " + s.getItem_desc() + ", " + s.getSatisfaction_value());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static boolean loadSimilarity() {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECTALLCATEGORY);
            rs = pstmt.executeQuery();

            ArrayList<FoodCategory> foodCategoryArrayList = new ArrayList<>();
            while (rs.next()) {
                String itemID = rs.getString(1);
                String itemDesc = rs.getString(2);
                double price = rs.getDouble(3);
                String course = rs.getString(4);
                String origin = rs.getString(5);
                String tags = rs.getString(6);
                String hotCold = rs.getString(7);
                foodCategoryArrayList.add(new FoodCategory(itemID, itemDesc, price, course, origin, tags, hotCold));
            }

            ArrayList<FoodSimilarity> foodSimilarities = new ArrayList<>();

            for (FoodCategory food1 : foodCategoryArrayList) {
                String itemID1 = food1.getItem_id();
                String itemDesc1 = food1.getItemDesc();
                String origin1 = food1.getOrigin();
                String hotCold1 = food1.getHotcold();
                String tag1 = food1.getTags();
                String[] tags1 = tag1.split(",");
                ArrayList<String> tagsList1 = new ArrayList<String>(Arrays.asList(tags1));

                for (FoodCategory food2: foodCategoryArrayList) {
                    String itemID2 = food2.getItem_id();
                    String itemDesc2 = food2.getItemDesc();
                    if (itemID1.equals(itemID2) && itemDesc1.equals(itemDesc2)) {
                        continue;
                    }
                    String origin2 = food2.getOrigin();
                    String hotCold2 = food2.getHotcold();
                    String tag2 = food2.getTags();
                    String[] tags2 = tag2.split(",");
                    ArrayList<String> tagsList2 = new ArrayList<String>(Arrays.asList(tags2));

                    int totalPoints = 2 + (origin1.isEmpty()?0:1) + (origin2.isEmpty()?0:1) + tags1.length + tags2.length;
                    int similarPoints = 0;

                    if (origin1.equals(origin2) && !origin1.isEmpty()) {
                        similarPoints++;
                    }
                    if (hotCold1.equals(hotCold2)) {
                        similarPoints++;
                    }
                    tagsList2.retainAll(tagsList1);
                    int intersectionSize = tagsList2.size() * 2;
                    similarPoints += intersectionSize;

                    double similarity = similarPoints*1.0 / totalPoints;
                    foodSimilarities.add(new FoodSimilarity(itemID1, itemDesc1, itemID2, itemDesc2, similarity));
                    //System.out.println(itemDesc1 + " + " + itemDesc2 + " : " + similarPoints + "/" + totalPoints);
                }
            }


            conn.setAutoCommit(false);
            pstmt2 = conn.prepareStatement(INSERTSIMILARITYSQL);

            for (FoodSimilarity similarity: foodSimilarities) {
                pstmt2.setString(1, similarity.getItemID());
                pstmt2.setString(2, similarity.getItemDesc());
                pstmt2.setString(3, similarity.getItemID2());
                pstmt2.setString(4, similarity.getItemDesc2());
                pstmt2.setDouble(5, similarity.getSimilarityVal());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
            conn.commit();
        } catch (SQLException k) {
            k.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

}
