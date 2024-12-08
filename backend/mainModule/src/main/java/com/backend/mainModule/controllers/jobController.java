package com.backend.mainModule.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.mainModule.dto.Applicantt;
import com.backend.mainModule.dto.Jobb;
import com.backend.mainModule.models.Applicants;
import com.backend.mainModule.models.HR;
import com.backend.mainModule.models.Job;
import com.backend.mainModule.models.Organisation;
import com.backend.mainModule.models.Status;
import com.backend.mainModule.repo.*;
import com.backend.mainModule.util.SupabaseService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import java.util.*;

@RestController
@RequestMapping("/api")
public class jobController {

    @Autowired
    JobRepo jobRepo;

    @Autowired
    HrRepo hrRepo;

    @Autowired
    ApplicantRepo applicantrepo;

    @Autowired
    OrganisationRepo organisationRepo;

    @Autowired
    SupabaseService supabaseService;
    @PostMapping("/job")
    public ResponseEntity<String> createJob(@RequestBody Jobb jobb) {
        try {
            // Find the HR with ID 2
            HR hr = hrRepo.findById(2L).orElseThrow(() -> new RuntimeException("HR not found"));

            // Find the Organisation with ID 1
            Organisation organisation = organisationRepo.findById(1L).orElseThrow(() -> new RuntimeException("Organisation not found"));

            // Create a new Job entity
            Job job = new Job();
            job.setTitle(jobb.getTitle());
            job.setDescription(jobb.getDescription());
            job.setLocation(jobb.getLocation());
            job.setEmploymentType(jobb.getEmploymentType());
            job.setHr(hr); // Set HR reference
            job.setOrganisation(organisation); // Set Organisation reference

            jobRepo.save(job);

            return ResponseEntity.ok("Job created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

   @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName) {
        try {
            String fileUrl = supabaseService.uploadFile(file, fileName);
            return ResponseEntity.ok("File uploaded successfully: " + fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file: " + e.getMessage());
        }
    }

    @PostMapping("/apply")
    public ResponseEntity<?> applyJob(
        @RequestParam("name") String name,
        @RequestParam("email") String email,
        @RequestParam("jobID") Long jobID,
        @RequestParam("pdf") MultipartFile pdf) {
        
        try {
            Applicants applicant = new Applicants();
            applicant.setName(name);
            applicant.setEmail(email);
            
            Job job = jobRepo.findById(jobID)
                .orElseThrow(() -> new RuntimeException("Job not found"));
            applicant.setJob(job);
            
            applicant.setApplicationStatus(Status.Pending);
            
                     
            Applicants savedApplicant = applicantrepo.save(applicant);
            
            // Upload resume
            String fileName = savedApplicant.getApplicantID() + ".pdf";
            System.out.println(fileName);
            String resumeUrl = supabaseService.uploadFile(pdf, fileName);
            
            savedApplicant.setResumeUrl(resumeUrl);
            applicantrepo.save(savedApplicant);
            
            return ResponseEntity.ok("Application submitted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error submitting application: " + e.getMessage());
        }
    }


    
    
}
