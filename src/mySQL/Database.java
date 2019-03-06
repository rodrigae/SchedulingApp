package mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Settings.DBSettings;
import application.Logging;
/*Author: Anthony E Rodriguez
 * Date: 02/23/2019
 * Details: Create a connection to mysql database, and have it reuseable throughout the system.
 */
public class Database {

	private final static Logger Log = Logger.getLogger(Logging.class.getName());
    private static Connection connection;

    public static void begin(){
    	Log.log(Level.INFO, "Attempting database connection..");
        try{
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://"+DBSettings.getIP()+":3306/"+DBSettings.getDatabase(),DBSettings.getUserName(),DBSettings.getPassword());
           if (connection.isClosed()){Log.log(Level.SEVERE, "Connection Failed");}else{  Log.log(Level.INFO, "Connected");}
        }catch (ClassNotFoundException ce){
            Log.log(Level.SEVERE, "No JDBC Class Found: " + ce.getMessage());
            //ce.printStackTrace();
        }catch(Exception e){
        	  Log.log(Level.SEVERE, "Error: DB Connection - " + e.getClass().getName() + " - " + e.getMessage());
}
    }

    public static Connection getDB(){

    	try {
			if (connection.isClosed()){
				Log.log(Level.INFO, "Loss connection to database. Reconnecting..");
				begin();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			 Log.log(Level.SEVERE, "Error: DB Connection - Unable to reconnect" + e.getClass().getName() + " - " + e.getMessage());
		}
        return connection;
    }

    public static void TerminatedDB(){
        try{
            connection.close();
            Log.log(Level.INFO, "Connection Closed.");
        }catch (Exception e){
        	 Log.log(Level.SEVERE, "Error: Terminate DB - " + e.getClass().getName() + " - " + e.getMessage());
        }
    }

}
