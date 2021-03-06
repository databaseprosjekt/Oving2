
public class DbQuerys {

	public DbQuerys(){

	}


	public static String getTrening(int id){
		return "SELECT * FROM pciverse_tdt4145.Trenings�kt WHERE �ktID =" + id;
	}
	public static String getResultat(String type){
		return "SELECT * FROM pciverse_tdt4145.Resultat" + type;
	}
	public static String getAlleGrupper(){
		return "SELECT * FROM pciverse_tdt4145.Gruppe;";
	}

	//////////////////////////
	//Querys for trenings�kt//
	//////////////////////////

	public static String getAlleTreninger(){
		return "SELECT * FROM pciverse_tdt4145.Trenings�kt ORDER BY Dato, Tidspunkt;";
	}

	public static String getInne(int sessionID)
	{
		return "SELECT * "
				+ "FROM pciverse_tdt4145.Innend�rsaktivitet I "
				+ "WHERE I.�ktID = " + sessionID + ";";
	}

	public static String getUte(int sessionID)
	{
		return "SELECT * "
				+ "FROM pciverse_tdt4145.Utend�rsaktivitet U "
				+ "WHERE U.�ktID = " + sessionID + ";";
	}

	public static String getOvelseFraTrening(int sessionID)
	{
		return "SELECT �.�velsesID, �.Navn, �.Beskrivelse "
				+ "FROM pciverse_tdt4145.Trenings�kt_�velse T�, pciverse_tdt4145.�velse � "
				+ "WHERE T�.�ktID = " + sessionID + " AND T�.�velseID = �.�velsesID;";
	}

	public static String deleteTrening(int sessionID)
	{
		return "DELETE FROM pciverse_tdt4145.Trenings�kt "
				+ "WHERE �ktID = " + sessionID +";";
	}
	/////////////////////////
	//Querys for categories//
	/////////////////////////

	public static  String getSubGrupper(int groupID){
		return "SELECT G.Navn, GG.AltGruppeID "
				+ "FROM pciverse_tdt4145.Gruppe G, pciverse_tdt4145.Gruppe_Gruppe GG "
				+ "WHERE G.GruppeID = GG.AltGruppeID "
				+ "AND GG.GruppeID = '"+Integer.toString(groupID)+"';";
	}
	public static String getIkkeSubGrupper(){
		return "SELECT * "
				+ "FROM pciverse_tdt4145.Gruppe G "
				+ "WHERE NOT EXISTS "
					+ "(SELECT *"
					+ "FROM pciverse_tdt4145.Gruppe_Gruppe GG "
					+ "WHERE G.GruppeID = GG.AltGruppeID);";
	}

	public static String getOvelseFraGruppe(int groupID){
		return "SELECT Ovelse.Navn, Ovelse.�velsesID "
				+ "FROM `pciverse_tdt4145`.`Gruppe_�velse` Gruppe_Ovelse, "
				+ "`pciverse_tdt4145`.`�velse` Ovelse "
				+ "WHERE Ovelse.�velsesID = Gruppe_Ovelse.�velsesID  "
				+ "AND Gruppe_Ovelse.GruppeID = "+Integer.toString(groupID)+";";
	}
	public static String newTrening(String dato, String tidspunkt, int varighet, int form, int prestasjon, String notat) {
		return "INSERT INTO pciverse_tdt4145.Trenings�kt"
				+ "(Dato, Tidspunkt, Varighet, Form, Prestasjon, Notat) "
				+ "VALUES('"+dato+"', '"+tidspunkt+"', '"+(varighet/60)+":"+(varighet%60)+":00', "+form+", "+prestasjon+", '"+notat+"');";
	}
	public static String newOving(int ovingid, int treningid) {
		return "INSERT INTO pciverse_tdt4145.Trenings�kt_�velse(�ktID, �velseID)"
				+ " values("+Integer.toString(treningid)+", " + Integer.toString(ovingid)+");";
	}
	public static String setInne(int oktid, String ventelasjon, int antallTilskuere){
		return "INSERT INTO pciverse_tdt4145.Innend�rsaktivitet(�ktID, Luftventilasjon, Antall_tilskuere) "
				+ "values("+oktid+",'"+ventelasjon+"',"+antallTilskuere+"); ";
	}
	public static String setUte(int oktid, String verForhold, String verType,  int temp){
		return "INSERT INTO pciverse_tdt4145.Utend�rsaktivitet(�ktID, V�rforhold, v�rtype, Temperatur) "
				+ "values("+oktid+",'"+verForhold+"','"+verType+"',"+temp+");";
	}

	///////////////////////
	//Querys for periodes//
	///////////////////////

	public static String getTreningFromPeriode(int periode) {
		return "SELECT p.PeriodeID, p.FraDato, p.TilDato, COUNT(*)"
				+ " FROM pciverse_tdt4145.Trenings�kt t, pciverse_tdt4145.Periode p "
				+ "WHERE p.FraDato < t.Dato"
				+ " AND p.TilDato > t.Dato;";
	}
	// Todo: knytte datoer til bestemte perioder

	public static String getResultatFromPeriode(int periode) {
		return "SELECT p.PeriodeID, t.�ktID, r.ResultatID, t.Prestasjon, t.Notat"
				+ " FROM pciverse_tdt4145.Periode p, pciverse_tdt4145.Resultat_Periode r_p, pciverse_tdt4145.Resultat r, pciverse_tdt4145.Trenings�kt t "
				+ "WHERE p.PeriodeID = r_p.PeriodeID "
				+ "AND r.ResultatID = r_p.ResultatID "
				+ "AND t.�ktID = r.�ktID;";
}
    public String getWorkoutFromPeriod(int periodeid){
        return "select DISTINCT t.Dato, t.Tidspunkt, t.�ktID, t.Varighet, t.Notat, t.Form, t.Prestasjon  "
                + "FROM pciverse_tdt4145.Trenings�kt t, "
                + "pciverse_tdt4145.Periode p "
                + "WHERE p.PeriodeID = " + periodeid +" "
                + "AND t.Dato between p.Fradato and p.Tildato;";
 
    }
	/////////////////////
	//Querys for stats://
	/////////////////////

	public static String getTotalResaults(){
		return "SELECT COUNT(*) FROM pciverse_tdt4145.Resultat;";
	}
	public String getTotalWorkouts() {
		return "SELECT COUNT(*) FROM pciverse_tdt4145.Trenings�kt;";
	}
	public String getTotaltExercies() {
		return "SELECT COUNT(*) FROM pciverse_tdt4145.�velse;";
	}	
	public String getPeriodes(){
		return "SELECT * FROM pciverse_tdt4145.Periode;";
	}

	public static String getOvelse(int id){
        return "SELECT * FROM pciverse_tdt4145.�velse WHERE �velsesID = " + id + ";";
    }

}
