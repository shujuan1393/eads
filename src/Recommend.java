/*
input member id
select # pax
get time of day
if time of day between 3-6pm or 8-10pm
	recommend snacks
else
	recommend mains
recommend function gets top 5 highest satVal & highest price 


get temperature
temp >= 24
	recommend 5 highest satVal & price cold drinks & desserts
else
	recommend 5 highest satVal & price hot drinks & desserts
*/

import java.util.*;
import java.util.Date.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.*;

public class Recommend{
	static String dbName = "omnomnomnom";
	static String dbusername = "root";
	static String dbpassword = "root";
	
	public static void main(String[] args){
		ArrayList<String> results = new ArrayList<>();
		Scanner sc = new Scanner(System.in);
		// input member id
		System.out.println("Please input member id");
		String memberId = sc.nextLine();
		
		// select # pax
		System.out.println("Please input number of pax");
		int paxNum = sc.nextInt();
		
		// get time of day
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat dateFormat = new SimpleDateFormat("HH");
		java.util.Date date = new java.util.Date();
		int hour = Integer.parseInt(dateFormat.format(date));
		
		// if time of day between 3-6pm or 8-10pm
		if(hour >= 15 && hour <= 18 || hour >= 20 && hour <= 22){
			// recommend snacks
			results.addAll(recommendThingies("snacks"));
		}
		// else
		else{
			// recommend mains
			results.addAll(recommendThingies("mains"));
		}
		
		
		// get temperature
		System.out.println("Please input temperature");
		double temperature = sc.nextDouble();
		
		// temp >= 24
		if(temperature >= 24){
			// recommend 5 highest satVal & price cold drinks & desserts
			results.addAll(recommendThingies("coldDrinks"));
			results.addAll(recommendThingies("coldDesserts"));
		}
		// else
		else{
			// recommend 5 highest satVal & price hot drinks & desserts
			results.addAll(recommendThingies("hotDrinks"));
			results.addAll(recommendThingies("hotDesserts"));
		}
		
		System.out.println("We recommend the following items:");
		results.forEach(s -> System.out.println(s));
		System.out.println("Bon apetit! OM NOM NOM NOM NOM");
	}
	
	public static ArrayList<String> runSql(String sqlStatement){
		ArrayList<String> results = new ArrayList<>();
		try{
			Class.forName("com.mysql.jdbc.Driver");  
			Connection con=DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/"+dbName+"",""+dbusername+"",""+dbpassword+"");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs = stmt.executeQuery(sqlStatement);  
			con.close();
			while(rs.next()){
				// System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
				results.add(rs.getString(1));
			}
			return results;
		}catch(Exception e){ 
			System.out.println(e);
		}
		return results;
	}
	
	public static ArrayList<String> recommendThingies(String type){
		String sqlStatement = "select * " +
														"from foods "+
														"where type = " + type +
														"order by satVal desc " +
														"limit 5";
		ArrayList<String> results = runSql(sqlStatement);
		return results;
	}
}