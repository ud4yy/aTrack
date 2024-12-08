package com.backend.mainModule.models;
import org.hibernate.annotations.Type;
import io.hypersistence.utils.hibernate.type.json.JsonType;

import java.util.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.ser.std.MapSerializer;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicants")
public class Applicants {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicantID;
    
    @ManyToOne
    @JoinColumn(name = "jobID")
    private Job job;  

    private String name;   // Applicant's name
    private String email;  // Applicant's email
    private String resumeUrl;
    
    @Column(columnDefinition = "jsonb")
    private List<String> resume_keywords;

    private Status applicationStatus; 
    
    private Integer score;

    @Column(columnDefinition = "jsonb")
    @Type(JsonType.class)
    private Map<String, List<String>> skills;
}