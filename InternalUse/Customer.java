import java.sql.*;

public class Customer
{
	private static Connection connection;

    private static Statement statement;
    
    private static ResultSet resultSet;
    
	public static void main(String[] args)
	{
		String username = "cjs176";
		String password = "@Minniedog8111";

		String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";

        // Open the connection
		try
		{

			DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());

			connection = DriverManager.getConnection(url, username, password);
        }
		catch(Exception e)
		{
			System.out.println("Error connecting to the database. Error: ");

			e.printStackTrace();
		}
		
        try
        {
            // Form the query
            statement = connection.createStatement();
            String query = "SELECT * from Airline";
        
            // Get the result
            resultSet = statement.executeQuery(query);
        
            int counter = 1;
        
            // Print the results
            while(resultSet.next())
            {
                System.out.println("Record " + counter + ": " + resultSet.getString(1)
                                   + " " + resultSet.getString(2) + " " + resultSet.getString(3)
                                   + " " + resultSet.getInt(4));
                counter ++;
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
        
        // Close the connection
        try
        {
            connection.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Error connecting to database.  Machine Error: " +
                               Ex.toString());
        }
	}
}