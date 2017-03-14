package connect;

import entity.Data;
import com.monitorjbl.xlsx.StreamingReader;
import entity.Customer;
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
public class Bootstrap {
    private static final String SQLCREATE = "CREATE TABLE IF NOT EXISTS `users` (\n" +
         "  `customerid` varchar(10) NOT NULL,\n" +
         "  `age` varchar(10) NOT NULL,\n" +
         "  `gender` varchar(50) NOT NULL,\n" +
         "  `spending` double NOT NULL,\n" +
                "   PRIMARY KEY(`customerid`))";
    
    public static boolean bootstrap(Connection conn) {
        PreparedStatement userpmt = null;
        PreparedStatement insertUser = null;
        try {
            //File myFile = new File("C://Users//User 2//Documents//SMUX - Outlet Data V1.xlsx");
            //File myFile = new File("C://Users//User 2//Documents//SMUX - Outlet Data V1.xlsx");

            //FileInputStream fis = new FileInputStream(myFile);
            // Finds the workbook instance for XLSX file
            //XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
            // Return first sheet from the XLSX workbook
            //XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            // Get iterator to all the rows in current sheet
            //Iterator<Row> rowIterator = mySheet.iterator();
            userpmt = conn.prepareStatement(SQLCREATE);
            userpmt.executeUpdate();
            int noOfLines = 0;

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd"); //for transact date
            DateFormat df2 = new SimpleDateFormat("h:mm:ss a"); //for transact time
            Calendar time = Calendar.getInstance();
            ArrayList<Data> list = new ArrayList<Data>();
            ArrayList<Customer> clist = new ArrayList<Customer>();
            
            InputStream is = new FileInputStream(new File("/Users/smu/Documents/Y4/S2/Enterprise Analytics/Project/SMUX - Outlet Data V1.xlsx"));

//            InputStream is = new FileInputStream(new File("C://Users//User 2//Documents//SMUX - Outlet Data V1.xlsx"));
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
                Customer cust = new Customer(0, 0, "", 0);
                
                if (noOfLines > 0) {

                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = r.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        int cellIndex = cell.getColumnIndex();
                        //System.out.println("2");
                        //System.out.println("cellIndex = " + cellIndex);
                        switch (cellIndex) {
                            case 0: //customer id
                                int custid = (int) cell.getNumericCellValue();
                                data.setCustomerId(custid);
                                cust.setCustomerId(custid);
                                //System.out.println("case 0");
                                break;
                            case 1: //age
                                try {
                                    int age = (int) cell.getNumericCellValue();
                                    data.setAge(age);
                                    cust.setAge(age);
                                } catch (Exception e) {
                                    //leave it as 0
                                }

                                break;
                            case 2: //gender
                                String gender = cell.getStringCellValue(); 
                                data.setGender(gender);
                                cust.setGender(gender);
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
                                time.setTime(cell.getDateCellValue());
                               //System.out.println("case 5 " + df2.format(time.getTime()) );
                                data.setTransactTime(df2.format(time.getTime()));
                                //data.setTransactTime(df2.format(cell.getNumericCellValue()));
                                //System.out.println("case 5 " + cell.getNumericCellValue());
                                //System.out.println("case 5 " + df2.format(cell.getNumericCellValue()));
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
                                //try {
                                data.setItem(cell.getStringCellValue());
                                //} catch (Exception e) {
                                //  data.setItem(String.valueOf((int) cell.getNumericCellValue()));
                                //}
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
                                double spending = cell.getNumericCellValue();
                                data.setSpending(spending);
                                cust.setSpending(spending);
                                //System.out.println("case 13");
                                break;
                            default:

                        }
                    }
                    list.add(data);
                    //System.out.println("");
                }
                
                if(clist.isEmpty()) {
                    clist.add(cust);
                } else {
                    boolean isCreated = false;
                    
                    for (Customer c : clist) {
                        int stored = c.getCustomerId();
                        int cur = cust.getCustomerId();
                        
                        if (stored == cur) {
                            isCreated = true;
                            break;
                        } else {
                            isCreated = false;
                        }
                    }
                    if (isCreated == false) {
                        clist.add(cust);         
                    }
                }
                
                noOfLines++;
                if (noOfLines == 32740 ) {
                    
                    //establish connection, sql, execute sql
                    try {
                        String sql = "Insert into data VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        String users = "Insert into users VALUES(?,?,?,?)";
                        PreparedStatement pstmt = null;
                        ResultSet rs = null;
                        //upload by batches
                        conn.setAutoCommit(false);
                        //total 556581
                        
                        pstmt = conn.prepareStatement(sql);
                        insertUser = conn.prepareStatement(users);
                        
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
                        
                        for (Customer d : clist) {
                            insertUser.setInt(1, d.getCustomerId());
                            insertUser.setInt(2, d.getAge());
                            insertUser.setString(3, d.getGender());
                            insertUser.setDouble(4, d.getSpending());
                            insertUser.addBatch();
                        }
                        //System.out.println(pstmt);
                        insertUser.executeBatch();
                        
                        //System.out.println(pstmt);
                        pstmt.executeBatch();
                        conn.commit();
                        System.out.println("current counter = " + counter);
                    } catch (SQLException k) {
                        k.printStackTrace();
                    }
                    
                    noOfLines = 1;
                    list.clear();
                    clist.clear();
                    //System.out.println("batch submitted");
                } else if (counter > 556560 && counter <= 556580){
                    try {
                        String sql = "Insert into data VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                        PreparedStatement pstmt = null;
                        ResultSet rs = null;
                        //upload by batches
                        //conn.setAutoCommit(false);
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
            
            //close connection
//            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
