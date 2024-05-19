package application;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javafx.application.Platform;
import javafx.collections.ObservableList;


public class LoadFiles extends Thread {
	
	private final String fileName;
	private int numOfLines = 0;
	private int currentLine = 0;
    private ObservableList<PersonData> data;
    
    public LoadFiles(String fileName, ObservableList<PersonData> data) {
		this.fileName = fileName;
		this.data = data;
    }
    
    @Override
    public void run() {
    	
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			numOfLines++;
			String onePersonInfo = reader.readLine(); // nuskaityti pirma eilute, nes nereikalinga
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
			        Thread.sleep(20);
					
				} catch (InterruptedException e) {
					System.out.println("Thread was interrupted");
					e.printStackTrace();
				} catch (DateTimeParseException e) {
		            System.out.println("Invalid date format for line " + currentLine + " in file " + fileName);
		        }
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read a file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to read a line number " + numOfLines);
		}
    	System.out.println(numOfLines + " lines printed.");
    }
    
}
