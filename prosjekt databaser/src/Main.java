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
			System.out.println("1: Categories");
			System.out.println("2: Periods");
			System.out.println("3: Statistics");
			System.out.println("4: Exit");

			String input = inputScanner.nextLine();

			switch(input)
			{
				case "1":
					System.out.println(categories(-1));
					break;

				case "2":
					periods();
					break;

				case "3":
					statistics();
					break;

				case "4":
					break run;

				default:
					System.out.println("Invalid input");
			}
			System.out.println();
		}
	}

	private static int categories(int groupID)
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
			System.out.println("Øvelser:");
			for (int i = 0; i < exercises.get(0).size(); i++)
			{
				System.out.println(Integer.toString(index++) + ": " + exercises.get(1).get(i));
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
			return categories(inputIndex);
		else
			return (Integer.parseInt((String)exercises.get(0).get(inputIndex-groups.get(0).size()-1)));
	}

	private static void periods()
	{
		System.out.println("Go Periods");
	}

	private static void statistics()
	{
		System.out.println("Go Statistics");
	}

}
