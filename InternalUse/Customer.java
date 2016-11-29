import java.sql.*;

import java.util.Scanner;

public class Customer
{
	private static Connection connection;

    private static Statement statement;
    
    private static ResultSet resultSet;
    
	public static void main(String[] args)
	{
		String username = "cjs176";
		String password = "";

		String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

        // Open the connection
		try
		{
			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

			connection = DriverManager.getConnection(url, username, password);
        }
		catch(Exception e)
		{
			System.out.println("Error connecting to the database. Error: ");

			e.printStackTrace();
		}
		
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
        	String cityA = PromptForInput("Enter the first city: ");
        	String cityB = PromptForInput("Enter the second city: ");
        	CustomerActions.FindPriceOfFlights(connection, cityA, cityB);
        }
        else if(input.equals("4"))
        {
        	System.out.println("");
        	String dep = PromptForInput("Enter the departure city: ");
        	String arr = PromptForInput("Enter the arrival city: ");
        	CustomerActions.FindCityRoutes(connection, dep, arr);
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

        // Close the connection
        try
        {
            connection.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Error connecting to database.  Machine Error: " +
                               Ex.toString());
        }
	}  

	// TODO
	public static void Init(Connection connection)
	{
		// same code as main

	}    
    
    private static String GetActionInput()
    {
        String input;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter a number to select an action:");
        System.out.println("1 - Add a customer");
        System.out.println("2 - Show customer info");
        System.out.println("3 - Find price for flights between two cities");
        System.out.println("4 - Find all routes between two cities");
        //////
        System.out.println("9 - Show reservation info");
        System.out.println("10 - Buy ticket from existing reservation");

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