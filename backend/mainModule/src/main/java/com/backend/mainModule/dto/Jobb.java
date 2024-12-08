package com.backend.mainModule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Jobb {
    private String title;         
    private String description;    
    private String location;       
    private String employmentType; 
}
