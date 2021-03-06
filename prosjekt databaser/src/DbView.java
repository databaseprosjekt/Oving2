//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;

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
   
   
   public ArrayList<ArrayList> getOvelseFraTrening(int sessionID)
   {
	   ResultSet result = enquire(querys.getOvelseFraTrening(sessionID));
	   ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<String>());
	   arrayResult.add(new ArrayList<String>());
	   try {
		   while(result.next())
		   {
			   arrayResult.get(0).add(result.getString("�velsesID"));
			   arrayResult.get(1).add(result.getString("Navn"));
			   arrayResult.get(2).add(result.getString("Beskrivelse"));
		   }
		   closeConnection(result);
		   return arrayResult;
	   }catch (SQLException e)
	   {
		   e.printStackTrace();
	   }
	   closeConnection(result);
	   return null;
   }

   /**
    * Gives �velse for a given gruppe
    * @param groupID
    * @return ArrayList<ArrayList>
    */
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
   /**
    * All group with relation to groupid
    * @param groupID
    * @return ArrayList<ArrayList>
    */
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
   public ArrayList<ArrayList> getPeriodes()
   {
	  ResultSet result =  enquire(querys.getPeriodes());
	  ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<Date>());
	  arrayResult.add(new ArrayList<Date>());

	  try {
		while(result.next())
		{
			arrayResult.get(0).add(result.getInt("PeriodeID"));
			arrayResult.get(1).add(result.getDate("FraDato"));
			arrayResult.get(2).add(result.getDate("TilDato"));

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
   /**
    * Give you all groups with no relation
    * @return ArrayList<ArrayList>
    */
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

   public ArrayList<ArrayList> getAlleTreninger()
   {
	   ResultSet result = enquire(querys.getAlleTreninger());
	   ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<Date>());
	   arrayResult.add(new ArrayList<Time>());
	   arrayResult.add(new ArrayList<Time>());
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<String>());

	   try {
			while(result.next())
			{
				arrayResult.get(0).add(result.getInt("�ktID"));
				arrayResult.get(1).add(result.getDate("Dato"));
				arrayResult.get(2).add(result.getTime("Tidspunkt"));
				arrayResult.get(3).add(result.getTime("Varighet"));
				arrayResult.get(4).add(result.getInt("Form"));
				arrayResult.get(5).add(result.getInt("Prestasjon"));
				arrayResult.get(6).add(result.getString("Notat"));
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
   
   public ArrayList<Object> getOvelse(int exerciseID)
   {
       ResultSet result = enquire(querys.getOvelse(exerciseID));
       ArrayList<Object> arrayResult = new ArrayList<Object>();
       try {
           if(result.next())
           {
               arrayResult.add(result.getInt("�velsesID"));
               arrayResult.add(result.getString("Navn"));
               arrayResult.add(result.getString("Beskrivelse"));
           }
           closeConnection(result);
           return arrayResult;
       }catch (SQLException e)
       {
           e.printStackTrace();
       }
       closeConnection(result);
       return null;
   }
   public ArrayList<Object> getInne(int sessionID)
   {
	   ResultSet result = enquire(querys.getInne(sessionID));
	   ArrayList<Object> arrayResult = new ArrayList<Object>();

	   try {
			if(result.next())
			{
				arrayResult.add(result.getString("Luftventilasjon"));
				arrayResult.add(result.getInt("Antall_tilskuere"));
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

   public ArrayList<Object> getUte(int sessionID)
   {
	   ResultSet result = enquire(querys.getUte(sessionID));
	   ArrayList<Object> arrayResult = new ArrayList<Object>();

	   try {
			if(result.next())
			{
				arrayResult.add(result.getString("V�rforhold"));
				arrayResult.add(result.getString("V�rtype"));
				arrayResult.add(result.getInt("Temperatur"));
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
   public ArrayList<ArrayList> getWorkoutForPeriod(int periodeID)
   {
	   ResultSet result = enquire(querys.getWorkoutFromPeriod(periodeID));
	   ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<Date>());
	   arrayResult.add(new ArrayList<Time>());
	   arrayResult.add(new ArrayList<Time>());
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<Integer>());
	   arrayResult.add(new ArrayList<String>());


	   try {
			while(result.next())
			{
				arrayResult.get(0).add(result.getInt("�ktID"));
				arrayResult.get(1).add(result.getDate("Dato"));
				arrayResult.get(2).add(result.getTime("Tidspunkt"));
				arrayResult.get(3).add(result.getTime("Varighet"));
				arrayResult.get(4).add(result.getInt("Form"));
				arrayResult.get(5).add(result.getInt("Prestasjon"));
				arrayResult.get(6).add(result.getString("Notat"));
				
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


   /**
    * Ads new Trenings�kt to db
    * @param dato
    * @param tidspunkt
    * @param varighet
    * @param form
    * @param prestasjon
    * @param notat
    */
   public int newTrening(String dato, String tidspunkt, int varighet, int form, int prestasjon, String notat) {
	   ResultSet result = insert(querys.newTrening(dato, tidspunkt, varighet, form, prestasjon, notat));
	   int id = -1;
	   try{
		   result.next();
		   id = result.getInt(1);
	   } catch(SQLException e){}
	   closeConnection(result);
	   return id;
   }

   /**
    * Adds new Oving to Trenings�kt
    * @param ovingid
    * @param treningid
    */
   public void newOving(int ovingid, int treningid) {
	   ResultSet result = insert(querys.newOving(ovingid, treningid));
	   closeConnection(result);
   }
   /**
    * Sets Trenings�kt til inneaktivitet
    * @param oktid
    * @param ventelasjon
    * @param antallTilskuere
    */
   public void setInne(int oktid, String ventelasjon, int antallTilskuere) {
	   ResultSet result = insert(querys.setInne(oktid, ventelasjon, antallTilskuere));
	   closeConnection(result);
   }
   /**
    * sets Trenings�kt til uteaktivitet
    * @param oktid
    * @param verForhold
    * @param verType
    * @param temp
    */
   public void setUte(int oktid, String verForhold, String verType,  int temp){
	   ResultSet result = insert(querys.setUte(oktid, verForhold, verType, temp));
	   closeConnection(result);
   }



   public ArrayList<ArrayList> getTreningFromPeriode(int periode) {
	  ResultSet result =  enquire(querys.getTreningFromPeriode(periode));
	  ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<Date>());
	  arrayResult.add(new ArrayList<Date>());
	  arrayResult.add(new ArrayList<Integer>());
	  try {
			while(result.next())
			{
				arrayResult.get(0).add(result.getString("p.PeriodeID"));
				arrayResult.get(1).add(result.getDate("p.FraDato"));
				arrayResult.get(2).add(result.getDate("p.TilDato"));
				arrayResult.get(3).add(result.getString("COUNT(*)"));
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

   public ArrayList<ArrayList> getResultatFromPeriode(int periode) {
	  ResultSet result =  enquire(querys.getResultatFromPeriode(periode));
	  ArrayList<ArrayList> arrayResult = new ArrayList<ArrayList>();
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<Integer>());
	  arrayResult.add(new ArrayList<String>());
	  try {
			while(result.next())
			{
				arrayResult.get(0).add(result.getInt("p.PeriodeID"));
				arrayResult.get(1).add(result.getInt("t.�ktID"));
				arrayResult.get(2).add(result.getInt("r.ResultatID"));
				arrayResult.get(3).add(result.getInt("t.Prestasjon"));
				arrayResult.get(4).add(result.getString("t.Notat"));
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

   public String getTotaltResults(){
	   ResultSet result = enquire(querys.getTotalResaults());
	   String returnValue = null;
		  try {
			while(result.next())
			{
				returnValue = result.getString("COUNT(*)");
			}
			  closeConnection(result);
			  return returnValue;


		} catch (SQLException e)
		  	{
			e.printStackTrace();
		  	}
		  closeConnection(result);
		  return null;

   }

   public String getTotalWorkouts(){
	   ResultSet result = enquire(querys.getTotalWorkouts());
	   String returnValue = null;
		  try {
			while(result.next())
			{
				returnValue = result.getString("COUNT(*)");
			}
			  closeConnection(result);
			  return returnValue;

		} catch (SQLException e)
		  	{
			e.printStackTrace();
		  	}
		  closeConnection(result);
		  return null;

   }
	public String getTotaltExercies() {
		   ResultSet result = enquire(querys.getTotaltExercies());
		   String returnValue = null;
			  try {
				while(result.next())
				{
					returnValue = result.getString("COUNT(*)");
				}
				  closeConnection(result);
				  return returnValue;

			} catch (SQLException e)
			  	{
				e.printStackTrace();
			  	}
			  closeConnection(result);
			  return null;
	}

	public void deleteTrening(int sessionID)
	{
		delete(querys.deleteTrening(sessionID));
	}

   /**
    *
    * sets up, adn executes sql
    * @parm sql
    * @return ResultSet with db values
    */
	public ResultSet enquire(String sql){
		try {
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
	      stmt = conn.createStatement();
	      ResultSet rs = stmt.executeQuery(sql);

	      return rs;

		   }catch(SQLException se){
			   se.printStackTrace();
			   }
		System.out.println("Database error");
		return null;

	}
	/**
	 * sends a add insert querye to db
	 * @param sql
	 * @return
	 */
	public ResultSet insert(String sql){
		try {
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		      ResultSet rs = stmt.getGeneratedKeys();

		      return rs;

			   }catch(SQLException se){
				   se.printStackTrace();
				   }
			System.out.println("Database error");
			return null;
	}
	
	/**
	 * Sends a delete querye to db
	 * @param sql
	 */
	public void delete(String sql){
		try {
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      stmt = conn.createStatement();
		      stmt.executeUpdate(sql);


			}catch(SQLException se){
			   se.printStackTrace();
			}
		try {
		    	stmt.close();
		    	conn.close();
			}catch(Exception se){
				se.printStackTrace();
			}
	}

	/**
	 * Closes connection with db, and closes ResultSet
	 * @param ResultSet db
	 * @return void
	 */
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