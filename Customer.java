import java.sql.*;

import java.util.Scanner;

public class Customer
{
	public static void Begin(Connection connection)
	{
        String input;
        input = GetActionInput();
        
        if(input.equals("1"))
        {
            CustomerActions.AddCustomer(connection);
        }
        else if(input.equals("2"))
        {
        	System.out.println("");
        	String fName = PromptForInput("Enter the FIRST NAME of the customer you want to see:");
        	String lName = PromptForInput("Enter the LAST NAME of the customer you want to see:");
        	CustomerActions.ShowCustomerInfo(connection, fName, lName);
        }
        else if(input.equals("3"))
        {
        	System.out.println("");
        	String cityA = PromptForInput("Enter the first city: (Three Letter Code)");
        	String cityB = PromptForInput("Enter the second city: (Three Letter Code)");
        	CustomerActions.FindPriceOfFlights(connection, cityA, cityB);
        }
        else if(input.equals("4"))
        {
        	System.out.println("");
        	String dep = PromptForInput("Enter the departure city: (Three Letter Code)");
        	String arr = PromptForInput("Enter the arrival city: (Three Letter Code)");
        	CustomerActions.FindCityRoutes(connection, dep, arr);
        }
        else if(input.equals("5"))
        {
			System.out.println("");
			String dep = PromptForInput("Enter the departure city: (Three Letter Code)");
			String arr = PromptForInput("Enter the destination city: (Three Letter Code)");
			String airlineName = PromptForInput("Enter the airline name:");
			CustomerActions.FindCityRoutesWithAirline(connection, dep, arr, airlineName);

        }
        else if(input.equals("6"))
        {        	
			System.out.println("");	
			String dep = PromptForInput("Enter the departure city: (Three Letter Code)");
			String arr = PromptForInput("Enter the destination city: (Three Letter Code)");
			String date = PromptForInput("Enter the flight date: (dd/mm/yyyy");
			CustomerActions.FindRoutesWithSeats(connection, dep, arr, date);
        }
        else if(input.equals("7"))
        {
        	System.out.println("");
        	String dep = PromptForInput("Enter the departure city: (Three Letter Code)");
			String arr = PromptForInput("Enter the destination city: (Three Letter Code)");
			String date = PromptForInput("Enter the flight date: (dd/mm/yyyy");
			String airlineName = PromptForInput("Enter the airline name:");
			CustomerActions.FindRoutesWithSeatsOnAirline(connection, dep, arr, date, airlineName);
        }
        else if(input.equals("8"))
        {
        	System.out.println("");
        	CustomerActions.AddReservation(connection);
        }
        else if(input.equals("9"))
        {
        	System.out.println("");
        	String resNumber = PromptForInput("Enter the desired reservation number: ");
        	CustomerActions.ShowReservationInfo(connection, resNumber);
        }
        else if(input.equals("10"))
        {
        	System.out.println("");
        	String resNumber = PromptForInput("Enter the desired reservation number: ");
        	CustomerActions.PurchaseTicket(connection, resNumber);
        }

        return;
	}   
    
    private static String GetActionInput()
    {
        String input;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Customer Options");
        System.out.println("================");
        System.out.println("1: Add a customer");
        System.out.println("2: Show customer info");
        System.out.println("3: Find price for flights between two cities");
        System.out.println("4: Find all routes between two cities");
        System.out.println("4: Find all routes between two cities");
		System.out.println("5: Find all routes between two cities for an airline");
		System.out.println("6: Find all routes between two cities with available seats on a date");
		System.out.println("7: Find all routes between two cities with available seats on a date for an airline");
        System.out.println("8: Add reservation");
        System.out.println("9: Show reservation info");
        System.out.println("10: Buy ticket from existing reservation");

        input = in.nextLine();

        return input;
    }

    // Gets a single input from a user given a query string
    private static String PromptForInput(String promptText)
    {
    	String input;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println(promptText);
        input = in.nextLine();

        return input;
    }
}