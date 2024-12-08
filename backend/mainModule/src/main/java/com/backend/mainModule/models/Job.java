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
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobID;

    private String title;         
    private String description;    
    private String location;       
    private String employmentType; 
    @ManyToOne
    @JoinColumn(name = "id")
    private HR hr;

    @Column(columnDefinition = "jsonb")
    private List<String> keywords;

    @ManyToOne
    @JoinColumn(name = "orgID")
    private Organisation organisation;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Applicants> applicants;
}
