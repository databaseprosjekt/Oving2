import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


	private static Scanner inputScanner = null;
	private static DbView dbView = new DbView();
	public static void main (String[] args)
	{
		inputScanner = new Scanner(System.in);
		run:
		while(true)
		{
			System.out.println("1: Opprett treningsøkt");
			System.out.println("2: Perioder");
			System.out.println("3: Statistikk");
			System.out.println("4: Avslutt");

			String input = inputScanner.nextLine();

			switch(input)
			{
				case "1":
					createSession();
					break;

				case "2":
					System.out.println(periods(-1));
					break;

				case "3":
					statistics();
					break;

				case "4":
					break run;

				default:
					System.out.println("Ugyldig valg, prøv igjen:");
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
			System.out.println("øvelser:");
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

	private static int periods(int periode) {
		ArrayList<ArrayList> periodes = null;
		ArrayList<ArrayList> results = null;


		if (periode < 0) {
			periodes = dbView.getTreningFromPeriode(periode);
			results = dbView.getResultatFromPeriode(periode);
		}

		int index = 1;
		if (periodes.get(0).size() > 0)
		{
			System.out.println("Perioder:");
			for (int i = 0; i < periodes.get(0).size(); i++)
			{
				System.out.println(Integer.toString(index++) + ": " + periodes.get(1).get(i) + " til "
						+ periodes.get(2).get(i) + " har " + periodes.get(3).get(i) + " treninger.");
			}

			System.out.println();
		}
		/*if (results.get(0).size() > 0)
		{
			System.out.println("Resultater:");
			for (int i = 0; i < results.get(0).size(); i++)
			{
				System.out.println(Integer.toString(index++) + ": Treningsøkt" + results.get(1).get(i));
			}
		}*/
		if (index == 1)
		{
			System.out.println("result is empty");
			return -1;
		}
		String input = "";
		int inputIndex = 0;
		boolean invalid = true;
		while (invalid) {
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
		if(inputIndex <= results.get(0).size())
			return periods(inputIndex);
		else
			return (Integer.parseInt((String)periodes.get(0).get(inputIndex-results.get(0).size()-1)));
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
				System.out.println("Ugyldig dato, prøv igjen::");
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
				System.out.println("Ugyldig tid, prøv igjen:");
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
				System.out.println("Ugyldig varighet, prøv igjen:");
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
				System.out.println("Ugyldig form, prøv igjen:");
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
				System.out.println("Ugyldig prestasjon, prøv igjen:");
				invalid = true;
			}
		}

		System.out.println();
		System.out.println("Notat til treningsøkten:");
		String note = inputScanner.nextLine();
		int id = dbView.newTrening(date, time, duration, shape, performance, note);

		System.out.println();
		System.out.println("1. Sett som innendørsøving.");
		System.out.println("2. Sett som utendørsøving.");

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
					System.out.println("Ugyldig valg, prøv igjen:");
					invalid = true;
					break;
			}
		}

		ArrayList<Integer> exercises = new ArrayList<Integer>();

		addExercise: while(true)
		{
			System.out.println();
			System.out.println("1. Legg til øving.");
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
						System.out.println(exercises);
						break;

					case "2":
						break addExercise;

					default:
						System.out.println("Ugyldig valg, prøv igjen:");
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
				System.out.println("Ugyldig tilskuerantall, prøv igjen:");
				invalid = true;
			}
		}
		dbView.setInne(sessionId, ventilation, spectators);
	}

	private static void setOutdoors(int sessionId)
	{
		String input = "";
		System.out.println();
		System.out.println("Beskriv værforhold:");
		String weatherConditions = inputScanner.nextLine();

		System.out.println();
		System.out.println("Beskriv værtype:");
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
				System.out.println("Ugyldig temperatur, prøv igjen:");
				invalid = true;
			}
		}
		dbView.setUte(sessionId, weatherConditions, weatherType, temperature);
	}

	private static void addExercise(int sessionId, ArrayList<Integer> exercises)
	{
		int id = categories(-1, exercises);
		if(exercises.contains(id))
			exercises.remove(new Integer(id));
		else
			exercises.add(id);
	}

	private static void periods()
	{
		System.out.println("Go");
	}


	private static void statistics()
	{

		System.out.println("Total results: " + dbView.getTotaltResults() +".");
		System.out.println("Total workouts: " + dbView.getTotalWorkouts() +".");
		System.out.println("Total exercices: " + dbView.getTotaltExercies() +".");
	}

}
