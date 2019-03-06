package ControllerActions;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JOptionPane;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import mySQL.Statements;

public class Appointments {

	@FXML
	private TableView<Model.Customer> CustomerWithAppts;
	@FXML
	private Button DeleteClear;
	@FXML
	private TextField SearchField;
	@FXML
	private TextField TitleField;
	@FXML
	private DatePicker Date;
	@FXML
	private ComboBox<String> StartTime;
	@FXML
	private ComboBox<String> EndTime;
	@FXML
	private ComboBox<String> TypeDropDown;
	@FXML
	private Button save;
	@FXML
	private Button cancelApt;
	@FXML
	private ComboBox<String> ApptByDays;
	@FXML
	private TableView<Model.Customer> ApptList;
	@FXML
	private TableColumn<Model.Customer, String> CustomerNameT;
	@FXML
	private TableColumn<Model.Customer, String> ApptName;
	@FXML
	private TableColumn<Model.Customer, String> title;

	@FXML
	private TableColumn<Model.Customer, String> start;
	@FXML
	private TableColumn<Model.Customer, String> end;
	@FXML
	private TableColumn<Model.Customer, String> Type;


	List<Model.Customer> Dates = new ArrayList<>();
	private TreeMap<String, List<String>> StartDateTime = new TreeMap<>();
	private TreeMap<String, List<String>> EndDateTime = new TreeMap<>();

