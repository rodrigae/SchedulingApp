package Controllers;


import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import mySQL.Statements;

/*Author: Anthony E Rodriguez
 * Date: 02/24/2019
 * Description: Contains the controller for all of the actions in the Main program.
 */

public class SchedulingApp implements Initializable{

	//Following Fields are for the Customer section
	@FXML
	private TextField fullName;
	@FXML
	private TextField address;
	@FXML
	private TextField address2;
	@FXML
	private TextField city;
	@FXML
	private TextField postalCode;
	@FXML
	private TextField country;
	@FXML
	private TextField phoneNo;
	@FXML
	private TextField iD;
	@FXML
	private Button newButton;
	@FXML
	private Button cancel;
	@FXML
	private Button deleteButton;
	@FXML
	private Label saveLabel;
	@FXML
	private TitledPane CustomerPane;
	@FXML
	private TitledPane ApptsPane;
	@FXML
    private TableView<Model.Customer> customerTable;
	@FXML
	private TableColumn<Model.Customer, String> fNameT;
	@FXML
	private TableColumn<Model.Customer, String> phoneT;
	@FXML
	private TableColumn<Model.Customer, String> idT;
	private ControllerActions.Customer Customer = null;
	//End of Customer Field Section

	//Following Fields are for Appointments
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
	private ControllerActions.Appointments CustomerAppts = null;

	private final ObservableList<String> ApptsBy = FXCollections.observableArrayList();
	//End of appointments fields

	//Report Fields
	@FXML
	private TableView<Model.Customer> MyScheduleTable;
	@FXML
	private TableColumn<Model.Customer, String> reportApptAgent;
	@FXML
	private TableColumn<Model.Customer, String> reportApptName;
	@FXML
	private TableColumn<Model.Customer, String> reporttitle;
	@FXML
	private TableColumn<Model.Customer, String> reportstart;
	@FXML
	private TableColumn<Model.Customer, String> reportend;
	@FXML
	private TableColumn<Model.Customer, String> reportType;
	@FXML
	private TitledPane reportsPane;

