package connect;

import com.monitorjbl.xlsx.StreamingReader;
import entity.FoodCategory;
import entity.Outlet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Jing Xiang on 1/4/2017.
 */
public class LoadOutlet {

    private static final String SELECTUNIQUEOUTLET = "SELECT DISTINCT outlet, outletdistrict " +
            "FROM data";
    private static final String INSERTOUTLETSQL = "INSERT INTO outlets VALUES(?,?,?)";

    public static boolean loadOutlet() {
        Connection conn = DatabaseConnectionManager.connect();
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;

        try {
            pstmt = conn.prepareStatement(SELECTUNIQUEOUTLET);
            rs = pstmt.executeQuery();

            ArrayList<Outlet> outletList = new ArrayList<>();
            while (rs.next()) {
                String outlet = rs.getString(1);
                int outletDistrict = rs.getInt(2);
                String region = getRegion(outletDistrict);

                outletList.add(new Outlet(outlet, outletDistrict, region));
            }

            conn.setAutoCommit(false);
            pstmt2 = conn.prepareStatement(INSERTOUTLETSQL);

            for (Outlet outlet: outletList) {
                pstmt2.setString(1, outlet.getOutlet());
                pstmt2.setInt(2, outlet.getOutletDistrict());
                pstmt2.setString(3, outlet.getRegion());
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

    public static String getRegion(int outletDistrict) {
        String region = "";
        //3, 4 south
        //1, 2, 6, 7, 8, 9, 10, 11, 12 CBD
        //5, 21, 22, 23, 24 west
        //13, 14, 15, 16, 17, 18 east
        //19, 20, 25, 26, 27, 28 north
        switch (outletDistrict) {
            case 3:
            case 4:
                region = "South";
                break;
            case 1:
            case 2:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                region = "CBD";
                break;
            case 5:
            case 21:
            case 22:
            case 23:
            case 24:
                region =  "West";
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                region = "East";
                break;
            case 19:
            case 20:
            case 25:
            case 26:
            case 27:
            case 28:
                region = "North";
                break;
            default:
                break;
        }
        return region;
    }
}
