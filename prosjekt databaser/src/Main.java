import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

import com.mysql.jdbc.StringUtils;

public class Main {
	
	/**
	 * sets up main main menu system
	 */
	private static Scanner inputScanner = null;
	private static DbView dbView = new DbView();
	public static void main (String[] args)
	{
		inputScanner = new Scanner(System.in);
		run:
		while(true)
		{
			System.out.println("1: Opprett trenings�kt");
			System.out.println("2: Se trenings�kter");
			System.out.println("3: Se �velser");
			System.out.println("4: Statistikk");
			System.out.println("5: Avslutt");

			String input = inputScanner.nextLine();

			switch(input)
			{
				case "1":
					createSession();
					break;
					
				case "2":
					viewSessionList();
					break;

				case "3":
					viewExercise(categories(-1));
					break;
					
				case "4":
					statistikk();
					break;
					
				case "5":
					break run;

				default:
					System.out.println("Ugyldig valg, pr�v igjen:");
					break;
			}
			System.out.println();
		}
	}


	/**
	 * Recursively navigate through categories based of user input.
	 * Call with -1 to initiate using groups that are not subgroups in any other groups
	 * @param groupID
	 * @return OvelsesID
	 */
	private static int categories(int groupID)
	{
		return categories(groupID, null);
	}
	
	/**
	 * Allows the user to search based on category
	 * @param groupID
	 * @param includedExercises
	 * @return
	 */
	private static int categories(int groupID, ArrayList<Integer> includedExercises)
	{
		ArrayList<ArrayList> groups = null;
		ArrayList<ArrayList> exercises = null;


		if (groupID < 0)
		{
			groups = dbView.getIkkeSubGrupper();
			exercises = new ArrayList<ArrayList>();
			exercises.add(new ArrayList<Integer>());
			exercises.add(new ArrayList<String>());
		}
		else
		{
			groups = dbView.getsubGruppe(groupID);
			exercises = dbView.getOvelseFraGruppe(groupID);
		}

		int index = 1;
		if (groups.get(0).size() > 0)
		{
			System.out.println("Kategorier:");
			for (int i = 0; i < groups.get(0).size(); i++)
			{
				System.out.println(Integer.toString(index++) + ": " + groups.get(1).get(i));
			}

			System.out.println();
		}
		if (exercises.get(0).size() > 0)
		{
			System.out.println("�velser:");
			for (int i = 0; i < exercises.get(0).size(); i++)
			{
				String prefix = "";
				if (includedExercises != null)
				{
					if (includedExercises.contains(Integer.parseInt((String)exercises.get(0).get(i))))
						prefix = "[X] ";
					else
						prefix = "[ ] ";
				}
				System.out.println(prefix + Integer.toString(index++) + ": " + exercises.get(1).get(i));
			}
		}
		if (index == 1)
		{
			System.out.println("group is empty");
			return -1;
		}
		String input = "";
		int inputIndex = 0;
		boolean invalid = true;
		while (invalid)
		{
			invalid = false;
			try
			{
				input = inputScanner.nextLine();
				inputIndex = Integer.parseInt(input);
				if (inputIndex < 1 || inputIndex >= index)
					throw new Exception();
			}
			catch(Exception e)
			{
				System.out.println("Invalid input");
				invalid = true;
			}
		}
		if(inputIndex <= groups.get(0).size())
			return categories(inputIndex, includedExercises);
		else
			return (Integer.parseInt((String)exercises.get(0).get(inputIndex-groups.get(0).size()-1)));
	}

	private static void createSession()
	{
		String input = "";
		System.out.println();
		System.out.println("Oppgi dato (YYYY-MM-DD):");
		boolean invalid = true;
		while (invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				Date.valueOf(input);
			}catch(Exception e){
				System.out.println("Ugyldig dato, pr�v igjen::");
				invalid = true;
			}
		}
		String date = input;

