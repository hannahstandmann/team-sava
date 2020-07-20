/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        String col = (null == column || "".equals(column))?"birthday":column;
        SortOrder dir = null == direction?SortOrder.ASC:direction;
        return userService.getUsers(col,dir);
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
