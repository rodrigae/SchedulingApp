package mySQL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Logging;

public class loginTracker {
	private static FileWriter write = null;
	private static File file = null;
	private final static ZoneId newzid = ZoneId.systemDefault();
	private final static Logger Log = Logger.getLogger(Logging.class.getName());
	private static BufferedWriter br = null;

	public static void UserLoginTracker(String UserName){
		//track user logging into a txt file
		try {
			file = new File("SchedulingApp.txt");
			if (!file.exists()){file.createNewFile();}
			write = new FileWriter(file, true);
			br = new BufferedWriter(write);
			br.write(UserName + " - " + "Zone ID: " + newzid  + " TimeZone: " + new SimpleDateFormat("M/dd/Y h:mm a").format(new Date()));
			br.newLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			 for (StackTraceElement st : e.getStackTrace())
       	  {
       		 Log.log(Level.SEVERE, "Class: " + st.getClassName() + " Method : " +  st.getMethodName() + " line : " + st.getLineNumber());
       	   }
		}finally{
			try {
				br.close();
				write.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 for (StackTraceElement st : e.getStackTrace())
	        	  {
	        		 Log.log(Level.SEVERE, "Class: " + st.getClassName() + " Method : " +  st.getMethodName() + " line : " + st.getLineNumber());
	        	   }
			}
		}

	}

}
