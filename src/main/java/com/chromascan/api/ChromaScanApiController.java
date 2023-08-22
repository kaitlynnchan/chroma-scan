// package com.chromascan.api;

// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// @Controller
// public class ChromaScanApiController {
    
// 	@PostMapping("/files/")
// 	public String handleFileUpload(@RequestParam("file") MultipartFile file,
// 			RedirectAttributes redirectAttributes) {

//         // if file does not exists in database add it
//         // if it does return file
// 		// storageService.store(file);
// 		// redirectAttributes.addFlashAttribute("message",
// 		// 		"You successfully uploaded " + file.getOriginalFilename() + "!");

// 		return "redirect:/";
// 	}
// }