		System.out.println();
		System.out.println("Oppgi tidspunkt (HH:MM)");
		invalid = true;
		while (invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				Time.valueOf(input+":00");
			}catch(Exception e){
				System.out.println("Ugyldig tid, pr�v igjen:");
				invalid = true;
			}
		}
		String time = input + ":00";

		System.out.println();
		System.out.println("Oppgi varighet (antall minutter)");
		invalid = true;
		int duration = 0;
		while (invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				duration = Integer.parseInt(input);
				if (duration <= 0)
					throw new Exception();
			}catch(Exception e){
				System.out.println("Ugyldig varighet, pr�v igjen:");
				invalid = true;
			}
		}

		System.out.println();
		System.out.println("Oppgi form (skala fra 1 til 10)");
		invalid = true;
		int shape = 0;
		while (invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				shape = Integer.parseInt(input);
				if (shape < 1 || shape > 10)
					throw new Exception();
			}catch(Exception e){
				System.out.println("Ugyldig form, pr�v igjen:");
				invalid = true;
			}
		}

		System.out.println();
		System.out.println("Oppgi prestasjon (skala fra 1 til 10)");
		invalid = true;
		int performance = 0;
		while (invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				performance = Integer.parseInt(input);
				if (performance < 1 || performance > 10)
					throw new Exception();
			}catch(Exception e){
				System.out.println("Ugyldig prestasjon, pr�v igjen:");
				invalid = true;
			}
		}

		System.out.println();
		System.out.println("Notat til trenings�kten:");
		String note = inputScanner.nextLine();
		int id = dbView.newTrening(date, time, duration, shape, performance, note);

		System.out.println();
		System.out.println("1. Sett som innend�rs�ving.");
		System.out.println("2. Sett som utend�rs�ving.");

		invalid = true;
		while(invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			switch(input)
			{
				case "1":
					setIndoors(id);
					break;

				case "2":
					setOutdoors(id);
					break;

				default:
					System.out.println("Ugyldig valg, pr�v igjen:");
					invalid = true;
					break;
			} 
		}

		ArrayList<Integer> exercises = new ArrayList<Integer>();

		addExercise: while(true)
		{
			System.out.println();
			System.out.println("1. Legg til �ving.");
			System.out.println("2. Ferdig.");

			invalid = true;
			while(invalid)
			{
				invalid = false;
				input = inputScanner.nextLine();
				switch(input)
				{
					case "1":
						addExercise(id, exercises);
						break;

					case "2":
						break addExercise;

					default:
						System.out.println("Ugyldig valg, pr�v igjen:");
						invalid = true;
						break;
				}
			}
		}

		for (int i = 0; i < exercises.size(); i++)
		{
			dbView.newOving(exercises.get(i), id);
		}
		System.out.println();
	}
	/**
	 * setts workout to indoor
	 * @param sessionId
	 */
	private static void setIndoors(int sessionId)
	{
		String input = "";
		System.out.println();
		System.out.println("Beskriv ventilasjonsforhold:");
		String ventilation = inputScanner.nextLine();

		System.out.println();
		System.out.println("Antall tilskuere:");
		int spectators = -1;
		boolean invalid = true;
		while(invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				spectators = Integer.parseInt(input);
				if (spectators < 0)
					throw new Exception();
			}catch(Exception e){
				System.out.println("Ugyldig tilskuerantall, pr�v igjen:");
				invalid = true;
			}
		}
		dbView.setInne(sessionId, ventilation, spectators);
	}
	/**
	 * sets workout to outdoor
	 * @param sessionId
	 */
	private static void setOutdoors(int sessionId)
	{
		String input = "";
		System.out.println();
		System.out.println("Beskriv v�rforhold:");
		String weatherConditions = inputScanner.nextLine();

		System.out.println();
		System.out.println("Beskriv v�rtype:");
		String weatherType = inputScanner.nextLine();

		System.out.println();
		System.out.println("Temperatur:");
		int temperature = 0;
		boolean invalid = true;
		while(invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				temperature = Integer.parseInt(input);
			}catch(Exception e){
				System.out.println("Ugyldig temperatur, pr�v igjen:");
				invalid = true;
			}
		}
		dbView.setUte(sessionId, weatherConditions, weatherType, temperature);
	}
	//*
	
	private static void addExercise(int sessionId, ArrayList<Integer> exercises)
	{
		int id = categories(-1, exercises);
		if(exercises.contains(id))
			exercises.remove(new Integer(id));
		else
			exercises.add(id);
	}
	
	/**
	 * Enables the user to choise how he/she want to see the workout list, from period or all
	 */
	private static void viewSessionList()
	
	{
		run:
		while(true)
		{
			System.out.println("1: Se alle");
			System.out.println("2: Se etter periode");
			System.out.println("3: Tilbake");

			String input = inputScanner.nextLine();

			switch(input)
			{
				case "1":
					ArrayList<ArrayList> sessions = dbView.getAlleTreninger();
					for (int i = 1; i <= sessions.get(0).size(); i++)
					{
						System.out.println(i + ": " + sessions.get(1).get(i-1).toString() + ", " + sessions.get(2).get(i-1).toString());
					}
					selectWorkout(sessions);
					break;
					
				case "2":
					periods();
					break;

				case "3":
					break run;

				default:
					System.out.println("Ugyldig valg, pr�v igjen:");
					break;
			}
			System.out.println();
		}
	}
	
	/**
	 * select a given workout
	 * @param sessions
	 */
	public static void selectWorkout(ArrayList<ArrayList> sessions) {
		String input = "";
		int index = -1;
		boolean invalid = true;
		while (invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			try{
				index = Integer.parseInt(input)-1;
				if (index < 0 || index >= sessions.get(0).size())
					throw new Exception();
			}catch(Exception e){
				System.out.println("Ugyldig valg, pr�v igjen");
				invalid = true;
			}
		}
		int sessionID = (Integer)sessions.get(0).get(index);
		Date date = (Date)sessions.get(1).get(index);
		Time time = (Time)sessions.get(2).get(index);
		Time duration = (Time)sessions.get(3).get(index);
		int shape = (Integer)sessions.get(4).get(index);
		int performance = (Integer)sessions.get(5).get(index);
		String note = (String)sessions.get(6).get(index);
		viewSession(sessionID, date, time, duration, shape, performance, note);
	}
	
	/**
	 * show, edit and delete workout
	 * @param sessionID
	 * @param date
	 * @param time
	 * @param duration
	 * @param shape
	 * @param performance
	 * @param note
	 */
	private static void viewSession(int sessionID, Date date, Time time, Time duration, int shape, int performance, String note)
	{
		System.out.println();
		System.out.println("Dato: " + date);
		System.out.println("Tidspunkt: " + time);
		System.out.println("Varighet: " + duration);
		System.out.println("Form: " + shape);
		System.out.println("Prestasjon: " + performance);
		System.out.println("Notat: " + note);
		System.out.println();

		ArrayList<Object> inside = dbView.getInne(sessionID);
		if (inside.size() > 0)
		{
			System.out.println("Innend�rstrening:");
			System.out.println("Luftventilasjon: " + (String)inside.get(0));
			System.out.println("Antall tilskuere: " + (Integer)inside.get(1));
		}
		else
		{
			ArrayList<Object> outside = dbView.getUte(sessionID);
			if (outside.size() > 0)
			{
				System.out.println("Utend�rstrening:");
				System.out.println("V�rforhold: " + (String)outside.get(0));
				System.out.println("V�rtype: " + (String)outside.get(1));
				System.out.println("Temperatur: " + (Integer)outside.get(2));
			}
		}

		System.out.println();
		System.out.println("�velser:");

		ArrayList<ArrayList> exercises = dbView.getOvelseFraTrening(sessionID);

		for (int i = 0; i < exercises.get(0).size(); i++)
		{
			System.out.println();
			System.out.println("Navn: " + (String)exercises.get(1).get(i));
			System.out.println("Beskrivelse: " + (String)exercises.get(2).get(i));
		}
		
		System.out.println();
		System.out.println("1: Slett trenings�kt");
		System.out.println("2: Ferdig");

		String input = "";
		boolean invalid = true;
		while(invalid)
		{
			invalid = false;
			input = inputScanner.nextLine();
			switch(input)
			{
				case "1":
					dbView.deleteTrening(sessionID);

				case "2":
					break;

				default:
					System.out.println("Ugyldig valg, pr�v igjen:");
					invalid = true;
					break;
			}
		}
	}
	/**
	 * shows basic statistics
	 */
	private static void statistikk()
	{
		System.out.println("Total results: " + dbView.getTotaltResults() +".");
		System.out.println("Total workouts: " + dbView.getTotalWorkouts() +".");
		System.out.println("Total exercices: " + dbView.getTotaltExercies() +".");
		System.out.println("");
		
		run:
		while(true)
		{
			System.out.println("1. Tilbake");
			String input = inputScanner.nextLine();

			switch(input)
			{
				case "1":
					break run;

				default:
					System.out.println("Invalid input");
			}
		}
	}
	
	/**
	 * prints workout for a given period
	 */
	private static void periods() {
		System.out.println("Nr\tFraDato \tTilDato \n----------------------------------------");
		ArrayList<ArrayList> periodes = dbView.getPeriodes();
		
		for(int i = 0;i<periodes.get(0).size();i++){
			System.out.print(i+1+"\t");
			System.out.print(periodes.get(1).get(i)+"\t");
			System.out.print(periodes.get(2).get(i)+"\t\n");
			}
		System.out.println("");
		
		String input = inputScanner.nextLine();
		if(StringUtils.isStrictlyNumeric(input) && Integer.parseInt(input)>0 && Integer.parseInt(input)-1<periodes.get(0).size()){
			int index = (int) periodes.get(0).get(Integer.parseInt(input)-1);
			System.out.println("Nr\tDato \t \tTid \n----------------------------------------");
			ArrayList<ArrayList> workout = dbView.getWorkoutForPeriod(index);
			
			for(int i = 0;i<workout.get(0).size();i++){
				System.out.print(i+1 + "\t");
				System.out.print(workout.get(1).get(i)+"\t");
				System.out.print(workout.get(2).get(i)+"\t\n");
				}
			System.out.println("");
			selectWorkout(workout);
		}
	}
		
	/**
	 * Show exercise window
	 * @param exerciseID
	 */
	private static void viewExercise(int exerciseID)
	    {
        ArrayList<Object> exercise = dbView.getOvelse(exerciseID);
        System.out.println();
        System.out.println("Navn: " + (String)exercise.get(1));
        System.out.println("Beskrivelse: " + (String)exercise.get(2));
        System.out.println();
        System.out.println("Enter for � fortsette");
        inputScanner.nextLine();
	    }
}
