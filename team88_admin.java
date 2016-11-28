import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class team88_admin
{
	static Scanner scan = new Scanner(System.in);
	static int selection;
	private static Connection dbcon;
	private static Statement statement;
	private static ResultSet resultSet;
	private static String username = "dmb147";
	private static String password = "3906832";
	private static String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

	public static void main(String args[]) throws IOException
	{	
		 // Open the connection
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			dbcon = DriverManager.getConnection(url, username, password);
        }
		catch(Exception e)
		{
			System.out.println("Error connecting to the database. Error: ");

			e.printStackTrace();
		}

		System.out.println("Which interface would you like to load?");
		System.out.println("1: Admin");
		System.out.println("2: Customer");

		selection = scan.nextInt();

		// Admin
		if(selection == 1)
		{
			System.out.println("Administrator Options");
			System.out.println("=====================");
			System.out.println("1: Erase the database");
			System.out.println("2: Load airline information");
			System.out.println("3: Load schedule information");
			System.out.println("4: Load pricing information");
			System.out.println("5: Load plane information");
			System.out.println("6: Generate passenger manifest");

			selection = scan.nextInt();

			if(selection == 1)
			{
				String[] table_names = {"Airline", "Plane", "Flight", "Price", "Customer", "Reservation", "Reservation_detail", "System_time"};

				System.out.println("Do you really want to erase the database? (y/n)");

				String response = scan.nextLine();

				if(response.equals("y"))
				{
					try
			        {
			            statement = dbcon.createStatement();
			            String query = "";

			            for(String s : table_names)
			            {
			            	query = "DELETE  FROM "+s;    
			               	resultSet = statement.executeQuery(query);
			            }
			        }
			        catch(SQLException Ex)
			        {
			            System.out.println("Error running the sample queries.  Machine Error: " +
			                               Ex.toString());
			        }
			        finally
			        {
			            // CLose the statement
			            try
			            {
			                if (statement != null) statement.close();
			            }
			            catch (SQLException e)
			            {
			                System.out.println("Cannot close Statement. Machine error: "+e.toString());
			            }
			        }
				}				
			}
			else if(selection == 2)
			{

			}
			else if(selection == 3)
			{
				
			}
			else if(selection == 4)
			{
				
			}
			else if(selection == 5)
			{
				
			}
			else if(selection == 6)
			{
				
			}
		}
		// Customer
		else if(selection == 2)
		{

		}

		try
        {
            dbcon.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Error connecting to database.  Machine Error: " +
                               Ex.toString());
        }
	}
}