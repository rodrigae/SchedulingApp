package Model;

/*Author: Anthony E Rodriguez
 *Date: 02/23/2019
 *Description: Contains the table model for mysql User
 */
public class User {

    private static String userName;

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		User.userName = userName;
	}



}
