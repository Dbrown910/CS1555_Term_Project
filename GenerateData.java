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
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateData
{
	//----------------------------------------------------------------------//
	//                  Arrays of data that we can pull from              //
	//----------------------------------------------------------------------//

	// The possible airline IDs that are already in the team88-data.sql file
	public static String AIRLINE_IDS[] = {"001", "002", "003", "004", "005", "006", "007", "008", "009", "010"};
	public static String PLANE_TYPES[] = {"A010", "A020", "A030", "A040", "A050", "A060", "A070", "A080", "A090", "A100", 
											"B010", "B020", "B030", "B040", "B050", "B060", "B070", "B080", "B090", "B100",
											"C010", "C020", "C030", "C040", "C050", "C060", "C070", "C080", "C090", "C100"};
	public static String SALUTATIONS[] = {"Mr", "Mrs", "Ms"};
	public static String DATES[] = {"01-JAN-2015 10:00:00", "01-MAR-2015 10:00:00", "01-SEP-2016 10:00:00"};
	public static String CITIES[] = {"PIT", "JFK", "DCA", "DET", "PHI", "LTZ", "MCH", "CMP"};
	public static String SCHEDULES[] = {"SMTW-F-", "S--WTF-", "-MTWTF-", "--TWTFS", "SMTWT--", "-M-WT--"};
	public static String PT_OID[] = {"A010 001", "A020 001", "A060 002", "B030 005", "B070 006", "C070 009", "C100 010"};
	public static String NAMES[];
	//----------------------------------------------------------------------//
	//                                 End                                  //
	//----------------------------------------------------------------------//

	private static String flights[];
	private static List<String> flightPrices;

	public static void main(String[] args)
	{
		try
		{
			// Since we are creating 200 people
			NAMES = new String[200];

			// So we don't repeat flight pricing info
			flights = new String[100];
			flightPrices = new ArrayList<String>();

			ReadNames();

			File file = new File("team88-data.sql");

			// Create the file if it does not exist
			if(!file.exists())
			{
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			CreateUsers(bw);
			CreateReservations(bw);
			CreateFlights(bw);
			WriteFlightPrices(bw);

			bw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Creates 100 flights
	private static void CreateFlights(BufferedWriter bw)
	{
		try
		{
			for(int i=0; i<100; i++)
			{
				String flightNum = "" + (i+1);
				String aId_Pt[] = PT_OID[ThreadLocalRandom.current().nextInt(0, PT_OID.length)].split(" ");
				String airlineId = aId_Pt[1];		
				String planeType = aId_Pt[0];

				String deptCity = CITIES[ThreadLocalRandom.current().nextInt(0, CITIES.length)];			
				String arrivalCity = "";
				do
				{
					arrivalCity = CITIES[ThreadLocalRandom.current().nextInt(0, CITIES.length)];
				}while(arrivalCity.equals(deptCity));

				String depTime = "1200";
				String arrivalTime = "2200";
				String weekSchedule = SCHEDULES[ThreadLocalRandom.current().nextInt(0, SCHEDULES.length)];
		
				String finalString = "";
				finalString = "INSERT INTO Flight VALUES( '" + flightNum + "', '" + airlineId + "', '" + planeType + "', '" + deptCity + "', '"
				+ arrivalCity + "', '" + depTime + "', '" + arrivalTime + "', '" + weekSchedule + "' );\n";					

				bw.write(finalString);

				CreateFlightPricing(deptCity, arrivalCity);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Creates flight pricing given a path
	private static void CreateFlightPricing(String deptCity, String arrivalCity)
	{
		String key = deptCity + arrivalCity;

		for(int i=0; i<flights.length; i++)
		{
			if(flights[i] == null)
			{
				flights[i] = key;
				break;
			}
			else if(flights[i].equals(key))
			{
				return;
			}
		}

		String airlineId = AIRLINE_IDS[ThreadLocalRandom.current().nextInt(0, AIRLINE_IDS.length)];

		String lowPrice = "" + ThreadLocalRandom.current().nextInt(100, 200); // Low prices are 100-199
		String highPrice = "" + ThreadLocalRandom.current().nextInt(200, 300); // High prices are 200-299

		String finalString = "";
		finalString = "INSERT INTO Price VALUES( '" + deptCity + "', '" + arrivalCity + "', '" + airlineId + "', " + highPrice + ", "
		+ lowPrice + " );\n";	

		flightPrices.add(finalString);
	}

	// Writes the flight prices to file
	private static void WriteFlightPrices(BufferedWriter bw)
	{
		try
		{
			for(int i=0; i<flightPrices.size(); i++)
			{
				if(flightPrices.get(i) != null)
				{
					bw.write(flightPrices.get(i));
				}
				else
				{
					return;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Creates 300 reservations
	private static void CreateReservations(BufferedWriter bw)
	{
		try
		{
			for(int i=0; i<300; i++)
			{
				String reservationNumber = "" + (i+1);
				String cid = "" + ThreadLocalRandom.current().nextInt(1, 201); // Picks a random number between 1 and 200
				String cost = "258";
				String ccNumber = GenerateCCNumber();
				String date = DATES[ThreadLocalRandom.current().nextInt(0, DATES.length)];
				String ticketed = "" + ThreadLocalRandom.current().nextInt(0,2);
				String startCity = CITIES[ThreadLocalRandom.current().nextInt(0, CITIES.length)];			
				String endCity = "";
				do
				{
					endCity = CITIES[ThreadLocalRandom.current().nextInt(0, CITIES.length)];
				}while(endCity.equals(startCity));

				String finalString = "";
				finalString = "INSERT INTO Reservation VALUES( '" + reservationNumber + "', '" + cid + "', " + cost + ", '"
				+ ccNumber + "', to_date('" + date + "', 'DD-MON-YYYY HH24:MI:SS'), '" + ticketed + "', '" + startCity + "', '" + endCity + "' );\n";					

				bw.write(finalString);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	// Creates 200 unique users (called customers in the schema)
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
				String expireDate = DATES[ThreadLocalRandom.current().nextInt(0, DATES.length)];
				String street = "Foo";
				String city = "Lititz";
				String state = "PA";
				String phone = "1234567890";
				String email = "foo@bar.null";
				
				String frequentMile = "NULL";
				// 50% chance of being a frequent mile
				if(ThreadLocalRandom.current().nextInt(0,2) == 1)
				{
					frequentMile = AIRLINE_IDS[ThreadLocalRandom.current().nextInt(0, AIRLINE_IDS.length)];
				}				

				String finalString = "";
				if(frequentMile.equals("NULL"))
				{
					finalString = "INSERT INTO Customer VALUES( '" + id + "', '" + salutation + "', '" + fName + "', '"
					+ lName + "', '" + ccNumber + "', to_date('" + expireDate + "', 'DD-MON-YYYY HH24:MI:SS'), '" + street + "', '" + city + "', '" + state + "', '" + phone 
					+ "', '" + email + "', " + frequentMile + " );\n";
				}
				else
				{
					finalString = "INSERT INTO Customer VALUES( '" + id + "', '" + salutation + "', '" + fName + "', '"
					+ lName + "', '" + ccNumber + "', to_date('" + expireDate + "', 'DD-MON-YYYY HH24:MI:SS'), '" + street + "', '" + city + "', '" + state + "', '" + phone 
					+ "', '" + email + "', '" + frequentMile + "' );\n";
				}
				

				bw.write(finalString);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}				
	}

	// --------------------------------------
	// HELPER FUNCTIONS ---------------------
	// --------------------------------------

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