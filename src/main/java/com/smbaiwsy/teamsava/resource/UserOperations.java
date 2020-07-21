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
    Collection<UserDto> getAll(@Nullable @RequestParam(required = false) String column, @Nullable @RequestParam(required = false) SortOrder direction);

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Optional<UserDto> getById(@PathVariable String id);

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public UserDto create(@RequestBody UserDto userDTO);
}
