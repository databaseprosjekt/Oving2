//STEP 1. Import required packages
import java.sql.*;

public class DbView {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no";

   //  Database credentials
   static final String USER = "pciverse_tdt4145";
   static final String PASS = "password";

   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      //STEP 2: Register JDBC driver
      Class.forName("com.mysql.jdbc.Driver");

      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //STEP 4: Execute a query
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql;
      sql = "SELECT * FROM pciverse_tdt4145.Poster";
      ResultSet rs = stmt.executeQuery(sql);

      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         int id  = rs.getInt("PostNr.");
         String beskrivelse = rs.getString("Beskrivelse");

         //Display values
         System.out.print("ID: " + id + " ");
         System.out.println("Beskrivelse: " + beskrivelse);
      }
      //STEP 6: Clean-up environment
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
   }catch(Exception e){
      //Handle errors for Class.forName
      e.printStackTrace();
   }finally{
      //finally block used to close resources
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }// nothing we can do
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }//end finally try
   }//end try
   System.out.println("Goodbye!");
}//end main
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


}//end FirstExample