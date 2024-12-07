package com.backend.mainModule.repo;

import com.backend.mainModule.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<Job, Long> {
    
    List<Job> findByOrganisation_OrgId(Long orgId);
}