  	private final ObservableList<String> startTimes = FXCollections.observableArrayList();
    private final ObservableList<String> endTimes = FXCollections.observableArrayList();
    private final DateTimeFormatter timeDTF = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);

	public Appointments(TableColumn<Model.Customer, String> ApptName, TableColumn<Model.Customer, String> title,TableColumn<Model.Customer, String> Type,TableColumn<Model.Customer, String> start,TableColumn<Model.Customer, String> end,TableView<Model.Customer> CustomerWithAppts, Button DeleteClear, TextField SearchField, TextField TitleField, DatePicker Date, ComboBox<String>  StartTime, ComboBox<String>  EndTime
			, ComboBox<String> TypeDropDown, Button save, Button cancelApt, ComboBox<String> ApptByDays, TableView<Model.Customer> ApptList,TableColumn<Model.Customer, String> CustomerNameT)
	{
		this.CustomerWithAppts =CustomerWithAppts; this.DeleteClear = DeleteClear; this.SearchField = SearchField; this.TitleField = TitleField; this.Date = Date; this.StartTime =StartTime;
		this.EndTime = EndTime; this.TypeDropDown = TypeDropDown; this.save = save; this.cancelApt = cancelApt; this.ApptByDays = ApptByDays; this.ApptList = ApptList; this.CustomerNameT = CustomerNameT;
		this.ApptName = ApptName; this.title = title;  this.start = start; this.end = end; this.Type = Type; this.Date = Date;
	}

	public void loadCustomers(){
		//method will load the data to the customer table
		CustomerWithAppts.getItems().clear();//remove the current items
		CustomerNameT.setCellValueFactory(new PropertyValueFactory<>("fullName"));
    	CustomerWithAppts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        CustomerWithAppts.getItems().setAll(Statements.getCustomerList());

	}

	public void  getApptsByCustomer(){
		//This method views the app by Customer
		ApptList.getItems().clear();//remove the current listed items before update
		ApptName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		start.setCellValueFactory(new PropertyValueFactory<>("start"));
		end.setCellValueFactory(new PropertyValueFactory<>("end"));
		Type.setCellValueFactory(new PropertyValueFactory<>("description"));
		ApptList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//populate the appt list appointments.
		ApptList.getItems().setAll(Statements.getApptListByCustomer(CustomerWithAppts.getSelectionModel().getSelectedItem().getCustomerId()));

	}

	public void  getAllAppts(){
		//this method loads all of the appts created
		ApptList.getItems().clear();//remove the current listed items before update
		ApptName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		start.setCellValueFactory(new PropertyValueFactory<>("start"));
		end.setCellValueFactory(new PropertyValueFactory<>("end"));
		Type.setCellValueFactory(new PropertyValueFactory<>("description"));
		ApptList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//populate the appt list appointments.
		ApptList.getItems().setAll(Statements.getApptList());


	}

	public void  getApptsByToggle(int Days){
		//this method loads all of the appts created
		ApptList.getItems().clear();//remove the current listed items before update
		ApptName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		start.setCellValueFactory(new PropertyValueFactory<>("start"));
		end.setCellValueFactory(new PropertyValueFactory<>("end"));
		Type.setCellValueFactory(new PropertyValueFactory<>("description"));
		ApptList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//populate the appt list appointments.
		ApptList.getItems().setAll(Statements.getApptListByToggle(Days));


	}

	public void  getBookedAppts(){

		List<Model.Customer> applist = Statements.getApptList();


		//looping with lambda to obtain the classes to populater the available time
		applist.forEach(list -> {
			Dates.add(list);
		});
		List<String> times = new ArrayList<>(); List<String> endTimes = new ArrayList<>();

		//this lambda code will help to obtain the dates and time, so we can populate the combox with only the available times
		Dates.forEach(dates -> {

			//not working, not all of the times and dates are printing out

			String StartTime = dates.getStart();//obtain the start time and date
			String EndTime = dates.getEnd();//obtain the end time and date
			String DateStart = StartTime.substring(0,StartTime.indexOf(" ")).toString().trim(); //grab the date
			times.add(StartTime.substring(StartTime.indexOf(" "),StartTime.length()).toString().trim());//grab the time, and added to the list
			endTimes.add(EndTime.substring(EndTime.indexOf(" "),EndTime.length()).toString().trim()); ////grab the end time taken, and add to the list

			Collections.sort(times);
			Collections.sort(endTimes);

			 StartDateTime.put(DateStart,times);
			 EndDateTime.put(DateStart,endTimes);
		});


	}

	public void getAvailableTimes(){
		getBookedAppts();
		 LocalTime time = LocalTime.of(9, 0);

		 endTimes.clear();
		 startTimes.clear();
		 StartTime.setValue("");
		 EndTime.setValue("");
     	do {


     		startTimes.add(time.format(timeDTF));
     		endTimes.add(time.format(timeDTF));
     		time = time.plusMinutes(15);

     	} while(!time.equals(LocalTime.of(18, 15)));
     		startTimes.remove(startTimes.size() - 1);
     		endTimes.remove(0);

if (Date.getValue() != null){
     		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    		LocalDate localDate = LocalDate.parse(Date.getValue().toString(),format);
    		String dateEntered = localDate.toString();
     		//Using a lambda here to remove times that already booked in the system
             StartDateTime.forEach((k,v) -> {
            	 List<String> times = v;

            	 if (k.equals(dateEntered)){
	            	 times.forEach(t -> {
	            		 if (startTimes.contains(t)){
	                	 	 startTimes.remove(t);
	                	 }
	            	 });
            	}

             });
           //Using a lambda here to remove times that already booked in the system
             EndDateTime.forEach((k,v) -> {
            	 List<String> times = v;
            	 if (k.equals(dateEntered)){
	            	 times.forEach(t -> {
	            		 if (endTimes.contains(t)){
	                		 endTimes.remove(t);
	                	 }
	            	 });
            	 }
             });
}

             StartTime.setItems(startTimes);
             EndTime.setItems(endTimes);


	}

	private boolean verifyEntry(){
			ZoneId zid = ZoneId.systemDefault();
			LocalDate localDate = Date.getValue();
	        LocalTime startTime = LocalTime.parse(StartTime.getSelectionModel().getSelectedItem(), timeDTF);
	        LocalTime endTime = LocalTime.parse(EndTime.getSelectionModel().getSelectedItem(), timeDTF);

	        LocalDateTime startDT = LocalDateTime.of(localDate, startTime);
	        LocalDateTime endDT = LocalDateTime.of(localDate, endTime);

	        ZonedDateTime start = startDT.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));
	        ZonedDateTime end = endDT.atZone(zid).withZoneSameInstant(ZoneId.of("UTC"));

		if (CustomerWithAppts.getSelectionModel().getSelectedItem() == null){
			JOptionPane.showMessageDialog(null, "Please select a customer from the customer list.","No Customer Choosen", JOptionPane.INFORMATION_MESSAGE);
			CustomerWithAppts.requestFocus();
			return false;
		}else if (Date.getValue() == null)  {
			JOptionPane.showMessageDialog(null, "Please choose a date","No Date Choosen", JOptionPane.INFORMATION_MESSAGE);
			Date.requestFocus();
			return false;
		}else if (StartTime.getSelectionModel().getSelectedItem() == null){
			JOptionPane.showMessageDialog(null, "Please choose a start time","No start time Choosen", JOptionPane.INFORMATION_MESSAGE);
			StartTime.requestFocus();
			return false;
		}else if (EndTime.getSelectionModel().getSelectedItem() == null){
			JOptionPane.showMessageDialog(null, "Please choose a end time","No end time Choosen", JOptionPane.INFORMATION_MESSAGE);
			EndTime.requestFocus();
			return false;
		}else if (TitleField.getText().isEmpty()){
			TitleField.requestFocus();
			JOptionPane.showMessageDialog(null, "Please enter a title","No title Choosen", JOptionPane.INFORMATION_MESSAGE);
		return false;
		}else if (TypeDropDown.getSelectionModel().getSelectedItem() == null){
			JOptionPane.showMessageDialog(null, "Please choose a type","No type Choosen", JOptionPane.INFORMATION_MESSAGE);
			TypeDropDown.requestFocus();
			return false;
		}else if (end.equals(start) || end.isBefore(start)){
			JOptionPane.showMessageDialog(null, "End time must be after Start time","Incorrect Times", JOptionPane.INFORMATION_MESSAGE);
			return false;
        } else
			try {
				if (Statements.isConflictedAppt(start, end,  User.getUserName())){
					JOptionPane.showMessageDialog(null, "Conflicted Appointment, please choose a different time. ","Invalid Times", JOptionPane.INFORMATION_MESSAGE);
					return false;
				}
			} catch (HeadlessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}

	public void newAppt(){
		//enter the new appt in the system
		if (verifyEntry()){
			Model.Customer nAppt = new Model.Customer();
			nAppt.setCustomerId(CustomerWithAppts.getSelectionModel().getSelectedItem().getCustomerId());
			nAppt.setFullName(CustomerWithAppts.getSelectionModel().getSelectedItem().getFullName());
			nAppt.setDate(Date.getValue());
			nAppt.setStart(StartTime.getValue());
			nAppt.setEnd(EndTime.getValue());
			nAppt.setTitle(TitleField.getText());
			nAppt.setLocation(CustomerWithAppts.getSelectionModel().getSelectedItem().getCountry());
			nAppt.setType(TypeDropDown.getSelectionModel().getSelectedItem());
			nAppt.setUrl("NONE");
			if (Statements.newAppt(nAppt, User.getUserName())){
				getAllAppts();
				clearFields();
			}else{
				JOptionPane.showMessageDialog(null, "Unable to create new appt","Failure", JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	public void clearFields(){
		//clear the fields when requested by user
        Date.setValue(LocalDate.now());
        this.StartTime.setValue("");
        this.EndTime.setValue("");
        this.TitleField.setText("");
        this.TypeDropDown.setValue("");

        save.setText("Save");

	}
	public void setFields(){
		//get the appt details loaded for edits
                DateTimeFormatter dateDTF = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
		String StartTime = ApptList.getSelectionModel().getSelectedItem().getStart();//get the start time
		StartTime = StartTime.substring(StartTime.indexOf(" "),StartTime.length()).toString().trim();//grab the time, and added to the list

		String EndTime = ApptList.getSelectionModel().getSelectedItem().getEnd();
		EndTime = EndTime.substring(EndTime.indexOf(" "),EndTime.length()).toString().trim(); ////grab the end time taken, and add to the list

        Date.setValue(LocalDate.parse(ApptList.getSelectionModel().getSelectedItem().getStart(), dateDTF));
        this.StartTime.setValue(StartTime);
        this.EndTime.setValue(EndTime);
        this.TitleField.setText(ApptList.getSelectionModel().getSelectedItem().getTitle());
        this.TypeDropDown.setValue(ApptList.getSelectionModel().getSelectedItem().getDescription());
        save.setText("Update");

	}

	public void UpdateAppt(){
		if (verifyEntry()){
		//update the appts for a customer
		Model.Customer nAppt = new Model.Customer();
		nAppt.setAppointmentId(ApptList.getSelectionModel().getSelectedItem().getAppointmentId());
		nAppt.setDate(Date.getValue());
		nAppt.setStart(StartTime.getValue());
		nAppt.setEnd(EndTime.getValue());
		nAppt.setTitle(TitleField.getText());
		nAppt.setType(TypeDropDown.getSelectionModel().getSelectedItem());
		if (Statements.updateAppt(nAppt, User.getUserName())){
			getAllAppts();
			clearFields();
		}
		}

	}

	public void deleteAppt(){
		//update the appts for a customer
				Model.Customer nAppt = new Model.Customer();
				nAppt.setCustomerId(ApptList.getSelectionModel().getSelectedItem().getCustomerId());
				nAppt.setAppointmentId(ApptList.getSelectionModel().getSelectedItem().getAppointmentId());
				Statements.deleteAppt(nAppt);
	}


}
