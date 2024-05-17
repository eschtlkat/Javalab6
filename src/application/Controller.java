package application;

import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class Controller {
	
	@FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private DatePicker datePickerFrom;

    @FXML
    private DatePicker datePickerTo;

    @FXML
    public Label firstFileStatus;

    @FXML
    private Button loadFiles;

    @FXML
    public Label secondFileStatus;

    @FXML
    private Button submit;

    @FXML
    public TableView<PersonData> table;

    @FXML
    public Label thirdFileStatus;
    
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
	}
	


}
