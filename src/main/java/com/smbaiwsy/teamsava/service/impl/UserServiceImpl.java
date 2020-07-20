/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.service.impl;

import com.mongodb.MongoException;
import org.springframework.data.domain.Sort;
import com.smbaiwsy.teamsava.document.User;
import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import com.smbaiwsy.teamsava.repository.UserRepository;
import com.smbaiwsy.teamsava.service.UserService;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Collection<UserDto> getUsers(String column, SortOrder direction) {
        
        return UserMapper.collectionToStream(
                userRepository.findAll(UserMapper.createSort(column, direction)))
                .map(UserMapper::fromUser)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(String id) {
        if (userRepository.findById(id).isPresent()) {
            return UserMapper.fromUser(userRepository.findById(id).get());
        } else {
            throw new MongoException("Record not Found");
        }
    }

    @Override
    public UserDto createUser(UserDto userDTO) {
        return UserMapper.fromUser(
                userRepository.save(
                        User.builder()
                                .firstName(userDTO.getFirstName())
                                .surname(userDTO.getSurname())
                                .birthday(userDTO.getBirthday())
                                .build()));

    }

}
