import java.sql.*;
import java.lang.InterruptedException;
import java.text.ParseException;
import java.util.Scanner;

public class CustomerDriver
{

	public static void Begin(Connection connection)
	{
		System.out.println("--------------------");
		System.out.println("----- Action 1 -----");
		System.out.println("--- Add Customer ---");
		System.out.println("--------------------\n");
		AddCustomer(connection);

		System.out.println("\n--------------------------");
		System.out.println("-------- Action 2 --------");
		System.out.println("--- Show Customer Info ---");
		System.out.println("--------------------------\n");
		ShowCustomerInfo(connection);

		System.out.println("\n-------------------------------------------------");
		System.out.println("-------------------- Action 3 -------------------");
		System.out.println("--- Find Price for Flights Between Two Cities ---");
		System.out.println("-------------------------------------------------\n");
		FindPriceForFlightsBetweenTwoCities(connection);
	}

	// Add 200 customers
	// print every 20th customer
	private static void AddCustomer(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
	    	/* FINDING THE HIGHEST CID ALREADY IN THE DATABASE */
			// Select all the customers
	        statement = connection.createStatement();
	        query = "SELECT cid from Customer";
	        resultSet = statement.executeQuery(query);

	        // Find the highest id, make the new id 1 higher
	        int highest = 0;
	        while(resultSet.next())
	        {
	        	int id = Integer.parseInt(resultSet.getString(1));

	        	if(id > highest)
	        	{
	        		highest = id;
	        	}
	        }

	        highest += 1; // The first new cid is 1 higher than the highest
	        /****************************************************/

	        // The starting point for the new CIDs
	        int cid = highest;

	        System.out.println("Found highest cid of " + cid);

	        for(int i=0; i<200; i++)
	        {
	        	if(i % 20 == 0)
	        	{
	        		System.out.println("Inserting customer " + i + " into the database");
	        	}

	        	statement = connection.createStatement();
	            query = "INSERT INTO Customer VALUES ('" + cid + "', 'Mrs', 'Fletcher', 'Zahl', '0925960155412105', to_date('01/01/2015', 'dd/mm/yyyy'), 'Foo', 'Lititz', 'PA', '1234567890', 'foo@bar.null', '005' )";
	            statement.executeQuery(query); 

	        	cid++;
	        }
    	}
		catch(SQLException e)
		{
			System.out.println("Error inserting the customer. Error: " + e.toString());
		}
		finally
		{
			try
			{
				if(statement != null) statement.close();
			}
			catch(SQLException e)
			{
				System.out.println("Cannot close Statement. Error: " + e.toString());
			}
		}
	}

	private static void ShowCustomerInfo(Connection connection)
	{

	}

	private static void FindPriceForFlightsBetweenTwoCities(Connection connection)
	{

	}
}