import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class team88_driver
{
	private static Connection connection;
	private static String username = "cjs176";
	private static String password = "@Minniedog8111";
	private static String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

	public static void main(String args[])
	{
		// Open the connection
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			connection = DriverManager.getConnection(url, username, password);
        }
		catch(Exception e)
		{
			System.out.println("Error connecting to the database. Error: ");

			e.printStackTrace();
		}

		System.out.println("\n###################################################");
		System.out.println("#                                                 #");
		System.out.println("#   EXECUTING ADMINISTRATOR INTERFACE FUNCTIONS   #");
		System.out.println("#                                                 #");
		System.out.println("###################################################\n");

		// Do all admin queries
		AdminDriver.Begin(connection);

		System.out.println("\n##############################################");
		System.out.println("#                                            #");
		System.out.println("#   EXECUTING CUSTOMER INTERFACE FUNCTIONS   #");
		System.out.println("#                                            #");
		System.out.println("##############################################\n");

		// Do all customer queries
		CustomerDriver.Begin(connection);

		// Close the connection
		try
        {
            connection.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Error connecting to database.  Machine Error: " + Ex.toString());
        }
	}
}