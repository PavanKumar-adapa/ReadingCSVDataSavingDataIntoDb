package com.csvData.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csvData.Entity.Employee;
import com.csvData.Repository.EmployeeRepository;

@Service
public class FileService {
	
	@Autowired
	private EmployeeRepository empRepo;
    
	//check for the file format
	public boolean hasCSVFormat(MultipartFile file) {
	    String type="text/csv";
	    if(!type.equals(file.getContentType()))
	      return false;
		return true;
	}
    
	
	//process the file data to DB
	public void saveData(MultipartFile file) throws IOException {
        //create a input stream with file 
		List<Employee> emp = csvtoEmployee(file.getInputStream());
		empRepo.saveAll(emp);
	}
    
	private List<Employee> csvtoEmployee(InputStream inputStream) throws IOException {
		//create a file reader with input stream 
         BufferedReader fileReader = new BufferedReader (new InputStreamReader(inputStream, "UTF-8"));
         //parse the CSV file and remove the header 
		try (CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {
			//create list to hold the data to send to data base. 
			List<Employee> empData = new ArrayList<Employee>();
			//Fetch all records in CSV file 
			List<CSVRecord> records  =csvParser.getRecords();
			//Iterate one by one 
			for(CSVRecord csvrecord : records) {
				//map each record to our employee object 
				Employee employees = new Employee(Long.parseLong(csvrecord.get("Index")), csvrecord.get("User Id"), csvrecord.get("First Name"), csvrecord.get("Last Name"), 
						csvrecord.get("Sex"), csvrecord.get("Email"), csvrecord.get("Phone"), csvrecord.get("Date of birth"), csvrecord.get("Job Title"));
				//add each record to employee list
				empData.add(employees);
			}
			//return this list and call saveAll method.
			return empData;
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}

}
