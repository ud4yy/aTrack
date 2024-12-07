package com.backend.mainModule.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
@Entity
@Table(name = "HR")
@AllArgsConstructor
@Data
public class HR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobID;

    @OneToOne
    @JoinColumn(name = "hr_id", referencedColumnName = "UID")
    private User user;

    @OneToOne
    @JoinColumn(name = "organisation_id", referencedColumnName = "orgId")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "mng_id")
    private Manager manager;

    private String email;
}
