import java.util.*;
import java.io.*;
import java.sql.*;
import java.text.ParseException;

public class team88-admin_interface
{
	Scanner s = new Scanner(System.in);
	int selection;
	private static Connection dbcon;
	private Statement Statement;
	private String username = "dmb147";
	private String password = "3906832";
	private String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

	public static void main(String args[]) throws IOException
	{	
		dbcon = DriverManager.getConnection(url, uName, pw);

		System.out.println("Which interface would you like to load?");
		System.out.println("1: Admin");
		System.out.println("2: Customer");

		selection = s.nextInt();

		// Admin
		if(selection == 1)
		{
			System.out.println("Administrator Options");
			System.out.println("=====================");
			System.out.println("1: Erase the database");
			System.out.println("2: Load airline information");
			System.out.println("3: Load schedule information");
			System.out.println("4: Load pricing information");
			System.out.println("5. Load plane information");
			System.out.println("6. Generate passenger manifest");

			selection = s.nextInt();

			if(selection == 1)
			{
				
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
		else if(selection = 2)
		{

		}

		dbcon.close();
	}
}