import java.sql.*;
import java.text.ParseException;
import java.util.Scanner;

// This class provides the actions that can be taken from the customer interface
public class CustomerActions
{
	// 1
	public static void AddCustomer(Connection connection)
    {
    	// DB variables
    	Statement statement = null;
    	PreparedStatement prepStatement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{	    		    	
	    	// Prepare the statement
			query = "INSERT into CUSTOMER values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";
			prepStatement = connection.prepareStatement(query);

	    	// User input variables
	        String salutation, fName, lName, ccNum, ccExpireDate, street, city, state, phone, email;

	        // Get user input
	        Scanner in = new Scanner(System.in);
	        System.out.println("\nEnter the following information for the prompted fields:");

	        System.out.println("Salutation (Mr/Mrs/Ms) --");
	        salutation = in.nextLine();

	        System.out.println("First Name --");
	        fName = in.nextLine();

	        System.out.println("Last Name --");
	        lName = in.nextLine();

	        System.out.println("Credit Card Number --");
	        ccNum = in.nextLine();

	        System.out.println("Credit Card Expite Date (dd/mm/yyyy)--");
	        ccExpireDate = in.nextLine();

	        System.out.println("Street --");
	        street = in.nextLine();

	        System.out.println("City --");
	        city = in.nextLine();

	        System.out.println("State --");
	        state = in.nextLine();

	        System.out.println("Phone Number (1234567890) --");
	        phone = in.nextLine();

	        System.out.println("Email --");
	        email = in.nextLine();

	        // Capitalize the first and last name
	        String fNameCap = Capitalize(fName);
	        String lNameCap = Capitalize(lName);

	        // If the same first and last name exist, throw an error and return
	        statement = connection.createStatement();
	        query = "SELECT * from customer where first_name = " + "'" + fNameCap + "'" + " AND last_name = " + "'" + lNameCap + "'";
	        resultSet = statement.executeQuery(query);

	        int numRows = GetNumRows(resultSet);	        

	        if(numRows == -1)
	        {
	        	System.out.println("Could not count the number of rows, aborting");
	        }
	        else if(numRows > 0)
	        {	        
	        	System.out.println("A customer with that first and last name already exists");
	        	return;
	        }

	        // Assign a unique cid
	        String cid;

	        // Select all the customers
	        statement = connection.createStatement();
	        query = "SELECT cid from customer";
	        resultSet = statement.executeQuery(query);

	        // Find the highest cid, make the new cid 1 higher
	        int highest = 0;
	        while(resultSet.next())
	        {
	        	int id = Integer.parseInt(resultSet.getString(1));

	        	if(id > highest)
	        	{
	        		highest = id;
	        	}
	        }

	        highest += 1; // The new cid is 1 higher than the highest
	        cid = highest + "";

	        // Format the date
	        // dd/mm/yyyy
	        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/mm/yyyy");
	        java.sql.Date date_reg = new java.sql.Date (df.parse(ccExpireDate).getTime());

	        // Insert query values
	        prepStatement.setString(1, cid);
	        prepStatement.setString(2, salutation);
	        prepStatement.setString(3, fNameCap);
	        prepStatement.setString(4, lNameCap);
	        prepStatement.setString(5, ccNum);
	        prepStatement.setDate(6, date_reg);
	        prepStatement.setString(7, street);
	        prepStatement.setString(8, city);
	        prepStatement.setString(9, state);
	        prepStatement.setString(10, phone);
	        prepStatement.setString(11, email);

	        // Execute the statement
	        prepStatement.executeUpdate();

	        // Check if it's in there
	        statement = connection.createStatement();
	        query = "SELECT * from customer where cid = 400";
	        resultSet = statement.executeQuery(query);

	        // TODO
	        // Commit the change

	        // Print out the cid
	        System.out.println("Customer Added! Your PittRewards number is " + cid);
		}
		catch(SQLException e)
		{
			System.out.println("Error inserting the customer. Error: " + e.toString());
		}
		catch(ParseException e)
		{
			System.out.println("Error parsing the date. Error: " + e.toString());
		}
		finally
		{
			try
			{
				if(statement != null) statement.close();
				if(prepStatement != null) prepStatement.close();
			}
			catch(SQLException e)
			{
				System.out.println("Cannot close Statement. Error: " + e.toString());
			}
		}
    }

