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
        input = GetInput();
        
        if(input.equals("1"))
        {
            CustomerActions.AddCustomer(connection);
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
    
    private static String GetInput()
    {
        String input;
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("Enter a number to select an action:");
        System.out.println("1 - Add a customer");
        input = in.nextLine();

        return input;
    }
}