
public class DbQuerys {

	public DbQuerys(){
		
	}
	public String getTrening(int id){
		return "SELECT * FROM pciverse_tdt4145.Trenings�kt WHERE �ktID =" + id;
	}
	public String getResultat(String type){
		return "SELECT * FROM pciverse_tdt4145.Resultat" + type; 
	}
	public String getAlleGrupper(){
		return "SELECT * FROM pciverse_tdt4145.Gruppe;"; 
	}
	public String getSubGrupper(int groupID){
		return "SELECT Navn, AltGruppeID "
				+ "FROM `pciverse_tdt4145`.`Gruppe`,`pciverse_tdt4145`.`Gruppe_Gruppe` "
				+ "WHERE `pciverse_tdt4145`.`Gruppe`.GruppeID = `pciverse_tdt4145`.`Gruppe_Gruppe`.GruppeID "
				+ "AND `pciverse_tdt4145`.`Gruppe`.GruppeID = '"+Integer.toString(groupID)+"';";
	}
	public String getIkkeSubGrupper(){
		return "SELECT * "
				+ "FROM pciverse_tdt4145.Gruppe G "
				+ "WHERE NOT EXISTS "
					+ "(SELECT *"
					+ "FROM pciverse_tdt4145.Gruppe_Gruppe GG "
					+ "WHERE G.GruppeID = GG.AltGruppeID);";
	}
	
	public String getOvelseFraGruppe(int groupID){
		return "SELECT Ovelse.Navn, Ovelse.Beskrivelse "
				+ "FROM `pciverse_tdt4145`.`Gruppe_�velse` Gruppe_Ovelse, "
				+ "`pciverse_tdt4145`.`�velse` Ovelse "
				+ "WHERE Ovelse.�velsesID = Gruppe_Ovelse.�velsesID  "
				+ "AND Gruppe_Ovelse.GruppeID = "+Integer.toString(groupID)+";";
	}
}
