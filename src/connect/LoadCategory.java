package connect;

import entity.Data;
import com.monitorjbl.xlsx.StreamingReader;
import entity.FoodCategory;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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

    private static final String SQLCREATE = "CREATE TABLE IF NOT EXISTS `category` (\n" +
         "  `item_id` varchar(200) NOT NULL,\n" +
         "  `item_desc` varchar(300) NOT NULL,\n" +
         "  `course` varchar(10) NOT NULL,\n" +
         "  `origin` varchar(50) NOT NULL,\n" +
         "  `tags` varchar(255) NOT NULL,\n" +
         "  `hot_cold` varchar(10) NOT NULL\n" +
         ")";

    private static final String SQLINSERT = "Insert into category VALUES(?,?,?,?,?,?)";
    
    public static boolean loadCategory(Connection conn) {
        
        try {
            int noOfLines = 0;
            PreparedStatement pstmt = null;
            PreparedStatement pstmt2 = null;
            ResultSet rs = null;
            
            ArrayList<FoodCategory> list = new ArrayList<>();
            InputStream is = new FileInputStream(new File("C:/Users/User/Dropbox/Y4S2/EADS/Project/category.xlsx"));

            StreamingReader reader = StreamingReader.builder()
                    .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
                    .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
                    .sheetIndex(0) // index of sheet to use (defaults to 0)
                    .read(is);            // InputStream or File for XLSX file (required)
            int counter = 0;
            for (Row r : reader) {
                counter++;
                
                FoodCategory foodCategory = new FoodCategory("", "", "", "", "", "");
                if (noOfLines > 0) {

                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = r.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        //System.out.println("2");
                        //System.out.println("cellIndex = " + cellIndex);
                        switch (cellIndex) {
                            case 0: //item id

                                foodCategory.setItem_id(cell.getStringCellValue());
                                //System.out.println("case 0");
                                break;
                            case 1: //item desc
                                foodCategory.setItemDesc(cell.getStringCellValue());
                                break;
                            case 2: //course
                                foodCategory.setCourse(cell.getStringCellValue());
                                break;
                            case 3: //transact id
                                foodCategory.setOrigin(cell.getStringCellValue());
                                //System.out.println("Transact id = " + data.getTransactId() );
                                //System.out.println("case 3");
                                break;
                            case 4: //transact date
                                foodCategory.setTags(cell.getStringCellValue());
                                //System.out.println("case 4");
                                break;
                            case 5: //transact time
                                foodCategory.setHotcold(cell.getStringCellValue());
                                break;
                            default:
                        }
                    }
                    list.add(foodCategory);

                    //System.out.println("");
                }
                noOfLines++;
            }
                //establish connection, sql, execute sql
            try {
//                conn = connect.DatabaseConnectionManager.getConnection();
                pstmt = conn.prepareStatement(SQLCREATE);
                pstmt.executeUpdate();
              
                //upload by batches
                conn.setAutoCommit(false);
                //total 556581
                pstmt2 = conn.prepareStatement(SQLINSERT);
                //loop through user list
                for (FoodCategory d : list) {
                    pstmt2.setString(1, d.getItem_id());
                    pstmt2.setString(2, d.getItemDesc());
                    pstmt2.setString(3, d.getCourse());
                    pstmt2.setString(4, d.getOrigin());
                    pstmt2.setString(5, d.getTags());
                    pstmt2.setString(6, d.getHotcold());
                    pstmt2.addBatch();
                }
                //System.out.println(pstmt);
                pstmt2.executeBatch();
                conn.commit();
                System.out.println("current counter = " + counter);
            } catch (SQLException k) {
                k.printStackTrace();
            }  finally {
//            if (conn != null) {
//                connect.DatabaseConnectionManager.closeConnection(conn);
//            }
//            if (pstmt2 != null) {
//                pstmt2.close();
//            }
        }
            list.clear();
           
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
