package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import mySQL.Database;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class App extends Application {
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		try {
			// Load root layout from fxml file.
			  	FXMLLoader loader = new FXMLLoader();
	            loader.setLocation(App.class.getResource("/Views/Login.fxml"));
	            AnchorPane loginScreen = (AnchorPane) loader.load();

	            //Pass the stage to the login controller to assign new scenes
	            LoginController controller = loader.getController();
	            controller.AssignStage(primaryStage);

	            // Show the scene containing the root layout.
	            Scene scene = new Scene(loginScreen);
	            primaryStage.setTitle("Schedule Login");
	            primaryStage.setResizable(false);
	            primaryStage.setScene(scene);
	            primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		 	Database.begin();
		 	Logging.LogInformation();
	        launch(args);
	        Database.TerminatedDB();

	}

}
