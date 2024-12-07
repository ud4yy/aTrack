package com.backend.mainModule.models;

import java.time.LocalDateTime;
import java.util.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;


@Entity
@Table (name = "organisation")
@Data
@AllArgsConstructor
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orgId; // Auto-incremented ID

    private String orgName; 
    @Column(unique = true, nullable = false)
    private String orgEmail; 
    private String address; 
    private String contactNumber; 
    private String industryType; 
    private LocalDateTime createdAt; 
    private LocalDateTime updatedAt; 
    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs; // List of jobs associated with this organisation
}