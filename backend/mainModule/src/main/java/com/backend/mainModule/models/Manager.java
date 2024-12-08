package com.backend.mainModule.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@Table(name = "manager")
@NoArgsConstructor
public class Manager {
    @Id
    private Long managerID; 
    
    @MapsId
    @OneToOne
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "orgId")
    private Organisation organisation;

    private String email;

    @OneToMany(mappedBy = "manager") 
    //with mapped by the joining tables are not created, 
    //logical mapping is created between 2 models however.
    //the owning side of relationship, that table will have a
    // FK
    private List<HR> hrList; 
}
