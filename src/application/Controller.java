package application;

import java.time.LocalDate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
	ObservableList<PersonData> filteredData = FXCollections.observableArrayList();
	
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
    private String choiceForFiltering;
    
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
	
	
    Predicate<PersonData> dateFilter = person -> {
        if (dateFrom == null && dateTo == null) {
            return true;
        }


        LocalDate personBirthDate = person.getBirthDate();
        if(dateFrom != null && dateTo != null) {
            return personBirthDate.isAfter(dateFrom) && personBirthDate.isBefore(dateTo);
        }
        else if (dateFrom != null) {
            return personBirthDate.isAfter(dateFrom);
        }
        else {
            return personBirthDate.isBefore(dateTo);
        }

    };
    
    public void Sort() {
    	choiceForFiltering = choiceBox.getValue();
    	Filtering();
    	
    	dateFrom = datePickerFrom.getValue();
    	dateTo = datePickerTo.getValue();
    	FilteredList<PersonData> filteredList = new FilteredList(filteredData);
		filteredList.setPredicate(dateFilter);
		Platform.runLater(() -> table.setItems(filteredList));
		
		
    }
    
    public void Filtering () {
    	if (choiceForFiltering == "ID ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getId() - b.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "ID descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getId() - a.getId()).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "First Name ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getFirstName().compareTo(b.getFirstName())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "First Name descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getFirstName().compareTo(a.getFirstName())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Last Name ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getLastName().compareTo(b.getLastName())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Last Name descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getLastName().compareTo(a.getLastName())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Email ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getEmail().compareTo(b.getEmail())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Email descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getEmail().compareTo(a.getEmail())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Gender ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getGender().compareTo(b.getGender())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Gender descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getGender().compareTo(a.getGender())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Country ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getCountry().compareTo(b.getCountry())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Country descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getCountry().compareTo(a.getCountry())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Domain Name ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getDomain().compareTo(b.getDomain())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Domain Name descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getDomain().compareTo(a.getDomain())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Birth Date ascending") {
    		filteredData = data.stream().sorted((a, b) -> a.getBirthDate().compareTo(b.getBirthDate())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	} else if (choiceForFiltering == "Birth Date descending") {
    		filteredData = data.stream().sorted((a, b) -> b.getBirthDate().compareTo(a.getBirthDate())).collect(Collectors.toCollection(FXCollections::observableArrayList));
    		
    	}
    }
	
	
	
	
	
	
	
	
}
