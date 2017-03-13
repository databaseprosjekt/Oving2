
public class DbQuerys {

	public DbQuerys(){
		
	}
	public static String getTrening(int id){
		return "SELECT * FROM pciverse_tdt4145.Treningsøkt WHERE ØktID =" + id;
	}
	public static String getResultat(String type){
		return "SELECT * FROM pciverse_tdt4145.Resultat" + type; 
	}
	public static String getAlleGrupper(){
		return "SELECT * FROM pciverse_tdt4145.Gruppe;"; 
	}
	public static  String getSubGrupper(int groupID){
		return "SELECT Navn, AltGruppeID "
				+ "FROM `pciverse_tdt4145`.`Gruppe`,`pciverse_tdt4145`.`Gruppe_Gruppe` "
				+ "WHERE `pciverse_tdt4145`.`Gruppe`.GruppeID = `pciverse_tdt4145`.`Gruppe_Gruppe`.GruppeID "
				+ "AND `pciverse_tdt4145`.`Gruppe`.GruppeID = '"+Integer.toString(groupID)+"';";
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
		return "SELECT Ovelse.Navn, Ovelse.ØvelsesID "
				+ "FROM `pciverse_tdt4145`.`Gruppe_Øvelse` Gruppe_Ovelse, "
				+ "`pciverse_tdt4145`.`Øvelse` Ovelse "
				+ "WHERE Ovelse.ØvelsesID = Gruppe_Ovelse.ØvelsesID  "
				+ "AND Gruppe_Ovelse.GruppeID = "+Integer.toString(groupID)+";";
	}
	public static String newTrening(String dato, String tidspunkt, int varighet, int form, int prestasjon, String notat) {
		return "INSERT INTO pciverse_tdt4145.Treningsøkt"
				+ "(Dato, Tidspunkt, Varighet, Form, Prestasjon, Notat) "
				+ "values("+dato+","+tidspunkt+","+varighet+","+form+","+prestasjon+","+notat+");";
	}
}
