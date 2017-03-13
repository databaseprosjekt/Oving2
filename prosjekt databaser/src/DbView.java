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
			arrayResult.get(0).add(result.getString("Ovelse.ØvelsesID"));
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
}