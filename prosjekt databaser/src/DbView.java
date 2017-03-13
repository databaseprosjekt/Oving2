//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;

public class DbView {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no";

   //  Database credentials
   static final String USER = "pciverse_tdt4145";
   static final String PASS = "password";

   private DbQuerys querys = new DbQuerys();

   Connection conn = null;
   Statement stmt = null;

   public DbView(){
	   try{
		   Class.forName("com.mysql.jdbc.Driver");
	   }catch(Exception e){
		   System.out.println(e);
	   }
   }

   public ArrayList<ArrayList> getOvelseFraGruppe(int groupID)
   {
	  ResultSet result =  enquire(querys.getOvelseFraGruppe(groupID));
	  ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<String>());
	  
	  try {
		while(result.next())
		{
			arrayResult.get(1).add(result.getString("Ovelse.Navn"));
			arrayResult.get(0).add(result.getString("Ovelse.�velsesID"));
		}
		  closeConnection(result);
		  return arrayResult;
		  
	} catch (SQLException e) 
	  	{
		e.printStackTrace();
	  	}
	  closeConnection(result);
	  return null;
   }
   public ArrayList<ArrayList> getsubGruppe(int groupID)
   {
	  ResultSet result =  enquire(querys.getSubGrupper(groupID));
	  ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<String>());
	  
	  try {
		while(result.next())
		{
			arrayResult.get(1).add(result.getString("Navn"));
			arrayResult.get(0).add(result.getString("AltGruppeID"));
		}
		  closeConnection(result);
		  return arrayResult;
		  
	} catch (SQLException e) 
	  	{
		e.printStackTrace();
	  	}
	  closeConnection(result);
	  return null;
   }
   public ArrayList<ArrayList> getIkkeSubGrupper()
   {
	  ResultSet result =  enquire(querys.getIkkeSubGrupper());
	  ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<String>());
	  
	  try {
		while(result.next())
		{
			arrayResult.get(1).add(result.getString("Navn"));
			arrayResult.get(0).add(result.getString("GruppeID"));
		}
		  closeConnection(result);
		  return arrayResult;
		  
	} catch (SQLException e) 
	  	{
		e.printStackTrace();
	  	}
	  closeConnection(result);
	  return null;
   }
   public void newTrening(String dato, String tidspunkt, int varighet, int form, int prestasjon, String notat) {
	   ResultSet result = enquire(querys.newTrening(dato, tidspunkt, varighet, form, prestasjon, notat));
	   closeConnection(result);
   }
   
	public ResultSet enquire(String sql){
		try {
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

	      //STEP 4: Execute a query
	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);

	      return rs;
	      
		   }catch(SQLException se){
			   se.printStackTrace();
			   }
		System.out.println("Database error");
		return null;
		
	}
	public void closeConnection(ResultSet rs){
		try {
	      rs.close();
	      stmt.close();
	      conn.close();
		   }catch(Exception se){
			      //Handle errors for JDBC
			      se.printStackTrace(); 
		}
	}


	 /*
	   public void addTreningsØkt(Date date, Time time, int varighet, int form, int prestasjon, String notat){
	      String sql = "INSERT INTO Treningsøkt VALUES (" + date + ", " + time + ", " + varighet + ", " + form + ", " + prestasjon + ", " + notat + ");";
	      // PROBLEM: Her kan det bli noe problem hvis java ikke vil konvertere int/date/time til string. pls no.
	      // Kanskje vi må definere stmt eller whatever her, og på alle metoder. Eller så kan vi ha metodene inni main.
	      ResultSet rs = stmt.executeQuery(sql); // eller noe. THIS IS AN WRONG atm PROBLEM
	   }

	   public void addGruppe(String navn){
	      String sql = "INSERT INTO Gruppe VALUES " + navn + ");";
	      ResultSet rs = stmt.executeQuery(sql); // THIS IS AN WRONG atm PROBLEM
	   }

	   public void addØvelse(String øvelse, String beskrivelse){
	      String sql = "INSERT INTO Øvelse " + øvelse + ", " + beskrivelse + ");";
	      ResultSet rs = stmt.executeQuery(sql); // THIS IS AN WRONG atm PROBLEM
	   }
 
	   public void addPeriode(Date fraDate, Date tilDate){
	      String sql = "INSERT INTO Øvelse " + fraDate + ", " + tilDate+ ");";
	      ResultSet rs = stmt.executeQuery(sql); // THIS IS AN WRONG atm PROBLEM
	   }

	*/
}