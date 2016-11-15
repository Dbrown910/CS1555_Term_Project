// 200 users
// 300 reservations
// 10 airlines
// 30 planes
// 100 flights
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.concurrent.ThreadLocalRandom;

public class GenerateData
{
	//----------------------------------------------------------------------//
	//                  Arrays of data that we can pull from              //
	//----------------------------------------------------------------------//

	// The possible airline IDs that are already in the team88-data.sql file
	public static String AIRLINE_IDS[] = {"001", "002", "003", "004", "005", "006", "007", "008", "009", "010"};
	public static String SALUTATIONS[] = {"Mr", "Mrs", "Ms"};
	public static String DATES[] = {"01-JAN-2015 10:00:00", "01-MAR-2015 10:00:00", "01-SEP-2016 10:00:00"};
	public static String NAMES[];
	//----------------------------------------------------------------------//
	//                                 End                                  //
	//----------------------------------------------------------------------//

	public static void main(String[] args)
	{
		try
		{
			// Since we are creating 200 people
			NAMES = new String[200];

			ReadNames();

			File file = new File("team88-data.sql");

			// Create the file if it does not exist
			if(!file.exists())
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			CreateUsers(bw);

			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Creates 200 unique users (called customers in the schema)
	// Ex) INSERT INTO Customers VALUES(1, Mr, John, Doe, 1234567891234567, to_date(), Cherry, Lititz, PA, 7171234567)
	private static void CreateUsers(BufferedWriter bw)
	{		
		try
		{
			for(int i=0; i<200; i++)
			{
				String id = "" + (i+1);
				String salutation = SALUTATIONS[ThreadLocalRandom.current().nextInt(0, 3)]; // Picks a random salutation
				String names[] = NAMES[i].split(" ");
				String fName  = names[0];
				String lName = names[1];
				String ccNumber = GenerateCCNumber();
				String date = "to_date(" + DATES[ThreadLocalRandom.current().nextInt(0, DATES.length)] + ")";
				
				bw.write(id + " " + salutation + " " + fName + " " + lName + " " + ccNumber + " " + date +"\n");
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}				
	}

	private static String GenerateCCNumber()
	{
		String ccNumber = "";

		for(int i=0; i<16; i++)
		{
			ccNumber += "" + ThreadLocalRandom.current().nextInt(0, 10);
		}

		return ccNumber;
	}

	// Reads names from a text file and puts them in an array
	private static void ReadNames()
	{
		File file = new File("names.txt");
		Scanner input;

		try
		{
			input = new Scanner(file);

			for(int i=0; i<200; i++)
			{
				String name = input.nextLine();
				NAMES[i] = name;
			}	
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}		
	}
}