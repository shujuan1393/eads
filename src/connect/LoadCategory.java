package connect;

import com.monitorjbl.xlsx.StreamingReader;
import entity.FoodCategory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import entity.Outlet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User 2
 */
public class LoadCategory {

    private static final String SELECTUNIQUECATEGORY = "SELECT DISTINCT item, itemdesc, min(price) FROM data GROUP BY item, itemdesc";
    private static final String INSERTCATEGORYSQL = "INSERT INTO category VALUES (?,?,?,?,?,?,?)";
    
    public static boolean loadCategory() {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        try {
            int noOfLines = 0;

            HashMap<String, FoodCategory> foodCategoryHashMap = new HashMap<>();
            InputStream is = new FileInputStream(new File("./excel/category.xlsx"));

            StreamingReader reader = StreamingReader.builder()
                    .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
                    .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
                    .sheetIndex(0) // index of sheet to use (defaults to 0)
                    .read(is);            // InputStream or File for XLSX file (required)
            int counter = 0;
            for (Row r : reader) {
                counter++;

                FoodCategory foodCategory = new FoodCategory("", "", 0.0,"", "", "", "");
                if (noOfLines > 0) {

                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = r.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        switch (cellIndex) {
                            case 0:
                                foodCategory.setItem_id(cell.getStringCellValue());
                                break;
                            case 1:
                                foodCategory.setItemDesc(cell.getStringCellValue());
                                break;
                            case 2:
                                foodCategory.setCourse(cell.getStringCellValue());
                                break;
                            case 3:
                                foodCategory.setOrigin(cell.getStringCellValue());
                                break;
                            case 4:
                                foodCategory.setTags(cell.getStringCellValue());
                                break;
                            case 5:
                                foodCategory.setHotcold(cell.getStringCellValue());
                                break;
                            default:
                        }
                    }
                    foodCategoryHashMap.put(foodCategory.getItem_id()+"|"+foodCategory.getItemDesc(), foodCategory);
                }
                noOfLines++;
            }


            pstmt = conn.prepareStatement(SELECTUNIQUECATEGORY);
            rs = pstmt.executeQuery();

            ArrayList<FoodCategory>  foodCategoryList = new ArrayList<>();
            while (rs.next()) {
                String item = rs.getString(1);
                String itemdesc = rs.getString(2);
                double price = rs.getDouble(3);
                foodCategoryList.add(new FoodCategory(item, itemdesc, price));
            }

            conn.setAutoCommit(false);
            pstmt2 = conn.prepareStatement(INSERTCATEGORYSQL);

            for (FoodCategory foodCategory: foodCategoryList) {
                pstmt2.setString(1, foodCategory.getItem_id());
                pstmt2.setString(2, foodCategory.getItemDesc());
                pstmt2.setDouble(3, foodCategory.getPrice());

                String key = foodCategory.getItem_id()+"|"+foodCategory.getItemDesc();
                FoodCategory mapFoodCategory = foodCategoryHashMap.get(key);
                if (mapFoodCategory == null) {
                    continue;
                }

                pstmt2.setString(4, mapFoodCategory.getCourse());
                pstmt2.setString(5, mapFoodCategory.getOrigin());
                pstmt2.setString(6, mapFoodCategory.getTags());
                pstmt2.setString(7, mapFoodCategory.getHotcold());
                pstmt2.addBatch();
            }
            pstmt2.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn!= null) {
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
