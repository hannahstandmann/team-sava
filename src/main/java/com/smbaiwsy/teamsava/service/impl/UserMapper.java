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

    public static Sort createSort(String column, SortOrder direction) {
        String sortColumn = "birthday";
        if (column != null) {
            switch (column) {
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
        }
        return direction == SortOrder.ASC
                ? Sort.by(sortColumn).ascending()
                : Sort.by(sortColumn).descending();
    }

}
