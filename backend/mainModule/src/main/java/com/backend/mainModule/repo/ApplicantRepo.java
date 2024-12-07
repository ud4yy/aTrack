package com.backend.mainModule.repo;

import com.backend.mainModule.models.Applicants;
import com.backend.mainModule.models.Job;
import com.backend.mainModule.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicants, Long> {
    
    List<Applicants> findByJob(Job job);
    
    List<Applicants> findByApplicationStatus(Status status);
    
    Optional<Applicants> findByEmail(String email);

    @Query(value = "SELECT * FROM applicants a " +
               "WHERE a.job_id = :jobId " +
               "ORDER BY a.score DESC " +
               "LIMIT :limit", 
        nativeQuery = true)
    
    List<Applicants> findTopNApplicantsByJob(
        @Param("jobId") Long jobId,
        @Param("limit") int limit
    );

    

}