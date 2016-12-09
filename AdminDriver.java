import java.sql.*;

import java.util.Scanner;

public class AdminDriver
{

	public static void Begin(Connection connection)
	{
		System.out.println("--------------------");
		System.out.println("----- Action 1 -----");
		System.out.println("--- Erase the database ---");
		System.out.println("--------------------\n");
		EraseDB(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 2 -----");
		System.out.println("--- Load Airline Information ---");
		System.out.println("--------------------\n");
		LoadAirline(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 3 -----");
		System.out.println("--- Load Airline Information ---");
		System.out.println("--------------------\n");
		LoadSchedule(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 4 -----");
		System.out.println("--- Load Airline Information ---");
		System.out.println("--------------------\n");
		LoadPricing(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 5 -----");
		System.out.println("--- Load Airline Information ---");
		System.out.println("--------------------\n");
		LoadPlane(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 6 -----");
		System.out.println("--- Load Airline Information ---");
		System.out.println("--------------------\n");
		GenerateManifest(connection);
	}

	private static void EraseDB(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	
    	try
    	{
    		String[] table_names = {"Airline", "Plane", "Flight", "Price", "Customer", "Reservation", "Reservation_detail", "System_time"};
	        statement = connection.createStatement();
	        
	        //clear and repopulate the database 20 times
	        for(int i = 0; i < 20, i++)
	        {
	        	for(String s : table_names)
			 		statement.executeUpdate("DELETE FROM "+s);

			 	statement.executeQuery("INSERT INTO Airline VALUES('001', 'Adlair Aviation', 'AA', 1978)");
			 	statement.executeQuery("INSERT INTO Plane VALUES('A010', 'Airabon', 100, to_date('01-JAN-2015 10:00:00', 'DD-MON-YYYY HH24:MI:SS'), 2015, '001')");	
			 	statement.executeQuery("INSERT INTO Flight VALUES( '1', '001', 'A010', 'PHI', 'LTZ', '1200', '2200', '-M-WT--' )");
			 	statement.executeQuery("INSERT INTO Price VALUES( 'CMP', 'PHI', '001', 258, 177 )");
			 	statement.executeQuery("INSERT INTO Customer VALUES( '1', 'Mrs', 'Fletcher', 'Zahl', '0925960155412105', to_date('01/01/2015', 'dd/mm/yyyy'), 'Foo', 'Lititz', 'PA', '1234567890', 'foo@bar.null', '001' )");	 	
			 	statement.executeQuery("INSERT INTO Reservation VALUES( '1', '90', 258, '0221724026632400', to_date('01/09/2016', 'dd/mm/yyyy'), '0', 'PHI', 'DET' )"); 	
			 	statement.executeQuery("INSERT INTO Reservation_detail VALUES( '1', '1', to_date('03/09/2016', 'mm/dd/yyyy'), 1)");
			 	statement.executeQuery("INSERT INTO System_time VALUES(to_date('01-JAN-2015 10:00:00', 'DD-MON-YYYY HH24:MI:SS'))");
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

	private static void LoadAirline(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
	        statement = connection.createStatement();
	        query = "";
	        resultSet = statement.executeQuery(query);
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

	private static void LoadSchedule(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
	        statement = connection.createStatement();
	        query = "";
	        resultSet = statement.executeQuery(query);
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

	private static void LoadPricing(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
	        statement = connection.createStatement();
	        query = "";
	        resultSet = statement.executeQuery(query);
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

	private static void LoadPlane(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
	        statement = connection.createStatement();
	        query = "";
	        resultSet = statement.executeQuery(query);
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

	private static void GenerateManifest(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
	        statement = connection.createStatement();
	        query = "";
	        resultSet = statement.executeQuery(query);
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
}