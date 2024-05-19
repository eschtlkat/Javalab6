package application;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;


public class LoadFiles extends Thread {
	
	public Controller controller;
	private final String fileName;
	private int numOfLines = 0;
	private int currentLine = 0;
    private ObservableList<PersonData> data;
    
    public LoadFiles(String fileName, ObservableList<PersonData> data, Controller controller) {
		this.fileName = fileName;
		this.data = data;
		this.controller = controller;
    }
    
    @Override
    public void run() {
    	
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			numOfLines++;
			String onePersonInfo = reader.readLine(); 
			currentLine = 1;
			while ((onePersonInfo = reader.readLine()) != null) {
				try {
					PersonData personData = new PersonData();
					String[] row = onePersonInfo.split(",");
					int id = Integer.parseInt(row[0]);
					personData.setId(id);
					personData.setFirstName(row[1]);
					personData.setLastName(row[2]);
					personData.setEmail(row[3]);
					personData.setGender(row[4]);
					personData.setCountry(row[5]);
					personData.setDomain(row[6]);
			        LocalDate birthDay = LocalDate.parse(row[7]);
			        personData.setBirthDate(birthDay);
			        Platform.runLater(() -> data.add(personData));
			        currentLine++;
			        numOfLines++;
			        Thread.sleep(1);
					
				} catch (InterruptedException e) {
					System.out.println("Thread was interrupted");
					e.printStackTrace();
				} catch (DateTimeParseException e) {
		            System.out.println("Invalid date format for line " + currentLine + " in file " + fileName);
		        }
			}
			reader.close();
			Platform.runLater(() -> controller.table.setItems(data));
			
			if (fileName == "MOCK_DATA1.csv") {
				Platform.runLater(() -> controller.firstFileStatus.setText("First file status: Completed."));
			}
			else if (fileName == "MOCK_DATA2.csv") {
				Platform.runLater(() -> controller.secondFileStatus.setText("Second file status: Completed."));
			}
			else if (fileName == "MOCK_DATA3.csv") {
				Platform.runLater(() -> controller.thirdFileStatus.setText("Third file status: Completed."));
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read a file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to read a line number " + numOfLines);
		}
    }
    
}
