package connect;

import entity.Data;
import com.monitorjbl.xlsx.StreamingReader;
import entity.Customer;
import entity.Outlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
public class Bootstrap {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //for transact date
    private static DateFormat df2 = new SimpleDateFormat("h:mm:ss a"); //for transact time
    private static Calendar time = Calendar.getInstance();

    public static boolean bootstrap() {
        Connection conn = DatabaseConnectionManager.connect();

        try {
            
            int noOfLines = 0;

            ArrayList<Data> list = new ArrayList<>();
            Set<Customer> clist = new HashSet<>();
            Set<Outlet> olist = new HashSet<>();
            
            InputStream is = new FileInputStream(new File("./excel/SMUX - Outlet Data V1.xlsx"));

            StreamingReader reader = StreamingReader.builder()
                    .rowCacheSize(100) // number of rows to keep in memory (defaults to 10)
                    .bufferSize(4096) // buffer size to use when reading InputStream to file (defaults to 1024)
                    .sheetIndex(0) // index of sheet to use (defaults to 0)
                    .read(is);            // InputStream or File for XLSX file (required)
            int counter = 0;
            for (Row r : reader) {
                counter++;

                //initialize a data object
                Data data = new Data(0, 0, "NULL", 0, "NULL", "", "Outlet", 0, 0, "", "", 0, 0, 0);
                
                if (noOfLines > 0) {
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = r.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();

                        switch (cellIndex) {
                            case 0: //customer id
                                int custid = (int) cell.getNumericCellValue();
                                data.setCustomerId(custid);
                                break;
                            case 1: //age
                                try {
                                    int age = (int) cell.getNumericCellValue();
                                    data.setAge(age);
                                } catch (Exception e) {
                                    //leave it as 0
                                }

                                break;
                            case 2: //gender
                                String gender = cell.getStringCellValue(); 
                                data.setGender(gender);
                                //System.out.println("case 2");
                                break;
                            case 3: //transact id
                                data.setTransactId((int) cell.getNumericCellValue());
                                break;
                            case 4: //transact date
                                data.setTransactDate(df.format(cell.getDateCellValue()));
                                break;
                            case 5: //transact time
                                time.setTime(cell.getDateCellValue());
                                data.setTransactTime(df2.format(time.getTime()));
                                break;
                            case 6: //outlet
                                data.setOutlet(cell.getStringCellValue());
                                break;
                            case 7: //outlet district
                                data.setOutletDistrict((int) cell.getNumericCellValue());
                                break;
                            case 8: //transact details id
                                data.setTransactDetailsId((int) cell.getNumericCellValue());
                                break;
                            case 9: //item
                                //try {
                                data.setItem(cell.getStringCellValue());
                                break;
                            case 10: //item description
                                data.setItemDesc(cell.getStringCellValue());
                                break;
                            case 11: //quantity
                                data.setQuantity((int) cell.getNumericCellValue());
                                break;
                            case 12: //price
                                data.setPrice(cell.getNumericCellValue());
                                break;
                            case 13: //spending
                                double spending = cell.getNumericCellValue();
                                data.setSpending(spending);
                                break;
                            default:
                        }
                    }
                    list.add(data);
                }

                noOfLines++;
                if (noOfLines == 32740 ) {
                    
                    //establish connection, sql, execute sql
                    try {
                        String sql = "Insert into data VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pstmt = null;
                        //upload by batches
                        conn.setAutoCommit(false);
                        //total 556581
                        
                        pstmt = conn.prepareStatement(sql);
                        //loop through user list
                        for (Data d : list) {
                            pstmt.setInt(1, d.getCustomerId());
                            pstmt.setInt(2, d.getAge());
                            pstmt.setString(3, d.getGender());
                            pstmt.setInt(4, d.getTransactId());
                            pstmt.setString(5, d.getTransactDate());
                            pstmt.setString(6, d.getTransactTime());
                            pstmt.setString(7, d.getOutlet());
                            pstmt.setInt(8, d.getOutletDistrict());
                            pstmt.setInt(9, d.getTransactDetailsId());
                            pstmt.setString(10, d.getItem());
                            pstmt.setString(11, d.getItemDesc());
                            pstmt.setInt(12, d.getQuantity());
                            pstmt.setDouble(13, d.getPrice());
                            pstmt.setDouble(14, d.getSpending());
                            pstmt.addBatch();
                        }

                        //System.out.println(pstmt);
                        pstmt.executeBatch();
                        pstmt.close();
                        conn.commit();
                        System.out.println("current counter = " + counter);
                    } catch (SQLException k) {
                        k.printStackTrace();
                    }
                    
                    noOfLines = 1;
                    //System.out.println("batch submitted");
                } else if (counter > 556560 && counter <= 556580){
                    try {
                        String sql = "Insert into data VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pstmt = null;
                        //upload by batches
                        conn.setAutoCommit(false);
                        //total 556581
                        
                        pstmt = conn.prepareStatement(sql);
                        //loop through user list
                        for (Data d : list) {
                            pstmt.setInt(1, d.getCustomerId());
                            pstmt.setInt(2, d.getAge());
                            pstmt.setString(3, d.getGender());
                            pstmt.setInt(4, d.getTransactId());
                            pstmt.setString(5, d.getTransactDate());
                            pstmt.setString(6, d.getTransactTime());
                            pstmt.setString(7, d.getOutlet());
                            pstmt.setInt(8, d.getOutletDistrict());
                            pstmt.setInt(9, d.getTransactDetailsId());
                            pstmt.setString(10, d.getItem());
                            pstmt.setString(11, d.getItemDesc());
                            pstmt.setInt(12, d.getQuantity());
                            pstmt.setDouble(13, d.getPrice());
                            pstmt.setDouble(14, d.getSpending());
                            //pstmt.addBatch();
                        }
                        //System.out.println(pstmt);
                        pstmt.executeUpdate();
                        //conn.commit();
                        System.out.println("current counter = " + counter);
                    } catch (SQLException k) {
                        k.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return true;
    }
}
