package mySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Customer;
import application.Logging;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
public class Statements {

	private final static Logger Log = Logger.getLogger(Logging.class.getName());
	private static PreparedStatement Statement = null;
	private static ResultSet Results = null;

	public static boolean UserValidated(String userName, String passWord){
		//confirm the user has access to the system, return true or false
		try{
			Statement = Database.getDB().prepareStatement("SELECT 1 FROM user WHERE userName='"+userName+"' AND password='"+passWord+"'");
            Results =  Statement.executeQuery();

            if(Results.next()){
            	return true;

            } else {
                return false;
            }
        } catch(SQLException e){
        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());

        }finally{
        	try {
				Statement.clearBatch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return false;
	}

	public static boolean newUser(Object obj, String user){
		//check the object is an actual user
		if (obj instanceof Customer){
			try{
			Statement = Database.getDB().prepareStatement("Insert into customer (customerId,customerName,addressId,createdBy,createDate,lastUpdate,lastUpdateBy,active) Values(?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?,true)",Statement.RETURN_GENERATED_KEYS);

			 	Statement.setString(1, ((Customer) obj).getCustomerId());
		        Statement.setString(2, ((Customer) obj).getFullName());
		        Statement.setString(3, ((Customer) obj).getAddressId());
		        Statement.setString(4,user);
		        Statement.setString(5,user);
		        Statement.execute();

		        return true;
			}catch (SQLException e) {
				// TODO: handle exception
				 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
			}finally{
	        	try {
	        		 Statement.clearBatch();
	 		         Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		return false;

	}

	public static void deleteCustomer(Customer customer) {
		//delete the customer from the list
	        try{

	            Statement = Database.getDB().prepareStatement("DELETE customer.*, address.* from customer, address WHERE customer.customerId = ? AND customer.addressId = address.addressId");
	            Statement.setString(1, customer.getCustomerId());
	            Statement.executeUpdate();

	        } catch(SQLException e){
	        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
	        }finally{
	        	try {
	        		 Statement.clearBatch();
	 		         Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }

	public static boolean updateCustomer(Object obj, String User){
		if (obj instanceof Customer){
			try{
				//update customer
				Statement = Database.getDB().prepareStatement("UPDATE customer SET customerName = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE customerId = ?",Statement.RETURN_GENERATED_KEYS);
				Statement.setString(1, ((Customer) obj).getFullName());
		        Statement.setString(2, User);
		        Statement.setString(3, ((Customer) obj).getCustomerId());
		        Statement.executeUpdate();
		        Statement.clearBatch();
		        Statement.clearParameters();
		        Statement.clearWarnings();
		        //upcate country
				Statement = Database.getDB().prepareStatement("UPDATE country SET country = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE countryId = ?",Statement.RETURN_GENERATED_KEYS);
				Statement.setString(1, ((Customer) obj).getCountry());
		        Statement.setString(2, User);
		        Statement.setString(3, ((Customer) obj).getCustomerId());
		        Statement.executeUpdate();
		        Statement.clearBatch();
		        Statement.clearParameters();
		        Statement.clearWarnings();
				//update city
				Statement = Database.getDB().prepareStatement("UPDATE city SET city = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE cityId = ?",Statement.RETURN_GENERATED_KEYS);
				Statement.setString(1, ((Customer) obj).getCity());
		        Statement.setString(2, User);
		        Statement.setString(3, ((Customer) obj).getCustomerId());
		        Statement.executeUpdate();
		        Statement.clearBatch();
		        Statement.clearParameters();
		        Statement.clearWarnings();
		        //update address
				Statement = Database.getDB().prepareStatement("UPDATE address SET address = ?, address2 = ?, postalCode = ?, phone = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? WHERE addressId = ?",Statement.RETURN_GENERATED_KEYS);
				Statement.setString(1, ((Customer) obj).getAddress());
		        Statement.setString(2, ((Customer) obj).getAddress2());
		        Statement.setString(3, ((Customer) obj).getPostalCode());
		        Statement.setString(4, ((Customer) obj).getPhone());
		        Statement.setString(5, User);
		        Statement.setString(6, ((Customer) obj).getCustomerId());
		        Statement.executeUpdate();
		        Statement.clearBatch();
		        Statement.clearParameters();
		        Statement.clearWarnings();


		        return true;

			}catch (Exception e) {
				// TODO: handle exception
				 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
			}finally{
	        	try {
	        		 Statement.clearBatch();
	 		         Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		return false;
	}

	public static boolean newUserAddress(Object obj, String User){

		if (obj instanceof Customer){
			try{
			Statement = Database.getDB().prepareStatement("Insert into address (addressId,address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdate,lastUpdateBy) Values(?,?,?,?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)",Statement.RETURN_GENERATED_KEYS);
		 	Statement.setString(1, ((Customer) obj).getAddressId());
	        Statement.setString(2, ((Customer) obj).getAddress());
	        Statement.setString(3, ((Customer) obj).getAddress2());
	        Statement.setString(4, ((Customer) obj).getCityId());
	        Statement.setString(5,((Customer) obj).getPostalCode());
	        Statement.setString(6,((Customer) obj).getPhone());
	        Statement.setString(7,User);
	        Statement.setString(8,User);
	        Statement.execute();
	        return true;
			}catch (Exception e) {
				// TODO: handle exception
				 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
			}finally{
	        	try {
	        		 Statement.clearBatch();
	 		        Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	public static boolean newCity(Object obj, String User){
		if (obj instanceof Customer){
			try{
			Statement = Database.getDB().prepareStatement("Insert into city (cityId,city,countryId,createDate,createdBy,lastUpdate,lastUpdateBy) Values(?,?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)",Statement.RETURN_GENERATED_KEYS);

		 	Statement.setString(1, ((Customer) obj).getCityId());
	        Statement.setString(2, ((Customer) obj).getCity());
	        Statement.setString(3, ((Customer) obj).getCountryId());
	        Statement.setString(4,User);
	        Statement.setString(5,User);
	        Statement.execute();

	        return true;
			}catch (Exception e) {
				 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
			}finally{
	        	try {
	        		 Statement.clearBatch();
	 		        Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	public static boolean newCountry(Object obj, String User){
		if (obj instanceof Customer){
			try{

			Statement = Database.getDB().prepareStatement("Insert into country (countryId,country,createDate,createdBy,lastUpdate,lastUpdateBy) Values(?,?,CURRENT_TIMESTAMP,?,CURRENT_TIMESTAMP,?)",Statement.RETURN_GENERATED_KEYS);
		 	Statement.setString(1, ((Customer) obj).getCountryId());
	        Statement.setString(2, ((Customer) obj).getCountry());
	        Statement.setString(3,User);
	        Statement.setString(4,User);
	        Statement.execute();
	        return true;

			}catch (Exception e) {
				 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
			}finally{
	        	try {
	        		 Statement.clearBatch();
	 		        Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	private static List<Integer> getCurrentIdList(){
		//obtain the list of users in the system, to avoid duplicates
		try{
			List<Integer> CustomerIds = new ArrayList<>();
			Statement = Database.getDB().prepareStatement("SELECT DISTINCT customerId FROM customer");
            Results =  Statement.executeQuery();

            while(Results.next()){
            	CustomerIds.add(Results.getInt("customerId"));
            }

            return CustomerIds;

        } catch(SQLException e){
        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
        }finally{
        	try {
				Statement.clearBatch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		return null;
	}

	private static List<Integer> getCurrentApptIdList(){
		//obtain the list of users in the system, to avoid duplicates
		try{
			List<Integer> appointmentId = new ArrayList<>();
			Statement = Database.getDB().prepareStatement("SELECT DISTINCT appointmentId FROM appointment");
            Results =  Statement.executeQuery();

            while(Results.next()){
            	appointmentId.add(Results.getInt("appointmentId"));
            }

            return appointmentId;

        } catch(SQLException e){
        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
        }finally{
        	try {
				Statement.clearBatch();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		return null;
	}

	public static String generateID(){
		//obtain an id that is not on the system
		int ID = 0;
		List<Integer> CustomerIds = getCurrentIdList();//obtain the IDs in the system before proceeding.
		Random rand = new Random();
		while (true){
			ID = rand.nextInt(9999 - 1000) + 1000;
			if (!CustomerIds.contains(ID) || CustomerIds.isEmpty()) {
				break;
			}
		}
		return String.valueOf(ID);
	}

	private static Integer generateApptID(){
		//obtain an id that is not on the system
		int ID = 0;
		List<Integer> appointmentId = getCurrentApptIdList();//obtain the IDs in the system before proceeding.
		Random rand = new Random();
		while (true){
			ID = rand.nextInt(9999 - 1) + 1;
			if (!appointmentId.contains(ID) || appointmentId.isEmpty()) {
				break;
			}
		}
		return ID;
	}



   public static List<Model.Customer> getCustomerList() {
	   //obtain the customer list and all of the data needed it.

        ObservableList<Model.Customer> customerList = FXCollections.observableArrayList();
        try{



        	Statement = Database.getDB().prepareStatement("SELECT customer.customerId, customer.customerName, address.address, "
        			+ "address.address2, address.postalCode, city.city, country.country, address.phone FROM customer, address, city, country "
        			+ "WHERE customer.customerId = address.addressId AND address.addressId = city.cityId AND city.cityId = country.countryId ORDER BY customer.customerName");
         Results = Statement.executeQuery();

        Model.Customer user = null;

            while (Results.next()) {
            	user = new Model.Customer();
                user.setCustomerId(Results.getString("customer.customerId"));
                user.setFullName(Results.getString("customer.customerName"));
                user.setAddress(Results.getString("address.address"));
                user.setAddress2(Results.getString("address.address2"));
                user.setCity(Results.getString("city.city"));
                user.setCountry(Results.getString("country.country"));
                user.setPostalCode(Results.getString("address.postalCode"));
                user.setPhoneNo(Results.getString("address.phone"));
                customerList.add(user);
            	}


        } catch (SQLException s) {
        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
        } catch (Exception e) {
        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
        }finally{
        	try {
				Statement.clearBatch();
				Statement.clearParameters();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }


        return customerList;

    }

   public static boolean newAppt(Object obj, String User) {
	   if (obj instanceof Model.Customer){



	    DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
	    LocalDate localDate = ((Model.Customer) obj).getDate();
		LocalTime startTime = LocalTime.parse(((Model.Customer) obj).getStart(), timeDTF);
		LocalTime endTime = LocalTime.parse(((Model.Customer) obj).getEnd(), timeDTF);
		ZoneId zid = ZoneId.systemDefault();

	       LocalDateTime startDT = LocalDateTime.of(localDate, startTime);
	       LocalDateTime endDT = LocalDateTime.of(localDate, endTime);

	       ZonedDateTime startUTC = startDT.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
	       ZonedDateTime endUTC = endDT.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

	       Timestamp start = Timestamp.valueOf(startUTC.toLocalDateTime()); //convert time to local database format
	       Timestamp end = Timestamp.valueOf(endUTC.toLocalDateTime()); //convert time to local database format

       try {

    	   		int ApptId = generateApptID();//Generate a new id for a new client

               Statement = Database.getDB().prepareStatement("INSERT INTO appointment "
               + "(customerId, title, description, location, contact, url, start, end, createDate, createdBy, lastUpdate, lastUpdateBy,appointmentId)"
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?,?)");

               Statement.setString(1, ((Model.Customer) obj).getCustomerId());
               Statement.setString(2, ((Model.Customer) obj).getTitle());
               Statement.setString(3, ((Model.Customer) obj).getType());
               Statement.setString(4, ((Model.Customer) obj).getLocation());
               Statement.setString(5, User);
               Statement.setString(6, ((Model.Customer) obj).getUrl());
               Statement.setTimestamp(7, start);
               Statement.setTimestamp(8, end);
               Statement.setString(9, User);
               Statement.setString(10, User);
               Statement.setInt(11, ApptId);
               Statement.executeUpdate();

               return true;

           } catch (SQLException e) {
        	   		e.printStackTrace();
           }finally{
           	try {
   				Statement.clearBatch();
   				Statement.clearParameters();
   			} catch (SQLException e) {
   				// TODO Auto-generated catch block
   				e.printStackTrace();
   			}




           }
	  }
		return false;
   }

   public static List<Model.Customer> getApptList() {
	   //obtain the customer list and all of the data needed it.

       ObservableList<Model.Customer> apptList = FXCollections.observableArrayList();


        try{
                DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
                ZoneId newzid = ZoneId.systemDefault();
                Timestamp tsStart = null;
                ZonedDateTime zonetStart = null;
        	ZonedDateTime Start = null;

                Timestamp tsEnd = null;
                ZonedDateTime zoneEnd = null;
        	ZonedDateTime End = null;

        	 PreparedStatement pst = Database.getDB().prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, appointment.contact, appointment.url, appointment.start, appointment.end, customer.customerName FROM appointment, customer where customer.customerId = appointment.customerId");
        	 Results = pst.executeQuery();

        Model.Customer user = null;

            while (Results.next()) {
                tsStart = Results.getTimestamp("start");
                zonetStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                Start = zonetStart.withZoneSameInstant(newzid);
                
                tsEnd = Results.getTimestamp("end");
                zoneEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
                End = zoneEnd.withZoneSameInstant(newzid);
                
            	user = new Model.Customer();
            	user.setAppointmentId(Results.getString("appointmentId"));
                user.setCustomerId(Results.getString("customerId"));
                user.setFullName(Results.getString("customerName"));
                user.setTitle(Results.getString("title"));
                user.setDescription(Results.getString("description"));
                user.setLocation(Results.getString("location"));
                user.setContact(Results.getString("contact"));
                user.setUrl(Results.getString("url"));
                user.setStart(Start.format(timeDTF));
                user.setEnd(End.format(timeDTF));
                apptList.add(user);
            	}


        } catch (SQLException s) {
        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
        } catch (Exception e) {
        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
        }finally{
        	try {
				Statement.clearBatch();
				Statement.clearParameters();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }


        return apptList;

    }

   public static List<Model.Customer> getApptListByCustomer(String CustomerId) {
	   //obtain the customer list and all of the data needed it.

       ObservableList<Model.Customer> apptList = FXCollections.observableArrayList();


        try{

               DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
                ZoneId newzid = ZoneId.systemDefault();
                Timestamp tsStart = null;
                ZonedDateTime zonetStart = null;
        	ZonedDateTime Start = null;

                Timestamp tsEnd = null;
                ZonedDateTime zoneEnd = null;
        	ZonedDateTime End = null;
                
        	 PreparedStatement pst = Database.getDB().prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, appointment.contact, appointment.url, appointment.start, appointment.end, customer.customerName FROM appointment, customer where customer.customerId = appointment.customerId AND appointment.customerId = ?");
        	 pst.setString(1, CustomerId);
        	 Results = pst.executeQuery();

        Model.Customer user = null;

            while (Results.next()) {
                
                  tsStart = Results.getTimestamp("start");
                zonetStart = tsStart.toLocalDateTime().atZone(ZoneId.of("UTC"));
                Start = zonetStart.withZoneSameInstant(newzid);
                
                tsEnd = Results.getTimestamp("end");
                zoneEnd = tsEnd.toLocalDateTime().atZone(ZoneId.of("UTC"));
                End = zoneEnd.withZoneSameInstant(newzid);
                
            	user = new Model.Customer();
            	user.setAppointmentId(Results.getString("appointmentId"));
                user.setCustomerId(Results.getString("customerId"));
                user.setFullName(Results.getString("customerName"));
                user.setTitle(Results.getString("title"));
                user.setDescription(Results.getString("description"));
                user.setLocation(Results.getString("location"));
                user.setContact(Results.getString("contact"));
                user.setUrl(Results.getString("url"));
                user.setStart(Start.format(timeDTF));
                user.setEnd(End.format(timeDTF));
                apptList.add(user);
            	}


        } catch (SQLException s) {
        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
        } catch (Exception e) {
        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
        }finally{
        	try {
				Statement.clearBatch();
				Statement.clearParameters();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


        }


        return apptList;

    }

   public static boolean updateAppt(Object obj, String User){
	   //update the appoint for customers in the database
		if (obj instanceof Model.Customer){
			try{


			    DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
			    LocalDate localDate = ((Model.Customer) obj).getDate();
				LocalTime startTime = LocalTime.parse(((Model.Customer) obj).getStart(), timeDTF);
				LocalTime endTime = LocalTime.parse(((Model.Customer) obj).getEnd(), timeDTF);
				ZoneId zid = ZoneId.systemDefault();

			       LocalDateTime startDT = LocalDateTime.of(localDate, startTime);
			       LocalDateTime endDT = LocalDateTime.of(localDate, endTime);

			       ZonedDateTime startUTC = startDT.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
			       ZonedDateTime endUTC = endDT.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

			       Timestamp start = Timestamp.valueOf(startUTC.toLocalDateTime()); //convert time to local database format
			       Timestamp end = Timestamp.valueOf(endUTC.toLocalDateTime()); //convert time to local database format

				//update customer appt
				Statement = Database.getDB().prepareStatement("UPDATE appointment SET title = ?, description = ?, start = ?, end = ?, lastUpdateBy = ?, lastUpdate = CURRENT_TIMESTAMP WHERE appointmentId = ?");
				Statement.setString(1, ((Customer) obj).getTitle());
		        Statement.setString(2, ((Customer) obj).getType());
		        Statement.setTimestamp(3, start);
		        Statement.setTimestamp(4, end);
		        Statement.setString(5, User);
		        Statement.setString(6, ((Customer) obj).getAppointmentId());
		        Statement.executeUpdate();


		        return true;

			}catch (Exception e) {
				// TODO: handle exception
				 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
			}finally{
	        	try {
	        		 Statement.clearBatch();
	 		         Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		}
		return false;
	}

	public static void deleteAppt(Object obj) {
		//delete the customer appointment from the list
	        try{
	        if (obj instanceof Customer){
	            Statement = Database.getDB().prepareStatement("DELETE FROM `U03Ob2`.`appointment` WHERE customerId = ? AND appointmentId = ?");
	            Statement.setString(1, ((Customer) obj).getCustomerId());
	            Statement.setString(2, ((Customer) obj).getAppointmentId());
	            Statement.executeUpdate();
	        }
	        } catch(SQLException e){
	        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
	        }finally{
	        	try {
	        		 Statement.clearBatch();
	 		         Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    }

	   public static boolean isConflictedAppt(ZonedDateTime newStart, ZonedDateTime newEnd, String User) throws SQLException {

	        try{
	        Statement = Database.getDB().prepareStatement("SELECT * FROM appointment WHERE (? BETWEEN start AND end OR ? BETWEEN start AND end OR ? < start AND ? > end) AND (createdBy = ?)");
	        Statement.setTimestamp(1, Timestamp.valueOf(newStart.toLocalDateTime()));
	        Statement.setTimestamp(2, Timestamp.valueOf(newEnd.toLocalDateTime()));
	        Statement.setTimestamp(3, Timestamp.valueOf(newStart.toLocalDateTime()));
	        Statement.setTimestamp(4, Timestamp.valueOf(newEnd.toLocalDateTime()));
	        Statement.setString(5, User);
	        ResultSet rs = Statement.executeQuery();

	        if(rs.next()) {
	            return true;
		}

	        } catch (SQLException e) {
	       	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
	        }finally{
	        	try {
	        		 Statement.clearBatch();
	 		         Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        return false;
	    }

	   public static List<Model.Customer> getApptListByToggle(int Days) {
		   //obtain the customer list and all of the data needed it.

	       ObservableList<Model.Customer> apptList = FXCollections.observableArrayList();


	        try{

	        	 PreparedStatement pst = Database.getDB().prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, appointment.contact, appointment.url, appointment.start, appointment.end, customer.customerName FROM appointment, customer where customer.customerId = appointment.customerId AND appointment.start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL "+Days+" DAY)");
	        	 Results = pst.executeQuery();

	        Model.Customer user = null;

	            while (Results.next()) {
	            	user = new Model.Customer();
	            	user.setAppointmentId(Results.getString("appointmentId"));
	                user.setCustomerId(Results.getString("customerId"));
	                user.setFullName(Results.getString("customerName"));
	                user.setTitle(Results.getString("title"));
	                user.setDescription(Results.getString("description"));
	                user.setLocation(Results.getString("location"));
	                user.setContact(Results.getString("contact"));
	                user.setUrl(Results.getString("url"));
	                user.setStart(new SimpleDateFormat("yyyy-MM-dd h:mm a").format(Timestamp.valueOf(Results.getString("start"))));
	                user.setEnd(new SimpleDateFormat("yyyy-MM-dd h:mm a").format(Timestamp.valueOf(Results.getString("end"))));
	               // user.setStart(new SimpleDateFormat("h:mm a").format(Timestamp.valueOf(Results.getString("start"))));
	               // user.setEnd(new SimpleDateFormat("h:mm a").format(Timestamp.valueOf(Results.getString("end"))));
	                apptList.add(user);
	            	}


	        } catch (SQLException s) {
	        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
	        } catch (Exception e) {
	        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
	        }finally{
	        	try {
					Statement.clearBatch();
					Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	        }


	        return apptList;

	    }
	   public static List<Model.Customer> getApptListByConsultant(String CreatedBy) {
		   //obtain the customer list and all of the data needed it, for the appointments within 15 minutes or passed due.

	       ObservableList<Model.Customer> apptList = FXCollections.observableArrayList();


	        try{

	        	 PreparedStatement pst = Database.getDB().prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, appointment.contact, appointment.url, appointment.start, appointment.end, customer.customerName FROM appointment, customer where customer.customerId = appointment.customerId AND appointment.createdBy = ?");
	        	 pst.setString(1, CreatedBy);
	        	 Results = pst.executeQuery();

	        Model.Customer user = null;
	        String Today = null;
	        String ApptTime = null;
	        SimpleDateFormat Timeformat = new SimpleDateFormat("h:mm a");
	        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
	        String ApptDate = null;

	            while (Results.next()) {
	            	//add the user to the list if the appt is within 15 minutes for notifications

	            	Today = Dateformat.format(new Date());//Todays date
	            	ApptTime = Timeformat.format(Timestamp.valueOf(Results.getString("start")));
	            	ApptDate = Dateformat.format(Timestamp.valueOf(Results.getString("start")));

	            	if (ApptComingUp(ApptTime) && Today.equals(ApptDate)){
			            	user = new Model.Customer();
			            	user.setAppointmentId(Results.getString("appointmentId"));
			                user.setCustomerId(Results.getString("customerId"));
			                user.setFullName(Results.getString("customerName"));
			                user.setTitle(Results.getString("title"));
			                user.setDescription(Results.getString("description"));
			                user.setLocation(Results.getString("location"));
			                user.setContact(Results.getString("contact"));
			                user.setUrl(Results.getString("url"));
			                user.setStart(new SimpleDateFormat("yyyy-MM-dd h:mm a").format(Timestamp.valueOf(Results.getString("start"))));
			                user.setEnd(new SimpleDateFormat("yyyy-MM-dd h:mm a").format(Timestamp.valueOf(Results.getString("end"))));
			                apptList.add(user);
	            		}
	            	}


	        } catch (SQLException s) {
	        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
	        } catch (Exception e) {
	        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
	        }finally{
	        	try {
					Statement.clearBatch();
					Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	        }


	        return apptList;

	    }

	   public static List<Model.Customer> getMySchedule(String CreatedBy) {
		   //obtain the all schedule for created user

	       ObservableList<Model.Customer> mySchedule = FXCollections.observableArrayList();


	        try{

	        	 Statement = Database.getDB().prepareStatement("SELECT appointment.appointmentId, appointment.customerId, appointment.title, appointment.description, appointment.location, appointment.contact, appointment.url, appointment.start, appointment.end, customer.customerName FROM appointment, customer where customer.customerId = appointment.customerId AND appointment.createdBy = ?");
	        	 Statement.setString(1, CreatedBy);
	        	 Results = Statement.executeQuery();

	        Model.Customer user = null;

	            while (Results.next()) {
	            	       	user = new Model.Customer();
	            	       	user.setAgent(CreatedBy);
			            	user.setAppointmentId(Results.getString("appointmentId"));
			                user.setCustomerId(Results.getString("customerId"));
			                user.setFullName(Results.getString("customerName"));
			                user.setTitle(Results.getString("title"));
			                user.setDescription(Results.getString("description"));
			                user.setLocation(Results.getString("location"));
			                user.setContact(Results.getString("contact"));
			                user.setUrl(Results.getString("url"));
			                user.setStart(new SimpleDateFormat("yyyy-MM-dd h:mm a").format(Timestamp.valueOf(Results.getString("start"))));
			                user.setEnd(new SimpleDateFormat("yyyy-MM-dd h:mm a").format(Timestamp.valueOf(Results.getString("end"))));
			                mySchedule.add(user);

	            	}


	        } catch (SQLException s) {
	        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
	        } catch (Exception e) {
	        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
	        }finally{
	        	try {
					Statement.clearBatch();
					Statement.clearParameters();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


	        }


	        return mySchedule;

	    }


		private static boolean ApptComingUp(String dateStop) {
			//Determine if this user is ready for reboot on action
			String timeNow = new SimpleDateFormat("h:mm a").format(new Date()); //get the current time
			SimpleDateFormat format = new SimpleDateFormat("HH:mm a");  //used to format

			try {

				Date UserTimeLogged = format.parse(dateStop);
				Date currentTime = format.parse(timeNow);

				long minutesTimeLogged = TimeUnit.MILLISECONDS.toMinutes(UserTimeLogged.getTime());
			    long minutesCurrentTime = TimeUnit.MILLISECONDS.toMinutes(currentTime.getTime());
			   	long minutes = minutesTimeLogged-minutesCurrentTime;

			   	if (minutes > 0){
			   		return (minutesTimeLogged-minutesCurrentTime) < 15;
			   	}
			}catch(Exception e) {
				e.printStackTrace();
			}

			return false;

		}

		public static List<Model.Appointment> getTypeByMonth() {
			 //get the data for appts by Month and the amount
			 ObservableList<Model.Appointment> byMonth = FXCollections.observableArrayList();
		        try{
		        Statement = Database.getDB().prepareStatement(
		            "SELECT MONTHNAME(`start`) AS \"Month\", description AS \"Type\", COUNT(*) as \"Amount\" FROM appointment GROUP BY MONTHNAME(`start`), description");
		           Results = Statement.executeQuery();

		           Model.Appointment app = null;
		            while (Results.next()) {
		            	app = new Model.Appointment();
		            	app.setMonth(Results.getString("Month"));
		            	app.setType(Results.getString("Type"));
		            	app.setTypeCount(Results.getString("Amount"));
		                byMonth.add(app);
		            }

		        } catch (SQLException s) {
		        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
		        } catch (Exception e) {
		        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
		        }finally{
		        	try {
						Statement.clearBatch();
						Statement.clearParameters();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
		        	}
		      return byMonth;
		 }
	    public static ObservableList<XYChart.Data<String, Integer>> getTypeChartData() {

	    	ObservableList<XYChart.Data<String, Integer>>  data = FXCollections.observableArrayList();

	            try {
	            	Statement = Database.getDB().prepareStatement("SELECT description, COUNT(description) FROM appointment GROUP BY description");
	                Results = Statement.executeQuery();

	                while (Results.next()) {
	                        data.add(new Data<>(Results.getString("description"), Results.getInt("COUNT(description)")));
	                }

	            }  catch (SQLException s) {
		        	 Log.log(Level.SEVERE, "Class: " + s.getClass().getCanonicalName() + " - Error: " + s.getMessage());
		        } catch (Exception e) {
		        	 Log.log(Level.SEVERE, "Class: " + e.getClass().getCanonicalName() + " - Error: " + e.getMessage());
		        }finally{
		        	try {
						Statement.clearBatch();
						Statement.clearParameters();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}
		        	}
	        return data;

	    }
}
