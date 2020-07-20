/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.service;

import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import java.util.Collection;

/**
 *
 * @author anamattuzzi-stojanovic
 */
public interface UserService {

    public Collection<UserDto> getUsers(String column, SortOrder direction);

    public UserDto getUserById(String id);

    public UserDto createUser(UserDto userDTO);

}

