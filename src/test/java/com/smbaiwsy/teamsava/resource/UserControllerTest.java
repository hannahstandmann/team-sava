/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.resource;

import com.smbaiwsy.teamsava.dto.UserDto;
import com.smbaiwsy.teamsava.exception.UserNotFoundException;
import com.smbaiwsy.teamsava.resource.impl.UserController;
import com.smbaiwsy.teamsava.service.UserService;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import static net.bytebuddy.matcher.ElementMatchers.is;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 *
 * @author anamattuzzi-stojanovic
 */

@Slf4j
@WebMvcTest(value = UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;

    @Test
    public void retrieveUser() throws Exception {
        
        LocalDate birthday = LocalDate.of(1974, 7, 28);

        UserDto retrieved = UserDto.builder()
                .id("1")
                .birthday(birthday)
                .firstName("Ana")
                .surname("Mattuzzi")
                .build();
        
        Mockito.when(
                userService.getUserById("1"))
                .thenReturn(retrieved);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/users/1").accept(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.surname").value("Mattuzzi"))
                .andExpect(jsonPath("$.birthday").value("1974-07-28"));
      
    }
    
    @Test
    public void noUserFound() throws Exception {
        


      
        
        Mockito.when(
                userService.getUserById("1"))
                .thenThrow(new UserNotFoundException("NOT_FOUND", "Record not found"));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/users/1").accept(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Record not found"));
      
    }

}
