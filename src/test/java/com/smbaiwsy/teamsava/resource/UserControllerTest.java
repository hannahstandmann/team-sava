/*
 * Copyright 2020 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smbaiwsy.teamsava.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import com.smbaiwsy.teamsava.exception.UserNotFoundException;
import com.smbaiwsy.teamsava.resource.impl.UserController;
import com.smbaiwsy.teamsava.service.UserService;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private ObjectMapper mapper;

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

    @Test
    public void retrieveUsers() throws Exception {

        Collection<UserDto> retrieved = List.of(
                UserDto.builder()
                        .id("1")
                        .birthday(LocalDate.of(1974, 7, 28))
                        .firstName("Ana")
                        .surname("Mattuzzi")
                        .build(),
                UserDto.builder()
                        .id("2")
                        .birthday(LocalDate.of(1977, 10, 23))
                        .firstName("Miloš")
                        .surname("Mirković")
                        .build());

        Mockito.when(
                userService.getUsers(null, SortOrder.ASC))
                .thenReturn(retrieved);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/users").accept(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].firstName").value("Ana"))
                .andExpect(jsonPath("$.[0].surname").value("Mattuzzi"))
                .andExpect(jsonPath("$.[0].birthday").value("1974-07-28"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].firstName").value("Miloš"))
                .andExpect(jsonPath("$.[1].surname").value("Mirković"))
                .andExpect(jsonPath("$.[1].birthday").value("1977-10-23"));

    }

    @Test
    public void createUser() throws Exception {

        UserDto createUser = UserDto.builder()
                .birthday(LocalDate.of(1974, 7, 28))
                .firstName("Ana")
                .surname("Mattuzzi")
                .build();
        UserDto createdUser = UserDto.builder()
                .id("1")
                .birthday(LocalDate.of(1974, 7, 28))
                .firstName("Ana")
                .surname("Mattuzzi")
                .build();
        Mockito.when(
                userService.createUser(createUser))
                .thenReturn(createdUser);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/users")
                .content(mapper.writeValueAsString(createUser))
                .accept(
                        MediaType.APPLICATION_JSON)
                .contentType(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ana"))
                .andExpect(jsonPath("$.surname").value("Mattuzzi"))
                .andExpect(jsonPath("$.birthday").value("1974-07-28"));
    }

}
