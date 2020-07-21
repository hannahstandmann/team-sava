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
package com.smbaiwsy.teamsava.service.impl;

import com.smbaiwsy.teamsava.document.User;
import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import com.smbaiwsy.teamsava.exception.UserNotFoundException;
import com.smbaiwsy.teamsava.repository.UserRepository;
import com.smbaiwsy.teamsava.service.UserService;
import java.time.LocalDate;
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
            throw new UserNotFoundException("NOT_FOUND", "Record not Found");
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
                                .creationDate(LocalDate.now())
                                .build()));

    }

}
