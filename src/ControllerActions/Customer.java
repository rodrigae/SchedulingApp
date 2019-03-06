package ControllerActions;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import mySQL.Statements;

public class Customer {

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
	    private TableView<Model.Customer> customerTable;

		@FXML
		private TableColumn<Model.Customer, String> fNameT;
		@FXML
		private TableColumn<Model.Customer, String> phoneT;
		@FXML
		private TableColumn<Model.Customer, String> idT;
		//End of Customer Field Section


		public Customer(TextField Name, TextField address, TextField address2, TextField city, TextField postalCode, TextField country, TextField phoneNo, TextField id,
				Button newButton, Button cancel, Button deleteButton, Label saveLabel, TableView<Model.Customer> customerTable, TableColumn<Model.Customer, String> fNameT,
				TableColumn<Model.Customer, String> phoneT
				,TableColumn<Model.Customer, String> idT,TitledPane CustomerPane){

				this.fullName = Name; this.phoneNo = phoneNo; this.iD = id; this.newButton = newButton; this.deleteButton = deleteButton;
				this.address = address; this.address2 = address2; this.city = city; this.postalCode = postalCode; this.country = country;
				this.saveLabel = saveLabel; this.customerTable = customerTable; this.fNameT = fNameT; this.phoneT = phoneT; this.idT = idT;
				this.CustomerPane = CustomerPane; this.cancel = cancel;

		}


		public void loadCustomers(){
			//method will load the data to the customer table
			idT.setCellValueFactory(new PropertyValueFactory<>("customerId"));
	    	fNameT.setCellValueFactory(new PropertyValueFactory<>("fullName"));
	    	phoneT.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
	    	List<Model.Customer> list = Statements.getCustomerList();
	    	customerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	        customerTable.getItems().setAll(list);

		}


		public boolean verifyEntry(){
			//this method will check the data entry fields on customer pane, it will now allow to save until all data is filled in.
			boolean ready = true;
			if (fullName.getText().isEmpty()){
				saveLabel.setText("Please enter your name");
				fullName.requestFocus();
				return false;
			}else if (city.getText().isEmpty()){
				saveLabel.setText("Please enter your city");
				city.requestFocus();
				return false;
			}else if (country.getText().isEmpty()){
				saveLabel.setText("Please enter your country");
				country.requestFocus();
				return false;
			}else if (address.getText().isEmpty()){
				saveLabel.setText("Please enter your address");
				address.requestFocus();
				return false;
			}else if (postalCode.getText().isEmpty()){
				saveLabel.setText("Please enter your postal code");
				postalCode.requestFocus();
				return false;
			}else if (phoneNo.getText().isEmpty()){
				saveLabel.setText("Please enter your phone number");
				phoneNo.requestFocus();
				return false;
			}

			return true;
		}


		public void actions(String action){
			//create a new customer

			String add2 = "None";
			if (!address2.getText().isEmpty()){
				//replace value for address 2 if empty
				add2 = address2.getText();
			}
			Model.Customer nCustomer = new Model.Customer();

			nCustomer.setCustomerId(iD.getText());
			nCustomer.setAddressId(iD.getText());
			nCustomer.setCityId(iD.getText());
			nCustomer.setCountryId(iD.getText());
			nCustomer.setFullName(fullName.getText());
			nCustomer.setCity(city.getText());
			nCustomer.setAddress(address.getText());
			nCustomer.setAddress2(add2);
			nCustomer.setPostalCode(postalCode.getText());
			nCustomer.setCountry(country.getText());

			//if (customerTable.getFocusModel().getFocusedIndex() > -1){
			//	nCustomer.setCustomerId(customerTable.getSelectionModel().getSelectedItem().getCustomerId());
			//}
			nCustomer.setPhone(phoneNo.getText());

			switch(action){
			case "NewCustomer":
					//save the new customer
					if (Statements.newUser(nCustomer, User.getUserName())){
						//if user gets created, apply the rest of the information
						Statements.newCity(nCustomer, User.getUserName());
						Statements.newCountry(nCustomer, User.getUserName());
						Statements.newUserAddress(nCustomer, User.getUserName());
						disablereadyCustomerItems();
						newButton.setText("New");
						saveLabel.setText("");
					}else{
					saveLabel.setText("User already exists");
				}
				break;

			case "Update":
					//update the customer
					Statements.updateCustomer(nCustomer, User.getUserName());

				break;

			case "Delete":
				//Delete Customer
				Statements.deleteCustomer(nCustomer);
				loadCustomers();
				disablereadyCustomerItems();
				break;
			}


		}


		public void disablereadyCustomerItems(){
			//clear all the fields, and disable the items
			  fullName.setEditable(false);
			  address.setEditable(false);
			  address2.setEditable(false);
			  city.setEditable(false);
			  postalCode.setEditable(false);
			  country.setEditable(false);
			  phoneNo.setEditable(false);
			  fullName.clear();
			  address.clear();
			  address2.clear();
			  city.clear();
			  postalCode.clear();
			  country.clear();
			  phoneNo.clear();
			  iD.clear();
			  cancel.setDisable(true);
			  deleteButton.setDisable(true);
			  saveLabel.setText("");


		}


		public void readyCustomerItems(){
			//clear all the fields before entering new ones for customers
			  fullName.setEditable(true);
			  address.setEditable(true);
			  address2.setEditable(true);
			  city.setEditable(true);
			  postalCode.setEditable(true);
			  country.setEditable(true);
			  phoneNo.setEditable(true);
			  fullName.clear();
			  address.clear();
			  address2.clear();
			  city.clear();
			  postalCode.clear();
			  country.clear();
			  phoneNo.clear();
			  saveLabel.setText("");

		}

}
