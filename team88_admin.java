import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class team88_admin
{
	static Scanner scan = new Scanner(System.in);
	static Scanner fr = null;
	
	private static Connection dbcon;
	private static Statement statement;
	private static PreparedStatement statement2;
	private static ResultSet resultSet;
	private static String username = "dmb147";
	private static String password = "3906832";
	private static String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
	private static int selection;

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
			System.out.println("4: Edit or Load pricing information");
			System.out.println("5: Load plane information");
			System.out.println("6: Generate passenger manifest");

			selection = scan.nextInt();

			if(selection == 1)
			{
				System.out.println("Do you really want to erase the database? (y/n):");

				String response = scan.nextLine();

				if(response.equals("y"))
				{

					String[] table_names = {"Airline", "Plane", "Flight", "Price", "Customer", "Reservation", "Reservation_detail", "System_time"};

					try
			        {
			            statement2 = dbcon.prepareStatement("DELETE FROM ?");

			            for(String s : table_names)
			            {
			            	statement2.setString(1,s); 
			            	statement2.executeQuery();
			               	//resultSet = statement.executeQuery(query);
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
				String file_line = "";
				String id = "";
				String name = "";
				String abbrev = "";
				Integer year_founded = null;

				//read in path of the file 
				System.out.println("Enter the path of the file name:");
				String file_path = scan.nextLine();
				fr = new Scanner(new FileReader(file_path));

				//for each line of the file
				while(fr.hasNext())
				{
					//read in, and split on the spaces
					file_line = fr.next();
					
					String[] tokens = file_line.split(" ");
					StringBuilder sb = new StringBuilder(50);

					//cycles through the tokens of the input as given in example from Milestone 2, Task #2. Necessary to handle varying number of words in title
					int i = 0;
					id = tokens[i];
					i++;

					//checking for a lowercase last letter for all the words of the title
					while(Character.isLowerCase(tokens[i].charAt(tokens.length - 1)))
					{
						sb.append(tokens[i]);
						i++;
					}

					name = sb.toString();
					i++;

					abbrev = tokens[i];

					year_founded = new Integer(tokens[tokens.length - 1]);

					//build the insert query with the data and execute it
					try
			        {
			            statement = dbcon.createStatement();
			            String query = "INSERT INTO Airline VALUES ('"+id+"', '"+name+"', '"+abbrev+"', '"+year_founded.intValue()+")";
			            statement.executeQuery(query);    
			            //resultSet = statement.executeQuery(query);
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
			else if(selection == 3)
			{
				String file_line = "";
				String flight_num = "";
				String airline_id = "";
				String plane_type = "";
				String src_city = "";
				String dest_city = "";
				String src_time = "";
				String dest_time = "";
				String weekly_schedule = "";

				//read in path of the file 
				System.out.println("Enter the path of the file name:");
				String file_path = scan.nextLine();
				fr = new Scanner(new FileReader(file_path));

				//for each line of the file
				while(fr.hasNext())
				{
					//read in, and split on the spaces
					file_line = fr.next();
					
					String[] tokens = file_line.split(" ");

					//asign tokens to their respective variables
					flight_num = tokens[0];
					airline_id = tokens[1];
					plane_type = tokens[2];
					src_city= tokens[3];
					dest_city= tokens[4];
					src_time = tokens[5];
					dest_time = tokens[6];
					weekly_schedule = tokens[7];

					//build the insert query with the data and execute it
					try
			        {
			            statement = dbcon.createStatement();
			            String query = "INSERT INTO Flight VALUES ('"+flight_num+"', '"+airline_id+"', '"+plane_type+"', '"+src_city+"', '"+dest_city+"', '"+
			            													src_time+"', '"+dest_time+"', '"+weekly_schedule+")";  
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
			}
			else if(selection == 4)
			{
				System.out.println("Do you want to load new pricing data, or change existing prices? (L/C)");

				String response = scan.nextLine();

				//if the user prompts to load the data
				if(response.charAt(0) == 'L')
				{

					String file_line = "";
					String src_city = "";
					String dest_city = "";
					String airline_id = "";
					Integer high_price = null;
					Integer low_price = null;

					//read in path of the file 
					System.out.println("Enter the path of the file name:");
					String file_path = scan.nextLine();
					fr = new Scanner(new FileReader(file_path));

					//for each line of the file
					while(fr.hasNext())
					{
						//read in, and split on the spaces
						file_line = fr.next();
						
						String[] tokens = file_line.split(" ");

						src_city = tokens[0];
						dest_city = tokens[1];
						airline_id = tokens[2];
						high_price = new Integer(tokens[3]);
						low_price = new Integer(tokens[4]);

						//build the insert query with the data and execute it
						try
				        {
				            statement = dbcon.createStatement();
				            String query = "INSERT INTO Price VALUES ('"+src_city+"', '"+dest_city+"', '"+airline_id+"', '"+high_price.intValue()+"', '"+low_price.intValue()+")";
				            statement.executeQuery(query);    
				            //resultSet = statement.executeQuery(query);
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
				//if the user prompts to edit the data
				else if(response.charAt(0) =='C')
				{
					String src_city = "";
					String dest_city = "";
					int high_price = 0;
					int low_price = 0;

					System.out.println("Enter the departure city (Three Letter Code)");
					src_city = scan.nextLine();

					System.out.println("Enter the destination city (Three Letter Code)");
					dest_city = scan.nextLine();

					System.out.println("Enter the high price of the flight");
					high_price = scan.nextInt();

					System.out.println("Enter the high price of the flight");
					low_price = scan.nextInt();

					try
			        {
			            statement = dbcon.createStatement();
			            String query = "UPDATE Price "+
			            			   "SET high_price = "+high_price+", low_price = "+low_price+
			            			   "WHERE departue_city = "+src_city+" AND arrival_city = "+dest_city;
			            statement.executeQuery(query);    
			            //resultSet = statement.executeQuery(query);
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
			else if(selection == 5)
			{
				String file_line = "";
				String plane_type = "";
				String manufact = "";
				Integer capacity = null;
				String last_service = "";
				Integer year_made = null;
				String owner_id = "";

				//read in path of the file 
				System.out.println("Enter the path of the file name:");
				String file_path = scan.nextLine();
				fr = new Scanner(new FileReader(file_path));

				//for each line of the file
				while(fr.hasNext())
				{
					//read in, and split on the spaces
					file_line = fr.next();
					
					String[] tokens = file_line.split(" ");

					plane_type = tokens[0];
					manufact = tokens[1];
					capacity = new Integer(tokens[2]);
					last_service = tokens[3];
					year_made = new Integer(tokens[4]);
					owner_id = tokens[5];

					//build the insert query with the data and execute it
					try
			        {
			            statement = dbcon.createStatement();
			            String query = "INSERT INTO Plane VALUES ('"+plane_type+"', '"+manufact+"', '"+capacity.intValue()+"', to_date('"+last_service+"', 'DD-MON-YYYY HH24:MI:SS'), '"+
			            													year_made.intValue()+"', '"+owner_id+")"; 
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
			}
			else if(selection == 6)
			{
				System.out.println("Enter the flight number:");

				String flight_num = scan.nextLine();

				System.out.println("Enter the flight date: (MM/DD/YYYY)");

				String date = scan.nextLine();

				try
		        {
		            statement = dbcon.createStatement();
		            String query = "SELECT salutation, first_name, last_name " +
		            			   "FROM Customer, Reservation, Reservation_detail" +
		            			   "WHERE Reservation.cid = Customer.cid" +
		            			   "AND Reservation_detail.reservation_number = Reservation.reservation_number" +
		            			   "AND Reservation_detail.flight_date = to_date('"+date+"', 'DD-MON-YYYY HH24:MI:SS')" +
		            			   "AND Reservation_detail.flight_num = " +flight_num;
		            statement.executeQuery(query);    
		            resultSet = statement.executeQuery(query);

		            while (resultSet.next()) 
				    {
				    	System.out.println("Flight "+flight_num+" Passenger List for "+date);
				    	System.out.println("============================================================");
					   	System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
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