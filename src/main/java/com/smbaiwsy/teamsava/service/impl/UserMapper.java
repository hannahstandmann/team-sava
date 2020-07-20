/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.service.impl;

import com.smbaiwsy.teamsava.document.User;
import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.data.domain.Sort;

/**
 *
 * @author anamattuzzi-stojanovic
 */
public class UserMapper {
    public static UserDto fromUser(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .surname(user.getSurname())
                .birthday(user.getBirthday())
                .build();
    }


    public static <T> Stream<T> collectionToStream(Collection<T> collection) {
        return Optional.ofNullable(collection)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }
    
    public static Sort createSort(String column, SortOrder direction){
        String sortColumn = "birthday";
        switch(column){
            case "firstName":
                sortColumn = "firstName";
                break;
            case "surname":
                sortColumn = "surname";
                break;
            default:
                sortColumn = "birthday";
                break;
        }
        return  direction == SortOrder.ASC? 
                Sort.by(sortColumn).ascending():
                Sort.by(sortColumn).descending();
    }
    
}
