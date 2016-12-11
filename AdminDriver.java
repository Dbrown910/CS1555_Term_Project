import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class AdminDriver
{
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

		Begin(dbcon);
	}

	public static void Begin(Connection connection) throws IOException
	{
		System.out.println("--------------------");
		System.out.println("----- Action 2 -----");
		System.out.println("--- Load Airline Information ---");
		System.out.println("--------------------\n");
		LoadAirline(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 3 -----");
		System.out.println("--- Load Schedule Information ---");
		System.out.println("--------------------\n");
		LoadSchedule(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 4 -----");
		System.out.println("--- Load Pricing Information ---");
		System.out.println("--------------------\n");
		LoadPricing(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 5 -----");
		System.out.println("--- Load Plane Information ---");
		System.out.println("--------------------\n");
		LoadPlane(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 6 -----");
		System.out.println("--- Generate Passenger Manifest---");
		System.out.println("--------------------\n");
		GenerateManifest(connection);

		System.out.println("--------------------");
		System.out.println("----- Action 1 -----");
		System.out.println("--- Erase the database ---");
		System.out.println("--------------------\n");
		EraseDB(connection);
	}

	private static void EraseDB(Connection connection) 
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	
    	System.out.println("Eraasing the Database");

    	try
    	{
    		String[] table_names = {"Airline", "Plane", "Flight", "Price", "Customer", "Reservation", "Reservation_detail", "System_time"};
	        statement = connection.createStatement();
	        
	        //clear and repopulate the database 20 times
	        for(int i = 0; i < 20; i++)
	        {
	        	for(String s : table_names)
			 		statement.executeUpdate("DELETE FROM "+s);

			 	statement.executeQuery("INSERT INTO Airline VALUES('001', 'Adlair Aviation', 'AA', 1978)");
			 	statement.executeQuery("INSERT INTO Plane VALUES('A010', 'Airabon', 100, to_date('01-JAN-2015 10:00:00', 'DD-MON-YYYY HH24:MI:SS'), 2015, '001')");	
			 	statement.executeQuery("INSERT INTO Flight VALUES( '1', '001', 'A010', 'PHI', 'LTZ', '1200', '2200', '-M-WT--' )");
			 	statement.executeQuery("INSERT INTO Price VALUES( 'CMP', 'PHI', '001', 258, 177 )");
			 	statement.executeQuery("INSERT INTO Customer VALUES( '1', 'Mrs', 'Fletcher', 'Zahl', '0221724026632400', to_date('01/01/2015', 'dd/mm/yyyy'), 'Foo', 'Lititz', 'PA', '1234567890', 'foo@bar.null', '001' )");	 	
			 	statement.executeQuery("INSERT INTO Reservation VALUES( '1', '1', 258, '0221724026632400', to_date('01/09/2016', 'dd/mm/yyyy'), '0', 'PHI', 'DET' )"); 	
			 	statement.executeQuery("INSERT INTO Reservation_detail VALUES( '1', '1', to_date('03/09/2016', 'mm/dd/yyyy'), 1)");
			 	statement.executeQuery("INSERT INTO System_time VALUES(to_date('01-JAN-2015 10:00:00', 'DD-MON-YYYY HH24:MI:SS'))");

			 	if(i % 5 == 0)
			 		System.out.println("Erased succesfully "+i+" times");
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

	private static void LoadAirline(Connection connection) throws IOException
	{
		Scanner fr = null;
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	String file_line;

		fr = new Scanner(new File("input2.txt").getAbsoluteFile());
		System.out.println("Testing input file 2");

		while(fr.hasNextLine())
		{
			//read in, and split on the commas
			file_line = fr.nextLine();

			String[] tokens = file_line.split(",");

			try
	        {
		        statement = connection.createStatement();
		        query = "INSERT INTO Airline VALUES ('"+tokens[0]+"', '"+tokens[1]+"', '"+tokens[2]+"', '"+Integer.parseInt(tokens[4])+"')";
		        statement.executeQuery(query);
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

	    System.out.println("Input file 2 successfully inserted");
	}

	private static void LoadSchedule(Connection connection) throws IOException
	{
		Scanner fr = null;
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	String file_line;

    	fr = new Scanner(new File("input3.txt").getAbsoluteFile());
    	System.out.println("Testing input file 3");

		//for each line of the file
		while(fr.hasNextLine())
		{
			//read in, and split on the spaces
			file_line = fr.nextLine();
			
			String[] tokens = file_line.split(",");

			//build the insert query with the data and execute it
			try
	        {
	            statement = connection.createStatement();
	            query = "INSERT INTO Flight VALUES ('"+tokens[0]+"', '"+tokens[1]+"', '"+tokens[2]+"', '"+tokens[3]+"', '"+tokens[4]+"', '"+
	            													tokens[5]+"', '"+tokens[6]+"', '"+tokens[7]+"')";  
				statement.executeQuery(query);
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

	    System.out.println("Input file 3 successfully inserted");	
	}

	private static void LoadPricing(Connection connection) throws IOException
	{
		Scanner fr = null;
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	String file_line;

		fr = new Scanner(new File("input4.txt").getAbsoluteFile());
		System.out.println("Testing input file 4\n");

		//for each line of the file
		while(fr.hasNextLine())
		{
			//read in, and split on the spaces
			file_line = fr.nextLine();
			
			String[] tokens = file_line.split(",");
			int temp1, temp2;
			temp1 = Integer.parseInt(tokens[3]);
			temp2 = Integer.parseInt(tokens[4]);

	    	try
	    	{
		        statement = connection.createStatement();
		        query = "INSERT INTO Price VALUES ('"+tokens[0]+"', '"+tokens[1]+"', '"+tokens[2]+"', '"+temp1+"', '"+temp2+"')";
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

		System.out.println("Input file 4 successfully inserted");
		
		try
    	{
    		System.out.println("Testing inserts by the user");

	        statement = connection.createStatement();
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'AZC', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'AWD', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'AEE', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'AEB', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'ARN', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'ATM', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'AYV', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'AOA', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('AAM', 'APS', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'AZC', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'AWD', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'AEE', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'AEB', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'ARN', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'ATM', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'AYV', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'AOA', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'AZC', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'AWD', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'AEE', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'AEB', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'ARN', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'ATM', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'AYV', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'AOA', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ACM', 'APS', '003', '200', '100')");
	        statement.executeQuery("INSERT INTO Price VALUES('ABM', 'APS', '003', '200', '100')");
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

		System.out.println("User input successful inserted");
	}

	private static void LoadPlane(Connection connection) throws IOException
	{
		Scanner fr = null; 
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	String file_line = "";

		fr = new Scanner(new File("input5.txt").getAbsoluteFile());
		System.out.println("Testing input file 5");

		//for each line of the file
		while(fr.hasNextLine())
		{
			//read in, and split on the spaces
			file_line = fr.nextLine();
			
			String[] tokens = file_line.split(",");
			int temp1, temp2;
			temp1 = Integer.parseInt(tokens[2]);
			temp2 = Integer.parseInt(tokens[4]);

	    	try
	    	{
		        statement = connection.createStatement();
		        query = "INSERT INTO Plane VALUES ('"+tokens[0]+"', '"+tokens[1]+"', '"+temp1+"', to_date('"+tokens[3]+"', 'DD-MON-YYYY HH24:MI:SS'), '"+
		            													temp2+"', '"+tokens[5]+"')";
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

		System.out.println("Input file 5 successfully inserted");
	}

	private static void GenerateManifest(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    	String[] dates = {"03/09/2016","12/16/2015","02/07/2016","04/10/2016","05/08/2016"};
    	String[] flight_nums = {"92","93","94","95","96"};

    	System.out.println("Producing 25 flight manifests");

    	for(String date : dates)
    	{
    		for(String flight_num : flight_nums)
    		{
		    	try
		    	{
			        statement = connection.createStatement();
			         query = "SELECT salutation, first_name, last_name " +
				            			   "FROM allReservations " +
				            			   "WHERE flight_date = to_date('"+date+"', 'MM/DD/YYYY') " +
				            			   "AND flight_number = '" +flight_num+"'";
		            statement.executeQuery(query);    
		            resultSet = statement.executeQuery(query);

		            System.out.println("Flight "+flight_num+" Passenger List for "+date);
				    System.out.println("============================================================");
					   	
		            while (resultSet.next()) 
				    {
				    	System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
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
		}
	}
}