    // 2
    public static void ShowCustomerInfo(Connection connection, String fName, String lName)
    {
		// DB variables
    	Statement statement = null;
    	PreparedStatement prepStatement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{	    	
    		// Capitalize the names
    		String fNameCap = Capitalize(fName);
    		String lNameCap = Capitalize(lName);

    		// Find the customer    	
	    	statement = connection.createStatement();
	        query = "SELECT * from customer where first_name = " + "'" + fNameCap + "'" + " AND last_name = " + "'" + lNameCap + "'";
	        resultSet = statement.executeQuery(query);

	        System.out.println("");

	        // Print out the information
	        while(resultSet.next())
	        {
	        	System.out.println("Cid:\t\t\t" + resultSet.getString(1));
	        	System.out.println("Salutation:\t\t" + resultSet.getString(2));
	        	System.out.println("First Name:\t\t" + resultSet.getString(3));
	        	System.out.println("Last Name:\t\t" + resultSet.getString(4));
	        	System.out.println("Credit Card Cumber:\t" + resultSet.getString(5));
	        	System.out.println("Credit Card Expire Date:" + resultSet.getDate(6).toString());
	        	System.out.println("Street:\t\t\t" + resultSet.getString(7));
	        	System.out.println("City:\t\t\t" + resultSet.getString(8));
	        	System.out.println("State:\t\t\t" + resultSet.getString(9));
	        	System.out.println("Phone:\t\t\t" + resultSet.getString(10));
	        	System.out.println("Email:\t\t\t" + resultSet.getString(11));
	        	System.out.println("Frequent Miles:\t\t" + resultSet.getString(12));
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
				if(prepStatement != null) prepStatement.close();
			}
			catch(SQLException e)
			{
				System.out.println("Cannot close Statement. Error: " + e.toString());
			}
		}
    }

    // 3
    public static void FindPriceOfFlights(Connection connection, String a, String b)
    {
		// DB variables
    	Statement statement = null;
    	PreparedStatement prepStatement = null;
    	ResultSet resultSet;
    	String query;
    
    	try
    	{	    	
    		// Upper case the city name, that's how they are stored in the DB
	    	String cityA = a.toUpperCase();
	    	String cityB = b.toUpperCase();

	    	// To keep track of the results
	    	int lowAtoB = 0;
		    int lowBtoA = 0;
		    int highAtoB = 0;
		    int highBtoA = 0;

	    	// high low prices for one-way from A to B	
			statement = connection.createStatement();			
		    query = "SELECT * from PRICE where departure_city = " + "'" + cityA + "'" + " AND arrival_city = " + "'" + cityB + "'";
		    resultSet = statement.executeQuery(query);		    


		    // Print out the information
		    System.out.println("");
		    System.out.println("High and Low prices for a flight from " + cityA + " to " + cityB + ".");
	        while(resultSet.next())
	        {
	        	lowAtoB = resultSet.getInt(5);
	        	highAtoB = resultSet.getInt(4);

	        	System.out.println("Low Price: " + lowAtoB + "\tHighPrice: " + highAtoB);
	        }

	        // high low prices for one-way from B to A	
			statement = connection.createStatement();			
		    query = "SELECT * from PRICE where departure_city = " + "'" + cityB + "'" + " AND arrival_city = " + "'" + cityA + "'";
		    resultSet = statement.executeQuery(query);		    

		    // Print out the information
		    System.out.println("");
		    System.out.println("High and Low prices for a flight from " + cityB + " to " + cityA + ".");
	        while(resultSet.next())
	        {
	        	lowBtoA = resultSet.getInt(5);
	        	highBtoA = resultSet.getInt(4);

	        	System.out.println("Low Price: " + lowBtoA + "\tHighPrice: " + highBtoA);
	        }

	        // Print the round trip prices
	        System.out.println("");
	        System.out.println("Round Trip prices between city " + cityA + " and city " + cityB + ".");
	        System.out.println("Low Price: " + (lowAtoB + lowBtoA) + "\tHigh Price: " + (highAtoB + highBtoA));
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
				if(prepStatement != null) prepStatement.close();
			}
			catch(SQLException e)
			{
				System.out.println("Cannot close Statement. Error: " + e.toString());
			}
		}
    }

    // Returns the number of tuples in a result set
    private static int GetNumRows(ResultSet resultSet)
    {
    	int count = 0;

    	try
    	{
			while (resultSet.next())
		    {		   
				count++;
		    }

		    return count;
    	}
    	catch(SQLException e)
    	{
    		System.out.println("Error counting the rows. Error: " + e.toString());
    		return -1;
    	}
    }

    // Capitalizes a string
    private static String Capitalize(String s)
    {
    	return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
    }
}