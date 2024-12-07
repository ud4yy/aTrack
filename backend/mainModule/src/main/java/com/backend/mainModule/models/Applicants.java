package com.backend.mainModule.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicants")
public class Applicants {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantID; // Unique ID for each applicant
    
    @ManyToOne
    @JoinColumn(name = "job_id", referencedColumnName = "jobID") // Foreign key to Job
    private Job job;  
    
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "UID") // Foreign key to User
    private User user; 
    
    private String name;   // Applicant's name
    private String email;  // Applicant's email
    private String resumeUrl;
    
    @Column(columnDefinition = "jsonb")
    private List<String> resume_keywords;

    private Status applicationStatus; 
    
    @ManyToOne
    @JoinColumn(name = "hr_id", referencedColumnName = "HR_ID") // Foreign key to HR
    private HR hr; 
    
    private Integer score;

    @Column(columnDefinition = "json")
    private String skills;
}
