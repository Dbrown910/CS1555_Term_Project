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

	        // TODO
	        // If the same first and last name exist, throw an error and re-prompt

	        // TODO
	        // Assign a unique cid
	        // Fake it for now
	        String cid = "400";

	        // Format the date
	        // dd/mm/yyyy
	        java.text.SimpleDateFormat df = new java.text.SimpleDateFormat("dd/mm/yyyy");
	        java.sql.Date date_reg = new java.sql.Date (df.parse(ccExpireDate).getTime());

	        // Insert query values
	        prepStatement.setString(1, cid);
	        prepStatement.setString(2, salutation);
	        prepStatement.setString(3, fName);
	        prepStatement.setString(4, lName);
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
}