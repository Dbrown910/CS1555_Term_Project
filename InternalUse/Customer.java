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
    
    private static String GetActionInput()
    {
        String input;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter a number to select an action:");
        System.out.println("1 - Add a customer");
        System.out.println("2 - Show customer info");
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