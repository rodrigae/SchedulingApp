package ControllerActions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import mySQL.Statements;

public class Reports {


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
	private TableView<Model.Appointment> ApptByTypeMonthsTable;
	@FXML
	private TableColumn<Model.Customer, String> reportmonthColumn;
	@FXML
	private TableColumn<Model.Customer, String> reporttypeColumn;
	@FXML
	private TableColumn<Model.Customer, String> reportTypeCount;
	@FXML
	private BarChart TypeChart;

	public Reports(BarChart TypeChart, TableView<Model.Appointment> AppByTypeMonthsTable, TableColumn<Model.Customer, String> reportMonthColumn, TableColumn<Model.Customer, String> reporttypeColumn,TableColumn<Model.Customer, String> reportTypeCount, TableView<Model.Customer> myScheduleTable, TableColumn<Model.Customer, String> reportApptAgent,
			TableColumn<Model.Customer, String> reportApptName, TableColumn<Model.Customer, String> reporttitle,
			TableColumn<Model.Customer, String> reportstart, TableColumn<Model.Customer, String> reportend,
			TableColumn<Model.Customer, String> reportType) {

		this.MyScheduleTable = myScheduleTable; this.reportApptAgent = reportApptAgent; this.reportApptName = reportApptName; this.reporttitle = reporttitle; this.reportstart = reportstart; this.reportend = reportend;
		this.reportType = reportType; this.ApptByTypeMonthsTable = AppByTypeMonthsTable; this.reportmonthColumn = reportMonthColumn; this.reporttypeColumn = reporttypeColumn; this.reportTypeCount = reportTypeCount;
		this.TypeChart = TypeChart;
		// TODO Auto-generated constructor stub
	}

	public void LoadMySchedule(String User){

	}

	public void  getApptsByCustomer(String User){
		//Load SChedule for the logged in user
		MyScheduleTable.getItems().clear();//remove the current listed items before update
		reportApptAgent.setCellValueFactory(new PropertyValueFactory<>("Agent"));
		reportApptName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		reporttitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		reportstart.setCellValueFactory(new PropertyValueFactory<>("start"));
		reportend.setCellValueFactory(new PropertyValueFactory<>("end"));
		reportType.setCellValueFactory(new PropertyValueFactory<>("description"));
		MyScheduleTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		MyScheduleTable.getItems().setAll(Statements.getMySchedule(User));

	}

	public void  getApptsByMonth(){
		//Load the data by month and count
		ApptByTypeMonthsTable.getItems().clear();//remove the current listed items before update
		reportmonthColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
		reporttypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
		reportTypeCount.setCellValueFactory(new PropertyValueFactory<>("typeCount"));
		ApptByTypeMonthsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		ApptByTypeMonthsTable.getItems().setAll(Statements.getTypeByMonth());

	}

	@SuppressWarnings("unchecked")
	public void LoadChart(){
		//populate Chart report
			TypeChart.getData().clear();
	        XYChart.Series<String, Integer> data = new XYChart.Series<>();
	        data.getData().addAll(Statements.getTypeChartData());
	        TypeChart.getData().add(data);
	}

}
