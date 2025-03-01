package com.csvData.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.csvData.Message.ResponseMessage;
import com.csvData.Service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {
	@Autowired
	private FileService fileService;
    
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> processFile(@RequestParam("file") MultipartFile file) throws IOException{
	if(fileService.hasCSVFormat(file)) {
		fileService.saveData(file);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseMessage("File saved succssfully"));	
	}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("application will accept only CSV files"));	
	}
}
