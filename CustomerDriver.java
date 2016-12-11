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
		//AddCustomer(connection);

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

		System.out.println("\n------------------------------------------");
		System.out.println("---------------- Action 4 ----------------");
		System.out.println("--- Find All Routes Between Two Cities ---");
		System.out.println("------------------------------------------\n");
		FindAllRoutesBetweenTwoCities(connection);

		System.out.println("\n-------------------------------------------------------------");
		System.out.println("------------------------- Action 5 --------------------------");
		System.out.println("--- Find All Routes Between Two Cities of a Given Airline ---");
		System.out.println("-------------------------------------------------------------\n");
		FindAllRoutesBetweenTwoCitiesOfGivenAirline(connection);

		System.out.println("\n---------------------------------------------------------------");
		System.out.println("------------------------- Action 6 ----------------------------");
		System.out.println("--- Find All Routes With Available Seats Between Two Cities ---");
		System.out.println("---------------------------------------------------------------\n");
		FindAllRoutesWithAvailableSeats(connection);

		System.out.println("\n----------------------------------------------------------------------------------");
		System.out.println("------------------------------------ Action 7 -------------------------------------");
		System.out.println("--- Find All Routes With Available Seats Between Two Cities For a Given Airline ---");
		System.out.println("-----------------------------------------------------------------------------------\n");
		FindAllRoutesWithAvailableSeatsComplicated(connection);

		System.out.println("\n-----------------------");
		System.out.println("-------- Action 8 -----");
		System.out.println("--- Add Reservation ---");
		System.out.println("-----------------------\n");
		AddReservation(connection);

		System.out.println("\n------------------------");
		System.out.println("-------- Action 9 ------");
		System.out.println("--- Show Reservation ---");
		System.out.println("------------------------\n");
		ShowReservation(connection);

		System.out.println("\n--------------------------------------------");
		System.out.println("------------------ Action 10 ----------------");
		System.out.println("--- Buy Ticket from Existing Reservation ---");
		System.out.println("--------------------------------------------\n");
		BuyTicket(connection);
	}

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
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{
    		System.out.println("Printing every 30th customer's name");

			// Select all the customers
	        statement = connection.createStatement();
	        query = "SELECT * from Customer";
	        resultSet = statement.executeQuery(query);


	        int count = 0;
	        while(resultSet.next())
	        {
	        	if(count % 30 == 0)
	        	{
	        		System.out.println("Cid: " + resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3) + "\t" + resultSet.getString(4));
	        	}

	        	count ++;
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

	private static void FindPriceForFlightsBetweenTwoCities(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;
    
    	try
    	{	    	
	    	String cityA = "LTZ";
	    	String cityB = "PHI";

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
			}
			catch(SQLException e)
			{
				System.out.println("Cannot close Statement. Error: " + e.toString());
			}
		}
	}

	private static void FindAllRoutesBetweenTwoCities(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

        try
        {
        	String src_city = "LTZ";
        	String dest_city = "PHI";

            statement = connection.createStatement();
            query = "SELECT flight_number, departure_city, departure_time, arrival_time " +
            			   "FROM Flight " +
            			   "WHERE departure_city= '"+src_city+"' " +
            			   "AND arrival_city = '" +dest_city+"'";
            statement.executeQuery(query);    
            resultSet = statement.executeQuery(query);

            System.out.println("Flights from "+src_city+" to "+dest_city);
            System.out.println("Flight No.\tDeparture City\tArrival City\tArrival Time");
			   	
            while (resultSet.next()) 
		    {
		    	System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t"+ resultSet.getString(3) + "\t" + resultSet.getString(4));
		    }
        }
        catch(SQLException Ex)
        {
            System.out.println("Error running the sample queries.  Machine Error: " +
                               Ex.toString());
        }
        finally
        {
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

	private static void FindAllRoutesBetweenTwoCitiesOfGivenAirline(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
        {
        	String src_city = "LTZ";
        	String dest_city = "PHI";
        	String airline_name = "Adlair Aviation";

            statement = connection.createStatement();
            query = "SELECT Flight.airline_id, flight_number, departure_city, departure_time, arrival_time " +
            			   "FROM Flight JOIN Airline ON Flight.airline_id = Airline.airline_id " +
            			   "WHERE departure_city= '"+src_city+"' " +
            			   "AND airline_name= '"+airline_name+"' " +
            			   "AND arrival_city = '" +dest_city+"'";
            statement.executeQuery(query);    
            resultSet = statement.executeQuery(query);

            System.out.println("Flights from "+src_city+" to "+dest_city);
            System.out.println("Airline ID\tFlight No.\tDeparture City\tArrival City\tArrival Time");
			   	
            while (resultSet.next()) 
		    {
		    	System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t"+ resultSet.getString(3) + "\t" + resultSet.getString(4)+ "\t" + resultSet.getString(5));
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

	private static void FindAllRoutesWithAvailableSeats(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
        {
        	String src_city = "LTZ";
        	String dest_city = "PHI";
        	String date = "01/01/2015";

            statement = connection.createStatement();
            query = "SELECT Flight.flight_number, departure_city, departure_time, arrival_time " +
            			   "FROM Flight " + 
            			   "JOIN Reservation_detail ON Flight.flight_number = Reservation_detail.flight_number " +
            			   "JOIN Reservation ON Reservation_detail.reservation_number = Reservation.reservation_number " +
            			   "WHERE departure_city= '"+src_city+"' " +		 
            			   "AND arrival_city = '" +dest_city+"' " +
            			   "AND flight_date = to_date('"+date+"', 'dd/mm/yyyy') ";
            statement.executeQuery(query);    
            resultSet = statement.executeQuery(query);

            System.out.println("Flights from "+src_city+" to "+dest_city);
            System.out.println("Flight No.\tDeparture City\tArrival City\tArrival Time");
			   	
            while (resultSet.next()) 
		    {
		    	System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t"+ resultSet.getString(3) + "\t" + resultSet.getString(4));
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

	private static void FindAllRoutesWithAvailableSeatsComplicated(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

		try
        {
        	String src_city = "LTZ";
        	String dest_city = "PHI";
        	String airline_name = "Adlair Aviation";
        	String date = "01/01/2015";

            statement = connection.createStatement();
            query = "SELECT Flight.airline_id, Flight.flight_number, departure_city, departure_time, arrival_time " +
            			   "FROM Flight " + 
            			   "JOIN Airline ON Airline.airline_id = Flight.airline_id " +
            			   "JOIN Reservation_detail ON Flight.flight_number = Reservation_detail.flight_number " +
            			   "JOIN Reservation ON Reservation_detail.reservation_number = Reservation.reservation_number " +
            			   "WHERE departure_city= '"+src_city+"' " +		 
            			   "AND arrival_city = '" +dest_city+"' " +
            			   "AND airline_name = '" +airline_name+"' " +
            			   "AND flight_date = to_date('"+date+"', 'dd/mm/yyyy') ";
            statement.executeQuery(query);    
            resultSet = statement.executeQuery(query);

            System.out.println("Flights from "+src_city+" to "+dest_city);
            System.out.println("Airline ID\tFlight No.\tDeparture City\tArrival City\tArrival Time");
			   	
            while (resultSet.next()) 
		    {
		    	System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t"+ resultSet.getString(3) + "\t" + resultSet.getString(4)+ "\t" + resultSet.getString(5));
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

	private static void AddReservation(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{	    
    		System.out.println("Adding 50 flights...\n");

	        String flightNum = "1";
	        String deptDate = "03/09/2016"; // max of 4 legs

	        // We now have all the data, make sure there are enough seats
	        boolean enoughSeats = true;

	        for(int i=0; i<50; i++)
	        {
		        int maxCapacity;

	        	// Find the max capacity
	        	statement = connection.createStatement();
	        	query = "SELECT plane_type, airline_id from Flight where flight_number = " + flightNum;
	        	resultSet = statement.executeQuery(query);

	        	resultSet.next();

	        	statement = connection.createStatement();
	        	query = "SELECT plane_capacity from Plane where plane_type = " + "'" + resultSet.getString(1) + "'" + " AND owner_id = " + "'" + resultSet.getString(2) + "'";
	        	resultSet = statement.executeQuery(query);

	        	resultSet.next();

	        	maxCapacity = resultSet.getInt(1);

	        	// Find the current capacity
	        	statement = connection.createStatement();
	        	query = "SELECT COUNT(*) from Reservation_detail where flight_number = " + flightNum;
	        	resultSet = statement.executeQuery(query);

	        	resultSet.next();

	        	int flightCap = resultSet.getInt(1);

	        	// The plane is full
	        	if(flightCap >= maxCapacity)
	        	{
	        		enoughSeats = false;

	        		System.out.println("There are not enough seats on the flight with flight number " + flightNum);
	        		break;
	        	}
		        
		        if(enoughSeats)
		        {
		        	// Assign a unique cid
			        String rNumber;

			        // Select all the customers
			        statement = connection.createStatement();
			        query = "SELECT reservation_number from reservation";
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

			        highest += 1; // The new cid is 1 higher than the highest
			        rNumber = (highest + i) + "";

			        System.out.println("Your reservation has been made. Your unique reservation number is " + rNumber);

			        if(statement != null) statement.close();
		        }
	        }
		}
		catch(SQLException e)
		{
			System.out.println("Error adding the reservation. Error: " + e.toString());
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

	private static void ShowReservation(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{	   
    		String resNumber = "91";

    		// Get the reservation
    		statement = connection.createStatement();
    		query = "SELECT * from Reservation where reservation_number = " + resNumber;
    		resultSet = statement.executeQuery(query);

    		// Reservation info
    		String startCity = "";
    		String endCity = "";    		
	        int legs = 0;
	        String[] flightNumbers = new String[10]; // just hardcode 10

    		int count = 0;
    		while(resultSet.next())
    		{
    			startCity = resultSet.getString(7);
    			endCity = resultSet.getString(8);
    			count ++;
    		}

    		// Exit if it doesn't exist
    		if(count == 0)
    		{
    			System.out.println("The reservation " + resNumber + " does not exist in the database.");
    			return;
    		}

    		// Get all legs of the reservation
    		statement = connection.createStatement();
	        query = "SELECT * from Reservation_detail where reservation_number = " + resNumber;	        
	        resultSet = statement.executeQuery(query);

	        // Get all the leg info
	        while(resultSet.next())
	        {
	        	int leg = resultSet.getInt(4);
	        	flightNumbers[leg] = resultSet.getString(2);

	        	legs ++;	        	
	        }

	        // Print the initial info
	        System.out.println("Reservation " + resNumber + " has " + legs + " connecting flights between " + startCity + " and " + endCity + ".");

	        for(int i=0; i<legs; i++)
	        {
	        	// Get the flight for this leg
	        	statement = connection.createStatement();
	        	query = "SELECT * from Flight where flight_numer = " + flightNumbers[i];	        
	        	resultSet = statement.executeQuery(query);

	        	resultSet.next();

	        	System.out.println("Leg " + i + " is a flight from " + resultSet.getString(4) + " to " + resultSet.getString(5) + ".");
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

	private static void BuyTicket(Connection connection)
	{
		// DB variables
    	Statement statement = null;
    	ResultSet resultSet;
    	String query;

    	try
    	{	   
    		String resNumber = "85";

    		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

    		// Update the reservation
    		statement = connection.createStatement();
    		query = "UPDATE Reservation set ticketed = 'Y' where reservation_number = " + resNumber;
    		int result = statement.executeUpdate(query);

    		// Sleep for 4
    		Thread.sleep(4000);

    		statement = connection.createStatement();
    		query = "SELECT ticketed from Reservation where reservation_number = " + resNumber;
    		resultSet = statement.executeQuery(query);

    		while(resultSet.next())
    		{
    			if(resultSet.getString(1).equals("Y"))
    			{
    				System.out.println("The ticket for reservation " + resNumber + " has been bought!");
    			}
    		}
		}
		catch(SQLException e)
		{
			System.out.println("Error inserting the customer. Error: " + e.toString());
		}
		catch(InterruptedException e)
		{
			System.out.println("Interruption error. Error: " + e.toString());
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