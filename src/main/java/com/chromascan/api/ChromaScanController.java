package com.chromascan.api;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chromascan.api.storage.StorageFileNotFoundException;
import com.chromascan.api.storage.StorageService;
import com.chromascan.controller.ImageController;
import com.chromascan.model.Color;
import com.chromascan.model.ColorBreakdown;
import com.chromascan.model.DataPoint;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ChromaScanController {

	private final StorageService storageService;
    private ImageController ic;

	@Autowired
	public ChromaScanController(StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping(value = {"/api/file/{filename}/getDominantColor", 
		"/api/file/{filename}/{datapoints}/getDominantColor"})
	public ResponseEntity<ColorBreakdown> getDominantColor(@PathVariable(required = true) String filename,
			@PathVariable(required = false) String[] datapoints, HttpServletResponse response) {
		try {
			Resource file = storageService.loadAsResource(filename);
			// data points are provided
			if(datapoints != null){
				ic = new ImageController(file.getFile(), parseDataPoints(datapoints));
			} else {
				ic = new ImageController(file.getFile());
			}

			ic.populateBreakdownArr();
			ColorBreakdown cb = ic.getDominantColor();
			
			response.setStatus(HttpServletResponse.SC_OK);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file.getFilename() + "\"").body(cb);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	private ArrayList<DataPoint> parseDataPoints(String[] dp){
		// validate that the data has at least 2 points
		if(dp.length % 2 != 0){
			return null;
		}
		// create data point array list
		// first index is x, next is y, alternating
		ArrayList<DataPoint> arr = new ArrayList<DataPoint>();
		for(int i = 0; i < dp.length; i+=2){
			DataPoint point = new DataPoint(Integer.parseInt(dp[i]), Integer.parseInt(dp[i+1]));
			arr.add(point);
		}
		return arr;
	}

	@GetMapping(value = {"/api/file/{filename}/getColorMix", "/api/file/{filename}/{datapoints}/getColorMix"})
	public ResponseEntity<Color> getColorMix(@PathVariable(required = true) String filename, 
			@PathVariable(required = false) String[] datapoints, HttpServletResponse response) {
		try {
			Resource file = storageService.loadAsResource(filename);
			
			// data points are provided
			if(datapoints != null){
				ic = new ImageController(file.getFile(), parseDataPoints(datapoints));
			} else {
				ic = new ImageController(file.getFile());
			}

			ic.populateBreakdownArr();
			Color cb = ic.getColorMix();
			
			response.setStatus(HttpServletResponse.SC_OK);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file.getFilename() + "\"").body(cb);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	@PostMapping("/api/file/upload")
	public void handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, HttpServletResponse response) {
		// upload file to storage service
		storageService.store(file);
		response.setStatus(HttpServletResponse.SC_FOUND);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