	@FXML
	private TableView<Model.Appointment> ApptByTypeMonthsTable;
	@FXML
	private TableColumn<Model.Customer, String> reportmonthColumn;
	@FXML
	private TableColumn<Model.Customer, String> reporttypeColumn;
	@FXML
	private TableColumn<Model.Customer, String> reportTypeCount;
	private ControllerActions.Reports Reports = null;
	@SuppressWarnings("rawtypes")
	@FXML
	private BarChart TypeChart;



	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//Using this listener with lambda to load customer data for the customer section table
	    CustomerPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	        	Customer = new ControllerActions.Customer(fullName,address, address2,city,postalCode,country,phoneNo, iD, newButton,cancel,deleteButton, saveLabel,customerTable,fNameT,phoneT,idT,CustomerPane);
	        	Customer.loadCustomers();

	        }
	    });

	    //this listener is used to load the data to the fields, so the user can edit the customer
		   customerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		        if (newSelection != null) {
		        	Customer.readyCustomerItems();//clear the items and enable to load the data
		        	iD.setText(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
		    		fullName.setText(customerTable.getSelectionModel().getSelectedItem().getFullName());
		    		city.setText(customerTable.getSelectionModel().getSelectedItem().getCity());
		    		country.setText(customerTable.getSelectionModel().getSelectedItem().getCountry());
		            address.setText(customerTable.getSelectionModel().getSelectedItem().getAddress());
		            address2.setText(customerTable.getSelectionModel().getSelectedItem().getAddress2());
		            postalCode.setText(customerTable.getSelectionModel().getSelectedItem().getPostalCode());
		            phoneNo.setText(customerTable.getSelectionModel().getSelectedItem().getPhoneNo());
		            newButton.setText("Update");
		            cancel.setDisable(false);
		            deleteButton.setDisable(false);
		        }
		    });


		 //THE BELOW CODE IS FOR THE APPTS SECTION
	    //using this listener with lambda for the appts to load customer names and current
	    ApptsPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	        	CustomerAppts = new ControllerActions.Appointments(ApptName, title,Type,start,end,CustomerWithAppts,DeleteClear,SearchField,TitleField,Date,StartTime,EndTime,TypeDropDown,save,cancelApt,ApptByDays,ApptList,CustomerNameT);
	        	CustomerAppts.loadCustomers();

	        	 StartTime.getItems().clear();
	        	 EndTime.getItems().clear();

	        	 //load the types list
                 ObservableList<String> types = FXCollections.observableArrayList();
                 types.addAll("Analysis","Upgrade", "Downgrade","New Account", "Terminate Account");
                 TypeDropDown.setItems(types);

                 //will first load all of the appts
	             CustomerAppts.getAllAppts();
	             CustomerAppts.getAvailableTimes();


	             //Load the appts by type
	             ApptsBy.clear();
	             ApptsBy.add("All Appointment");
	             ApptsBy.add("Appointment For Month");
	             ApptsBy.add("Appointment For Week");
	             Collections.sort(ApptsBy);
	             ApptByDays.setItems(ApptsBy);


	        }

	    });


	   //Load Appts By Customer
	   CustomerWithAppts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	 ApptList.getItems().clear();//clear the list for reload
                 //will first load all of the appts
	           CustomerAppts.getApptsByCustomer();
	          CustomerAppts.clearFields();
	        }
	    });


	   //enable features for edit
	   ApptList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {
	        	CustomerAppts.setFields();
	        }
	    });

	   ApptByDays.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	        if (newSelection != null) {

	        	if (ApptByDays.getSelectionModel().getSelectedItem().equals("Appointment For Week")){
	        		CustomerAppts.getApptsByToggle(7);
	        	}else if (ApptByDays.getSelectionModel().getSelectedItem().equals("Appointment For Month")){
	        		CustomerAppts.getApptsByToggle(30);
	        	}else if (ApptByDays.getSelectionModel().getSelectedItem().equals("All Appointment")){
	        		CustomerAppts.getAllAppts();
	        	}

	        }
	    });

	 //Using this listener with lambda to load reports data
	   reportsPane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	        	Reports = new ControllerActions.Reports(TypeChart, ApptByTypeMonthsTable, reportmonthColumn, reporttypeColumn, reportTypeCount, MyScheduleTable, reportApptAgent, reportApptName, reporttitle, reportstart, reportend, reportType);
	        	Reports.getApptsByCustomer(User.getUserName());
	        	Reports.getApptsByMonth();
	        	Reports.LoadChart();

	        }
	    });
	}


	@FXML
	void saveOrEditCustomer(ActionEvent event){
		//determine if the user is going to be edit or delete
	switch(newButton.getText().toString()){
		case "New":
			iD.setText(Statements.generateID());
			Customer.readyCustomerItems();//get the fields ready
			newButton.setText("Save");
			cancel.setDisable(false);
			break;

		case "Update":
			if (Customer.verifyEntry()){
				Customer.actions("Update");
				newButton.setText("New");
				Customer.disablereadyCustomerItems();
				Customer.loadCustomers();
			}
			break;

		case "Save":
			if (Customer.verifyEntry()){
				Customer.actions("NewCustomer");;//check for entry before saving customer
				Customer.loadCustomers();//reload customers
			}
			break;

			}
		}

	@FXML
	void cancelCustomerUpdate(ActionEvent event){
		//this event is used for the cancel button in the customer section pane
		Customer.disablereadyCustomerItems();//reset items
		saveLabel.setText("");
		newButton.setText("New");
		customerTable.getSelectionModel().clearSelection();
	}

	@FXML
	void deleteCustomer(ActionEvent event){
		//this event controls the delete button on the customer section field
		Customer.actions("Delete");
	}

	@FXML
	void cancelApts(ActionEvent event){
		CustomerAppts.clearFields();
		CustomerAppts.getAllAppts();
	}

	@FXML
	void newAppt(ActionEvent event){
		//insert a new appt
		switch(save.getText()){
		case "Save":
			//this is used to save a new appt after choosing an customer
			CustomerAppts.newAppt();
			break;
		case "Update":
			//update information on the appt
			CustomerAppts.UpdateAppt();
			break;
		}

	}

	@FXML
	void getTimes(ActionEvent event){
		CustomerAppts.getAvailableTimes();
		EndTime.setValue("");
		StartTime.setValue("");
	}

	@FXML
	void DeleteApt(ActionEvent event){
		CustomerAppts.deleteAppt();
		CustomerAppts.getAllAppts();
		CustomerAppts.clearFields();
	}

	@FXML
	void Exit(ActionEvent event){
		System.exit(0);
	}


}
