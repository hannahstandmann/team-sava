/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsava.resource;

import com.smbaiwsy.teamsava.dto.SortOrder;
import com.smbaiwsy.teamsava.dto.UserDto;
import java.util.Collection;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@RequestMapping("/default")
public interface UserOperations {

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    Collection<UserDto> getAll(@Nullable @RequestParam String column, @Nullable @RequestParam SortOrder direction);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<UserDto> getById(@PathVariable String id);

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto create(@RequestBody UserDto userDTO);
}
