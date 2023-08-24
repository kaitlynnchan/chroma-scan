package com.chromascan.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chromascan.api.storage.StorageFileNotFoundException;
import com.chromascan.api.storage.StorageService;
import com.chromascan.controller.ImageController;
import com.chromascan.model.Colour;
import com.chromascan.model.ColourBreakdown;
import com.chromascan.model.DataPoint;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileUploadController {

	private final StorageService storageService;
    private ImageController ic;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
	}

	// @GetMapping("/")
	// public String listUploadedFiles(Model model) throws IOException {

	// 	model.addAttribute("files", storageService.loadAll().map(
	// 			path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
	// 					"serveFile", path.getFileName().toString()).build().toUri().toString())
	// 			.collect(Collectors.toList()));

	// 	return "uploadForm";
	// }

	@GetMapping("/api/file/{filename}")
	public ResponseEntity<ColourBreakdown> getDominantColour(@PathVariable String filename, HttpServletResponse response) {

		Resource file1 = storageService.loadAsResource(filename);
		
		// File file = storageService.loadAsFile(filename);
		try {
			ic = new ImageController(file1.getFile());
			ic.populateBreakdownArr();

			ColourBreakdown cb = ic.getDominantColour();
			response.setStatus(HttpServletResponse.SC_OK);
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=\"" + file1.getFilename() + "\"").body(cb);
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}

	@PostMapping("/api/file/upload")
	public void handleFileUpload(@RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, HttpServletResponse response) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		
		response.setStatus(HttpServletResponse.SC_FOUND);
	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
