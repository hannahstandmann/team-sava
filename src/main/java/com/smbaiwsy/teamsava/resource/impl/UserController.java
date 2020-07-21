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
package com.smbaiwsy.teamsava.resource.impl;

import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import com.smbaiwsy.teamsava.resource.UserOperations;
import com.smbaiwsy.teamsava.service.UserService;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController implements UserOperations {
    
    @Autowired
    private UserService userService;

    @Override
    public Collection<UserDto> getAll(String column, SortOrder direction) {
       // String col = (null == column || "".equals(column))?"birthday":column;
        SortOrder dir = null == direction?SortOrder.ASC:direction;
        return userService.getUsers(column,dir);
    }
    
    @Override
    public Optional<UserDto> getById(String id) {
        return Optional.of(userService.getUserById(id));
    }
	 
    @Override
    public UserDto create(UserDto userDTO) {
        return userService.createUser(userDTO);
    }
	 
}
