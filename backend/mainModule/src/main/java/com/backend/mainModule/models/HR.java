package com.backend.mainModule.models;


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
@Table(name = "HR")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class HR {
    @Id
    private Long id;

    @MapsId
    @OneToOne
    @JoinColumn(name = "uid")
    private User user;

    @ManyToOne
    @JoinColumn(name = "orgId")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "managerID")
    private Manager manager;

    private String email;
}
