import java.util.ArrayList;
import java.util.Scanner;

public class Main {


	private static Scanner inputScanner = null;
	private static String input = null;
	public static void main (String[] args)
	{
		DbView dbView = new DbView();
		inputScanner = new Scanner(System.in);
		ArrayList<ArrayList> result = dbView.getOvelseFraGruppe(1);
		System.out.println(result.get(0).get(0));
		System.out.println(result.get(1).get(0).toString());
		run:
		while(true)
		{
			System.out.println("1: Categories");
			System.out.println("2: Periods");
			System.out.println("3: Statistics");
			System.out.println("4: Exit");

			input = inputScanner.nextLine();

			switch(input)
			{
				case "1":
					categories();
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

	private static void categories()
	{
		System.out.println("Go Categories");
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
