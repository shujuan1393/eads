package connect;

import entity.Data;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

    public static boolean bootstrap(Connection conn) {

        try {
            //File myFile = new File("C://Users//User 2//Documents//SMUX - Outlet Data V1.xlsx");
//            File myFile = new File("/Users/smu/SMUX - Outlet Data V1.xlsx");
            File myFile = new File("/Users/smu/Documents/Y4/S2/Enterprise Analytics/Project/SMUX - Outlet Data V1.xlsx");

            FileInputStream fis = new FileInputStream(myFile);

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

            // Return first sheet from the XLSX workbook
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // Get iterator to all the rows in current sheet
            Iterator<Row> rowIterator = mySheet.iterator();

            int noOfLines = 0;

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy"); //for transact date
            DateFormat df2 = new SimpleDateFormat("hh:mm a"); //for transact time

            ArrayList<Data> list = new ArrayList<Data>();
            // Traversing over each row of XLSX file
            
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                //initialize a data object
                Data data = new Data(0, 0, "NULL", 0, "NULL", "", "Outlet", 0, 0, "", "", 0, 0, 0);

                if (noOfLines > 0) {
                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
//                        System.out.println("2");
//                        System.out.println("cellIndex = " + cellIndex);
                        switch (cellIndex) {
                            case 0: //customer id

                                data.setCustomerId((int) cell.getNumericCellValue());
                                //System.out.println("case 0");
                                break;
                            case 1: //age
                                try {
                                    data.setAge((int) cell.getNumericCellValue());
                                } catch (Exception e) {
                                    //leave it as 0
                                }

                                break;
                            case 2: //gender
                                data.setGender(cell.getStringCellValue());
                                //System.out.println("case 2");
                                break;
                            case 3: //transact id
                                data.setTransactId((int) cell.getNumericCellValue());
                                //System.out.println("Transact id = " + data.getTransactId() );
                                //System.out.println("case 3");
                                break;
                            case 4: //transact date
                                data.setTransactDate(df.format(cell.getDateCellValue()));
                                //System.out.println("case 4");
                                break;
                            case 5: //transact time
                                data.setTransactTime(df2.format(cell.getNumericCellValue()));
                                //System.out.println("case 5 " + cell.getNumericCellValue() );
                                break;
                            case 6: //outlet
                                data.setOutlet(cell.getStringCellValue());
                                //System.out.println("case 6");
                                break;
                            case 7: //outlet district
                                data.setOutletDistrict((int) cell.getNumericCellValue());
                                //System.out.println("case 7");
                                break;
                            case 8: //transact details id
                                data.setTransactDetailsId((int) cell.getNumericCellValue());
                                //System.out.println("case 8");
                                break;
                            case 9: //item
                                try {
                                    data.setItem(cell.getRichStringCellValue().getString());
                                } catch (Exception e) {
                                    data.setItem(String.valueOf(cell.getNumericCellValue()));
                                }
                                //System.out.println("case 9");
                                break;
                            case 10: //item description
                                data.setItemDesc(cell.getStringCellValue());
                                //System.out.println("case 10");
                                break;
                            case 11: //quantity
                                data.setQuantity((int) cell.getNumericCellValue());
                                //System.out.println("case 11");
                                break;
                            case 12: //price
                                data.setPrice(cell.getNumericCellValue());
                                //System.out.println("case 12");
                                break;
                            case 13: //spending
                                data.setSpending(cell.getNumericCellValue());
                                //System.out.println("case 13");
                                break;
                            default:

                        }
                        
                    }
                    list.add(data);

                    System.out.println("");
//                    break;
                }
                noOfLines++;
            }
            
            //establish connection, sql, execute sql
            try {
                String sql = "Insert into data VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement pstmt = null;
                ResultSet rs = null;
                //upload by batches
                conn.setAutoCommit(false);
                final int batchSize = 500;
//                final int totalRows = 400000;
                int count = 0;
                pstmt = conn.prepareStatement(sql);
                //loop through user list
                for (Data d : list) {
                    pstmt.setInt(1, (int) d.getCustomerId());
                    pstmt.setInt(2, (int) d.getAge());
                    pstmt.setString(3, d.getGender());
                    pstmt.setInt(4, (int) d.getTransactId());
                    pstmt.setString(5, d.getTransactDate());
                    pstmt.setString(6, d.getTransactTime());
                    pstmt.setString(7, d.getOutlet());
                    pstmt.setInt(8, (int) d.getOutletDistrict());
                    pstmt.setInt(9, (int) d.getTransactDetailsId());
                    pstmt.setString(10, d.getItem());
                    pstmt.setString(11, d.getItemDesc());
                    pstmt.setInt(12, (int) d.getQuantity());
                    pstmt.setDouble(13, d.getPrice());
                    pstmt.setDouble(14, d.getSpending());
                    pstmt.addBatch();
                    count++;
                    
//                    if (count % batchSize == 0) {
//                        System.out.println(count + " " + batchSize);
//                        pstmt.executeBatch();
//                        pstmt = conn.prepareStatement(sql);
//                    }
////                    
//                    if (count == totalRows) {
//                        System.out.println(count + " " + totalRows);
//                        break;
//                    }
                    if (count % batchSize == 0) {
                        System.out.println(pstmt);
                        pstmt.executeBatch();
                        pstmt = conn.prepareStatement(sql);
//                        pstmt.executeQuery();
    
                    }
                }
//                pstmt.executeUpdate();
//                pstmt.executeBatch();
                conn.commit();
                //close connection
                conn.close();
            } catch (SQLException k) {
                k.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
