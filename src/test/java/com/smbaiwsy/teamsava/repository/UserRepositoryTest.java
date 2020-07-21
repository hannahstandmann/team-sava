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
package com.smbaiwsy.teamsava.repository;

/**
 *
 * @author anamattuzzi-stojanovic
 */
import com.smbaiwsy.teamsava.document.User;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
 
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryTest {
    
    @BeforeEach
    public void before(@Autowired MongoTemplate mongoTemplate){
        mongoTemplate.dropCollection("collection");
    }

    @Test
    public void testSort(@Autowired MongoTemplate mongoTemplate) {
        // given
        User user1 = User.builder()
                .firstName("Ana")
                .surname("Mattuzzi")
                .birthday(LocalDate.of(1974, 7, 28))
                .creationDate(LocalDate.now())
                .build();
        User user2 = User.builder()
                .firstName("Miloš")
                .surname("Mirković")
                .birthday(LocalDate.of(1977, 10, 23))
                .creationDate(LocalDate.now())
                .build();
      
        // when
        mongoTemplate.save(user2, "collection");
        mongoTemplate.save(user1, "collection");
        
        Query query = new Query().with(Sort.by("birthday").ascending());
        List<User> allUsers = mongoTemplate.find(query, User.class, "collection");

        // then
        assertEquals(2, allUsers.size());
        assertEquals( "Ana", allUsers.get(0).getFirstName());
        assertEquals( "Miloš", allUsers.get(1).getFirstName());
        
        
    }
    
    @Test
    public void testFindOne(@Autowired MongoTemplate mongoTemplate) {
        
        LocalDate creationDate = LocalDate.now();
        // given
        User user1 = User.builder()
                .firstName("Ana")
                .surname("Mattuzzi")
                .birthday(LocalDate.of(1974, 7, 28))
                .creationDate(creationDate)
                .build();
        User user2 = User.builder()
                .firstName("Miloš")
                .surname("Mirković")
                .birthday(LocalDate.of(1977, 10, 23))
                .creationDate(creationDate)
                .build();
        User user3 = User.builder()
                .firstName("Hannah")
                .surname("Standmann")
                .birthday(LocalDate.of(1974, 07, 28))
                .creationDate(creationDate)
                .build();
      
        // when
        mongoTemplate.save(user1, "collection");
        mongoTemplate.save(user2, "collection");
        mongoTemplate.save(user3, "collection");
        
        Query query = new Query(); //.with(Sort.by("birthday").ascending());
        List<User> allUsers = mongoTemplate.find(query, User.class, "collection");
        String id = allUsers.get(0).getId();
        
     
        User findById = mongoTemplate.findById(id, User.class, "collection");

        // then
        assertEquals( id, findById.getId());
        assertEquals( "Ana", findById.getFirstName());
        assertEquals( "Mattuzzi", findById.getSurname());
        assertEquals( creationDate, findById.getCreationDate());
        assertEquals( LocalDate.of(1974, Month.JULY, 28), findById.getBirthday());
    }
    
    
}
