import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class team88_admin
{
	static Scanner scan = new Scanner(System.in);
	static Scanner fr = null;
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

				System.out.println("Do you really want to erase the database? (y/n):");

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
			            	statement.executeQuery(query);
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
					
					String[] line_tokens = file_line.split(" ");
					StringBuilder sb = new StringBuilder(50);

					int i = 0;
					id = line_tokens[i];
					i++;

					while(Character.isLowerCase(line_tokens[i].charAt(line_tokens.length - 1)))
					{
						sb.append(line_tokens[i]);
						i++;
					}

					name = sb.toString();
					i++;

					abbrev = line_tokens[i];

					year_founded = new Integer(line_tokens[line_tokens.length - 1]);

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
					
					String[] line_tokens = file_line.split(" ");

					flight_num = line_tokens[0];
					airline_id = line_tokens[1];
					plane_type = line_tokens[2];
					src_city= line_tokens[3];
					dest_city= line_tokens[4];
					src_time = line_tokens[5];
					dest_time = line_tokens[6];
					weekly_schedule = line_tokens[7];

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
					
					String[] line_tokens = file_line.split(" ");

					plane_type = line_tokens[0];
					manufact = line_tokens[1];
					capacity = new Integer(line_tokens[2]);
					last_service = line_tokens[3];
					year_made = new Integer(line_tokens[4]);
					owner_id = line_tokens[5];

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