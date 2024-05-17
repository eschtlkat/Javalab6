package application;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.ObservableList;


public class LoadFiles extends Thread {
	
	private final String fileName;
	private int numOfLines = 0;
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
			        data.add(personData);

			        Thread.sleep(3000);
					
				} catch (InterruptedException e) {
					System.out.println("Thread was interrupted");
					e.printStackTrace();
				} 
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Failed to read a file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Failed to read a line number " + numOfLines);
		}
    }
    
}
