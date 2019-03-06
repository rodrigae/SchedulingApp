package Controllers;

import java.util.List;

import Model.Customer;
import Model.User;

/*Author: Anthony Rodriguez
 * Date: 02/23/2019
 * Description: This class contains the login information to access the main system.
 * From here users are tracked after every log in, and saved to a text file
 */

import application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import mySQL.Statements;
import mySQL.loginTracker;
public class LoginController {



	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button loginBtn;
	@FXML
	private Button closeBtn;
	@FXML
	private Label errorlabel;
        @FXML
        private Button TranslateError;

	private Stage InitialStage;

	private  User  user = new User();

	@FXML
	    void Login(ActionEvent event) {
		//Obtain the user information and validated access
		  user.setUserName(username.getText());
	      if(username.getText().isEmpty() || password.getText().isEmpty()){
                        TranslateError.setVisible(true);
	    	 	errorlabel.setText("Please enter username and password.");
              }else{
	        	boolean result = Statements.UserValidated(username.getText(), password.getText());
	        	if (result){
                                TranslateError.setVisible(false);
	        		loginTracker.UserLoginTracker(username.getText());
	        		ShowMain();
	        	}else{
	        		errorlabel.setText("You are not authorized.\nUsted no est� autorizado.");
	        	}

	        }
	    }
            
            @FXML 
            void Translate(ActionEvent event){
                switch(TranslateError.getText()){
                    case "Translate To Spanish":
                        errorlabel.setText("Por favor ingrese su nombre de usuario y contrasena. ");
                        TranslateError.setText("Translate To English");
                        break;
                      case "Translate To English":
                        errorlabel.setText("Please enter username and password.");
                        TranslateError.setText("Translate To Spanish");
                        break;
                }
            }

	  @FXML
	  void CloseAppHandle(ActionEvent event){
		  System.exit(0);
	  }

	  private void ShowMain(){
		  //once a user has been validated, will provide access to the rest of the system here
		  try{


			FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/Views/SchedulingApp.fxml"));
            AnchorPane Home = (AnchorPane) loader.load();

           //Create the Home Page
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Scheduling");
            dialogStage.setResizable(false);
            InitialStage.hide();
            dialogStage.initOwner(InitialStage);
            Scene scene = new Scene(Home);
            dialogStage.setScene(scene);

         //   List<Model.Customer> list = Statements.getCustomerList();
          //  SchedulingApp.setAll(list);
            // Show the dialog and wait until the user closes it


            List<Customer> DueAppts = Statements.getApptListByConsultant(user.getUserName());
            StringBuilder Appts = new StringBuilder("Appointment: ");

            if (DueAppts.isEmpty()) {
            	//do nothing;
            } else {
            	//Lambda used to obtain appt from list
            	DueAppts.forEach(a -> {
            	String type = a.getDescription();
            	 String customer = a.getFullName();
            	String	start = a.getStart();
            		Appts.append("Description - "+ type + " with " + customer + " is set for " + start);
            		Appts.append("\n");

            	});
                Alert notification = new Alert(Alert.AlertType.INFORMATION);
                notification.setTitle("Upcoming Appointment Reminder");
                notification.setHeaderText("Reminder: Here are your appointment within the next 15 minutes.");
                notification.setContentText(Appts.toString());
                notification.showAndWait();
            }

            dialogStage.showAndWait();

            }catch (Exception e){
			  e.printStackTrace();
		  }
	  }

	  public void AssignStage(Stage stage){
		  InitialStage = stage;
	  }


}
