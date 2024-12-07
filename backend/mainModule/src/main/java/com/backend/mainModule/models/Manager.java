package com.backend.mainModule.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long managerID; // Unique ID for each applicant
    
    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "UID") 
    private User user;

    @OneToOne
    @JoinColumn(name = "organisation_id", referencedColumnName = "orgId") 
    private Organisation organisation;

    private String email;

    @OneToMany(mappedBy = "manager") 
    //with mapped by the joining tables are not created, 
    //logical mapping is created between 2 models however.
    //the owning side of relationship, that table will have a
    // FK
    private List<HR> hrList; 
}
