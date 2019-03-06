package Settings;

public class DBSettings {
	/*Author: Anthony Rodriguez
	 * Details: Contains all of the settings to init a mysql database connection
	 *Date: 02-26-19
	 */
	private static String Database = "U03Ob2";
	private static String UserName = "U03Ob2";
	private static String Password = "53688034114";
	private static String IP = "52.206.157.109";

	public static String getDatabase() {
		return Database;
	}

	public static String getUserName() {
		return UserName;
	}

	public static String getPassword() {
		return Password;
	}

	public static String getIP() {
		return IP;
	}



}
