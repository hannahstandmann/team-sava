/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@Data
@Builder
public class UserDto {

    private String id;
    
    private String firstName;

    private String surname;

    private LocalDate birthday;

}
