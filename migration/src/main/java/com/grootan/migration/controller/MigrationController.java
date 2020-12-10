/**
 * 
 */
package com.grootan.migration.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.grootan.migration.service.MigrationService;

/**
 * @author Mathivathani
 *
 */

@Controller
public class MigrationController {
	@Autowired
	MigrationService migrationService;

    //Save the uploaded file to this folder
   // private static String UPLOADED_FOLDER = "F://temp//";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        String message = "";
        int sizeCount =0;

	    if (MigrationHelper.hasCSVFormat(file)) {
	      try {
	    	  sizeCount=migrationService.saveDetails(file);

	        message = "Uploaded the file successfully: " + file.getOriginalFilename();
	        redirectAttributes.addFlashAttribute("message", message);
	        redirectAttributes.addAttribute("count", sizeCount);
	        return "redirect:/uploadStatus";
	      } catch (Exception e) {
	    	  e.printStackTrace();
	        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	        redirectAttributes.addFlashAttribute("message", message);
	        redirectAttributes.addAttribute("count", sizeCount);

	        return "redirect:/uploadStatus";
	      }
	    }

	    message = "Please upload a csv file!";
	    redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addAttribute("count", sizeCount);
        
        System.out.println("Count: " + sizeCount);

	    return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
    
    @GetMapping("/download/failureList.csv")
    public void downloadCsv(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=failureList.csv");
        migrationService.downloadCsv(response.getWriter()) ;
    }

}