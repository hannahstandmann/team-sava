/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.document;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@Document (collection="team_sava_user")
@Data
@Builder
public class User {
    @Id
    private String id;
    
    private String firstName;
    
    private String surname;
    
    private LocalDate birthday;
    
    private LocalDate creationDate;
    
}
