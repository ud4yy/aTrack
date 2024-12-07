package com.backend.mainModule.repo;

import com.backend.mainModule.models.HR;
import com.backend.mainModule.models.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HrRepo extends JpaRepository<HR, Long> {
    // Find HR by organization
    List<HR> findByOrganisation(Organisation organisation);
    
    // Find HR by email
    Optional<HR> findByEmail(String email);
    
}