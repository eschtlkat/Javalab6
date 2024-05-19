package application;

import java.time.LocalDate;
import java.util.function.Predicate;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
	ObservableList<PersonData> data = FXCollections.observableArrayList();
	
	@FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    public Label firstFileStatus;

    @FXML
    private Button loadFilesButton;

    @FXML
    public Label secondFileStatus;

    @FXML
    private Button submit;

    @FXML
    public TableView<PersonData> table;

    @FXML
    public Label thirdFileStatus;
    
    LocalDate dateFrom;
    LocalDate dateTo;
    
	@FXML
	void initialize () {
		
		choiceBox.getItems().addAll("ID ascending", "ID descending", "First Name ascending", "First Name descending", "Last Name ascending", "Last Name descending", "Email ascending", "Email descending", "Gender ascending", "Gender descending", "Country ascending", "Country descending", "Domain Name ascending", "Domain Name descending", "Birth Date ascending", "Birth Date descending");
		
		TableColumn<PersonData, String> id = new TableColumn<>("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table.getColumns().add(id);

        TableColumn<PersonData, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        table.getColumns().add(firstName);

        TableColumn<PersonData, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        table.getColumns().add(lastName);

        TableColumn<PersonData, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        table.getColumns().add(email);

        TableColumn<PersonData, String> gender = new TableColumn<>("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        table.getColumns().add(gender);

        TableColumn<PersonData, String> country = new TableColumn<>("Country");
        country.setCellValueFactory(new PropertyValueFactory<>("country"));
        table.getColumns().add(country);

        TableColumn<PersonData, String> domainName = new TableColumn<>("Domain Name");
        domainName.setCellValueFactory(new PropertyValueFactory<>("domain"));
        table.getColumns().add(domainName);

        TableColumn<PersonData, LocalDate> birthDate = new TableColumn<>("Birth Date");
        birthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        table.getColumns().add(birthDate);
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        Platform.runLater(() -> table.setItems(data));
	}
	
	@FXML
	public void Load() {
		
		Platform.runLater(() -> firstFileStatus.setText("First file status: Loading."));
		Platform.runLater(() -> secondFileStatus.setText("Second file status: Loading."));
		Platform.runLater(() -> thirdFileStatus.setText("Third file status: Loading."));
		
		
		Thread thread1 = new LoadFiles("MOCK_DATA1.csv", data, this);
        thread1.start();
        Thread thread2 = new LoadFiles("MOCK_DATA2.csv", data, this);
        thread2.start();
        Thread thread3 = new LoadFiles("MOCK_DATA3.csv", data, this);
        thread3.start();


    }
	
	
    Predicate<PersonData> dateFilter = personData -> {
        if (dateFrom == null && dateTo == null) {
        	System.out.println("NULL");
            return true;
        }


        LocalDate personBirthDate = personData.getBirthDate();
        if(dateFrom != null && dateTo != null) {
        	System.out.println("Not Null");
            return personBirthDate.isAfter(dateFrom) && personBirthDate.isBefore(dateTo);
        }
        else if (dateFrom != null) {
        	System.out.println("Not Null");
            return personBirthDate.isAfter(dateFrom);
        }
        else {
        	System.out.println("Not Null");
            return personBirthDate.isBefore(dateTo);
        }

    };
    
    public void Sort() {
    	dateFrom = datePickerFrom.getValue();
    	dateTo = datePickerTo.getValue();
    }
	
	
	
	
	
	
	
	
}
